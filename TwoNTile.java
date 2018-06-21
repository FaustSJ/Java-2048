import java.util.Scanner;

// Concrete implementation of a Tile. TwoNTiles merge with each other
// but only if they have the same value.
public class TwoNTile extends Tile {
  private int value;

  // Create a tile with the given value of n; should be a power of 2
  // though no error checking is done
  public TwoNTile(int n){
  	if(n<2)
  		System.out.println("making tile of val 0");
    this.value = n;
  }

  // Returns true if this tile merges with the given tile. "this"
  // (calling tile) is assumed to be the stationary tile while moving
  // is presumed to be the moving tile. TwoNTiles only merge with
  // other TwoNTiles with the same internal value.
  public boolean mergesWith(Tile moving){
    if(!(moving instanceof TwoNTile)){
      return false;
    }
    TwoNTile tile = (TwoNTile) moving;
    return this.value == tile.value;
  }

  // Produce a new tile which is the result of merging this tile with
  // the other. For TwoNTiles, the new Tile will be another TwoNTile
  // and will have the sum of the two merged tiles for its value.
  // Throw a runtime exception with a useful error message if this
  // tile and other cannot be merged.
  public Tile merge(Tile moving){
    if(!this.mergesWith(moving)){
      String msg = 
        String.format("Can't merge tiles |%s| and |%s|",
                      this.toString(), moving.toString());
      throw new RuntimeException(msg);
    }
    TwoNTile tile = (TwoNTile) moving;
    TwoNTile merged = new TwoNTile(this.value + tile.value);
    return merged;
  }

  // Get the score for this tile. The score for TwoNTiles are its face
  // value.
  public int getScore(){
    return this.value;
  }

  // Return a string representation of the tile
  public String toString(){
    return String.format("%s",this.value);
  }

  // REQUIRED: Determine if this TwoNTile is equal to another object
  // which is only true when the other object is a TwoNTile with the
  // same value as this tile. Required for tests to work correctly.
  public boolean equals(Object other){
    if(!(other instanceof TwoNTile)){
      return false;
    }
    TwoNTile tile = (TwoNTile) other;
    return this.value == tile.value;
  }

}
