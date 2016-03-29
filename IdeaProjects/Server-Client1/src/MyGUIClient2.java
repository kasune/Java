// MyGUIClient.java
import java.io.*;
import java.net.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.border.*;

public class MyGUIClient2 extends JFrame {
  // Text area for entering server text
  private JTextArea jtaServer = new JTextArea();

  // Text area for displaying client text
  private JTextArea jtaClient = new JTextArea();

	  private PrintWriter output;

	  private static InetAddress HostAddress;

	  private static Socket MainServer;

	  private static Socket BackupServer;

	  private static final int MAIN_PORT= 1234;

	  private static final int BACKUP_PORT= 1235;

	  private static Scanner network_In;

	  private static PrintWriter network_Out;

	  private static boolean Main_Open_Port=false;

	  private static boolean Backup_Open_Port=false;

  public static void main(String[] args) {
    new MyGUIClient();

  }

  public MyGUIClient2() {
    // Place text area on the frame
    getContentPane().setLayout(new GridLayout(2, 1));
    JScrollPane jScrollPane1 = new JScrollPane(jtaServer);
    JScrollPane jScrollPane2 = new JScrollPane(jtaClient);
    jScrollPane1.setBorder(new TitledBorder("Server"));
    jScrollPane2.setBorder(new TitledBorder("Client"));
    getContentPane().add(jScrollPane2, BorderLayout.CENTER);
    getContentPane().add(jScrollPane1, BorderLayout.CENTER);

    jtaServer.setWrapStyleWord(true);
    jtaServer.setLineWrap(true);
    jtaClient.setWrapStyleWord(true);
    jtaClient.setLineWrap(true);
    jtaServer.setEditable(false);

    setTitle("MyGUIClient");
    setSize(500, 300);
    setResizable(false);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setVisible(true);
    GetClientHost();
	CreateClientSocket();
	jtaClient.setText("Type in ('xxxTERMINATExxx'  *~*~*~ To Close Connection *~*~*~):");
	jtaClient.addKeyListener(new KeyAdapter()

	{

		public void keyPressed(KeyEvent e)
		{

			if (e.getKeyCode() == 10)
			{

				if(Main_Open_Port==false && Backup_Open_Port==false)
				{
						jtaServer.append("\n *~*~*~ Reconnecting *~*~*~\n");

						CreateClientSocket();
						if(Main_Open_Port==true ||Backup_Open_Port==true)
						{
								sendMessages();
						}
						jtaClient.setText("Type in ('xxxTERMINATExxx' *~*~*~ To Close Connection *~*~*~):");

				}
				else
				{
					if(Main_Open_Port==false && Backup_Open_Port==true)
					{
						CreateClientSocketAgain();
						if(Main_Open_Port==true)
						{
							sendMessages();
						}
						else
						{
							sendMessages();
						}
					}
					else
					{
						sendMessages();
					}
				}
	        }

		}});
}

  public void GetClientHost()
	{
		try
		{
			HostAddress = InetAddress.getLocalHost();
		}
		catch(UnknownHostException uhEx)
		{
			System.out.println("\n *~*~*~ Host is Uncreachable *~*~*~\n");
			System.exit(1);
		}
	}
  public void CreateClientSocket()
	{
		try
			{
			try
				{
				jtaServer.append("*~*~*~ Try to Connect to Main Server *~*~*~");
				MainServer = new Socket(HostAddress,MAIN_PORT);
				Main_Open_Port=true;
				jtaServer.append("\n *~*~*~ Connection Establish to Main Server *~*~*~\n");
				network_In =new Scanner(MainServer.getInputStream());
				network_Out =new PrintWriter(MainServer.getOutputStream(),true);
				}
				catch(IOException ioEx)
				{
					jtaServer.append("*~*~*~ Main Server Down *~*~*~ \n *~*~*~ Establish Connection To Backup Server *~*~*~");
					BackupServer = new Socket(HostAddress,BACKUP_PORT);
					Backup_Open_Port=true;
					Main_Open_Port=false;
					jtaServer.append("\n*~*~*~ Connection Establish to BackUp Server *~*~*~\n");
					network_In =new Scanner(BackupServer.getInputStream());
					network_Out =new PrintWriter(BackupServer.getOutputStream(),true);
				}
			}
			catch (IOException e)
			{
				jtaServer.append("\n *~*~*~ BackUp Server is Down As Well *~*~*~");
				Main_Open_Port=false;
				Backup_Open_Port=false;
			}
	}
  public void CreateClientSocketAgain()
	{
		try
			{
					MainServer = new Socket(HostAddress,MAIN_PORT);
					Main_Open_Port=true;
					jtaServer.append("\n*~*~*~ Connected To Main Server *~*~*~\n");
					network_In =new Scanner(MainServer.getInputStream());
					network_Out =new PrintWriter(MainServer.getOutputStream(),true);
					BackupServer.close();
					Backup_Open_Port=false;
			}
				catch(IOException ioEx)
				{
					jtaServer.append("\nMain server still down...");
				}

	}

  public void sendMessages()
	{
		String message=" ", response,clstr;
		char s1;
		String[] array1;
		array1 =new String[2];

		try
		{
				clstr = jtaClient.getText();
				array1= clstr.split(":");
				message=array1[1];
				message=message.trim();
				network_Out.println(message);
				array1=null;
				if(network_In.hasNext())
				{
					response = network_In.nextLine();
					//jtaServer.append( "Response From "+response);
					//jtaServer.append("\n");
					jtaClient.setText("Type in ('xxxTERMINATExxx' *****to Close Connection******:");

					if(response.equals("Backup Server>xxxTERMINATExxx"))
					{
						BackupServer.close();
						Backup_Open_Port=false;
						jtaClient.setText("\nClosing connection for Backup Server.....");
						jtaClient.setEditable(false);
						}
						else
						{
							if(response.equals("Main Server>xxxTERMINATExxx"))
							{
								MainServer.close();
								Main_Open_Port=false;
								jtaClient.setText("\nClosing connection for Main Server.....");
								jtaClient.setEditable(false);
							}
						}
				}
					else
					{
						jtaServer.append("*~*~*~Reconnecting*~*~*~");
						CreateClientSocket();
						if(Main_Open_Port==true ||Backup_Open_Port==true)
						{
							sendMessages();
						}
						jtaClient.setText("Type in ('xxxTERMINATExxx' *~*~*~ to Close Connection *~*~*~ :");

					}
			}
			catch(Exception ioEx)
			{
				jtaServer.append("*~*~*~Unable to Disconnect*~*~*~");
				System.exit(1);
			}

		}

    public void getMessage(){
        while(true){
               try{
           if(network_In.hasNext())
				{
					String response = network_In.nextLine();
					jtaServer.append( "Response From "+response);
					jtaServer.append("\n");
                }

            Thread.sleep(1000);

            }catch(InterruptedException ie){
                   System.out.println(ie.toString());
               }
        }
    }
}
