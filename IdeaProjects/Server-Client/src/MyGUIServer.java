// MyGUIServer.java

import java.io.*;
import java.net.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;

public class MyGUIServer extends JFrame {
    //serverSocket for accept new user or client connection
    private static ServerSocket serverSocket;
    //server should listen on this port
    private static final int PORT = 1234;

    private static Socket client;
    //client no will display on server screen
    public static int ClientNo = 0;
    public static int ClientAus = 0;
    public static int ClientAsia = 0;

    static String tokens[][] = new String[10][2];

    // Text area for entering server text
    private static JTextArea jtaServer = new JTextArea();

    // Text area for displaying client text
    private static JTextArea jtaClient = new JTextArea();

    private Scanner input;
    private PrintWriter output;//constructor to do almost all action in server

    public static Object Client_obj[];
    public static Object Client_aus[];
    public static Object Client_asia[];


    public static void main(String[] args) {
        Client_obj = new Object[100];
        Client_aus = new Object[100];

        Client_asia = new Object[100];
        String sta = null;
        String stb = null;
        new MyGUIServer();
        StartServer();
        CreateSocket();
        Client_Update(sta);
        Server_Update(stb);
        // broadcast();
        //constructor to create GUI
        //generate two type of area on server.one for server and other for client connection
    }

    public void tokenManager() {
        tokens[0][1] = "kasun12345";
        tokens[1][1] = "kasun98765";
    }

    public MyGUIServer() {
        // Place text area on the frame
        getContentPane().setLayout(new GridLayout(2, 1));
        JScrollPane jScrollPane1 = new JScrollPane(jtaServer);
        JScrollPane jScrollPane2 = new JScrollPane(jtaClient);
        jScrollPane1.setBorder(new TitledBorder("Server"));
        jScrollPane2.setBorder(new TitledBorder("Client"));
        getContentPane().add(jScrollPane1, BorderLayout.CENTER);
        getContentPane().add(jScrollPane2, BorderLayout.CENTER);

        jtaServer.setWrapStyleWord(true);
        jtaServer.setLineWrap(true);
        jtaClient.setWrapStyleWord(true);
        jtaClient.setLineWrap(true);
        jtaClient.setEditable(false);
        jtaServer.setEditable(true);

        setTitle("Main Server");
        setSize(500, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true); // It is necessary to show the frame here!
    }

    public static void StartServer() {
        jtaServer.append("Opening Port...\n");
        try {
            serverSocket = new ServerSocket(PORT);
        }
        catch (IOException ioEx) {
            jtaServer.append("\nUnable to set up port!");
            System.exit(1);
        }
    }


    public static void CreateSocket() {
        do {
            try {

                client = serverSocket.accept();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
            ClientNo++;
            jtaServer.append("Client " + ClientNo + ": Connected.\n");
            Client_obj[ClientNo] = client;
            ClientHandler1 clnt;
            clnt = new ClientHandler1(client, ClientNo);
            clnt.start();

        } while (true);

    }

    public static void Client_Update(String clnt) {
        jtaClient.append(clnt);
        jtaClient.append("\n");
    }

    public static void Server_Update(String ser) {
        jtaServer.append(ser);
        jtaServer.append("\n");
    }

    public static void broadcast(String received) {
        int count = 1;
        System.out.println(ClientNo);
        while (count <= ClientNo) {
            try {

                Socket cl = (Socket) Client_obj[count];
                PrintWriter output2 = new PrintWriter(cl.getOutputStream(), true);
                output2.println("Main Server=>" + received);
                System.out.println(received + "--" + Client_obj[count]);
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
            count++;
        }
    }

    public static void ausBroadcast(String received) {
        int count = 0;
        System.out.println(Client_aus.length);
        while (count < Client_aus.length) {
            if (Client_aus[count] != null) {
                try {
                    System.out.println("gng to broadcast");
                    Socket cl = (Socket) Client_aus[count];
                    PrintWriter output2 = new PrintWriter(cl.getOutputStream(), true);
                    output2.println("Main Server=>" + received);
                    System.out.println(received + "--" + Client_aus[count]);
                } catch (IOException ioe) {
                    System.out.println(ioe.toString());
                } catch (ArrayIndexOutOfBoundsException aiob) {
                    System.out.println("issue1");
                } catch (NullPointerException npe) {
                    System.out.println("issue2");
                }
            }
            count++;
        }
    }

    public static void asiaBroadcast(String received) {
        int count = 0;
        //System.out.println(Client_asia.length);
        System.out.println("Broadcasting asia");
        while (count < Client_asia.length) {
            if (Client_asia[count] != null) {
                try {

                    Socket cl = (Socket) Client_asia[count];
                    PrintWriter output2 = new PrintWriter(cl.getOutputStream(), true);
                    output2.println("Main Server=>" + received);
                    System.out.println(received + "--" + Client_asia[count]);
                } catch (IOException ioe) {
                    System.out.println(ioe.toString());
                } catch (ArrayIndexOutOfBoundsException aiob) {
                    System.out.println("ArrayIndexOutOfBounds");
                } catch (NullPointerException npe) {
                    System.out.println("NullPointer Exception");
                }
            }
            count++;
        }
    }

}

class ClientHandler1 extends Thread {
    private Socket client;
    private Scanner input;
    private int ClientCounter;
    private PrintWriter output;
    private int clientNo;
    private String room;
    private String name;
    private int roomNo;

    public ClientHandler1(Socket socket, int Clint_no) {
        client = socket;
        ClientCounter = Clint_no;
        try {
            input = new Scanner(client.getInputStream());
            //output = new PrintWriter(client.getOutputStream(),true);
        }
        catch (IOException ioEx) {
            ioEx.printStackTrace();
        }

    }

    public void run() {
        String received, Clint_no;
        do {
            if (input.hasNext()) {

                received = input.nextLine();
                System.out.println(received);

                if (clientNo == 0) {
                    String firstMessage[] = new String[2];
                    firstMessage = received.split(":");

                    if (firstMessage[1].equals("asia")) {
                        MyGUIServer.Client_asia[MyGUIServer.ClientAsia] = client;
                        roomNo = MyGUIServer.ClientAsia;
                        MyGUIServer.ClientAsia++;
                        clientNo++;
                        room = "asia";
                        name = firstMessage[0];
                        MyGUIServer.Client_Update("Message from Client " + firstMessage[0] + ">" + received);
                        MyGUIServer.asiaBroadcast(firstMessage[0] + " joined asia");
                        //System.out.println("broadcast to asia");

                    } else {

                        if (firstMessage[1].equals("aus")) {
                            MyGUIServer.Client_aus[MyGUIServer.ClientAus] = client;
                            roomNo = MyGUIServer.ClientAus;
                            MyGUIServer.ClientAus++;
                            clientNo++;
                            room = "aus";
                            name = firstMessage[0];
                            MyGUIServer.Client_Update("Message from Client " + firstMessage[0] + ">" + received);
                            MyGUIServer.ausBroadcast(firstMessage[0] + " joined aus");
                            //System.out.println("broadcast to aus");

                        }
                    }
                } else {

                    System.out.println(name + "-" + received);
                    if (room.equals("aus")) {
                        //System.out.println("message to aus");
                        System.out.println(MyGUIServer.ClientAus);
                        MyGUIServer.ausBroadcast(name + "-" + received);
                    } else {
                        //System.out.println("message to asia");
                        System.out.println(MyGUIServer.ClientAsia);
                        MyGUIServer.asiaBroadcast(name + "-" + received);
                    }
                    //MyGUIServer.broadcast(received);
                    clientNo++;
                    //output.println("Main Server=>"+received);
                    MyGUIServer.Client_Update("Message from Client " + name + ">" + received);
                }
            } else {
                try {
                    client.close();
                } catch (IOException e) {

                    e.printStackTrace();
                }
                break;
            }

        }

        while (!received.equals("TERMINATE"));
        try

        {
            if (client != null) {
                MyGUIServer.Server_Update("Closing connection for Client " + name);
                client.close();
                if (room.equals("asia")) {
                    MyGUIServer.Client_asia[roomNo] = null;
                } else {
                    MyGUIServer.Client_aus[roomNo] = null;
                }
            } else {
                System.out.println("client adapter is NULL" + name);
            }
        }

        catch (Exception ex)

        {
            System.out.println(ex.toString());
            MyGUIServer.Server_Update("Unable to disconnect!");
        }
    }
}