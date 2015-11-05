/**
 * 
 */
package com.affairs.dao;

import java.util.List;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.affairs.constants.MongoDBConstants;

/**
 * This is a DAO class, for the collection Entities to process Tower, Platform and Program.
 * Contains list of DAO methods that are needed to perform the CRUD operations on the entity Object
 * 
 * @author Amar
 *
 */
public class EntityDAO {
	private final DBCollection entityCollection;
	
	public EntityDAO(final DBCollection collectionName) {
		entityCollection = collectionName;
    }
	
	/**
	 * @param filterDBObject
	 * @return
	 */
	public List<DBObject> getObjectsAsList(BasicDBObject filterDBObject)	{
		DBObject fields = new BasicDBObject(MongoDBConstants.NONSELECTFIELDS).append("_id", false);
        filterDBObject.append("isCurrent", true);
		DBCursor entityCursor = entityCollection.find(filterDBObject, fields);
		return entityCursor.toArray();
	}
}
