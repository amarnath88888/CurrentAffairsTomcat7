/**
 * @author Amar
 */
package com.affairs.service;

import java.util.List;

import com.affairs.dao.CurrentAffairsDAO;
import com.affairs.dao.ICurrentAffairs;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;

/**
 * This is a service class to perform each of the service operations like insert, delete, update and find records from collections
 * It provides the services by invoking the DAO medhods
 * @author Amar
 *
 */
public class CurrentService {

	private ICurrentAffairs daoInt;

	public CurrentService(final DBCollection dbCollection) {
		daoInt = new CurrentAffairsDAO(dbCollection);
	}
	/**
	 * @param filterDBObject
	 * @return The list of records to the business handler
	 */
	public List<DBObject> getAllNews() {
		return daoInt.getAllNEWSAsList();
	}
	/**
	 * @param filter
	 * @return The list of latest records to the business handler after inserting the current record
	 */
	public List<DBObject> insertNews(BasicDBObject filter) {
		if(daoInt.addNews(filter))	{
			System.out.println("Inserted successfully");
		}
		return getAllNews();
	}
	/**
	 * @param filterDBObject
	 * @return The list of latest records to the business handler after deleting the current record
	 */
	public List<DBObject> deleteNews(BasicDBObject filterDBObject) {
		if(daoInt.deleteNews(filterDBObject))	{
			System.out.println("Deleted successfully");
		}
		return getAllNews();
	}
	/**
	 * @param filterDBObject
	 * @param jsonDBObject
	 * @return The list of latest records to the business handler after updating the current record
	 */
	public List<DBObject> updateNews(BasicDBObject filterDBObject, BasicDBObject jsonDBObject) {
		if(daoInt.updateNews(filterDBObject, jsonDBObject))	{
			System.out.println("Updated successfully");
		}
		return getAllNews();
	}
}
