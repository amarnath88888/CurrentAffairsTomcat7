package com.affairs.service;

import java.util.List;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.affairs.dao.EntityDAO;

/**
 * This class is used to create object for EntityDAO by passing the collection name.
 * Contains list of methods to invoke DAO methods for CRUD operations
 * 
 * @author Amar
 *
 */
public class EntityService {

	private EntityDAO entityDAO;
	
	public EntityService(final DBCollection collectionName) {
		entityDAO = new EntityDAO(collectionName);
	}
	public List<DBObject> getEntities(BasicDBObject filter) {
		return entityDAO.getObjectsAsList(filter);
	}
}
