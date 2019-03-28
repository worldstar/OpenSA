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

public class ObjectiveFunctionQAP implements QAPObjectiveFunctionI {

  chromosome chromosome1, originalChromosome;
  int[][] flow, distance;
  int[] cutPoints;
  int length, indexOfObjective;
  boolean firstImplement = true;
  double objectiveValue = 0;

  public void setQAPData(int[][] flow, int[][] distance) {
    this.flow = flow;
    this.distance = distance;
  }

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

  public void setCutPoints(int[] cutPoints){
    this.cutPoints = cutPoints;
  }

  public void calcObjective(){
    //first time to evaluate objective value, it needs to evaluate all combinations.
    double obj;
    double objectives[] = chromosome1.getObjectiveValue();
    if(firstImplement == true){
      obj = evaluateAll();
    }
    else{
      obj = evaluatePartialCombinations();
    }
    objectives[indexOfObjective] = obj;
    chromosome1.setObjValue(objectives);
  }

  public double evaluateAll(){
    double obj = 0;
    for(int i = 0 ; i < length - 1 ; i ++ ){//from point i
      for(int j = i+1 ; j < length  ; j ++ ){//to point j
        obj += objectiveValueBetweenTwoDepartments(i, j, chromosome1);
      }
    }

    return obj;
  }

  public double evaluatePartialCombinations(){
    double obj = originalChromosome.getObjectiveValue()[indexOfObjective];
    System.out.println("evaluatePartialCombinations obj "+obj);
    for(int i = 0 ; i < length ; i ++ ){
      System.out.print(chromosome1.getSolution()[i]+" ");
    }

    //eliminate the obj values between the two cutpoints
    for(int i = cutPoints[0] ; i <= cutPoints[1] ; i ++ ){//from point i
      for(int j = 0 ; j < length ; j ++ ){//to point j
        if(i != j){
          obj -= objectiveValueBetweenTwoDepartments(i, j, originalChromosome);
        }
      }
    }
    System.out.println("\nobj "+obj);

    for(int i = cutPoints[0] ; i <= cutPoints[1] ; i ++ ){//from point i
      for(int j = 0 ; j < length ; j ++ ){//to point j
        if(i != j){
          obj += objectiveValueBetweenTwoDepartments(i, j, chromosome1);
        }
      }
    }

    return obj;
  }



  //to calculate the flow from a department to others departments.
  /**
   * @param X The array position
   * @return
   */
  public double objectiveValueBetweenTwoDepartments(int X, int Y, chromosome _chromosome1){
    int index1 = _chromosome1.getSolution()[X];
    int index2 = _chromosome1.getSolution()[Y];

    return flow[index1][index2]*distance[X][Y];
  }

  public chromosome getchromosome(){
    return chromosome1;
  }


  public static void main(String[] args) {
  }
}