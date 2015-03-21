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
import com.jme3.scene.control.CameraControl.ControlDirection;
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
    CameraNode camNode;
    Node node;
   
    public static void main(String[] args) {
        Main app = new Main();
        
        app.setShowSettings(true);
        AppSettings settings = new AppSettings(true);
        settings.setTitle("Space Vector");
        
        settings.setSettingsDialogImage("Project_Assets/breen-starships.jpg");
        
        app.setDisplayStatView(false);
        app.setDisplayFps(false);
        app.setSettings(settings);
        
        
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

        this.node = new Node();
        
        rootNode.attachChild(node);
        
        this.node.attachChild(geom);
        CameraNode camNode = new CameraNode("Camnode", cam);
        
        
        
        this.node.attachChild(camNode);        
      
        
        camNode.setLocalTranslation(new Vector3f(0, 5, -5));
        
        
        camNode.lookAt(geom.getLocalTranslation(), Vector3f.UNIT_Y);
        camNode.setControlDir(ControlDirection.SpatialToCamera);
        meteorFactory = new MeteorFactory(this);
        meteorFactory.generateMeteors();

        
    }

    @Override
    public void simpleUpdate(float tpf) {
        this.testShip.step();
        
        node.move(new Vector3f(0, (float) -0.05 , (float) 0.05 ));
        
        geom.setLocalRotation(Quaternion.IDENTITY);
        //geom.rotate(this.cam.getRotation());
        
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
    
    public void setNodeDir(float x, float y, float z){
        node.rotate( x , y , z );
    }
}
