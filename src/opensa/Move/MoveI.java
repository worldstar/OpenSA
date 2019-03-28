package opensa.Move;
import opensa.Solution.chromosome;
/**
 * <p>Title: The OpenSA project is a open structure of Simulated Annealing</p>
 * <p>Description: The interface defines the behavior of the Move</p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: Yuan-Ze University</p>
 * @author Chen, Shih-HSin
 * @version 1.0
 */

public interface MoveI {
  public void setData(chromosome chromosome1);
  public void setData(double mutationRate, chromosome chromosome1);
  public void startMutation();
  public chromosome getMutationResult();
  public int[] getCutPoints();
}