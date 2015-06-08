package com.mongodb.mongoWeek2;

import com.mongodb.MongoClient;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Sorts;
import com.mongodb.client.result.DeleteResult;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jchigurupati on 6/7/15.
 */
public class HW2Part3 {
    public static void main(String[] args) {
        final MongoClient client = new MongoClient(new ServerAddress(
                "localhost", 27017));

        try {
            final MongoDatabase db = client.getDatabase("students");
            final MongoCollection<Document> collection = db
                    .getCollection("grades");

            double studentId = -1.0D, tempStudentId = -1.0D;

            final Bson filter = Filters.eq("type", "homework");

            final Bson sort = Sorts.orderBy(Sorts.ascending("student_id"),
                    Sorts.ascending("score"));

            final List<Document> findResult = (List<Document>) collection
                    .find().filter(filter).sort(sort)
                    .into(new ArrayList<Document>());

            if (!findResult.isEmpty()) {
                System.out.println("count: " + findResult.size());

                for (Document document : findResult) {
                    tempStudentId = document.getDouble("student_id");

                    if (studentId != tempStudentId) {
                        studentId = tempStudentId;

                        final Bson deleteFilter = Filters.and(Filters.eq(
                                "student_id", (tempStudentId)), Filters.eq(
                                "type", "homework"), Filters.eq("score",
                                document.getDouble("score")));

                        final DeleteResult deleteManyResult = collection
                                .deleteOne(deleteFilter);
                        System.out.println(deleteManyResult.getDeletedCount());
                    }
                }
            } else {
                System.err.println("No results...");
            }
        } finally {
            if (client != null) {
                client.close();
            }
        }
    }
}
