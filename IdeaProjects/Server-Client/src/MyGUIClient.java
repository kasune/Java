// MyGUIClient.java

import java.io.*;
import java.net.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.text.Highlighter;
import javax.swing.text.DefaultHighlighter;
import javax.swing.border.*;

public class MyGUIClient extends JFrame {
    // Text area for entering server text
    private static JTextArea jtaServer = new JTextArea();

    // Text area for displaying client text
    private static JTextArea jtaClient = new JTextArea();

    private static JTextArea jtaRooms = new JTextArea();

    private PrintWriter output;

    private static InetAddress HostAddress;

    private static Socket MainServer;

    private static Socket BackupServer;

    private static final int MAIN_PORT = 1234;

    private static final int BACKUP_PORT = 1235;

    private static Scanner network_In;

    private static PrintWriter network_Out;

    private static boolean Main_Open_Port = false;

    private static boolean Backup_Open_Port = false;

    private static Highlighter hilit;
    private static Highlighter.HighlightPainter painter;

    private static boolean read_enable = false;
    private static String serverToken = "kasun12345";

    private static String myToken = "kasun98765";
    private static String userName;
    private static String groupName;
    private static int InCounter = 0;
    private static String chatRooms[] = {"asia", "australia"};

    public static void main(String[] args) {
        new MyGUIClient();
        getMessage();
    }

    public MyGUIClient() {
        // Place text area on the frame
        getContentPane().setLayout(new GridLayout(3, 1));
        JScrollPane jScrollPane1 = new JScrollPane(jtaServer);
        JScrollPane jScrollPane2 = new JScrollPane(jtaClient);
        JScrollPane jScrollPane3 = new JScrollPane(jtaRooms);

        jtaServer.setForeground(Color.green);
        jtaRooms.setForeground(Color.ORANGE);

        jScrollPane1.setBorder(new TitledBorder("Server"));
        jScrollPane2.setBorder(new TitledBorder("Client"));
        jScrollPane3.setBorder(new TitledBorder("Rooms"));

        getContentPane().add(jScrollPane2, BorderLayout.CENTER);
        getContentPane().add(jScrollPane1, BorderLayout.CENTER);
        getContentPane().add(jScrollPane3, BorderLayout.CENTER);

        jtaServer.setWrapStyleWord(true);
        jtaServer.setLineWrap(true);
        jtaClient.setWrapStyleWord(true);
        jtaClient.setLineWrap(true);
        jtaServer.setEditable(false);

        setTitle("talkMe");
        setSize(800, 500);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        GetClientHost();
        //CreateClientSocket();
        jtaClient.setText("");
        //jtaServer.setText("----------------First Message to Join a Room - name:group----------------");
        getChatRooms();
        jtaClient.addKeyListener(new KeyAdapter()

        {

            public void keyPressed(KeyEvent e) {
                try {
                    if (e.getKeyCode() == 10) {

                        String inText = jtaClient.getText();
                        //jtaServer.setText("----------------First Message to Join a Room - name:group----------------");
                        jtaServer.setForeground(Color.green);
                        System.out.println(InCounter);
                        //jtaServer.setText("");
                        if (InCounter == 0) {
                            String firstMsg[] = new String[2];
                            firstMsg = inText.split(":");
                            userName = firstMsg[0].trim();
                            groupName = firstMsg[1].trim();
                            System.out.println("username -" + userName);
                            System.out.println("groupname -" + groupName);


                            if (groupName.equalsIgnoreCase("asia") || groupName.equalsIgnoreCase("aus")) {

                                System.out.println("Second Message format ok");


                                //array=null;
                                CreateClientSocket();

                                if (Main_Open_Port == true || Backup_Open_Port == true) {
                                    //sendMessages(inText.concat(":").concat(serverToken));
                                    sendMessages(inText);
                                }
                                jtaClient.setText("");
                                InCounter++;

                            } else {
                                InCounter = 0;
                                jtaClient.setText("First Message should be in USER:GROUP format");

                            }

                        } else {
                            if (Main_Open_Port == true || Backup_Open_Port == true) {
                                sendMessages(inText);
                                jtaClient.setText("");
                            }

                            jtaRooms.setText("");
                            /*jtaRooms.append("Join/Change Chat room");
                            jtaRooms.append("\n");
                            jtaRooms.append("example- kasun:america");
                            jtaRooms.append("\n");*/
                            //jtaClient.setText("Type in ('UserName-TERMINATE' *****to Close Connection******:");
                            //jtaClient.setText("");

                            getChatRooms();
                            InCounter++;
                        }
                    }
                } catch (Exception ex) {
                    System.out.println("entered values are wrong" + ex.toString());
                    jtaClient.setText("");
                    InCounter = 0;
                    jtaServer.setText("WARNING---------------First Message to Join a Room - name:group----------------");
                    Font font = new Font("Verdana", Font.BOLD, 12);
                    jtaServer.setFont(font);
                    jtaServer.setForeground(Color.RED);
                }
            }

        });
    }

    public static void getChatRooms() {
        jtaRooms.append("-------Rooms-------");
        for (int i = 0; i < chatRooms.length; i++) {
            jtaRooms.append("\n");
            jtaRooms.append(chatRooms[i]);

        }
        jtaRooms.append("\n");
        jtaRooms.append("-------------------");
        jtaRooms.append("\n");
        jtaRooms.append("Close the connection - TERMINATE in Client window");

    }

    public void GetClientHost() {
        try {
            HostAddress = InetAddress.getLocalHost();
        }
        catch (UnknownHostException uhEx) {
            System.out.println("\n *~*~*~ Host is Uncreachable *~*~*~\n");
            System.exit(1);
        }
    }

    public void CreateClientSocket() {
        try {
            try {
                jtaServer.append("*~*~*~ Try to Connect to Main Server *~*~*~");
                MainServer = new Socket(HostAddress, MAIN_PORT);
                Main_Open_Port = true;
                jtaServer.append("\n *~*~*~ Connection Establish to Main Server *~*~*~\n");
                network_In = new Scanner(MainServer.getInputStream());
                network_Out = new PrintWriter(MainServer.getOutputStream(), true);
            }
            catch (IOException ioEx) {
                jtaServer.append("*~*~*~ Main Server Down *~*~*~ \n *~*~*~ Establish Connection To Backup Server *~*~*~");
                BackupServer = new Socket(HostAddress, BACKUP_PORT);
                Backup_Open_Port = true;
                Main_Open_Port = false;
                jtaServer.append("\n*~*~*~ Connection Establish to BackUp Server *~*~*~\n");
                network_In = new Scanner(BackupServer.getInputStream());
                network_Out = new PrintWriter(BackupServer.getOutputStream(), true);
            }
        }
        catch (IOException e) {
            jtaServer.append("\n *~*~*~ BackUp Server is Down As Well *~*~*~");
            Main_Open_Port = false;
            Backup_Open_Port = false;
        }
    }

    public void CreateClientSocketAgain() {
        try {
            MainServer = new Socket(HostAddress, MAIN_PORT);
            Main_Open_Port = true;
            jtaServer.append("\n*~*~*~ Connected To Main Server *~*~*~\n");
            network_In = new Scanner(MainServer.getInputStream());
            network_Out = new PrintWriter(MainServer.getOutputStream(), true);
            BackupServer.close();
            Backup_Open_Port = false;
        }
        catch (IOException ioEx) {
            jtaServer.append("\nMain server still down...");
        }

    }

    public void sendMessages(String send) {
        String message = send.trim();
        network_Out.println(message);
        System.out.println("Message sent " + message);
    }

    public static void getMessage() {
        //ReceiveHandler rh= new ReceiveHandler(network_In, network_Out, jtaServer );
        //rh.start();
        System.out.println("receiver started");

        while (true) {
            try {

                //jserver.append("hmmmm--sss");
                String response = network_In.nextLine();
                if (response.equals(myToken.concat("YourToken"))) {

                }
                jtaServer.append("Response From " + response);
                jtaServer.append("\n");
                System.out.println("response" + response);
                //}

                Thread.sleep(1000);

            } catch (InterruptedException ie) {
                System.out.println(ie.toString());
            }
            catch (NullPointerException npe) {
                //npe.printStackTrace();
            }
            catch (Exception ex) {
                ex.toString();
            }

        }
    }
}

class ReceiveHandler extends Thread {
    Scanner net_in;
    PrintWriter net_out;
    JTextArea jserver;

    ReceiveHandler(Scanner in, PrintWriter out, JTextArea js) {
        net_in = in;
        net_out = out;
        jserver = js;
    }

    public void run() {
        while (true) {
            try {
                //jserver.append("hmmmm--sss");
                String response = net_in.nextLine();
                //jserver.append( "Response From "+response);
                //jserver.append("\n");
                System.out.println("response" + response);
                //}

                Thread.sleep(10000);

            } catch (InterruptedException ie) {
                System.out.println(ie.toString());
            }
            catch (NullPointerException npe) {
                //System.out.println(npe.toString());
            }
        }
    }
}
