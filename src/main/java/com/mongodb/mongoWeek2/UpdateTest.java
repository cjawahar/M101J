package com.mongodb.mongoWeek2;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import org.bson.Document;

import static com.mongodb.mongoWeek2.Helpers.printJson;
import static com.mongodb.client.model.Filters.*;


import java.util.ArrayList;

/**
 * Created by jchigurupati on 6/7/15.
 */
public class UpdateTest {
    public static void main(String[] args) {
        MongoClient client = new MongoClient();
        MongoDatabase db = client.getDatabase("course");
        MongoCollection<Document> collection = db.getCollection("findWithFilterTest");

        collection.drop();   //Collections is deleted every time program is run, will not see in Mongo Terminal

        //insert 8 documents with both x and y set to the value of the loop variable
        for (int i = 0; i < 8; i++) {
            collection.insertOne(new Document().append("_id", i)
                                                .append("x", i));
        }

//        collection.replaceOne(eq("x", 5), new Document("_id", 5).append("x", 20).append("update", true));

//        collection.updateOne(eq("_id", 9), new Document("$set", new Document("x", 20)), //Update
//                                        new UpdateOptions().upsert(true));      //If doc doesn't exist, creates new one.

        collection.updateMany(gte("_id", 5), new Document("$inc", new Document("x", 1))); //id's 5,6,7 are incremented by 1

        for (Document cur : collection.find().into(new ArrayList<Document>())) {
            printJson(cur);
        }
    }
}
