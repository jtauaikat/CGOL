
/**
 * Write a description of class MainGame here.
 *
 * Joshua Toumu'a
 * 09/05/22
 */
import java.util.Random;

public class MainGame
{
    Random rand = new Random();
    public int x = 0;
    public int y = 0;
    public int ROWS = 20;
    public int COLUMNS = 20;
    public int temp[][] = new int[COLUMNS][ROWS];
    public int board[][] = new int[COLUMNS][ROWS];
    public int totalNeighbors = 0;
    public int toggle=0;
    public MainGame()
    {
        System.out.println('\u000c');
        for(int y = 0; y<COLUMNS; y++){
            for(int x = 0; x<ROWS; x++){
                board[y][x] = (rand.nextInt(2))*(rand.nextInt(2));
            }
        }
        System.out.println("------------------------------------");
        while(toggle<5){
        for(y = 0; y<COLUMNS; y++){
            for(x = 0; x<ROWS; x++){
                gameRules();
                System.out.print(board[y][x] + " ");
            }
            System.out.println();
        }
        arrayClone();
        System.out.println("------------------------------------");
        toggle++;
    }
    }

    void gameRules()
    {
        for(int yMod = -1; yMod<2;yMod++){
            for(int xMod = -1; xMod<2; xMod++ ){
                if(x+xMod != x && y+yMod != y &&  x+xMod >= 0 && y+yMod >= 0 && x+xMod <= ROWS && y+yMod <= COLUMNS){
                    totalNeighbors += board[y+yMod][x+xMod];
                }
            }
        }
        if(board[y][x] == 1){
            if(totalNeighbors<2){
                temp[y][x] = 0;
            }
            else if(totalNeighbors>=2 || totalNeighbors<=3){
                temp[y][x] = 1;
            }
            if(totalNeighbors>3){
                temp[y][x] = 0;
            }
        }else if(board[y][x] == 0){
            if(totalNeighbors == 3){
                temp[y][x] = 1;
            }else{
                temp[y][x] = 0;
            }
        }
    }
    
    void arrayClone()
    {
        for(int yClone = 0; yClone<COLUMNS; yClone++){
            for(int xClone = 0; xClone<ROWS; xClone++){
                board[yClone][xClone] = temp[yClone][xClone];
            }
        }
    }
}
