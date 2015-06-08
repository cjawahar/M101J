package com.mongodb.mongoWeek1;

import freemarker.template.Configuration;
import freemarker.template.Template;
import spark.Request;
import spark.Route;
import spark.Spark;

import java.io.StringWriter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;


/**
 * Created by jchigurupati on 5/27/15.
 */
public class SparkFormHandling {
    public static void main(String[] args) {
        //Configure Freemarker
        final Configuration configuration = new Configuration();
        configuration.setClassForTemplateLoading(SparkFormHandling.class, "/");

        //Configure routes
        Spark.get(new Route("/") {
            @Override
            public Object handle(final Request request, final spark.Response response) {
                try {

                    Map<String, Object> fruitsMap = new HashMap<String, Object>();
                    fruitsMap.put("fruits",
                            Arrays.asList("apples", "oranges", "bananas", "peaches"));

                    Template fruitPickerTemplate =
                            configuration.getTemplate("fruitPicker.ftl");
                    StringWriter writer = new StringWriter();
                    fruitPickerTemplate.process(fruitsMap, writer);
                    return writer;
                } catch (Exception e) {
                    halt(500);
                    return null;
                }
            }
        });

        Spark.post(new Route("/favorite_fruit") {
            @Override
            public Object handle(final Request request, final spark.Response response) {
                final String fruit = request.queryParams("fruit");
                if (fruit == null) {
                    return "Why don't you pick one?";
                }
                else {
                    return "Your favorite fruit is " + fruit;
                }
            }
        });
    }
}
