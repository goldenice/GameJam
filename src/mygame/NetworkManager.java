/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame;

import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import ship.Ship;

/**
 *
 * @author Rick Lubbers
 */
public class NetworkManager implements Runnable {

    private Main app;
    private Socket socket;
    public BufferedReader in;
    public BufferedWriter out;
    private String username;
    private int controlId = -1;
    
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
                        } else if (cmd.getCommandType() == Command.CommandType.CONTROL) {
                            controlId = Integer.parseInt(cmd.getArguments()[0]);
                        } else if (cmd.getCommandType() == Command.CommandType.UPDATE) {
                            updateLocation(cmd.getArguments());
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
    
    public int getId(){
        return this.controlId;
    }
    
    public void updateObject(String[] args) {
        int id = Integer.parseInt(args[0]);
        if ("Meteor".equals(args[1])) {
            float[] coords = { Float.parseFloat(args[2]), Float.parseFloat(args[3]), Float.parseFloat(args[4]) };
            float[] rotation = { Float.parseFloat(args[5]), Float.parseFloat(args[6]), Float.parseFloat(args[7]) };
            float radius = Float.parseFloat(args[8]);
            app.getMeteorFactory().addMeteor(id, coords, rotation, radius);
        } else if ("Ship".equals(args[1])) {
            if (id == controlId) {
                app.enableControls();
                app.getShip().setId(controlId);
                World.getInstance().resetId(-5, controlId);
            } else {
                Ship newship = new Ship(Integer.parseInt(args[0]), 
                        new Vector3f(Float.parseFloat(args[2]), Float.parseFloat(args[3]), Float.parseFloat(args[4])), 
                        new Vector3f(Float.parseFloat(args[5]), Float.parseFloat(args[6]), Float.parseFloat(args[7])), 
                        true, app, new Node(), "");
                World.getInstance().register(Integer.parseInt(args[0]), newship);
            }
        } else {
            // TODO: implement other objects
        }
    }
    
    public void updateLocation(String[] args) {
        System.out.println("X:" + args[3]);
        if (Integer.parseInt(args[0]) != controlId) {
            if ( World.getInstance().getEntityById(Integer.parseInt(args[0])) != null ) {
                World.getInstance().getEntityById(Integer.parseInt(args[0])).setPosition(new Vector3f(
                            Float.parseFloat(args[2]),
                            Float.parseFloat(args[3]),
                            Float.parseFloat(args[4])
                        ));
                World.getInstance().getEntityById(Integer.parseInt(args[0])).setDirection(new Vector3f(
                            Float.parseFloat(args[5]),
                            Float.parseFloat(args[6]),
                            Float.parseFloat(args[7])
                        ));
            }
        }
    }
    
    public Socket getSock(){
        return this.socket;
    }
    
}
