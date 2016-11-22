
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Locale;

public class decimalPointsInDataFiles {

    public decimalPointsInDataFiles () throws FileNotFoundException, IOException {
        File workDirectory = new File(".");
        File[] files = workDirectory.listFiles();
        for (int i=0;i<files.length;i++) {
            if (files[i].getName().contains("badanie")) {
                File[] dataFiles = files[i].listFiles();
                for (int j=0;j<dataFiles.length;j++) if (dataFiles[j].getName().contains("Configurations_") && !dataFiles[j].getName().contains("Results")) {
                    BufferedReader reader = new BufferedReader(new FileReader(dataFiles[j]));
                    String line = reader.readLine(); reader.close();
                    double pressure = Double.parseDouble(line.split("\t")[2].split(" ")[1]);
                    String newPressure = String.format(Locale.ENGLISH,"%.3f",pressure);
                    String oldPressure = dataFiles[j].getName().substring(26,dataFiles[j].getName().length()-4);
                    System.out.println(newPressure+"  "+oldPressure);
                    for (int k=0;k<dataFiles.length;k++) if (dataFiles[k].getName().contains(oldPressure)) {
                        String[] nameParts = dataFiles[k].getName().split(oldPressure);
                        //dataFiles[k].renameTo(new File(dataFiles[k].getParent()+"/"+nameParts[0]+newPressure+nameParts[1]));
                    }
                } 
            }
        }
    }
    
    public static void main(String[] args) throws IOException {        
        try {
            new decimalPointsInDataFiles();
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
    }
    
}