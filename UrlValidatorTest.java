/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


import junit.framework.TestCase;





/**
 * Performs Validation Test for url validations.
 *
 * @version $Revision: 1128446 $ $Date: 2011-05-27 13:29:27 -0700 (Fri, 27 May 2011) $
 */
public class UrlValidatorTest extends TestCase {
	
	// --------------------------------------------------------------------
	public static int MyTestCount_T = 0;
	public static int MyTestCount_F = 0;
	public static boolean displayResults = true;
	// --------------------------------------------------------------------
		
   private boolean printStatus = false;
   private boolean printIndex = false;//print index that indicates current scheme,host,port,path, query test were using.

   public UrlValidatorTest(String testName) 
   {
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
	   assertEquals(urlVal.isValid(manualUrlList[0].item), manualUrlList[0].valid);
	   assertEquals(urlVal.isValid(manualUrlList[1].item), manualUrlList[1].valid);
	   assertEquals(urlVal.isValid(manualUrlList[2].item), manualUrlList[2].valid);
	   assertEquals(urlVal.isValid(manualUrlList[3].item), manualUrlList[3].valid);
	   assertEquals(urlVal.isValid(manualUrlList[4].item), manualUrlList[4].valid);
	   assertEquals(urlVal.isValid(manualUrlList[5].item), manualUrlList[5].valid);
	   assertEquals(urlVal.isValid(manualUrlList[6].item), manualUrlList[6].valid);
	   assertEquals(urlVal.isValid(manualUrlList[7].item), manualUrlList[7].valid);
	   assertEquals(urlVal.isValid(manualUrlList[8].item), manualUrlList[8].valid);
	   assertEquals(urlVal.isValid(manualUrlList[9].item), manualUrlList[9].valid);
	   assertEquals(urlVal.isValid(manualUrlList[10].item), manualUrlList[10].valid);
	   assertEquals(urlVal.isValid(manualUrlList[11].item), manualUrlList[11].valid);
	   assertEquals(urlVal.isValid(manualUrlList[12].item), manualUrlList[12].valid);
	   assertEquals(urlVal.isValid(manualUrlList[13].item), manualUrlList[13].valid);
	   assertEquals(urlVal.isValid(manualUrlList[14].item), manualUrlList[14].valid);
	   assertEquals(urlVal.isValid(manualUrlList[15].item), manualUrlList[15].valid);
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
	   assertEquals(urlVal.isValid(manualSchemeList[0].item), manualSchemeList[0].valid);
	   assertEquals(urlVal.isValid(manualSchemeList[1].item), manualSchemeList[1].valid);
	   assertEquals(urlVal.isValid(manualSchemeList[2].item), manualSchemeList[2].valid);
	   assertEquals(urlVal.isValid(manualSchemeList[3].item), manualSchemeList[3].valid);
	   assertEquals(urlVal.isValid(manualSchemeList[4].item), manualSchemeList[4].valid);
	   assertEquals(urlVal.isValid(manualSchemeList[5].item), manualSchemeList[5].valid);
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
		
		assertEquals(urlVal.isValid(manualHostList[0].item), manualHostList[0].valid);
		assertEquals(urlVal.isValid(manualHostList[1].item), manualHostList[1].valid);
		assertEquals(urlVal.isValid(manualHostList[2].item), manualHostList[2].valid);
		assertEquals(urlVal.isValid(manualHostList[3].item), manualHostList[3].valid);
		assertEquals(urlVal.isValid(manualHostList[4].item), manualHostList[4].valid);
		assertEquals(urlVal.isValid(manualHostList[5].item), manualHostList[5].valid);
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
		
		assertEquals(urlVal.isValid(manualPortList[0].item), manualPortList[0].valid);
		assertEquals(urlVal.isValid(manualPortList[1].item), manualPortList[1].valid);
		assertEquals(urlVal.isValid(manualPortList[2].item), manualPortList[2].valid);
		assertEquals(urlVal.isValid(manualPortList[3].item), manualPortList[3].valid);
		assertEquals(urlVal.isValid(manualPortList[4].item), manualPortList[4].valid);
		assertEquals(urlVal.isValid(manualPortList[5].item), manualPortList[5].valid);
		assertEquals(urlVal.isValid(manualPortList[6].item), manualPortList[6].valid);
		assertEquals(urlVal.isValid(manualPortList[7].item), manualPortList[7].valid);
		assertEquals(urlVal.isValid(manualPortList[8].item), manualPortList[8].valid);
		assertEquals(urlVal.isValid(manualPortList[9].item), manualPortList[9].valid);
		assertEquals(urlVal.isValid(manualPortList[10].item), manualPortList[10].valid);
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
		
		assertEquals(urlVal.isValid(manualPathList[0].item), manualPathList[0].valid);
		assertEquals(urlVal.isValid(manualPathList[1].item), manualPathList[1].valid);
		assertEquals(urlVal.isValid(manualPathList[2].item), manualPathList[2].valid);
		assertEquals(urlVal.isValid(manualPathList[3].item), manualPathList[3].valid);
		assertEquals(urlVal.isValid(manualPathList[4].item), manualPathList[4].valid);
		assertEquals(urlVal.isValid(manualPathList[5].item), manualPathList[5].valid);
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
		
		assertEquals(urlVal.isValid(manualQueryList[0].item), manualQueryList[0].valid);
		assertEquals(urlVal.isValid(manualQueryList[1].item), manualQueryList[1].valid);
		assertEquals(urlVal.isValid(manualQueryList[2].item), manualQueryList[2].valid);
		assertEquals(urlVal.isValid(manualQueryList[3].item), manualQueryList[3].valid);
		assertEquals(urlVal.isValid(manualQueryList[4].item), manualQueryList[4].valid);
		assertEquals(urlVal.isValid(manualQueryList[5].item), manualQueryList[5].valid);
  }
   
  public void testIPv4_Partition()
  {
	   UrlValidator urlVal = new UrlValidator(null, null, UrlValidator.ALLOW_ALL_SCHEMES);
	   
	   String testThisItem = "Test";	// Enter any Scheme test string here
	   boolean testThisValid = false; 	// State if the test item above is expected to be [true or false]
	   
	   String fixedStr = "http://";
	   
	   ResultPair[] manualIPv4List = 
	   {																		// Test #
			   new ResultPair(fixedStr + testThisItem		, testThisValid),	// This is for a quick test check
			   new ResultPair(fixedStr + ""					, false),			// 1
			   new ResultPair(fixedStr + "..."				, false),			// 2
			   new ResultPair(fixedStr + "0.0.0.0"			, true),			// 3
			   new ResultPair(fixedStr + "12.241.195.1"		, true),			// 4
			   new ResultPair(fixedStr + "255.255.255.255"	, true),			// 5
			   new ResultPair(fixedStr + "256.0.0.0"		, false),			// 6
			   new ResultPair(fixedStr + "0.256.0.0"		, false),			// 7
			   new ResultPair(fixedStr + "0.0.256.0"		, false),			// 8
			   new ResultPair(fixedStr + "0.0.0.256"		, false),			// 9
			   new ResultPair(fixedStr + "xyz.0.0.0"		, false),			// 10
			   new ResultPair(fixedStr + "0.xyz.0.0"		, false),			// 11
			   new ResultPair(fixedStr + "0.0.xyz.0"		, false),			// 12
			   new ResultPair(fixedStr + "0.0.0.xyz"		, false)			// 13
	   };
	
		if (displayResults == true)	{	displayResults(urlVal, manualIPv4List, "IPv4 Partition Tests");	}
		
		assertEquals(urlVal.isValid(manualIPv4List[0].item), manualIPv4List[0].valid);
		assertEquals(urlVal.isValid(manualIPv4List[1].item), manualIPv4List[1].valid);
		assertEquals(urlVal.isValid(manualIPv4List[2].item), manualIPv4List[2].valid);
		assertEquals(urlVal.isValid(manualIPv4List[3].item), manualIPv4List[3].valid);
		assertEquals(urlVal.isValid(manualIPv4List[4].item), manualIPv4List[4].valid);
		assertEquals(urlVal.isValid(manualIPv4List[5].item), manualIPv4List[5].valid);
		assertEquals(urlVal.isValid(manualIPv4List[6].item), manualIPv4List[6].valid);
		assertEquals(urlVal.isValid(manualIPv4List[7].item), manualIPv4List[7].valid);
		assertEquals(urlVal.isValid(manualIPv4List[8].item), manualIPv4List[8].valid);
		assertEquals(urlVal.isValid(manualIPv4List[9].item), manualIPv4List[9].valid);
		assertEquals(urlVal.isValid(manualIPv4List[10].item), manualIPv4List[10].valid);
		assertEquals(urlVal.isValid(manualIPv4List[11].item), manualIPv4List[11].valid);
		assertEquals(urlVal.isValid(manualIPv4List[12].item), manualIPv4List[12].valid);
		assertEquals(urlVal.isValid(manualIPv4List[13].item), manualIPv4List[13].valid);
  }
   
   public void testIsValid()
   {
	   for(int i = 0;i<10000;i++)
	   {
		   
	   }
   }
   
   public void testAnyOtherUnitTest()
   {
	   
   }
   /**
    * Create set of tests by taking the testUrlXXX arrays and
    * running through all possible permutations of their combinations.
    *
    * @param testObjects Used to create a url.
    */
   

}
