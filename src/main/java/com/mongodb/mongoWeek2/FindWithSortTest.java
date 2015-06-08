package com.mongodb.mongoWeek2;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bson.conversions.Bson;

import static com.mongodb.mongoWeek2.Helpers.printJson;
import static com.mongodb.client.model.Projections.*;
import static com.mongodb.client.model.Sorts.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jchigurupati on 6/7/15.
 */
public class FindWithSortTest {
    public static void main(String[] args) {
        MongoClient client = new MongoClient();
        MongoDatabase db = client.getDatabase("course");
        MongoCollection<Document> collection = db.getCollection("findWithSortTest");

        collection.drop();   //Collections is deleted every time program is run, will not see in Mongo Terminal

        //insert 10 documents
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                collection.insertOne(new Document().append("i", i).append("j", j));
            }
        }

        Bson projection = fields(include("i", "j"), excludeId());

        //Bson sort = new Document("i", 1).append("j", -1);  //Sort i and j via documents. 1 ascending, -1 descending.

        //Statically import Sorts.
        Bson sort = orderBy(ascending("i"),descending("j")); //Combine up/down sorting.

        List<Document> all = collection.find()
                                        .projection(projection)
                                        .sort(sort)
                                        .limit(50)  //limit to first 50 results
                                        .skip(20)   //skip the first 20 results
                                        .into(new ArrayList<Document>());

        for (Document cur: all) {
            printJson(cur);
        }
    }
}
