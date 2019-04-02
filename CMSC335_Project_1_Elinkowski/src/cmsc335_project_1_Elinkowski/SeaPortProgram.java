
package cmsc335_project_1_Elinkowski;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.HeadlessException;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.border.EmptyBorder;
import javax.swing.tree.DefaultMutableTreeNode;

 /**
 * File name: SeaPortProgram class
 * Date: 20181024 1417L
 * Author: Keith R. Elinkowski
 * Purpose: Primary program, contains the Main method.  It initializes the 
 * program, creates the GUI and instantiates a World.  It also includes 
 * actionListeners for the "Read", "Display", and "Search" buttons as well 
 * as a ComboBox that allows the User to select their search target. Contains
 * a method to generate a JTree object based off of the User input simulation
 * file.
 */

public class SeaPortProgram  extends JFrame {
    
    private World world;
    private Scanner scanner;
    private JPanel structurePanel;
    private JTextArea console;
    private JComboBox<String> searchComboBox;
    private JTextField searchField;
    private JTree root;
    private Dimension screenSize;
    private HashMap<Integer, Thing> structureMap;
    private final HashMap<Integer, SeaPort> portMap = new HashMap<>();
    private final HashMap<Integer, Dock> dockMap = new HashMap<>();
    private final HashMap<Integer, Ship> shipMap = new HashMap<>();
    
    /* Starts program */
    public static void main(String[] args) {
        SeaPortProgram simulation = new SeaPortProgram();
        simulation.seaPortProgramDisplay();
        simulation.addWindowListener(new WindowAdapter() {
        @Override
        public void windowClosing(WindowEvent e) {
            if(JOptionPane.showConfirmDialog(simulation, "Quit?", "Exiting.", 
                    JOptionPane.OK_OPTION, 0, new ImageIcon(""))!= 0){
                return;
            }
            System.exit(0);
        }});
    }
    
    /*Draws the GUI and handles all of the buttons, panels and comboBoxes.
    Uses BorderLayout to display everything in a neat, but non-intuitive 
    manner*/
    private void seaPortProgramDisplay() {
        /* GUI setup */
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        screenSize = toolkit.getScreenSize();
        setTitle ("Keith R. Elinkowski Seaport Simulation");
        setSize((screenSize.width / 2), 800);
        setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        setLayout(new BorderLayout());
        
        /* Console Text Area */
        console = new JTextArea();
        console.setFont(new Font("Monospaced", 0, 12));
        console.setEditable(false);
        JPanel consolePanel = new JPanel(new BorderLayout());
        consolePanel.setPreferredSize(new Dimension(300, screenSize.height/2));
        consolePanel.setBorder(new EmptyBorder(10,0,25,25));
        JScrollPane scrollPane = new JScrollPane(console);
        scrollPane.setPreferredSize(new Dimension(300, 400));
        consolePanel.add(scrollPane, BorderLayout.NORTH);
        add(consolePanel, BorderLayout.CENTER);

        /* Read Button */
        JButton readButton = new JButton("Read");
        readButton.addActionListener((ActionEvent e)->readSimulation());

        /* Display Button */
        JButton displayButton = new JButton("Display");
        displayButton.addActionListener((ActionEvent e)->displayStructure());

        /* Search ComboBox */
        JLabel searchLable = new JLabel("Search Target");
        searchField = new JTextField(15);
        searchComboBox = new JComboBox<>();
        searchComboBox.addItem("Index");
        searchComboBox.addItem("Type");
        searchComboBox.addItem("Name");
        searchComboBox.addItem("Skill");
        
        /* Search Button */
        JButton searchButton = new JButton("Search");
        searchButton.addActionListener((ActionEvent e)->search((String)
                (searchComboBox.getSelectedItem()), searchField.getText()));

        /* Action Panel */
        JPanel actionPanel = new JPanel();
        actionPanel.setFont(new Font("Monospaced", 0, 12));
        actionPanel.add(readButton);
        actionPanel.add(displayButton);
        actionPanel.add(searchLable);
        actionPanel.add(searchField);
        actionPanel.add(searchComboBox);
        actionPanel.add(searchButton);
        add(actionPanel,BorderLayout.NORTH);

        /* Structure Panel */
        structurePanel = new JPanel(new BorderLayout());
        structurePanel.setFont(new Font("Monospaced", 0, 12));
        structurePanel.setPreferredSize(new Dimension(400, screenSize.height/2));
        structurePanel.setBorder(new EmptyBorder(10,25,25,25));
        add(structurePanel, BorderLayout.WEST);
       
        validate();
    }
    
    /*Method to be called when the User hits the Read button. Uses an
    instance of JFileChooser to select a simulation file to read. Returns
    a Scanner object that is then used by the World Class to populate
    the structure of the World.*/
    private Scanner readSimulation() {
        console.append(">>> You pressed the \"Read\" Button.\n");
        JFileChooser fc = new JFileChooser(".");
        try {
            if(fc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                scanner = new Scanner(fc.getSelectedFile());
                console.append(">>> Reading simulation file [" + 
                        fc.getSelectedFile().getName() + "]\n");
                TimeUnit.MILLISECONDS.sleep(500);
                console.append(">>> . \n");
                TimeUnit.MILLISECONDS.sleep(500);
                console.append(">>> . .\n");
                TimeUnit.MILLISECONDS.sleep(500);
                console.append(">>> . . .\n");
                console.append(">>> Simulation [" + fc.getSelectedFile().
                        getName() + "] successfully loaded.\n");
            }
        } catch (InterruptedException e) {
            
        } catch (FileNotFoundException e) {
            console.append(">>> Error occurred while loading the simulation. "
                    + "Please try again!\n");
        }
        console.append(">>> Structure is ready to be display.\n");
        console.append("<<< Please hit the \"Display\" button!\n");
        return null;
    }
    
    /*Method used to populate the World structure.  Uses HashMaps and method 
    calls of each Type to store objects.*/ 
    private void buildStructure(Scanner lineScanner){
        world = new World(lineScanner);
        structureMap = new HashMap<>();
        while(lineScanner.hasNextLine()) {
            String line = lineScanner.nextLine().trim();
            if(line.length() == 0) {
                continue;
            }
            Scanner thingScanner = new Scanner(line);
            if(!thingScanner.hasNext()) {
                return;
            }
            String thing = thingScanner.next();
            switch(thing) {
                case "port":
                    addPort(thingScanner);
                    break;
                case "dock":
                    addDock(thingScanner);
                    break;
                case "passengership":
                    addShip(thingScanner, thing);
                    break;
                case "pship":
                    addShip(thingScanner, thing);
                    break;
                case "cargoship":
                    addShip(thingScanner, thing);
                    break;
                case "cship":
                    addShip(thingScanner, thing);
                    break;
                case "person":
                    addPerson(thingScanner);
                    break;
                case "job":
                    addJob(thingScanner);
                    break;
                default:
                    break;
            }
        }
    }
    
   /*Method used to build and track World structure for ports.*/
    public SeaPortProgram() throws HeadlessException {
    }
    private void addPort(Scanner sc) {
        SeaPort port = new SeaPort(sc);
        portMap.put(port.getIndex(), port);
        structureMap.put(port.getIndex(), port);
        world.assignSeaPort(port);
        console.append(">>> Added new Port - ["+port.getName()+"]\n");
    }
    
    /*Method used to build and track World structure for Docks. */
    private void addDock(Scanner sc) {
        Dock dock = new Dock(sc);
        dockMap.put(dock.getIndex(), dock);
        structureMap.put(dock.getIndex(), dock);
        world.assignDock(dock, portMap.get(dock.getParent()));
        console.append(">>> Added new Pier - ["+dock.getName()+"]\n");
    }
    
    /*Method used to build and track World structure for Ships. */
    private void addShip(Scanner sc, String thing) {
        if(thing.equals("pship") || thing.equals("passengership")) {
            PassengerShip passengerShip = new PassengerShip(sc);
            shipMap.put(passengerShip.getIndex(), passengerShip);
            structureMap.put(passengerShip.getIndex(), passengerShip);
            SeaPort passengerPort = portMap.get(passengerShip.getParent());
            Dock passengerDock = dockMap.get(passengerShip.getParent());
            if(passengerPort == null) {
                passengerPort = portMap.get(passengerDock.getParent());
            }
            world.assignShip(passengerShip, passengerPort, passengerDock);
            console.append(">>> Added new PassengerShip - ["+
                    passengerShip.getName()+"]\n");
        }
        if(thing.equals("cship") || thing.equals("cargoship")) {
            CargoShip cargoShip = new CargoShip(sc);
            shipMap.put(cargoShip.getIndex(), cargoShip);
            structureMap.put(cargoShip.getIndex(), cargoShip);
            SeaPort cargoPort = portMap.get(cargoShip.getParent());
            Dock cargoDock = dockMap.get(cargoShip.getParent());
            if(cargoPort == null) {
                cargoPort = portMap.get(cargoDock.getParent());
            }
            world.assignShip(cargoShip, cargoPort, cargoDock);
            console.append(">>> Added new CargoShip - ["+cargoShip.getName()
                    +"]\n");
        }
    }
    
    /*Method used to track and build World strucutre for People*/
    private void addPerson(Scanner sc) {
        Person person = new Person(sc);
        structureMap.put(person.getIndex(), person);
        world.assignPerson(person, portMap.get(person.getParent()));
        console.append(">>> Added new Person - ["+person.getName()+"]\n");
    }
    
    /*Method used to track and build World structure for Jobs */
    private void addJob(Scanner sc) {
        Job job = new Job(sc);
        structureMap.put(job.getIndex(), job);
        world.assignJob(job, structureMap.get(job.getParent()));
        console.append(">>> Added new Job - ["+job.getName()+"]\n");
    }
    
    /*Simple method used to display the world structure in the left most pane
    of the gui.*/
    private void drawStructure() {
        root = new JTree(createBranch("World Tree"));
        JScrollPane structurePane = new JScrollPane(root);
        structurePanel.add(structurePane, BorderLayout.CENTER);
        validate();
    }
        
    /*Helper method to build world structure, using basic tree, branch, 
    leaf(node) structure.  This method adds a branch for each thing of a type.*/
    private DefaultMutableTreeNode createBranch(String title) {
        DefaultMutableTreeNode branch = new DefaultMutableTreeNode(title);
        DefaultMutableTreeNode rootBranch;
        DefaultMutableTreeNode jobBranch;
        for(SeaPort port : world.getPorts()) {
            try {
                rootBranch = new DefaultMutableTreeNode("Port of " + 
                        port.getName()+"("+port.getIndex()+")");
                branch.add(rootBranch);
            if(port.getDocks() != null) {
                rootBranch.add(addNode(port.getDocks(), "Docks"));
            }
            if(port.getQueue() != null) {
                rootBranch.add(addNode(port.getQueue(), "Queue"));
            }
            if(port.getShips() != null) {
                rootBranch.add(addNode(port.getShips(), "Ships"));
                
            }
            if(port.getPersons() != null) {
                rootBranch.add(addNode(port.getPersons(), "People"));
            }
            if(port.getShips() != null) {
                jobBranch = new DefaultMutableTreeNode("Jobs");
                rootBranch.add(jobBranch);
                for(Ship ship : port.getShips()) {
                    if(ship.getJobs().size() > 0) {
                        jobBranch.add(addNode(ship.getJobs(), ship.getName() 
                                + " Jobs"));
                    }
                }
            }
            } catch(IllegalArgumentException e) {
                console.append(">>> Error!\n");
            }

        }
        return branch;
    }
    
    /*Creates a branch for each Type of thing. */ 
    private <T extends Thing> DefaultMutableTreeNode addNode(ArrayList<T> 
            things, String name){
        DefaultMutableTreeNode leaf = new DefaultMutableTreeNode(name);
        for(Thing thing : things) { 
            String displayString = thing.getName();
            if(thing instanceof Dock) {
                if(((Dock)thing).getShip() != null) {
                    displayString = String.format("%s(%d): SS %s(%d)", 
                            ((Dock)thing).getName(), 
                            ((Dock)thing).getIndex(), 
                            ((Dock)thing).getShip().getName(), 
                            ((Dock)thing).getShip().getIndex());
                }
                else {
                    displayString = "Empty Berth";
                }          
            }
            if(thing instanceof Ship) {
                if(((Ship)thing).getDraft() != 0) {
                    displayString = String.format("SS %s(%d)", 
                            ((Ship)thing).getName(), ((Ship)thing).getIndex());
                }
                else {
                    displayString = "";
                }
            }
            if(thing instanceof Person) {
                if(((Person)thing).getSkill() != null) {
                    displayString = String.format("Contractor %s(%d)", 
                            ((Person)thing).getName(), ((Person)thing).getIndex());
                }
            }
            if(thing instanceof Job) {
                if(((Job)thing).getName() != null) {
                    displayString = String.format("%s(%d)", ((Job)thing).getName(), ((Job)thing).getIndex());
                }
            }
            leaf.add(new DefaultMutableTreeNode(displayString));
        }
        return leaf;
    }
    
    /*Method used to display the structure of the simulation world.  Calls
    the buildStructure method to do the work of populating the HashMap
    and the drawStructure method to display it in the left most pane of the
    GUI.*/ 
    public void displayStructure(){
        if(scanner == null) {
            console.append(">>> Simulation not loaded. You must \"Read\" in the "
                    + "simulation file before hitting the \"Display\" button.\n");
            readSimulation();
        }
        console.append(">>> You hit the \"Display\" Button!\n");
        buildStructure(scanner);
        drawStructure();
    }
    
    /*Search helper method called when user hits search button.  Uses the
    combobox input to determine what the target of the search is.  dumps 
    results into the console window.*/
    private void search(String searchType, String searchTarget) {
        console.append(">>> You pressed the \"Search\" button!\n");
        if(scanner == null) {
            displayStructure();
        }
        if(searchTarget.equals("")) {
            console.append(">>> Please try again!\n");
            return;
        }
        console.append(">>> You selected the following \"Type\": [" + searchType 
                + "], and are searching for, [" + searchTarget + "]\n\n");
        ArrayList<Thing> searchResults = new ArrayList<>();
        switch(searchType) {
            case "Index":
                try {
                    int requestedIndex = Integer.parseInt(searchTarget);
                    searchResults.add(structureMap.get(requestedIndex));
                }
                catch(NumberFormatException e) {
                    console.append(">>> Invalid \"Index\" input, please try again!");
                }
                break;
            case "Type":
                try {
                    searchResults = world.searchByType(searchTarget);
                    if(searchResults == null) {
                        console.append(">>> Type not found!\n");
                        return;
                    }
                } catch (NullPointerException e) {
                    console.append(">>> Invalid \"Type\" input, please try again!");
                }
                break;
            case "Name":
                try {
                searchResults = world.searchByName(searchTarget);
                if(searchResults.size() <= 0) {
                        console.append(">>> Name not found!\n");
                        return;
                    }
                } catch (NullPointerException e) {
                    console.append(">>> Invalid \"Name\" input, please try again!");
                }
                break;
            case "Skill":
                try {
                    searchResults = world.findSkill(searchTarget);
                    if(searchResults.size() <= 0) {
                        console.append(">>> Skill not found!\n");
                        return;
                    }
                } catch (NullPointerException e) {
                    console.append(">>> Invalid \"Skill\" input, please try again!");
                }
                break;
        }
        for(Thing thing : searchResults) {
            if(thing != null) {
                console.append(thing + "\n");
            }
            else {
                console.append("Your search returned ZERO results.\n");
            }
        }
    }  
}