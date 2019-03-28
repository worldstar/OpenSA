package opensa.applications;
import opensa.Solution.chromosome;
import opensa.Move.*;
import opensa.ObjectiveFunctions.*;
import opensa.Selection.*;
import opensa.MainProgram.*;
import opensa.util.fileWrite1;
/**
 * <p>Title: The OpenSA project is a open structure of Simulated Annealing</p>
 * <p>Description: An application of Qudrastic Assignment Problem. We use the Nug30 as testing example.</p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: Yuan-Ze University</p>
 * @author Chen, Shih-HSin
 * @version 1.0
 */

public class QAP_NVR {
  public QAP_NVR(){

  }

  chromosome chromosome1;//the solution class.
  MoveI[] moves;          //the move operators --> it proceduces moves.length solns.
  //The objective function to evaluate the QAP solution.
  QAPObjectiveFunctionI ObjectiveFunction1[];
  SelectI Selection;      //select better moves.
  MainI SAMain;           //The main program of the SA.
  /**
   * SA parameters. Although we have initialized it, we can use the method "setParameter" to alter the value.
   */
  double initialTemperature = 1000, alpha = 0.9, currentTemperature = initialTemperature, finalTemperature = 10;
  int numberOfMoves = 2000;
  int numberOfDepartments = 30;
  int seed = 12345;

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

  /**
   * Initiate the SA objects.
   */
  public void initialObjects(){
    java.util.Random r = new java.util.Random(seed);
    chromosome1 = new chromosome();
    chromosome1.setGenotypeAndLength(true, numberOfDepartments, 1);
    chromosome1.initChromosome();
    chromosome1.setRandomObject(r);
    r = null;//release memory of r
    /*
    chromosome1.genes = new int[]{14,5,28,24,1,3,16,15,10,9,21,2,4,29,25,22,13,26,17,30,6,20,19,8,18,7,27,12,11,23};
    for(int i = 0 ; i < chromosome1.genes.length ; i ++ ){
      chromosome1.genes[i] -= 1;
    }
    */

    moves = new MoveI[3];
    moves[0] = new swapMutation();
    moves[1] = new shiftMutation();
    moves[2] = new inverseMutation();

    //set objective functions and related data
    opensa.applications.data.forQAP forQAP1 = new opensa.applications.data.forQAP();
    ObjectiveFunction1 = new QAPObjectiveFunctionI[1];
    ObjectiveFunction1[0] = new ObjectiveFunctionQAP();
    ObjectiveFunction1[0].setQAPData(forQAP1.getNugentData_Flow(), forQAP1.getNugentData_Distance());
    forQAP1 = null; //release memory of forQAP1

    Selection = new binaryTournament();
    SAMain = new singleThreadSA();
    SAMain.setData(chromosome1, moves,ObjectiveFunction1, Selection,initialTemperature, alpha, finalTemperature, numberOfMoves);
  }

  /**
   * Starting the SA to do calculation.
   */
  public void startSA(){        
        opensa.util.timeClock timeClock1 = new opensa.util.timeClock();
        timeClock1.start();
        SAMain.startSA();
        timeClock1.end();
        //The first value is the alpha, stopping criteria (finalTemperature),  intial starting point, and the final one is the random seed
        String factors = ""+alpha+"\t"+finalTemperature+"\t"+seed+"\t"+timeClock1.getExecutionTime();
        //to receive result in string type.
        String implementResult = factors+"\t"+SAMain.getBestSolution().toString1() +"\t"+SAMain.getBestObjectiveValue()[0]+"\n";
        writeFile("QAP", implementResult);
  }

  /**
   * Write the data into text file.
   */
  void writeFile(String fileName, String _result){
    fileWrite1 writeLotteryResult = new fileWrite1();
    writeLotteryResult.writeToFile(_result,fileName+".txt");
    Thread thread1 = new Thread(writeLotteryResult);
    thread1.run();
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

    QAP_NVR QAP_NVR1 = new QAP_NVR();
    int counter = 0;
    //to test different kinds of combinations.
    for(int i = 0 ; i < alpha.length ; i ++ ){
      for(int j = 0 ; j < finalTemperature.length ; j ++ ){
        for(int k = 0 ; k < seed.length ; k ++ ){
          System.out.println("The "+(i+1)+" implement and finalTemperature "+(j+1)+"\tcounter: "+counter);
          QAP_NVR1.setParameter(alpha[i], finalTemperature[j], numberOfMoves, seed[k], initialTemperature[0]);//, int , double ){
          QAP_NVR1.initialObjects();
          QAP_NVR1.startSA();
          counter++;
        }
      }
    }//end for

    System.exit(0);
  }
}