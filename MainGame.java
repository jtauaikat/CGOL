
/**
 * Write a description of class MainGame here.
 *
 * Joshua Toumu'a
 * 09/05/22
 */
import java.util.Random;
import java.util.Arrays;
public class MainGame
{
    Random rand = new Random();
    
    public int SIZE = 20;
    public int temp[][] = new int[SIZE][SIZE];
    //public int board[][] = new int[SIZE][SIZE];
    public int board[][] = 
        {{0,0,1,1,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0},
            {0,0,1,1,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0},
            {0,0,1,1,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0},
            {0,0,1,1,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0}};
    public int generations = 5;
    public int totalNeighbors = 0;
    public int toggle=0;
    public MainGame()
    {
        System.out.print('\u000c');
        //for(int y = 0; y<SIZE; y++){
        //  for(int x = 0; x<SIZE; x++){
        //      board[y][x] = (rand.nextInt(2))*(rand.nextInt(2));
        //  }
        //}
        System.out.println("------------------------------------");
        while(toggle<5){
            for(int y = 0; y<SIZE; y++){
                for(int x = 0; x<SIZE; x++){
                    gameRules(x, y);
                    System.out.print(board[y][x] + " ");
                }
                System.out.println();
            }
            arrayClone();
            System.out.println("------------------------------------");
            toggle++;
        }
    }

    void gameRules(int xIn,int yIn)
    {
        for(int yMod = -1; yMod<2;yMod++){
            for(int xMod = -1; xMod<2; xMod++ ){
                if(xMod+yMod != 0 &&  xIn+xMod >= 0 && yIn+yMod >= 0 && xIn+xMod < SIZE && yIn+yMod < SIZE){
                    totalNeighbors += board[yIn+yMod][xIn+xMod];
                }
            }
        }
        if(board[yIn][xIn] == 1){
            if(totalNeighbors<2){
                temp[yIn][xIn] = 0;
            }
            else if(totalNeighbors>=2 && totalNeighbors<=3){
                temp[yIn][xIn] = 1;
            }
            if(totalNeighbors>3){
                temp[yIn][xIn] = 0;
            }
        }else if(board[yIn][xIn] == 0){
            if(totalNeighbors == 3){
                temp[yIn][xIn] = 1;
            }else{
                temp[yIn][xIn] = 0;
            }
        }
    }

    void arrayClone()
    {
        for(int yClone = 0; yClone<SIZE; yClone++){
            for(int xClone = 0; xClone<SIZE; xClone++){
                board[yClone][xClone] = temp[yClone][xClone];
            }
        }
    }
}
