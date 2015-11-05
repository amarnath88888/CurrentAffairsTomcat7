package com.affairs.dao;

import java.util.Date;
import java.util.List;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.WriteResult;
import com.affairs.constants.MongoDBConstants;

/**
 * This is a DAO class, for the collection SayDo to process saydo related activities.
 * Contains list of DAO methods that are needed to perform the CRUD operations on the Saydo Object
 * @author Amar
 *
 */
public class SayDoDAO implements ICurrentAffairs {

	private final DBCollection businessCollection;

	public SayDoDAO(DBCollection dbCollection) {
		businessCollection = dbCollection;
	}

	public List<DBObject> getAllNEWSAsList() {
		DBObject selectAttributes = new BasicDBObject(MongoDBConstants.NONSELECTFIELDS);
		DBCursor businessCursor = businessCollection.find(MongoDBConstants.FILTERCRITERIA, selectAttributes);
		return businessCursor.toArray();
	}
    /* (non-Javadoc)
     * Add a SayDo Project to the Collection
     * @see com.nielsen.metrics.dao.DAOInterface#addProject(java.lang.String, com.mongodb.BasicDBObject)
     */
    public boolean addNews(BasicDBObject insertDBObject) {

        boolean insertSuccess = true;
        BasicDBObject projectObj = new BasicDBObject(insertDBObject);
        projectObj.append("isCurrent", true).append("delInd", false);
        projectObj.append("createdDate", new Date()).append("updatedDate", new Date());
        WriteResult insertResult = null;

        try {
            insertResult = this.businessCollection.insert(projectObj);
            System.out.println("insert Log is " + insertResult.getLastError());
        } catch (Exception exp) {
            System.out.println(exp.getMessage());
            System.out.println("Insert did not happen properly with log " + insertResult.getLastError());
            insertSuccess = false;
        }

        /*if (insertResult.getLastError() != null) {
        	System.out.println("Error is"+insertResult.getLastError());
            System.out.println("Insert did not happen properly");
            insertSuccess = false;
        }*/

        return insertSuccess;
    }
    
    /* (non-Javadoc)
     * Delete an existing saydo Project record
     * i.e to update the delInd to true -- Soft Delete
     * @see com.nielsen.metrics.dao.DAOInterface#deleteProject(java.lang.String, com.mongodb.BasicDBObject)
     */
    public boolean deleteNews(BasicDBObject filterDBObject) {

        boolean deleteSuccess = true;

        BasicDBObject updateConditionDBObject = new BasicDBObject(MongoDBConstants.DELINDICATORTRUE);
        updateConditionDBObject.append("updatedDate", new Date());

        WriteResult deleteResult = null;

        try {
            deleteResult = this.businessCollection.update(filterDBObject, new BasicDBObject("$set", updateConditionDBObject));
            System.out.println("Delete Log is " + deleteResult.getLastError());
        } catch (Exception exp) {
            System.out.println(exp.getMessage());
            System.out.println("Delete did not happen properly with log " + deleteResult.getLastError());
            deleteSuccess = false;
        }

       /* if (updateResult.getLastError() != null) {
            System.out.println("Update did not happen properly");
            deleteSuccess = false;
        }*/

        return deleteSuccess;
    }

    /* (non-Javadoc)
     * Update the existing record to non current and insert a new current record
     * @see com.nielsen.metrics.dao.DAOInterface#updateProject(java.lang.String, com.mongodb.BasicDBObject, com.mongodb.BasicDBObject)
     */
    public boolean updateNews(BasicDBObject filterDBObject, BasicDBObject updateDBObject) {

        boolean updateSuccess = true;

        BasicDBObject updateCurrentObject = new BasicDBObject("isCurrent", false);
        updateCurrentObject.append("updatedDate", new Date());

        WriteResult updateResult = null;

        try {
        	// set the isCurrent to false for previous value and insert new record into DB for updated values
            updateResult = this.businessCollection.update(filterDBObject, new BasicDBObject("$set", updateCurrentObject));
            System.out.println("Update Log is " + updateResult.getLastError());
            if (updateResult.getLastError().get("err") == null) {
            	System.out.println("Update success from log");
            }
            // add a new record for updated values into DB
            updateSuccess =  addNews(updateDBObject);
        } catch (Exception exp) {
            System.out.println(exp.getMessage());
            System.out.println("Update did not happen properly with log " + updateResult.getLastError());
            updateSuccess = false;
        }
        return updateSuccess;
    }
}
