package core;

import enums.ContentType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;


public class HTTPConnectionWorkerThread extends Thread {
    public static Logger log = LoggerFactory.getLogger(HTTPConnectionWorkerThread.class);
    private final Socket socket;

    String htmlFilePath = "src/main/resources/index.html";
    String jsonFilePath = "src/main/resources/index.json";
    String _404FilePath = "src/main/resources/404.html";

    public HTTPConnectionWorkerThread(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try (InputStream inputStream = socket.getInputStream();
             OutputStream outputStream = socket.getOutputStream()) {

            String index;
            String ext;
            Scanner scanner = new Scanner(inputStream);


            String[] line = scanner.nextLine().split(" ");

            Path path;
            if (line[0].equals("GET") && line[1].equals("/index.html") || line[1].equals("/")){
                path = Paths.get(htmlFilePath);
                index = Files.readString(path);
                ext = ContentType.HTML.toString();
            } else if (line[0].equals("GET") && line[1].equals("/index.json")  || line[1].equals("/json")){
                path = Paths.get(jsonFilePath);
                index = Files.readString(path);
                ext = ContentType.JSON.toString();
            } else {
                path = Paths.get(_404FilePath);
                index = Files.readString(path);
                ext = ContentType.HTML.toString();
            }

            final String CRLF = "\n\r"; // can be represented with 13 and 10 respectively in bytes
            // Status Line : HTTP_VERSION RESPONSE_CODE RESPONSE_MESSAGE
            String response =
                    "HTTP/1.1 200 OK" + CRLF +
                            ext + CRLF +
                            "Content-length:" + index.length() + CRLF + CRLF +
                            index +
                            CRLF + CRLF;
            outputStream.write(response.getBytes());

            log.info("*** Connection Processing Done!");
        } catch (IOException e) {
            log.error("### Problem Encountered in Communication! ### ", e);
        } finally {
            try {
                if (socket != null) socket.close();
            } catch (IOException ignored) {
            }
        }
    }
}
