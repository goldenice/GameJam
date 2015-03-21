/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package input;

import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import mygame.Main;
import ship.Ship;

/**
 *
 * @author Gerryflap
 */
public class ShipKeyBoardListener implements ActionListener{
   private Ship ship;
   private boolean tiltForward;
   private boolean tiltBackward;

   
   public ShipKeyBoardListener(Main app){
       
       app.getInputManager().addMapping("tiltForward", new KeyTrigger(KeyInput.KEY_W));
       app.getInputManager().addMapping("tiltBackward", new KeyTrigger(KeyInput.KEY_S));
<<<<<<< HEAD
       app.getInputManager().addMapping("tiltLeft", new KeyTrigger(KeyInput.KEY_A));
       app.getInputManager().addMapping("tiltRight", new KeyTrigger(KeyInput.KEY_D));
       app.getInputManager().addMapping("shoot", new KeyTrigger(KeyInput.KEY_SPACE));
       app.getInputManager().addListener(this, new String[]{"tiltForward", "tiltBackward", "shoot", "tiltLeft", "tiltRight"});
=======
       app.getInputManager().addMapping("rollLeft", new KeyTrigger(KeyInput.KEY_A));
       app.getInputManager().addMapping("rollRight", new KeyTrigger(KeyInput.KEY_D));
       app.getInputManager().addMapping("shoot", new KeyTrigger(KeyInput.KEY_SPACE));
       app.getInputManager().addListener(this, new String[]{"tiltForward", "tiltBackward", "rollLeft", "rollRight", "shoot"});
>>>>>>> 3d9745d1c076433b11d2033af45c1c94c7a155c0
   }
    
    public void setShip(Ship ship){
        this.ship = ship;
    }
    public void onAction(String name, boolean isPressed, float tpf) {
        if (isPressed){
<<<<<<< HEAD
            if (name.equals("shoot")){
=======
            if(name.equals("tiltForward")){
                this.ship.wPressed(); 
            } else if (name.equals("tiltBackward")){
                this.ship.sPressed();
            } else if (name.equals("rollLeft")) {
                this.ship.aPressed();
            } else if (name.equals("rollRight")) {
                this.ship.dPressed();
            } else if (name.equals("shoot")){
>>>>>>> 3d9745d1c076433b11d2033af45c1c94c7a155c0
                this.ship.shoot();
            }
        }
        
        if (name.equals("tiltForward")){
                this.tiltForward = isPressed;
            } else if (name.equals("tiltBackward")){
                this.tiltBackward = isPressed;
            }
    }
    
    public void step(){
        if (tiltForward){            
            this.ship.wPressed(); 
        }
        if (tiltBackward) {
            this.ship.sPressed();
        }
    }
    
}
