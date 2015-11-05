package com.affairs.dao;

import java.util.List;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

/**
 * This interface declares the list of methods to be implemented by any DAO class 
 * @author Amar
 * 
 */
public interface ICurrentAffairs {

	/**
	 * @return The list of records to the business handler
	 */
	public List<DBObject> getAllNEWSAsList();

    /**
     * Add a Project to the Collection
     * @param insertDBObject
     * @return true if the insert happened successfully, else false
     */
    public boolean addNews(BasicDBObject insertDBObject);

	/**
	 * Delete an existing Project record
	 * i.e to update the delInd to true -- Soft Delete
	 * @param filterDBObject
	 * @return true if the delete happened successfully, else false
	 */
	public boolean deleteNews(BasicDBObject filterDBObject);

	/**
	 * Update the existing record to non current and insert a new current record
	 * @param filterDBObject
	 * @param updateDBObject
	 * @return true if the update happened successfully, else false
	 */
	public boolean updateNews(BasicDBObject filterDBObject, BasicDBObject updateDBObject);

}
