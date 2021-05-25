import java.util.*;
/**
 *  This class is the main class of the "World of Zuul" application. 
 *  "World of Zuul" is a very simple, text based adventure game.  Users 
 *  can walk around some scenery. 
 * 
 * This main class creates and initialises all the others: it creates all
 *  rooms, creates the parser and starts the game.  It also evaluates and
 *  executes the commands that the parser returns.
 * 
 * */

public class Game 
{
    private Parser parser;
    private Stack<Room> path;
    private Player player;
        
    /**
     * Create the game and initialise its internal map.
     */
    public Game() 
    {
        path = new Stack<Room>();
        createRooms();
        parser = new Parser();
    }

    /**
     * Create all the rooms and link their exits together.
     */
    private void createRooms()
    {
        Room outside, theatre, pub, lab, office, cellar;
        TransporterRoom secretRoom;
        RoomRandomizer random = new RoomRandomizer();
        
        // create the rooms
        outside = new Room("outside the main entrance of the university");
        theatre = new Room("in a lecture theatre");
        pub = new Room("in the campus pub");
        lab = new Room("in a computing lab");
        office = new Room("in the computing admin office");
        cellar = new Room("in the cellar");
        
        
        // add the rooms to the RoomRandomizer object
        random.addRoom(outside);
        random.addRoom(pub);
        random.addRoom(office);
        random.addRoom(cellar);
        
        // create transporter room
        secretRoom = new TransporterRoom("in the secret room", random);
        
        // initialise room exits
        outside.setExit("east", theatre);
        outside.setExit("south", lab);
        outside.setExit("west", pub);
        theatre.setExit("west", outside);
        theatre.setExit("south", secretRoom);
        pub.setExit("east", outside);
        lab.setExit("north", outside);
        lab.setExit("east", office);
        lab.setExit("west", secretRoom);
        office.setExit("west", lab);
        office.setExit("down", cellar);
        cellar.setExit("up", office);
        secretRoom.setExit("north", theatre);
        secretRoom.setExit("east", lab);

        // inicializa itens das salas
        outside.setItem("mushrrom","small red mushroom", 5);
        outside.setItem("key", "rusty key", 10);
        theatre.setItem("cookie", "magic cookie", 30);
        pub.setItem("bottle", "empty beer bottle", 50);
        lab.setItem("liquid", "disgusting green liquid", 10);
        lab.setItem("burner", " bunsen burner", 20);
        office.setItem("files","medical files", 35);
        cellar.setItem("knife", "sharp knife", 15);
        
        player = new Player(outside);  // start game outside
        path.push(player.getCurrentRoom()); // lista a sequência de cômodos visitados
    }

    /**
     *  Main play routine.  Loops until end of play.
     */
    public void play() 
    {            
        printWelcome();
        printLocationInfo();
        printItemInfo();

        // Enter the main command loop.  Here we repeatedly read commands and
        // execute them until the game is over.
        boolean finished = false;
        while (! finished) {
            Command command = parser.getCommand();
            finished = processCommand(command);
        }
        System.out.println("Thank you for playing.  Good bye.");
    }

    /**
     * Print out the opening message for the player.
     */
    private void printWelcome()
    {
        System.out.println();
        System.out.println("Welcome to the World of Zuul!");
        System.out.println("World of Zuul is a new, incredibly boring adventure game.");
        System.out.println("Type 'help' if you need help.");
        System.out.println();
    }
    
    /**
     * Imprime as informações atuais de localização
     */
    public void printLocationInfo()
    {
        System.out.println(player.getCurrentRoom().getLongDescription());
        System.out.println();
    }
    
    /**
     * Imprime as informações do item disponível na sala.
     */
    public void printItemInfo()
    {
        System.out.println("Items: ");
        for (Item item : player.getCurrentRoom().getItems()) {
            System.out.print(item.getName() + ": ");
            System.out.println(item.getDescription());
        }
        System.out.println();
    }

    /**
     * Given a command, process (that is: execute) the command.
     * @param command The command to be processed.
     * @return true If the command ends the game, false otherwise.
     */
    private boolean processCommand(Command command) 
    {
        boolean wantToQuit = false;
        
        CommandWord commandWord = command.getCommandWord();
        
        if(commandWord == CommandWord.UNKNOWN) {
            System.out.println("I don't know what you mean...");
            return false;
        }

        if (commandWord == CommandWord.HELP) {
            printHelp();
        }
        else if (commandWord == CommandWord.GO) {
            goRoom(command);
        }
        else if (commandWord == CommandWord.ITEMS){
            printItemsInfo();
        }
        else if (commandWord == CommandWord.EAT) {
            eat(command);
        }
        else if (commandWord == CommandWord.LOOK) {
            look();
        }
        else if (commandWord == CommandWord.TAKE) {
            take(command);
        }
        else if (commandWord == CommandWord.BACK) {
            backRoom(command);
        }
        else if (commandWord == CommandWord.QUIT){
            wantToQuit = quit(command);
        }
        // else command not recognised.
        return wantToQuit;
    }

    // implementations of user commands:

    /**
     * Print out some help information.
     * Here we print some stupid, cryptic message and a list of the 
     * command words.
     */
    private void printHelp() 
    {
        System.out.println("You are lost. You are alone. You wander");
        System.out.println("around at the university.");
        System.out.println();
        System.out.println("Your command words are:");
        parser.showCommands();
    }

    /** 
     * Try to go to one direction. If there is an exit, enter
     * the new room, otherwise print an error message.
     */
    private void goRoom(Command command) 
    {
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            System.out.println("Go where?");
            return;
        }

        String direction = command.getSecondWord();

        // Try to leave current room.
        Room nextRoom = player.getCurrentRoom().getExit(direction);
       
        if (nextRoom == null) {
            System.out.println("There is no door!");
        } else {
            if(player.getCurrentRoom() instanceof TransporterRoom) {
                System.out.println("Oh! That's weird! You are being transported to somewhere else ..."); 
            }
            player.setCurrentRoom(nextRoom);
            try {
                path.push(player.getCurrentRoom());
            } catch (Exception ex) { System.out.println("Failed");}
            printLocationInfo();
            printItemInfo();
        }
    }
    
    /**
     * Retornar para a sala anterior.
     */
    private void backRoom(Command command)
    {
        if(command.hasSecondWord()) {
            // if there is no second word,we do not need a second word
            System.out.println("We go back one room at a time. Type just back.");
            return;
        }
        if(path.size() == 1) {
            System.out.println("We are back where we started");
            printLocationInfo();
            printItemInfo();
            return;
        } else {
            if(path.lastElement() instanceof TransporterRoom) {
                System.out.println("There is something wrong with this room...");
                System.out.println("You cannot go back from here...");
                printLocationInfo();
                printItemInfo();
                return;
            } else {
                path.pop();
                player.setCurrentRoom(path.lastElement());
                printLocationInfo();
                printItemInfo();
            }
        }
    }
    
    /**
     * Imprimir a descrição do itens existentes na sala.
     */
    private void look()
    {
        printLocationInfo();
        printItemInfo();
    }
    
    /**
     * Pegar item.
     * 
     * @param command O comando executado pelo usuário. 
     */
    private void take(Command command)
    {
        if(command.hasSecondWord()) {
            String itemName = command.getSecondWord();
            for (Item item : player.getCurrentRoom().getItems()) {
                if (item.getName().equals(itemName)) {
                    if (player.take(item) == 0) {
                        player.getCurrentRoom().removeItem(item);
                        return;
                    }
                }
            }
        }
        else {
            System.out.println("One shall specify what one is willing to take from this room!");
        }
    }
    
    /**
     * Comer item.
     * 
     * @param command O comando executado pelo usuário.
     */
    private void eat(Command command)
    {
        if(command.hasSecondWord()) {
            String item = command.getSecondWord();
            for (Item itemName : player.getItems()) {
                if (itemName.getName().equals(item)) {
                    player.eat(itemName);
                    return;
                } else {
                    System.out.println("One do not have " + item + "! One shall continue hungry!");
                } 
            }
        } else {
            System.out.println("One shall specify what one shall eat!");
        }
    }
    
    /**
     * Imprimir os itens e o peso total do jogador.
     */
    private void printItemsInfo() {
        for (Item item : player.getItems()) {
            System.out.println(item.getName());
        }
        System.out.println("Total weight: " + player.getWeight());
    }

    /** 
     * "Quit" was entered. Check the rest of the command to see
     * whether we really quit the game.
     * @return true, if this command quits the game, false otherwise.
     */
    private boolean quit(Command command) 
    {
        if(command.hasSecondWord()) {
            System.out.println("Quit what?");
            return false;
        }
        else {
            return true;  // signal that we want to quit
        }
    }

    /**
     *
     */
    public static void main (String[] args) {
        Game game = new Game();
        game.play();
    }
}
