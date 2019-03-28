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

public class shiftMutation extends inverseMutation {

  public void startMutation(){
    setCutpoint();
    newChromosome.setSolution(shiftGenes(chromosome1).getSolution());
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

  private chromosome shiftGenes(chromosome _chromosome){
    int length = cutPoint2 - cutPoint1  + 1;
    int backupGenes[] = new int[length];
    int counter = 0;

    //store the genes at backupGenes.
    for(int i = cutPoint1 ; i <= cutPoint2 ; i ++ ){
      backupGenes[counter++] = _chromosome.genes[i];
    }

    //assgin the gene at the end of the range to the place of the range.
    _chromosome.genes[cutPoint1] = backupGenes[backupGenes.length - 1];
    counter = 0;

    //write data of backupGenes into the genes
    for(int i = cutPoint1 + 1 ; i <= cutPoint2 ; i ++){
      _chromosome.genes[i] = backupGenes[counter++];
    }
    return _chromosome;
  }


  public static void main(String[] args) {
  }
}