/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame;

import input.ShipKeyBoardListener;
import java.util.ArrayList;
import java.util.HashMap;
import objects.GameObject;
import ship.Ship;

/**
 *
 * @author Gerryflap
 */
public class GameController {
    private Main app;
    private HashMap<Integer, GameObject> objects;
    
    public GameController(Main app){
        this.app = app;
        this.objects = new HashMap<Integer, GameObject>();
    }
    
    private GameObject getById(int id){
        return objects.get(id);
    }
    
    
}
