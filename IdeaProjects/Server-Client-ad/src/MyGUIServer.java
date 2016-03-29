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
    public static int ClientAusNo = 0;
    public static int ClientAsiaNo = 0;
  // Text area for entering server text
  private static JTextArea jtaServer = new JTextArea();

  // Text area for displaying client text
  private static JTextArea jtaClient = new JTextArea();

  private Scanner input;
  private PrintWriter output;//constructor to do almost all action in server

  public static Object Client_obj[];
  public static Object Client_aus[];
  public static Object Client_asia[];

  public static String rooms[] = {"australia","asia"};

  public static void main(String[] args) {
      Client_obj = new Object[100];
      String sta=null;
		String stb=null;
		new MyGUIServer();
		StartServer();
		CreateSocket();
		Client_Update(sta);
  	Server_Update(stb);
     // broadcast();
      //constructor to create GUI
  	//generate two type of area on server.one for server and other for client connection
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
  public static void StartServer()
	{
	  jtaServer.append("Opening Port...\n");
		try
		{
			serverSocket = new ServerSocket(PORT);
		}
		catch (IOException ioEx)
		{
			jtaServer.append("\nUnable to set up port!");
			System.exit(1);
		}
    }


  public static void CreateSocket()
	{
		do
		{
			try
			{

				client = serverSocket.accept();

			ClientNo++;
			jtaServer.append("Client "+ClientNo+ ": Connected.\n");
            //Client_obj[ClientNo] = client;
            Scanner input1 = new Scanner(client.getInputStream());
            PrintWriter output3 = new PrintWriter(client.getOutputStream(),true);

            for(int i=0;i<rooms.length;i++){
            output3.println("ChatRoomsIN~"+rooms[i]);
            }



            String array[]= new String[2];
            String rec = input1.nextLine();
            jtaServer.append(rec);
           System.out.println(rec);
            ClientHandler1 clnt;
                if (rec.equals("ausroomJoin-aus")){

                    Client_aus[ClientAusNo] = client;
                    clnt = new ClientHandler1(client,ClientNo,"aus");
                    clnt.start();

                }else{
                    if(rec.equals("asiaroomJoin-asia")){

                        Client_asia[ClientAsiaNo] = client;
                        clnt = new ClientHandler1(client,ClientNo,"asia");
                        clnt.start();

                    }else{

                        output3.println("Main Server=>Please join a chat room to proceed");
                        ClientNo--;
                        client.close();
                    }
                }



            }        catch (IOException e)
			{
				e.printStackTrace();
			}



        }while(true);

	}

  public static void Client_Update(String clnt)
	{
		jtaClient.append(clnt);
		jtaClient.append("\n");
	}
  public static void Server_Update(String ser)
	{
		jtaServer.append(ser);
		jtaServer.append("\n");
	}

    public static void broadcast(String received)
	{
        int count=1;
        System.out.println(ClientNo);
        while (count <= ClientNo){
            try{

                Socket cl=(Socket)Client_obj[count];
                PrintWriter output2 = new PrintWriter(cl.getOutputStream(),true);
                output2.println("Main Server=>"+received);
                 System.out.println();
            }catch(IOException ioe){
                  ioe.printStackTrace();
            }
                   count ++;
        }
    }
    public static void ausbroadcast(String received)
	{
        int count=1;
        System.out.println(ClientNo);
        while (count <= ClientAusNo){
            try{

                Socket cl=(Socket)Client_aus[count];
                PrintWriter output2 = new PrintWriter(cl.getOutputStream(),true);
                output2.println("Main Server=>"+received);
                 System.out.println();
            }catch(IOException ioe){
                  ioe.printStackTrace();
            }
                   count ++;
        }
    }

     public static void asiabroadcast(String received)
	{
        int count=1;
        System.out.println(ClientNo);
        while (count <= ClientAsiaNo){
            try{

                Socket cl=(Socket)Client_asia[count];
                PrintWriter output2 = new PrintWriter(cl.getOutputStream(),true);
                output2.println("Main Server=>"+received);
                 System.out.println();
            }catch(IOException ioe){
                  ioe.printStackTrace();
            }
                   count ++;
        }
    }
}

  class ClientHandler1 extends Thread
  {
  	private Socket client;
  	private Scanner input;
  	private int ClientCounter;
  	private PrintWriter output;
    private String room;

      public ClientHandler1(Socket socket,int Clint_no, String room)
  	{
  		client = socket;
  		ClientCounter=Clint_no;
        room = room;
          try
  		{
  			input = new Scanner(client.getInputStream());
  			//output = new PrintWriter(client.getOutputStream(),true);
  		}
  		catch(IOException ioEx)
  		{
  			ioEx.printStackTrace();
  		}

  	}

  	public void  run()
  	{
  		String received,Clint_no;
  		do
  		{
  			if(input.hasNext())
  			{
  				received = input.nextLine();
                  if (room.equals("aus")){
                MyGUIServer.ausbroadcast(received);
                  }else{
                     MyGUIServer.asiabroadcast(received);
                  }
                //output.println("Main Server=>"+received);
  				MyGUIServer.Client_Update("Message from Client" + ClientCounter + ">" + received);
  			}
  			else
  			{
  				try {
  						client.close();
  					} catch (IOException e) {

  						e.printStackTrace();
  					}
  					break;
  			}

  		}while (!received.equals("xxxTERMINATExxx"));
  		try
  		{
  			if (client!=null)
  			{
  				MyGUIServer.Server_Update("Closing connection for Client " + ClientCounter);
  				client.close();
  			}
  		}
  		catch(IOException ioEx)
  		{
  			MyGUIServer.Server_Update("Unable to disconnect!");
  		}
  	}
  }