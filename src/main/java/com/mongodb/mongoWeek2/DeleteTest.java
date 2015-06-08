package com.mongodb.mongoWeek2;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.ArrayList;

import static com.mongodb.mongoWeek2.Helpers.printJson;
import static com.mongodb.client.model.Filters.*;

/**
 * Created by jchigurupati on 6/7/15.
 */
public class DeleteTest {
    public static void main(String[] args) {
        MongoClient client = new MongoClient();
        MongoDatabase db = client.getDatabase("course");
        MongoCollection<Document> collection = db.getCollection("DeleteTest");

        collection.drop();   //Collections is deleted every time program is run, will not see in Mongo Terminal

        //insert 8 documents with _id set to the value of the loop variable
        for (int i = 0; i < 8; i++) {
            collection.insertOne(new Document().append("_id", i));
        }

        collection.deleteMany(gt("_id", 4));

        collection.deleteOne(eq("_id", 4));

        for (Document cur : collection.find().into(new ArrayList<Document>())) {
            printJson(cur);
        }
    }
}
