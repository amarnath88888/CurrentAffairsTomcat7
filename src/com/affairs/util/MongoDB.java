package com.affairs.util;

import java.net.UnknownHostException;

import com.affairs.constants.MongoDBConstants;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;
import com.mongodb.ServerAddress;

/**
 * This is a singleton class to get the mongoDB connection
 * @author Amarnath
 * 
 */
public class MongoDB {
	private static MongoDB instance = null;
	private MongoClient client;
	private DB database;
	
	public static MongoDB getInstance() {
		if (instance == null) {
			instance = new MongoDB();
		}
		return instance;
	}
	private MongoDB() {
	    if (instance == null)	{
			try {
				client = new MongoClient(new ServerAddress(MongoDBConstants.HOST_NAME, MongoDBConstants.PORT_NO));
				setDatabase(client.getDB(MongoDBConstants.DB_NAME));
		    	database.authenticate(MongoDBConstants.USER_NAME, MongoDBConstants.PASSWORD);
		    	System.out.println("Mongo DB connected to " + MongoDBConstants.HOST_NAME +" to Database : " + MongoDBConstants.DB_NAME);
			} catch (UnknownHostException unknownHostExceptione) {
				unknownHostExceptione.printStackTrace();
			}
	    }
	}
	private void setDatabase(DB database) {
		this.database = database;
	}
	public DBCollection getCollection(String collectionName) {
		return database.getCollection(collectionName);
	}
}
