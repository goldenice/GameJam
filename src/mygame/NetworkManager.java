/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

/**
 *
 * @author Rick Lubbers
 */
public class NetworkManager implements Runnable {

    private Main app;
    private Socket socket;
    private BufferedReader in;
    private BufferedWriter out;
    private String username;
    
    public NetworkManager(Main app, Socket socket, String username) {
        this.username = username;
        try {
            this.app = app;
            this.socket = socket;
            this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            send(new Command(Command.CommandType.CONNECT).addArgument(username));
        } catch (Exception e) {
            System.err.println("CHAOS AND DESTRUCTION WHILE USING SOCKET!");
        }
    }
    
    public void run() {
        try {
            while (true) {
                String input = null;
                if ((input = in.readLine()) != null) {
                    
                    Command cmd = Command.parseCommand(input);
                    if (cmd != null) {
                        if (cmd.getCommandType() == Command.CommandType.OBJECT) {
                            String[] args = cmd.getArguments();
                            updateObject(args);
                        } else {
                            // TODO: implement other commands
                        }
                    }
                    input = null;
                }
            }
        } catch (Exception e) {
            System.err.println("Something went terribly wrong with the socket");
            e.printStackTrace();
        }
    }
    
    public void send(Command cmd) throws IOException {
        out.write(cmd.toString());
        out.newLine();
        out.flush();
    }
    
    public void updateObject(String[] args) {
        if ("Meteor".equals(args[1])) {
            int id = Integer.parseInt(args[0]);
            float[] coords = { Float.parseFloat(args[2]), Float.parseFloat(args[3]), Float.parseFloat(args[4]) };
            float[] rotation = { Float.parseFloat(args[5]), Float.parseFloat(args[6]), Float.parseFloat(args[7]) };
            float radius = Float.parseFloat(args[8]);
            app.getMeteorFactory().addMeteor(id, coords, rotation, radius);
        } else {
            // TODO: implement other objects
        }
    }
    
}
