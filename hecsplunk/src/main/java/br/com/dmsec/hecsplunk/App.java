package br.com.dmsec.hecsplunk;

import java.util.ArrayList;

import java.util.List;

import com.google.gson.Gson;

import br.com.dmsec.hecsplunk.model.DataStructure;
import br.com.dmsec.hecsplunk.model.Event;

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
        
        
        //Teste final
        
        
        
        DataStructure data1 = new DataStructure();
        data1.setCod("COD01");
        data1.setDescription("ABCD");
        
        DataStructure data2 = new DataStructure();
        data2.setCod("COD02");
        data2.setDescription("JKLM");
        
        
        
        Event ev = new Event("HECSplunk", "INFO",data1);
        Event ev2 = new Event("HECSplunk", "ERROR",data1);
        
        Gson gson = new Gson();
        
        Args arg = new Args();
        arg.put("event", gson.toJson(ev));
        // Submit an event over HTTP
        Receiver myIndex = new Receiver(service);
               
        //myIndex.submit(eventArgs, dados.toJson(eventArgs));
        
        //myIndex.submit(gson.toJson(ev));
        myIndex.submit(gson.toJson(arg));
        
        
        Args arg2 = new Args();
        arg2.put("event", gson.toJson(ev2));
        myIndex.submit(gson.toJson(arg2));
    }
}
