package singhr2.examples.mongo.simpleClient;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * Ref: https://github.com/mongolab/mongodb-driver-examples/blob/master/java/JavaSimpleExample.java
 *
 */
public class BulkInsert {
    public static void main(String[] args) {
        // Standard URI format: mongodb://[dbuser:dbpassword@]host:port/dbname

        MongoClientURI uri  = new MongoClientURI("mongodb://localhost:27017/test");
        MongoClient client = new MongoClient(uri);
        MongoDatabase db = client.getDatabase(uri.getDatabase());
        System.out.println("Database name :" + db.getName());

        System.out.println("Total documents in DummyCollection : " + db.getCollection("DummyCollection").count());


        MongoCollection<Document> songs = db.getCollection("songs");

        //MongoCollection<Document> songs = insertBulkRecords(db);

        //Bulk insert
        //TODO : UNCOMMENT WHEN NEEDED TO INSERT RECORDS
        insertBulkRecords(db);

        System.out.println("Printing documents ...");
        songs.find().forEach((Consumer<? super Document>) System.out::println);


        /*
         * Finally we run a query which returns all the hits that spent
         * more than 10 weeks at number 1.
         */
        System.out.println("\nPrinting hits that spent more than 10 weeks at number 1. ...");

        Document findQuery = new Document("weeksAtOne", new Document("$gt",10)); //$gte :  >=
        Document orderBy = new Document("decade", 1);
        songs.find(findQuery).sort(orderBy).forEach((Consumer<? super Document>) System.out::println);

        // Since this is an example, we'll clean up after ourselves.
        //TODO : UNCOMMENT IF WANT TO DELETE COLLECTION
        //songs.drop();

        // Only close the connection when your app is terminating
        client.close();
    }

    private static /*MongoCollection<Document>*/ void insertBulkRecords(MongoDatabase db) {
        MongoCollection<Document> songs = db.getCollection("songs");

        List<Document> seedData = new ArrayList<Document>();

        seedData.add(new Document("decade", "1970s")
                .append("artist", "Debby Boone")
                .append("song", "You Light Up My Life")
                .append("weeksAtOne", 10)
        );

        seedData.add(new Document("decade", "1980s")
                .append("artist", "Olivia Newton-John")
                .append("song", "Physical")
                .append("weeksAtOne", 10)
        );

        seedData.add(new Document("decade", "1990s")
                .append("artist", "Mariah Carey")
                .append("song", "One Sweet Day")
                .append("weeksAtOne", 16)
        );

        songs.insertMany(seedData);
        //return songs;
    }
}
