
/**
 * Write a description of class MainGame here.
 *
 * Joshua Toumu'a
 * 09/05/22
 */
import java.util.Random;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;
public class MainGame
{
    Random rand = new Random();

    public int SIZE = 20;

    public int board[][] = 
           {{0,0,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
            {0,0,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
            {0,0,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
            {0,0,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}};
    public int generations = 5;
    public int[][]temp = new int[SIZE][SIZE];
    public int toggle=0;
    int y = 0;
    int x = 0;
    public MainGame()
    {
        System.out.print('\u000c');

        //System.out.println("---------------------------------------");
        while(toggle<5){
            System.out.print('\u000c');
            temp = new int[SIZE][SIZE];
            for(y = 0; y<SIZE; y++){
                for(x = 0; x<SIZE; x++){
                    gameRules();
                    System.out.print(board[y][x] + " ");
                }
                System.out.println();
            }
            board = temp;
            //System.out.println("---------------------------------------");
            toggle++;
            try{
                TimeUnit.SECONDS.sleep(1);
            }catch(Exception e){

            }
            
        }
    }

    void gameRules()
    {
        int totalNeighbors = 0;
        for(int yMod = -1; yMod<2;yMod++){

            
            for(int xMod = -1; xMod<2; xMod++ ){
                if( x+xMod >= 0 
                && y+yMod >= 0 && x+xMod < SIZE && y+yMod < SIZE){
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
