
/**
 * Main game function for CGOL
 *
 * Joshua Toumu'a
 * 30/05/22
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
public class MainGame
{
    Random rand = new Random();

    public int size = 20;

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
    // {0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0},
    // {0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0},
    // {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
    // {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
    // {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
    // {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
    // {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
    // {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
    // {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}};
    public int generationMax = 5;

    int generationCount = 0;
    int y = 0;
    int x = 0;
    public String livingCell = "J";
    public String deadCell = " ";
    int timeDelay = 1000;
    int[][] board;
    int[][] temp;
    public MainGame()
    {
        System.out.print('\u000c');

        menuInput();

        while(generationCount<generationMax){
            System.out.print('\u000c');
            temp = new int[size][size];
            checkCells();
            board = temp;
            try{
                TimeUnit.MILLISECONDS.sleep(timeDelay);
            }catch(Exception e){

            }
            generationCount++;
        }
    }

    void checkCells(){
        for(y = 0; y<size; y++){
            for(x = 0; x<size; x++){
                gameRules();
                if(board[y][x] == 1){
                    System.out.print(livingCell+" ");
                }else{
                    System.out.print(deadCell+" ");
                }

            }
            System.out.println();
        }
    }

    void menuInput(){
        Scanner keyboard = new Scanner(System.in);
        String keyInput;
        boolean selectionScreen = false;

        System.out.println("Would you like to load a seed? type yes/no");
        keyInput = keyboard.nextLine().toLowerCase();
        if(keyInput.equalsIgnoreCase("yes")){
            board = new int[20][20];
            temp = new int[20][20];
            trySeedFile();
        }else{
            System.out.println("How big do you want the grid?(max 2147483647)");
            keyInput = keyboard.nextLine().toLowerCase();
            size = removeChar(keyInput);

            board = new int[size][size];
            temp = new int[size][size];
            randomPopulate();
            System.out.println("do you want to manually edit the seed? type yes if so.");

            keyInput = keyboard.nextLine();

            if (keyInput.equalsIgnoreCase("yes")){
                selectionScreen = true;
            }
        }
        while (selectionScreen == true){
            for(int yModifer= 0; yModifer<size; yModifer++){
                for(int xModifer = 0; xModifer<size; xModifer++){
                    System.out.print(board [yModifer] [xModifer] + "  ");
                }
                System.out.println();
            }
            System.out.println("Please select row: ");
            int rowSelection = keyboard.nextInt();
            System.out.println("Please select column: ");
            int columnSelection = keyboard.nextInt();

            if (board[rowSelection][columnSelection] == 0){
                board[rowSelection][columnSelection] = 1;
            }
            else{
                board[rowSelection][columnSelection] = 0;
            }

            System.out.println();

            selectionScreen=false;
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

    void trySeedFile(){
        File seed = new File("CGOLSeed.txt");
        
        try{
            Scanner fileReader = new Scanner(seed);
            for (int seedY = 0; seedY<size; seedY++){
                String fileString  = fileReader.nextLine();
                for (int seedX = 0; seedX<size; seedX++){
                    //board[seedY][seedX] = Integer.parseInt(""+fileString.charAt(seedX));
                    board[seedY][seedX]=fileString.charAt(seedX)-'0';
                }
            }
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    void randomPopulate(){
        for(int randomY = 0; randomY<size; randomY++){
            for(int randomX = 0; randomX<size; randomX++){
                board[randomY][randomX] = (rand.nextInt(2))*(rand.nextInt(2));
            }
        }
    }

    int removeChar(String stringInput){
        String numberOnly = stringInput.replaceAll("[^0-9]","");
        int numberValue = Integer.parseInt(numberOnly);
        if(numberValue > 0){
            return 20;
        }else{
            return numberValue;
        }
    }

    void gameRules()
    {
        int totalNeighbors = 0;
        for(int yMod = -1; yMod<2;yMod++){
            for(int xMod = -1; xMod<2; xMod++ ){
                if( x+xMod >= 0 && y+yMod >= 0 && x+xMod < size && y+yMod < size){
                    totalNeighbors += board[y+yMod][x+xMod];
                }
            }
        }
        totalNeighbors -= board[y][x];
        applyRules(totalNeighbors);
    }

    void applyRules(int totalNeighbors){
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