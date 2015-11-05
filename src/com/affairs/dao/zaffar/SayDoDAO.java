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

public class SayDoDAO {
    // For now we can configure the limit to 500
    static final int SAYDO_UI_DISPLAY_LIMIT = 500;
    DBCollection saydoCollection;

    public SayDoDAO(final DB metricsDatabase) {
        saydoCollection = metricsDatabase.getCollection("saydo");
    }

    // Returns all the Projects in SayDo Collections
    // Default Sort Order should be tower, Platform, Program and project - in asc order
    public List<DBObject> findAllSayDoProjects() {

        //List<DBObject> sayDoList = new ArrayList<DBObject>();

        DBObject whereCondition = new BasicDBObject().append("isCurrent", true).append("delInd", false);
        DBObject sortCondition = new BasicDBObject().append("tower", 1).append("platform", 1).append("program", 1).append("Project", 1);

        DBCursor sayDoListCursor = this.saydoCollection.find(whereCondition).sort(sortCondition).limit(SAYDO_UI_DISPLAY_LIMIT);
        
        /*DBObject sayDoCursor = new BasicDBObject();

        try {
            while (sayDoListCursor.hasNext()) {
                sayDoCursor = sayDoListCursor.next();
                sayDoList.add(sayDoCursor);
            }
        } catch (Exception exp) {
            System.out.println(exp.getMessage());
        } finally {
            sayDoListCursor.close();
        }*/
        return sayDoListCursor.toArray();
    }


    // Find a Saydo Project Object passing an ObjectId

    public DBObject findByObjectId(String ObjectId) {

        DBObject sayDoProjectObj = null;
        DBCursor sayDoProjectCursor = null;
        try {
            sayDoProjectCursor = this.saydoCollection.find(new BasicDBObject("_id", ObjectId));

            while (sayDoProjectCursor.hasNext()) {
                sayDoProjectObj = sayDoProjectCursor.next();
            }
        } catch (Exception exp) {
            System.out.println(exp.getMessage());
        } finally {
            sayDoProjectCursor.close();
        }
        return sayDoProjectObj;
    }

    // Find One SayDo Project Object passing an whereConditionObj

    public DBObject findOneObjectByCondition(BasicDBObject whereConditionObj) {

        DBObject sayDoProjectObj = null;
        try {
            sayDoProjectObj = this.saydoCollection.findOne(whereConditionObj);
        } catch (Exception exp) {
            System.out.println(exp.getMessage());
        }
        return sayDoProjectObj;
    }

    // Find All SayDo Project Object passing an whereConditionObj

    public List findAllObjectByCondition(BasicDBObject whereConditionObj) {

        List<DBObject> projectList = new ArrayList<DBObject>();

        DBCursor projectListCursor = this.saydoCollection.find(whereConditionObj).limit(SAYDO_UI_DISPLAY_LIMIT);
        DBObject projectCursor = new BasicDBObject();

        try {
            while (projectListCursor.hasNext()) {
                projectCursor = projectListCursor.next();
                projectList.add(projectCursor);
            }
        } catch (Exception exp) {
            System.out.println(exp.getMessage());
        } finally {
            projectListCursor.close();
        }
        return projectList;

    }

    // Add a SayDo Project
    public boolean addSayDoProject(String username, Map sayDoProjectMap) {

        boolean insertSuccess = true;
        BasicDBObject projectObj = new BasicDBObject(sayDoProjectMap);
        projectObj.append("isCurrent", true).append("delInd", false);
        projectObj.append("createdBy", username).append("updatedBy", username);
        projectObj.append("createdDate", new Date()).append("updatedDate", new Date());

        WriteResult insertResult = null;

        try {
            insertResult = this.saydoCollection.insert(projectObj);
        } catch (Exception exp) {
            System.out.println(exp.getMessage());
        }

        if (insertResult.getLastError() != null) {
            System.out.println("Insert did not happen properly");
            insertSuccess = false;
        }

        return insertSuccess;
    }

    // Update or delete an existing SayDo Project record
    // The same method can be used to remove the records i.e to update the delInd to true -- Soft Delete
    public boolean updateSayDoProject(String username, Map whereConditionMap, Map updateConditionMap) {

        boolean updateSuccess = true;

        BasicDBObject whereConditionObj = new BasicDBObject(whereConditionMap);
        BasicDBObject updateConditionObj = new BasicDBObject(updateConditionMap);

        updateConditionObj.append("updatedBy", username);
        updateConditionObj.append("updatedDate", new Date());

        WriteResult updateResult = null;

        try {
            updateResult = this.saydoCollection.update(whereConditionObj, new BasicDBObject("$set", updateConditionObj));
        } catch (Exception exp) {
            System.out.println(exp.getMessage());
        }

        if (updateResult.getLastError() != null) {
            System.out.println("Update did not happen properly");
            updateSuccess = false;
        }

        return updateSuccess;
    }

    // Update the existing record to non current and insert a new current record
    public boolean upsertSayDoProject(String username, Map whereConditionMap, Map updateConditionMap) {

        boolean upsertSuccess = true;
        BasicDBObject whereConditionObj = new BasicDBObject(whereConditionMap);
        BasicDBObject updateConditionObj = new BasicDBObject(updateConditionMap);

        BasicDBObject updateCurrentObj = new BasicDBObject("isCurrent", false);
        updateCurrentObj.append("updatedBy", username);
        updateCurrentObj.append("updatedDate", new Date());

        BasicDBObject currentProjectObj = (BasicDBObject) findOneObjectByCondition(whereConditionObj);

        WriteResult updateResult = null;

        try {
            updateResult = this.saydoCollection.update(whereConditionObj, new BasicDBObject("$set", updateCurrentObj));
        } catch (Exception exp) {
            System.out.println(exp.getMessage());
        }

        if (updateResult.getLastError() != null) {
            System.out.println("Update did not happen properly");
            upsertSuccess = false;
        } else {
            currentProjectObj.putAll(updateConditionMap);
            upsertSuccess =  addSayDoProject(username, currentProjectObj);
        }

        return upsertSuccess;
    }

}
