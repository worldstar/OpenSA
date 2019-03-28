package opensa.Solution;

/**
 * <p>Title: The OpenSA project is a open structure of Simulated Annealing</p>
 * <p>Description: The encoding of SA which imitate the Genetic algorithm. </p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: Yuan-Ze University</p>
 * @author Chen, Shih-HSin
 * @version 1.0
 */
public class chromosome implements SolutionI{
  public chromosome(){
  }
  //If true, it's Phenotype or real number.
  //If not, it's binary one.
  boolean     genotype;
  public int  genes[];
  public double realGenes[];
  boolean     valid;
  int         length;
  //to store the calculations result of objective functions and fitnesses.
  //We keep the two data here and data is passed from other program.
  int         numberOfObjs;
  double      objectives[];
  double      scalarizedObjectiveValue;
  double      normObjectives[];
  double      fitness;
  double lwBounds[];//lower bound
  double upBound[]; //upper bound
  java.util.Random r = new java.util.Random(123456);

  /**
   * @param thetype if it's true which means binary one.
   * @param num The length of a chromosome.
   */
  public void setGenotypeAndLength(boolean thetype, int num, int numberOfObjs){
    this.genotype = thetype;
    this.length   = num;
    genes = new int[length];
    realGenes = new double[length];
    this.numberOfObjs = numberOfObjs;
    objectives = new double[numberOfObjs];

  }

  public void setBounds(double lwBounds[], double upBound[]){
    this.lwBounds = lwBounds;
    this.upBound= upBound;
  }

  public void initChromosome(){
    if(genotype == true){
      generateRealNumberPop(length);
    }
    else{
      generateBinaryPop(length);
    }
  }

  public void setRandomObject(java.util.Random r){
    this.r = r;
  }

  public void generateRealnumberPop(int numberofGenes){
    for(int i = 0 ; i < numberofGenes ; i ++ ){
      realGenes[i] = lwBounds[i] + Math.random()*(upBound[i] - lwBounds[i]);
    }
  }

  public void generateBinaryPop(int numberofGenes){
    for(int i = 0 ; i < numberofGenes ; i ++ ){
      if(Math.random() > 0.5){
        genes[i] = 1;
      }
      else{
        genes[i] = 0;
      }
    }
  }

  public void generateRealNumberPop(int numberofGenes){
    //initial the chromosome
    for(int i = 0 ; i < numberofGenes ; i ++ ){
      genes[i] = i;
    }

    for(int i = 0 ; i < numberofGenes ; i ++ ){
      //swap
      int temp1 = genes[i];
      int indexTemp = (int)(numberofGenes*r.nextDouble());
      int temp2 = genes[indexTemp];
      genes[indexTemp] = temp1;
      genes[i] = temp2;
    }
  }

  public void setObjValue(double objectives[]){
    System.arraycopy(objectives, 0, this.objectives, 0, objectives.length );
  }

  public void setScalarizedObjectiveValue(double value){
    this.scalarizedObjectiveValue = value;
  }

  public void setNormalizedObjValue(double normObjectives[]){
    System.arraycopy(normObjectives, 0, this.normObjectives, 0, normObjectives.length );
  }

  public void setFitnessValue(double fitness){
    this.fitness = fitness;
  }

  public void setSolution(int soln[]){
    System.arraycopy(soln, 0, this.genes, 0, soln.length );
  }

  public void setRealNumberSolution(double soln[]){
    System.arraycopy(soln, 0, this.realGenes, 0, soln.length );
  }


  public int[] getSolution(){
     return genes;
  }

  public double[] getRealNumberSolution(){
    return realGenes;
  }


  public double[] getObjectiveValue(){
    return objectives;
  }

  public int getLength(){
    return length;
  }

  public boolean getEncodeType(){
    return genotype;
  }

  //to return the string type of all gene value
  public String toString1(){
    String allResults = "";
    for(int i = 0 ; i < genes.length ; i ++ ){
      allResults += genes[i]+" ";
    }

    return allResults;
  }

  //to return the string type of all realGenes value
  public String toString2(){
    String allResults = "";
    for(int i = 0 ; i < realGenes.length ; i ++ ){
      allResults += realGenes[i]+"\t";
    }

    return allResults;
  }



  public static void main(String[] args) {
    chromosome chromosome1 = new chromosome();
  }

}