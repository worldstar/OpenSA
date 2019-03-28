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

public class ObjectiveFunctionContinuous implements ObjectiveFunctionContinuousI{
  chromosome chromosome1, originalChromosome;
  int length, indexOfObjective;
  boolean firstImplement = true;
  double objectiveValue = 0;


  public void setData(chromosome chromosome1, int indexOfObjective){
    this.chromosome1 = chromosome1;
    this.length = chromosome1.getLength();
    this.indexOfObjective = indexOfObjective;
  }

  public void setOriginalSolution(chromosome originalChromosome){
    this.originalChromosome = originalChromosome;
  }

  public void setFirstImplement(boolean firstImplement){
    this.firstImplement = firstImplement;
  }

  public void calcObjective(){
    //first time to evaluate objective value, it needs to evaluate all combinations.
    double obj;
    double objectives[] = chromosome1.getObjectiveValue();
    obj = evaluateAll();
    objectives[indexOfObjective] = obj;
    chromosome1.setObjValue(objectives);


  }

  public double evaluateAll(){
    double obj = 0;
    double term1 = Math.pow(Math.pow(chromosome1.realGenes[0], 2)+chromosome1.realGenes[1] - 11, 2);
    double term2 = Math.pow(chromosome1.realGenes[0] + Math.pow(chromosome1.realGenes[1], 2)- 7, 2);
    double term3 = 0.1*(Math.pow(chromosome1.realGenes[0] - 3, 2) + Math.pow(chromosome1.realGenes[1] - 2, 2));
    obj = term1 + term2 + term3;
    //System.out.print("\nin the Obj class, obj "+obj+" term1 "+term1+"\t"+" term2 "+term2+"tn"+" term3 "+term3+"\n");
    return obj;
  }

  public chromosome getchromosome(){
    return chromosome1;
  }

  public double[] getObjectives(){
    return chromosome1.getObjectiveValue();
  }

  public static void main(String[] args) {
    ObjectiveFunctionContinuous ObjectiveFunctionContinuous1 = new ObjectiveFunctionContinuous();
    chromosome chromosome1 = new chromosome();
    chromosome1.setGenotypeAndLength(true, 2, 1);
    chromosome1.realGenes = new double[]{-4.13327106, 4.6062468231906};
    ObjectiveFunctionContinuous1.setData(chromosome1, 0);
    ObjectiveFunctionContinuous1.calcObjective();
    System.out.println(ObjectiveFunctionContinuous1.getObjectives()[0]);
  }

}