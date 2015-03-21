/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame;

import input.ShipKeyBoardListener;
import ship.Ship;

/**
 *
 * @author Gerryflap
 */
public class GameController {
    private Ship thisShip;
    private Main app;
    private ShipKeyBoardListener shipKeyBoardListener;
    //public static final int nee = 1;
    
    public GameController(Main app){
        this.app = app;
        this.shipKeyBoardListener = new ShipKeyBoardListener(app);
        this.shipKeyBoardListener.setShip(thisShip);
    }
}
