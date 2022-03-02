package cellsociety.model.simulations;

/**
 * helper class for percolation
 *
 * @author David Wu
 */


public class UnionFinder {

  private int[] myID;
  private int myComponents;


  public UnionFinder(int numOfElements) {
    myComponents = numOfElements;
    myID = new int[numOfElements];

    initialize(numOfElements);
  }

  private void initialize(int numOfElements) {

    for (int i = 0; i < numOfElements; i++) {
      myID[i] = i;
    }
  }

  public boolean isConnected(int p, int q) {
    return myID[p] == myID[q];
  }

  public void union(int p, int q) {
    if (isConnected(p, q)) {
      return;
    }

    int pid = myID[p];

    for (int i = 0; i < myID.length; i++) {
      if (myID[i] == pid) {
        myID[i] = myID[q];
      }

      myComponents--;
    }
  }
}
