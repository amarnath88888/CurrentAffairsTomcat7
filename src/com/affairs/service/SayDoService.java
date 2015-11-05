/**
 * @author Amar
 */
package com.affairs.service;

import java.util.List;

import com.affairs.dao.ICurrentAffairs;
import com.affairs.dao.SayDoDAO;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;

/**
 * This is a service class to perform each of the service operations like insert, delete, update and find records from collections
 * It provides the services by invoking the DAO medhods
 * @author Amar
 *
 */
/*public class SayDoService {

	private ICurrentAffairs daoInt;

	public SayDoService(final DBCollection dbCollection) {
		daoInt = new SayDoDAO(dbCollection);
	}
	*//**
	 * @param filterDBObject
	 * @return The list of records to the business handler
	 *//*
	public List<DBObject> getSaydo() {
		return daoInt.getAllNEWSAsList();
	}
	*//**
	 * @param filter
	 * @return The list of latest records to the business handler after inserting the current record
	 *//*
	public List<DBObject> insertSaydo(BasicDBObject filter) {
		if(daoInt.addNews(filter))	{
			System.out.println("Inserted successfully");
		}
		return getSaydo();
	}
	*//**
	 * @param filterDBObject
	 * @return The list of latest records to the business handler after deleting the current record
	 *//*
	public List<DBObject> deleteSaydo(BasicDBObject filterDBObject) {
		if(daoInt.deleteNews(filterDBObject))	{
			System.out.println("Deleted successfully");
		}
		return getSaydo();
	}
	*//**
	 * @param filterDBObject
	 * @param jsonDBObject
	 * @return The list of latest records to the business handler after updating the current record
	 *//*
	public List<DBObject> updateSaydo(BasicDBObject filterDBObject, BasicDBObject jsonDBObject) {
		if(daoInt.updateNews(filterDBObject, jsonDBObject))	{
			System.out.println("Updated successfully");
		}
		return getSaydo();
	}
}*/
