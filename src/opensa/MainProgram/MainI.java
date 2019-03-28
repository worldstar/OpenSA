package opensa.MainProgram;
import opensa.Solution.chromosome;
import opensa.Move.*;
import opensa.ObjectiveFunctions.*;
import opensa.Selection.SelectI;

/**
 * <p>Title: The OpenSA project is a open structure of Simulated Annealing</p>
 * <p>Description: To define the characteristic of SA main.</p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: Yuan-Ze University</p>
 * @author Chen, Shih-HSin
 * @version 1.0
 */

public interface MainI {
  public void setData(chromosome chromosome1, MoveI moves[], ObjectiveFunctionI ObjectiveFunction1[], SelectI Selection,
                      double initialTemperature, double alpha, double finalTemperature, int numberOfMoves);
  public void startSA();
  public chromosome[] getMoves();
  public chromosome calcObjectiveValue(chromosome tempCh, int cutPoints[], int indexOfObjective);
  public boolean acceptanceRule();
  public void acceptSolution(chromosome destination, chromosome source);
  public boolean BolzmanFunction(double Ztemp, double Zcurrent);
  public void reheating(int numberOfUnimproved, int counter);
  public void additionalMethods();
  public double[] getBestObjectiveValue();
  public chromosome getBestSolution();
}