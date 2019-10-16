package no.ntnu.datakomm;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import static com.sun.activation.registries.LogSupport.log;

public class ClientHandler extends Thread {
    private final Socket clientSocket;

    public ClientHandler(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    @Override
    public void run()
    {
        InputStreamReader reader = null;

        try
        {
            boolean alive = true;

            while (alive)
            {
                reader = new InputStreamReader(clientSocket.getInputStream());
                BufferedReader bufferedReader = new BufferedReader(reader);

                String clientInput = bufferedReader.readLine();
                log(clientInput);
                if (alive && !clientInput.equalsIgnoreCase("game over"))
                {
                    String[] parts = clientInput.split("\\+");


                    //We expect two words
                    String response = "";
                    if (parts.length == 2)
                    {
                        try
                        {
                            int result = Integer.parseInt(parts[0]) + Integer.parseInt(parts[1]);
                            response = parts[0] + " + "  + parts[1] + " = " + result;
                        }
                        catch (NumberFormatException e)
                        {
                            response = "ERROR";
                        }
                    }
                    else
                    {
                        response = "ERROR";
                    }

                    PrintWriter writer = new PrintWriter(clientSocket.getOutputStream(), true);
                    writer.println(response);
                }
                else if (clientInput.equalsIgnoreCase("game over"))
                {
                    alive = false;
                    clientSocket.close();
                    log("Socket closed.");
                }
            }


        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * Log a message to the system console.
     *
     * @param message The message to be logged (printed).
     */
    private void log(String message) {
        String threadID = "THREAD #" + Thread.currentThread().getId() + ": ";
        System.out.println(threadID + message);
    }


}