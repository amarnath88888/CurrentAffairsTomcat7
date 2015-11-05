/**
 * @author Amar
 */
package com.affairs.Handler;

import java.util.List;

import org.json.simple.JSONObject;

import com.affairs.service.CurrentService;
import com.affairs.util.JsonUtil;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.affairs.constants.JsonConstants;
import com.affairs.constants.MongoDBConstants;
import com.affairs.util.MongoDB;

/**
 * @author Amar
 *
 */
public class BusinessHandler extends JsonConstants{

	public JSONObject getResponse(String operation, BasicDBObject jsonDBObject)	{
		List<DBObject> entities = null;
		CurrentService currService = new CurrentService(MongoDB.getInstance().getCollection(MongoDBConstants.CURRENT));
		
		if(JsonConstants.FIND.equals(operation))	{
			entities = currService.getAllNews();
		}
		else if(JsonConstants.INSERT.equals(operation))	{
			//BasicDBObject newJSONDBObject = JsonUtil.getSayDoObjForMongoDB(jsonDBObject);
			entities = currService.insertNews(jsonDBObject);
		}
		/*else if(JsonConstants.DELETE.equals(operation))	{
			BasicDBObject newFilterDBObject = JsonUtil.getIdObjForMongoDB(filterDBObject);
			entities = currService.deleteNews(newFilterDBObject);
		}
		else if(JsonConstants.UPDATE.equals(operation))	{
			BasicDBObject newJsonDBObject = JsonUtil.getSayDoObjForMongoDB(jsonDBObject);
			BasicDBObject newFilterDBObject = JsonUtil.getIdObjForMongoDB(filterDBObject);
			entities = currService.updateNews(newFilterDBObject, newJsonDBObject);
		}*/
		System.out.println(JsonUtil.getJSONObjectForExtjs(entities));
		return JsonUtil.getJSONObjectForExtjs(entities);
	}
}
