
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;

public class generateStartArgumentsFileFromDataPoints {

    public generateStartArgumentsFileFromDataPoints () throws FileNotFoundException, IOException {
        File workDirectory = new File(".");
        File[] files = workDirectory.listFiles();
        double pressureList[] = new double[1000]; 
        for (int i=0;i<files.length;i++) {
            
            if (files[i].getName().contains("badanie-1")) {
                int licznik=0;
                File[] dataFiles = files[i].listFiles();
                for (int j=0;j<dataFiles.length;j++) if (dataFiles[j].getName().endsWith("_Results.txt"))
                    pressureList[licznik++] = Double.parseDouble(dataFiles[j].getName().substring(26,dataFiles[j].getName().length()-12));
            
                for (int j=0;j<licznik;++j) {
                    int minValueIndex=j;
                    for (int k=j+1;k<licznik;++k) if (pressureList[minValueIndex]>pressureList[k]) minValueIndex=k;
                    double buffer = pressureList[j];
                    pressureList[j]=pressureList[minValueIndex];
                    pressureList[minValueIndex]=buffer;
                }

                BufferedWriter writer = new BufferedWriter(new FileWriter("startArguments-"+files[i].getName().split("mD-")[1]+".txt"));
                for (int j=0;j<licznik;j++) {
                    writer.write("1.234\t"+String.format(Locale.ENGLISH,"%.3f",pressureList[j]));
                    writer.newLine();
                }
                writer.close();
            }
        }
        
        
    }
    
    public static void main(String[] args) throws IOException {        
        try {
            new generateStartArgumentsFileFromDataPoints();
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
    }
    
}