package singhr2.examples.mongo.simpleClient;

import com.mongodb.*;

import java.util.List;

public class MongoClientTest {
    public static void main(String[] args) {
        MongoClient mongoClient = new MongoClient("localhost" , 27017 );

        mongoClient.getDatabaseNames().forEach(System.out::println);

        DB testDb = mongoClient.getDB("test");
        testDb.getCollectionNames().forEach(System.out::println);

        //boolean auth = testDb.authenticate("username", "pwd".toCharArray());

        DBCollection collection = testDb.getCollection("user");

        //find all
        DBCursor cursor = collection.find();
        while(cursor.hasNext()){
            System.out.println(cursor.next());
        }


        //INSERT
        BasicDBObject newDocument = new BasicDBObject();
        newDocument.put("username", "dummy1");
        newDocument.put("password", "pwd1");
        collection.insert( newDocument );

        mongoClient.close();

    }
}
