
/**
 * Main game function for CGOL
 *
 * Joshua Toumu'a
 * 15/06/22
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
    Random rand = new Random();
    //establishes randomness for random board generation

    int size = 20;
    //default size of the board is 20x20

    //testing board with preset stable and oscillator to test CGOL rules
    // public int board[][] = 
    // {{0,0,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
    // {0,0,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
    // {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
    // {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
    // {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
    // {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
    // {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
    // {0,0,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
    // {0,0,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
    // {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
    // {0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0},
    // {0,0,0,0,0,0,0,1,1,1,0,0,0,0,0,0,0,0,0,0},
    // {0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0},
    // {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
    // {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
    // {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
    // {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
    // {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
    // {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
    // {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}};

    //creates generation counter with default of 5 if any errors occur
    int generationMax = 5;
    int generationCount = 0;

    //x and y is used to apply CGOL rules for every cell on the board
    int y = 0;
    int x = 0;

    //what the living and dead cells will look like on the console
    String livingCell = "X";
    String deadCell = " ";

    //how many milliseconds between each generation
    int timeDelay = 1000;

    //defines board and temp boards
    int[][] board;
    int[][] temp;
    public MainGame(){
        //clears canvas
        System.out.print('\u000c');

        //runs menu system method
        menuInput();
        boolean mainLoop = true;

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

    void endMenu(){
        Scanner keyboard = new Scanner(System.in);
        if(generationCount >= generationMax){
            System.out.println("Would you like to quit, continue, or restart the simulation?");
            String keyInput = keyboard.nextLine().toLowerCase();
            if(keyInput.equalsIgnoreCase("quit")){
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

    //method for menu input code
    void menuInput(){
        //sets up the keyboard scanner
        Scanner keyboard = new Scanner(System.in);
        String keyInput;
        boolean selectionScreen = false;

        System.out.println("Would you like to load a seed? type yes/no");
        keyInput = keyboard.nextLine().toLowerCase();
        if(keyInput.equalsIgnoreCase("yes")){
            //if user inputs yes, sets board size as 20
            board = new int[20][20];
            temp = new int[20][20];
            //runs txt file loader
            trySeedFile();
        }else{
            //if user inputs anything else, runs random input code, allows user customization
            System.out.println("How big do you want the grid?(max 2147483647)");
            keyInput = keyboard.nextLine().toLowerCase();
            size = removeChar(keyInput);

            board = new int[size][size];
            temp = new int[size][size];
            //random population method

            //old code for trialling inputs, checks if user wants to manually edit the board
            System.out.println("do you want to manually edit the seed? type yes if so.");

            keyInput = keyboard.nextLine();

            if (keyInput.equalsIgnoreCase("yes")){
                for(int yModifier= 0; yModifier<size; yModifier++){
                    for(int xModifier = 0; xModifier<size; xModifier++){
                        board[yModifier][xModifier] = 0;
                    }
                }
                selectionScreen = true;
                //System.out.print("\t");
                //for(int xModifier = 0; xModifier<size; xModifier++){
                  //  System.out.print((xModifier+1)+" ");
                //}
                //System.out.println();
                for(int yModifier= 0; yModifier<size; yModifier++){
                    System.out.print((yModifier+1)+"\t");
                    for(int xModifier = 0; xModifier<size; xModifier++){
                        System.out.print(board[yModifier][xModifier] + " ");
                    }
                    System.out.println();
                }
            }else{
                randomPopulate();
            }
        }
        //loop for manually editing the board
        while (selectionScreen == true){

            //allows user to input x and y coordinates for the cell you want to replace
            System.out.println("Please select column: ");
            int columnSelection = removeChar(keyboard.nextLine())-1;
            System.out.println("Please select row: ");
            int rowSelection = removeChar(keyboard.nextLine())-1;
            //keyboard.nextLine();

            //switches cells around, if living, switch to dead and vice versa.
            if(columnSelection >=0 && columnSelection < size && rowSelection >= 0 && rowSelection < size){
                if (board[columnSelection][rowSelection] == 1){
                    board[columnSelection][rowSelection] = 0;
                }
                else{
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

        System.out.println("How many generations?(max 2147483647)");
        keyInput = keyboard.nextLine();
        generationMax = removeChar(keyInput);

        System.out.println("How many milliseconds between each generation?(max 2147483647)");
        keyInput = keyboard.nextLine();
        timeDelay = removeChar(keyInput);

        System.out.println("What character for living cells?");
        livingCell = keyboard.nextLine();

        System.out.println("What character for dead cells?");
        deadCell = keyboard.nextLine();

    }

    //method for loading in .txt file
    void trySeedFile(){
        //defines file
        File seed = new File("CGOLSeed.txt");

        try{
            //creates scanner
            Scanner fileReader = new Scanner(seed);
            //code that takes the 20x20 grid in character by character and places into array
            for (int seedY = 0; seedY<size; seedY++){
                //reads next line
                String fileString  = fileReader.nextLine();
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

    //method that ensures code will not crash if user accidentally inputs a value that is not a number
    int removeChar(String stringInput){
        //removes all values that aren't between 0-9
        String numberOnly = stringInput.replaceAll("[^0-9]","");
        int numberValue = Integer.parseInt(numberOnly);
        if(numberValue <= 0){
            //if value is invalid/lesser than 0, returns safe number of 20
            return 20;
        }else{
            //returns value without characters
            return numberValue;
        }
    }

    //method that checks every cell surrounding a cell, adds them to a total.
    void gameRules()
    {
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
        //if cell is alive
        if(board[y][x] == 1){
            //if there are less than 2 cells, dies of underpopulation
            if(totalNeighbors<2){
                temp[y][x] = 0;
            }
            //if there between 2-3 neighbors, cell lives on to the next generation
            if(totalNeighbors>=2 && totalNeighbors<=3){
                temp[y][x] = 1;
            }
            //if there are more than 3 cells, cell dies of overpopulation
            if(totalNeighbors>3){
                temp[y][x] = 0;
            }
        }else if(board[y][x] == 0){
            //if cell is dead
            //if there are exactly 3 cells, cell comes back to life from repopulation
            if(totalNeighbors == 3){
                temp[y][x] = 1;
            }
        }else{
            //cell remains the same if no other rules apply.
            temp[y][x] = board[y][x];
        }
    }
}