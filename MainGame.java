/**
 * Main game function for CGOL, finished & commented.
 *
 * Creator: Joshua Toumu'a
 * Date: 17/08/22
 * Version: 27
 * 
 */
import java.util.Random;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;
import java.util.Scanner;
import java.io.File;
import java.io.IOException;
import java.io.*;
import java.util.*;
import java.lang.Object;
//all imports needed
public class MainGame
{
    //creates generation counter with default of 5 if any errors occur
    int generationMax = 5;
    int generationCount = 0;
    
    //x and y is used to apply CGOL rules for every cell on the board
    int y = 0;
    int x = 0;
    
    //what the living and dead cells will look like on the console
    String livingCell = "█";
    String deadCell = "░";
    
    //how many milliseconds between each generation
    int timeDelay = 1000;
    
    //defines board and temp boards
    int[][] board;
    int[][] temp;
    boolean mainLoop = true;
    
    //establishes randomness for random board generation
    Random rand = new Random();
    
    //default size of the board is 20x20
    int size = 0;

    //import keyboard scanner
    Scanner keyboard = new Scanner(System.in);
    String keyInput;
    
    //define some booleans for loops for menu
    boolean menuLoop = true;
    boolean selectionScreen = false;
    public MainGame(){
        //clears canvas
        System.out.print('\u000c');
        //runs menu system method
        menuInput();
        //repeats for set amount of generations
        while(mainLoop){
            //separate loop for each generation
            while(generationCount<generationMax){
                //clears canvas and temp board
                System.out.print('\u000c');
                temp = new int[size][size];
                //runs program that applies CGOL rules to every cell on temp board
                checkCells();
                //applies changes to the real board
                //this is done on a temp board in order to prevent any interference from changes from previous cells to change the states of other cells
                board = temp;
                //creates a delay of a set amount of milliseconds, uses try to prevent crashes
                try{
                    TimeUnit.MILLISECONDS.sleep(timeDelay);
                }catch(Exception e){
                }
                //repeats to next generation
                generationCount++;
            }
            //runs code to allow user to continue using software after the loop finishes
            endMenu();
        }
    }
    
    //method for menu input code
    void menuInput(){
        System.out.println("Conway's Game Of Life Simulator");
        System.out.println("to populate the board, would you like to:");
        System.out.println("Would you like to load a 𝘀𝗲𝗲𝗱, 𝗿𝗮𝗻𝗱𝗼𝗺 board or 𝗲𝗱𝗶𝘁 manually?");
        
        //creates loop to make the user provide valid answer
        menuLoop = true;
        while(menuLoop){
            keyInput = keyboard.nextLine().toLowerCase();
            System.out.print('\u000c');
            switch(keyInput){
                case "seed":
                //runs txt file loader methods
                trySeedFile();
                //ends loop and prints out the board
                menuLoop = false;
                printBoard();
                break;

                case "random":
                //asks user for grid size then applies random population method
                System.out.println("How big do you want the grid?");
                keyInput = keyboard.nextLine().toLowerCase();
                size = removeChar(keyInput);
                //takes key input and changes board size accordingly
                board = new int[size][size];
                temp = new int[size][size];
                //runs method to randomly populate, ends the loop
                randomPopulate();
                menuLoop = false;
                break;
                
                case "edit":
                //asks user for grid size then applies the manual population method loop
                System.out.println("How big do you want the grid?");
                keyInput = keyboard.nextLine().toLowerCase();
                size = removeChar(keyInput);
                board = new int[size][size];
                temp = new int[size][size];
                manualPopulate();
                menuLoop = false;
                selectionScreen = true;
                manualEdit();
                break;

                default:
                //outputs the invalid input method
                System.out.println("invalid command. please enter \'seed\', \'random\' or \'edit\'.");
                System.out.println();
                System.out.println("to populate the board, would you like to:");
                System.out.println("Would you like to load a 𝘀𝗲𝗲𝗱, 𝗿𝗮𝗻𝗱𝗼𝗺 board or 𝗲𝗱𝗶𝘁 manually?");
                break;
            }
        }
        //takes key input and accounts for user error, removing any characters other than numbers
        System.out.println("How many generations?");
        keyInput = keyboard.nextLine();
        System.out.print('\u000c');
        generationMax = removeChar(keyInput);
        
        //takes key input and accounts for user error, removing any characters other than numbers
        System.out.println("How many milliseconds between each generation?");
        keyInput = keyboard.nextLine();
        System.out.print('\u000c');
        timeDelay = removeChar(keyInput);
        
        
        System.out.println("Would you like to use custom characters?");
        keyInput = keyboard.nextLine().toLowerCase();
        System.out.print('\u000c');
        //if user inputs yes, allows user to change livingCell and deadCell
        if(keyInput.equals("yes")||keyInput.equals("y")){
            System.out.println("What character for living cells?");
            livingCell = keyboard.nextLine();

            System.out.println("What character for dead cells?");
            deadCell = keyboard.nextLine();
        }
    }
    
    //method for user to change cells in console
    void manualEdit(){
        while (selectionScreen == true){
            //allows user to input x and y coordinates for the cell you want to replace
            System.out.println("Please select row: ");
            int rowSelection = removeChar(keyboard.nextLine())-1;
            System.out.println("Please select column: ");
            int columnSelection = removeChar(keyboard.nextLine())-1;
            
            //switches cells around, if living, switch to dead and vice versa.
            if(columnSelection >=0 && columnSelection < size && rowSelection >= 0 && rowSelection < size){
                if (board[rowSelection][columnSelection] == 1){
                    board[rowSelection][columnSelection] = 0;
                }else{
                    board[rowSelection][columnSelection] = 1;
                }
            }else{
                System.out.println("Invalid Cell.");
            }
            //prints out board
            for(int yModifier= 0; yModifier<size; yModifier++){
                System.out.print((yModifier+1)+"\t");
                for(int xModifier = 0; xModifier<size; xModifier++){
                    System.out.print(board[yModifier][xModifier] + " ");
                }
                System.out.println();
            }
            System.out.println();
            System.out.println("would you like to stop editing cells?");
            //if user answers "yes", ends loop
            keyInput = keyboard.nextLine();
            if(keyInput.equals("yes")){
                selectionScreen=false;
            }
            System.out.print('\u000c');
        }
    }

    //code for menu after running all generations
    void endMenu(){
        if(generationCount >= generationMax){
            System.out.println("Would you like to quit, continue, or restart the simulation?");
            String keyInput = keyboard.nextLine().toLowerCase();
            if(keyInput.equalsIgnoreCase("quit")){
                //if user inputs quit, ends whole game loop
                mainLoop = false;
                return;
            }else if(keyInput.equalsIgnoreCase("continue")){
                //if user inputs continue, asks for generations, restarts generation counter and restarts generation loop
                System.out.println("How many generations?");
                keyInput = keyboard.nextLine();
                generationMax = removeChar(keyInput);
                generationCount = 0;
            }else if(keyInput.equalsIgnoreCase("restart")){
                //starts the menuInput code, resets generations and clears board
                generationCount = 0;
                System.out.print('\u000c');
                menuInput();
            }else{
                //sends default text
                System.out.println("I do not understand that command. Enter \"continue\", \"quit\" or \"restart\".");
            }
        }
    }
    
    //method that applies the game rules to every cell
    void checkCells(){
        //goes along y axis, checks all cells
        for(y = 0; y<size; y++){
            //goes along x axis, checks all cells
            for(x = 0; x<size; x++){
                //applies the rules of CGOL, applies changes to cells on temp board
                gameRules();

                //prints out previous generation
                if(board[y][x] == 1){
                    System.out.print(livingCell+" ");
                }else{
                    System.out.print(deadCell+" ");
                }

            }
            //creates new line
            System.out.println();

        }
    }
    
    //method for loading in .txt file
    void trySeedFile(){
        //defines file
        File seed = new File("CGOLSeed.txt");
        try{
            //creates scanner
            Scanner fileReader = new Scanner(seed);
            //code that takes the 20x20 grid in character by character and places into array
            boolean firstLineLoop = true;
            
            //code to set board sizes
            board = new int[size][size];
            temp = new int[size][size];
            for (int seedY = 0; seedY<size-1; seedY++){
                //takes keyinput
                String fileString = fileReader.nextLine();
                for (int seedX = 0; seedX<size; seedX++){
                    //board[seedY][seedX] = Integer.parseInt(""+fileString.charAt(seedX));

                    //takes the unicode character for "0" and subtracts the numerical value of the cell selected. if subtracted from itself, it gives 0, and if subtracted from "1", gives 1.
                    board[seedY][seedX]=fileString.charAt(seedX)-'0';
                }
            }
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
    //method that gives each cell a random value between 0 and 1
    void randomPopulate(){
        for(int randomY = 0; randomY<size; randomY++){
            for(int randomX = 0; randomX<size; randomX++){
                board[randomY][randomX] = (rand.nextInt(2))*(rand.nextInt(2));
            }
        }
    }
    
    //method that prints out entire board
    void printBoard(){
        for(int yModifier= 0; yModifier<size; yModifier++){
                    for(int xModifier = 0; xModifier<size; xModifier++){
                        System.out.print(board[yModifier][xModifier] + " ");
                    }
                    System.out.println();
                }
    }
    
    //populates entire board with 0s and then prints with labelled left side
    void manualPopulate(){
        for(int yModifier= 0; yModifier<size; yModifier++){
            for(int xModifier = 0; xModifier<size; xModifier++){
                board[yModifier][xModifier] = 0;
            }
        }
        for(int yModifier= 0; yModifier<size; yModifier++){
            System.out.print((yModifier+1)+"\t");
            for(int xModifier = 0; xModifier<size; xModifier++){
                System.out.print(board[yModifier][xModifier] + " ");
            }
            System.out.println();
        }
    }
    
    //method that ensures code will not crash if user accidentally inputs a value that is not a number
    int removeChar(String stringInput){
        //removes all values that aren't between 0-9
        String numberOnly = stringInput.replaceAll("[^0-9]","");
        if(numberOnly.equals("")){
            //if there were no valid integers within the string, returns 20 as default safe number
            System.out.println("Invalid input. setting to default of 20.");
            return 20;
        }
        //turns new clean string into an integer
        int numberValue = Integer.parseInt(numberOnly);
        if(numberValue == 0 || numberValue >= 2147483647){
            //if value is invalid (larger than the largest integer stored in an int variable, (>=2147483647))/ <0, returns safe number of 20
            System.out.println("not an acceptable integer. Returning 20.");
            return 20;
        }
        //otherwise, returns valid value
        return numberValue;
    }
    
    //method that checks every cell surrounding a cell, adds them to a total.
    void gameRules(){
        int totalNeighbors = 0;
        //checks the surrounding cells and adds all living cells to the total to apply extra rules to.
        for(int yMod = -1; yMod<2;yMod++){
            for(int xMod = -1; xMod<2; xMod++ ){
                if( x+xMod >= 0 && y+yMod >= 0 && x+xMod < size && y+yMod < size){
                    totalNeighbors += board[y+yMod][x+xMod];
                }
            }
        }
        //subtracts self from total
        totalNeighbors -= board[y][x];
        //now with value, applies true rules
        applyRules(totalNeighbors);
    }

    void applyRules(int totalNeighbors){
        //method takes the totalNeighbors values and uses them to decide if cell is living or dead.
        
        //if cell is alive and there is <2 neighbours, the cell dies of underpopulation
        //if cell is alive and between 2-3 neighbours, the cell lives on to the next generation
        //if cell is alive and there is >3 neighbours, the cell dies of overpopulation
        //if cell is dead and there is exactly 3 neighbours, the cell comes back to life due to reproduction
        //cell remains the same if no other rules apply.
        
        if(board[y][x] == 1){
            if(totalNeighbors<2){
                temp[y][x] = 0;
            }
            if(totalNeighbors>=2 && totalNeighbors<=3){
                temp[y][x] = 1;
            }
            if(totalNeighbors>3){
                temp[y][x] = 0;
            }
        }else if(board[y][x] == 0){
            if(totalNeighbors == 3){
                temp[y][x] = 1;
            }
        }else{
            temp[y][x] = board[y][x];
        }
    }
}