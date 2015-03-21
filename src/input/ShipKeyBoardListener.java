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
   
   public ShipKeyBoardListener(Main app){
       
       app.getInputManager().addMapping("tiltForward", new KeyTrigger(KeyInput.KEY_W));
       app.getInputManager().addMapping("tiltBackward", new KeyTrigger(KeyInput.KEY_S));
       app.getInputManager().addMapping("shoot", new KeyTrigger(KeyInput.KEY_SPACE));
       app.getInputManager().addListener(this, new String[]{"tiltForward", "tiltBackward", "shoot"});
   }
    
    public void setShip(Ship ship){
        this.ship = ship;
    }
    public void onAction(String name, boolean isPressed, float tpf) {
        if (isPressed){
            if(name.equals("tiltForward")){
                this.ship.wPressed(); 
            } else if (name.equals("tiltBackward")){
                this.ship.sPressed();
            } else if (name.equals("shoot")){
                this.ship.shoot();
            }
        }
    }
    
}
