
package cmsc335_project_1_Elinkowski;

import java.util.Scanner;

/**
 * File name: PassengerShip class
 * Date: 20181024 1442L
 * Author: Keith R. Elinkowski
 * Purpose: Extends Ship class. Class is used to hold values for passengers.
 */

public class PassengerShip extends Ship{
    private int numberOfPassengers;
    private int numberOfRooms;
    private int numberOfOccupiedRooms;

    //PassengerShip Constructor
    public PassengerShip(Scanner scanner) {
        super(scanner);
        if(scanner.hasNextInt()) {
            numberOfPassengers = scanner.nextInt();
        }
        else {
            numberOfPassengers = 0;
        }
        if(scanner.hasNextInt()) {
            numberOfRooms = scanner.nextInt();
        }
        else {
            numberOfRooms = 0;
        }
        if(scanner.hasNextInt()) {
            numberOfOccupiedRooms = scanner.nextInt();
        }
        else {
            numberOfOccupiedRooms = 0;
        }
    }
    
    //Getter and Setter for numberOfPassengers
    public int getNumberOfPassengers() {
        return numberOfPassengers;
    }
    
    public void setNumberOfPassengers(int passengers) {
        if(passengers >= 0) {
            numberOfPassengers = passengers;
        }
    }
    
    //Getter and Setter for numberOfRooms
    public int getNumberOfRooms() {
        return numberOfRooms;
    }
    
    public void setNumberOfRooms(int rooms) {
        if(rooms >= 0) {
            numberOfRooms = rooms;
        }
    }
    
    //Getter and Setter for numberOfOccupiedRooms
    public int getNumberOfOccupiedRooms() {
        return numberOfOccupiedRooms;
    }
    
    public void setNumberOfOccupiedRooms(int occupiedRooms) {
        if(occupiedRooms >= 0) {
            numberOfOccupiedRooms = occupiedRooms;
        }
    }
    
    //toString Method
    @Override
    public String toString() {
        String outPassengerShip = String.format("Passenger Ship %s\n", 
                super.toString());
        outPassengerShip += String.format("   |___Passengers:     %d\n", 
                numberOfPassengers);
        outPassengerShip += String.format("   |___Total Rooms:    %d\n", 
                numberOfRooms);
        outPassengerShip += String.format("   |___Occupied Rooms: %d\n", 
                numberOfOccupiedRooms);
        return outPassengerShip;
    }
}
