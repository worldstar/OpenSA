package opensa.Solution;

/**
 * <p>Title: The OpenSA project is a open structure of Simulated Annealing</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: Yuan-Ze University</p>
 * @author Chen, Shih-HSin
 * @version 1.0
 */

public interface SolutionI {
  public void setSolution(int soln[]);
  public void setObjValue(double val[]);
  public int[] getSolution();
  public double[] getObjectiveValue();
/*
  //to store the current best solution.
  public void setBestSolution(int soln[][]);
  public void setBestObjectiveValue(double val[][]);
  public int[][] getBestSolutions();
  public double[][] getBestObjectiveValues();
*/
}