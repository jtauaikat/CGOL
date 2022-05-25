
/**
 * Main game function for CGOL
 *
 * Joshua Toumu'a
 * 23/05/22
 */
import java.util.Random;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;
import java.util.Scanner;
import java.io.File;
import java.io.IOException;

public class MainGame
{
    Random rand = new Random();

    int size = 20;

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
    int generationMax = 5;
    int generationCount = 0;
    int y = 0;
    int x = 0;
    String livingCell = "J";
    String deadCell = " ";
    int timeDelay = 1000;
    boolean importSeed =false;
    File CGOLSeed = new File("CGOLSeed.txt");
    Scanner keyboard = new Scanner(System.in);

    int[][]board = new int[size][size];
    int[][]temp = new int[size][size];
    
    int menuCount = 0;

    public MainGame()
    {
        System.out.print('\u000c');
            String[] menuText = {"Do you want to import from CGOLSeed.txt? (max 20x20 grid)","How big do you want the grid?(max 2147483647)","How many generations?(max 2147483647)","How many milliseconds between each generation?(max 2147483647)",
                "What character for living cells?", "What character for dead cells?"};
                
            System.out.println(menuText[1]);
            size = keyboard.nextInt();

           
           System.out.println(menuText[2]);
           generationMax = keyboard.nextInt();
           generationCount = 0;
           
           System.out.println(menuText[3]);
           timeDelay = keyboard.nextInt();
           
           keyboard.nextLine();
           System.out.println(menuText[4]);
           livingCell = keyboard.nextLine();
           
           keyboard.nextLine();
           System.out.println(menuText[5]);
           deadCell = keyboard.nextLine();
        
           randomPopulate();
           
        int keyInputCounter = 0;
        while(generationCount<generationMax){
            temp = new int[size][size];
            if(generationCount==generationMax-1){
                System.out.println("How many generations?(max 2147483647)");
                generationMax = keyboard.nextInt();
                generationCount = 0;
            }else{
                System.out.print('\u000c');
            }
            for(y = 0; y<size; y++){
                for(x = 0; x<size; x++){
                    checkNeighbors();
                    if(board[y][x] == 1){
                        System.out.print(livingCell+" ");
                    }else{
                        System.out.print(deadCell+" ");
                    }

                }
                System.out.println();
            }
            board = temp;
            try{
                TimeUnit.MILLISECONDS.sleep(timeDelay);
            }catch(Exception e){

            }
            generationCount++;
        }
    }

    void randomPopulate(){
        for(int randomY = 0; randomY<size; randomY++){
            for(int randomX = 0; randomX<size; randomX++){
                board[randomY][randomX] = (rand.nextInt(2))*(rand.nextInt(2));
            }
        }
    }

    void userInput( int menuCount)
    {
        String[] menuText = {"Do you want to import from CGOLSeed.txt? (max 20x20 grid)","How big do you want the grid?(max 2147483647)","How many generations?(max 2147483647)","How many milliseconds between each generation?(max 2147483647)",
                "What character for living cells?", "What character for dead cells?"};
        boolean randomPopulateToggle = false;
            System.out.print('\u000c');
            System.out.println(menuText[menuCount]);
            String stringInput = keyboard.nextLine();
            switch(menuCount){
                case 0:
                if(stringInput.equalsIgnoreCase("yes")){

                }else if(stringInput.equalsIgnoreCase("no")){
                    randomPopulateToggle = true;
                    menuCount++;
                }else{
                    System.out.println("Invalid Input. Input \"yes\" or \"no\"");
                }
                return;
                case 1:
                if (removeChar(stringInput) > 0 && removeChar(stringInput) < 2147483647){
                    size = removeChar(stringInput);
                }else{
                    size = 20;
                }
                if(randomPopulateToggle){
                    randomPopulate();
                }
                menuCount++;
                return;
                case 2:
                if (removeChar(stringInput) > 0 && removeChar(stringInput) < 2147483647){
                    generationMax = removeChar(stringInput);
                }else{
                    generationMax = 20;
                }
                menuCount++;
                return;
                case 3:
                if (removeChar(stringInput) > 0 && removeChar(stringInput) < 2147483647){
                    timeDelay = removeChar(stringInput);
                }else{
                    timeDelay = 20;
                }
                menuCount++;
                return;
                case 4:
                livingCell = stringInput;
                menuCount++;
                return;
                case 5:
                deadCell = stringInput;
                menuCount++;
                return;
            }
        

        // size = keyboard.nextInt();

        // keyboard.nextLine();

        // generationMax = keyboard.nextInt();
        // generationCount = 0;

        // timeDelay = keyboard.nextInt();
        // keyboard.nextLine();

        // livingCell = keyboard.nextLine();

        // deadCell = keyboard.nextLine();
    }

    int removeChar(String stringInput){
        String numberOnly = stringInput.replaceAll("[^0-9]","");
        int numberValue = Integer.parseInt(numberOnly);
        return numberValue;
    }

    void checkNeighbors()
    {
        int totalNeighbors = 0;
        for(int yMod = -1; yMod<2;yMod++){
            for(int xMod = -1; xMod<2; xMod++ ){
                if( x+xMod >= 0 && y+yMod >= 0 && x+xMod < size && y+yMod < size){
                    totalNeighbors += board[y+yMod][x+xMod];
                }
            }
        }
        applyRules(totalNeighbors);
    }

    void applyRules(int totalNeighbors){
        totalNeighbors -= board[y][x];
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
