import java.util.Scanner;

// Class to play a single game of 2048 with bricks
public class PlayText2048 {

  // Play a game of 2048 of the given size. Allows one to specify the
  // a number of random bricks, whether to use a sparse/dense board
  // and a random seed.
  // 
  // usage: java PlayText2048 rows cols bricks {sparse|dense} [random-seed]
  //   rows/cols: the size of the board [int]
  //   bricks: the number of immovable bricks to add to the board, 0 for none [int]
  //   {sparse|dense}: use either a sparse or dense board ["sparse" or "dense"]
  //   random-seed: used to initialize the random number generator [int]
  public static void main(String args[]){
    if(args.length < 4){
      System.out.println("usage: java PlayText2048 rows cols bricks {sparse|dense} [random-seed]");
      System.out.println("  rows/cols: the size of the board [int]");
      System.out.println("  bricks: the number of immovable bricks to add to the board, 0 for none [int]");
      System.out.println("  {sparse|dense}: use either a sparse or dense board ['sparse' or 'dense']");
      System.out.println("  random-seed: used to initialize the random number generator [int]");
      return;
    }

    int rows = Integer.parseInt(args[0]);
    int cols = Integer.parseInt(args[1]);
    int brickCount = Integer.parseInt(args[2]);
    String boardStyle = args[3];
    boolean useSparse = false;
    if(boardStyle.equals("sparse")){ useSparse = true; }
    else if(boardStyle.equals("dense")){ useSparse = false; }
    else{ throw new RuntimeException(String.format("3rd arg '%s' must be either 'sparse' or 'dense'",boardStyle)); }
    int seed = 13579;           // Default random number
    if(args.length >= 5){
      seed = Integer.parseInt(args[4]);
    }

    System.out.println("Instructions");
    System.out.println("------------");
    System.out.println("Enter moves as l r u d q for");
    System.out.println("l: shift left");
    System.out.println("r: shift right");
    System.out.println("u: shift up");
    System.out.println("d: shift down");
    System.out.println("q: quit game");
    System.out.println();

    Game2048 game = new Game2048(rows,cols,seed,useSparse);
    // Add bricks to the game
    for(int i=0; i<brickCount; i++){
      game.addRandomBrick();
    }
    // Default is 25% of board has tiles, round down
    int initialTiles = rows*cols/4-brickCount;
    initialTiles = initialTiles==0 ? 1 : initialTiles;
    for(int i=0; i<initialTiles; i++){
      game.addRandomTile();
    }

    Scanner stdin = new Scanner(System.in);
    while(!game.isGameOver()){
      System.out.printf("Score: %d\n",game.getScore());
      System.out.println(game.boardString());
      System.out.printf("Move: ");
      String input = stdin.next();

      if(input.equals("q")){ 
        break; 
      }
      else if(input.equals("l")){
        game.shiftLeft();
      }
      else if(input.equals("r")){
        game.shiftRight();
      }
      else if(input.equals("u")){
        game.shiftUp();
      }
      else if(input.equals("d")){
        game.shiftDown();
      }

      System.out.println(input);

      if(game.lastShiftMovedTiles()){
        game.addRandomTile();
      }
    }
    System.out.println(game);
    System.out.printf("Game Over! Final Score: %d\n",game.getScore());
  }
}
