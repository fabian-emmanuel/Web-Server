package core;

import enums.ContentType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Scanner;

import static enums.ThreadColor.*;


public class HTTPConnectionWorkerThread extends Thread {
    public static Logger log = LoggerFactory.getLogger(HTTPConnectionWorkerThread.class);
    private final Socket clientSocket;

    String _htmlFilePath = "src/main/resources/myHtml.html";
    String _jsonFilePath = "src/main/resources/myJson.json";
    String _404FilePath = "src/main/resources/404.html";

    public HTTPConnectionWorkerThread(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
        try {
            InputStream inputStream = clientSocket.getInputStream();
             OutputStream outputStream = clientSocket.getOutputStream();

            Scanner scanner = new Scanner(inputStream);
            String[] line = scanner.nextLine().split(" ");

            System.out.println(Arrays.toString(line));

            String _index;
            String _contentType;
            Path path;

            if (line[1].equals("/")){
                path = Paths.get(_htmlFilePath);
                _index = Files.readString(path);
                _contentType = ContentType.HTML.toString();
            } else if (line[1].equals("/json")){
                path = Paths.get(_jsonFilePath);
                _index = Files.readString(path);
                _contentType = ContentType.JSON.toString();
            } else {
                path = Paths.get(_404FilePath);
                _index = Files.readString(path);
                _contentType = ContentType.HTML.toString();
            }

            String response =
                    "HTTP/1.1 200 OK" + "\n\r" +
                            _contentType + "\n\r" +
                            "Content-length:" + _index.length() + "\n\r" + "\n\r" +
                            _index +
                            "\n\r" + "\n\r";

            outputStream.write(response.getBytes());

            log.info(GREEN + "*** Connection Processing Done!");
        } catch (IOException e) {
            log.error("### Problem Encountered in Communication! ### ", e);
        } finally {
            try {
                if (clientSocket != null) clientSocket.close();
            } catch (IOException ignored) {
            }
        }
    }
}
