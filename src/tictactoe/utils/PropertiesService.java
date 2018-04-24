/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tictactoe.utils;

import java.io.InputStream;
import java.util.Properties;

/**
 * The properties service.
 * Loads an input string from a file within the project resources.
 * 
 * @author czhiller
 */
public class PropertiesService {

    /**
     *
     * The properties.
     * 
     */
    public static final Properties PROPERTIES = new Properties();

    /**
     * Loads the properties from file input.properties
     * located in project resources.
     */
    public PropertiesService() {
        try {
            InputStream in = getClass().getResourceAsStream("/resources/input.properties");
            PROPERTIES.load(in);
            in.close();

        } catch (Exception e) {

        }
    }

    /**
     * returns the property by key
     * @param key
     * @return 
     */
    public String getProperty(String key) {
        if (PROPERTIES.get(key) != null) {
            return String.valueOf(PROPERTIES.get(key));
        } else {
            return null;
        }
    }

}
