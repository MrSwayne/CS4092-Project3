import javax.swing.*;
import java.io.*;
import java.util.*;

public class FlightManager
{
	//publicly declared for use in other methods
        public static ArrayList<ArrayList<String>> airportList = new ArrayList<ArrayList<String>>();
        public static ArrayList<ArrayList<String>> flightList = new ArrayList<ArrayList<String>>();
	
        /*
	@authors 	Ian McKay & Adam Swayne
	
	Input: 		args from commandline
        
	processing:	Checks if files are present, if they are then the program launches,
			the files are read in, and the instructions are displayed to user.
			if files are not present then files are created and programme continues.
	*/
        
        public static void main(String[] args) 
	{
        boolean isFiles = validateFiles();
		if(isFiles)
		{
			displayInstructions();
			readInFiles();
			programLauncher(args);
		}		
	}
	
        /*
	@authors 	Ian Mckay
	
	Input: 		Nothing
	
	Processing:	reads in files from text file and stores in the arrayLists
	
        Output:		Nothing		
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
			
			in = new Scanner(flightFile);
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
	@authors	Ian McKay & Adam Swayne
	
	Input:		Nothing
	
	Processing:	Makes sure the files are present, if they are not, then they are created
	
        Output:		Returns true when files are present
	*/
        
        public static boolean validateFiles()
	{
		try
		{
		File airportFile = new File("Airports.txt");
		File flightFile = new File("Flights.txt");
		if(airportFile.exists() && flightFile.exists())			 									return true;
		else airportFile.createNewFile();	flightFile.createNewFile();		
		}
		catch(IOException e)
		{
			System.out.print("Error, " + e.getMessage());
		}
		return true;
	}
        
         /*
	@authors 	Ian McKay & Adam Swayne 
	
	Input:		input to be validated, and num for whatever you want to validate, 1 being airportName, 2 being airportCode etc 		
        
	Output:		true or false depending if the validation was successful or not
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
				if(validateAirport(input,2))
				{
					isValid=false;
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
					
			default:      errorMessage(3);
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
				String days = "MTWTFSS";
				String dash = "-";
				int countInOrder=0;
				int matchCount=0;
				boolean matchThisPos=false;
				
				if(input.length() == 7)
				{
					for(int i = 0; i < input.length(); i++)
					{
						for(int j = countInOrder; j < days.length(); j++)
						{
							if((input.charAt(i)==days.charAt(j) || input.charAt(i) == dash.charAt(0)) && !matchThisPos)
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
				}	
				break;
			case 4:       
				validateOf = "StartEndDate";
				String dateElements[];
				int ddInt, mmInt, yyInt;
				int[] daysArray = {31,28,31,30,31,30,31,31,30,31,30,31};
				boolean dateIsValid = true;
				dateElements = input.split("/");
				ddInt= Integer.parseInt(dateElements[0]);
				mmInt= Integer.parseInt(dateElements[1]);
				yyInt= Integer.parseInt(dateElements[2]);
				if(ddInt == 0 || mmInt == 0 || yyInt == 0)dateIsValid =false;
				else if(mmInt > 12)dateIsValid =false;
				else if(ddInt == 29 && mmInt == 2 && ((yyInt % 4 == 0 && yyInt % 100 != 0) || (yyInt % 400 == 0)))dateIsValid =true;
				else if(ddInt > daysArray[mmInt -1])dateIsValid =false;
				if(dateIsValid == true)isValid = true;
				break;
				
				default:      errorMessage(3);
			}
		return isValid;
        
	}
					
	public static void errorMessage(int errorNum)
	{
		switch(errorNum)
		{
			case 1:System.out.println("Invalid number of command-line arguments."); break;
			case 2:System.out.println("Invalid first command-line argument."); break;
			case 3: System.out.println("Airport code must be three alphabetic characters in length."); break;
			case 4:System.out.println("File named Airports.txt does not exist."); break;
			case 5:System.out.println("Airport already exists."); break;
			case 6:System.out.println("Airport details added"); break;
			case 7:System.out.println("Validation Incomplete"); break;
			case 8:System.out.println("Airport code not found"); break;
			case 9:System.out.println("Departing Airport not found"); break;
			case 10:System.out.println("Arrival Airport not found"); break;
			case 12:System.out.println("Invalid airport name."); break;
			case 13:System.out.println("Invalid days"); break;
			case 14:System.out.println("Invalid start date."); break;
			case 15:System.out.println("Invalid end date."); break;
			case 16:System.out.println("Invalid flight number"); break;
			case 17:System.out.println("Flight number not found"); break;	
			default: System.out.println("error not caught"); break;
		} 
	}
	public static void displayInstructions() 
	{ 
		System.out.println(""); 
		System.out.println("************************ Assistance *****************************************************");
		System.out.println("Add new airport               e.g. java FlightManager AA Lisbon LIS"); 
		System.out.println("Edit airport                  e.g. java FlightManager EA BHD Belfast");
		System.out.println("Delete airport                e.g. java FlightManager DA SNN"); 
		System.out.println("Edit flight                   e.g. java FlightManager EF EF3240 MTW-F-- 1/5/2017 31/10/2017"); 
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
			switch(input[0].toUpperCase())
			{
				case "AA":       addAirport(input[1],input[2]); break;
				case "EA":       editAirport(input[1],input[2]); break;
				case "DA":       deleteAirport(input[1]); break;
				case "EF":       editFlight(input[1],input[2],input[3],input[4]); break;
				case "DF":       deleteFlight(input[1]); break;
				case "SF":       searchFlight(input[1],input[2]); break;
				case "SD":       searchDate(input[1],input[2],input[3]); break;
				case "help":	 	displayInstructions();
				default:        	errorMessage(2);
			}	
		}
		catch(ArrayIndexOutOfBoundsException exception)
		{
			errorMessage(1);
		}

		sortFiles();
		
    }
        
            /*
	@authors Ibrahim Alaydi
	Input: command-line arguments supplied
    Output:validating airportname and code and checking of it exists, also allowing user to add a new airport
	*/
        
public static void addAirport(String airport,String airportCode)
{	
		   

		boolean alreadyExists=false;
		//if both return true loop through array list and check if airport or airportcode already exist
		if(validateAirport(airport,1) && validateAirport(airportCode,2))
		{
			for(int i=0;i<airportList.size();i++) //looping through the airport list 
			{
			  if(airportList.get(i).get(0).equals(airport))//&&(airportList.get(1).get(i).equals(airportCode))) 
				  //checking if airport code and name exist file
				{
				  alreadyExists=true;
				  errorMessage(5);
				}  
			
			}
				if(!alreadyExists)
				{//if they don't exist we need to create a new airport
					ArrayList<String>Entry = new ArrayList<String>();
					Entry.add(airport);
					Entry.add(airportCode);
					airportList.add(Entry);
					System.out.println("The airport is added successfully");
				}
		}
		else if(!validateAirport(airport,1))errorMessage(3);	
		else if(!validateAirport(airportCode,2))errorMessage(12);
 }
        
        /*
	@authors Ibrahim Alaydi
	Input: command-line arguments
    Output: error messages or new airport is written to the file
	*/
        
public static void editAirport(String airportCode,String airport)
{
	boolean isValid=false; //create a boolean
	String temp; //intialize temp for sorting airport and code
	if(validateAirport(airport,1) && validateAirport(airportCode,2))
	isValid=true;// if in the correct order isValid is set to true
	else if(validateAirport(airport,2) && validateAirport(airportCode,1))
	{
		temp=airport;
		airport=airportCode;
		airportCode=temp;
		isValid=true; //if not, then we sort it.
	}
		else
		{ 
		errorMessage(8);
		isValid=false; //else we deplay an error
		}
				 
		if(isValid)// once it is valid we can enter the loop to edit airport list
		{
			boolean isFound = false;
			for(int i=0;i<airportList.size() && !isFound;i++) //loops through airportList
			{
				if(airportList.get(i).get(1).equals(airportCode))//checks if they match once again after sorting
				{
					 
					isFound = true;
					airportList.get(i).set(0,airport);	
				}
					
			}
		if(!isFound){
		errorMessage(5);
		}
	}
}
        
        /*
	@authors 
	Input: 
        Output:
	*/
        
     public static void deleteAirport(String airportCode)
	{
		if(validateAirport(airportCode, 2))
		{
			boolean isFound =false;
			boolean flightToDelete=false;
			int rowFound=0;
			for (int i = 0; i <airportList.size(); i++)
			{
				if(airportList.get(i).get(1).equals(airportCode))
				{
					isFound=true;
					rowFound=i;
				}
			}
			if(isFound)
			{
				airportList.remove(rowFound);
				for (int i = 0; i <flightList.size(); i++)
				{
					for(int j = 0; j<flightList.get(i).size();j++)
					{
						if(flightList.get(i).get(j).equals(airportCode))
						{
							rowFound=i;
							flightToDelete=true;
						}
					}
					if(flightToDelete)
					{
						flightList.remove(rowFound);
						flightToDelete=false;
						i--;
					}
				}
				System.out.print("Airport deleted successfully");
			}
			else
			{
				errorMessage(8);
			}
		}
		else
		{
			errorMessage(3);
		}	
    }
        
        /*
	@authors Matthew Blake
	Input: Command-line arguments
        Output: Error or success message
	*/
        
public static void editFlight(String flightNum,String flightDays,String startDateFlight,String endDateFlight)
{
	boolean isValid = false; //create a booleans
	boolean isFound = false;
 	boolean flightToEdit = false;
 	int rowFound = 0;
	for (int i = 0; i < flightList.size() && !isFound; i++)
  	  {
		if(flightList.get(i).get(0).equals(flightNum))
		{
			isFound = true;//checks if flight exists
		}
    }
    if(!isFound)
	{
		errorMessage(17);
	}
	else
    {
      for (int i = 0; i < flightList.size(); i++)
      {
        for(int j = 0; j < flightList.get(i).size();j++)
        {
          if(flightList.get(i).get(j).equals(flightNum))
			{
				rowFound = i;//finds row of flight in .txt file
				flightToEdit = true;
				
			}
		}
	  }
    for (int i = 0; i < flightList.size(); i++)
    {
		if(flightList.get(i).get(0).equals(flightNum))
		{
			isFound = true;
		}
    }
	if(flightToEdit)
	{
	if(validateFlight(flightDays,3) && validateFlight(startDateFlight,4) && validateFlight(endDateFlight,4))
  {
	isValid = true;
  }
  else
  {
    if(!validateFlight(flightDays,3))
	{
		errorMessage(13);
	}
	if(!validateFlight(startDateFlight,4))
	{
		errorMessage(14);
	}
	if(!validateFlight(endDateFlight,4))
	{
		errorMessage(15);
	}
  }
  if(isValid)
  {
        flightList.get(rowFound).set(5,flightDays);
		flightList.get(rowFound).set(6,startDateFlight);
		flightList.get(rowFound).set(7,endDateFlight);
		System.out.print("Flight successfully edited");
  }
}
}
}

        
        /*
	@authors Matthew Blake
	Input: Command-line arguments
        Output:	Error or success message
	*/
        
public static void deleteFlight(String flightNum)
{
  if(validateFlight(flightNum,1))
  {
    boolean isFound = false;
    boolean flightToDelete = false;
    int rowFound = 0;
    for (int i = 0; i < flightList.size(); i++)
    {
		if(flightList.get(i).get(0).equals(flightNum))
		{
			isFound = true;
		}
    }
    if(isFound)
    {
      for (int i = 0; i < flightList.size(); i++)
      {
        for(int j = 0; j < flightList.get(i).size();j++)
        {
          if(flightList.get(i).get(j).equals(flightNum))
          {
            rowFound = i;
			flightList.remove(rowFound);
			System.out.print("Flight has been deleted");
			flightToDelete = false;
			i--;
		  }
        }
      }
    }
	else
	{
		errorMessage(17);
	}
  }
  else
  {
    errorMessage(16);
  }
}
        
        /*
	@authors 
	Input: 
        Output:
	*/
        
        public static void searchFlight(String airportDepart,String airportArrive)
	{
		if(validateAirport(airportDepart, 1)&& validateAirport(airportArrive,1))
		{
			boolean departFound =false;
			boolean arriveFound =false;
			String result = "";
			String departCode = "";
			String arriveCode = "";

			
			for (int i = 0; i <airportList.size(); i++)
			{
				if(airportList.get(i).get(0).equals(airportDepart))
				{
					departCode= airportList.get(i).get(1);
					departFound=true;
				}
				if(airportList.get(i).get(0).equals(airportArrive))
				{
					arriveCode= airportList.get(i).get(1);
					arriveFound=true;
				}
			}            
			if(departFound && arriveFound)
			{
				System.out.println("The Following Flight(s) depart from " + airportDepart + " and arrive in " + airportArrive + ".");
				for (int i = 0; i <flightList.size(); i++)
				{
					if(flightList.get(i).get(2).equals(arriveCode)&& flightList.get(i).get(1).equals(departCode))
					{
						System.out.println(flightList.get(i));
					}
				}
			}
			else
			{
				if(!departFound)errorMessage(9);
				if(!arriveFound)errorMessage(10);
			}
		}
		else 
		{
			errorMessage(11);
		}

        }
        
		/*
			@author Adam Swayne
			
			Input:				 airport that the flight is departing from, the airport the flight is arriving to and the date.
			
			processing: 	Checks to see if the airports that were inputted exists, then gets their corresponding airport
									codes from the airports array,  then checks the flights array and checks if there is any flight
									going from that airport to the other airport on that date, it also checks if the date is within the timeframe
									and then checks if the date inputted matches the days that the flight is running on and then outputs
									the list of flights matching that criteria to the user
								
			Output:			List of flights matching criteria
		*/
        
        public static void searchDate(String airportDepart,String airportArrive, String date)
		{
			if(validateFlight(date,4) )
			{
				boolean stop = false;
				int[] numbers = new int[flightList.size()];
				String airportDepartCode = "", airportArriveCode = "";
				int counter = 0;
				
				//Finds the corresponding airport code to the airports supplied
				for(int i = 0;i < airportList.size() && !stop; i++)
				{
					if (airportDepart.equalsIgnoreCase(airportList.get(i).get(0)))		airportDepartCode += airportList.get(i).get(1);
					if(airportArrive.equalsIgnoreCase(airportList.get(i).get(0)))			airportArriveCode += airportList.get(i).get(1);	
					
					
					//once both airports have been found, it should break out of the loop
					if ((!(airportDepartCode.equals(""))) && (!(airportArriveCode.equals(""))))		stop = true;
				}
					//if an airport isnt found in the textfile, then an error message is outputted to the user
				if(airportDepartCode.equals(""))				System.out.println("Error, " + airportDepart + " airport not found in Airports.txt");
				else if(airportArriveCode.equals(""))		System.out.println("Error, " + airportArrive + " airport not found in Airports.txt");
				
				else
				{
					for(int i = 0;i < flightList.size();i++)
					{
						//go through all of the arrayList and check if the arrival/departing airport and the date matches what's on file, if it does, then the index of that is put into an array so I can call it again later on
						if((flightList.get(i).get(1).equalsIgnoreCase(airportDepartCode)) && (flightList.get(i).get(2).equalsIgnoreCase(airportArriveCode)) && (checkDate(date.split("/"), flightList.get(i).get(6).split("/"), flightList.get(i).get(7).split("/"), flightList.get(i).get(5))))
						{
							numbers[counter] = i;
							counter++;
						}
						
					}
					//Print out how many flights were found corresponding to search criteria
					if(counter == 1)	System.out.println("There was only 1 flight found matching that criteria.");
					else 						System.out.println("There were " + (counter) + " flights found matching that criteria.");
					
					for(int i = 0; i < counter; i++)
					{
						//Output the textfile data to the user
						System.out.println((i + 1) + ".\nFlight Number   :\t"  + flightList.get(numbers[i]).get(0) +
														"\nleaving \t: \t" + airportDepart + "\t\t at : \t" + flightList.get(numbers[i]).get(3).substring(0,2) + ":" + flightList.get(numbers[i]).get(3).substring(2,4) +
														"\nArriving to \t:\t" + airportArrive + "\t\t at : \t" + flightList.get(numbers[i]).get(4).substring(0,2) + ":" + flightList.get(numbers[i]).get(4).substring(2,4) );
					}	
				}
			}
			else	System.out.println("Error, invalid date supplied");
		}
		
		/*
			@Author 			Adam Swayne
			
			input:				date inputted by user, start and end date in flights.txt and the days the flight is running in flights.txt (from searchDate method)
			
			processing: 		checks the dates of flights and checks if the flight start date is before the input date and that the end date is after the input date, 
									if that passes, then the checkDaysOfWeek method is called which will then check what day of the week is the date that was inputted,
									if that date corresponds to the days of the flights on file as well as the date paramaters, then the method returns true to searchDate method
			
			output:				returns the value of isValid (if the date paramaters AND the day match) to the searchDate method
		*/
		public static boolean checkDate(String[] dateInput, String[] dateStart, String[] dateEnd, String days)
		{
			
			boolean isValid = false;
			
			String dayOfWeek = "";
			String daysExtended = "";
	
			//Creates 3 variables of Calendar type
			Calendar inputDate = Calendar.getInstance();
			Calendar startDate = Calendar.getInstance();
			Calendar endDate = Calendar.getInstance();
			
			//the 3 calendars are set to the year, month and day of the input date, the start date of the flight on file, and the end date of the flight on file
			inputDate.set(Integer.parseInt(dateInput[2]), Integer.parseInt(dateInput[1]), Integer.parseInt(dateInput[0]));
			startDate.set(Integer.parseInt(dateStart[2]), Integer.parseInt(dateStart[1]), Integer.parseInt(dateStart[0]));
			endDate.set(Integer.parseInt(dateEnd[2]), Integer.parseInt(dateEnd[1]), Integer.parseInt(dateEnd[0]));

			//checks to see if the start date is before or equal to the current inputted date, and that the end date is after or equal to the current inputted date
			if((startDate.before(inputDate) || startDate.equals(inputDate)) && (endDate.after(inputDate) || endDate.equals(inputDate)))
				isValid = true;
			
			//if the date is valid, then the days in the flights.txt file are analysed to check if the inputted date day is equal to a day that is on file
			if (isValid)	
			{
				try
				{
					//this is so I can differentiate between days starting with the same letter i.e Tuesday and Thursday or Saturday and Sunday
					dayOfWeek = checkDayOfWeek(dateInput);
					if(days.contains("M"))												daysExtended += "mo/";
					if(days.substring(1,2).equalsIgnoreCase("t"))				daysExtended += "tu";
					if(days.contains("W"))												daysExtended += "we";
					if(days.substring(3,4).equalsIgnoreCase("t"))				daysExtended += "th";
					if(days.contains("F"))													daysExtended += "fr";
					if(days.substring(5,6).equalsIgnoreCase("s"))				daysExtended += "sa";
					if(days.substring(6,7).equalsIgnoreCase("s"))				daysExtended += "su";
					
					//checks if the current day from the inputted date matches a day that is in the flights.txt file
					if(daysExtended.contains(dayOfWeek.substring(0,2)))
						isValid = true;
					else
						isValid = false;
				}
				//if invalid args in the flights.txt file, an error is thrown
				catch(StringIndexOutOfBoundsException e)
				{
					System.out.println("Error, Please make sure the days are in the format ------- with each dash representing a day i.e -TWT-S- represents tuesday, wednesday, thursday and saturday");
				}
			}
	
			return isValid;
		}
		
		/*
			@author			Adam Swayne
			
			input: 				inputDate from checkDate method
			
			processing:		calculates the day of the week using the ZellerCongruenceTest that we had in semester 1
			
			output: 				returns the day of the week to checkDate method
			
		*/
		
		public static String checkDayOfWeek(String[] inputDate)
		{
			int d = Integer.parseInt(inputDate[0]);
			int m = Integer.parseInt(inputDate[1]);
			int y = Integer.parseInt(inputDate[2]);
			
			String result = ""; 
			int a, b, dayOfWeek;
			
			if (m == 1 || m == 2)
			{
				m += 12; 
				y -=  1;
			}
			
			a = y % 100;  
			b = y / 100;
			dayOfWeek = ((d + (((m + 1)*26)/10)+ a + (a/4) + (b/4)) + (5*b)) % 7;
			switch(dayOfWeek)
			{
				case 0: result = "Saturday";  break;  case 1: result = "Sunday";    break;
				case 2: result = "Monday";    break;  case 3: result = "Tuesday";   break;
				case 4: result = "Wednesday"; break;  case 5: result = "Thursday";  break;
				case 6: result = "Friday";    break;
			}	
			return result.toLowerCase();
		}
		
		
	
	/*
		@author 			Adam Swayne
		
		Input:				Nothing 
		
		Processing:		Sorts the airport.txt file by airport, sorts the flights.txt file by flightNumber then calls the writeTo methods.
		
        Output:				Nothing
	*/
        
        public static void sortFiles()
	{	

		String[] temp;
		for(int i = 0;i < airportList.size();i++)
		{
			for(int j = i + 1;j < airportList.size();j++)
			{
				//checks if the airport comes alphabetically before the airport after it 
				if(airportList.get(i).get(0).compareToIgnoreCase(airportList.get(j).get(0)) > 0)
				{
					//if it does then it preforms a bubble sort depending on how big the arrayList is
					temp = new String[airportList.get(i).size()];
					
					//for loop swaps all the elements of the arrayList
					for(int x = 0; x < airportList.get(i).size();x++ )
					{
						temp[x] = airportList.get(i).get(x);
						
						airportList.get(i).set(x,airportList.get(j).get(x));
						
						airportList.get(j).set(x, temp[x]);					
					}
				}
			}
		}	
	
		//exact same as the one above, duplicated because the 2 arrayLists are public therefore can't pass it to this method. I.E must be done separately
		for(int i = 0;i < flightList.size();i++)
		{
			for(int j = i + 1;j < flightList.size();j++)
			{
				if(flightList.get(i).get(0).compareToIgnoreCase(flightList.get(j).get(0)) > 0)
				{
					temp = new String[flightList.get(i).size()];
					for(int x = 0; x < flightList.get(i).size();x++ )
					{
						temp[x] = flightList.get(i).get(x);
						
						flightList.get(i).set(x,flightList.get(j).get(x));
						
						flightList.get(j).set(x, temp[x]);					
					}
				}
			}
		}
		
		
		writeToAirports();
		writeToFlights();
       }
        
        /*
		@authors	Ian McKay
	
		Input:				Nothing
	
		Proccessing:		
	
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
	@authors	Ian McKay
	
	Input:		Nothing
	
	Proccessing:	Writes the arrayList to the flights.txt file
	
        Output:		Nothing
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
