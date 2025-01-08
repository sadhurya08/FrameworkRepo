package com.ninza.hrm.api.genericUtility;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class FileUtility {

	public String getDataFromPropertiesFile(String key) throws IOException
	{
		//key is the value in the property file like browser,url,username,password
		FileInputStream fis = new FileInputStream("./config_env_data/configEnvdata.properties");
		Properties pobj = new Properties();
		pobj.load(fis);
		
		String data = pobj.getProperty(key); 
		
		return data;
		
	}
}

