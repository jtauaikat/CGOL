
/**
 * Write a description of class MainGame here.
 *
 * Joshua Toumu'a
 * 09/05/22
 */
public class MainGame
{
    public int x;
    public int y;
    public int temp[][] = new int[20][20];
    public int board[][] = new int[20][20];
    public MainGame()
    {
        
        
    }
    
    void gameRules()
    {
        int verticalMod = -1;
        int horizontalMod = -1;
        int totalNeighbors = 0;
        for(int vertical = 0; vertical<3;vertical++){
            for(int horizontal = 0; vertical<3; vertical++){
                if(board[horizontal+horizontalMod][vertical+verticalMod] == 0 
                ){
                totalNeighbors += board[horizontal+horizontalMod][vertical+verticalMod];
                
                }
            }
        }
    }
}
