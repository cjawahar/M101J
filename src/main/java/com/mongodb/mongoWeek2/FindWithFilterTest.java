package com.mongodb.mongoWeek2;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Projections;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static com.mongodb.mongoWeek2.Helpers.printJson;
import static com.mongodb.client.model.Filters.*;
import static com.mongodb.client.model.Projections.*;
/**
 * Created by jchigurupati on 6/7/15.
 */
public class FindWithFilterTest {
    public static void main(String[] args) {
        MongoClient client = new MongoClient();
        MongoDatabase db = client.getDatabase("course");
        MongoCollection<Document> collection = db.getCollection("findWithFilterTest");

        collection.drop();   //Collections is deleted every time program is run, will not see in Mongo Terminal

        //insert 10 documents
        for (int i = 0; i < 10; i++) {
            collection.insertOne(new Document()
                                    .append("x", new Random().nextInt(2))
                                    .append("y", new Random().nextInt(100))
                                    .append("i", i));
        }

        //Query filters with raw documents
//        Bson filter = new Document("x", 0)
  //                          .append("y", new Document("$gt", 10).append("$lt", 90));

        //Query filters with equality builders.
        Bson filter = and(eq("x", 0), gt("y", 10), lt("y", 90));

        //Filtering with a projection to eliminate x field since we know it's always 0.

        //Bson projection = new Document("x", 0).append("_id", 0);  //_id field now also excluded.

        //Bson projection = new Document("y", 1).append("i", 1);  //y and i are now the only INCLUDED fields.

        //Bson projection = Projections.exclude("x", "_id"); //Alternate method to exclude with projections

        //Bson projection = Projections.include("y", "i");  //Including with Projections

        //This projection uses fields to hold both include/exclude commands
        Bson projection = fields(include("y", "i"), excludeId());

        //You can statically import Projections to remove the Projections. prefix in all examples.

        List<Document> all = collection.find(filter)
                                        .projection(projection)   //Applies projection restriction
                                        .into(new ArrayList<Document>());

        for (Document cur : all) {
            printJson(cur);
        }

        long count = collection.count(filter);
        System.out.println();
        System.out.println(count);



    }
}
