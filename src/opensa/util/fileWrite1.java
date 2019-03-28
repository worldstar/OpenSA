package opensa.util;
import java.io.*;


public class fileWrite1 implements Runnable{
  public fileWrite1(){
    //constructor of fileWriteObject
  }

     FileOutputStream fos;
     String data = "";
     byte buf[];

  public void writeToFile(String data, String fileName){
    try{
      //the second parameter true means when there exists a file in the same file name,
     //it won't overwrite original meg but to add below original one.
      //The false means it overwrites the file.
     fos = new FileOutputStream(fileName, true);
     this.data = data;
    }
    catch(Exception e)
    {
            System.out.print(e.toString());
    }
  }

  public void run(){
    try{
     buf = data.getBytes();
     fos.write(buf);
     fos.close();
    }
    catch(Exception e)
    {
            System.out.print(e.toString());
    }
  }
}
