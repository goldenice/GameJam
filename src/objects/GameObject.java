/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package objects;

import com.jme3.math.Vector3f;

/**
 *
 * @author Gerryflap
 */
public class GameObject {
    protected int id;
    protected Vector3f position;
    protected Vector3f direction;
    
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
    
    public int getId(){
        return this.id;
    }
    
    public void setPosition(Vector3f position){
        this.position = position;
    }
}
