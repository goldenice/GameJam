package ship;

import com.jme3.light.PointLight;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Quaternion;
import com.jme3.math.Ray;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.Spatial.CullHint;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import mygame.Command;
import mygame.Main;
import mygame.StepListener;
import objects.GameObject;
/**
 * 
 * @author Destion
 */
public class Ship extends GameObject implements StepListener {
    
    private int id; 
    
    private int counter;
    
    private Vector3f speeds;
    private float[] angles;
    private int inverted;
    private int health;
    
    private Main app;
    
    private String username;

    public static Material mat;
    public static Spatial globalSpatial;
    
    public PointLight light;
    
    private Spatial spatial;
    private Node node;
    
    private static final int DEFAULTFIREPOWER = 50;
    private static final int DEFAULTRELOADTIME = 5000;
    private static final int DEFAULTAMMO = 8;
    
    private boolean dead = false;
    private long deathtime = 0;
    private static final long RESPAWN = 5000;
    
    private final float SPEED = 0.008f;
    
    Weapon weapon;
    
    
    
    public Ship(int id, Vector3f position, Vector3f direction, boolean invert, Main app, Node node, String username){
        this(id, position, direction, invert, Ship.DEFAULTRELOADTIME, Ship.DEFAULTFIREPOWER, 500, Ship.DEFAULTAMMO, app, node, username);

    }
    
    public Ship(int id, Vector3f position, Vector3f direction, boolean invert, int seperation, int firePower, int reloadTime, int ammo, Main app, Node node, String username){
        super(id, position, direction);
        this.counter = 0;
        this.speeds = new Vector3f(0, 0, 0);
        this.inverted = (invert) ? -1 : 1;
        this.angles = new float[]{0, 0, 0};
        this.health = 100;        
        this.app = app;        
        this.weapon = new Weapon(8, 4000, 1000);
        if(globalSpatial == null){
            globalSpatial = app.getAssetManager().loadModel("Models/ship/ship.j3o");
            globalSpatial.scale(5);
            globalSpatial.setMaterial(mat);
        } 
        this.spatial = globalSpatial.clone();
        node.attachChild(spatial);
        this.node = node;
        
        
    }
    
    public Weapon getWep(){
        return this.weapon;
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
    
    public void reduceHealth(int x){
        this.health -= x;
    }
    
    public Vector3f getLoc(){
        //This is here for backwards compability
        return getPosition();
    }
    
    public float[] getAngles(){
        return this.angles;
    }
    
    public int getHealth(){
        return this.health;
    }
    
    
    public float getSpeed(){
        return this.SPEED;
    }
    
    /**
     *
     * @throws IOException
     */
    public void step(){
        
        if (delta_pos != Vector3f.NAN) {
            node.move(delta_pos);
            delta_pos = Vector3f.NAN;
        }
        if (delta_dir != Vector3f.NAN) {
            app.setNodeDir(node, delta_dir.x, delta_dir.y, delta_dir.z);
            delta_dir = Vector3f.NAN;
        }
        
        if(this.app.doesCollide(this.getSpatial().getWorldBound())){
            System.out.println("Collison!");
        }
        node.move(this.app.getCamera().getDirection().normalizeLocal().mult(new Vector3f(1f, 1f, 1f))); // 0.1 = speed        
        this.setPosition(node.getLocalTranslation());
        this.direction = this.app.getCamDir();
        this.spatial.setLocalRotation(Quaternion.IDENTITY);

        if (this.id == this.app.getNet().getId()){
            counter += 1;
            if(counter == 60){
                counter = 0;
                String[] argStrings = new String[]{Float.toString(position.x), Float.toString(position.y), Float.toString(position.z), Float.toString(direction.x), Float.toString(direction.y), Float.toString(direction.z)};
                try {
                    this.app.getNet().send(new Command(Command.CommandType.MOVE, argStrings));
                } catch (IOException ex) {
                    Logger.getLogger(Ship.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        }

        if ((this.getX() > 3000 || this.getY() > 3000 || this.getZ() > 3000 || this.getX() < -3000 || this.getY() < -3000 || this.getZ() < -3000) && (this.getHealth() > 0)){
            this.reduceHealth(1);
        } else if(this.getHealth() <= 0){
            app.getGui().attachChild(app.getDeathScreen());
            this.dead = true;
            this.deathtime = System.currentTimeMillis();
            try{
                this.app.getNet().send(new Command(Command.CommandType.KILL).addArgument(Integer.toString(this.id)));
            } catch (IOException e){
                
            }
        }
        
        
        if ((System.currentTimeMillis() - this.deathtime) > RESPAWN){
            app.getGui().detachChild(app.getDeathScreen());
            this.setPosition(new Vector3f(0,0,0));
        } 

        
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
        app.setNodeDir(node, force * inverted * 0.03f, 0, 0);
    }
    
    public void sPressed(float force){
        app.setNodeDir(node, force * -1 * (inverted * 0.03f), 0, 0);        
    }
    
    public void aPressed(float force) {
        app.setNodeDir(node, 0, 0, force * -1 * 0.04f);
    }
    
    public void dPressed(float force) {
        app.setNodeDir(node, 0, 0, force * 0.04f);
    }
    
    public Spatial getSpatial(){
        return this.node;
    }
         
    public void shoot(){
        if(this.weapon.fire()){
            Vector3f directionXYZ = this.direction;
            Vector3f positionXYZ = this.position;

            Ray ray = new Ray(directionXYZ, positionXYZ);

//            if (this.app.meteorFactory.doesCollide(ray)){
//                System.out.println("Lol");
//                this.app.meteorFactory.collideObject(ray).setCullHint(CullHint.Always);
//                System.out.println("Hoi");
//            }
        } else {
        }
        
    }
     
    public void hit(int damage){
         this.health -= damage;
     }     
     
    //Weapon carries the reloadtime and damage of the weapon this ship is carrying, enabling multiple types of ship
    public class Weapon implements StepListener {
        int ammunition;
        boolean reloading;
        boolean firing;
        
        int cooldown;
        int reload;
        
        long lastFire;
        long lastReload;
        
        public Weapon(int ammunition, int reload, int cooldown) {
            this.cooldown = cooldown;
            this.reload = reload;
            this.ammunition = ammunition;
            this.reloading = false;
            this.firing = false;
        }
        
        public boolean fire(){
            long currentTime = System.currentTimeMillis();
            if (!reloading && !firing && ammunition > 0) {
                lastFire = currentTime;
                firing = true;
                --ammunition;
                return true;
            } else if (!reloading && ammunition == 0) {
                lastReload = currentTime;
                reloading = true;
            }
            return false;
        }
        
        public void step() {
            long currentTime = System.currentTimeMillis();
            if (reloading && currentTime - lastReload > reload) {
                reloading = false;
                ammunition = 8;
            }
            if (firing && currentTime - lastFire > cooldown) {
                firing = false;
            }
        }
        
        public int getAmmo(){
            return this.ammunition;
        }
    }
}
