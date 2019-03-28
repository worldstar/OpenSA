package opensa.MainProgram;
import opensa.Solution.chromosome;
import opensa.Move.MoveI;
/**
 * <p>Title: The OpenSA project is a open structure of Simulated Annealing</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: Yuan-Ze University</p>
 * @author Chen, Shih-HSin
 * @version 1.0
 */

public class singleThreadSAforContinuous extends singleThreadSA implements MainContinuousI{
  double lwBounds[];//lower bound
  double upBound[]; //upper bound

  /**
   * @param lwBounds lower bound
   * @param upBound  upper bound
   */
  public void setBounds(double lwBounds[], double upBound[]){
    this.lwBounds = lwBounds;
    this.upBound= upBound;
  }

  public void startSA() {
    int counter = 0;
    chromosome1 = intialStage(chromosome1);
    int numberOfUnimproved = 0;

    while(currentTemperature >= finalTemperature && counter < 5000){
      counter++;
      //System.out.println(counter);
      //moves in the same temperature numberOfMoves
      for(int i = 0 ; i < numberOfMoves ; i ++ ){
        //to do local search by different move program.
        tempChromosomes = getMoves();
        //System.out.println("\n"+tempChromosomes[0]);
        //evaluate the solutions
        tempChromosomes = evaulateObjectives(tempChromosomes, moves);

        if(acceptanceRule()){
          numberOfUnimproved = 0;
        }
        else{
          numberOfUnimproved ++ ;
        }
        //reheating
        reheating(numberOfUnimproved,  counter);
      }//end total local search in the current temperature.

      //decrease temperature
      currentTemperature *= alpha;
    }// end the loop of temperature decreasing

    //System.out.print("\nFinal soln\n "+"best obj = "+best.getObjectiveValue()[0]+"\t X1 and X2 ->\t");
    for(int i = 0 ; i < best.getSolution().length ; i ++ ){
      //System.out.print(best.realGenes[i]+" ");
    }
  }

  //replaces the oldSoln by source
  public void acceptSolution(chromosome destination, chromosome source){
    System.arraycopy(source.realGenes, 0, destination.realGenes, 0, destination.realGenes.length);
    destination.setObjValue(source.getObjectiveValue());
  }


}