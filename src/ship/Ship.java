package ship;

import com.jme3.math.Ray;
import com.jme3.math.Vector3f;
import mygame.Main;
/**
 * 
 * @author Destion
 */
public class Ship {
    
    private float x;
    private float y;
    private float z;
    
    private Main app;
    
    private Vector3f speeds;
    
    private float[] angles;
    
    private int id; 
    
    private final int DEFAULTFIREPOWER = 50;
    private final int DEFAULTRELOADTIME = 3000;
    private final int DEFAULTAMMO = 8;
    
    
    //Invert the4 directions the ship takes on W or S presses
    private boolean inverted;
    
    private int health;
    
    private final float SPEED = 0.008f;
    
    
    
    public Ship(boolean invert, float xpos, float ypos, float zpos, int shipId, Main app){
        this.speeds = new Vector3f(0, 0, 0);
        this.inverted = invert;
        this.angles = new float[]{0,0,0};
        this.x = xpos;
        this.y = ypos;
        this.z = zpos;
        this.id = shipId;
        this.health = 100;
        
        this.app = app;
        
        Weapon weapon = new Weapon(500, this.DEFAULTFIREPOWER, this.DEFAULTRELOADTIME, this.DEFAULTAMMO );
        
    }
    
    public Ship(boolean invert, float xpos, float ypos, float zpos, int shipId, int seperation, int firePower, int reloadTime, int ammo, Main app){
        this.inverted = invert;
        this.angles = new float[]{0, 0, 0};
        this.x = xpos;
        this.y = ypos;
        this.z = zpos;
        this.id = shipId;
        this.health = 100;
        
        this.app = app;
        
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
        this.speeds.x = (float) (SPEED * (Math.cos(this.angles[2]) * -Math.sin(this.angles[1])));
        this.speeds.y = (float) (SPEED * (Math.cos(this.angles[0]) * Math.sin(this.angles[2])));
        this.speeds.z = (float) (SPEED * (Math.cos(this.angles[1]) * Math.sin(this.angles[0])));
        
        this.x += this.speeds.x;
        this.y += this.speeds.y;
        this.z += this.speeds.z;
        System.out.println(String.format("x: %s, y: %s, z: %s", angles[0], angles[1], angles[2]));
        System.out.println(String.format("x: %s, y: %s, z: %s", Math.sin(this.angles[2]) * Math.cos(this.angles[1]) , Math.sin(this.angles[0]) * Math.cos(this.angles[2]), Math.sin(this.angles[1]) * Math.cos(this.angles[0])));
    }
    
    //Getter for identification number
    public int getId(){
        return this.id;
    }
    
    public Vector3f getSpeeds(){
        return this.speeds;
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
        Vector3f camDirXYZ = this.app.getCamDir();
        if (this.inverted){
            app.setCamDir(camDirXYZ.x, camDirXYZ.y + 0.1f, camDirXYZ.z);
        } else{
            
            app.setCamDir(camDirXYZ.x, camDirXYZ.y - 0.1f, camDirXYZ.z);
        }  
    }
    
    public void sPressed(){
        Vector3f camDirXYZ = this.app.getCamDir();
        if (this.inverted){
            app.setCamDir(camDirXYZ.x, (float) (camDirXYZ.y - 0.1), camDirXYZ.z);
        } else{
            
            app.setCamDir(camDirXYZ.x, (float) (camDirXYZ.y + 0.1), camDirXYZ.z);
        }        
    }
     
    public int shoot(){
        Vector3f directionXYZ = app.getCamDir();
        Vector3f positionXYZ = app.getCamLoc();
        
        Ray ray = new Ray(directionXYZ, positionXYZ);
        
        //TODO fill in the node for enemy ships
        
        
        
        return 0;
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
        boolean reloading;
        
        public Weapon(int reloadTime, int firePower, int seperation, int ammunition){
            this.reloading = false;
            this.seperation = seperation;
            this.firePower = firePower;
            this.reloadTime = reloadTime;
            this.ammunition = ammunition;
        }

            //Use fire when firing a bullet, canFire checks reloadtime
        public boolean canFire(){
            if ((System.currentTimeMillis() - this.reloadStart) > this.reloadTime){
                this.reloadStart = 0;
                return false;
            }  
            
            if (reloading){
                return false;
            } else {            
                if ((System.currentTimeMillis() - this.lastFire) > this.seperation){
                    return true;
                } else {
                    return false;
                }
            }
        }
        public boolean fire(){
            if (canFire()){
                this.lastFire = System.currentTimeMillis();
            }
            this.ammunition -= 1;
            if (this.ammunition <= 0){
                this.reloading = true;
            }
            return this.canFire();
        }
    }
}
