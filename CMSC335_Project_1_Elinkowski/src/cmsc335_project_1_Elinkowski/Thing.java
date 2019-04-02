package cmsc335_project_1_Elinkowski;

import java.util.Scanner;

/**
 * File name: Thing class
 * Date: 20181024 1436L
 * Author: Keith R. Elinkowski
 * Purpose: Implements Comparable. This class is the base class for all other 
 * objects.
 */

public class Thing implements Comparable{
    private int index;
    private String name;
    private int parent;
    
    //Thing Constructor
    public Thing(Scanner scanner) {
        if(scanner.hasNext()) {
            name = scanner.next();
        }
        else {
            name = "Error!";
        }
        
        if(scanner.hasNextInt()) {
            index = scanner.nextInt();
        }
        else {
            index = 0;
        }
        
        if(scanner.hasNextInt()) {
            parent = scanner.nextInt();
        }
        else {
            parent = 0;
        }
    }
    
    //Getter and Setter for index
    public int getIndex() {
        return index;
    }
    
    public void setIndex(int i) {
        if(i > 0) {
            index = i;
        }
    }
    
    //Getter and Setter for name
    public String getName() {
        return name;
    }
    
    public void setName(String n) {
        name = n;
    }
    
    //Getter and Setter for parent
    public int getParent() {
        return parent;
    }
    
    public void setParent(int p) {
        if(p > 0) {
            parent = p;
        }
    }

    //toString method
    @Override
    public String toString() {
        return String.format("%s, INDEX: (%d)", name, index);
    }
    @Override
    public int compareTo(Object o) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}