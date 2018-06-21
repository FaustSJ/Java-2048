import java.util.Random;

// Represents the internal state of a game of 2048 and allows various
// operations of game moves as methods. Uses TwoNTiles and DenseBoard
// to implement the game.
public class Game2048{

  private Board board;
  private int score;
  private Random random;

  // Create a game with a DenseBoard with the given number of rows and
  // columns. Initialize the game's internal random number generator
  // to the given seed.
  public Game2048(int rows, int cols, int seed) {
    this(rows,cols,seed,false);
  }

  // Create a game with a DenseBoard which has the given arrangement
  // of tiles. Initialize the game's internal random number generator
  // to the given seed.
  public Game2048(Tile tiles[][], int seed) {
    this(tiles,seed,false);
  }

  // REQUIRED: The final parameter indicates whether a SparseBoard
  // (true) or a DenseBoard (false) should be used during the game
  // 
  // Create a game with a DenseBoard with the given number of rows and
  // columns. Initialize the game's internal random number generator
  // to the given seed.
  public Game2048(int rows, int cols, int seed, boolean useSparse) {
  	  if(useSparse)
  	  	  this.board = new SparseBoard(rows, cols);
  	  else
  	  	  this.board = new DenseBoard(rows, cols);
  	  this.random = new Random(seed);
  	  
  }

  // REQUIRED: The final parameter indicates whether a SparseBoard
  // (true) or a DenseBoard (false) should be used during the game
  // 
  // Create a game with a DenseBoard which has the given arrangement
  // of tiles. Initialize the game's internal random number generator
  // to the given seed.
  public Game2048(Tile tiles[][], int seed, boolean useSparse) {
  	  if(useSparse)
  	  	  this.board = new SparseBoard(tiles);
  	  else
  	  	  this.board = new DenseBoard(tiles);
  	  this.random = new Random(seed);
  }

  // Return the number of rows in the Game
  public int getRows(){
    return this.board.getRows();
  }

  // Return the number of columns in the Game
  public int getCols(){
    return this.board.getCols();
  }

  // Return the current score of the game.
  public int getScore(){
    return this.score;
  }

  // Return a string representation of the board; useful for text UIs
  // like PlayText2048
  public String boardString(){
    return board.toString();
  }

  // Return the tile at a given position in the grid; throws an
  // exception if the request is out of bounds. Potentially useful for
  // more complex UIs which want to lay out tiles individually.
  public Tile tileAt(int i, int j){
    return board.tileAt(i,j);
  }

  // Shift tiles left and update the score
  public void shiftLeft(){
    this.score += board.shiftLeft();
  }
  // Shift tiles right and update the score
  public void shiftRight(){
    this.score += board.shiftRight();
  }
  // Shift tiles up and update the score
  public void shiftUp(){
    this.score += board.shiftUp();
  }
  // Shift tiles down and update the score
  public void shiftDown(){
    this.score += board.shiftDown();
  }

  // Generate and return a random tile according to the probability
  // distribution. 
  //    70% 2-tile
  //    25% 4-tile
  //     5% 8-tile
  // Use the internal random number generator for the game.
  public Tile getRandomTile(){
    double rand = this.random.nextDouble();
    //    System.out.printf("\n\nMY DOUBLE IS %f\n",rand);
    if(rand <= 0.70){
      return new TwoNTile(2);
    }
    else if(rand <= 0.95){
      return new TwoNTile(4);
    }
    else{
      return new TwoNTile(8);
    }
  }

  // If the game board has F>0 free spaces, return a random integer
  // between 0 and F-1.  If there are no free spaces, throw an
  // exception.
  public int randomFreeLocation(){
    int freeSpaces = this.board.getFreeSpaceCount();
    return this.random.nextInt(freeSpaces);
  }

  // Add a random tile to a random free position. To adhere to the
  // automated tests, the order of calls to random methods MUST BE
  // 
  // 1. Generate a random location using randomFreeLocation()
  // 2. Generate a random tile using getRandomTile()
  // 3. Add the tile to board using one of its methods
  public void addRandomTile(){
    int freeSpaces = this.board.getFreeSpaceCount();
    if(freeSpaces == 0){
      return;
    }
    int location = randomFreeLocation();
    Tile tile = getRandomTile();
    board.addTileAtFreeSpace(location,tile);
  }

  // REQUIRED: Add a brick at a random location.
  // 
  // 1. Generate a random location using randomFreeLocation()
  // 2. Add the Brick to board using one of its methods
  public void addRandomBrick(){
    int freeSpaces = this.board.getFreeSpaceCount();
    if(freeSpaces == 0){
      return;
    }
    int location = randomFreeLocation();
    Tile tile = new Brick();
    board.addTileAtFreeSpace(location,tile);
  }

  // Returns true if the game over conditions are met (no free spaces,
  // no merge possible) and false otherwise
  public boolean isGameOver(){
    int freeSpace = board.getFreeSpaceCount();
    if(freeSpace > 0){
      return false;              // Board not full, can continue playing
    }
    return !board.mergePossible(); // Full board may still be able to merge
  }

  // true if the last shift moved any tiles and false otherwise
  public boolean lastShiftMovedTiles(){
    return board.lastShiftMovedTiles();
  }

  // Optional: pretty print some representation of the game. No
  // specific format is required, used mainly for debugging purposes.
  public String toString(){
    return String.format("Score: %d\n%s",
                         getScore(), board);
  }

}

