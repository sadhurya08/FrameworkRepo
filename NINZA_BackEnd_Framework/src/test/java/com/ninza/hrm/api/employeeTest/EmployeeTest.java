package com.ninza.hrm.api.employeeTest;

import static io.restassured.RestAssured.given;

import java.io.IOException;
import org.hamcrest.Matchers;
import org.testng.Assert;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.Test;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.testng.annotations.Test;

import io.restassured.http.ContentType;

import com.ninza.hrm.api.baseclass.BaseAPIClass;
import com.ninza.hrm.api.genericUtility.DataBaseUtility;
import com.ninza.hrm.api.genericUtility.FileUtility;
import com.ninza.hrm.api.genericUtility.JavaUtility;
import com.ninza.hrm.api.pojoClass.EmployeePojo;
import com.ninza.hrm.api.pojoClass.ProjectPojo;
import com.ninza.hrm.constants.endPoints.IendPoint;

import org.hamcrest.Matchers;
import org.json.simple.JSONObject;
import org.testng.annotations.Test;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
//import com.mysql.cj.jdbc.Driver;
import static io.restassured.RestAssured.*;

public class EmployeeTest extends BaseAPIClass {
   
	@Test
	public void addEmployeeTest() throws SQLException, IOException
	
	{
		String BASEURI = fLib.getDataFromPropertiesFile("BASEUri");
		
		String projectName="Airtel_"+ jLib.getRandomNumber();
		String userName = "user_"+ jLib.getRandomNumber();
			
		ProjectPojo pObj = new ProjectPojo(projectName,"Created","Deepak",0);
		
	    given()
		.spec(specReqObj)
		.body(pObj)
		.when()
		.post(IendPoint.ADDProj)
		.then()
		.spec(specRespObj)
		.log().all();
		
		
		
		//aPI-2 add employee to same project
		/*
		 * contructor parameters
		 * String designation, String dob, String email, String empName, int experience,
		 * String mobileNo, String project, String role, String username
		 */
		
		EmployeePojo empObj = new EmployeePojo("Architect","30/06/1985","dipak@gmail.com",userName,18,"9756812345",projectName,"ROLE_EMPLOYEE",userName);
		
		given()
		.spec(specReqObj)
		.body(empObj)
		.when()
		.post(IendPoint.ADDEmp)
		.then()
		.assertThat().statusCode(201)
		.and()
		.time(Matchers.lessThan(3000L))
		.spec(specRespObj)
		.log().all();
		
		
		        //verify the projectName in DB Layer
				//will not work because there is no database in the system just copied the program alone
		/*
		dLib.getDbconnection();
		boolean flag = dLib.executeQueryVerifyAndGetData("select * from employee", 5, userName);
		Assert.assertTrue(flag,"Employee in DB is not verified");
	    dLib.closeDbconnection();
	    */
	}
	
	@Test
	public void addEmployeeTestWithOutEmail() throws SQLException, IOException
	
	{
		String BASEURI = fLib.getDataFromPropertiesFile("BASEUri");
		
		String projectName="Airtel_"+ jLib.getRandomNumber();
		String userName = "user_"+ jLib.getRandomNumber();
			
		ProjectPojo pObj = new ProjectPojo(projectName,"Created","Deepak",0);
		
	    given()
		.spec(specReqObj)
		.body(pObj)
		.when()
		.post(IendPoint.ADDProj)
		.then()
		.spec(specRespObj)
		.log().all();
		
		//aPI-2 add employee to same project
		/*
		 * contructor parameters
		 * String designation, String dob, String email, String empName, int experience,
		 * String mobileNo, String project, String role, String username
		 */
		
		EmployeePojo empObj = new EmployeePojo("Architect","30/06/1985","",userName,18,"9756812345",projectName,"ROLE_EMPLOYEE",userName);
		
		given()
		.spec(specReqObj)
		.body(empObj)
		.when()
		.post(IendPoint.ADDEmp)
		.then()
		.assertThat().statusCode(500)
		.and()
		.time(Matchers.lessThan(3000L))
		.spec(specRespObj)
		.log().all();
		
		
		
		
	}
	
	
	
	
	
	
	
}
