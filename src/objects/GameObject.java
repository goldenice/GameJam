/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package objects;

import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Spatial;

/**
 *
 * @author Gerryflap
 */
public abstract class GameObject {
    protected int id;
    protected Vector3f position;
    protected Vector3f direction;
    protected Vector3f delta_pos = Vector3f.NAN;
    protected Vector3f delta_dir = Vector3f.NAN;
    
    public GameObject(int id){
        this(id, new Vector3f(0, 0, 0), new Vector3f(0, 0, 0));
    }
    
    public GameObject( int id, Vector3f position, Vector3f direction){
        this.id = id;
        this.position = position;
        this.direction = direction;
    }
    
    public Vector3f getPosition(){
        return this.position;
    }
    
    public Vector3f getDirection(){
        return this.direction;
    }
    
    public void setDirection(Vector3f dir) {
        this.delta_dir = dir;
        this.direction = dir;
    }
    
    public int getId(){
        return this.id;
    }
    
    public void setPosition(Vector3f position){
        this.delta_pos = position.subtract(position);
        this.position = position;
    }
    
    public abstract Spatial getSpatial();
    
}
