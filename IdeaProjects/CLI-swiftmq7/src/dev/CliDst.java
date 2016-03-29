package dev;

import javax.jms.*;
import javax.naming.*;
import java.util.*;
import java.io.*;

import com.swiftmq.admin.cli.*;
import com.swiftmq.admin.cli.event.*;

/*
 * Copyright (c) 2000 IIT GmbH, Bremen/Germany. All Rights Reserved.
 *
 * IIT grants you ("Licensee") a non-exclusive, royalty free, license to use,
 * modify and redistribute this software in source and binary code form,
 * provided that i) this copyright notice and license appear on all copies of
 * the software; and ii) Licensee does not utilize the software in a manner
 * which is disparaging to IIT.
 *
 * This software is provided "AS IS," without a warranty of any kind. ALL
 * EXPRESS OR IMPLIED CONDITIONS, REPRESENTATIONS AND WARRANTIES, INCLUDING ANY
 * IMPLIED WARRANTY OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE OR
 * NON-INFRINGEMENT, ARE HEREBY EXCLUDED. IIT AND ITS LICENSORS SHALL NOT BE
 * LIABLE FOR ANY DAMAGES SUFFERED BY LICENSEE AS A RESULT OF USING, MODIFYING
 * OR DISTRIBUTING THE SOFTWARE OR ITS DERIVATIVES. IN NO EVENT WILL IIT OR ITS
 * LICENSORS BE LIABLE FOR ANY LOST REVENUE, PROFIT OR DATA, OR FOR DIRECT,
 * INDIRECT, SPECIAL, CONSEQUENTIAL, INCIDENTAL OR PUNITIVE DAMAGES, HOWEVER
 * CAUSED AND REGARDLESS OF THE THEORY OF LIABILITY, ARISING OUT OF THE USE OF
 * OR INABILITY TO USE SOFTWARE, EVEN IF IIT HAS BEEN ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGES.
 *
 * This software is not designed or intended for use in on-line control of
 * aircraft, air traffic, aircraft navigation or aircraft communications; or in
 * the design, construction, operation or maintenance of any nuclear
 * facility. Licensee represents and warrants that it will not use or
 * redistribute the Software for such purposes.
 */

/**
 * The AdminClient is an example for using SwiftMQ's CLI Admin API. It reads
 * commands from System.in and executes them via CLI's executeCommand method.
 *
 * @author IIT GmbH, Bremen/Germany
 */

public class CliDst {
    static BufferedReader inReader = new BufferedReader(new InputStreamReader(System.in));

    public static String concat(String[] s, String delimiter) {
        StringBuffer b = new StringBuffer();
        for (int i = 0; i < s.length; i++) {
            if (i > 0)
                b.append(delimiter);
            b.append(s[i]);
        }
        return b.toString();
    }

    private static String readLine() {
        String line = null;
        boolean ok = false;
        try {
            while (!ok) {
                if (line == null)
                    line = inReader.readLine();
                ok = line != null && !line.trim().startsWith("#") || line == null;
            }
        } catch (Exception e) {
        }
        return line;
    }

    public static void main(String[] args) {
        if (args[0].equalsIgnoreCase("reboot") || args[0].equalsIgnoreCase("reboot")) {
        QueueConnection connection = null;
        CLI cli = null;
        try {
            Hashtable env = new Hashtable();
            //System.out.println("before env put");
            env.put(Context.INITIAL_CONTEXT_FACTORY, "com.swiftmq.jndi.InitialContextFactoryImpl");
            env.put(Context.PROVIDER_URL, "smqp://localhost:4001/timeout=100");
            InitialContext ctx = new InitialContext(env);
            QueueConnectionFactory connectionFactory = (QueueConnectionFactory) ctx.lookup("QueueConnectionFactory");
            connection = connectionFactory.createQueueConnection();
            ctx.close();
            cli = new CLI(connection);
        } catch (Exception e) {
            System.out.println(e);
            System.exit(-1);
        }

        cli.addRouterListener(new RouterListener() {
            public void onRouterEvent(String routerName, boolean available) {
                System.out.println("Router '" + routerName + "' is " + (available ? "AVAILABLE" : "UNAVAILABLE"));
            }
        });

        cli.waitForRouter(args[1], 1000);

        String command = null;

        System.out.println("Act Router: " + cli.getActRouter() + " Act Context: " + cli.getActContext());
        String[] availableRouters = cli.getAvailableRouters();
        if (availableRouters != null)
            System.out.println("Available Routers: " + concat(availableRouters, ", "));



            System.out.println("Command: " + args[0]);
            command = "reboot";

            try {
                cli.executeCommand("sr " + args[1]);
                System.out.println("Command " + command.trim() + " going to be executed");
                cli.executeCommand(command.trim());
                cli.close();
                connection.close();
            } catch (CLIException e) {
                System.out.println("problem occured when try to execute the command");
                //e.printStackTrace();
                System.out.println(e.getMessage());
            } catch (Exception ignored) {
            }

        } else {
            System.out.println("The command entered not used or not authorized");
            System.out.println("Usage reboot|restart");
        }
    }
}

