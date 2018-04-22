
import java.util.Properties;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Noah, Randy, Ricky
 */
public class Config {
    
    private Properties configFile = new java.util.Properties();
    public Config(){
	configFile = new java.util.Properties();
	try {
            configFile.load(this.getClass().getClassLoader().
            getResourceAsStream("properties.cfg"));
	}
        catch(Exception eta){
	    eta.printStackTrace();
	}
    }
 
    public int getProperty(String key)
    {
	return Integer.parseInt(this.configFile.getProperty(key));
	
    }
}
