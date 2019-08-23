package br.com.dmsec.hecsplunk;


abstract class BaseService extends HttpService {
    protected BaseService() {
        super();
    }
    
    protected BaseService(String host) {
        super(host);
    }

    protected BaseService(String host, int port) {
        super(host, port);
    }

    protected BaseService(String host, int port, String scheme) {
        super(host, port, scheme);
    }
}
