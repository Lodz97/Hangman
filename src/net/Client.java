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
    BufferedReader console;

    public Client(String hostName, int port)
    {
        this.hostName = hostName;
        this.port = port;
        console = new BufferedReader(new InputStreamReader(System.in));
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
            BackgroundUpdate up = new BackgroundUpdate(socket);
            up.start();

            OutputStream output = socket.getOutputStream();
            PrintWriter writer = new PrintWriter(output, true);
            InputStream input = socket.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(input));

            Console console = System.console();
            String line;
            String text;
            do
            {
                //while(!(line = reader.readLine()).equals("Inserisci una lettera: "))
                while((line = reader.readLine()) != null)
                {
                    if(line.equals("Inserisci una lettera: "))
                    {
                        System.out.print(line);
                    }
                    else
                    {
                        System.out.println(line);
                    }
                }
                //System.out.print("Inserisci una lettera: ");
                text = console.readLine();
                System.out.print("ss " + text);
                writer.println(text);
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

class BackgroundUpdate extends Thread
{
    private Socket socket;
    private boolean isRunning = true;

    BackgroundUpdate(Socket socket)
    {
        this.socket = socket;
    }

    @Override
    public void run()
    {

        while (isRunning)
        {
            try
            {
                InputStream input = socket.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(input));
                OutputStream output = socket.getOutputStream();
                PrintWriter writer = new PrintWriter(output, true);

                Console console = System.console();
                String text;
                text = console.readLine();
                writer.println(text);
            }
            catch (IOException ex)
            {

                System.out.println("I/O error: " + ex.getMessage());
            }
            try
            {
                Thread.sleep(1);
            }
            catch (InterruptedException ex)
            {
                ex.printStackTrace();
                System.out.println("Client closed");
            }
        }
    }

    public void stopThread()
    {
        isRunning = false;
        if (!isRunning)
        {
            interrupt();
        }
    }
}




