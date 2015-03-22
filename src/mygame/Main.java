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
import com.jme3.scene.Spatial;
import com.jme3.scene.control.CameraControl.ControlDirection;
import com.jme3.scene.plugins.OBJLoader;
import com.jme3.scene.shape.Box;
import com.jme3.scene.shape.Sphere;
import com.jme3.system.AppSettings;
import com.jme3.texture.Texture;
import input.ShipKeyBoardListener;
import java.util.Random;
import objects.MeteorFactory;
import ship.Ship;

/**
 * test
 * @author normenhansen
 */
public class Main extends SimpleApplication {
    public MeteorFactory meteorFactory;
    Geometry geom;
    float count;
    Ship testShip;
    CameraNode camNode;
    Node node;
    ShipKeyBoardListener skbListener;
    Spatial player;
            
    public static void main(String[] args) {
        Main app = new Main();  
        
        app.setShowSettings(true);
        AppSettings settings = new AppSettings(true);
        settings.setTitle("Space Vector");
        
        settings.setSettingsDialogImage("Project_Assets/breen-starships.jpg");
        
        app.setDisplayStatView(false);
        app.setDisplayFps(false);
        app.setSettings(settings);
        
        settings.setFullscreen(true);
        
        settings.setVSync(true);
        settings.setResolution(1024, 720);
        
      
        
        app.start();
    }

    @Override
    public void simpleInitApp() {         
        this.flyCam.setEnabled(false);
        
        Sphere planetSphere= new Sphere(20,20,800);
        Geometry planetGeom = new Geometry("planet", planetSphere);
        Material planetMat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        planetMat.setColor("Color", ColorRGBA.Blue);
        planetGeom.setMaterial(planetMat);
        planetGeom.setCullHint(Spatial.CullHint.Never);
        rootNode.attachChild(planetGeom);
        planetGeom.move(new Vector3f(4000, -600, 0));
        
        
        skbListener = new ShipKeyBoardListener(this);
        testShip = new Ship(0, new Vector3f(0,0,0), new Vector3f(0,0,0), false, this);
        skbListener.setShip(testShip);
        
        
        this.player = assetManager.loadModel("Project_Assets/ship.obj");
        
        Box b = new Box(1, 1, 1);
        geom = new Geometry("Box", b);

        Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat.setColor("Color", ColorRGBA.LightGray);
        geom.setMaterial(mat);
        
        player.setMaterial(mat);
        
        this.node = new Node();
        
        rootNode.attachChild(node);
        
        this.node.attachChild(player);
        cam.setFrustumFar(3000);
        CameraNode camNode = new CameraNode("Camnode", cam);
        
        this.node.attachChild(camNode);        
      
        
        camNode.setLocalTranslation(new Vector3f(0, 10, -50));
        
        
        //camNode.lookAt(geom.getLocalTranslation(), Vector3f.UNIT_Y);
        camNode.setControlDir(ControlDirection.SpatialToCamera);
        meteorFactory = new MeteorFactory(this);
        meteorFactory.generateMeteors();

        
    }

    @Override
    public void simpleUpdate(float tpf) {
        skbListener.step();
        this.testShip.step();
        if(meteorFactory.doesCollide(this.player.getWorldBound())){
            System.out.println("Collision");
        }
        node.move(this.cam.getDirection().normalizeLocal().mult(new Vector3f(10f, 10f, 10f))); // 0.1 = speed
        
        //geom.setLocalRotation(Quaternion.IDENTITY);
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
