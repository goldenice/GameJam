package mygame;

import com.jme3.app.SimpleApplication;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Quaternion;
import com.jme3.renderer.RenderManager;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Box;
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
        testShip = new Ship(0, 0, 0, 0, 1000, 1);
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
        //geom.setLocalRotation(Quaternion.IDENTITY);
        geom.setLocalRotation(this.cam.getRotation());
        this.cam.getRotation().fromAngles(testShip.getAngles());
        this.cam.getLocation().set(testShip.getLoc());
        this.cam.update();
        
        //geom.rotate(this.cam.getRotation(), count/2, 0);
    }

    @Override
    public void simpleRender(RenderManager rm) {
        //TODO: add render code
    }
}
