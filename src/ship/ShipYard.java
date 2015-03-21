package ship;

import java.util.ArrayList;

/**
 *
 * @author Destion
 */
public class ShipYard extends ArrayList<Ship> {
    
    public void addShip(Ship ship){
        this.add(ship);
    }
    
    public boolean shipDouble(int id){
        boolean exists = false;
        for(Ship ship : this){
            if (ship.getId() == id) {
                exists = true;
            }
        }
        return exists;
    }
    
    public int shipCount(){
        return this.size();
    }
    
}
