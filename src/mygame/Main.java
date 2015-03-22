package mygame;

import com.jme3.app.SimpleApplication;
import com.jme3.font.BitmapText;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.post.FilterPostProcessor;
import com.jme3.post.filters.FogFilter;
import com.jme3.renderer.RenderManager;
import com.jme3.scene.CameraNode;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.CameraControl.ControlDirection;
import com.jme3.scene.shape.Box;
import com.jme3.scene.shape.Sphere;
import com.jme3.system.AppSettings;
import input.ShipKeyBoardListener;
import java.io.IOException;
import java.net.Socket;
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
    ShipKeyBoardListener skbListener;  
    Ship testShip2;
    private Socket sock;
    private Thread thread;
    private NetworkManager net;
    
    private BitmapText hudText;
    
    public static Main app;
    public static final String HOST = "localhost";
    public static final int PORT = 6969;
    public static final String[] USERNAMES = { "Button", "EBOLA.EXE", "OneManCheeseBurgerApocalypse", "BlackMesa", "Microsoft_GLa-DoS", "ZeroCool", "CrashOverride", "AcidBurn", "CerealKiller", "ThaPhreak" };
    
    public static void main(String[] args) {
        app = new Main();  
        
        app.setShowSettings(true);
        AppSettings settings = new AppSettings(true);
        

        settings.setSettingsDialogImage("Textures/dialog.jpg");
        settings.setTitle("Space Vector");
        
        settings.setFullscreen(true);
        settings.setVSync(true);
        settings.setResolution(1024, 720);
        
        app.setDisplayStatView(false);
        app.setDisplayFps(true);
        app.setSettings(settings);
        
        app.start();
    }

    @Override
    public void simpleInitApp() {     
        meteorFactory = new MeteorFactory(this);
        try {
            this.sock = new Socket(HOST, PORT);
            this.net = new NetworkManager(this, sock, generateUsername());
            this.thread = new Thread(net);
            thread.start();
        } catch (IOException e) {
            System.err.println("SOCKET FUCKUP EXCEPTION");
            e.printStackTrace();
            System.exit(1);
        }
        
        this.flyCam.setEnabled(false);
        
        FilterPostProcessor fpp = new FilterPostProcessor(assetManager);
        FogFilter fog = new FogFilter();
        fog.setFogColor(ColorRGBA.BlackNoAlpha);
        fog.setFogDistance(3000);
        fog.setFogDensity(1.5f);
        fpp.addFilter(fog);
        viewPort.addProcessor(fpp);
        
        Sphere planetSphere= new Sphere(32, 32, 800);
        Geometry planetGeom = new Geometry("planet", planetSphere);
        Material planetMat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        planetMat.setColor("Color", ColorRGBA.Blue);
        planetGeom.setMaterial(planetMat);
        planetGeom.setCullHint(Spatial.CullHint.Never);
        rootNode.attachChild(planetGeom);
        planetGeom.move(new Vector3f(4000, -600, 0));
        

        skbListener = new ShipKeyBoardListener(this);     
        
        Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat.setTexture("ColorMap", assetManager.loadTexture("Models/ship/ship.png"));
        Ship.mat = mat;
        
        testShip2 = new Ship(1, new Vector3f(0,0,0), new Vector3f(0,0,0), false, this, rootNode, "Henk");
        
        Node node = new Node();

        
        rootNode.attachChild(node);
        
        testShip = new Ship(0, new Vector3f(0,0,0), new Vector3f(0,0,0), false, this, node, "Piet");
        skbListener.setShip(testShip);

        cam.setFrustumFar(5000);
        CameraNode camNode = new CameraNode("Camnode", cam);
        
        node.attachChild(camNode);        
      
        
        camNode.setLocalTranslation(new Vector3f(0, 50, -250));
        
        
        camNode.lookAt(testShip.getLoc(), Vector3f.UNIT_Y);
        camNode.setControlDir(ControlDirection.SpatialToCamera);
        meteorFactory = new MeteorFactory(this);
        meteorFactory.generateMeteors();

        
        this.hudText = new BitmapText(guiFont, false);
        hudText.setSize(guiFont.getCharSet().getRenderedSize()); 
        hudText.setColor(ColorRGBA.Blue);
        hudText.setText("Ammunition: " + testShip.getWep().getAmmo() + "/8");
        hudText.setLocalTranslation(300, hudText.getLineHeight(), 0);
        guiNode.attachChild(hudText);
    }

    @Override
    public void simpleUpdate(float tpf) {
        skbListener.step();
        World.getInstance().processQueue();
        meteorFactory.processQueue();
        this.testShip.step();

        this.testShip.getWep().reload();
        this.hudText.setText("Ammunition: " + testShip.getWep().getAmmo() + "/8");
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
    
    public void setNodeDir(Node node, float x, float y, float z){
        node.rotate( x , y , z );
    }
    
    public static String generateUsername() {
        Random rand = new Random();
        return USERNAMES[rand.nextInt(USERNAMES.length)] + (new Integer(rand.nextInt(9999)).toString());
    }

    public synchronized MeteorFactory getMeteorFactory() {
        return meteorFactory;
    }

    public synchronized void attachToRootNode(Spatial thing) {
        rootNode.attachChild(thing);
    }

    public NetworkManager getNet() {
        return net;
    }
    
    public Socket getSock() {
        return sock;
    }
    
    
}
