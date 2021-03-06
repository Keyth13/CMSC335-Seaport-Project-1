
package cmsc335_project_1_Elinkowski;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * File name: World class
 * Date: 20181025 1009L
 * Author: Keith R. Elinkowski
 * Purpose: Extends Thing class. Primary class that organizes all other Thing 
 * objects. For this program, this class acts as the world for the scanned 
 * simulation file. It accepts Scanner input data and assigns it.  It assembles 
 * instances of the programs classes and moves it to the proper ArrayList. This 
 * class also contains code for the search functionality.
 */

public class World extends Thing {
    private ArrayList<SeaPort> ports;
    private PortTime time;
    
    //World Constructor
    public World(Scanner scanner) {
        super(scanner);
        ports = new ArrayList<>();
    }
    
    //Getter and Setter for Ports
    public ArrayList<SeaPort> getPorts() {
        return ports;
    }
    
    public void setPorts(ArrayList<SeaPort> p) {
        ports = p;
    }
    
    //Getter and Setter for PortTime
    public PortTime getPortTime() {
        return time;
    }
    
    public void setPortTime(PortTime t) {
        time = t;
    }
    
    /*Methods used to assign Things to their proper port.  If the given
    thing has a parent index that matches its super class, it will be 
    added to the proper list */
    public void assignSeaPort(SeaPort port) {
        ports.add(port);
    }

    public void assignDock(Dock dock, SeaPort port) {
        port.getDocks().add(dock);
    }
    
    public void assignShip(Ship ship, SeaPort port, Dock dock) {
        port.getShips().add(ship);
        if(dock != null) {
            dock.setShip(ship);
        }
        else {
            port.getQueue().add(ship);
        }
    }
    public void assignPerson(Person person, SeaPort port) {
        port.getPersons().add(person);
    }
    
    public void assignJob(Job job, Thing thing) {
        if(thing instanceof Ship) {
            ((Ship)thing).getJobs().add(job);
        }
        else {
            ((Dock)thing).getShip().getJobs().add(job);
            job.setParent(((Dock)thing).getShip().getIndex());
        }
    }
    
    /*Search method to verify user selected Type and calls proper method
    to then find the requested Type */
    public ArrayList<Thing> searchByType(String requestedType) {
        String searchType = requestedType.toUpperCase().replace(" ", "");
        for(SeaPort port : ports) {
            switch(searchType) {
                case "SEAPORT":
                    return findType(ports);
                case "PORT":
                    return findType(ports);
                case "DOCK":
                    return findType(port.getDocks());
                case "PIER":
                    return findType(port.getDocks());
                case "PERSON":
                    return findType(port.getPersons());
                case "PEOPLE":
                    return findType(port.getPersons());
                case "SHIP":
                    return findType(port.getShips());
                case "CSHIP":
                    return findType(port.getShips());
                case "CARGOSHIP":
                    return findType(port.getShips());
                case "PSHIP":
                    return findType(port.getShips());
                case "PASSENGERSHIP":
                    return findType(port.getShips());
                case "JOB":
                    ArrayList<ArrayList<Thing>> jobs = new ArrayList<>();
                    ArrayList<Thing> jobList = new ArrayList<>();
                    for(Ship ship : port.getShips()) {
                        jobs.add(findType(ship.getJobs()));
                    }
                    for(ArrayList<Thing> innerList : jobs) {
                        for(Thing thing : innerList) {
                            jobList.add(thing);
                        }
                    }
                    return jobList;
            }
        }
        return null;
    }
   
    /*Search method that looks through each list for each Type.  All 
    found matching names are then stored in an Arraylist and returned to 
    the console*/
    public ArrayList<Thing> searchByName(String name) {
        ArrayList<Thing> searchResults = new ArrayList<>();
        for(SeaPort port : ports) {
            if(port.getName().equalsIgnoreCase(name)) {
                    searchResults.add(port);
            }
            if(name != null){
                try {
                    searchResults.addAll(findName(port.getDocks(), name));
                } catch (NullPointerException e) {
                    return null;
                }
                try {
                    searchResults.addAll(findName(port.getShips(), name));
                } catch (NullPointerException e) {
                    return null;
                }
                try {
                    searchResults.addAll(findName(port.getPersons(), name));
                } catch (NullPointerException e) {
                    return null;
                }
            }
        }
        return searchResults;
    }
    
    /*Helper method that to find all User requested types.  Returns an ArrayList 
    of all Things that match the User requested type.*/
    private <T> ArrayList<Thing> findType(ArrayList<T> list) {
        ArrayList<Thing> findTypeResults = new ArrayList<>();
        for(T thing : list) {
            if(thing instanceof SeaPort) {
                findTypeResults.add((Thing)thing);
            }
            if(thing instanceof Dock) {
                findTypeResults.add((Thing)thing);
            }
            if(thing instanceof Ship) {
                findTypeResults.add((Thing)thing);
            }
            if(thing instanceof CargoShip) {
                findTypeResults.add((Thing)thing);
            }
            if(thing instanceof PassengerShip) {
               findTypeResults.add((Thing)thing);
            }
            if(thing instanceof Person) {
               findTypeResults.add((Thing)thing);
            }
            if(thing instanceof Job) {
               findTypeResults.add((Thing)thing);
            }
        }
        return findTypeResults;
    }
        
    /*Helper method to find all instances of user requested Name.  
    Returns an ArrayList of all Things that match the User requested Name.*/
    private<T extends Thing> ArrayList<Thing> findName(ArrayList<T> list, 
            String name) {
        ArrayList<Thing> findResults = new ArrayList<>();
        for(T thing : list) {
            if(thing.getName().equalsIgnoreCase(name)) {
                findResults.add(thing);
            }
        }
        return findResults;
    }
    
    /*Helper method to find all People who have the user requested Skill. 
    Returns an ArrayList of all People with the user requested Skill.*/
    public ArrayList<Thing> findSkill(String skill) {
        ArrayList<Thing> skillResults = new ArrayList<>();
        for(SeaPort port : ports) {
            for(Person person : port.getPersons()){
                if(person.getSkill().equalsIgnoreCase(skill)){
                    skillResults.add(person);
                }
            }
        }
        return skillResults;
    }
    
    /*toString method that builds a list of all ports in Simulation
    world.*/
    @Override
    public String toString() {
        String outWorld = "Welcome to my World! >>> \n";
        for(SeaPort port : ports) {
            outWorld += port;
        }
        return outWorld;
    }
}
