package br.com.fiap.properties;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

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
	private static String path = "./properties/application.properties";
	
	public static Properties getProp() {
	
		Properties props = new Properties();
		FileInputStream file;
		try {
			file = new FileInputStream(path);
			props.load(file);
			file.close();
		} catch (IOException | NullPointerException e) {
			e.printStackTrace();
			logger.error("Houve um problema ao recuperar o " + path);
		}
		return props;
		
	}
	
}
