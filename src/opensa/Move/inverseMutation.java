package opensa.Move;
import opensa.Solution.chromosome;

/**
 * <p>Title: The OpenSA project</p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: Yuan-Ze University</p>
 * @author Chen, Shih-Hsin
 * @version 1.0
 * Suppose there is 8 bits of a chromosome. The sequence is as following.
 * Chromsome 1: 6 3 7 2 5 0 1 4
 *
 * Then, we random generate two cut points at the position
 * Chromsome 1: 6 3 | 7 2 5 0 | 1 4
 *
 * Therefore, the new chromosome becomes:
 * Chromsome 1: 6 3 0 5 2 7 1 4
 */

public class inverseMutation implements MoveI{
  public inverseMutation() {
  }

  double mutationRate;            //mutation rate
  int popSize, chromosomeLength;  //size of population and number of digits of chromosome
  int cutPoint1, cutPoint2;       //the genes between the two points are inversed
  chromosome chromosome1, newChromosome;

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

  public void startMutation(){
    setCutpoint();
    newChromosome.setSolution(inverseGenes(chromosome1).getSolution());
  }

  private void setCutpoint(){
    boolean theSame = true;
    cutPoint1 = (int)(Math.random() * chromosomeLength);
    cutPoint2 = (int)(Math.random() * chromosomeLength);
    //cutPoint1 = 3; //default for test
    //cutPoint2 = 6; //default for test

    if(cutPoint1 == cutPoint2){
      cutPoint1 -=  (int)(Math.random()*cutPoint1);
      //increase the position of cutPoint2
      cutPoint2 += (int)((chromosomeLength - cutPoint2)*Math.random());

      //double check it.
      if(cutPoint1 == cutPoint2){
        //setCutpoint();
      }
    }

    //swap
    if(cutPoint1 > cutPoint2){
      int temp = cutPoint2;
      cutPoint2 = cutPoint1;
      cutPoint1 = temp;
    }
  }


  private chromosome inverseGenes(chromosome _chromosome){
    int length = cutPoint2 - cutPoint1  + 1;
    int backupGenes[] = new int[length];
    int counter = 0;

    //store the genes at backupGenes.
    for(int i = cutPoint1 ; i <= cutPoint2 ; i ++ ){
      backupGenes[counter++] = _chromosome.genes[i];
    }

    counter = 0;
    //write data of backupGenes into the genes
    for(int i = cutPoint2; i >= cutPoint1 ; i --){
      _chromosome.genes[i] = backupGenes[counter++];
    }
    return _chromosome;
  }

  public chromosome getMutationResult(){
    return newChromosome;
  }

  public int[] getCutPoints(){
    return new int[]{cutPoint1, cutPoint2};
  }

  public static void main(String[] args) {
    inverseMutation inverse1 = new inverseMutation();
  }

}