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
public class Meteor {
    private Vector3f position;
    private Sphere sphere;
    private Material material;
    private Geometry geom;
    public static MeteorFactory meteorFactory;
    
    Meteor(Vector3f pos, Sphere sphere, Material material, Node node){
        position = pos;
        this.sphere = sphere;
        this.material = material;
        geom = new Geometry("Sphere", sphere);
        geom.setMaterial(material);
        geom.move(pos.x, pos.y, pos.z);
        node.attachChild(geom);
        
    }
    
    public void setPosition(Vector3f pos){
        this.position = pos;
    }

    public Material getMaterial() {
        return this.material;
    }
    
    
}
