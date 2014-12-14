package regularExpression.current;

import java.io.FileReader;
import java.util.Scanner;

public class Snippet {
	
	

	public static void main(String[] args) {
	        String accountNum, password, give;  
	        int count=0, menuChoice,size=0; 
	        String[] validAccounts=null; 
	
	        //gets the data from the file and stores into an array
	        try {
	            Scanner file= new Scanner (new FileReader("H:\\ATMdata.txt.txt"));
	
	            //counts the lines in the text file
	
	            while (file.hasNextLine()) {
	                size++;
	                String theLine = file.nextLine();
	            }
	
	            validAccounts= new String[size];
	
	            for ( int countFile=0; count==size; countFile++) {
	                validAccounts[countFile]= file.nextLine();
	            }
	        }
	        catch(Exception e) {
	        	e.printStackTrace();
	            System.out.println("Error processing file.");
	        }
}
	}

