/*
 * Copyright 2012 Splunk, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"): you may
 * not use this file except in compliance with the License. You may obtain
 * a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */

package br.com.dmsec.hecsplunk;

import java.net.URLStreamHandler;

/**
 * The {@code ServiceArgs} class contains a collection of arguments that are
 * used to initialize a Splunk {@code Service} instance.
 */
public class ServiceArgs extends Args {
    /**
	 * 
	 */
	private static final long serialVersionUID = 5130991304852985643L;

	
    public Integer port = null;
   
    public String scheme = null;

    public String token = null;

	public String host = null;

    /**
     * @param host
     *      The host name of the service.
     */
    public void setHost(String host) {
        this.host = host; // for backward compatibility
        this.put("host", host);
    }

    /**
     * @param handler
     *      A URLStreamHandler to handle HTTPS requests for the service.
     */
    public void setHTTPSHandler(URLStreamHandler handler) {
    	this.put("httpsHandler", handler);
    }
   
    /**
     * @param port
     *      The port number of the service.
     */
    public void setPort(int port) {
        this.port = port; // for backward compatibility
        this.put("port", port);
    }

    /**
     * @param scheme
     *      The scheme to use for accessing the service.
     */
    public void setScheme(String scheme) {
        this.scheme = scheme; // for backward compatibility
        this.put("scheme", scheme);
    }

    /**
     * @param securityProtocol
     *      The SSL security protocol for the service.
     */
    public void setSSLSecurityProtocol(SSLSecurityProtocol securityProtocol) {
        this.put("SSLSecurityProtocol", securityProtocol);
    }

    /**
     * @param token
     *      A Splunk authentication token to use for the session.
     */
    public void setToken(String token) {
        this.token = token; // for backward compatibility
        this.put("token", token);
    }

    
}
