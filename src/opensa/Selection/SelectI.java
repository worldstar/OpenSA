package opensa.Selection;
import opensa.Solution.chromosome;
/**
 * <p>Title: The OpenGA project</p>
 * <p>Description: The project is to build general framework of Genetic algorithm and problem independent.</p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: Yuan-Ze University</p>
 * @author Chen, Shih-Hsin
 * @version 1.0
 */

public interface SelectI {
  public chromosome setData(chromosome chromosome1, chromosome chromosome2);
  public void setData(chromosome chromosomes[]);
  public void startToSelect();
  public chromosome getSelectionResult();
}
