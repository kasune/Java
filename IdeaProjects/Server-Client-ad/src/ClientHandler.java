import java.net.Socket;
import java.util.Scanner;
import java.io.PrintWriter;
import java.io.IOException;

class ClientHandler extends Thread {
    private Socket client;
    private Scanner input;
    private int ClientCounter;
    private PrintWriter output;

    public ClientHandler(Socket socket, int Clint_no) {
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
                MyGUIServer.broadcast(received);
                //output.println("Main Server=>"+received);
                MyGUIServer.Client_Update("Message from Client" + ClientCounter + ">" + received);
            } else {
                try {
                    client.close();
                } catch (IOException e) {

                    e.printStackTrace();
                }
                break;
            }

        } while (!received.equals("xxxTERMINATExxx"));
        try {
            if (client != null) {
                MyGUIServer.Server_Update("Closing connection for Client " + ClientCounter);
                client.close();
            }
        }
        catch (IOException ioEx) {
            MyGUIServer.Server_Update("Unable to disconnect!");
        }
    }
}