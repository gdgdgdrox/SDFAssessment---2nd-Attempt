import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class task01{
    private static File csvFile;
    private static File templateFile;

    public static void main(String[] args) throws FileNotFoundException {
        if (args.length == 2){
            if (args[0].contains(".csv")){
                csvFile = new File(args[0]);
            }
            else 
                templateFile = new File(args[0]);
                
                if (args[1].contains(".txt")){
                templateFile = new File(args[1]);
            }
            else
                csvFile = new File (args[1]);
        }
        convertCSVTo2DArray(csvFile);
        convertTemplateToString(templateFile);
        printMail(convertCSVTo2DArray(csvFile), convertTemplateToString(templateFile));
    }
    
    public static String convertTemplateToString (File templateFile){
        String template = "";
        try {
            BufferedReader br = new BufferedReader(new FileReader(templateFile));
            String line;
            StringBuilder builder = new StringBuilder();
            while((line=br.readLine())!=null){
                if (line.isEmpty()){
                    line = "\n";
                }
                builder.append(line);
            }
            br.close();
            template = builder.toString();
        }
        catch (IOException ex){
            ex.printStackTrace();
        }
        return template;
    }

    public static String[][] convertCSVTo2DArray (File csvFile) throws FileNotFoundException {
        //To initialize a 2D array, we need to know the number of rows (e.g. String[3][] exampleArray)
        //otherwise we cant even declare (String[][] exampleArray DOES NOT WORK)
        int numOfRows = 0;
        Scanner scan = new Scanner(csvFile);
        while (scan.hasNextLine()){
            numOfRows++;      
            scan.nextLine();   
        }
        scan.close();

        Scanner scan2 = new Scanner(csvFile);      //need a new scanner cause the previous scanner has reached the end of file and you can't re-use it.
        String [][]csv2DArray = new String[numOfRows][];  
        for (int i = 0; i < numOfRows; i++){
            csv2DArray[i] = scan2.nextLine().split(","); 
        }
        scan2.close();
        return csv2DArray;
    }

    public static void printMail (String[][] twoDimensionalArray, String textTemplate){
        String originalTemplate = textTemplate; 
        String[] headerRow = twoDimensionalArray[0];
        for (int i = 1; i < twoDimensionalArray.length; i ++){
            textTemplate = originalTemplate;
            for (int j = 0; j < headerRow.length; j++){
                if (textTemplate.contains("__"+headerRow[j]+"__")){
                    textTemplate = textTemplate.replace("__"+headerRow[j]+"__", twoDimensionalArray[i][j]);
                }
            }
            System.out.println(textTemplate + "\n");
        }
    }
}