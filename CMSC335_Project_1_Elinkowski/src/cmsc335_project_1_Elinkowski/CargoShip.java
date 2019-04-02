
package cmsc335_project_1_Elinkowski;

import java.util.Scanner;

/**
 * File name: CargoShip class
 * Date: 20181024 1436L
 * Author: Keith R. Elinkowski
 * Purpose: Extends Ship class. Class is used to hold values for cargo.
 */

public class CargoShip extends Ship{
    private double cargoWeight;
    private double cargoVolume;
    private double cargoValue;

    
    //CargoShip Constructor
    public CargoShip(Scanner scanner) {
        super(scanner);
        if(scanner.hasNextDouble()) {
            cargoWeight = scanner.nextDouble();
        }
        else {
            cargoWeight = 0.0;
        }
        if(scanner.hasNextDouble()) {
            cargoVolume = scanner.nextDouble();
        }
        else {
            cargoVolume = 0.0;
        }
        if(scanner.hasNextDouble()) {
            cargoValue = scanner.nextDouble();
        }
        else {
            cargoValue = 0.0;
        }
    }
    
    //Getter and Setter for CargoValue
    public double getCargoValue() {
        return cargoValue;
    }
    
    public void setCargoValue(double cValue) {
        if(cValue >= 0.0) {
            cargoValue = cValue;
        }
    }
    
    //Getter and Setter for CargoVolume
    public double getCargoVolume() {
        return cargoVolume;
    }
    
    public void setCargoVolume(double cVolume) {
        if(cVolume >= 0.0) {
            cargoVolume = cVolume;
        }
    }
    
    //Getter and Setter for CargoWeight
    public double getCargoWeight() {
        return cargoWeight;
    }
    
    public void setCargoWeight(double cWeight) {
        if(cWeight >= 0.0) {
            cargoWeight = cWeight;
        }
    }
    
    //toString Method
    @Override
    public String toString() {
        String outCargoShip = String.format("Cargo Ship %s\n", super.toString());
        outCargoShip += String.format("   |___Cargo Weight: %.2f\n", cargoWeight);
        outCargoShip += String.format("   |___Cargo Volume: %.2f\n", cargoVolume);
        outCargoShip += String.format("   |___Cargo Value:  %.2f\n", cargoValue);
        return outCargoShip;
    }
}
