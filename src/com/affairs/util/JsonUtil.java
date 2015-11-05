package com.affairs.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.bson.types.ObjectId;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.affairs.constants.JsonConstants;

public class JsonUtil extends JsonConstants  {

	public static JSONObject getJSONObjectForExtjs(List<DBObject> JsonObject)	{
		JSONObject entityJsonObjForExtjs = new JSONObject();
		//System.out.println(JsonObject);
		List<JSONObject> newjsonobj = getJsonobj(JsonObject);
		entityJsonObjForExtjs.put(ENTITY, (List<JSONObject>)newjsonobj);
		entityJsonObjForExtjs.put(SUCCESS, TRUE);
		return entityJsonObjForExtjs;
	}
	private static List<JSONObject> getJsonobj(List<DBObject> jsonObject) {
		List<JSONObject> newjsonObject = new ArrayList<JSONObject>();
		Iterator<DBObject> itr = jsonObject.iterator();
		while (itr.hasNext())	{
			JSONObject newObj = new JSONObject();
			DBObject obj = itr.next();
			Set<String> keyset = obj.keySet();
			Iterator<String> itr1 = keyset.iterator();
			while(itr1.hasNext())	{
				String key = itr1.next();
				String value = obj.get(key).toString();
				newObj.put(key, value);
			}
			newjsonObject.add(newObj);
		}
		return newjsonObject;
	}
	
	/**
	 * @param extjsJSON
	 * @return BasicDBObject of given extjs JSONObject for MongoDB type
	 */
	public static BasicDBObject getBasicDbObjectFromJson(String extjsJSON)	{
		JSONObject jsonObject = (JSONObject) JSONValue.parse(extjsJSON);
		Set<String> keyset = jsonObject.keySet();
		Iterator<String> itr = keyset.iterator();
		BasicDBObject basicDBObject = new BasicDBObject();
		while (itr.hasNext())	{
			String key = itr.next();
			String val = jsonObject.get(key).toString();
			basicDBObject.append(key, val);
		}
		return basicDBObject;
	}
	
	/**
	 * This method will convert the date values present in the json object to Date type 
	 * and return the BasicDBObject with date datatype values
	 * @param filter
	 * @return BasicDBObject with date datatype values
	 */
	public static BasicDBObject getSayDoObjForMongoDB(BasicDBObject filter)	{
		Set<String> keyset = filter.keySet();
		Iterator<String> arrayIterator = keyset.iterator();
		BasicDBObject newBasicDBObject = new BasicDBObject();
		String key =null;
		String val = null;
		DateFormat jsonDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss"); // input from EXTJS date format
		DateFormat mongoDbDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ"); // input for MongoDB date format
		while (arrayIterator.hasNext())	{
			key = arrayIterator.next();
			if(key.equals("Planned_Start_Date") || key.equals("Planned_Finish_Date") || key.equals("Actual_Start_Date") || key.equals("Actual_End_Date"))	{
				Date date = null;
				try {
					date = jsonDateFormat.parse(filter.get(key).toString());
                    String newdatestr = mongoDbDateFormat.format(date);
                    date = mongoDbDateFormat.parse(newdatestr);
				} catch (ParseException e) {
					e.printStackTrace();
				}
				newBasicDBObject.append(key, date);
			}
			else	{
				val = filter.get(key).toString();
				newBasicDBObject.append(key, val);
			}
		}
		return newBasicDBObject;
	}
	
	/**
	 * This method is used to convert the JSON _id Object to ObjectId
	 * @param idJson
	 * @return BasicDBObject of _id for ObjectId type
	 */
	public static BasicDBObject getIdObjForMongoDB(BasicDBObject idJson) {
		String key = "_id";
		String val = idJson.get(key).toString();
		BasicDBObject basicDBObject = new BasicDBObject(key, new ObjectId(val));
		return basicDBObject;
	}

}
