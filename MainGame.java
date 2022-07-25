
/**
 * Main game function for CGOL
 *
 * Joshua Toumu'a
 * 26/07/22
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
    //testing board with preset stable and oscillator to test CGOL rules
    // public int board[][] = 
    // {{1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1},
    // {1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1},
    // {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
    // {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
    // {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
    // {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
    // {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
    // {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
    // {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
    // {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
    // {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
    // {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
    // {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
    // {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
    // {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
    // {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
    // {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
    // {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0},
    // {1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0},
    // {1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0}};

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
    //
    Scanner keyboard = new Scanner(System.in);
    String keyInput;
    
    //
    boolean boardPopulateLoop = true;
    boolean selectionScreen = false;
    
    public MainGame(){
        //clears canvas
        System.out.print('\u000c');
        //runs menu system method
        menuInput();
        //repeats for set amount of generations
        while(mainLoop){
            while(generationCount<generationMax){
                //clears canvas and temp board
                System.out.print('\u000c');
                temp = new int[size][size];
                //runs program that applies CGOL rules to every cell on temp board
                checkCells();
                //applies changes to the real board
                //this is done on a temp board in order to prevent any interference from changes from previous cells to change the states of other cells
                board = temp;
                //creates a delay of a set amount of milliseconds
                try{
                    TimeUnit.MILLISECONDS.sleep(timeDelay);
                }catch(Exception e){

                }
                //repeats to next generation
                generationCount++;
            }
            endMenu();
        }
    }
    
    //method for menu input code
    void menuInput(){
        System.out.println("Would you like to load a 𝐬𝐞𝐞𝐝, 𝐫𝐚𝐧𝐝𝐨𝐦 board or 𝐞𝐝𝐢𝐭 manually?");
        boardPopulateLoop = true;
        while(boardPopulateLoop){
            keyInput = keyboard.nextLine().toLowerCase();
            switch(keyInput){
                case "seed":
                //runs txt file loader
                trySeedFile();
                boardPopulateLoop = false;
                seedPopulate();
                break;

                case "random":
                //if user inputs anything else, runs random input code, allows user customization
                System.out.println("How big do you want the grid?");
                keyInput = keyboard.nextLine().toLowerCase();
                size = removeChar(keyInput);
                board = new int[size][size];
                temp = new int[size][size];
                randomPopulate();
                boardPopulateLoop = false;
                break;

                case "edit":
                //if user inputs anything else, runs random input code, allows user customization
                System.out.println("How big do you want the grid?");
                keyInput = keyboard.nextLine().toLowerCase();
                size = removeChar(keyInput);
                board = new int[size][size];
                temp = new int[size][size];
                manualPopulate();
                boardPopulateLoop = false;
                selectionScreen = true;
                manualEdit();
                break;

                default:
                //spits out invalid input code
                System.out.println("invalid command. please enter \'seed\', \'random\' or \'edit\'.");
                break;
            }
        }
        //loop for manually editing the board
        System.out.println("How many generations?");
        keyInput = keyboard.nextLine();
        generationMax = removeChar(keyInput);

        System.out.println("How many milliseconds between each generation?");
        keyInput = keyboard.nextLine();
        timeDelay = removeChar(keyInput);
        
        System.out.println("Would you like to use custom characters?");
        keyInput = keyboard.nextLine().toLowerCase();
        
        if(keyInput.equals("yes")){
            System.out.println("What character for living cells?");
            livingCell = keyboard.nextLine();

            System.out.println("What character for dead cells?");
            deadCell = keyboard.nextLine();
        }
    }
    
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

            keyInput = keyboard.nextLine();
            if(keyInput.equals("yes")){
                selectionScreen=false;
            }
        }
    }

    void endMenu(){
        if(generationCount >= generationMax){
            System.out.println("Would you like to quit, continue, or restart the simulation?");
            String keyInput = keyboard.nextLine().toLowerCase();
            if(keyInput.equalsIgnoreCase("quit")){
                mainLoop = false;
                return;
            }else if(keyInput.equalsIgnoreCase("continue")){
                System.out.println("How many generations?(max 2147483647)");
                keyInput = keyboard.nextLine();
                generationMax = removeChar(keyInput);
                generationCount = 0;
            }else if(keyInput.equalsIgnoreCase("restart")){
                generationCount = 0;
                System.out.print('\u000c');
                menuInput();
            }else{
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
            
            board = new int[size][size];
            temp = new int[size][size];
            for (int seedY = 0; seedY<size-1; seedY++){
                String fileString  = fileReader.nextLine();
                for (int seedX = 0; seedX<size; seedX++){
                    //board[seedY][seedX] = Integer.parseInt(""+fileString.charAt(seedX));

                    //takes the unicode character for "0" and subtracts the numerical value of the cell selected. if subtracted from itself, it gives 0, and if subtracted from "1", gives 1.
                    board[seedY][seedX]=fileString.charAt(seedX)-'0';
                }
                //reads next line
                
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
    void seedPopulate(){
        for(int yModifier= 0; yModifier<size; yModifier++){
                    for(int xModifier = 0; xModifier<size; xModifier++){
                        System.out.print(board[yModifier][xModifier] + " ");
                    }
                    System.out.println();
                }
    }
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
            //if value is invalid/lesser than 0, returns safe number of 20
            System.out.println("Invalid input. setting to default of 20.");
            return 20;
        }
        int numberValue = Integer.parseInt(numberOnly);
        if(numberValue == 0 || numberValue >= 2147483647){
            System.out.println("not an acceptable integer. Returning 20.");
            return 20;
        }
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