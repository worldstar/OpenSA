package opensa.applications;
import opensa.MainProgram.*;
import opensa.Move.*;
import opensa.ObjectiveFunctions.*;
import opensa.Solution.*;
import opensa.util.fileWrite1;
/**
 * <p>Title: The OpenSA project is a open structure of Simulated Annealing</p>
 * <p>Description: Himmelblau is a function which is a continuous problem.</p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: Yuan-Ze University</p>
 * @author Chen, Shih-HSin
 * @version 1.0
 */

public class Himmelblau {
  chromosome chromosome1;
  RealMoveI[] moves;
  ObjectiveFunctionContinuousI ObjectiveFunction1[];
  MainI SAMain;
  /**
   * SA parameters.
   */
  double initialTemperature = 1000, alpha = 0.9, currentTemperature = initialTemperature, finalTemperature = 10;
  int numberOfMoves = 2000;
  int numberOfDepartments = 30;
  int seed = 12345;

  int numberOfDimension = 2;
  double lwBounds[];//lower bound
  double upBound[]; //upper bound

  /**
   * The method is to modify the default value.
   */
  public void setParameter(double alpha, double finalTemperature, int numberOfMoves, int seed, double initialTemperature){
    this.initialTemperature = initialTemperature;
    currentTemperature = initialTemperature;
    this.alpha = alpha;
    this.finalTemperature = finalTemperature;
    this.numberOfMoves = numberOfMoves;
    this.seed = seed;
  }

  public void initialObjects(){
    lwBounds = new double[]{-6 ,-6 };
    upBound = new double[]{6 ,6 };
    java.util.Random r = new java.util.Random(2000);
    chromosome1 = new chromosome();
    chromosome1.setGenotypeAndLength(false, numberOfDimension, 1);
    chromosome1.setBounds(lwBounds, upBound);
    chromosome1.generateRealnumberPop(numberOfDimension);
    chromosome1.realGenes = new double[]{0, 0};

    /*
    chromosome1.genes = new int[]{14,5,28,24,1,3,16,15,10,9,21,2,4,29,25,22,13,26,17,30,6,20,19,8,18,7,27,12,11,23};
    for(int i = 0 ; i < chromosome1.genes.length ; i ++ ){
      chromosome1.genes[i] -= 1;
    }
    */

    moves = new RealMoveI[1];
    moves[0] = new realValueMutation();
    moves[0].setBounds(lwBounds, upBound);

    ObjectiveFunction1 = new ObjectiveFunctionContinuousI[1];
    ObjectiveFunction1[0] = new ObjectiveFunctionContinuous();

    SAMain = new singleThreadSAforContinuous();
    SAMain.setData(chromosome1, moves,ObjectiveFunction1, null, initialTemperature, alpha, finalTemperature, numberOfMoves);
  }

  void writeFile(String fileName, String _result){
    fileWrite1 writeLotteryResult = new fileWrite1();
    writeLotteryResult.writeToFile(_result,fileName+".txt");
    Thread thread1 = new Thread(writeLotteryResult);
    thread1.run();
  }

  public void startSA(){        
        opensa.util.timeClock timeClock1 = new opensa.util.timeClock();
        timeClock1.start();
        SAMain.startSA();
        timeClock1.end();
        //The first value is the alpha, stopping criteria (finalTemperature),  intial starting point, and the final one is the random seed
        String factors = ""+alpha+"\t"+finalTemperature+"\t"+seed+"\t"+timeClock1.getExecutionTime();
        //to receive result in string type.
        String implementResult = factors+"\t"+SAMain.getBestSolution().toString2() +"\t"+SAMain.getBestObjectiveValue()[0]+"\n";
        writeFile("Continuous", implementResult);
  }

  public static void main(String[] args) {
    //define parameters
    double initialTemperature[], alpha[], finalTemperature[];
    int numberOfMoves = 1000;
    int seed[] = new int[10];
    alpha = new double[]{0.9, 0.8, 0.7};
    initialTemperature = new double[]{1000, 2000, 3000};
    finalTemperature   = new double[]{10, 100, 500};

    java.util.Random r = new java.util.Random(1100);
    for(int i = 0 ; i < seed.length ; i ++ ){
      seed[i] = Math.abs(r.nextInt());
    }
    r = null;

    Himmelblau Himmelblau1 = new Himmelblau();
    int counter = 0;

    for(int i = 0 ; i < alpha.length ; i ++ ){
      for(int j = 0 ; j < finalTemperature.length ; j ++ ){
        for(int k = 0 ; k < seed.length ; k ++ ){
          System.out.println("The "+(i+1)+" implement and finalTemperature "+(j+1)+"\tcounter: "+counter);
          Himmelblau1.setParameter(alpha[i], finalTemperature[j], numberOfMoves, seed[k], initialTemperature[0]);//, int , double ){
          Himmelblau1.initialObjects();
          Himmelblau1.startSA();
          counter++;
        }
      }
    }//end for

  }
}