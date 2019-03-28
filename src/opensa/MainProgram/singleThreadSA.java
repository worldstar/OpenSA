package opensa.MainProgram;
import opensa.Solution.chromosome;
import opensa.Move.*;
import opensa.ObjectiveFunctions.*;
import opensa.Selection.SelectI;

/**
 * <p>Title: The OpenSA project is a open structure of Simulated Annealing</p>
 * <p>Description: SA main processes the whole procedures and including the reheating technique.</p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: Yuan-Ze University</p>
 * @author Chen, Shih-HSin
 * @version 1.0
 */

public class singleThreadSA implements MainI {
  public static void main(String[] args) {
  }

  chromosome chromosome1, best, tempChromosomes[];//soln classes.
  MoveI[] moves;                                  //some moves are used here
  ObjectiveFunctionI ObjectiveFunction1[];        //evalue the objective value by ObjectiveFunction.
  SelectI Selection;                              //The select a soln from a move
  /**
   * SA parameters.
   */
  double initialTemperature, alpha, currentTemperature = 0, finalTemperature;
  int numberOfMoves;

  public void setData(chromosome chromosome1, MoveI[] moves, ObjectiveFunctionI[] ObjectiveFunction1, SelectI Selection,
                      double initialTemperature, double alpha, double finalTemperature, int numberOfMoves) {
    this.chromosome1 = chromosome1;
    this.moves = moves;
    this.ObjectiveFunction1 = ObjectiveFunction1;
    this.Selection = Selection;
    this.initialTemperature = initialTemperature;
    currentTemperature = initialTemperature;
    this.alpha = alpha;
    this.finalTemperature = finalTemperature;
    this.numberOfMoves = numberOfMoves;

    best = new chromosome();
    best.setGenotypeAndLength(chromosome1.getEncodeType(), chromosome1.getLength(), chromosome1.getObjectiveValue().length);
    tempChromosomes = new chromosome[moves.length];
    for(int i = 0 ; i < moves.length ; i ++ ){
      tempChromosomes[i] = new chromosome();
      tempChromosomes[i].setGenotypeAndLength(chromosome1.getEncodeType(), chromosome1.getLength(), chromosome1.getObjectiveValue().length);
    }
  }

  /**
   * The main procedure of SA.
   */
  public void startSA() {
    int counter = 0;
    chromosome1 = intialStage(chromosome1);

    int numberOfUnimproved = 0;
    while(currentTemperature >= finalTemperature ){//&& counter < 5000
      counter++;
      //moves in the same temperature
      for(int i = 0 ; i < numberOfMoves ; i ++ ){
        //to do local search by different move program.
        tempChromosomes = getMoves();
        //evaluate the solutions
        tempChromosomes = evaulateObjectives(tempChromosomes, moves);

        //used bianry tournament to select better moves. We assign it to the first tempChromosomes to store it.
        tempChromosomes[0].setSolution(selectBetterMoves(tempChromosomes).getSolution());

        if(acceptanceRule()){
          numberOfUnimproved = 0;
        }
        else{
          numberOfUnimproved ++ ;
        }
        //re-heating.
        reheating(numberOfUnimproved,  counter);
      }//end total local search in the current temperature.
      //decrease temperature
      currentTemperature *= alpha;
    }// end the loop of temperature decreasing

    //System.out.println("Better soln found "+counter+"\tbest "+best.getObjectiveValue()[0]);
    for(int i = 0 ; i < best.getSolution().length ; i ++ ){
      //System.out.print(best.getSolution()[i]+" ");
    }
  }


  final public chromosome intialStage(chromosome _chromsome1){
    //evaluate the initial solution
    for(int k = 0 ; k < ObjectiveFunction1.length ; k ++ ){
      _chromsome1 = calcObjectiveValue(_chromsome1, new int[]{0, 0}, k);
    }
    //System.out.println("The obj value is "+_chromsome1.getObjectiveValue()[0]);
    //set the initial solution as the best solution.
    acceptSolution(best, _chromsome1);
    /*
    for(int i = 0 ; i < best.getSolution().length ; i ++ ){
      System.out.print(_chromsome1.getSolution()[i]+" ");
    }
        */

    return _chromsome1;
  }

  public chromosome[] getMoves(){
    //to do local search by different move program.
    for(int j = 0 ; j < moves.length ; j ++ ){
      moves[j].setData(chromosome1);
      moves[j].startMutation();
      tempChromosomes[j] = moves[j].getMutationResult();
    }
    return tempChromosomes;
  }

  public chromosome calcObjectiveValue(chromosome tempCh, int cutPoints[], int indexOfObjective){
    ObjectiveFunction1[indexOfObjective].setData(tempCh, indexOfObjective);
    ObjectiveFunction1[indexOfObjective].calcObjective();
    return ObjectiveFunction1[indexOfObjective].getchromosome();
  }

  public chromosome[] evaulateObjectives(chromosome _tempChromosomes[], MoveI _moves[]){
    for(int j = 0 ; j < _moves.length ; j ++ ){
      for(int k = 0 ; k < ObjectiveFunction1.length ; k ++ ){
        _tempChromosomes[j] = calcObjectiveValue(_tempChromosomes[j], _moves[j].getCutPoints(), k);
      }
    }
    return _tempChromosomes;
  }

  //If the new soln is accepted, it return true.
  public boolean acceptanceRule(){
    if(ObjectiveFunction1.length == 1){//a single objective problem.
      //System.out.println(": new soln: "+tempChromosomes[0].getObjectiveValue()[0]+" original chromosome1 "+chromosome1.getObjectiveValue()[0]);
      //if the solution is improved, accepted it.
      if(tempChromosomes[0].getObjectiveValue()[0] < chromosome1.getObjectiveValue()[0]){
        //set the temp solution as the overide solution.
        acceptSolution(chromosome1, tempChromosomes[0]);

        //if the solution is better than current best solution, replace it.
        if(chromosome1.getObjectiveValue()[0] < best.getObjectiveValue()[0]){
          acceptSolution(best, chromosome1);
          //System.out.println("Better soln found \tbest "+best.getObjectiveValue()[0]);
        }
        return true;
      }
      else if(BolzmanFunction(tempChromosomes[0].getObjectiveValue()[0], chromosome1.getObjectiveValue()[0])){//else, to test the Bolzman function test.
        acceptSolution(chromosome1, tempChromosomes[0]);
        //System.out.println("Bolzman");
        return false;
      }
      else{
        return false;
      }
    }
    else{//a multiple objective problem. We design it later.
      System.out.println("We design it later.");
      //System.exit(0);
    }
    return false;
  }

  private chromosome selectBetterMoves(chromosome tempCh[]){
    Selection.setData(tempCh);
    Selection.startToSelect();

    return Selection.getSelectionResult();
  }

  //replaces the oldSoln by source
  public void acceptSolution(chromosome destination, chromosome source){
    //oldSoln.realGenes = tempCh.realGenes;
    System.arraycopy(source.genes, 0, destination.genes, 0, destination.genes.length);
    destination.setObjValue(source.getObjectiveValue());
  }

  //to generate a probability to accept worse soln.
  final public boolean BolzmanFunction(double Ztemp, double Zcurrent){
    double randomProbability = Math.random();
    double BolzmanValue = Math.exp(-(Ztemp - Zcurrent)/currentTemperature);
    //boolean accept = false;

    if(randomProbability < BolzmanValue){
      return true;
    }
    else{
      return false;
    }
  }

  //To give a chance to heat up current temperature
  final public void reheating(int numberOfUnimproved, int counter){
    //reheating
    if(numberOfUnimproved == 20){
      currentTemperature += initialTemperature/(counter*0.05);
      numberOfUnimproved = 0;
      //System.out.println("re-heating: "+currentTemperature+"\tbest "+best.getObjectiveValue()[0]);
      //System.out.println(best.getObjectiveValue()[0]);
      acceptSolution(chromosome1, best);//replace
    }
  }

  /**
   * You can put your own method here or extends the singleThreadSA to override the method.
   */
  public void additionalMethods(){

  }

  /**
   * @return The best objective value has been found.
   */
  public double[] getBestObjectiveValue() {
    return best.getObjectiveValue();
  }

  /**
   * @return The best solution has been found.
   */
  public chromosome getBestSolution() {
    return best;
  }
}