package ship;

import com.jme3.math.Ray;
import com.jme3.math.Vector3f;
import com.jme3.scene.Spatial;
import com.jme3.scene.Spatial.CullHint;
import mygame.Main;
import objects.GameObject;
/**
 * 
 * @author Destion
 */
public class Ship extends GameObject{
    
    
    private Main app;
    
    private Vector3f speeds;
    
    private float[] angles;
    
    private int id; 
    
    private final int DEFAULTFIREPOWER = 50;
    private final int DEFAULTRELOADTIME = 5000;
    private final int DEFAULTAMMO = 5;
    
    
    //Invert the4 directions the ship takes on W or S presses
    private int inverted;
    
    private int health;
    
    private final float SPEED = 0.008f;
    Weapon weapon;
    
    
    
    public Ship(int id, Vector3f position, Vector3f direction, boolean invert, Main app){
        super(id, position, direction);
        this.speeds = new Vector3f(0, 0, 0);
        if (invert) {
            this.inverted = -1;
        } else {
            this.inverted = 1;
        }
        this.angles = new float[]{0,0,0};
        this.health = 100;
        
        this.app = app;
        
        this.weapon = new Weapon(this.DEFAULTRELOADTIME, this.DEFAULTFIREPOWER, 500, this.DEFAULTAMMO );
        
    }
    
    public Ship(int id, Vector3f position, Vector3f direction, boolean invert, int seperation, int firePower, int reloadTime, int ammo, Main app){
        super(id, position, direction);
        if (invert) {
            this.inverted = -1;
        } else {
            this.inverted = 1;
        }
        this.angles = new float[]{0, 0, 0};
        this.health = 100;
        
        this.app = app;
        
        this.weapon = new Weapon(seperation, firePower, reloadTime, ammo);
    }
    
    //Getters for position and speed
    public float getX(){
        return this.position.x;
    }
    public float getY(){
        return this.position.y;
    }
    public float getZ(){
        return this.position.z;
    }
    
    public Vector3f getLoc(){
        //This is here for backwards compability
        return getPosition();
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
        
        this.position.add(this.speeds);
        //System.out.println(String.format("x: %s, y: %s, z: %s", angles[0], angles[1], angles[2]));
        //System.out.println(String.format("x: %s, y: %s, z: %s", Math.sin(this.angles[2]) * Math.cos(this.angles[1]) , Math.sin(this.angles[0]) * Math.cos(this.angles[2]), Math.sin(this.angles[1]) * Math.cos(this.angles[0])));

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
        this.position.x =x;
    }
    public void setY(float y){
        this.position.y = y;
    }
    public void setZ(float z){
        this.position.z = z;
    }
    public void setId(int id){
        this.id = id;
    }
    
    public void wPressed(float force){
        app.setNodeDir(force * inverted * 0.03f, 0, 0);
    }
    
    public void sPressed(float force){
        app.setNodeDir(force * -1 * (inverted * 0.03f), 0, 0);        
    }
    
    public void aPressed(float force) {
        app.setNodeDir(0, 0, force * -1 * 0.04f);
    }
    
    public void dPressed(float force) {
        app.setNodeDir(0, 0, force * 0.04f);
    }
     
    public void shoot(){
        int fire = 0;
        int nofire = 0;
        if(this.weapon.fire()){
            this.weapon.ammunition -= 1;
            fire += 1;
            Vector3f directionXYZ = this.direction;
            Vector3f positionXYZ = this.position;

            Ray ray = new Ray(directionXYZ, positionXYZ);

            if (this.app.meteorFactory.doesCollide(ray)){
                System.out.println("Lol");
                this.app.meteorFactory.collideObject(ray).setCullHint(CullHint.Always);
                System.out.println("Hoi");
            }
        } else {
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
        
        public Weapon(int reloadTime, int firePower, int seperation, int ammunition){
            this.seperation = seperation;
            this.firePower = firePower;
            this.reloadTime = reloadTime;
            this.ammunition = 8;
            this.reloadStart = 0;
            this.lastFire = 0;
        }

            //Use fire when firing a bullet, canFire checks reloadtime
        public boolean canFire(){
            long currentTime = System.currentTimeMillis();
            if ((currentTime - this.reloadStart) > this.reloadTime){
                
                if(this.ammunition == 0){
                    this.ammunition = 8;
                }
                
                if ((currentTime - this.lastFire) > this.seperation){
                    return true;
                }
                else {
                    return false;
                }
            }
            return false;
        }
        public boolean fire(){
            if (canFire() && this.ammunition > 0){
                this.lastFire = System.currentTimeMillis();
                return true;
            } else if (this.ammunition == 0){
                this.reloadStart = System.currentTimeMillis();
                return false;
            } else {
                return false;
            }
        }
    }
}
