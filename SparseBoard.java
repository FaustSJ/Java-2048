import java.util.*;
import java.lang.*;
// Tracks the positions of an arbitrary 2D grid of Tiles.  SparseBoard
// uses internal linked lists of tile coordinates to track only the
// tiles that are non-empty.  A full-credit implementation will make
// use of a completed WLinkedList.
public class SparseBoard extends Board {
	
	private int r;				//number of rows
	private int c;				//number of columns
	private int free;			//the number of free spaces
	private boolean shifted;	//tracks if the last move shifted tiles or not
	private WLinkedList<TileNode> rowMajor;	//tracks the board by rows
	private WLinkedList<TileNode> colMajor;	//tracks the board by columns
	
	public class TileNode
  	    {
  	    	private int row;
  	    	private int column;
  	    	private Tile ti;
  	  
  	    	public TileNode(int r, int c, Tile t)
  	    	{
  	    		this.row = r;
  	    		this.column = c;
  	    		this.ti = t;
  	    	}
  	  
  	    	public int getRow()
  	    	{
  	    		return this.row;
  	    	}
  	  
  	    	public int getCol()
  	    	{
  	    		return this.column;
  	    	}
  	  
  	    	public Tile getTile()
  	    	{
  	    		return this.ti;
  	    	}
  	  
  	    	public void setRow(int r)
  	    	{
  	    		this.row = r;
  	    	}
  	  
  	    	public void setCol(int c)
  	    	{
  	    		this.column = c;
  	    	}
  	    	
  	    	public void setTile(Tile t)
  	    	{
  	    		this.ti = t;
  	    	}
  	    }
  // Build a Board of the specified size that is empty of any tiles
  public SparseBoard(int rows, int cols)
  {
  	  this.r = rows;
  	  this.c = cols;
  	  this.free = r*c;
  	  this.shifted = false;
  	  this.rowMajor = new WLinkedList<TileNode>();
  	  this.colMajor = new WLinkedList<TileNode>();
  }

  // Build a board that copies the 2D array of tiles provided Tiles
  // are immutable so can be referenced without copying.  Use internal
  // lists of tile coordinates.  Ignore empty spaces in the array t
  // which are indicated by nulls.
  public SparseBoard(Tile t[][])
  {
  	   
  	  //grabs the row and column count of the array
  	  this.r = t.length;
  	  this.c = t[0].length;
  	  //sets up the other class fields
  	  this.shifted = false;
  	  this.rowMajor = new WLinkedList<TileNode>();
  	  this.colMajor = new WLinkedList<TileNode>();
  	  this.free = 0;
  	  
  	  //a temporary tile is created for this method
  	  Tile temp;
  	  TileNode tn;
  	  
  	  //moves through the list t, 
  	  //	turns tiles into nodes, and adds them to rowMajor
  	  for(int row = 0; row<=(t.length-1); row++)
  	  {
  	  	  for(int col = 0; col<=(t[0].length-1); col++)
  	  	  {
  	  	  	  //check to see if this spot is empty
  	  	  	  if(t[row][col]==null)
  	  	  	  	  this.free++;
  	  	  	  else
  	  	  	  {
  	  	  	  	  //grabbing the tile from list t
  	  	  	  	  temp = t[row][col];
  	  	  	  	  //creating a node list from the grabed tile's score and location
  	  	  	  	  tn = new TileNode(row, col, temp);
  	  	  	  	  //adds the node list to rowMajor
  	  	  	  	  this.rowMajor.add(tn);
  	  }	  }	  }
  	  	  
  	  //moves through the list t, 
  	  //	turns tiles into nodes, and adds them to colMajor
  	  for(int col = 0; col<=(t[0].length-1); col++)
  	  {
  	  	  for(int row = 0; row<=(t.length-1); row++)
  	  	  {
  	  	  	  //check to see if this spot is empty
  	  	  	  if(t[row][col]==null)
  	  	  	  	  this.free++;
  	  	  	  else
  	  	  	  {
  	  	  	  	  //grabbing the tile from list t
  	  	  	  	  temp = t[row][col];
  	  	  	  	  
  	  	  	  	  //creating a node list from the grabed tile's score and location
  	  	  	  	  tn = new TileNode(row, col, temp);
  	  	  	  	  //adds the node list to colMajor
  	  	  	  	  this.colMajor.add(tn);
  	  }	  }	  }
  	  
  }

  // Create a distinct copy of the board including its internal tile
  // positions and any other state
  // 
  // TARGET COMPLEXITY: O(T)
  // T: the number of non-empty tiles in the board
  public Board copy()
  {
  	  //creates the copy board
  	  SparseBoard newBoard = new SparseBoard(this.r, this.c);
  	  //copy's over the fields
  	  newBoard.shifted = this.shifted;
  	  newBoard.rowMajor = this.rowMajor;
  	  newBoard.colMajor = this.colMajor;
  	  newBoard.free = this.free;
  	  //returns the copy board
  	  return newBoard;
  }

  // Return the number of rows in the Board
  // TARGET COMPLEXITY: O(1)
  public int getRows()
  {
  	  return this.r;
  }

  // Return the number of columns in the Board
  // TARGET COMPLEXITY: O(1)
  public int getCols()
  {
  	  return this.c;
  }

  // Return how many tiles are present in the board (non-empty spaces)
  // TARGET COMPLEXITY: O(1)
  public int getTileCount()
  {
  	  int tiles = (this.r*this.c)-this.free;
  	  return tiles;
  }

  // Return how many free spaces are in the board
  // TARGET COMPLEXITY: O(1)
  public int getFreeSpaceCount()
  {
  	  return this.free;
  }

  // Get the tile at a particular location.  If no tile exists at the
  // given location (free space) then null is returned. Throw a
  // runtime exception with a useful error message if an out of bounds
  // index is requested.
  // 
  // TARGET COMPLEXITY: O(T)
  // T: The number of non-empty tiles in the board 
  public Tile tileAt(int i, int j) throws RuntimeException
  {
  	  if((i>this.r)||(j>this.c))
  	  	  throw new RuntimeException("Out of bounds!");
  	  
  	  //utilize list itorator to minimize target complextity
  	  
  	  //if it is closer to zero row-wise rather than column-wise
  	  //	OR if the two coordinates are the same
  	  if((i<j)||(i==j))
  	  {  
  	  	  //checks how close i is to the last row
  	  	  int compare = this.r - i;
  	  	  //if i is closer to the last row than to the first, iterate backwards
  	  	  if(i>compare)
  	  	  {
  	  	  	  //starts the iterator at the end and works backwards
  	  	  	  ListIterator<TileNode> iter = rowMajor.listIterator(rowMajor.size()-1);
  	  	  	  while(iter.hasPrevious())
  	  	  	  {
  	  	  	  	  //grabs the node
  	  	  	  	  TileNode temp = iter.previous();
  	  	  	  	  //checks the node's coordinates
  	  	  	  	  if((temp.getRow()==i)&&(temp.getCol()==j))
  	  	  	  	  	  return temp.getTile();
  	  	  	  }
  	  	  }
  	  	  //if i is closer to the first row than to the last, iterate forwards
  	  	  else
  	  	  {
  	  	  	  //starts the iterator at the beginning and works forwards
  	  	  	  ListIterator<TileNode> iter = rowMajor.listIterator();
  	  	  	  while(iter.hasNext())
  	  	  	  {
  	  	  	  	  //grabs the node
  	  	  	  	  TileNode temp = iter.next();
  	  	  	  	  //checks the node's coordinates
  	  	  	  	  if((temp.getRow()==i)&&(temp.getCol()==j))
  	  	  	  	  	  return temp.getTile();
  	  	  	  }	  
  	  	  }
  	  }

  	  if(j<i)
  	  {
  	  	  //checks how close i is to the last row
  	  	  int compare = this.c - j;
  	  	  //if i is closer to the last row than to the first, iterate backwards
  	  	  if(j>compare)
  	  	  {
  	  	  	  //starts the iterator at the end and works backwards
  	  	  	  ListIterator<TileNode> iter = colMajor.listIterator(colMajor.size()-1);
  	  	  	  while(iter.hasPrevious())
  	  	  	  {
  	  	  	  	  //grabs the node
  	  	  	  	  TileNode temp = iter.previous();
  	  	  	  	  //checks the node's coordinates
  	  	  	  	  if((temp.getRow()==i)&&(temp.getCol()==j))
  	  	  	  	  	  return temp.getTile();
  	  	  	  }
  	  	  }
  	  	  //if i is closer to the first row than to the last, iterate forwards
  	  	  else
  	  	  {
  	  	  	  //starts the iterator at the beginning and works forwards
  	  	  	  ListIterator<TileNode> iter = colMajor.listIterator();
  	  	  	  while(iter.hasNext())
  	  	  	  {
  	  	  	  	  //grabs the node
  	  	  	  	  TileNode temp = iter.next();
  	  	  	  	  //checks the node's coordinates
  	  	  	  	  if((temp.getRow()==i)&&(temp.getCol()==j))
  	  	  	  	  	  return temp.getTile();
  	  	  	  }	  
  	  	  }
  	  }

  	  //if a tile was never returned, return null
  	  return null;
  }

  // true if the last shift operation moved any tile
  // false otherwise
  // TARGET COMPLEXITY: O(1)
  public boolean lastShiftMovedTiles()
  {
  	  return this.shifted;
  }

  // Return true if a shift left, right, up, or down would merge any
  // tiles. If no shift would cause any tiles to merge, return false.
  // The inability to merge anything is part of determining if the
  // game is over.
  // 
  // TARGET COMPLEXITY: O(T)
  // T: The number of non-empty tiles in the board 
  public boolean mergePossible()
  {
  	  //iterRow checks for merges among tiles in the same row
  	  ListIterator<TileNode> iterRow = rowMajor.listIterator();
  	  //iterCol checks fer merges among tiles in the same column
  	  ListIterator<TileNode> iterCol = colMajor.listIterator();
  	  
  	  //these track the current row and column
  	  int currentRow = -1;
  	  int currentCol = -1;
  	  
  	  //these tiles save the previous tiles to compare with the current tile
  	  Tile compareRow = new TwoNTile(0);
  	  Tile compareCol = new TwoNTile(0);
  	  
  	  //traversing through the iterators...
  	  while(iterRow.hasNext())
  	  {
  	  	  //grabs the nodes from the iterator
  	  	  TileNode rowNode = iterRow.next();
  	  	  TileNode colNode = iterCol.next();
  	  	  
  	  	  //grabs the tiles from the nodes
  	  	  Tile currentRowTile = rowNode.getTile();
  	  	  Tile currentColTile = colNode.getTile();
  	  	  
//IF----------------
  	  	  //if this is a new row
  	  	  if(rowNode.getRow()!=currentRow)
  	  	  {
  	  	  	  //sets the current tile as the tile to compare to
  	  	  	  compareRow = currentRowTile;
  	  	  }
  	  	  //if this is a new column
  	  	  if(colNode.getCol()!=currentCol)
  	  	  {
  	  	  	  //sets the current tile as the tile to compare to
  	  	  	  compareCol = currentColTile;
  	  	  }
//ELSE---------------
  	  	  //if this isn't the first tile of a new row...
  	  	  if(rowNode.getRow()==currentRow)
  	  	  {
  	  	  	  //returns true if the current and previous tiles can merge
  	  	  	  if(compareRow.mergesWith(currentRowTile))
  	  	  	  	  return true;
  	  	  	  //otherwise, sets the current tile as the tile to compare to
  	  	  	  compareRow = currentRowTile;
  	  	  }
  	  	  //if this isn't the first tile of a new column...
  	  	  if(colNode.getCol()==currentCol)
  	  	  {
  	  	  	  //returns true if the current and previous tiles can merge
  	  	  	  if(compareCol.mergesWith(currentColTile))
  	  	  	  	  return true;
  	  	  	  //otherwise, sets the current tile as the tile to compare to
  	  	  	  compareCol = currentColTile;
  	  	  } 
  	  }
  	  
  	  //if no possible merges were found
  	  return false;
  }

  // Add a the given tile to the board at the "freeL"th free space.
  // Free spaces are numbered 0,1,... from left to right accross the
  // columns of the zeroth row, then the first row, then the second
  // and so forth. For example the board with following configuration
  // 
  //    -    -    -    - 
  //    -    4    -    - 
  //   16    2    -    2 
  //    8    8    4    4 
  // 
  // has its 9 free spaces numbered as follows
  // 
  //    0    1    2    3 
  //    4    .    5    6 
  //    .    .    7    . 
  //    .    .    .    . 
  // 
  // where the dots (.) represent filled tiles on the board.
  // 
  // Calling addTileAtFreeSpace(6, new Tile(32) would leave the board in
  // the following state.
  // 
  //    -    -    -    - 
  //    -    4    -   32 
  //   16    2    -    2 
  //    8    8    4    4 
  // 
  // Throw a runtime exception with an informative error message if a
  // location that does not exist is requested.
  // 
  // TARGET COMPLEXITY: O(T+max(R,C))
  // T: the number of non-empty tiles in the board
  public void addTileAtFreeSpace(int freeL, Tile tile)
  {
  	  //stops it if there aren't any free spaces available
  	  if(this.free == 0)
  	  	  throw new RuntimeException("There are no free spaces on the board!");
  	  if(freeL>this.free)
  	  	  throw new RuntimeException("Spot "+freeL+" isn't free!");
  	  
  	  //sets up the iterator to traverse through the board
  	  ListIterator<TileNode> iter = this.rowMajor.listIterator();
  	  //found breaks the while loop once the free spot is found (true)
  	  boolean found = false;
  	  //freeCount tracks what number of free spots have been found
  	  int freeCount = -1;
  	  //tracks the initial or former row and column
  	  int preRow = 0;
  	  int preCol = 0;
  	  //tracks the tile's current row and column
  	  int currentRow = 0;
  	  int currentCol = 0;
  	  //once found, the freespots coords will be stored for colMajor to use
  	  int rowCM = 0;
  	  int colCM = 0;
  	  
  	  while(iter.hasNext())
  	  {
  	  	  //grabs the current node
  	  	  TileNode tnode = iter.next();
  	  	  
  	  	  //grabs the current coordinates
  	  	  currentRow = tnode.getRow();
  	  	  currentCol = tnode.getCol();
  	  	  
  	  	  
  	  	  //catches freespaces from any empty rows
  	  	  //	AND catches any remaining freespaces from previous nonempty row
  	  	  while(preRow<currentRow)
  	  	  {	
  	  	  	  	//add the new free spaces
  	  	  	  	int freeAdd = (this.c-1)-preCol;
  	  	  	  	freeCount += freeAdd;
  	  	  	  	//if the freeCount reaches freeL
  	  	  	  	if(freeCount>=freeL)
  	  	  	  	{
  	  	  	  		rowCM = preRow;
  	  	  	  		//colCM = last column - amount over
  	  	  	  		colCM = (this.c-1)-(freeCount-freeL);
  	  	  	  		//creates the new node and adds it to rowMajor
					TileNode newNode = new TileNode(rowCM, colCM, tile);
					iter.add(newNode);  	  	  	  		
  	  	  	  		found = true;
  	  	  	  	}
  	  	  	  	if(found)
  	  	  	  		break;
  	  	  	  	
  	  	  	  	//after freeCount updates, preCol and preRow are adjusted
  	  	  	  	//		for the next loop
  	  	  	  	preCol = 0;
  	  	  	  	preRow++;
  	  	  }
  	  	  //catches any free spaces in the same row ahead of the current tile
  	  	  //	AND catches any freespaces between tiles
  	  	  if(preRow==currentRow)
  	  	  {
  	  	  	  	//add the new free spaces
  	  	  	  	int freeAdd = currentCol-preCol;
  	  	  	  	freeCount += freeAdd;
  	  	  	  	//if the freeCount reaches freeL
  	  	  	  	if(freeCount>=freeL)
  	  	  	  	{
  	  	  	  		rowCM = preRow;
  	  	  	  		//colCM = last column - amount over
  	  	  	  		colCM = currentCol-(freeCount-freeL);
  	  	  	  		//creates the new node and adds it to rowMajor
					TileNode newNode = new TileNode(rowCM, colCM, tile);
					iter.add(newNode);  	  	  	  		
  	  	  	  		found = true;
  	  	  	  	}
  	  	  }
  	  	  //counts the free spaces after the last tile
  	  	  if(iter.hasNext()==false)
  	  	  {
  	  	  	  while(currentRow<=(this.r-1))
  	  	  	  {	
  	  	  	  	//add the new free spaces
  	  	  	  	int freeAdd = (this.c-1)-preCol;
  	  	  	  	freeCount += freeAdd;
  	  	  	  	//if the freeCount reaches freeL
  	  	  	  	if(freeCount>=freeL)
  	  	  	  	{
  	  	  	  		rowCM = preRow;
  	  	  	  		//colCM = last column - amount over
  	  	  	  		colCM = (this.c-1)-(freeCount-freeL);
  	  	  	  		//creates the new node and adds it to rowMajor
					TileNode newNode = new TileNode(rowCM, colCM, tile);
					iter.add(newNode);  	  	  	  		
  	  	  	  		found = true;
  	  	  	  	}
  	  	  	  	if(found)
  	  	  	  		break;
  	  	  	  	
  	  	  	  	//after freeCount updates, preCol and preRow are adjusted
  	  	  	  	//		for the next loop
  	  	  	  	preCol = 0;
  	  	  	  	currentRow++;
  	  	  	  }
  	  	  }
  	  	  
  	  	  //sets the previous coords up to compare for the next loop/node
  	  	  preRow = currentRow;
  	  	  preCol = currentCol;

  	  	  //breaks the loop is the freespot is found
  	  	  if(found==true)
  	  	  	  break;
  	  }
  	  
  	  //updates colMajor
  	  if(found==true)
  	  {
  	  	  boolean placed = false;
  	  	  TileNode newNode = new TileNode(rowCM, colCM, tile);
  	  	  ListIterator<TileNode> iterCol = this.colMajor.listIterator();
  	  	  //traversing through colMajor
  	  	  while(iterCol.hasNext())
  	  	  {
  	  	  	  //grabs the node
  	  	  	  TileNode tnode = iterCol.next();
  	  	  	  //grabs the coords
  	  	  	  currentRow = tnode.getRow();
  	  	  	  currentCol = tnode.getCol();
  	  	  	  
  	  	  	  //if the current column is before the freespace, move to next node
  	  	  	  if(colCM>currentCol)
  	  	  	  	  continue;
  	  	  	  //if the current column is the correct one, start checking rows
  	  	  	  if(colCM==currentCol)
  	  	  	  {
  	  	  	  	  //move on to next node if the currentRow is small
  	  	  	  	  if(rowCM>currentRow)
  	  	  	  	  	  continue;
  	  	  	  	  //if the node past the freespot is reached, insert behind
  	  	  	  	  if(rowCM<currentRow)
  	  	  	  	  {
  	  	  	  	  	  tnode = iterCol.previous();
  	  	  	  	  	  iterCol.add(newNode);
  	  	  	  	  	  placed = true;
  	  	  	  	  }
  	  	  	  }
  	  	  	  if(placed==true)
  	  	  	  	  break;
  	  	  	  //if the freespace column is passed over, moves back and inserts
  	  	  	  if(colCM<currentCol)
  	  	  	  {
  	  	  	  	  	tnode = iterCol.previous();
  	  	  	  	  	iterCol.add(newNode);
  	  	  	  	  	placed = true;
  	  	  	  }
  	  	  	  if(placed==true)
  	  	  	  	  break;
  	  	  }
  	  }
  }

  // Pretty-printed version of the board. Use the format "%4s " to
  // print the String version of each tile in a grid.
  // 
  // TARGET COMPLEXITY: O(R * C)
  // R: number of rows
  // C: number of columns
  public String toString()
  {
  	  //sets up the iterator to traverse through the board
  	  ListIterator<TileNode> iter = this.rowMajor.listIterator();
  	  //freeCount tracks what number of free spots have been found
  	  int freeCount = 0;
  	  //tracks the initial or former row and column
  	  int preRow = 0;
  	  int preCol = 0;
  	  //tracks the tile's current row and column
  	  int currentRow = 0;
  	  int currentCol = 0;
  	  //set up string
  	  StringBuilder stBu = new StringBuilder();
  	  String s = " ";
  	  
  	  
  	  while(iter.hasNext())
  	  {
  	  	  //grabs the current node
  	  	  TileNode tnode = iter.next();
  	  	  
  	  	  //grabs the current coordinates
  	  	  currentRow = tnode.getRow();
  	  	  currentCol = tnode.getCol();
  	  	  
  	  	  
  	  	  //prints freespaces from any empty rows above the current tile
  	  	  //	AND prints any remaining freespaces from previous nonempty row
  	  	  while(preRow<currentRow)
  	  	  {	
  	  	  	  	//add the new free spaces
  	  	  	  	freeCount = (this.c-1)-preCol;
  	  	  	  	for(int i=0; i<=freeCount; i++)
  	  	  	  	{
  	  	  	  		s = String.format("%4s ", "-");
  	  	  	  		stBu.append(s);
  	  	  	  	}
  	  	  	  	
  	  	  	  	//after freeCount updates, preCol and preRow are adjusted
  	  	  	  	//		for the next loop
  	  	  	  	preCol = 0;
  	  	  	  	preRow++;
  	  	  	  	stBu.append("\n");
  	  	  }
  	  	  //prints any free spaces in the same row ahead of the current tile,
  	  	  //	prints the tile,
  	  	  //	AND prints any freespaces between tiles
  	  	  if(preRow==currentRow)
  	  	  {
  	  	  	  	//add the new free spaces
  	  	  	  	freeCount = currentCol-preCol;
  	  	  	  	for(int i=0; i<=freeCount; i++)
  	  	  	  	{
  	  	  	  		s = String.format("%4s ", "-");
  	  	  	  		stBu.append(s);
  	  	  	  	}
  	  	  	  	//the add the tile
  	  	  	  	int val = tnode.getTile().getScore();
  	  	  	  	s = String.format("%4s ", String.valueOf(val));
  	  	  	  	stBu.append(s);

  	  	  }
  	  	  //prints the free spaces after the last tile
  	  	  if(iter.hasNext()==false)
  	  	  {
  	  	  	  while(currentRow<=(this.r-1))
  	  	  	  {	
  	  	  	  	//add the new free spaces
  	  	  	  	freeCount = (this.c-1)-preCol;
  	  	  	  	for(int i=0; i<=freeCount; i++)
  	  	  	  	{
  	  	  	  		s = String.format("%4s ", "-");
  	  	  	  		stBu.append(s);
  	  	  	  	}
  	  	  	  	
  	  	  	  	//after freeCount updates, preCol and preRow are adjusted
  	  	  	  	//		for the next loop
  	  	  	  	preCol = 0;
  	  	  	  	currentRow++;
  	  	  	  	stBu.append("\n");
  	  	  	  }
  	  	  }
  	  	  
  	  	  //sets the previous coords up to compare for the next loop/node
  	  	  preRow = currentRow;
  	  	  preCol = currentCol;
  	  }

  	  //the string version of the board is completed and returned
  	  return stBu.toString();
  }
  
  public String debugString()
  {
  	  return "I'm not used.";
  }

  // Shift the tiles of Board in various directions.  Any tiles that
  // collide and should be merged should be changed internally in the
  // board.  Shifts only remove tiles, never add anything.  The shift
  // methods also set the state of the board internally so that a
  // subsequent call to lastShiftMovedTiles() will return true if any
  // Tile moved and false otherwise.  The methods return the score
  // that is generated from the shift which is the sum of the scores
  // all tiles merged during the shift. If no tiles are merged, the
  // return score is 0.
  // 
  // TARGET RUNTIME COMPLEXITY: O(T + max(R,C))
  // TARGET SPACE COMPLEXITY:   O(T + max(R,C))
  // T: the number of non-empty tiles in the board
  // R: number of rows
  // C: number of columns
  public int shiftLeft()
  {  
  	  this.shifted = false;
  	  
  	  //tracks points scored during shift
  	  int shiftScore = 0;
  	  //tracks the free column space and the current row
  	  int freeC = 0;
  	  int currentRow = -1;

  	  
	//creates the iterator through rowMajor
  	  ListIterator<TileNode> iter = rowMajor.listIterator();
  	  Tile prevTile = new TwoNTile(0);
  	  
  	  //move horizontally through the board starting from 
  	  //	the TOP LEFT and finishing at the BOTTOM RIGHT
  	  while(iter.hasNext())					
  	  {	  	  
  	  	  TileNode tnode = iter.next();
  	  	  
  	  	  //if this is a new row
  	  	  if(tnode.getRow()!=currentRow)
  	  	  {
  	  	  	  //this deals with the first tile of a new row
  	  	  	  //currentRow is updated
  	  	  	  currentRow = tnode.getRow();
  	  	  	  //the tile's value is stored for comparison
  	  	  	  prevTile = tnode.getTile();
  	  	  	  //the free column spot is reset
  	  	  	  freeC = 0;
  	  	  	  
  	  	  	  //shifts the tile over if there's room and the tile isn't a brick
  	  	  	  if((tnode.getCol()>=freeC)&&(!(tnode.getTile() instanceof Brick)))
  	  	  	  {
  	  	  	  	  tnode.setCol(freeC);
  	  	  	  	  freeC++;
  	  	  	  }
  	  	  	  //if the tile is a brick, the free column tracker is adjusted
  	  	  	  if(tnode.getTile() instanceof Brick)
  	  	  	  	  freeC = tnode.getCol()+1;
  	  	  }
  	  	  //if this isn't the first tile in the current row
  	  	  else
  	  	  {
  	  	  	  //shifts the tile over if there's room and the tile isn't a brick
  	  	  	  if((tnode.getCol()>=freeC)&&(!(tnode.getTile() instanceof Brick)))
  	  	  	  {
  	  	  	  	  tnode.setCol(freeC);
  	  	  	  	  freeC++;
  	  	  	  }
  	  	  	  //if the tile is a brick, the free column tracker is adjusted
  	  	  	  if(tnode.getTile() instanceof Brick)
  	  	  	  	  freeC = tnode.getCol()+1;
  	  	  	  
  	  	  	  //checks if the current and previous tile can merge
  	  	  	  if(prevTile.mergesWith(tnode.getTile()))
  	  	  	  {
  	  	  	  	  //this assumes that the two tiles can merge
  	  	  	  	  //a new tile is created to store the merge
  	  	  	  	  Tile newTile = new TwoNTile(0);
  	  	  	  	  //the merge is attempted
  	  	  	  	  try{
  	  	  	  	  	  //merges the tiles
  	  	  	  	  	  newTile = prevTile.merge(tnode.getTile());
  	  	  	  	  }
  	  	  	  	  catch (RuntimeException e)
  	  	  	  	  {System.out.println(e);}
  	  	  	  	  
  	  	  	  	  //collects the points earned
  	  	  	  	  shiftScore += newTile.getScore();
  	  	  	  	  
  	  	  	  	  //the iterator is moved back to the previous node
  	  	  	  	  TileNode mergeSpot = iter.previous();
  	  	  	  	  //the previous node's tile is updated
  	  	  	  	  mergeSpot.setTile(newTile);
  	  	  	  	  //moves the iterator to the current node
  	  	  	  	  iter.next();
  	  	  	  	  //deletes the current node
  	  	  	  	  iter.remove();
  	  	  	  	  
  	  	  	  	  //freeC is adjusted
  	  	  	  	  freeC=mergeSpot.getCol()+1;
  	  	  	  } 
  	  	  }
  	  }
//-------------------------------------------------------------
  	  //colMajor is updated to match the nodes of rowMajor
  	  WLinkedList builder[] = new WLinkedList[this.c];
  	  ListIterator<TileNode> iterate = rowMajor.listIterator();
  	  while(iterate.hasNext())
  	  {
  	  	  TileNode currentNode = iterate.next();
  	  	  builder[currentNode.getCol()].addLast(currentNode);
  	  }
  	  colMajor = WLinkedList.coalesce(builder);
//------------------------------------------------------------
  	  return shiftScore;  
  }
//------------------------------------------------------------------------------
//------------------------------------------------------------------------------
  public int shiftRight()
  {
  	  this.shifted = false;
  	  
  	  //tracks points scored during shift
  	  int shiftScore = 0;
  	  //tracks the free column space and the current row
  	  int freeC = this.c-1;
  	  int currentRow = -1;

  	  
  	  //creates the iterator through rowMajor
  	  ListIterator<TileNode> iter = rowMajor.listIterator(rowMajor.size()-1);
  	  Tile prevTile = new TwoNTile(0);
  	  
  	  //move horizontally through the board starting from 
  	  //	the TOP RIGHT and finishing at the BOTTOM LEFT
  	  while(iter.hasPrevious())					
  	  {	  	  
  	  	  TileNode tnode = iter.previous();
  	  	  
  	  	  //if this is a new row
  	  	  if(tnode.getRow()!=currentRow)
  	  	  {
  	  	  	  //this deals with the first tile of a new row
  	  	  	  //currentRow is updated
  	  	  	  currentRow = tnode.getRow();
  	  	  	  //the tile's value is stored for comparison
  	  	  	  prevTile = tnode.getTile();
  	  	  	  //the free column spot is reset
  	  	  	  freeC = this.c-1;
  	  	  	  
  	  	  	  //shifts the tile over if there's room and the tile isn't a brick
  	  	  	  if((tnode.getCol()<=freeC)&&(!(tnode.getTile() instanceof Brick)))
  	  	  	  {
  	  	  	  	  tnode.setCol(freeC);
  	  	  	  	  freeC--;
  	  	  	  }
  	  	  	  //if the tile is a brick, the free column tracker is adjusted
  	  	  	  if(tnode.getTile() instanceof Brick)
  	  	  	  	  freeC = tnode.getCol()-1;
  	  	  }
  	  	  //if this isn't the first tile in the current row
  	  	  else
  	  	  {
  	  	  	  //shifts the tile over if there's room and the tile isn't a brick
  	  	  	  if((tnode.getCol()<=freeC)&&(!(tnode.getTile() instanceof Brick)))
  	  	  	  {
  	  	  	  	  tnode.setCol(freeC);
  	  	  	  	  freeC--;
  	  	  	  }
  	  	  	  //if the tile is a brick, the free column tracker is adjusted
  	  	  	  if(tnode.getTile() instanceof Brick)
  	  	  	  	  freeC = tnode.getCol()-1;
  	  	  	  
  	  	  	  //checks if the current and previous tile can merge
  	  	  	  if(prevTile.mergesWith(tnode.getTile()))
  	  	  	  {
  	  	  	  	  //this assumes that the two tiles can merge
  	  	  	  	  //a new tile is created to store the merge
  	  	  	  	  Tile newTile = new TwoNTile(0);
  	  	  	  	  //the merge is attempted
  	  	  	  	  try{
  	  	  	  	  	  //merges the tiles
  	  	  	  	  	  newTile = prevTile.merge(tnode.getTile());
  	  	  	  	  }
  	  	  	  	  catch (RuntimeException e)
  	  	  	  	  {System.out.println(e);}
  	  	  	  	  
  	  	  	  	  //collects the points earned
  	  	  	  	  shiftScore += newTile.getScore();
  	  	  	  	  
  	  	  	  	  //the iterator is moved back to the previous node
  	  	  	  	  TileNode mergeSpot = iter.next();
  	  	  	  	  //the previous node's tile is updated
  	  	  	  	  mergeSpot.setTile(newTile);
  	  	  	  	  //moves the iterator to the current node
  	  	  	  	  iter.previous();
  	  	  	  	  //deletes the current node
  	  	  	  	  iter.remove();
  	  	  	  	  
  	  	  	  	  //freeC is adjusted
  	  	  	  	  freeC = mergeSpot.getCol()-1;
  	  	  	  } 
  	  	  }
  	  }
//-------------------------------------------------------------
  	  //colMajor is updated to match the nodes of rowMajor
  	  WLinkedList builder[] = new WLinkedList[this.c];
  	  ListIterator<TileNode> iterate = rowMajor.listIterator();
  	  while(iterate.hasNext())
  	  {
  	  	  TileNode currentNode = iterate.next();
  	  	  builder[currentNode.getCol()].addLast(currentNode);
  	  }
  	  colMajor = WLinkedList.coalesce(builder);
//------------------------------------------------------------
  	  return shiftScore;
  }
//------------------------------------------------------------------------------
//------------------------------------------------------------------------------
  public int shiftUp()
  {
  	  this.shifted = false;
  	  
  	  //tracks points scored during shift
  	  int shiftScore = 0;
  	  //tracks the free row space and the current column
  	  int freeR = 0;
  	  int currentCol = -1;

  	  
	//creates the iterator through rowMajor
  	  ListIterator<TileNode> iter = colMajor.listIterator();
  	  Tile prevTile = new TwoNTile(0);
  	  
  	  //move horizontally through the board starting from 
  	  //	the TOP LEFT and finishing at the BOTTOM RIGHT
  	  while(iter.hasNext())					
  	  {	  	  
  	  	  TileNode tnode = iter.next();
  	  	  
  	  	  //if this is a new row
  	  	  if(tnode.getRow()!=currentCol)
  	  	  {
  	  	  	  //this deals with the first tile of a new row
  	  	  	  //currentRow is updated
  	  	  	  currentCol = tnode.getCol();
  	  	  	  //the tile's value is stored for comparison
  	  	  	  prevTile = tnode.getTile();
  	  	  	  //the free column spot is reset
  	  	  	  freeR = 0;
  	  	  	  
  	  	  	  //shifts the tile over if there's room and the tile isn't a brick
  	  	  	  if((tnode.getRow()>=freeR)&&(!(tnode.getTile() instanceof Brick)))
  	  	  	  {
  	  	  	  	  tnode.setRow(freeR);
  	  	  	  	  freeR++;
  	  	  	  }
  	  	  	  //if the tile is a brick, the free column tracker is adjusted
  	  	  	  if(tnode.getTile() instanceof Brick)
  	  	  	  	  freeR = tnode.getRow()+1;
  	  	  }
  	  	  //if this isn't the first tile in the current row
  	  	  else
  	  	  {
  	  	  	  //shifts the tile over if there's room and the tile isn't a brick
  	  	  	  if((tnode.getRow()>=freeR)&&(!(tnode.getTile() instanceof Brick)))
  	  	  	  {
  	  	  	  	  tnode.setRow(freeR);
  	  	  	  	  freeR++;
  	  	  	  }
  	  	  	  //if the tile is a brick, the free column tracker is adjusted
  	  	  	  if(tnode.getTile() instanceof Brick)
  	  	  	  	  freeR = tnode.getRow()+1;
  	  	  	  
  	  	  	  //checks if the current and previous tile can merge
  	  	  	  if(prevTile.mergesWith(tnode.getTile()))
  	  	  	  {
  	  	  	  	  //this assumes that the two tiles can merge
  	  	  	  	  //a new tile is created to store the merge
  	  	  	  	  Tile newTile = new TwoNTile(0);
  	  	  	  	  //the merge is attempted
  	  	  	  	  try{
  	  	  	  	  	  //merges the tiles
  	  	  	  	  	  newTile = prevTile.merge(tnode.getTile());
  	  	  	  	  }
  	  	  	  	  catch (RuntimeException e)
  	  	  	  	  {System.out.println(e);}
  	  	  	  	  
  	  	  	  	  //collects the points earned
  	  	  	  	  shiftScore += newTile.getScore();
  	  	  	  	  
  	  	  	  	  //the iterator is moved back to the previous node
  	  	  	  	  TileNode mergeSpot = iter.previous();
  	  	  	  	  //the previous node's tile is updated
  	  	  	  	  mergeSpot.setTile(newTile);
  	  	  	  	  //moves the iterator to the current node
  	  	  	  	  iter.next();
  	  	  	  	  //deletes the current node
  	  	  	  	  iter.remove();
  	  	  	  	  
  	  	  	  	  //freeC is adjusted
  	  	  	  	  freeR--;
  	  	  	  } 
  	  	  }
  	  }
//-------------------------------------------------------------
  	  //rowMajor is updated to match the nodes of colMajor
  	  WLinkedList builder[] = new WLinkedList[this.r];
  	  ListIterator<TileNode> iterate = colMajor.listIterator();
  	  while(iterate.hasNext())
  	  {
  	  	  TileNode currentNode = iterate.next();
  	  	  builder[currentNode.getRow()].addLast(currentNode);
  	  }
  	  rowMajor = WLinkedList.coalesce(builder);
//------------------------------------------------------------
  	  return shiftScore;
  }
//------------------------------------------------------------------------------
//------------------------------------------------------------------------------
  public int shiftDown()
  {
  	  this.shifted = false;
  	  
  	  //tracks points scored during shift
  	  int shiftScore = 0;
  	  //tracks the free row space and the current column
  	  int freeR = this.r-1;
  	  int currentCol = -1;

  	  
  	  //creates the iterator through rowMajor
  	  ListIterator<TileNode> iter = colMajor.listIterator(colMajor.size()-1);
  	  Tile prevTile = new TwoNTile(0);
  	  
  	  //move horizontally through the board starting from 
  	  //	the TOP RIGHT and finishing at the BOTTOM LEFT
  	  while(iter.hasPrevious())					
  	  {	  	  
  	  	  TileNode tnode = iter.previous();
  	  	  
  	  	  //if this is a new row
  	  	  if(tnode.getCol()!=currentCol)
  	  	  {
  	  	  	  //this deals with the first tile of a new row
  	  	  	  //currentRow is updated
  	  	  	  currentCol = tnode.getCol();
  	  	  	  //the tile's value is stored for comparison
  	  	  	  prevTile = tnode.getTile();
  	  	  	  //the free column spot is reset
  	  	  	  freeR = this.r-1;
  	  	  	  
  	  	  	  //shifts the tile over if there's room and the tile isn't a brick
  	  	  	  if((tnode.getRow()<=freeR)&&(!(tnode.getTile() instanceof Brick)))
  	  	  	  {
  	  	  	  	  tnode.setRow(freeR);
  	  	  	  	  freeR--;
  	  	  	  }
  	  	  	  //if the tile is a brick, the free column tracker is adjusted
  	  	  	  if(tnode.getTile() instanceof Brick)
  	  	  	  	  freeR = tnode.getRow()-1;
  	  	  }
  	  	  //if this isn't the first tile in the current row
  	  	  else
  	  	  {
  	  	  	  //shifts the tile over if there's room and the tile isn't a brick
  	  	  	  if((tnode.getRow()<=freeR)&&(!(tnode.getTile() instanceof Brick)))
  	  	  	  {
  	  	  	  	  tnode.setRow(freeR);
  	  	  	  	  freeR--;
  	  	  	  }
  	  	  	  //if the tile is a brick, the free column tracker is adjusted
  	  	  	  if(tnode.getTile() instanceof Brick)
  	  	  	  	  freeR = tnode.getRow()-1;
  	  	  	  
  	  	  	  //checks if the current and previous tile can merge
  	  	  	  if(prevTile.mergesWith(tnode.getTile()))
  	  	  	  {
  	  	  	  	  //this assumes that the two tiles can merge
  	  	  	  	  //a new tile is created to store the merge
  	  	  	  	  Tile newTile = new TwoNTile(0);
  	  	  	  	  //the merge is attempted
  	  	  	  	  try{
  	  	  	  	  	  //merges the tiles
  	  	  	  	  	  newTile = prevTile.merge(tnode.getTile());
  	  	  	  	  }
  	  	  	  	  catch (RuntimeException e)
  	  	  	  	  {System.out.println(e);}
  	  	  	  	  
  	  	  	  	  //collects the points earned
  	  	  	  	  shiftScore += newTile.getScore();
  	  	  	  	  
  	  	  	  	  //the iterator is moved back to the previous node
  	  	  	  	  TileNode mergeSpot = iter.next();
  	  	  	  	  //the previous node's tile is updated
  	  	  	  	  mergeSpot.setTile(newTile);
  	  	  	  	  //moves the iterator to the current node
  	  	  	  	  iter.previous();
  	  	  	  	  //deletes the current node
  	  	  	  	  iter.remove();
  	  	  	  	  
  	  	  	  	  //freeC is adjusted
  	  	  	  	  freeR = mergeSpot.getRow()-1;
  	  	  	  } 
  	  	  }
  	  }
//-------------------------------------------------------------
  	  //rowMajor is updated to match the nodes of colMajor
  	  WLinkedList builder[] = new WLinkedList[this.r];
  	  ListIterator<TileNode> iterate = colMajor.listIterator();
  	  while(iterate.hasNext())
  	  {
  	  	  TileNode currentNode = iterate.next();
  	  	  builder[currentNode.getRow()].addLast(currentNode);
  	  }
  	  rowMajor = WLinkedList.coalesce(builder);
//------------------------------------------------------------
  	  return shiftScore;
  }

}
