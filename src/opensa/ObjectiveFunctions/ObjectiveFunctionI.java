package opensa.ObjectiveFunctions;
import opensa.Solution.chromosome;
/**
 * <p>Title: The OpenSA project is a open structure of Simulated Annealing</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: Yuan-Ze University</p>
 * @author Chen, Shih-HSin
 * @version 1.0
 */
public interface ObjectiveFunctionI {
  public void setData(chromosome chromosome1, int indexOfObjective);
  public void setOriginalSolution(chromosome originalChromosome);

  public void setFirstImplement(boolean firstImplement);
  public void calcObjective();
  public chromosome getchromosome();
  //public double[] getObjectiveValues(int index);
  //public double getMinObjectiveValue();
  //public double getMaxObjectiveValue();
}
