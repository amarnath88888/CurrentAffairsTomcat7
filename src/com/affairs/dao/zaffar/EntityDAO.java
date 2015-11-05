/*
 * Copyright (c) 2008 - 2013 10gen, Inc. <http://10gen.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package com.affairs.dao.zaffar;

import com.mongodb.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class EntityDAO {
    DBCollection entityCollection;

    public EntityDAO(final DB metricsDatabase) {
        entityCollection = metricsDatabase.getCollection("entities");
    }

    // Returns entity by Type
    // Default Sort Order should be tower, Platform, Program and Release - in asc order
    public List<DBObject> findEntitiesByType(ArrayList entityTypes) {

        List<DBObject> entityList = new ArrayList<DBObject>();

        DBObject whereCondition = new BasicDBObject().append("isCurrent", true).append("delInd", false).append("type",new BasicDBObject("$in", entityTypes));
        DBObject sortCondition = new BasicDBObject().append("type", 1).append("mnm", 1);

        DBCursor entityListCursor = this.entityCollection.find(whereCondition).sort(sortCondition);
        DBObject entityCursor = new BasicDBObject();

        try {
            while (entityListCursor.hasNext()) {
                entityCursor = entityListCursor.next();
                entityList.add(entityCursor);
            }
        } catch (Exception exp) {
            System.out.println(exp.getMessage());
        } finally {
            entityListCursor.close();
        }
        return entityList;
    }


    // Find a SDLC Release Object passing an ObjectId

    public DBObject findByObjectId(String ObjectId) {

        DBObject entityObj = null;
        DBCursor entityCursor = null;
        try {
            entityCursor = this.entityCollection.find(new BasicDBObject("_id", ObjectId));

            while (entityCursor.hasNext()) {
                entityObj = entityCursor.next();
            }
        } catch (Exception exp) {
            System.out.println(exp.getMessage());
        } finally {
            entityCursor.close();
        }
        return entityObj;
    }

    // Find One Entity Object passing an whereConditionObj

    public DBObject findOneObjectByCondition(BasicDBObject whereConditionObj) {

        DBObject entityObj = null;
        try {
            entityObj = this.entityCollection.findOne(whereConditionObj);
        } catch (Exception exp) {
            System.out.println(exp.getMessage());
        }
        return entityObj;
    }

    // Find All entities Object passing an whereConditionObj

    public List findAllObjectByCondition(BasicDBObject whereConditionObj) {

        List<DBObject> entityList = new ArrayList<DBObject>();

        DBCursor entityListCursor = this.entityCollection.find(whereConditionObj);
        DBObject entityCursor = new BasicDBObject();

        try {
            while (entityListCursor.hasNext()) {
                entityCursor = entityListCursor.next();
                entityList.add(entityCursor);
            }
        } catch (Exception exp) {
            System.out.println(exp.getMessage());
        } finally {
            entityListCursor.close();
        }
        return entityList;

    }

    // Add an entity
    public boolean addEntity(String username, Map entityMap) {

        boolean insertSuccess = true;
        BasicDBObject entityObj = new BasicDBObject(entityMap);
        entityObj.append("isCurrent", true).append("delInd", false);
        entityObj.append("createdBy", username).append("updatedBy", username);
        entityObj.append("createdDate", new Date()).append("updatedDate", new Date());

        WriteResult insertResult = null;

        try {
            insertResult = this.entityCollection.insert(entityObj);
        } catch (Exception exp) {
            System.out.println(exp.getMessage());
        }

        if (insertResult.getLastError() != null) {
            System.out.println("Insert did not happen properly");
            insertSuccess = false;
        }

        return insertSuccess;
    }

    // Update or delete an existing entity record
    // The same method can be used to remove the records i.e to update the delInd to true -- Soft Delete
    public boolean updateEntity(String username, Map whereConditionMap, Map updateConditionMap) {

        boolean updateSuccess = true;
        BasicDBObject whereConditionObj = new BasicDBObject(whereConditionMap);
        BasicDBObject updateConditionObj = new BasicDBObject(updateConditionMap);

        updateConditionObj.append("updatedBy", username);
        updateConditionObj.append("updatedDate", new Date());

        WriteResult updateResult = null;

        try {
            updateResult = this.entityCollection.update(whereConditionObj, new BasicDBObject("$set", updateConditionObj));
        } catch (Exception exp) {
            System.out.println(exp.getMessage());
        }

        if (updateResult.getLastError() != null) {
            System.out.println("Update did not happen properly");
            updateSuccess = false;
        }

        return updateSuccess;
    }

}
