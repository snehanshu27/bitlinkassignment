package com.bitly.getgroups;

import static org.testng.AssertJUnit.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.testng.annotations.Test;
import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import junit.framework.Assert;

public class TC02_CreateBitlink {
	String token="cc034adfdc77be75e9adb892a0bd6d4bb1da7372";
  @Test
  public void createBitlink() {
	  
	//Verify with valid input user is able to successfully create a link
	  try{
		 
		  DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		  LocalDateTime now = LocalDateTime.now();
		  
		  RestAssured.baseURI="https://api-ssl.bitly.com";
		  RequestSpecification rs=RestAssured.given();
		 
		  /*URL mentioned in the request body has to be replaced with a new URL before each run else step 46 will fail as it will update the existing bitlink and return 200 instead if 201*/
          Response response=rs.header("Authorization", "Bearer "+token).contentType("application/json").body("{\"long_url\":\"https://stackoverflow.com/questions/622\"}").request(Method.POST,"/v4/shorten");
          int statuscode = response.getStatusCode();
          String successMessage=response.getStatusLine();
          String responsebody=response.getBody().asString();
          JsonPath jp = new JsonPath(responsebody);
          
          //Validate if response body contains specific string
          assertTrue(responsebody.contains("created_at"));
          assertTrue(responsebody.contains("id"));
          assertTrue(responsebody.contains("link"));
          assertTrue(responsebody.contains("custom_bitlinks"));
          assertTrue(responsebody.contains("long_url"));
          
          //Validate status code
          assertEquals(statuscode,201);
          
          //Validate if the specific JSON element is equal to expected value
          assertTrue("Success message is as expected", successMessage.contains("201 Created"));
          //URL used in step 29 should be the same for step 48 as well
          assertEquals("https://stackoverflow.com/questions/622",jp.get("long_url"));
          assertTrue("New ID is created", jp.get("id").toString().contains("bit.ly"));
          assertTrue("Created date is as expected", jp.get("created_at").toString().contains(dtf.format(now)));
          assertTrue("Link is as expected", jp.get("link").toString().contains("https://bit.ly/"));  
		  	  
	  }
	  catch(Exception e)
	  {
		  System.out.println(e);
	  }
	  
  }
  
  @Test
  public void createBitlinkWithoutToken() {
	 //Verify if bearer token is not passed user gets 403: forbidden error
	 try
	 {
		 
		 RestAssured.baseURI="https://api-ssl.bitly.com";
		  RequestSpecification rs=RestAssured.given();
		  Response response=rs.contentType("application/json").body("{\"long_url\":\"https://google.com\"}").request(Method.POST,"/v4/shorten");
		  int statuscode=response.getStatusCode();
		  
		//Validate status code
		  assertEquals(statuscode,403);
	  
	  }
	  
	  catch(Exception e)
	  {
		  System.out.println(e);
	  }
	  
 }
  
  @Test
  public void createBitlinkHavingIncorrectURI() {
	 //Verify if URI is incorrect URI gets 404:Not Found
	 try
	 {
		 
		 RestAssured.baseURI="https://api-ssl.bitly.com";
		  RequestSpecification rs=RestAssured.given();
		  Response response=rs.header("Authorization", "Bearer "+token).contentType("application/json").body("{\"long_url\":\"https://google.com\"}").request(Method.POST,"/v4/shorte");
		  int statuscode=response.getStatusCode();
		  
		//Validate status code
		  assertEquals(statuscode,404);
	  
	  }
	  
	  catch(Exception e)
	  {
		  System.out.println(e);
	  }
	  
 }
  
  @Test
  public void createBitlinkHavingIncorrectJSONBody() {
	 //Verify for incorrect JSON Body user gets 400:Bad Request
	 try
	 {
		 
		 RestAssured.baseURI="https://api-ssl.bitly.com";
		  RequestSpecification rs=RestAssured.given();
		  Response response=rs.header("Authorization", "Bearer "+token).contentType("application/json").body("{\"long_url\":\"\"}").request(Method.POST,"/v4/shorten");
		  int statuscode=response.getStatusCode();
		  
		//Validate status code
		  Assert.assertEquals(statuscode,400);
	  
	  }
	  
	  catch(Exception e)
	  {
		  System.out.println(e);	
	  }
	  
 }
  
  @Test
  public void createBitlinkHavingIncorrectContentType() {
	 //Verify for incorrect Content type user gets 406:Not Acceptable
	 try
	 {
		 
		 RestAssured.baseURI="https://api-ssl.bitly.com";
		  RequestSpecification rs=RestAssured.given();
		  Response response=rs.header("Authorization", "Bearer "+token).contentType("application/xml").body("{\"long_url\":\"https://google.com\"}").request(Method.POST,"/v4/shorten");
		  int statuscode=response.getStatusCode();
		  
		//Validate status code
		  assertEquals(statuscode,406);
	  
	  }
	  
	  catch(Exception e)
	  {
		  System.out.println(e);
	  }
	  
 }
  

}
