package driver;


import org.junit.Test;

import static org.junit.Assert.*;

public class HttpServerTest {

    @Test
    public void inputCorrectPort() {
        String[] args = new String[1];
        args[0] = "9876";
        assertEquals(8080, HttpServer.getValidPort(args));
    }

    @Test
    public void emptyPort() {
        String[] args = new String[0];
        assertEquals(8080, HttpServer.getValidPort(args));
    }

    @Test(expected = NumberFormatException.class)
    public void wrongPortEntered() {
        String[] args = new String[1];
        args[0] = "grks";
        assertEquals(8080, HttpServer.getValidPort(args));
    }

    @Test
    public void wrongPort() {
        String[] args = new String[1];
        args[0] = "grks";
        assertEquals(8080, HttpServer.getValidPort(args));
    }
}