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
   
   private int tiltForwardForce = 0;
   private int tiltBackwardForce = 0;
   private int rollLeftForce = 0;
   private int rollRightForce = 0;
   
   private boolean tiltForward;
   private boolean tiltBackward;
   private boolean rollLeft;
   private boolean rollRight;

   
   public ShipKeyBoardListener(Main app){
       
       app.getInputManager().addMapping("tiltForward", new KeyTrigger(KeyInput.KEY_UP));
       app.getInputManager().addMapping("tiltBackward", new KeyTrigger(KeyInput.KEY_DOWN));
       app.getInputManager().addMapping("rollLeft", new KeyTrigger(KeyInput.KEY_LEFT));
       app.getInputManager().addMapping("rollRight", new KeyTrigger(KeyInput.KEY_RIGHT));
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
    
    public void step() {
        adjustForces();
        this.ship.wPressed(force(tiltForwardForce));
        this.ship.sPressed(force(tiltBackwardForce));
        this.ship.aPressed(force(rollLeftForce));
        this.ship.dPressed(force(rollRightForce));
    }
    
    public void adjustForces() {
        if (tiltForward) {
            tiltForwardForce = Math.min(20, tiltForwardForce + 1);
        } else {
            tiltForwardForce = Math.max(0, tiltForwardForce - 2);
        }
        if (tiltBackward) {
            tiltBackwardForce = Math.min(20, tiltBackwardForce + 1);
        } else {
            tiltBackwardForce = Math.max(0, tiltBackwardForce - 2);
        }
        if (rollLeft) {
            rollLeftForce = Math.min(20, rollLeftForce + 2);
        } else {
            rollLeftForce = Math.max(0, rollLeftForce - 2);
        }
        if (rollRight) {
            rollRightForce = Math.min(20, rollRightForce + 2);
        } else {
            rollRightForce = Math.max(0, rollRightForce - 2);
        }
    }
    
    private float force(int frame) {
        if (frame / 20.0 > 0.78) return 1.0f;
        return (float) Math.sin((double) (frame / 20.0));
    }
    
}
