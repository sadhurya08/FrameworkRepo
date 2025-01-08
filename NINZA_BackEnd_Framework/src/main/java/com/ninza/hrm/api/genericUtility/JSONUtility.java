package com.ninza.hrm.api.genericUtility;

import static io.restassured.RestAssured.given;

import java.io.IOException;
import java.util.List;

import com.jayway.jsonpath.JsonPath;

import io.restassured.response.Response;

public class JSONUtility {
	
	FileUtility fLib = new FileUtility();

	public String getDataOnJSONPath(Response resp,String jsonXpath)
	{
		List<Object> list = JsonPath.read(resp.asString(),jsonXpath);
		return list.get(0).toString();
	}
	

	
	public String getDataOnXpath(Response resp, String xmlXpath)
	{
		return resp.xmlPath().get(xmlXpath);
	}
	
	public boolean verifyDataOnJSONPath(Response resp, String jsonXpath,String expectedData)
	{
		List<String> list = JsonPath.read(resp.asString(), jsonXpath);
		boolean flag = false;
		for(String str: list)
		{
			if(str.equals(expectedData))
			{
				System.out.println(expectedData + "is available==PASS");
				flag=true;
			}
		}
		if(flag==false)
		{
			System.out.println(expectedData + "is available==FAIL");
		}
		return flag;
	}
	
	public String getAccessToken() throws IOException 
	{
	
	Response resp =	given()
			.formParam("client_id",fLib.getDataFromPropertiesFile("ClientId"))
			.formParam("client_secret",fLib.getDataFromPropertiesFile("ClientSecret"))
			.formParam("grant_type","client_credentials")
			.when()
			.post("http://49.249.28.218:8180/auth/realms/ninza/protocol/openid-connect/token");
			resp.then()
			.log().all();
			
			
			//capture the token from the response
		    String token =	resp.jsonPath().get("access_token");
			
			return token;
}

	
	
}
	
	
	
	
	
