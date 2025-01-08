package com.ninza.hrm.api.baseclass;

import java.io.IOException;
import java.sql.SQLException;

import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import com.ninza.hrm.api.genericUtility.DataBaseUtility;
import com.ninza.hrm.api.genericUtility.FileUtility;
import com.ninza.hrm.api.genericUtility.JavaUtility;

import static io.restassured.RestAssured.*;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class BaseAPIClass {

	public JavaUtility jLib = new JavaUtility();
	public FileUtility fLib = new FileUtility();
	public DataBaseUtility dLib = new DataBaseUtility();
	public static RequestSpecification specReqObj;
	public static  ResponseSpecification specRespObj;
	
	@BeforeSuite
	public void configBS() throws SQLException, IOException
	{
		
		dLib.getDbconnection();
		//System.out.println("=======ConnectToDB=========");
		
		RequestSpecBuilder builder = new RequestSpecBuilder();
		builder.setContentType(ContentType.JSON);
		//builder.setAuth(basic("username", "password"));
		//builder.addHeader("", "");
		builder.setBaseUri(fLib.getDataFromPropertiesFile("BASEUri"));
	     specReqObj = builder.build();
		
	    ResponseSpecBuilder resbuilder = new   ResponseSpecBuilder();
	    resbuilder.expectContentType(ContentType.JSON);
	    specRespObj = resbuilder.build();
	    
	    
	    
	}
	
	@AfterSuite
	public void configAS() throws SQLException
	{
		//dLib.closeDbconnection();
		System.out.println("=======DisConnectToDB=========");
	}
}
