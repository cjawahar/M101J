package com.mongodb.mongoWeek2;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import static java.util.Arrays.asList;

/**
 * Created by jchigurupati on 6/6/15.
 */
public class InsertTest {
    public static void main(String[] args) {
        MongoClient client = new MongoClient();
        MongoDatabase db = client.getDatabase("course");
        MongoCollection<Document> coll = db.getCollection("insertTest");

        coll.drop();

        Document smith = new Document("name", "Smith")
                                .append("age", 30)
                                .append("profession", "programmer");

        Document jones = new Document("name", "Jones")
                                .append("age", 25)
                                .append("profession", "hacker");

        coll.insertMany(asList(smith, jones));
    }
}
