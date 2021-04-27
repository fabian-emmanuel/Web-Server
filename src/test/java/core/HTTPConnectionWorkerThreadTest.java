package core;

import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

public class HTTPConnectionWorkerThreadTest {
    // Testing request and response from server
    static HTTPConnectionWorkerThread workerThread;
    static ServerListenerThread listenerThread;
    static ServerSocket serverSocket;
    static Socket clientSocket;
    @BeforeAll
    static void setUp(){
        try {
            listenerThread = new ServerListenerThread(8080);
            listenerThread.start();
            serverSocket = new ServerSocket(8080);
            clientSocket = serverSocket.accept();
            workerThread = new HTTPConnectionWorkerThread(clientSocket);
            workerThread.start();

        } catch (IOException ignored) {}
    }
    @Test
    public void indexTest(){
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/"))
                .build();

        try {
            HttpResponse<String> httpResponse = client.send(request, HttpResponse.BodyHandlers.ofString());
            String expected  = Files.readString(Paths.get("src/main/resources/myHtml.html"))
                    .replaceAll("\\s", "");

            assertAll(
                    () -> assertEquals(expected, httpResponse.body().replaceAll("\\s", "")),
                    () -> assertEquals(200, httpResponse.statusCode())
            );
        } catch (IOException | InterruptedException ignored) {}
    }
    @Test
    public void jsonTest(){
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/json"))
                .build();

        try {
            HttpResponse<String> httpResponse = client.send(request, HttpResponse.BodyHandlers.ofString());
            String expected = Files.readString(Paths.get("src/main/resources/myJson.json"))
                    .replaceAll("\\s", "");

            assertAll(
                    () -> assertEquals(expected, httpResponse.body().replaceAll("\\s", "")),
                    () -> assertEquals(200, httpResponse.statusCode())
            );
        } catch (IOException | InterruptedException ignored) {}
    }
    @Test
        public void pageNotFoundTest(){
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:8080/notfound"))
                    .build();

            try {
                HttpResponse<String> httpResponse = client.send(request, HttpResponse.BodyHandlers.ofString());
                String expected = Files.readString(Paths.get("src/main/resources/404.html"))
                        .replaceAll("\\s", "");

                assertAll(
                        () -> assertEquals(expected, httpResponse.body().replaceAll("\\s", "")),
                        () -> assertEquals(404, httpResponse.statusCode())
                );
            } catch (IOException | InterruptedException ignored) {}
        }

}