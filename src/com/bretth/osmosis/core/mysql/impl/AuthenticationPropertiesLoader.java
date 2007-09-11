package com.bretth.osmosis.core.mysql.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.bretth.osmosis.core.OsmosisRuntimeException;


/**
 * Loads database authentication details from a properties file.
 * <p>
 * The recognised properties are:
 * <ul>
 * <li>host</li>
 * <li>database</li>
 * <li>user</li>
 * <li>password</li>
 * </ul> 
 * 
 * @author Brett Henderson
 */
public class AuthenticationPropertiesLoader {
	
	private static final Logger log = Logger.getLogger(AuthenticationPropertiesLoader.class.getName());
	
	private static final String KEY_HOST = "host";
	private static final String KEY_DATABASE = "database";
	private static final String KEY_USER = "user";
	private static final String KEY_PASSWORD = "password";
	
	private File propertiesFile;
	private Properties properties;
	
	
	/**
	 * Creates a new instance.
	 * 
	 * @param propertiesFile
	 *            The location of the properties file containing authentication
	 *            information.
	 */
	public AuthenticationPropertiesLoader(File propertiesFile) {
		this.propertiesFile = propertiesFile;
	}
	
	
	private Properties loadProperties(File configFile) {
		Properties loadedProperties;
		FileInputStream fileInputStream = null;
		
		loadedProperties = new Properties();
		
		try {
			fileInputStream = new FileInputStream(configFile);
			
			loadedProperties.load(fileInputStream);
			
		} catch (IOException e) {
			throw new OsmosisRuntimeException("Unable to load properties from config file " + configFile);
		} finally {
			if (fileInputStream != null) {
				try {
					fileInputStream.close();
				} catch (IOException e) {
					log.log(Level.WARNING, "Unable to close input stream for properties file " + configFile + ".", e);
				}
			}
		}
		
		return loadedProperties;
	}
	
	
	/**
	 * Updates the login credentials with values from the properties file. Only
	 * values contained in the properties file will be added.
	 * 
	 * @param loginCredentials
	 *            The login credentials to be updated.
	 */
	public void updateLoginCredentials(DatabaseLoginCredentials loginCredentials) {
		if (properties == null) {
			properties = loadProperties(propertiesFile);
		}
		
		if (properties.containsKey(KEY_HOST)) {
			loginCredentials.setHost(properties.getProperty(KEY_HOST));
		}
		if (properties.containsKey(KEY_DATABASE)) {
			loginCredentials.setHost(properties.getProperty(KEY_DATABASE));
		}
		if (properties.containsKey(KEY_USER)) {
			loginCredentials.setHost(properties.getProperty(KEY_USER));
		}
		if (properties.containsKey(KEY_PASSWORD)) {
			loginCredentials.setHost(properties.getProperty(KEY_PASSWORD));
		}
	}
}
