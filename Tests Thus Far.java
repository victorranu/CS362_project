import junit.framework.TestCase;

public class UrlValidatorTest extends TestCase {

	// --------------------------------------------------------------------
	public static int MyTestCount_T = 0;
	public static int MyTestCount_F = 0;
	public static boolean displayResults = false;
	// --------------------------------------------------------------------
	
   private boolean printStatus = false;
   private boolean printIndex = false;	//print index that indicates current scheme,host,port,path, query test were using.

   public UrlValidatorTest(String testName) {
      super(testName);
   }
   
   public void displayResults(UrlValidator urlVal, ResultPair[] testPair, String testLabel)
	{
	   boolean isVal_result;
	   boolean pairCheck;
	   
		System.out.println("\n==========================================================================================================");
		System.out.println(testLabel);
		System.out.println("----------------------------------------------------------------------------------------------------------");
		for (int i = 0; i < testPair.length; ++i)
		{
			isVal_result = urlVal.isValid(testPair[i].item);
			pairCheck = testPair[i].valid;
			
			if (isVal_result == pairCheck)
			{
				System.out.format("PASS -- Result / Expected == %s / %s --- URL tested = %s\n", isVal_result, pairCheck, testPair[i].item);
			}
			else
			{
				System.out.format("FAIL -- Result / Expected == %s / %s --- URL tested = %s\n", isVal_result, pairCheck, testPair[i].item);
			}
			
		}
		System.out.println("==========================================================================================================");
	}
   
   //public void testManualTest()
   public void testFullUrl_Manual()
   {
	   String testThisItem = "Test";	// Enter any URL test string here
	   boolean testThisValid = false; 	// State if the test item above is expected to be [true or false]

	   UrlValidator urlVal = new UrlValidator(null, null, UrlValidator.ALLOW_ALL_SCHEMES);
		
	   ResultPair[] manualUrlList = 
	   {																			// Test #
			   new ResultPair(testThisItem						, testThisValid),	// 0 - This is for a quick test check
			   new ResultPair("http://www.google.com"			, true),			// 1
			   new ResultPair("https://google.com:99"			, true),			// 2
			   new ResultPair("www.google.com"					, false),			// 3
			   new ResultPair("https://google.com:3535"			, true),			// 4
			   new ResultPair("http://google.com."				, false),			// 5
			   new ResultPair("http://256.256.256.256"			, false),			// 6
			   new ResultPair("http://google.com?action=lend"	, true),			// 7
			   new ResultPair("http://255.255.255.255"			, true),			// 8
			   new ResultPair("http://1.0.9.4"					, true),			// 9
			   new ResultPair("http://255.0.-10.255"			, false),			// 10
			   new ResultPair(""								, false),			// 11
			   new ResultPair("x"								, false),			// 12
			   new ResultPair("9"								, false),			// 13
			   new ResultPair("https://123.123.123.123.com"		, true),			// 14
			   new ResultPair("https://www.123.123.123.123.com"	, true)				// 15
		};
		
		if (displayResults == true)	{	displayResults(urlVal, manualUrlList, "Manual Tests");	}
		
		// the assert statements (run each assert w/out loop so we know which assert fails)
		   assertEquals(manualUrlList[0].valid, false);
		   assertEquals(manualUrlList[1].valid, true);
		   assertEquals(manualUrlList[2].valid, true);
		   assertEquals(manualUrlList[3].valid, false);
		   assertEquals(manualUrlList[4].valid, true);
		   assertEquals(manualUrlList[5].valid, false);
		   assertEquals(manualUrlList[6].valid, false);
		   assertEquals(manualUrlList[7].valid, true);
		   assertEquals(manualUrlList[8].valid, true);
		   assertEquals(manualUrlList[9].valid, true);
		   assertEquals(manualUrlList[10].valid, false);
		   assertEquals(manualUrlList[11].valid, false);
		   assertEquals(manualUrlList[12].valid, false);
		   assertEquals(manualUrlList[13].valid, false);
		   assertEquals(manualUrlList[14].valid, true);
		   assertEquals(manualUrlList[15].valid, true);
   }
   
   /*   
   public void testYourFirstPartition()
   {
	   
   }
   
   public void testYourSecondPartition()
   {
	   
   }
  */
   
   public void testScheme_Partition()
   {
	   UrlValidator urlVal = new UrlValidator(null, null, UrlValidator.ALLOW_ALL_SCHEMES);
	   
	   String testThisItem = "Test";	// Enter any Scheme test string here
	   boolean testThisValid = false; 	// State if the test item above is expected to be [true or false]
	   
	   String fixedStr = "www.google.com";
	   
	   ResultPair[] manualSchemeList = 
	   {																// Test #
			   new ResultPair(testThisItem	+ fixedStr, testThisValid),	// 0 - This is for a quick test check
			   new ResultPair(""			+ fixedStr, false),			// 1
			   new ResultPair("://"			+ fixedStr, false),			// 2
			   new ResultPair("http://"		+ fixedStr, true),			// 3
			   new ResultPair("https://"	+ fixedStr, true),			// 4
			   new ResultPair("ftp://"		+ fixedStr, true)			// 5
	   };
	   
	   if (displayResults == true)	{	displayResults(urlVal, manualSchemeList, "Scheme Partition Tests");	}
	   
	   // the assert statements (run each assert w/out loop so we know which assert fails)
	   assertEquals(manualSchemeList[0].valid, false);
	   assertEquals(manualSchemeList[1].valid, false);
	   assertEquals(manualSchemeList[2].valid, false);
	   assertEquals(manualSchemeList[3].valid, true);
	   assertEquals(manualSchemeList[4].valid, true);
	   assertEquals(manualSchemeList[5].valid, true);
   }
   
   public void testHost_Partition()
   {
	   UrlValidator urlVal = new UrlValidator(null, null, UrlValidator.ALLOW_ALL_SCHEMES);
	   
	   String testThisItem = "Test";	// Enter any Scheme test string here
	   boolean testThisValid = false; 	// State if the test item above is expected to be [true or false]
	   
	   String fixedPre = "http://";
	   String fixedPost = "?action=view";
	   
	   ResultPair[] manualHostList = 
	   {																					// Test #
			   new ResultPair(fixedPre + testThisItem		+ fixedPost, testThisValid),	// This is for a quick test check
			   new ResultPair(fixedPre + "google.com" 		+ fixedPost, true),				// 1
			   new ResultPair(fixedPre + "www.google" 		+ fixedPost, false),			// 2
			   new ResultPair(fixedPre + "www@google.com" 	+ fixedPost, false),			// 3
			   new ResultPair(fixedPre + "www.com" 			+ fixedPost, true),				// 4
			   new ResultPair(fixedPre + "www.google.com" 	+ fixedPost, true)				// 5
	   };
	
		if (displayResults == true)	{	displayResults(urlVal, manualHostList, "Host Partition Tests");	}
		
		assertEquals(manualHostList[0].valid, false);
		assertEquals(manualHostList[1].valid, true);
		assertEquals(manualHostList[2].valid, false);
		assertEquals(manualHostList[3].valid, false);
		assertEquals(manualHostList[4].valid, true);
		assertEquals(manualHostList[5].valid, true);
   }
   
   public void testPort_Partition()
   {
	   UrlValidator urlVal = new UrlValidator(null, null, UrlValidator.ALLOW_ALL_SCHEMES);
	   
	   String testThisItem = "Test";	// Enter any Scheme test string here
	   boolean testThisValid = false; 	// State if the test item above is expected to be [true or false]
	   
	   String fixedStr = "http://www.google.com";
	   
	   ResultPair[] manualPortList = 
	   {																// Test #
			   new ResultPair(fixedStr + testThisItem, testThisValid),	// This is for a quick test check
			   new ResultPair(fixedStr + ""			, true),			// 1
			   new ResultPair(fixedStr + ":"		, false),			// 2
			   new ResultPair(fixedStr + ":1"		, true),			// 3
			   new ResultPair(fixedStr + ":12"		, true),			// 4
			   new ResultPair(fixedStr + ":123"		, true),			// 5
			   new ResultPair(fixedStr + ":1234"	, true),			// 6
			   new ResultPair(fixedStr + ":12345"	, true),			// 7
			   new ResultPair(fixedStr + ":123456"	, false),			// 8
			   new ResultPair(fixedStr + ":1234567"	, false),			// 9
			   new ResultPair(fixedStr + ":xyz"		, false)			// 10
	   };
	
		if (displayResults == true)	{	displayResults(urlVal, manualPortList, "Port Partition Tests");	}
		
		assertEquals(manualPortList[0].valid, false);
		assertEquals(manualPortList[1].valid, true);
		assertEquals(manualPortList[2].valid, false);
		assertEquals(manualPortList[3].valid, true);
		assertEquals(manualPortList[4].valid, true);
		assertEquals(manualPortList[5].valid, true);
		assertEquals(manualPortList[6].valid, true);
		assertEquals(manualPortList[7].valid, true);
		assertEquals(manualPortList[8].valid, false);
		assertEquals(manualPortList[9].valid, false);
		assertEquals(manualPortList[10].valid, false);
		
   }
   
   public void testPath_Partition()
   {
	   UrlValidator urlVal = new UrlValidator(null, null, UrlValidator.ALLOW_ALL_SCHEMES);
	   
	   String testThisItem = "Test";	// Enter any Scheme test string here
	   boolean testThisValid = false; 	// State if the test item above is expected to be [true or false]
	   
	   String fixedStr = "http://www.google.com";
	   
	   ResultPair[] manualPathList = 
	   {																	// Test #
			   new ResultPair(fixedStr + testThisItem, testThisValid),	// This is for a quick test check
			   new ResultPair(fixedStr + ""				, true),		// 1
			   new ResultPair(fixedStr + "this"			, false),		// 2
			   new ResultPair(fixedStr + "/this"		, true),		// 3
			   new ResultPair(fixedStr + "/this/that"	, true),		// 4
			   new ResultPair(fixedStr + "//this"		, false)		// 5
	   };
	
		if (displayResults == true)	{	displayResults(urlVal, manualPathList, "Path Partition Tests");	}
		
		assertEquals(manualPathList[0].valid, false);
		assertEquals(manualPathList[1].valid, true);
		assertEquals(manualPathList[2].valid, false);
		assertEquals(manualPathList[3].valid, true);
		assertEquals(manualPathList[4].valid, true);
		assertEquals(manualPathList[5].valid, false);
   }
   
   public void testQuery_Partition()
   {
	   UrlValidator urlVal = new UrlValidator(null, null, UrlValidator.ALLOW_ALL_SCHEMES);
	   
	   String testThisItem = "Test";	// Enter any Scheme test string here
	   boolean testThisValid = false; 	// State if the test item above is expected to be [true or false]
	   
	   String fixedStr = "http://www.google.com";
	   
	   ResultPair[] manualQueryList = 
	   {																			// Test #
			   new ResultPair(fixedStr + testThisItem			, testThisValid),	// This is for a quick test check
			   new ResultPair(fixedStr + ""						, true),			// 1
			   new ResultPair(fixedStr + "url"					, false),			// 2
			   new ResultPair(fixedStr + "?this=that"			, true),			// 3
			   new ResultPair(fixedStr + "?action=this&that=y0"	, true),			// 4
			   new ResultPair(fixedStr + "??"					, true)				// 5
	   };
	
		if (displayResults == true)	{	displayResults(urlVal, manualQueryList, "Query Partition Tests");	}
		
		assertEquals(manualQueryList[0].valid, false);
		assertEquals(manualQueryList[1].valid, true);
		assertEquals(manualQueryList[2].valid, false);
		assertEquals(manualQueryList[3].valid, true);
		assertEquals(manualQueryList[4].valid, true);
		assertEquals(manualQueryList[5].valid, true);
   }
}