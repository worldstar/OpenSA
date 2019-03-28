package opensa.Move;
import opensa.Solution.chromosome;
/**
 * <p>Title: The OpenSA project is a open structure of Simulated Annealing</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: Yuan-Ze University</p>
 * @author Chen, Shih-HSin
 * @version 1.0
 */

public class swapMutation extends inverseMutation {
  public void startMutation(){
    setCutpoint();
    newChromosome.setSolution(swapGenes(chromosome1).getSolution());
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
        setCutpoint();
      }
    }

    //swap
    if(cutPoint1 > cutPoint2){
      int temp = cutPoint2;
      cutPoint2 = cutPoint1;
      cutPoint1 = temp;
    }
  }

  private chromosome swapGenes(chromosome _chromosome){
    int backupGenes = _chromosome.genes[cutPoint1];
    _chromosome.genes[cutPoint1] = _chromosome.genes[cutPoint2];
    _chromosome.genes[cutPoint2] = backupGenes;

    return _chromosome;
  }

  public static void main(String[] args) {
  }
}