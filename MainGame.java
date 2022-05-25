
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
    public MainGame()
    {
        System.out.print('\u000c');
        Scanner keyboard = new Scanner(System.in);

        System.out.println("How big do you want the grid?(max 2147483647)");
        size = keyboard.nextInt();

        keyboard.nextLine();

        System.out.println("How many generations?(max 2147483647)");
        generationMax = keyboard.nextInt();
        keyboard.nextLine();

        System.out.println("How many milliseconds between each generation?(max 2147483647)");
        timeDelay = keyboard.nextInt();
        keyboard.nextLine();

        System.out.println("What character for living cells?");
        livingCell = keyboard.nextLine();

        System.out.println("What character for dead cells?");
        deadCell = keyboard.nextLine();

        int[][]board = new int[size][size];
        int[][]temp = new int[size][size];

        for(int randomY = 0; randomY<size; randomY++){
            for(int randomX = 0; randomX<size; randomX++){
                board[randomY][randomX] = (rand.nextInt(2))*(rand.nextInt(2));
            }
        }

        //System.out.println("---------------------------------------");

        while(generationCount<generationMax){
            System.out.print('\u000c');
            temp = new int[size][size];
            for(y = 0; y<size; y++){
                for(x = 0; x<size; x++){
                    gameRules(board, temp);
                    if(board[y][x] == 1){
                        System.out.print(livingCell+" ");
                    }else{
                        System.out.print(deadCell+" ");
                    }

                }
                System.out.println();
            }

            board = temp;
            //System.out.println("---------------------------------------");

            try{
                TimeUnit.MILLISECONDS.sleep(timeDelay);
            }catch(Exception e){

            }
            
            generationCount++;

        }
    }

    void gameRules(int[][] board, int[][] temp)
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