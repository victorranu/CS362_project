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

import java.io.*;
import java.util.*;
import java.lang.*;



/**
 * Performs Validation Test for url validations.
 *
 * @version $Revision: 1128446 $ $Date: 2011-05-27 13:29:27 -0700 (Fri, 27 May 2011) $
 */
public class UrlValidatorTest extends TestCase {
	
	// --------------------------------------------------------------------
	public static int MyTestCount_T = 0;
	public static int MyTestCount_F = 0;
	public static boolean displayResults = false;
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
  public void test_FullUrl_Manual()
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
  
  public void test_Scheme_Partition()
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
  
  public void test_Host_Partition()
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
  
  public void test_Port_Partition()
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
  
  public void test_Path_Partition()
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
  
  public void test_Query_Partition()
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
   
  public void test_IPv4_Partition()
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
  
  /*
   * Test is the issue found in testIPv4_Partition() is from the isValidInet4Address() method
   * Send an IP and track its progress to find the issue found in testIPv4_Partition()
   * Placed a line break on the isValidInet4Address() method and ran the below test
   * 	Bug found: line: 96
   * 		The return value is incorrect, this should return a false when a iIpSegment is > 255.
   */
  public void test_isValidInet4Address()
  {
	  String testStr = "0.0.0.256";
	  boolean result;
	   	
	  InetAddressValidator InetVal = new InetAddressValidator();
	   	
	  result = InetVal.isValidInet4Address(testStr);
	   	
	  assertEquals(result, false);
  }
   
   public void testIsValid()
   {
	   for(int i = 0;i<10000;i++)
	   {
		   
	   }
   }
   
   /*
    * Testing the tld process
    * All data pulled from http://www.iana.org/domains/root/db
    */
   public void test_Tld_Partition()
   {
	   UrlValidator urlVal = new UrlValidator(null, null, UrlValidator.ALLOW_ALL_SCHEMES);
	   
	   String fixedStr = "http://www.google.";
	   String testThisItem = "Test";	// Enter any Scheme test string here
	   boolean testThisValid = false; 	// State if the test item above is expected to be [true or false]
	   
	   ResultPair[] manualTld_GenericList = 
	   {
			   new ResultPair(fixedStr + testThisItem	, testThisValid),	// This is for a quick test check
			   new ResultPair(fixedStr + "biz"		, true),				// 1
			   new ResultPair(fixedStr + "com"		, true),				// 2
			   new ResultPair(fixedStr + "biz"		, true),				// 3
			   new ResultPair(fixedStr + "market"	, true),				// 4
			   new ResultPair(fixedStr + "zip"		, true)					// 5
	   };
	   
	   ResultPair[] manualTld_InfrastructureList = 
	   {
			   new ResultPair(fixedStr + testThisItem	, testThisValid),	// This is for a quick test check
			   new ResultPair(fixedStr + "this"		, false),				// 1
			   new ResultPair(fixedStr + "arpa"		, true),				// 2
	   };
	   
	   ResultPair[] manualTld_SponsoredList = 
	   {
			   new ResultPair(fixedStr + testThisItem	, testThisValid),	// This is for a quick test check
			   new ResultPair(fixedStr + "aero"		, true),				// 1
			   new ResultPair(fixedStr + "coop"		, true),				// 2
			   new ResultPair(fixedStr + "jobs"		, true),				// 3
			   new ResultPair(fixedStr + "post"		, true),				// 4
			   new ResultPair(fixedStr + "xxx"		, true),				// 5
	   };
	   
	   ResultPair[] manualTld_CountryList = 
	   {
			   new ResultPair(fixedStr + testThisItem	, testThisValid),	// This is for a quick test check
			   new ResultPair(fixedStr + "ax"		, true),				// 1
			   new ResultPair(fixedStr + "gg"		, true),				// 2
			   new ResultPair(fixedStr + "sa"		, true),				// 3
			   new ResultPair(fixedStr + "us"		, true),				// 4
			   new ResultPair(fixedStr + "vc"		, true),				// 5
	   };
	   
	   if (displayResults == true)	{	displayResults(urlVal, manualTld_GenericList, "TLD - Generic Partition Tests");	}
	   if (displayResults == true)	{	displayResults(urlVal, manualTld_InfrastructureList, "TLD - Infrastructure Partition Tests");	}
	   if (displayResults == true)	{	displayResults(urlVal, manualTld_SponsoredList, "TLD - Sponsored Partition Tests");	}
	   if (displayResults == true)	{	displayResults(urlVal, manualTld_CountryList, "TLD - Country Partition Tests");	}
	   
	// Quick test tlds
	   assertEquals(urlVal.isValid(manualTld_GenericList[0].item), manualTld_GenericList[0].valid);
	   assertEquals(urlVal.isValid(manualTld_InfrastructureList[0].item), manualTld_InfrastructureList[0].valid);
	   assertEquals(urlVal.isValid(manualTld_SponsoredList[0].item), manualTld_SponsoredList[0].valid);
	   assertEquals(urlVal.isValid(manualTld_CountryList[0].item), manualTld_CountryList[0].valid);
	   
	// Generic tlds
	   assertEquals(urlVal.isValid(manualTld_GenericList[1].item), manualTld_GenericList[1].valid);
	   assertEquals(urlVal.isValid(manualTld_GenericList[2].item), manualTld_GenericList[2].valid);
	   assertEquals(urlVal.isValid(manualTld_GenericList[3].item), manualTld_GenericList[3].valid);
	   assertEquals(urlVal.isValid(manualTld_GenericList[4].item), manualTld_GenericList[4].valid);
	   assertEquals(urlVal.isValid(manualTld_GenericList[5].item), manualTld_GenericList[5].valid);
	// Infrastructure tlds
	   assertEquals(urlVal.isValid(manualTld_InfrastructureList[1].item), manualTld_InfrastructureList[1].valid);
	   assertEquals(urlVal.isValid(manualTld_InfrastructureList[2].item), manualTld_InfrastructureList[2].valid);
	   assertEquals(urlVal.isValid(manualTld_InfrastructureList[3].item), manualTld_InfrastructureList[3].valid);
	   assertEquals(urlVal.isValid(manualTld_InfrastructureList[4].item), manualTld_InfrastructureList[4].valid);
	   assertEquals(urlVal.isValid(manualTld_InfrastructureList[5].item), manualTld_InfrastructureList[5].valid);
	// Sponsored tlds
	   assertEquals(urlVal.isValid(manualTld_SponsoredList[1].item), manualTld_SponsoredList[1].valid);
	   assertEquals(urlVal.isValid(manualTld_SponsoredList[2].item), manualTld_SponsoredList[2].valid);
	   assertEquals(urlVal.isValid(manualTld_SponsoredList[3].item), manualTld_SponsoredList[3].valid);
	   assertEquals(urlVal.isValid(manualTld_SponsoredList[4].item), manualTld_SponsoredList[4].valid);
	   assertEquals(urlVal.isValid(manualTld_SponsoredList[5].item), manualTld_SponsoredList[5].valid);
	// Country tlds
	   assertEquals(urlVal.isValid(manualTld_CountryList[1].item), manualTld_CountryList[1].valid);
	   assertEquals(urlVal.isValid(manualTld_CountryList[2].item), manualTld_CountryList[2].valid);
	   assertEquals(urlVal.isValid(manualTld_CountryList[3].item), manualTld_CountryList[3].valid);
	   assertEquals(urlVal.isValid(manualTld_CountryList[4].item), manualTld_CountryList[4].valid);
	   assertEquals(urlVal.isValid(manualTld_CountryList[5].item), manualTld_CountryList[5].valid);
   }
   
   /*
    * Test all known good TLDs as pulled from http://www.iana.org/domains/root/db
    */
   public void test_AllKnownTLDs()
   {
	   boolean displayThisReport = false;
	   
	   /*
	    *  Get the current dir of the program
	    *  This will work from the src dir of the eclipse IDE for this program
	    */
	   String workingDir = System.getProperty("user.dir") + "\\src\\";
	   
	   /*
	    * List of files containing types of TLDs to test
	    */
	   String[] tldFile = 
		   {
			   "IANA_Country_Codes.txt",
			   "IANA_Generic_Codes.txt",
			   "IANA_Infrastructure_Codes.txt",
			   "IANA_Sponsored_Codes.txt"
		   };
	   
	   /*
	    * Set the array to hold the failure report summary
	    * There will be 1 report summary generated per tldFile run
	    */
	   String[] failSummary = new String[tldFile.length];
	   
	   int i,f;	// loops
	   int failCount = 0;
	   ArrayList<ResultPair> rpList;
	   String fixedStr = "http://www.google.";
	   
	   UrlValidator urlVal = new UrlValidator(null, null, UrlValidator.ALLOW_ALL_SCHEMES);
	   
	   for(f = 0; f < tldFile.length; f++)
	   {
		   // Used to read data from a file
		   rpList = createResultPairList(workingDir, tldFile[f]);
		   
		   // Run these tlds through the validator
		   for(i = 0; i < rpList.size(); i++)
		   {
			   if(displayThisReport)
			   {
				   try
				   {
					   assertTrue(urlVal.isValid(fixedStr + rpList.get(i).item));
				   }
				   catch (AssertionError e)
				   {
						   System.out.println(rpList.get(i).item + " - failed");
						   failCount++;
				   }
			   }
			   else
			   {
				   assertTrue(urlVal.isValid(fixedStr + rpList.get(i).item));
			   }
		   }
		   
		   failSummary[f] =   "tld List = " + tldFile[f] + "\n"
		  					+ "Country Code count = "+ rpList.size() + "\n"
		  					+ "Falure count = " + failCount + "\n";
		   
		   failCount = 0;
	   }
	   
	   if(displayThisReport)
	   {
		   System.out.println("------------------------------------------");
		   
		   for(i = 0; i < failSummary.length; i++)
		   {
			   System.out.println(failSummary[i]);
		   }
	   }
	   
	   /*
	    * This code does not produce a failed test if displayThisReport == true due to the try block
	    * The following will check if there were any failures and cause an assertion failure if true.
	    * 
	    * If there is an assertion failure, set displayThisReport to false and run to get an assertion failure at the source
	    */
	   if(displayThisReport)
	   {
		   int checkIndex, errCount;
		   String errorCountStr;
		   
		   for(i = 0; i < failSummary.length; i++)
		   {
			   checkIndex = failSummary[i].lastIndexOf("=") + 2;
			   errorCountStr = failSummary[i].substring(checkIndex);
			   errCount = Integer.parseInt(errorCountStr.substring(0, errorCountStr.length() - 1));
			   
			   assertEquals(0, errCount);
		   }
	   }
   }
   
   public ArrayList<ResultPair> createResultPairList(String dir, String f)
   {
	   TeamProject_ReadFile theFile = new TeamProject_ReadFile();
	   ArrayList<ResultPair> rpList = new ArrayList<ResultPair>();
	   
	   theFile.openFile(dir, f);
	   rpList = theFile.readFile_CreateResultPair();
	   theFile.closeFile();
	   
	   return rpList;
   }
   
   public void display_ResultPairList(ArrayList<ResultPair> rpList)
   {
	   int i;
	   
	   for(i = 0; i < rpList.size(); i++)
	   {
		   System.out.printf("%s %s\n", rpList.get(i).item, rpList.get(i).valid);
	   }
	   
   }
   
   public void test_localHostUrls()
   {
	   int i;	// loops
	   
	   // turn local host allow on or true for these tests
	   UrlValidator urlVal = new UrlValidator(UrlValidator.ALLOW_LOCAL_URLS);
	   
	   // manual test inputs:
	   ResultPair[] man_LocalHostList = 
		   {
//			   new ResultPair(fixedStr + testThisItem	, testThisValid),					// This is for a quick test check
			   new ResultPair("http://localhost/ProjectPartB.html"				, true),	// 1 - localHost no port
			   new ResultPair("http://localhost:8080/CS362/ProjectPartB.html"	, true),	// 2 - localHost with port
		   };
	   
	   if (displayResults == true)	{	displayResults(urlVal, man_LocalHostList, "LocalHost URL Test");	}
	   
	   // assert checks
	   assertEquals(urlVal.isValid(man_LocalHostList[0].item), man_LocalHostList[0].valid);
	   assertEquals(urlVal.isValid(man_LocalHostList[1].item), man_LocalHostList[1].valid);
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
