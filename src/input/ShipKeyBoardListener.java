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
   private boolean rollLeft;
   private boolean rollRight;

   
   public ShipKeyBoardListener(Main app){
       
       app.getInputManager().addMapping("tiltForward", new KeyTrigger(KeyInput.KEY_W));
       app.getInputManager().addMapping("tiltBackward", new KeyTrigger(KeyInput.KEY_S));
       app.getInputManager().addMapping("rollLeft", new KeyTrigger(KeyInput.KEY_A));
       app.getInputManager().addMapping("rollRight", new KeyTrigger(KeyInput.KEY_D));
       app.getInputManager().addMapping("shoot", new KeyTrigger(KeyInput.KEY_SPACE));
       app.getInputManager().addListener(this, new String[]{"tiltForward", "tiltBackward", "rollLeft", "rollRight", "shoot"});
   }
    
    public void setShip(Ship ship){
        this.ship = ship;
    }
    public void onAction(String name, boolean isPressed, float tpf) {
        if (isPressed){
            if (name.equals("shoot")){
                this.ship.shoot();
            }
        }
        
        if (name.equals("tiltForward")){
            this.tiltForward = isPressed;
        } else if (name.equals("tiltBackward")){
            this.tiltBackward = isPressed;
        } else if (name.equals("rollLeft")){
            this.rollLeft = isPressed;
        } else if (name.equals("rollRight")){
            this.rollRight = isPressed;
        }
    }
    
    public void step(){
        if (tiltForward) this.ship.wPressed(); 
        if (tiltBackward) this.ship.sPressed();
        if (rollLeft) this.ship.aPressed();
        if (rollRight) this.ship.dPressed();
    }
    
}
