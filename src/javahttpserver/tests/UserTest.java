package javahttpserver.tests;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import javahttpserver.User;

import java.net.ServerSocket;


public class UserTest {

    User user;

    private static final int TEST_PORT = 8000;
    private static final String TEST_HOST = "localhost";

    @Before
    public void initialize() throws Exception{
        user = new User(TEST_HOST, TEST_PORT);
    }

    @Test
    public void testBeginConnnection() throws Exception{

        ServerSocket testServerSocket;
        testServerSocket = new ServerSocket(TEST_PORT);
        testServerSocket.setSoTimeout(100);
        assertTrue("User begins a connection", user.beginConnection());
        testServerSocket.close();
    }

    @After

    public void end() throws Exception {
        user.endConnection();
    }

    @Test
    public void testEndConnection() throws Exception {
        ServerSocket testServerSocket;
        testServerSocket = new ServerSocket(TEST_PORT);
        testServerSocket.setSoTimeout(100);
        user.beginConnection();
        assertTrue("User ends a connection", user.endConnection());
        testServerSocket.close();
    }


}