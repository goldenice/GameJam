package mygame;

import com.jme3.app.SimpleApplication;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Box;
import java.util.Random;
import objects.MeteorFactory;

/**
 * test
 * @author normenhansen
 */
public class Main extends SimpleApplication {
    MeteorFactory meteorFactory;
    Geometry geom;
    float count;

    public static void main(String[] args) {
        Main app = new Main();
        app.start();
    }

    @Override
    public void simpleInitApp() {
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
        count += 0.1f;
        //geom.setLocalRotation(Quaternion.IDENTITY);
        geom.setLocalRotation(this.cam.getRotation());
        this.meteorFactory.generateMeteors();
        //geom.rotate(this.cam.getRotation(), count/2, 0);
    }

    @Override
    public void simpleRender(RenderManager rm) {
        //TODO: add render code
    }
}
