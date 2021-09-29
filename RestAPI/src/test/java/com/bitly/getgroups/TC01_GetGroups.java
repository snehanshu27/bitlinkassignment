package com.bitly.getgroups;

import static org.testng.AssertJUnit.assertEquals;
import org.testng.annotations.Test;
import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class TC01_GetGroups {
	 String token="cc034adfdc77be75e9adb892a0bd6d4bb1da7372";
  @Test
  public void getGroups() {
	//Verify happy path  
	 
	  try
	  {
	  RestAssured.baseURI="https://api-ssl.bitly.com";
	  RequestSpecification rs=RestAssured.given();
	  Response response=rs.header("Authorization", "Bearer "+token).request(Method.GET, "/v4/groups");
	  int statuscode=response.getStatusCode();
	  
	//Validate status code
	  assertEquals(statuscode,200);
      }
	  
	  catch(Exception e)
	  {
		  System.out.println(e);
	  }
  }
	  
   @Test
   public void getGroupsWithoutToken() {
	 //Verify if bearer token is not passed user gets 403: forbidden error
	 try
	 {
		 
		 RestAssured.baseURI="https://api-ssl.bitly.com";
		  RequestSpecification rs=RestAssured.given();
		  Response response=rs.request(Method.GET, "/v4/groups");
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
   public void getGroupsHavingIncorrectURI() {
	 //Verify if URI is incorrect user gets 404:Not Found
	 try
	 {
		 
		 RestAssured.baseURI="https://api-ssl.bitly.com";
		  RequestSpecification rs=RestAssured.given();
		  Response response=rs.request(Method.GET, "/groups");
		  int statuscode=response.getStatusCode();
		  
		  
		//Validate status code
		  assertEquals(statuscode,404);
	  
	  }
	  
	  catch(Exception e)
	  {
		  System.out.println(e);
	  }
	  
  }
}
