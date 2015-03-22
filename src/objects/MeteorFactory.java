/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package objects;

import com.jme3.collision.Collidable;
import com.jme3.collision.CollisionResults;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Sphere;
import java.util.ArrayList;
import java.util.Random;
import mygame.Main;

/**
 *
 * @author Gerryflap
 */
public class MeteorFactory {
    private ArrayList<Meteor> meteors;
    private ArrayList<Meteor> queue = new ArrayList<Meteor>();
    private Node node;
    private Main app;
    public static final int METEOR_NUM = 5000;
    public static final float CREATION_SCALE = 3000;
    public Material mat;
    
    
    public MeteorFactory(Main app){
        this.app = app;
        meteors = new ArrayList<Meteor>();
        node = new Node();
        
        mat = new Material(app.getAssetManager(), "Common/MatDefs/Misc/Unshaded.j3md");
        mat.setTexture("ColorMap", this.app.getAssetManager().loadTexture("./Project_Assets/meteor.png"));
    }
    
    public void generateMeteors(){
        app.getRootNode().attachChild(node);
        return;
        
        /*Vector3f pos = new Vector3f(0f, 0f, 0f);
        Sphere sphere;
        
        Random random = new Random();
        for(int i = 0; i < METEOR_NUM; i++){
            pos.x = random.nextFloat()*2*CREATION_SCALE-CREATION_SCALE;
            pos.y = random.nextFloat()*2*CREATION_SCALE-CREATION_SCALE;
            pos.z = random.nextFloat()*2*CREATION_SCALE-CREATION_SCALE;
            sphere = new Sphere(5, 5, random.nextFloat()*30 + 6f);
            meteors.add(new Meteor(0, pos, new Vector3f(random.nextFloat(), random.nextFloat(), random.nextFloat()), sphere, mat, node));
        }
        */
    }
    
    public synchronized void addMeteor(int id, float[] pos, float[] rot, float radius) {
        System.out.println("Creating meteor " + id);
        Vector3f posvect = new Vector3f(pos[0], pos[1], pos[2]);
        Vector3f dirvect = new Vector3f(rot[0], rot[1], rot[2]);
        Sphere sphere = new Sphere(5, 5, radius);
        Meteor meteor = new Meteor(id, posvect, dirvect, sphere, mat, node);
        meteors.add(meteor);
        queue.add(meteor);
    }
    
    public ArrayList<Meteor> getMeteors(){
        return this.meteors;
    }
    
    public boolean doesCollide(Collidable object){
        CollisionResults colRes = new CollisionResults();
        this.node.collideWith(object, colRes);
        return colRes.size() != 0;        
    }
    
    public synchronized void processQueue() {
        for (Meteor meteor : queue) {
            meteor.attach();
        }
        queue = new ArrayList<Meteor>();
    }
    
    public Node getNode(){
        return node;
    }
    
    public void shuffleColors(){
        for(Meteor meteor: meteors){
            meteor.getMaterial().setColor("Color", ColorRGBA.randomColor());
        }
    }
    
}
