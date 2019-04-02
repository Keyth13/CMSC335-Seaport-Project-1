
package cmsc335_project_1_Elinkowski;

import java.util.Scanner;

/**
 * File name: Dock class
 * Date: 20181025 1450L
 * Author: Keith R. Elinkowski
 * Purpose: Extends Thing class.  Specifies which ships are docked.
 */

public class Dock extends Thing{
    private Ship ship;
    
    //Dock Constructor
    public Dock(Scanner scanner) {
        super(scanner);
    }
    
    //Getter and Setter for ship
    public Ship getShip() {
        return ship;
    }
    
    public void setShip(Ship s) {
        ship = s;
    }
    
    //toString method
    @Override
    public String toString() {
        String outDock;
        if(ship != null) {
            outDock = ship.toString();
        }
        else {
            outDock = "";
        }
        return String.format(" %s\n   |___%s\n", super.toString(), outDock);
   }
}
