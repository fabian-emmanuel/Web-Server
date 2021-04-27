package core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

import static enums.ThreadColor.*;

public class ServerListenerThread extends Thread{
    public static Logger log = LoggerFactory.getLogger(ServerListenerThread.class);

    private final ServerSocket serverSocket;
    boolean verbose = true;

    public ServerListenerThread(int port) throws IOException {
        this.serverSocket = new ServerSocket(port);
    }

    @Override
    public void run() {
        try {
            while(serverSocket.isBound() && !serverSocket.isClosed()) {
                Socket clientSocket = serverSocket.accept();

                log.info(PURPLE + " *** Connection accepted *** : " + CYAN + clientSocket.getInetAddress());
                if (verbose) log.info(GREEN + "# Connection Processing Initiated! (" + new Date() + ")");

                HTTPConnectionWorkerThread workerThread = new HTTPConnectionWorkerThread(clientSocket);
                workerThread.start();
            }
        } catch (IOException e) {
            log.error("### Problem Encountered in Setting Socket! ### ", e);
        } finally {
            if (serverSocket.isClosed()) {
                try {
                    serverSocket.close();
                } catch (IOException ignored) {}
            }
        }
    }
}
