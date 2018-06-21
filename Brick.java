// Concrete implementation of a Tile. Bricks do not merge with
// anything and do not move.
public class Brick extends Tile {

  // Create a Brick
  public Brick()
  {
  }

  // Should always return false
  public boolean mergesWith(Tile moving)
  {
  	  return false;
  }

  // Should throw an informative runtime exception as bricks never
  // merge with anything
  public Tile merge(Tile moving) throws RuntimeException
  {
  	  throw new RuntimeException("Bricks cannot merge with tiles!");
  }

  // Always 0
  public int getScore()
  {
  	  return 0;
  }

  // Always false
  public boolean isMovable()
  {
  	  return false;
  }

  // Return the string "BRCK" <--  "BRICK" *
  public String toString()
  {
  	  String s = "BRICK";
  	  return s;
  }

  // true if the other is a Brick and false otherwise
  public boolean equals(Object other)
  {
  	  //is 'other' a Brick?
  	  if((other.getClass().equals(Brick.class)) &&
  	  	  other!=null)
  	  {  
  	  	  //'other' is a brick
  	  	  return true;
  	  }
  	  //'other' is not a brick
  	  return false;
  }

}
