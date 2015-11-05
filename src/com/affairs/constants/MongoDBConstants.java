package com.affairs.constants;

import com.mongodb.BasicDBObject;

public class MongoDBConstants {

	//public static final String PROPERTY_FILE_PATH = "config\\metrics.properties";
	
	public static final String HOST_NAME = "127.0.0.1";
	public static final int PORT_NO = 27017;
	public static final String DB_NAME = "Affairs";
	public static final String USER_NAME = "";
	public static final char[] PASSWORD = "".toCharArray();

	public static final String CURRENT = "current";
	
	public static final String TOWERS = "towers";
	public static final String PLATFORMS = "platforms";
	public static final String PROGRAMS = "programs";
	public static final String ENTITIES = "entities";
	public static final String SDLC = "sdlc";
	public static final String SAYDO = "saydo";

	/*public static final BasicDBObject DELINDICATORTRUE = new BasicDBObject("delInd", true);
	public static final BasicDBObject NONSELECTFIELDS = new BasicDBObject().append("createdBy", false).append("createdDate", false).append("delInd", false).append("isCurrent", false).append("updatedBy", false).append("updatedDate", false);
	public static final BasicDBObject FILTERCRITERIA = new BasicDBObject().append("isCurrent", true).append("delInd", false);*/
	
	public static final BasicDBObject DELINDICATORTRUE = new BasicDBObject("delInd", true);
	public static final BasicDBObject NONSELECTFIELDS = new BasicDBObject();
	public static final BasicDBObject FILTERCRITERIA = new BasicDBObject();
}
