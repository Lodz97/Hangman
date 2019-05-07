package net;

import java.io.*;
import java.net.*;
import java.util.ArrayList;

/**
 * This server is multi-threaded.
 *
 */

public class Server
{
    private int port;

    public Server(int port)
    {
        this.port = port;
    }

    public static void main(String[] args)
    {
        if (args.length < 1) return;
        Server server = new Server(Integer.parseInt(args[0]));
        server.startServer();
    }

    public void startServer()
    {
        try (ServerSocket serverSocket = new ServerSocket(port))
        {
            System.out.println("Server is listening on port " + port);

            while (true)
            {
                Socket socket = serverSocket.accept();
                System.out.println("New client connected");

                ServerThread t = new ServerThread(socket, this);
                t.start();
            }

        }
        catch (IOException ex)
        {
            System.out.println("Server exception: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

}