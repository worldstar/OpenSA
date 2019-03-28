package opensa.Move;
import opensa.Solution.chromosome;
/**
 * <p>Title: The OpenSA project is a open structure of Simulated Annealing</p>
 * <p>The real code type for continuous problem</P>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: Yuan-Ze University</p>
 * @author Chen, Shih-HSin
 * @version 1.0
 */

public class realValueMutation implements RealMoveI{
  double mutationRate;            //mutation rate
  int popSize, chromosomeLength;  //size of population and number of digits of chromosome
  int cutPoint1, cutPoint2;       //the genes between the two points are inversed
  chromosome chromosome1, newChromosome;
  double lwBounds[];//lower bound
  double upBound[]; //upper bound

  public void setData(chromosome chromosome1){
    setData(1, chromosome1);
  }

  public void setData(double mutationRate, chromosome chromosome1){
    this.mutationRate = 1;
    this.chromosome1 = chromosome1;
    chromosomeLength = chromosome1.getLength();
    newChromosome = new chromosome();
    newChromosome.setGenotypeAndLength(chromosome1.getEncodeType(), chromosomeLength, chromosome1.getObjectiveValue().length);
  }


  public void setBounds(double lwBounds[], double upBound[]){
    this.lwBounds = lwBounds;
    this.upBound= upBound;
  }

  public void startMutation(){
    newChromosome.realGenes = localSearchGenes(chromosome1).realGenes;
  }



  private chromosome localSearchGenes(chromosome _chromosome){
    //do local search on each gene
    for(int k = 0 ; k < _chromosome.getLength() ; k ++ ){
      //decide to move up or down
      if (Math.random() > 0.5) { //move up
        _chromosome.realGenes[k] += (upBound[k] - _chromosome.realGenes[k]) * Math.random();
      }
      else {
        _chromosome.realGenes[k] -= (_chromosome.realGenes[k] - lwBounds[k]) * Math.random();
      }
    }

    return _chromosome;
  }

  public int[] getCutPoints(){
    return new int[]{cutPoint1, cutPoint2};
  }

  public chromosome getMutationResult(){
    return newChromosome;
  }

  public static void main(String[] args) {
  }
}