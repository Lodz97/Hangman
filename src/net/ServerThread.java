package net;

import console.RemoteGame;

import java.io.*;
import java.net.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * This thread is responsible to handle client connection.
 *
 */

public class ServerThread extends Thread
{
    private Socket socket;
    private Server server;
    private PrintWriter writer;

    public ServerThread(Socket socket, Server server)
    {
        this.socket = socket;
        this.server = server;
    }

    public void run()
    {
        try
        {
            InputStream input = socket.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(input));

            OutputStream output = socket.getOutputStream();
            writer = new PrintWriter(output, true);

            System.out.println("start");
            new RemoteGame(writer, reader);
            System.out.println("finish");

            socket.close();
        }
        catch (IOException ex)
        {
            System.out.println("Server exception: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

}