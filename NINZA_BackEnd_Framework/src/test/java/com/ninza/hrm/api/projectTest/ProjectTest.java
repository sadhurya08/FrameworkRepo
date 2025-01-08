package com.ninza.hrm.api.projectTest;

import org.hamcrest.Matchers;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;
import org.testng.Assert;
import io.restassured.http.ContentType;

import com.ninza.hrm.api.baseclass.BaseAPIClass;
import com.ninza.hrm.api.genericUtility.DataBaseUtility;
import com.ninza.hrm.api.genericUtility.FileUtility;
import com.ninza.hrm.api.genericUtility.JavaUtility;
import com.ninza.hrm.api.pojoClass.ProjectPojo;
import com.ninza.hrm.constants.endPoints.IendPoint;

import org.hamcrest.Matchers;
import org.json.simple.JSONObject;
import org.testng.annotations.Test;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
//import com.mysql.cj.jdbc.Driver;
import static io.restassured.RestAssured.*;
import java.util.Random;

public class ProjectTest extends BaseAPIClass{

	ProjectPojo pObj;
	
	@Test
	public void addSingleProjectWithCreatedTest() throws SQLException, IOException
	{
	
		String BASEURI = fLib.getDataFromPropertiesFile("BASEUri");
		
		String actualSuccessMsg = "Successfully Added" ;
		String  projectName = "ABC_"+ jLib.getRandomNumber();
		
		pObj = new ProjectPojo(projectName,"Created","Deepak",0);
		
		//verify the projectName in aPI layer
		Response resp=	given()
						.spec(specReqObj)
						.body(pObj)
						.when()
						.post(IendPoint.ADDProj);
		
						resp.then()
						.assertThat().statusCode(201)
						.assertThat().time(Matchers.lessThan(3000L))
						.spec(specRespObj)
						.log().all();
						
		String actualMsg=resp.jsonPath().get("msg");
		Assert.assertEquals(actualSuccessMsg , actualMsg);
		
	
		//verify the projectName in DB Layer
		//will not work because there is no database in the system just copied the program alone
	/*
		dLib.getDbconnection();
		boolean flag = dLib.executeQueryVerifyAndGetData("select * from project", 4, projectName);
		
		Assert.assertTrue(flag,"Project in DB is not verified");
		dLib.closeDbconnection();
	*/
		}
	
	
	@Test(dependsOnMethods = "addSingleProjectWithCreatedTest")
	public void createDuplicateProjectTest() throws IOException
	{
		String BASEURI = fLib.getDataFromPropertiesFile("BASEUri");
		
		given()
		.spec(specReqObj).body(pObj)
		.when()
		.post(IendPoint.ADDProj)
		.then()
		.assertThat().statusCode(409)
		.spec(specRespObj)
		.log().all();
				
	}
	
	
	
	
	
	
	
	
	
}
