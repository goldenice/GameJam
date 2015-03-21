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
    
    private final int DEFAULTFIREPOWER = 50;
    private final int DEFAULTRELOADTIME = 3000;
    private final int DEFAULTAMMO = 8;
    
    
    //Invert the4 directions the ship takes on W or S presses
    private boolean inverted;
    
    private int health;
    
    private final float SPEED = 0.00 8f;
    
    
    
    public Ship(boolean invert, float xpos, float ypos, float zpos, int shipId){
        this.inverted = invert;
        this.angles = new float[]{0,0,0};
        this.x = xpos;
        this.y = ypos;
        this.z = zpos;
        this.id = shipId;
        this.health = 100;
        
        Weapon weapon = new Weapon(500, this.DEFAULTFIREPOWER, this.DEFAULTRELOADTIME, this.DEFAULTAMMO );
        
    }
    
    public Ship(boolean invert, float xpos, float ypos, float zpos, int shipId, int seperation, int firePower, int reloadTime, int ammo){
        this.inverted = invert;
        this.angles = new float[]{0, 0, 0};
        this.x = xpos;
        this.y = ypos;
        this.z = zpos;
        this.id = shipId;
        this.health = 100;
        
        Weapon weapon = new Weapon(seperation, firePower, reloadTime, ammo);
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
    
    public void step(){
        this.x += SPEED * (Math.sin(this.angles[0]) + Math.cos(this.angles[0]));
        this.y += SPEED * (Math.sin(this.angles[1]) + Math.cos(this.angles[1]));
        this.z += SPEED * (Math.sin(this.angles[2]) + Math.cos(this.angles[2]));
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
        if (this.inverted){
            this.angles[0] -= 0.1f;
        } else{
            this.angles[0] += 0.1f;
        } 
    }
    
     public void sPressed(){
        if (this.inverted){
            this.angles[0] += 0.1f;
        } else{
            this.angles[0] -= 0.1f;            
        }        
    }
     
     public void hit(int damage){
         this.health -= damage;
     }     
     
    //Weapon carries the reloadtime and damage of the weapon this ship is carrying, enabling multiple types of ship
    public class Weapon{
        
        int seperation;
        int firePower;
        int reloadTime;
        int ammunition;
        
        long lastFire;
        long reloadStart;
        boolean canFire;
        boolean reloading;
        
        public Weapon(int reloadTime, int firePower, int seperation, int ammunition){
            this.reloading = false;
            this.seperation = seperation;
            this.firePower = firePower;
            this.canFire = true;
            this.reloadTime = reloadTime;
            this.ammunition = ammunition;
        }

            //Use fire when firing a bullet, canFire checks reloadtime
        public void canFire(){
            if ((System.currentTimeMillis() - this.reloadStart) > this.reloadTime){
                this.reloadStart = 0;
                this.reloading = false;
            }  
            
            if (reloading){
                this.canFire = false;
            } else {            
                if ((System.currentTimeMillis() - this.lastFire) > this.seperation){
                    this.canFire = true;
                } else {
                    this.canFire = false;
                }
            }
        }
        public boolean fire(){
            this.canFire();
            if (canFire){
                this.lastFire = System.currentTimeMillis();
            }
            this.ammunition -= 1;
            if (this.ammunition <= 0){
                this.reloading = true;
            }
            return this.canFire;
        }
    }
}
