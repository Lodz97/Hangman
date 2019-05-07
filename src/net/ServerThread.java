package net;

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

            String text;

            do
            {
                text = reader.readLine();
                String[] split = text.split(" ");
                if(split.length != 1)
                {
                    writer.println("Lettera non valida.");
                }
                switch (split[0])
                {
                    case "add":
                        if(split.length != 2 || Integer.parseInt(split[1]) >= server.getMan().getFloors())
                        {
                            writer.println("Server: piano sconosciuto");
                        }
                        else
                        {
                            server.getMan().increment(Integer.parseInt(split[1]));
                            writer.println("Server: " + server.getMan().getCounter());
                        }
                        break;
                    case "show":
                        writer.println("Server update: " + server.getMan().getCounter());
                        break;
                    case "trans":
                        if(split.length != 5)
                        {
                            writer.println("Server: transazione errata");
                        }
                        else
                        {
                            SimpleDateFormat parser = new SimpleDateFormat("yyyy");
                            try
                            {
                                server.getMan().recordTransaction(new Transaction(split[1], Integer.parseInt(split[2]), parser.parse(split[3]), Double.parseDouble(split[4])));
                                writer.println("Server: transazione registrata");  // Importante, se no blocca output
                            }
                            catch (ParseException ex)
                            {
                                ex.printStackTrace();
                                writer.println("Server: data transazione errata");
                            }
                        }
                        break;
                    case "showT":
                        writer.println(server.getMan().showTransactions());
                        break;
                    /*case "loop":
                        man.loop();
                        writer.println("Server: " + server.getMan().getCounter());
                        break;*/
                    case "close":
                        writer.println("Server: closing connection...");
                        break;
                    default:
                        writer.println("Server: unknown operation");
                }
            }
            while (!text.equals("close"));
            socket.close();
        }
        catch (IOException ex)
        {
            System.out.println("Server exception: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

}