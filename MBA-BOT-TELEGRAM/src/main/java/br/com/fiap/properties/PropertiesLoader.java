package br.com.fiap.properties;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;

/**
 * 
 * 
 * Classe responsavel por recuperar os atributos setados no properties
 * @author lucasrodriguesdonascimento
 *
 */
public class PropertiesLoader {

	private static Logger logger;
	private static String path = "application.properties";
	private static Properties props;

	public static Properties getProp(){
		if(props==null){
			props = loadProps();
		}
		return props;
	}
	
	private static Properties loadProps() {
		Properties props = new Properties();
		try {
			InputStream input = Thread.currentThread().getContextClassLoader().getResourceAsStream(path);
			props.load(input);
		} catch (IOException | NullPointerException e) {
			e.printStackTrace();
			logger.error("Houve um problema ao recuperar o " + path);
		}
		return props;
		
	}
	
}
