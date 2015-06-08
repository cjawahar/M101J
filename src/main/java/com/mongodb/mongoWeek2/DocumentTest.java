package com.mongodb.mongoWeek2;

import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.Arrays;
import java.util.Date;

import static com.mongodb.mongoWeek2.Helpers.printJson;

/**
 * Created by jchigurupati on 6/6/15.
 */
public class DocumentTest {
    public static void main(String[] args) {
        Document document = new Document()
                                .append("str", "MongoDB, Hello")
                                .append("int", 42)
                                .append("l", 1l)
                                .append("double", 1.1)
                                .append("b", false)
                                .append("date", new Date())
                                .append("objectId", new ObjectId())
                                .append("null", null)
                                .append("embeddedDoc", new Document("x",0))
                                .append("list", Arrays.asList(1, 2, 3));

        printJson(document);
    }
}
