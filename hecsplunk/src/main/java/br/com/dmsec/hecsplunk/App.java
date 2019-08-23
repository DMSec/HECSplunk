package br.com.dmsec.hecsplunk;

import java.util.Date;

import com.google.gson.Gson;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        System.out.println( "Testing the HECSplunk - Example to use" );
        
        HttpService.setSslSecurityProtocol(SSLSecurityProtocol.TLSv1_2);
		ServiceArgs loginArgs = new ServiceArgs();
        loginArgs.setHost("localhost");
        loginArgs.setPort(8088);
        loginArgs.setToken("eeb59b3a-9d64-4844-8d46-7c97bd56cbf8");
       
        Service service = Service.connect(loginArgs);
        
        Date date = new Date();
        long epochTime = date.getTime();
        
        Args eventArgs2 = new Args();
        eventArgs2.put("A", "A");
        eventArgs2.put("B", "B");
        
        Gson aux = new Gson();
        
        Args eventArgs = new Args();
        
        eventArgs.put("time", epochTime);
        eventArgs.put("sourcetype", "_json");
        eventArgs.put("event",aux.toJson(eventArgs2));
        
        // Submit an event over HTTP
        Receiver myIndex = new Receiver(service);
        
        Gson dados = new Gson();
        System.out.println(dados.toJson(eventArgs));
        
        myIndex.submit(eventArgs, dados.toJson(eventArgs));
    }
}
