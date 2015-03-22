/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package objects;

import com.jme3.material.Material;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Sphere;
import java.util.ArrayList;
import java.util.Random;
import mygame.Main;
import static objects.CometFactory.CREATION_SCALE;
import static objects.CometFactory.METEOR_NUM;

/**
 *
 * @author Destion
 */
public class Comet extends GameObject {
    private Sphere sphere;
    private Material material;
    private Geometry geom;
    public static MeteorFactory meteorFactory;
    private Main app;
    
    public int cometNum;
    public static final float CCREATION_SCALE = 3000;
    
    public ArrayList<Node> nodes = new ArrayList<Node>();
    
    public Comet(int cometsNum, Main app, int id, Vector3f position, Vector3f direction, Sphere sphere, Material material, Node node){
        super(id, position, direction);
        this.cometNum = cometsNum;
        this.sphere = sphere;
        this.material = material;
        geom = new Geometry("Sphere", sphere);
        geom.setMaterial(material);
        geom.move(position.x, position.y, position.z);
        geom.rotate(direction.x, direction.y, direction.z);
        node.attachChild(geom);
        this.app = app;
    }
    
    public void generateComets(){
        Vector3f pos = new Vector3f(0f, 0f, 0f);
        Sphere sphere;        
        
        Material mat = new Material(app.getAssetManager(), "Common/MatDefs/Misc/Unshaded.j3md");
        mat.setTexture("ColorMap", this.app.getAssetManager().loadTexture("./Project_Assets/comet.png"));
        
        Random random = new Random();
        for(int i = 0; i < cometNum; i++){
            pos.x = random.nextFloat()*2*CCREATION_SCALE-CCREATION_SCALE;
            pos.y = random.nextFloat()*2*CCREATION_SCALE-CCREATION_SCALE;
            pos.z = random.nextFloat()*2*CCREATION_SCALE-CCREATION_SCALE;
            sphere = new Sphere(5, 5, random.nextFloat()*30 + 6f);
            Node node = new Node();
            node.setLocalTranslation(pos.x, pos.y, pos.z);
        }
        for (Node node: this.nodes){
            this.app.getRootNode().attachChild(node);
        }
    }
    
    public void setPosition(Vector3f pos){
        this.position = pos;
    }

    public Material getMaterial() {
        return this.material;
    }
}
