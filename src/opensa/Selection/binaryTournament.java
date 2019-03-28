package opensa.Selection;
import opensa.Solution.chromosome;
/**
 * <p>Title: The OpenSA project is a open structure of Simulated Annealing</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: Yuan-Ze University</p>
 * @author Chen, Shih-HSin
 * @version 1.0
 */

public class binaryTournament implements SelectI {

  chromosome chromosomes[], newChromosome;

  public void setData(chromosome chromosomes[]){
    this.chromosomes = chromosomes;
    newChromosome = new chromosome();
    newChromosome.setGenotypeAndLength(chromosomes[0].getEncodeType(), chromosomes[0].getLength(), chromosomes[0].getObjectiveValue().length);
  }

  //simply to determine which one is better by fitness value.
  //the chromosome has smaller fittness value is better from we use the criteria of Goldberg's fitness assignment.
  public chromosome setData(chromosome chromosome1, chromosome chromosome2){

    if(chromosome1.getObjectiveValue()[0] <= chromosome2.getObjectiveValue()[0]){
      return chromosome1;
    }
    return chromosome2;
  }

  public void startToSelect() {
    //to satisfy the sizeOfPop. It apply the elitism first.
    chromosome selectedChromosome1 = chromosomes[0];
    for(int i = 0 ; i < chromosomes.length - 1 ; i ++ ){
      selectedChromosome1 = setData(selectedChromosome1, chromosomes[i+1]);
    }
    newChromosome.setSolution(selectedChromosome1.getSolution());
  }

  public chromosome getSelectionResult() {
    return newChromosome;
  }

  public static void main(String[] args) {
  }
}