
package cmsc335_project_1_Elinkowski;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * File name: Ship class
 * Date: 20181025 1339L
 * Author: Keith R. Elinkowski
 * Purpose: Extends Thing class. This class holds all the requirements a ship 
 * in this World, would need to dock.
 */

public class Ship extends Thing{
    private PortTime arrivalTime;
    private PortTime dockTime;
    double draft;
    double length;
    double weight;
    double width;
    private ArrayList<Job> jobs;
    
    //Ship Constructor
    public Ship(Scanner scanner) {
        super(scanner);
        jobs = new ArrayList<>();
        if(scanner.hasNextDouble()) {
            weight = scanner.nextDouble();
        }
        else {
            weight = 0.0;
        }
        if(scanner.hasNextDouble()) {
            length = scanner.nextDouble();
        }
        else {
            length = 0.0;
        }
        if(scanner.hasNextDouble()) {
            width = scanner.nextDouble();
        }
        else {
            width = 0.0;
        }
        if(scanner.hasNextDouble()) {
            draft = scanner.nextDouble();
        }
        else {
            draft = 0.0;
        }
    }
    
    //Getter and Setter for arrivalTime
    public PortTime getArrivalTime() {
        return arrivalTime;
    }
    
    public void setArivalTime(PortTime aTime) {
        arrivalTime = aTime;
    }
    
    //Getter and Setter for dockTime;
    public PortTime getDockTime() {
        return dockTime;
    }
    
    public void setDockTime(PortTime dTime) {
        dockTime = dTime;
    }
    
    //Getter and Setter for draft
    public double getDraft() {
        return draft;
    }
    
    public void setDraft(double d) {
        if(d >= 0.0) {
            draft = d;
        }
    }
    
    //Getter and Setter for length
    public double getLength() {
        return length;
    }
    
    public void setLength(double l) {
        if(l >= 0.0) {
            length = l;
        }
    }
    
    //Getter and Setter for weight
    public double getWeight() {
        return weight;
    }
    
    public void setWeight(double we) {
        if(we >= 0.0) {
            weight = we;
        }
    }
    
    //Getter and Setter for width
    public double getWidth() {
        return width;
    }
    
    public void setWidth(double wi) {
        if(wi >= 0.0) {
            width = wi;
        }
    }
    
    //Getter and Setter for jobs
    public ArrayList<Job> getJobs() {
        return jobs;
    }
    
    public void setJobs(ArrayList<Job> job) {
        jobs = job;
    }
    
    //toString Method
    @Override
    public String toString(){
        String outShip = String.format("%s\n", super.toString());
        outShip += String.format("   |___Draft  =  %.2f\n", draft);
        outShip += String.format("   |___Length =  %.2f\n", length);
        outShip += String.format("   |___Weight =  %.2f\n", weight);
        outShip += String.format("   |___Width  =  %.2f\n", width);
        if(jobs != null) {
           outShip += String.format("   |___Jobs: =   %s", jobs);
        }
        else {
            outShip += String.format("   |___Jobs: = NO JOBS");
        }
        return outShip;
    }
}
