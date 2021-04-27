package driver;

import core.ServerListenerThread;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

import static enums.ThreadColor.*;

/**
 *  Driver class for Http Server
 *  @author : Fabian Emmanuel
 */
public class HttpServer {
    public static Logger log = LoggerFactory.getLogger(HttpServer.class);
    static final int defaultPort = 8080;
    static String webRoot = "src/main/resources/";

    public static void main(String[] args){
        log.info(PURPLE + "*** ...Starting HTTP Server... ***");
        log.info(GREEN + "*** ...Using Webroot : " + webRoot);

        try {
            new HttpServer().startWebServer(getValidPort(args));
        } catch (IOException e) {
            log.error("Server Encountered an Error during Startup", e);
        }
    }

    public synchronized void startWebServer(int port) throws IOException {
        ServerListenerThread serverListenerThread = new ServerListenerThread(port);
        log.info(BLUE + "*** ...Server Listening On Port : " + port);
        serverListenerThread.start();
    }

    static int getValidPort(String[] args){
        if (args.length > 0){
            int port = Integer.parseInt(args[0]);
            if (port > 0 && port < 65535) {
                return port;
            } else throw new NumberFormatException("### Port Is Invalid - Port range should be between 0 and 65535");
        } return defaultPort;
    }
}

