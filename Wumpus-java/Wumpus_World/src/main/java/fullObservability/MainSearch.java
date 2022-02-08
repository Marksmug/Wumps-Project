package fullObservability;

import wumpus.World;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;


/*
Usage:
 [-d]: generates a random world and runs [interactive]

 -f folder_to_worlds [output_file]: runs the agent in each world in the folder

 -d -f folder_to_worlds: DOES NOT WORK!

 [-d] file_world [output_file]: runs in the specified world [and prints the output in output_file] [interactive]

 output_file works only if the input file is also specified.
 */

public class MainSearch
{
    public static void main ( String[] args )
	{

		// Important Variables
		boolean search       = true;
		boolean debug        = false;
		boolean verbose      = false;
		boolean randomAI     = false;
		boolean manualAI      = false;
		boolean folder       = false;
		String	worldFile    = "";
		String	outputFile   = "";

		if ( args.length == 0 )
		{
			// Run on a random world and exit
			try
			{
				long startTime = System.currentTimeMillis();
				World world = new World( debug, randomAI, manualAI, search );
				int score = world.run();
				long elapsedTime = System.currentTimeMillis()-startTime;
				System.out.println("Your agent scored: " + score);
				System.out.println ( "Time in msec: " + elapsedTime);
			}
			catch ( Exception e )
			{
				e.printStackTrace();
			}
			return;
		}

		String 	firstToken 	 = args[0];

		// If there are options
		if ( firstToken.charAt(0) == '-' )
		{
			// Parse Options
			for ( int index = 1; index < firstToken.length(); ++index )
			{
				switch ( firstToken.charAt(index) )
				{
					case '-':
						break;
						
					case 'f':
					case 'F':
						folder = true;
						break;
						
					case 'v':
					case 'V':
						verbose = true;
						break;
						
					case 'r':
					case 'R':
						randomAI = true;
						break;
						
					case 'm':
					case 'M':
						manualAI = true;
						break;
						
					case 'd':
					case 'D':
						debug = true;
						break;
						
					case 'h':
					case 'H':
					default:
						System.out.println ( "Wumpus_World [Options] [InputFile] [OutputFile]" );
						System.out.println ( );
						System.out.println ( "Options:" );
						System.out.println ( "\t-d Debug mode, which displays the game board" );
						System.out.println ( "\t   after every mode." );
						System.out.println ( "\t-h Displays help menu and quits." );
						System.out.println ( "\t-f treats the InputFile as a folder containing" );
						System.out.println ( "\t   worlds. This will trigger the program to" );
						System.out.println ( "\t   display the average score and standard" );
						System.out.println ( "\t   deviation instead of a single score. InputFile" );
						System.out.println ( "\t   must be entered with this option." );
						System.out.println ( );
						System.out.println ( "InputFile: A path to a valid Wumpus World File, or" );
						System.out.println ( "           folder with -f. This is optional unless" );
						System.out.println ( "           used with -f." );
						System.out.println ( );
						System.out.println ( "OutputFile: A path to a file where the results will" );
						System.out.println ( "            be written. This is optional." );
						System.out.println ( );
						return;
				}
			}
			
			if ( randomAI && manualAI )
			{
				// If both AI's on, turn one off and let the user know.
				manualAI = false;
				System.out.println ( "[WARNING] Manual AI and Random AI both on; Manual AI was turned off." );
			}
			
			if ( args.length >= 2 )
				worldFile = args[1];
			if ( args.length >= 3 )
				outputFile = args[2];
		}
		else
		{
			if ( args.length >= 1 )
				worldFile = args[0];
			if ( args.length >= 2 )
				outputFile = args[1];
		}
		
		if ( worldFile == "" )
		{
			if ( folder )
				System.out.println( "[WARNING] No folder specified; running on a random world." );
			try
			{
				long startTime = System.currentTimeMillis();
				World world = new World( debug, randomAI, manualAI, search );
				int score = world.run();
				long endTime = System.currentTimeMillis();
				System.out.println ( "The agent scored: " + score );
				System.out.println ( "Time in msec: " + (endTime-startTime));
			}
			catch ( Exception e )
			{
				e.printStackTrace();
			}
			return;
		}
		
		if ( folder )
		{
			File worldFolder = new File ( worldFile );
			File[] listOfWorlds = worldFolder.listFiles();
			
			if ( listOfWorlds == null )
			{
				System.out.println ( "[ERROR] Failed to open directory." );
				return;
			}
			
			int		numOfScores        = 0;
			double	sumOfScores        = 0;
			double	sumOfScoresSquared = 0;
			long startTime,totalTime,elapsedTime;
			totalTime=0;

			for ( int worldIndex = 0; worldIndex < listOfWorlds.length; worldIndex++ )
			{
				if ( verbose )
					System.out.println ( "Running world: " + listOfWorlds[worldIndex] );
				
				int score = 0;
				try
				{
					startTime = System.currentTimeMillis();
					World world = new World( debug, randomAI, manualAI, search, listOfWorlds[worldIndex] );
					score = world.run();
					elapsedTime = System.currentTimeMillis()-startTime;
				}
				catch ( Exception e )
				{
					numOfScores = 0;
					sumOfScores = 0;
					sumOfScoresSquared = 0;
					break;
				}

				numOfScores += 1;
				sumOfScores += score;
				sumOfScoresSquared += score*score;
				totalTime += elapsedTime;
			}
			double avg = (float)sumOfScores / (float)numOfScores;
			double std_dev = Math.sqrt ( (sumOfScoresSquared - ((sumOfScores*sumOfScores) / (float)numOfScores) ) / (float)numOfScores );
			if ( outputFile == "" )
			{
				System.out.println ( "The agent's average score: " + avg );
				System.out.println ( "The agent's standard deviation: " + std_dev );
				System.out.println ("Total time in msec: " + totalTime);
			}
			else
			{
				BufferedWriter out = null;
				try
				{
					FileWriter fstream = new FileWriter ( outputFile );
					out = new BufferedWriter ( fstream );
					out.write ( "SCORE: " + avg + '\n' );
					out.write ( "STDEV: " + std_dev + '\n' );
					out.write("TIME " + totalTime);
				}
				catch ( Exception e )
				{
					System.out.println ( "[ERROR] Failure to write to output file." );
				}
				finally
				{
					try
					{
						if ( out != null )
						{
							out.close();
						}
					}
					catch ( IOException ioe )
					{
					}
				}
			}
			return;
		}
		

		if ( verbose )
			System.out.println ( "Running world: " + worldFile );
		
		File worldFileObject = new File ( worldFile );
		
		if ( !worldFileObject.exists() )
		{
			System.out.println ( "[ERROR] Failure to open file." );
			return;
		}
		
		int score = 0;
		long elapsedTime = 0;
		try
		{
			long startTime = System.currentTimeMillis();
			World world = new World( debug, randomAI, manualAI, search, worldFileObject );
			score = world.run();
			elapsedTime = System.currentTimeMillis()-startTime;
		}
		catch ( Exception e )
		{
			System.out.println ( "[ERROR] Failure to open file." );
			return;
		}
		
		if ( outputFile == "" )
		{
			System.out.println ( "The agent scored: " + score );
			System.out.println ( "Time in msec: " + elapsedTime);
		}
		else
		{
			BufferedWriter out = null;
			try
			{
				FileWriter fstream = new FileWriter ( outputFile );
				out = new BufferedWriter ( fstream );
				out.write ( "SCORE: " + score );
				out.write("TIME: " + elapsedTime);
			}
			catch ( Exception e )
			{
				System.out.println ( "[ERROR] Failure to write to output file." );
			}
			finally
			{
				try
				{
					if ( out != null )
					{
						out.close();
					}
				}
				catch ( IOException ioe )
				{
				}
			}
		}
	}
}
