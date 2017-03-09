import javax.swing.*;
import java.io.*;
import java.util.*;

public class flightManager
{
        public static ArrayList<ArrayList<String>> airportList = new ArrayList<ArrayList<String>>();
        public static ArrayList<ArrayList<String>> flightList = new ArrayList<ArrayList<String>>();
	/*
	TODO List:
	validateFlight()  only case 4 validation left
	addAirport()      simple
	editAirport()     simple
	deleteAirport()   complex lots of deleting
	editFlight()      medium difficulty
	deleteFlight()    medium difficulty
	searchFlight()    medium difficulty
	searchDate()      medium difficulty
	sortList()        hard, Adams got it
	
	FYI: the validation methods do not display errors if validation is false,
	it only return true or false, please handle and output all error messeges 
	within your function on a case by case basis
	*/
	
	
        /*
	@authors Ian McKay & Adam Swayne
	Input: 
        Output:
	*/
        
        public static void main(String[] args) 
	{
                boolean isFiles = validateFiles();
		if(isFiles)
		{
			readInFiles();
			programLauncher(args);
		}		
	}
	
        /*
	@authors Ian Mckay
	Input: 
        Output:
	*/
        
        public static void readInFiles()
	{
		try
		{
			FileReader airportFile = new FileReader("Airports.txt");
			FileReader flightFile = new FileReader("Flights.txt");
			
			Scanner in = new Scanner(airportFile);
			String data[];
			int counter = 0;
			
			while(in.hasNext())
			{
				airportList.add(new ArrayList<String>());
				data=in.nextLine().split(",");
				for(int i=0;i<data.length;i++){
					airportList.get(counter).add(data[i]);
				}
				counter++;
			}
			
			in = new Scanner(airportFile);
			counter=0;
			
			while(in.hasNext())
			{
				flightList.add(new ArrayList<String>());
				data=in.nextLine().split(",");
				for(int i=0;i<data.length;i++){
					flightList.get(counter).add(data[i]);
				}
				counter++;
			}
			
			in.close();
			airportFile.close();
			flightFile.close();
                        
                        
                        
		}
		catch(FileNotFoundException e)
		{
			System.out.print("FileNotFoundException : " + e.getMessage());
		}
		catch(IOException e)
		{
			System.out.print("IOException : " + e.getMessage());
		}
	}
	
	/*
	@authors Ian McKay & Adam Swayne
	Input: 
        Output:
	*/
        
        public static boolean validateFiles()
	{
		File airportFile = new File("Airports.txt");
		File flightFile = new File("Flights.txt");
			
		if(airportFile.exists() && flightFile.exists())			 			return true;
		else airportFile.createNewFile();	flightFile.createNewFile();		return true;
		return false;

	}
        
         /*
	@authors Ian McKay & Adam Swayne 
	Input: 
        Output:
	*/
        
	public static boolean validateAirport(String input, int num)
	{
		String validationOf;
		boolean isValid=false;
		switch(num)
		{
			case 1:       
				validationOf = "airportName";
					
				String pattern  = "[a-zA-Z\\s]*";
						
				if(input != null && input.length() != 0)
				{
					if(input.matches(pattern))
					{
						isValid = true;
					}
				}
				break;
					
			case 2:       
				validationOf = "airportCode";
				isValid=true;
					
				for (int i=0; i<input.length(); i++){
					if (!Character.isUpperCase(input.charAt(i))) isValid= false;
				}
				if (input.length()!=3) isValid= false;
				break;
					
			default:      errorMessege(3);
		}
		return isValid;
	}
	
        
         /*
	@authors Ian McKay
	Input: 
        Output:
	*/
        
	
	public static boolean validateFlight(String input, int num)
	{
		String pattern;
		String validateOf;
		boolean isValid = false;
		
		switch(num)
		{
			case 1:       
				validateOf = "flightNum";
				pattern="[A-Z][A-Z]\\d\\d\\d\\d";
				if(input != null && input.length() != 0)
				{
					if(input.matches(pattern))
					{
						isValid = true;
					}
				}
				break;
			case 2:       
				validateOf = "DepartureArrivalTime";
				pattern="\\d\\d\\d\\d";
				if(input != null && input.length() != 0)
				{
					if(input.matches(pattern))
					{
						isValid = true;
					}
				}
				int inputNum=Integer.parseInt(input);
				if(inputNum>=2400 ||inputNum%100>=60)
				{
					isValid = false;
				}
				break;
			case 3:       
				validateOf = "DaysRunning";
				String days = "-MTWTFSS-";
				int countInOrder=0;
				int matchCount=0;
				boolean matchThisPos=false;
				
				for(int i = 0; i < input.length(); i++)
				{
					for(int j = countInOrder; j < days.length(); j++)
					{
						if(input.charAt(i)==days.charAt(j) && !matchThisPos)
						{
							matchCount++;
							countInOrder= j+1;
							matchThisPos=true;
						}
					}
					matchThisPos=false;
				}
				if(matchCount==input.length())
				{
					isValid=true;
				}
					break;
			case 4:       
				validateOf = "StartEndDate";
				String dateElements[];
				int ddInt, mmInt, yyInt;
				int[] daysArray = {31,28,31,30,31,30,31,31,30,31,30,31};
				boolean dateIsValid = true;
				dateElements = input.split("//");
				ddInt= Integer.parseInt(dateElements[0]);
				mmInt= Integer.parseInt(dateElements[1]);
				yyInt= Integer.parseInt(dateElements[2]);
				if(ddInt == 0 || mmInt == 0 || yyInt == 0)dateIsValid =false;
				else if(mmInt > 12)dateIsValid =false;
				else if(ddInt == 29 && mmInt == 2 && ((yyInt % 4 == 0 && yyInt % 100 != 0) || (yyInt % 400 == 0)))dateIsValid =true;
				else if(ddInt > daysArray[mmInt -1])dateIsValid =false;
				break;
				
				default:      errorMessege(3);
			}
		return isValid;
        
	}
					
	public static void errorMessege(int errorNum)
	{
		switch(errorNum)
		{
			case 1:System.out.print("Invalid number of command-line arguments."); break;
			case 2:System.out.print("Invalid first command-line argument."); break;
			case 3: System.out.print("Airport code must be three alphabetic characters in length."); break;
			case 4:System.out.print("File named Airports.txt does not exist."); break;
			case 5:System.out.print("Airport already exists."); break;
			case 6:System.out.print("Airport details added"); break;
			case 7:System.out.print("Validation Incomplete"); break;
			default: System.out.print("error not caught"); break;
		}
	
	}
	public static void displayInstructions() 
	{ 
		System.out.println(""); 
		System.out.println("************************ ASSiStance *****************************************************");
		System.out.println("Add new airport               e.g. java FlightManager AA Lisbon LIS"); 
		System.out.println("Edit airport                  e.g. java FlightManager EA BHD Belfast");
		System.out.println("Delete airport                e.g. java FlightManager DA SNN"); 
		System.out.println("Edit flight                   e.g. java FlightManager EF E13240 -TWTF-1/5/2017 31/10/2017"); 
		System.out.println("Delete flight                 e.g. java FlightManager DF E13240"); 
		System.out.println("Search for flights            e.g. java FlightManager SF Dublin Shannon"); 
		System.out.println("Search for flights on date    e.g. java FlightManager SD Dublin Shannon 1/12/2017"); 
		System.out.println("*****************************************************************************************");
	}
						
						
						
        
         /*
	@authors Ian McKay &
	Input: 
        Output:
	*/
        
	public static void programLauncher(String[] input)
	{
		
		try 
		{
			switch(input[0])
			{
				case "AA":       addAirport(input[1],input[2]); break;
				case "EA":       editAirport(input[1],input[2]); break;
				case "DA":       deleteAirport(input[1]); break;
				case "EF":       editFlight(input[1],input[2],input[3],input[4]); break;
				case "DF":       deleteFlight(input[1]); break;
				case "SF":       searchFlight(input[1],input[2]); break;
				case "SD":       searchDate(input[1],input[2],input[3]); break;
				case "help":	 displayInstructions();
				default:         errorMessege(2);
			}	
		}
		catch(ArrayIndexOutOfBoundsException exception)
		{
			errorMessege(1);
		}
     }
        
        /*
	@authors 
	Input: 
        Output:
	*/
        
        public static void addAirport(String airport,String airportCode)
	{
        	//figure out which input is the airport name and whihc one is the airport code, then send it off to validation
        }
        
        /*
	@authors 
	Input: 
        Output:
	*/
        
        public static void editAirport(String airportCode,String airport)
	{
        	
        }
        
        /*
	@authors 
	Input: 
        Output:
	*/
        
        public static void deleteAirport(String airportCode)
	{
        	
        }
        
        /*
	@authors 
	Input: 
        Output:
	*/
        
        public static void editFlight(String flightNum,String flightDays, String startFlight, String endFlight)
	{
        	
        }
        
        /*
	@authors 
	Input: 
        Output:
	*/
        
        public static void deleteFlight(String flightNum)
	{

        }
        
        /*
	@authors 
	Input: 
        Output:
	*/
        
        public static void searchFlight(String airportDepart,String airportArrive)
	{

        }
        
        /*
	@authors 
	Input: 
        Output:
	*/
        
        public static void searchDate(String airportDepart,String airportArrive, String date)
	{
		
        }
	
	/*
	@authors Adam Swayne
	Input: 
        Output:
	*/
        
        public static void sortFiles()
	{
			//Going to bubble sort the airportList and flightList in these for loops
			for(int i = 0;i < airportList.size();i++)
			{
				for(int j = i + 1; j < airportList.size() - i;j++)
				{

				}
			}
			
			
		writeToAirports();
		writeToFlights();
        }
        
        /*
	@authors Ian McKay
	Input: 
        Output:
	*/
        
        public static void writeToAirports()
	{
               try
        	{
		       PrintWriter outFile = new PrintWriter("Airports.txt");
		       String currentLine = "";
		       for (int i = 0; i <airportList.size(); i++)
		       {
			       currentLine = airportList.get(i).get(0);
			       for(int j = 1; j<airportList.get(i).size();j++)
			       {
				       currentLine = currentLine + "," + airportList.get(i).get(j);
			       }
			       //prints each line to the text file
			       outFile.println(currentLine);
		       }
		       outFile.close();
		}
		catch(IOException e)
        	{
            		System.out.print("IOException : " + e.getMessage());
		}
        }
	
	/*
	@authors Ian McKay
	Input: 
        Output:
	*/
        
        public static void writeToFlights()
	{
		try
        	{
		       PrintWriter outFile = new PrintWriter("Flights.txt");
		       String currentLine = "";
		       for (int i = 0; i <flightList.size(); i++)
		       {
			       currentLine = flightList.get(i).get(0);
			       for(int j = 1; j<flightList.get(i).size();j++)
			       {
				       currentLine = currentLine + "," + flightList.get(i).get(j);
			       }
			       //prints each line to the text file
			       outFile.println(currentLine);
		       }
		       outFile.close();
		}
		catch(IOException e)
        	{
            		System.out.print("IOException : " + e.getMessage());
		}
               
        }
}
