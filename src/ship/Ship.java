package ship;

import com.jme3.math.Vector3f;

/**
 * 
 * @author Destion
 */
public class Ship {
    
    private float x;
    private float y;
    private float z;
    
    private float[] angles;
    
    private int id;   
    
    
    //Invert the4 directions the ship takes on W or S presses
    private boolean inverted;
    
    private int health;
    
    private final float SPEED = 80;
    
    public Ship(float xpos, float ypos, float zpos, int shipId, int reload, int firePower){
        this.angles = new float[]{0, 0, 0};
        this.x = xpos;
        this.y = ypos;
        this.z = zpos;
        this.id = shipId;
        this.health = 100;
        
        Weapon weapon = new Weapon(reload, firePower);
    }
    
    //Getters for position and speed
    public float getX(){
        return this.x;
    }
    public float getY(){
        return this.y;
    }
    public float getZ(){
        return this.z;
    }
    
    public Vector3f getLoc(){
        Vector3f loc = new Vector3f();
        loc.x = this.x;
        loc.y = this.y;
        loc.z = this.z;        
        return loc;
    }
    
    public float[] getAngles(){
        return this.angles;
    }
    
    
    public float getSpeed(){
        return this.SPEED;
    }
    
    //Getter for identification number
    public int getId(){
        return this.id;
    }
    
    
    //Setters for position, identification number and speedc
    public void setX(float x){
        this.x =x;
    }
    public void setY(float y){
        this.y = y;
    }
    public void setZ(float z){
        this.z = z;
    }
    public void setId(int id){
        this.id = id;
    }
    
    public void wPressed(){
        this.angles[0] += 0.1f;
    }
    
     public void sPressed(){
        this.angles[0] -= 0.1f;
    }
    
    //Weapon carries the 
    public class Weapon{
        
        int reloadTime;
        int firePower;
        
        long lastFire;
        boolean canFire;
        
        public Weapon(int reloadTime, int firePower){
            this.reloadTime = reloadTime;
            this.firePower = firePower;
            this.canFire = true;
        }

            //Use fire when firing a bullet, canFire checks reloadtime
        public void canFire(){
            if ((System.currentTimeMillis() - this.lastFire) > this.reloadTime){
                this.canFire = true;
            } else {
                this.canFire = false;
            }
        }
        public boolean fire(){
            this.canFire();
            if (canFire){
                this.lastFire = System.currentTimeMillis();
            }
            return this.canFire;
        }
    }
}
