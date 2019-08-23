package br.com.dmsec.hecsplunk;


import java.net.URLStreamHandler;
import java.util.Map;



/**
 * The {@code Service} class represents a Splunk service instance at a given
 * address (host:port), accessed using the {@code http} or {@code https}
 * protocol scheme.
 * <p>
 * A {@code Service} instance also captures an optional namespace context
 * consisting of an optional owner name (or "-" wildcard) and optional app name
 * (or "-" wildcard).
 * <p>
 * To access {@code Service} members, the {@code Service} instance must be
 * authenticated by presenting credentials using the {@code login} method, or
 * by constructing the {@code Service} instance using the {@code connect}
 * method, which both creates and authenticates the instance.
 */
public class Service extends br.com.dmsec.hecsplunk.HttpService {
    
    /** The current session token. */
    protected String token = null;

    /** The default simple receiver endpoint. */
    protected String hecEndPoint = "/services/collector/event";

    /** The version of this Splunk instance, once logged in. */
    public String version = null;

    /** The default host name, which is used when a host name is not provided.*/
    public static String DEFAULT_HOST = "localhost";

    /** The default port number, which is used when a port number is not
     * provided. */
    public static int DEFAULT_PORT = 8088;

    /** The default scheme, which is used when a scheme is not provided. */
    public static String DEFAULT_SCHEME = "https";

    /**
     * Creates a new {@code Service} instance using a host.
     *
     * @param host The host name.
     */
    public Service(String host) {
        super(host);
    }

    /**
     * Creates a new {@code Service} instance using a host and port.
     *
     * @param host The host name.
     * @param port The port number.
     */
    public Service(String host, int port) {
        super(host, port);
    }

    /**
     * Creates a new {@code Service} instance using a host, port, and
     * scheme for accessing the service ({@code http} or {@code https}).
     *
     * @param host The host name.
     * @param port The port number.
     * @param scheme The scheme ({@code http} or {@code https}).
     */
    public Service(String host, int port, String scheme) {
        super(host, port, scheme);
    }

    /**
     * Constructs a new {@code Service} instance using the given host,
     * port, and scheme, and instructing it to use the specified HTTPS handler.
     *
     * @param host The host name of the service.
     * @param port The port number of the service.
     * @param scheme Scheme for accessing the service ({@code http} or
     * {@code https}).
     */
    public Service(String host, int port, String scheme,
        URLStreamHandler httpsHandler) {
        this.host = host;
        this.port = port;
        this.scheme = scheme;
        this.httpsHandler = httpsHandler;
    }

    /**
     * Creates a new {@code Service} instance using a collection of arguments.
     *
     * @param args The {@code ServiceArgs} to initialize the service.
     */
    // NOTE: This overload exists primarily to provide better documentation
    //       for the "args" parameter.
    public Service(ServiceArgs args) {
        super();
        
        this.host = Args.<String>get(args,   "host",   args.host != null   ? args.host   : DEFAULT_HOST);
        
        this.port = Args.<Integer>get(args,  "port",   args.port != null   ? args.port   : DEFAULT_PORT);
        this.scheme = Args.<String>get(args, "scheme", args.scheme != null ? args.scheme : DEFAULT_SCHEME);
        this.token = Args.<String>get(args,  "token",  args.token != null  ? args.token  : null);
        this.httpsHandler = Args.<URLStreamHandler>get(args, "httpsHandler", null);
        this.setSslSecurityProtocol(Args.get(args, "SSLSecurityProtocol", Service.getSslSecurityProtocol()));
        
    }

    /**
     * Creates a new {@code Service} instance using a map of arguments.
     *
     * @param args A {@code Map} of arguments to initialize the service.
     */
    public Service(Map<String, Object> args) {
        super();
        
        this.host = Args.<String>get(args, "host", DEFAULT_HOST);
        this.port = Args.<Integer>get(args, "port", DEFAULT_PORT);
        this.scheme = Args.<String>get(args, "scheme", DEFAULT_SCHEME);
        this.token = Args.<String>get(args, "token", null);
        this.httpsHandler = Args.<URLStreamHandler>get(args, "httpsHandler", null);
        this.setSslSecurityProtocol(Args.get(args, "SSLSecurityProtocol", Service.getSslSecurityProtocol()));
       
        this.connectTimeout = Args.<Integer>get(args, "connectTimeout", null);
        this.readTimeout = Args.<Integer>get(args, "readTimeout", null);
    }

    
    public static Service connect(Map<String, Object> args) {
        Service service = new Service(args);
        return service;
    }

    /**
     * Ensures that the given path is fully qualified, prepending a path
     * prefix if necessary. The path prefix is constructed using the current
     * owner and app context when available.
     *
     * @param path The path to verify.
     * @return A fully-qualified resource path.
     */
    String fullpath(String path) {
        return fullpath(path, null);
    }

    /**
     * Ensures that a given path is fully qualified, prepending a path
     * prefix if necessary. The path prefix is constructed using the
     * current owner and app context when available.
     *
     * @param path The path to verify.
     * @param namespace The namespace dictionary (<i>app, owner, sharing</i>).
     * @return A fully-qualified resource path.
     */
    public String fullpath(String path, Args namespace) {
            return path;
    }
    

    /**
     * Returns the receiver object for the Splunk service.
     *
     * @return A Splunk receiver object.
     */
    public Receiver getReceiver() {
        return new Receiver(this);
    }

    /**
     * Returns the current session token. Session tokens can be shared across
     * multiple {@code Service} instances.
     *
     * @return The session token.
     */
    public String getToken() {
        return this.token;
    }



    /**
     * Issues an HTTP request against the service using a request path and
     * message.
     * This method overrides the base {@code HttpService.send} method
     * and applies the Splunk authorization header, which is required for
     * authenticated interactions with the Splunk service.
     *
     * @param path The request path.
     * @param request The request message.
     * @return The HTTP response.
     */
    @Override public ResponseMessage send(String path, RequestMessage request) {
            request.getHeader().put("Authorization", "Splunk " + getToken());
        return super.send(fullpath(path), request);
    }

    /**
     * Provides a session token for use by this {@code Service} instance.
     * Session tokens can be shared across multiple {@code Service} instances.
     *
     * @param value The session token, which is a basic authorization header in
     * the format "Basic <i>sessiontoken</i>", where <i>sessiontoken</i> is the
     * Base64-encoded "username:password" string.
     */
    public void setToken(String value) {
        this.token = value;
	}

    /**
     * Returns true if this Splunk instance's version is no earlier than
     * the version specified in {@code version}.
     *
     * So when called on a Splunk 4.3.2 instance:
     *   * {@code versionIsAtLeast("4.3.2")} is {@code true}.
     *   * {@code versionIsAtLeast("4.1.0")} is {@code true}.
     *   * {@code versionIsAtLeast("5.0.0")} is {@code false}.
     *
     * @param version The version to compare this Splunk instance's version against.
     * @return {@code true} if this Splunk instance's version is equal or
     *         greater than {@code version}; {@code false} otherwise.
     */
    boolean versionIsAtLeast(String version) {
        return versionCompare(version) >= 0;
    }

    /**
     * Returns true if this Splunk instance's version is earlier than
     * the version specified in {@code version}.
     *
     * So when called on a Splunk 4.3.2 instance:
     *   * {@code versionIsEarlierThan("4.3.2")} is {@code false}.
     *   * {@code versionIsEarlierThan("4.1.0")} is {@code false}.
     *   * {@code versionIsEarlierThan("5.0.0")} is {@code true}.
     *
     * @param version The version to compare this Splunk instance's version against.
     * @return {@code true} if this Splunk instance's version is less
     *         than {@code version}; {@code false} otherwise.
     */
    boolean versionIsEarlierThan(String version) {
        return versionCompare(version) < 0;
    }

    /**
     * Returns a value indicating how the version of this Splunk instance
     * compares to a given version:
     * <ul>
     * <li>{@code -1 if this version < the given version}</li>
     * <li>{@code  0 if this version = the given version}</li>
     * <li>{@code  1 if this version > the given version}</li>
     * </ul>
     *
     * @param otherVersion The other version to compare to.
     * @return -1 if this version is less than, 0 if this version is equal to,
     *         or 1 if this version is greater than the given version.
     */
    public int versionCompare(String otherVersion) {
        String[] components1 = this.version.split("\\.");
        String[] components2 = otherVersion.split("\\.");
        int numComponents = Math.max(components1.length, components2.length);

        for (int i = 0; i < numComponents; i++) {
            int c1 = (i < components1.length)
                    ? Integer.parseInt(components1[i], 10) : 0;
            int c2 = (i < components2.length)
                    ? Integer.parseInt(components2[i], 10) : 0;
            if (c1 < c2) {
                return -1;
            } else if (c1 > c2) {
                return 1;
            }
        }
        return 0;
    }
}
