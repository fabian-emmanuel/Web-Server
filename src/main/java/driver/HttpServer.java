package driver;

import core.ServerListenerThread;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;


/**
 *  Driver class for Http Server
 *  @author : Fabian Emmanuel
 */
public class HttpServer {
    public static Logger log = LoggerFactory.getLogger(HttpServer.class);

    public static void main(String[] args){
        int port = 8080;
        String webRoot = "src/main/resources/";

        log.info("*** ...Starting HTTP Server... ***");

        log.info("*** ...Using Port : " + port);
        log.info("*** ...Using Webroot : " + webRoot);

        try {
            ServerListenerThread serverListenerThread = new ServerListenerThread(port);
            serverListenerThread.start();
        } catch (IOException e){
            e.printStackTrace();
        }
    }
}

