package br.com.fiap.properties;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Classe responsavel por recuperar os atributos setados no properties
 *
 * @author lucasrodriguesdonascimento
 */
public class PropertiesLoader {

    private static final Logger logger = LogManager.getLogger(PropertiesLoader.class.getName());

    private static final String path = "application.properties";
    private static Properties props = loadProps();

    /**
     * Propriedades da aplicação
     * <p>
     *  retorna o objeto Singleton com as propriedades
     * @return
     */
    public static Properties getProp() {
        if (props == null) {
            props = loadProps();
        }
        return props;
    }

    /**
     * Carrega os valores das propriedades
     * @return
     */
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
