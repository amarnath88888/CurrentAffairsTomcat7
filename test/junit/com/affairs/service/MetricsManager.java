package com.affairs.service;

import java.util.List;

import org.json.simple.JSONObject;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.affairs.constants.MongoDBConstants;
import com.affairs.util.JsonUtil;
import com.affairs.util.MongoDB;

public class MetricsManager{

	public MetricsManager() {
	}
	public void getCurrentObjects()	{
		CurrentService currService = new CurrentService(MongoDB.getInstance().getCollection(MongoDBConstants.CURRENT));
		List<DBObject> allNews = currService.getAllNews();
		JSONObject sayDoObjForExtjs = JsonUtil.getJSONObjectForExtjs(allNews);
		//System.out.println(sayDo);
		System.out.println(sayDoObjForExtjs);
	}
	public void getEntityObjects(BasicDBObject filter)	{
		EntityService entityService = new EntityService(MongoDB.getInstance().getCollection(MongoDBConstants.ENTITIES));
		List<DBObject> entities = entityService.getEntities(filter);
		JSONObject entityObjForExtjs = JsonUtil.getJSONObjectForExtjs(entities);
		//System.out.println(entities);
		System.out.println(entityObjForExtjs);
	}
	/*public void getSayDoObjects()	{
		SayDoService sayDoService = new SayDoService(MongoDB.getInstance().getCollection(MongoDBConstants.SAYDO));
		List<DBObject> sayDo = sayDoService.getSaydo();
		JSONObject sayDoObjForExtjs = JsonUtil.getJSONObjectForExtjs(sayDo);
		//System.out.println(sayDo);
		System.out.println(sayDoObjForExtjs);
	}*/
}
