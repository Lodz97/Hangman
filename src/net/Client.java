package net;

import java.net.*;
import java.io.*;

/**
 * This program demonstrates a simple TCP/IP socket client that reads input
 * from the user.
 *
 */

public class Client
{
    private String hostName;
    private int port;

    public Client(String hostName, int port)
    {
        this.hostName = hostName;
        this.port = port;
    }

    public static void main(String[] args)
    {
        if (args.length < 2) return;

        Client client = new Client(args[0], Integer.parseInt(args[1]));
        client.startClient();

    }

    public void startClient()
    {
        try (Socket socket = new Socket(hostName, port))
        {
            OutputStream output = socket.getOutputStream();
            PrintWriter writer = new PrintWriter(output, true);

            Console console = System.console();
            String text;
            String txt;

            do
            {
                InputStream input = socket.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(input));
                txt = console.readLine("");
                writer.println(txt);
                text = reader.readLine();
                System.out.println(text);

            } while (!text.equals("close"));

        }
        catch (UnknownHostException ex)
        {

            System.out.println("Server not found: " + ex.getMessage());

        }
        catch (IOException ex)
        {

            System.out.println("I/O error: " + ex.getMessage());
        }
        catch (Exception ex)
        {

            System.out.println(ex.getMessage());
        }
    }
}



