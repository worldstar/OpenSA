package opensa.ObjectiveFunctions;

/**
 * <p>Title: The OpenSA project is a open structure of Simulated Annealing</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: Yuan-Ze University</p>
 * @author Chen, Shih-HSin
 * @version 1.0
 */

public interface QAPObjectiveFunctionI extends ObjectiveFunctionI{
  public void setQAPData(int flow[][], int distance[][]);
  public void setCutPoints(int[] cutPoints);
}