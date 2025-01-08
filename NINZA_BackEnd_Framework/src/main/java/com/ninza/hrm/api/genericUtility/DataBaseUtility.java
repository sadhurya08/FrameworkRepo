package com.ninza.hrm.api.genericUtility;

import java.sql.Connection;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.mysql.cj.jdbc.Driver;



public class DataBaseUtility {
	static Connection con=null;
	 static ResultSet result = null;
	FileUtility fLib = new FileUtility();

	public void getDbconnection(String url,String username,String password) throws SQLException
	{
		try {
		Driver driver = new Driver();
		DriverManager.registerDriver(driver);
		
		con =DriverManager.getConnection(url,username,password);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	 }
	//"jdbc:mysql://localhost:3306/ninza_hrm"
	
	public void getDbconnection() throws SQLException
	{
		try {
		Driver driver = new Driver();
		DriverManager.registerDriver(driver);
		
		con =DriverManager.getConnection(fLib.getDataFromPropertiesFile("DBUrl"),fLib.getDataFromPropertiesFile("DB_Username"),fLib.getDataFromPropertiesFile("DB_Password"));
		}
		catch(Exception e)
		{	
			e.printStackTrace();
		}
	 }
	
	public void closeDbconnection() throws SQLException
	{
	    try {
		con.close();
	    }
	    catch(Exception e) {
	    	e.printStackTrace();
	    }
	}
	
	//while doing the jdbc program do it in try catch block to handle the exception
	
	public ResultSet executeSelectQuery(String query) throws SQLException
	{   ResultSet result;
	    try {
		Statement stat = con.createStatement();
	    result = stat.executeQuery(query);
		}
	    catch(Exception e)
	    {	
	    	e.printStackTrace();
	    }
	    return result;
     }
	
	public int executeNonSelectQuery(String query)
	{
		int result =0;
		try {
			Statement stat = con.createStatement();
			result = stat.executeUpdate(query);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return result;
	}
	
	
	public static boolean executeQueryVerifyAndGetData(String query, int columnIndex,String expectedData) throws SQLException
	{
		
		boolean flag = false;
		try {
			result = con.createStatement().executeQuery(query);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		while(result.next())
		{
			if(result.getString(columnIndex).equals(expectedData))
			{
				flag=true;
				break;
			}
		}
		if(flag)
		{
			System.out.println(expectedData + "===> data verified in data base table");
			return true;
			
		}
		else {
			System.out.println(columnIndex + "===> data not verified in data base table");
			return false;
			
		}
		
	
	}
	
	//first connect to database
	//next either call select query or non select query
	//at the end close the connection
}
