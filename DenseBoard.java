import java.util.*;
import java.lang.*;

// Tracks the positions of an arbitrary 2D grid of Tiles.  
public class DenseBoard extends Board {

  private Tile[][] gameBoard;	//the array of Tiles
  private int r;				//number of rows
  private int c;				//number of columns
  private int free;				//the number of free spaces
  private boolean shifted;		//tracks if the last move shifted tiles or not
  
  //Builds an empty Board of the specified size
  public DenseBoard(int rows, int cols)
  {
  	  this.r = rows;
  	  this.c = cols;
  	  this.gameBoard = new Tile[rows][cols];
  	  this.free = r*c;
  	  this.shifted = false;
  }

  //Builds a board that copies the 2D array of tiles provided 
  public DenseBoard(Tile t[][])
  {
  	  
  	  this.r = t.length;
  	  this.c = t[0].length;
  	  this.gameBoard = new Tile[this.r][this.c];
  	  this.free = 0;
  	  Tile temp;
  	  this.shifted = false;
  	  
  	  for(int row = 0; row<=(t.length-1); row++)
  	  {
  	  	  for(int col = 0; col<=(t[0].length-1); col++)
  	  	  {
  	  	  	  temp = t[row][col];
  	  	  	  this.gameBoard[row][col] = temp;
  	  	  	  if(t[row][col]==null)
  	  	  	  	  this.free++;
  	  	  }
  	  }
  	  
  }
  
//------------------------------------------------------------------------------
//------------------------------------------------------------------------------

  //Creates a distinct copy of the board including its internal tile
  //	positions and any other state
  public Board copy()
  {
  	  DenseBoard newBoard = new DenseBoard(this.gameBoard);
  	  newBoard.shifted = this.shifted;
  	  return newBoard;
  }

  // Return the number of rows in the Board
  public int getRows()
  {
  	  return this.r;
  }

  // Return the number of columns in the Board
  public int getCols()
  {
  	  return this.c;
  }

  // Return how many tiles are present in the board (non-empty spaces)
  public int getTileCount()
  {
  	  int tiles = (r*c)-this.free;
  	  return tiles;
  }

  // Return how many free spaces are in the board
  public int getFreeSpaceCount()
  {
  	  return this.free;
  }

  // Get the tile at a particular location.
  public Tile tileAt(int i, int j) throws RuntimeException
  {
  	  if((i>this.r)||(j>this.c))
  	  	  throw new RuntimeException("Out of bounds!");
  	  Tile temp = gameBoard[i][j];
  	  return temp;
  }

  // true if the last shift operation moved any tile; false otherwise
  public boolean lastShiftMovedTiles()
  {
  	  return this.shifted;
  }
  
//------------------------------------------------------------------------------
//------------------------------------------------------------------------------

  // Return true if a shift left, right, up, or down would merge any
  // tiles. If no shift would cause any tiles to merge, return false.
  public boolean mergePossible()
  {
  	  System.out.println("making the 0 tiles");
  	  Tile temp1 = new TwoNTile(0);
  	  Tile temp2 = new TwoNTile(0);
  	  boolean test = false;
  	  
  	  //check the bottom and the right, snaking right and down
  	  for(int row = 0; row<=(this.gameBoard.length-1); row++)
  	  {
  	  	  for(int col = 0; col<=(this.gameBoard[row].length-1); col++)
  	  	  {
  	  	  	  //if there isn't a tile
  	  	  	  if(this.gameBoard[row][col]==null)
  	  	  	  	  continue;
  	  	  	  
  	  	  	  //RIGHT: if this isnt the last collumn
  	  	  	  if(col!=(this.gameBoard[row].length-1))
  	  	  	  {
  	  	  	  	  //check RIGHT for any possilbe merges
  	  	  	  	  for(int right=col+1;right<=(this.gameBoard[row].length-1);right++)
  	  	  	  	  {
  	  	  	  	  	  temp1 = this.gameBoard[row][col];
  	  	  	  	  	  temp2 = this.gameBoard[row][right];
  	  	  	  	  	  if (temp2!=null){
  	  	  	  	  	  	  test = temp1.mergesWith(temp2);
  	  	  	  	  	  	  break;
  	  	  	  	  	  }
  	  	  	  	  }
  	  	  	  	  if(test==true)
  	  	  	  	  	  	  return true;
  	  	  	  }
  	  	  	  
  	  	  	  //DOWN: if this row isnt the last row
  	  	  	  if(row!=(this.gameBoard.length-1))
  	  	  	  {
  	  	  	  	  //check DOWN for any possible merges
  	  	  	  	  for(int down=row+1;down<=(this.gameBoard.length-1);down++)
  	  	  	  	  {
  	  	  	  	  	  temp1 = this.gameBoard[row][col];
  	  	  	  	  	  temp2 = this.gameBoard[down][col];
  	  	  	  	  	  if (temp2!=null){
  	  	  	  	  	  	  test = temp1.mergesWith(temp2);
  	  	  	  	  	  	  break;
  	  	  	  	  	  }
  	  	  	  	  }
  	  	  	  	  if(test==true)
  	  	  	  	  	  	  return true;
  	  	  	  }
  	  	  }
  	  }
  	  //if no possible merges were found
  	  return false;
  }

  //Adds a random tile to a free spot
  public void addTileAtFreeSpace(int freeL, Tile tile) throws RuntimeException
  {
  	  //stops it if there aren't any free spaces available
  	  if(this.free == 0)
  	  	  throw new RuntimeException("There are no free spaces!");
  	  if(freeL>this.free)
  	  	  throw new RuntimeException("That free space doesn't exist!");
  	  
  	  //the trackers
  	  int nullCount = 0;
  	  boolean found = false;
  	  
  	  //loops through board counting nulls
  	  for(int row = 0; row<=(this.gameBoard.length-1); row++)					
  	  {
  	  	  for(int col = 0; col<=(this.gameBoard[row].length-1); col++)
  	  	  {
 	  	  	  //if the spot is free
  	  	  	  if((this.gameBoard[row][col])==null)
  	  	  	  {
  	  	  	  //if it is the right spot
  	  	  	  	  if(nullCount==freeL)
  	  	  	  	  {
  	  	  	  	  	  //insert the tile
  	  	  	  	  	  this.gameBoard[row][col] = tile;
  	  	  	  	  	  found = true;
  	  	  	  	  	  this.free--;
  	  	  	  	  }
  	  	  	  	  nullCount++;  	  	  	  	  
  	  	  	  }
  	  	  	  //no need to continue if the tile is added
  	  	  	  if(found)
  	  	  	  	  break;
  	  	  }
  	  	  if(found)
  	  	  	  break;
  	  }
  }

  // Pretty-printed version of the board.
  public String toString()
  {
  	  //bet up string
  	  StringBuilder stBu = new StringBuilder();
  	  String s = " ";
  	  
  	  //for loop moves through the tiles in the board
  	  for(int row = 0; row<=(this.gameBoard.length-1); row++)
  	  {
  	  	  for(int col = 0; col<=(this.gameBoard[row].length-1); col++)
  	  	  {
  	  	  	  //if the spot is empty...
  	  	  	  if(this.gameBoard[row][col]==null)
  	  	  	  s = String.format("%4s ", "-");
  	  	  	  else
  	  	  	  {
  	  	  	  	  //if the spot has a tile...
  	  	  	  	  int val = this.gameBoard[row][col].getScore();
  	  	  	  	  s = String.format("%4s ", String.valueOf(val));
  	  	  	  }
  	  	  	  stBu.append(s);
  	  	  }
  	  	  stBu.append("\n");
  	  }
  	  return stBu.toString();
  }
  
  public String debugString()
  {
  	  return "I'm not used.";
  }
//------------------------------------------------------------------------------
//------------------------------------------------------------------------------

  // Shift the tiles of Board in various directions. 
  public int shiftLeft()
  {
  	  this.shifted = false;
  	  
  	  //tracks points scored during shift
  	  int shiftScore = 0;
  	  //marks the head spot in the direction the board is shifting
  	  boolean firstSpot = true;
  	  //tracks the freespaces
  	  boolean haveFree = false;
  	  boolean haveTile = false;
  	  //checks whether tiles are mergable or not
  	  boolean mrgChk = false;
  	  //tracks the freespace and savedTile row coordinate
  	  int freeR = 0;
  	  int tileR = 0;
  	  //tracks the freespace and savedTile collumn coordinate
  	  int freeC = 0;
  	  int tileC = 0;
  	  //holds the last tile shifted, to check for merges
  	  Tile savedT = new TwoNTile(0);
  	  
  	  //move horizontally through the board starting from 
  	  //	the TOP LEFT and finishing at the BOTTOM RIGHT
  	  for(int row = 0; row<=(this.gameBoard.length-1); row++)					
  	  {
  	  	  for(int col = 0; col<=(this.gameBoard[row].length-1); col++)
  	  	  {
  	  	  	  
  	  	  	  //saves the first spot as empty coordinates or tile
  	  	  	  if(firstSpot)
  	  	  	  {
  	  	  	  	  //if the spot is empty...
  	  	  	  	  if(this.gameBoard[row][col]==null)
  	  	  	  	  {
  	  	  	  	  	  freeR = row;
  	  	  	  	  	  freeC = col;
  	  	  	  	  	  haveFree = true;
  	  	  	  	  }
  	  	  	  	  //if the spot has a tile...
  	  	  	  	  else
  	  	  	  	  {
  	  	  	  	  	  tileR = row;
  	  	  	  	  	  tileC = col;
  	  	  	  	  	  savedT = this.gameBoard[row][col];
  	  	  	  	  	  haveTile = true;
  	  	  	  	  }
  	  	  	  	  firstSpot = false;	  
  	  	  	  }
//------------------------------
  	  	  	  //if this is not the first spot...
  	  	  	  else
  	  	  	  {
  	  	  	  	  //if this spot is empty...
  	  	  	  	  if(this.gameBoard[row][col]==null)
  	  	  	  	  {
  	  	  	  	  	  //if there isnt a saved freespace...
  	  	  	  	  	  if(haveFree==false)
  	  	  	  	  	  {
  	  	  	  	  	  	  freeR = row;
  	  	  	  	  	  	  freeC = col;
  	  	  	  	  	  	  haveFree = true;  	  	  	  	  	  	  
  	  	  	  	  	  }
  	  	  	  	  	  //if a freespace is already saved...
  	  	  	  	  	  else
  	  	  	  	  	  	  continue;
  	  	  	  	  }
  	  	  	  	  //if there is a tile in this spot...
  	  	  	  	  else
  	  	  	  	  {
  	  	  	  	  	  //if a tile hasn't been saved
  	  	  	  	  	  if(haveTile==false)
  	  	  	  	  	  {
  	  	  	  	  	  	  //this is also not the first spot, 
  	  	  	  	  	  	  //	so it must be shifted because there's a 
  	  	  	  	  	  	  //	free space behind it
  	  	  	  	  	  	  savedT = this.gameBoard[row][col];
  	  	  	  	  	  	  this.gameBoard[freeR][freeC] = savedT;
  	  	  	  	  	  	  tileR = freeR;
  	  	  	  	  	  	  tileC = freeC;
  	  	  	  	  	  	  this.shifted = true;
  	  	  	  	  	  	  this.gameBoard[row][col] = null;
  	  	  	  	  	  	  
  	  	  	  	  	  	  //only one free-coordinate is shifted, which 
  	  	  	  	  	  	  //	would mark the next freespace in that line
  	  	  	  	  	  	  freeC++;
  	  	  	  	  	  	  haveTile = true;  	  	  	  	  	  	  
  	  	  	  	  	  }
  	  	  	  	  	  //if this is not the first tile
  	  	  	  	  	  else
  	  	  	  	  	  {
  	  	  	  	  	  	  mrgChk = savedT.mergesWith(this.gameBoard[row][col]);
  	  	  	  	  	  	  //if the tiles can merge
  	  	  	  	  	  	  if(mrgChk==true)
  	  	  	  	  	  	  {
  	  	  	  	  	  	  	  Tile newT = new TwoNTile(0);
  	  	  	  	  	  	  	  try{
  	  	  	  	  	  	  	  	  //merges the tiles
  	  	  	  	  	  	  	  	  newT= savedT.merge(this.gameBoard[row][col]);
  	  	  	  	  	  	  	  }
  	  	  	  	  	  	  	  catch (RuntimeException e)
  	  	  	  	  	  	  	  {System.out.println(e);}
  	  	  	  	  	  	  	  //collects the points earned
  	  	  	  	  	  	  	  shiftScore += newT.getScore();
  	  	  	  	  	  	  	  //replaces the static tile with the merged tile
  	  	  	  	  	  	  	  this.gameBoard[tileR][tileC] = newT;
  	  	  	  	  	  	  	  //sets the spot of the moved tile to null
  	  	  	  	  	  	  	  this.gameBoard[row][col] = null;
  	  	  	  	  	  	  	  
  	  	  	  	  	  	  	  //claims this as the new saved free spot
  	  	  	  	  	  	  	  freeR = tileR;
  	  	  	  	  	  	  	  freeC = tileC+1;
  	  	  	  	  	  	  	  haveFree = true;
  	  	  	  	  	  	  	  //and claims the new saved tile
  	  	  	  	  	  	  	  savedT = new TwoNTile(0);	  
  	  	  	  	  	  	  	  
  	  	  	  	  	  	  	  this.shifted = true;
  	  	  	  	  	  	  	  this.free++;
  	  	  	  	  	  	  }
  	  	  	  	  	  	  //if the tiles can't merge
  	  	  	  	  	  	  else
  	  	  	  	  	  	  {
  	  	  	  	  	  	  	  //if there were only tiles before, 
  	  	  	  	  	  	  	  //	we can't shift it
  	  	  	  	  	  	  	  if(haveFree==true)
  	  	  	  	  	  	  	  {
  	  	  	  	  	  	  	  		//shifts the tile over
  	  	  	  	  	  	  	  		savedT = this.gameBoard[row][col];
  	  	  	  	  	  	  	  		this.gameBoard[freeR][freeC] = savedT;
  	  	  	  	  	  	  	  		tileR = freeR;
  	  	  	  	  	  	  	  		tileC = freeC;
  	  	  	  	  	  	  	  		this.shifted = true;
  	  	  	  	  	  	  	  		//frees up spot
  	  	  	  	  	  	  	  		this.gameBoard[row][col] = null;
  	  	  	  	  	  	  	  		//the new freespace coordinate
  	  	  	  	  	  	  	  		freeC++;
  	  	  	  	  	  	  	  		
  	  	  	  	  	  	  	  }
  	  	  	  	  	  	  	  //else, make this the new saved tile
  	  	  	  	  	  	  	  else
  	  	  	  	  	  	  	  {
  	  	  	  	  	  	  	  	  savedT = this.gameBoard[row][col];
  	  	  	  	  	  	  	  	  tileR = row;
  	  	  	  	  	  	  	  	  tileC = col;
  	  	  	  	  	  	  	  }
  	  	  	  	  	  	  }
  	  	  	  	  	  }
  	  	  	  	  //exit tile check
  	  	  	  	  }
  	  	  	  //exit not-first-spot check
  	  	  	  }
  	  	  //exit col for loop
  	  	  }
  	  	  
  	  	  //reset the booleans for the next line of spots
  	  	  firstSpot = true;
  	  	  haveFree = false;
  	  	  haveTile = false;
  	  }
  	  return shiftScore;
  }

//------------------------------------------------------------------------------  
//shifts the tiles right
  public int shiftRight()
  {
  	  this.shifted = false;
  	  //tracks points scored during shift
  	  int shiftScore = 0;
  	  //marks the head spot in the direction the board is shifting
  	  boolean firstSpot = true;
  	  //tracks the freespaces
  	  boolean haveFree = false;
  	  boolean haveTile = false;
  	  //checks whether tiles are mergable or not
  	  boolean mrgChk = false;
  	  //tracks the freespace and savedTile row coordinate
  	  int freeR = 0;
  	  int tileR = 0;
  	  //tracks the freespace and savedTile collumn coordinate
  	  int freeC = 0;
  	  int tileC = 0;
  	  //holds the last tile shifted, to check for merges
  	  Tile savedT = new TwoNTile(0);
  	  
  	  //move horizontally through the board starting from 
  	  //	the TOP RIGHT and finishing at the BOTTOM LEFT
  	  for(int row = 0; row<=(this.gameBoard.length-1); row++)
  	  {
  	  	  for(int col = (this.gameBoard[0].length-1); col >= 0; col--)
  	  	  {
  	  	  	  
  	  	  	  //saves the first spot as empty coordinates or tile
  	  	  	  if(firstSpot)
  	  	  	  {
  	  	  	  	  //if the spot is empty...
  	  	  	  	  if(this.gameBoard[row][col]==null)
  	  	  	  	  {
  	  	  	  	  	  freeR = row;
  	  	  	  	  	  freeC = col;
  	  	  	  	  	  haveFree = true;
  	  	  	  	  }
  	  	  	  	  //if the spot has a tile...
  	  	  	  	  else
  	  	  	  	  {
  	  	  	  	  	  tileR = row;
  	  	  	  	  	  tileC = col;
  	  	  	  	  	  savedT = this.gameBoard[row][col];
  	  	  	  	  	  haveTile = true;
  	  	  	  	  }
  	  	  	  	  firstSpot = false;	  
  	  	  	  }
//------------------------------
  	  	  	  //if this is not the first spot...
  	  	  	  else
  	  	  	  {
  	  	  	  	  //if this spot is empty...
  	  	  	  	  if(this.gameBoard[row][col]==null)
  	  	  	  	  {
  	  	  	  	  	  //if there isnt a saved freespace...
  	  	  	  	  	  if(haveFree==false)
  	  	  	  	  	  {
  	  	  	  	  	  	  freeR = row;
  	  	  	  	  	  	  freeC = col;
  	  	  	  	  	  	  haveFree = true;  	  	  	  	  	  	  
  	  	  	  	  	  }
  	  	  	  	  	  //if a freespace is already saved...
  	  	  	  	  	  else
  	  	  	  	  	  	  continue;
  	  	  	  	  }
  	  	  	  	  //if there is a tile in this spot...
  	  	  	  	  else
  	  	  	  	  {
  	  	  	  	  	  //if a tile hasn't been saved
  	  	  	  	  	  if(haveTile==false)
  	  	  	  	  	  {
  	  	  	  	  	  	  //this is also not the first spot, 
  	  	  	  	  	  	  //	so it must be shifted because there's a 
  	  	  	  	  	  	  //	free space behind it
  	  	  	  	  	  	  savedT = this.gameBoard[row][col];
  	  	  	  	  	  	  this.gameBoard[freeR][freeC] = savedT;
  	  	  	  	  	  	  tileR = freeR;
  	  	  	  	  	  	  tileC = freeC;
  	  	  	  	  	  	  this.shifted = true;
  	  	  	  	  	  	  this.gameBoard[row][col] = null;  	  	  	  	  	  	  
  	  	  	  	  	  	  //only one free-coordinate is shifted, which 
  	  	  	  	  	  	  //	would mark the next freespace in that line
  	  	  	  	  	  	  freeC--;
  	  	  	  	  	  	  haveTile = true;  	  	  	  	  	  	  
  	  	  	  	  	  }
  	  	  	  	  	  //if this is not the first tile
  	  	  	  	  	  else
  	  	  	  	  	  {
  	  	  	  	  	  	  mrgChk = savedT.mergesWith(this.gameBoard[row][col]);
  	  	  	  	  	  	  //if the tiles can merge
  	  	  	  	  	  	  if(mrgChk==true)
  	  	  	  	  	  	  {
  	  	  	  	  	  	  	  Tile newT = new TwoNTile(0);
  	  	  	  	  	  	  	  try{
  	  	  	  	  	  	  	  	  //merges the tiles
  	  	  	  	  	  	  	  	  newT= savedT.merge(this.gameBoard[row][col]);
  	  	  	  	  	  	  	  }
  	  	  	  	  	  	  	  catch (RuntimeException e)
  	  	  	  	  	  	  	  {System.out.println(e);}
  	  	  	  	  	  	  	  //collects the points earned
  	  	  	  	  	  	  	  shiftScore += newT.getScore();
  	  	  	  	  	  	  	  //replaces the static tile with the merged tile
  	  	  	  	  	  	  	  this.gameBoard[tileR][tileC] = newT;
  	  	  	  	  	  	  	  //sets the spot of the moved tile to null
  	  	  	  	  	  	  	  this.gameBoard[row][col] = null;
  	  	  	  	  	  	  	  //claims this as the new saved free spot
  	  	  	  	  	  	  	  freeR = tileR;
  	  	  	  	  	  	  	  freeC = tileC-1;
  	  	  	  	  	  	  	  haveFree = true;
  	  	  	  	  	  	  	  //and claims the new saved tile
  	  	  	  	  	  	  	  savedT = new TwoNTile(0);
  	  	  	  	  	  	  	  this.shifted = true;
  	  	  	  	  	  	  	  this.free++;
  	  	  	  	  	  	  }
  	  	  	  	  	  	  //if the tiles can't merge
  	  	  	  	  	  	  else
  	  	  	  	  	  	  {
  	  	  	  	  	  	  	  //if there were only tiles before, 
  	  	  	  	  	  	  	  //	we can't shift it
  	  	  	  	  	  	  	  if(haveFree==true)
  	  	  	  	  	  	  	  {
  	  	  	  	  	  	  	  		//shifts the tile over
  	  	  	  	  	  	  	  		savedT = this.gameBoard[row][col];
  	  	  	  	  	  	  	  		this.gameBoard[freeR][freeC] = savedT;
  	  	  	  	  	  	  	  		tileR = freeR;
  	  	  	  	  	  	  	  		tileC = freeC;
  	  	  	  	  	  	  	  		this.shifted = true;
  	  	  	  	  	  	  	  		//frees up spot
  	  	  	  	  	  	  	  		this.gameBoard[row][col] = null;
  	  	  	  	  	  	  	  		//the new freespace coordinate
  	  	  	  	  	  	  	  		freeC--;
  	  	  	  	  	  	  	  }
  	  	  	  	  	  	  	  //else, make this the new saved tile
  	  	  	  	  	  	  	  else
  	  	  	  	  	  	  	  {
  	  	  	  	  	  	  	  	  savedT = this.gameBoard[row][col];
  	  	  	  	  	  	  	  	  tileR = row;
  	  	  	  	  	  	  	  	  tileC = col;
  	  	  	  	  	  	  	  }
  	  	  	  	  	  	  }
  	  	  	  	  	  }
  	  	  	  	  //exit tile check
  	  	  	  	  }
  	  	  	  //exit not-first-spot check
  	  	  	  }
  	  	  //exit col for loop
  	  	  }
  	  	  
  	  	  //reset the booleans for the next line of spots
  	  	  firstSpot = true;
  	  	  haveFree = false;
  	  	  haveTile = false;
  	  }
  	  return shiftScore;
  }

//------------------------------------------------------------------------------  
//shifts the tiles up
  public int shiftUp()
  {
  	  this.shifted = false;
  	  //tracks points scored during shift
  	  int shiftScore = 0;
  	  //marks the head spot in the direction the board is shifting
  	  boolean firstSpot = true;
  	  //tracks the freespaces
  	  boolean haveFree = false;
  	  boolean haveTile = false;
  	  //checks whether tiles are mergable or not
  	  boolean mrgChk = false;
  	  //tracks the freespace and savedTile row coordinate
  	  int freeR = 0;
  	  int tileR = 0;
  	  //tracks the freespace and savedTile collumn coordinate
  	  int freeC = 0;
  	  int tileC = 0;
  	  //holds the last tile shifted, to check for merges
  	  Tile savedT = new TwoNTile(0);
  	  
  	  //move vertically through the board starting from the 
  	  //	TOP LEFT and finishing at the BOTTOM RIGHT
  	  for(int col = 0; col <= (this.gameBoard[0].length-1); col++)
  	  {
  	  	  for(int row = 0; row<=(this.gameBoard.length-1); row++)
  	  	  {
  	  	  	  
  	  	  	  //saves the first spot as empty coordinates or tile
  	  	  	  if(firstSpot)
  	  	  	  {
  	  	  	  	  //if the spot is empty...
  	  	  	  	  if(this.gameBoard[row][col]==null)
  	  	  	  	  {
  	  	  	  	  	  freeR = row;
  	  	  	  	  	  freeC = col;
  	  	  	  	  	  haveFree = true;
  	  	  	  	  }
  	  	  	  	  //if the spot has a tile...
  	  	  	  	  else
  	  	  	  	  {
  	  	  	  	  	  tileR = row;
  	  	  	  	  	  tileC = col;
  	  	  	  	  	  savedT = this.gameBoard[row][col];
  	  	  	  	  	  haveTile = true;
  	  	  	  	  }
  	  	  	  	  firstSpot = false;	  
  	  	  	  }
//------------------------------
  	  	  	  //if this is not the first spot...
  	  	  	  else
  	  	  	  {
  	  	  	  	  //if this spot is empty...
  	  	  	  	  if(this.gameBoard[row][col]==null)
  	  	  	  	  {
  	  	  	  	  	  //if there isnt a saved freespace...
  	  	  	  	  	  if(haveFree==false)
  	  	  	  	  	  {
  	  	  	  	  	  	  freeR = row;
  	  	  	  	  	  	  freeC = col;
  	  	  	  	  	  	  haveFree = true;  	  	  	  	  	  	  
  	  	  	  	  	  }
  	  	  	  	  	  //if a freespace is already saved...
  	  	  	  	  	  else
  	  	  	  	  	  	  continue;
  	  	  	  	  }
  	  	  	  	  //if there is a tile in this spot...
  	  	  	  	  else
  	  	  	  	  {
  	  	  	  	  	  //if a tile hasn't been saved
  	  	  	  	  	  if(haveTile==false)
  	  	  	  	  	  {
  	  	  	  	  	  	  //this is also not the first spot, 
  	  	  	  	  	  	  //	so it must be shifted because there's a 
  	  	  	  	  	  	  //	free space behind it
  	  	  	  	  	  	  savedT = this.gameBoard[row][col];
  	  	  	  	  	  	  this.gameBoard[freeR][freeC] = savedT;
  	  	  	  	  	  	  tileR = freeR;
  	  	  	  	  	  	  tileC = freeC;
  	  	  	  	  	  	  this.shifted = true;
  	  	  	  	  	  	  this.gameBoard[row][col] = null;  	 
  	  	  	  	  	  	  //only one free-coordinate is shifted, which 
  	  	  	  	  	  	  //	would mark the next freespace in that line
  	  	  	  	  	  	  freeR++;
  	  	  	  	  	  	  haveTile = true;  	  	  	  	  	  	  
  	  	  	  	  	  }
  	  	  	  	  	  //if this is not the first tile
  	  	  	  	  	  else
  	  	  	  	  	  {
  	  	  	  	  	  	  mrgChk = savedT.mergesWith(this.gameBoard[row][col]);
  	  	  	  	  	  	  //if the tiles can merge
  	  	  	  	  	  	  if(mrgChk==true)
  	  	  	  	  	  	  {
  	  	  	  	  	  	  	  Tile newT = new TwoNTile(0);
  	  	  	  	  	  	  	  try{
  	  	  	  	  	  	  	  	  //merges the tiles
  	  	  	  	  	  	  	  	  newT= savedT.merge(this.gameBoard[row][col]);
  	  	  	  	  	  	  	  }
  	  	  	  	  	  	  	  catch (RuntimeException e)
  	  	  	  	  	  	  	  {System.out.println(e);}
  	  	  	  	  	  	  	  //collects the points earned
  	  	  	  	  	  	  	  shiftScore += newT.getScore();
  	  	  	  	  	  	  	  //replaces the static tile with the merged tile
  	  	  	  	  	  	  	  this.gameBoard[tileR][tileC] = newT;
  	  	  	  	  	  	  	  //sets the spot of the moved tile to null
  	  	  	  	  	  	  	  this.gameBoard[row][col] = null;
  	  	  	  	  	  	  	  //claims this as the new saved free spot
  	  	  	  	  	  	  	  freeR = tileR+1;
  	  	  	  	  	  	  	  freeC = tileC;
  	  	  	  	  	  	  	  haveFree = true;
  	  	  	  	  	  	  	  //and claims the new saved tile
  	  	  	  	  	  	  	  savedT = new TwoNTile(0);
  	  	  	  	  	  	  	  this.shifted = true;
  	  	  	  	  	  	  	  this.free++;
  	  	  	  	  	  	  }
  	  	  	  	  	  	  //if the tiles can't merge
  	  	  	  	  	  	  else
  	  	  	  	  	  	  {
  	  	  	  	  	  	  	  //if there were only tiles before, 
  	  	  	  	  	  	  	  //	we can't shift it
  	  	  	  	  	  	  	  if(haveFree==true)
  	  	  	  	  	  	  	  {
  	  	  	  	  	  	  	  		//shifts the tile over
  	  	  	  	  	  	  	  		savedT = this.gameBoard[row][col];
  	  	  	  	  	  	  	  		this.gameBoard[freeR][freeC] = savedT;
  	  	  	  	  	  	  	  		tileR = freeR;
  	  	  	  	  	  	  	  		tileC = freeC;
  	  	  	  	  	  	  	  		this.shifted = true;
  	  	  	  	  	  	  	  		//frees up spot
  	  	  	  	  	  	  	  		this.gameBoard[row][col] = null;
  	  	  	  	  	  	  	  		//the new freespace coordinate
  	  	  	  	  	  	  	  		freeR++;
  	  	  	  	  	  	  	  }
  	  	  	  	  	  	  	  //else, make this the new saved tile
  	  	  	  	  	  	  	  else
  	  	  	  	  	  	  	  {
  	  	  	  	  	  	  	  	  savedT = this.gameBoard[row][col];
  	  	  	  	  	  	  	  	  tileR = row;
  	  	  	  	  	  	  	  	  tileC = col;
  	  	  	  	  	  	  	  }
  	  	  	  	  	  	  }
  	  	  	  	  	  }
  	  	  	  	  //exit tile check
  	  	  	  	  }
  	  	  	  //exit not-first-spot check
  	  	  	  }
  	  	  //exit col for loop
  	  	  }
  	  	  
  	  	  //reset the booleans for the next line of spots
  	  	  firstSpot = true;
  	  	  haveFree = false;
  	  	  haveTile = false;
  	  }
  	  return shiftScore;
  }
  
//------------------------------------------------------------------------------
//shifts the tiles down
  public int shiftDown()
  {
  	  this.shifted = false;
  	  //tracks points scored during shift
  	  int shiftScore = 0;
  	  //marks the head spot in the direction the board is shifting
  	  boolean firstSpot = true;
  	  //tracks the freespaces
  	  boolean haveFree = false;
  	  boolean haveTile = false;
  	  //checks whether tiles are mergable or not
  	  boolean mrgChk = false;
  	  //tracks the freespace and savedTile row coordinate
  	  int freeR = 0;
  	  int tileR = 0;
  	  //tracks the freespace and savedTile collumn coordinate
  	  int freeC = 0;
  	  int tileC = 0;
  	  //holds the last tile shifted, to check for merges
  	  Tile savedT = new TwoNTile(0);
  	  
  	  //move vertically through the board starting from the 
  	  //	BOTTOM LEFT and finishing at the TOP RIGHT
  	  for(int col = 0; col <= (this.gameBoard[0].length-1); col++)
  	  {
  	  	  for(int row = (this.gameBoard.length-1); row >= 0; row--)
  	  	  {
  	  	  	  
  	  	  	  //saves the first spot as empty coordinates or tile
  	  	  	  if(firstSpot)
  	  	  	  {
  	  	  	  	  //if the spot is empty...
  	  	  	  	  if(this.gameBoard[row][col]==null)
  	  	  	  	  {
  	  	  	  	  	  freeR = row;
  	  	  	  	  	  freeC = col;
  	  	  	  	  	  haveFree = true;
  	  	  	  	  }
  	  	  	  	  //if the spot has a tile...
  	  	  	  	  else
  	  	  	  	  {
  	  	  	  	  	  tileR = row;
  	  	  	  	  	  tileC = col;
  	  	  	  	  	  savedT = this.gameBoard[row][col];
  	  	  	  	  	  haveTile = true;
  	  	  	  	  }
  	  	  	  	  firstSpot = false;	  
  	  	  	  }
//------------------------------
  	  	  	  //if this is not the first spot...
  	  	  	  else
  	  	  	  {
  	  	  	  	  //if this spot is empty...
  	  	  	  	  if(this.gameBoard[row][col]==null)
  	  	  	  	  {
  	  	  	  	  	  //if there isnt a saved freespace...
  	  	  	  	  	  if(haveFree==false)
  	  	  	  	  	  {
  	  	  	  	  	  	  freeR = row;
  	  	  	  	  	  	  freeC = col;
  	  	  	  	  	  	  haveFree = true;  	  	  	  	  	  	  
  	  	  	  	  	  }
  	  	  	  	  	  //if a freespace is already saved...
  	  	  	  	  	  else
  	  	  	  	  	  	  continue;
  	  	  	  	  }
  	  	  	  	  //if there is a tile in this spot...
  	  	  	  	  else
  	  	  	  	  {
  	  	  	  	  	  //if a tile hasn't been saved
  	  	  	  	  	  if(haveTile==false)
  	  	  	  	  	  {
  	  	  	  	  	  	  //this is also not the first spot, 
  	  	  	  	  	  	  //	so it must be shifted because there's a 
  	  	  	  	  	  	  //	free space behind it
  	  	  	  	  	  	  savedT = this.gameBoard[row][col];
  	  	  	  	  	  	  this.gameBoard[freeR][freeC] = savedT;
  	  	  	  	  	  	  tileR = freeR;
  	  	  	  	  	  	  tileC = freeC;
  	  	  	  	  	  	  this.shifted = true;
  	  	  	  	  	  	  this.gameBoard[row][col] = null;  	 
  	  	  	  	  	  	  //only one free-coordinate is shifted, which 
  	  	  	  	  	  	  //	would mark the next freespace in that line
  	  	  	  	  	  	  freeR--;
  	  	  	  	  	  	  haveTile = true;  	  	  	  	  	  	  
  	  	  	  	  	  }
  	  	  	  	  	  //if this is not the first tile
  	  	  	  	  	  else
  	  	  	  	  	  {
  	  	  	  	  	  	  mrgChk = savedT.mergesWith(this.gameBoard[row][col]);
  	  	  	  	  	  	  //if the tiles can merge
  	  	  	  	  	  	  if(mrgChk==true)
  	  	  	  	  	  	  {
  	  	  	  	  	  	  	  Tile newT = new TwoNTile(0);
  	  	  	  	  	  	  	  try{
  	  	  	  	  	  	  	  	  //merges the tiles
  	  	  	  	  	  	  	  	  newT= savedT.merge(this.gameBoard[row][col]);
  	  	  	  	  	  	  	  }
  	  	  	  	  	  	  	  catch (RuntimeException e)
  	  	  	  	  	  	  	  {System.out.println(e);}
  	  	  	  	  	  	  	  //collects the points earned
  	  	  	  	  	  	  	  shiftScore += newT.getScore();
  	  	  	  	  	  	  	  //replaces the static tile with the merged tile
  	  	  	  	  	  	  	  this.gameBoard[tileR][tileC] = newT;
  	  	  	  	  	  	  	  //sets the spot of the moved tile to null
  	  	  	  	  	  	  	  this.gameBoard[row][col] = null;
  	  	  	  	  	  	  	  //claims this as the new saved free spot
  	  	  	  	  	  	  	  freeR = tileR-1;
  	  	  	  	  	  	  	  freeC = tileC;
  	  	  	  	  	  	  	  haveFree = true;
  	  	  	  	  	  	  	  //and claims the new saved tile
  	  	  	  	  	  	  	  savedT = new TwoNTile(0);
  	  	  	  	  	  	  	  this.shifted = true;
  	  	  	  	  	  	  	  this.free++;
  	  	  	  	  	  	  }
  	  	  	  	  	  	  //if the tiles can't merge
  	  	  	  	  	  	  else
  	  	  	  	  	  	  {
  	  	  	  	  	  	  	  //if there were only tiles before, 
  	  	  	  	  	  	  	  //	we can't shift it
  	  	  	  	  	  	  	  if(haveFree==true)
  	  	  	  	  	  	  	  {
  	  	  	  	  	  	  	  		//shifts the tile over
  	  	  	  	  	  	  	  		savedT = this.gameBoard[row][col];
  	  	  	  	  	  	  	  		this.gameBoard[freeR][freeC] = savedT;
  	  	  	  	  	  	  	  		tileR = freeR;
  	  	  	  	  	  	  	  		tileC = freeC;
  	  	  	  	  	  	  	  		this.shifted = true;
  	  	  	  	  	  	  	  		//frees up spot
  	  	  	  	  	  	  	  		this.gameBoard[row][col] = null;
  	  	  	  	  	  	  	  		//the new freespace coordinate
  	  	  	  	  	  	  	  		freeR--;
  	  	  	  	  	  	  	  }
  	  	  	  	  	  	  	  //else, make this the new saved tile
  	  	  	  	  	  	  	  else
  	  	  	  	  	  	  	  {
  	  	  	  	  	  	  	  	  savedT = this.gameBoard[row][col];
  	  	  	  	  	  	  	  	  tileR = row;
  	  	  	  	  	  	  	  	  tileC = col;
  	  	  	  	  	  	  	  }
  	  	  	  	  	  	  }
  	  	  	  	  	  }
  	  	  	  	  //exit tile check
  	  	  	  	  }
  	  	  	  //exit not-first-spot check
  	  	  	  }
  	  	  //exit col for loop
  	  	  }
  	  	  
  	  	  //reset the booleans for the next line of spots
  	  	  firstSpot = true;
  	  	  haveFree = false;
  	  	  haveTile = false;
  	  }
  	  return shiftScore;
  }

}
