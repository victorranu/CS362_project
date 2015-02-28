import java.io.*;
import java.util.*;
public class TeamProject_ReadFile 
{
	private Scanner thisScan;
	
	public void openFile(String dir, String f)
	{
		try
		{
			thisScan = new Scanner(new File(dir + f));
		}
		catch (Exception e)
		{
			System.out.println("ERROR: Count not find file!");
		}
	}
	
	public void readFile(int opt)
	{
		String item, valid;
		
		if (opt == 0 || opt == 1)
		{
			while (thisScan.hasNext())
			{
				switch(opt)
				{
				case 0:
					item = thisScan.next();
					
					System.out.printf("%s\n", item);
					break;
				case 1:
					item = thisScan.next();
					valid = thisScan.next();
					
					System.out.printf("%s %s\n", item, valid);
					break;
				}				
			}
		}
		else
		{
			System.out.println("No opting provided");
		}
	}
	
	public ArrayList<ResultPair> readFile_CreateResultPair()
	{
		ArrayList<ResultPair> rpList = new ArrayList<ResultPair>();
		
		while (thisScan.hasNext())
		{
			rpList.add(new ResultPair(thisScan.next(), true));
		}
		
		return rpList;
	}
	
	public void closeFile()
	{
		thisScan.close();
	}
}
