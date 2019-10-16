package no.ntnu.datakomm;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * A Simple TCP client, used as a warm-up exercise for assignment A4.
 */
public class SimpleTcpServer {

    // TCP port
    private static final int PORT = 1301;

    public static void main(String[] args) {
        SimpleTcpServer server = new SimpleTcpServer();
        log("Simple TCP server starting");
        server.run();
        log("ERROR: the server should never go out of the run() method! After handling one client");
    }

    public void run() {

        ServerSocket welcomeSocket = null;
        try
        {
            welcomeSocket = new ServerSocket(PORT);
            System.out.println("Server started on port " + PORT);

            boolean mustRun = true;

            while (mustRun)
            {
                //Accept the next client connection
                Socket clientSocket = welcomeSocket.accept();
                ClientHandler clientHandler = new ClientHandler(clientSocket);
                clientHandler.start();
            }
             //CLose the listening socket, allow other services to listen on this TCP port
            welcomeSocket.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        // TODO - implement the logic of the server, according to the protocol.
        // Take a look at the tutorial to understand the basic blocks: creating a listening socket,
        // accepting the next client connection, sending and receiving messages and closing the connection
    }

    /**
     * Log a message to the system console.
     *
     * @param message The message to be logged (printed).
     */
    private static void log(String message) {
        String threadId = "THREAD #" + Thread.currentThread().getId() + ": ";
        System.out.println(threadId + message);
    }
}
