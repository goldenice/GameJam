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
import javax.vecmath.Vector3d;

/**
 *
 * @author Gerryflap
 */
public class Meteor extends GameObject{
    private Sphere sphere;
    private Material material;
    private Geometry geom;
    public static MeteorFactory meteorFactory;
    
    Meteor(int id, Vector3f position, Vector3f direction, Sphere sphere, Material material, Node node){
        super(id, position, direction);
        this.sphere = sphere;
        this.material = material;
        geom = new Geometry("Sphere", sphere);
        geom.setMaterial(material);
        geom.move(position.x, position.y, position.z);
        geom.rotate(direction.x, direction.y, direction.z);
        node.attachChild(geom);
        
    }
    
    public void setPosition(Vector3f pos){
        this.position = pos;
    }

    public Material getMaterial() {
        return this.material;
    }
    
    
}
