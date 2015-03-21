package mygame;

import com.jme3.app.SimpleApplication;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.scene.CameraNode;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Box;
import com.jme3.system.AppSettings;
import input.ShipKeyBoardListener;
import java.util.Random;
import objects.MeteorFactory;
import ship.Ship;

/**
 * test
 * @author normenhansen
 */
public class Main extends SimpleApplication {
    MeteorFactory meteorFactory;
    Geometry geom;
    float count;
    Ship testShip;
    
   
    public static void main(String[] args) {
        Main app = new Main();
        app.start();
    }

    @Override
    public void simpleInitApp() {         
        this.flyCam.setEnabled(false);
        
        ShipKeyBoardListener s = new ShipKeyBoardListener(this);
        testShip = new Ship(false, 0, 0, 0, 0, this);
        s.setShip(testShip);
        
        

        
        Box b = new Box(1, 1, 1);
        geom = new Geometry("Box", b);

        Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat.setColor("Color", ColorRGBA.Blue);
        geom.setMaterial(mat);

        rootNode.attachChild(geom);
        
        meteorFactory = new MeteorFactory(this);
        meteorFactory.generateMeteors();

        
    }

    @Override
    public void simpleUpdate(float tpf) {
        this.testShip.step();

        this.cam.lookAtDirection(this.testShip.getSpeeds(), new Vector3f(0, 1, 0));
        
        this.cam.update();
        geom.setLocalRotation(Quaternion.IDENTITY);
        geom.rotate(this.cam.getRotation());
        
        //geom.rotate(this.cam.getRotation(), count/2, 0);
    }

    @Override
    public void simpleRender(RenderManager rm) {
        //TODO: add render code
    }
    
    public Vector3f getCamDir(){
        return this.cam.getDirection();
    }
    
    public Vector3f getCamLoc(){
        return this.cam.getLocation();
    }
}
