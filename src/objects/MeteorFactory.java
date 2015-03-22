/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package objects;

import com.jme3.collision.Collidable;
import com.jme3.collision.CollisionResult;
import com.jme3.collision.CollisionResults;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Sphere;
import java.util.ArrayList;
import java.util.Random;
import mygame.Main;
import mygame.World;

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
    private int count = 0;
    
    
    public MeteorFactory(Main app){
        this.app = app;
        meteors = new ArrayList<Meteor>();
        node = new Node();
    }
    
    public void generateMeteors(){
        app.getRootNode().attachChild(node);
        return;
    }
    
    public synchronized void addMeteor(int id, float[] pos, float[] rot, float radius) {
        System.out.println("Creating meteor " + id);
        Vector3f posvect = new Vector3f(pos[0], pos[1], pos[2]);
        Vector3f dirvect = new Vector3f(rot[0], rot[1], rot[2]);
        Sphere sphere = new Sphere(5, 5, radius);
        
        
        mat = new Material(app.getAssetManager(), "Common/MatDefs/Light/Lighting.j3md");
        
        Random random = new Random();
        int text = random.nextInt(3);
        if(text == 0){
            mat.setTexture("DiffuseMap", this.app.getAssetManager().loadTexture("Textures/meteor.png"));
        } else if(text == 1){
            mat.setTexture("DiffuseMap", this.app.getAssetManager().loadTexture("Textures/meteor2.png"));
        } else if(text == 2){
            mat.setTexture("DiffuseMap", this.app.getAssetManager().loadTexture("Textures/meteor3.png"));
        }
        
        Meteor meteor = new Meteor(id, posvect, dirvect, sphere, mat);
        meteors.add(meteor);
        World.getInstance().register(id, meteor);
    }
    
    public ArrayList<Meteor> getMeteors(){
        return this.meteors;
    }
    
    public boolean doesCollide(Collidable object){
        CollisionResults colRes = new CollisionResults();
        this.node.collideWith(object, colRes);
        return colRes.size() != 0;        
    }
   
    public Geometry collideObject(Collidable object){
        CollisionResults colRes = new CollisionResults();
        this.node.collideWith(object, colRes);
        for (CollisionResult res : colRes){
            if (res.getContactPoint() != null){
                return res.getGeometry();
            }
        }
        return null;
    }

    public Node getNode(){
        return node;
    }
    
    public void shuffleColors(){
        for (Meteor meteor: meteors){
            meteor.getMaterial().setColor("Color", ColorRGBA.randomColor());
        }
    }
    
}
