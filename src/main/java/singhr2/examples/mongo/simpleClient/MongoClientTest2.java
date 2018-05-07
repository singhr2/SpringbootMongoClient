/**
 * Ref:
 *  Using MONGODB JAVA DRIVER 3.6
 *  http://mongodb.github.io/mongo-java-driver/3.6/driver/getting-started/quick-start/
 *
 *
 * MongoDB has a special interface called as com.mongodb.DBObject and
 * its implementation class in com.mongodb.BasicDBObject that can be use to create or represent a document in MongoDB database.
 * The DBObject is actually a map like structure with a key-value pairs.
 * If you look up to the class hierarchy you can actually see that a BasicDBObject is inherited from the
 * java.util.LinkedHashMap class.
 */

package singhr2.examples.mongo.simpleClient;

import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoIterable;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import org.bson.BsonDocument;
import org.bson.Document;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.UUID;
import java.util.function.Consumer;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Updates.combine;
import static com.mongodb.client.model.Updates.currentDate;
import static com.mongodb.client.model.Updates.set;

public class MongoClientTest2 {
    private static final String DB_HOST = "localhost";
    private static final int DB_PORT = 27017;
    private static final String DB_NAME = "test";
    private static final String COLLECTION_NAME = "DummyCollection";

    public static void main(String[] args) {

        //Handling authentication
        /*
        MongoCredential credential = MongoCredential.createCredential("administrator", "admin", "mypassword".toCharArray());
        MongoClient mongoClient1 = new MongoClient(new ServerAddress("localhost"), Arrays.asList(credential));
        */

        MongoClient mongoClient = new MongoClient(DB_HOST , DB_PORT );
        MongoDatabase testDb = mongoClient.getDatabase(DB_NAME);

        //Create Collection
        //DBCollection dummyCol = testDb.createCollection(COLLECTION_NAME, null);

        //Drop Collection
        //testDb.getCollection(COLLECTION_NAME).drop();

        System.out.println("Listing collection names...");
        MongoIterable<String> collNames = testDb.listCollectionNames();
        collNames.forEach((Consumer<? super String>) System.out::println);
        System.out.println("~~~~~~~~~~~~");

        //Get collection
        MongoCollection<Document> dummyCol = testDb.getCollection(COLLECTION_NAME);

        //Insert
        Document doc1 = new Document();
        doc1.put("uuid", UUID.randomUUID().toString());
        doc1.put("insert-time", LocalDateTime.now().toString());
        dummyCol.insertOne(doc1);
        System.out.println("~~~~~~~~~~~~");

        //SELECT ALL
        /*
        //avoid its use as the application can leak a cursor if the loop terminates early:

        MongoCursor<Document> cursor = dummyCol.find().iterator();
        try {
            while (cursor.hasNext()) {
                System.out.println(cursor.next().toJson());
            }
        } finally {
            cursor.close();
        }
        */

        System.out.println("\n -----------------------");
        System.out.println("Fetching all documents...");

        //sort by insert-time desc(-1)
        for (Document cur : dummyCol.find().sort(new BasicDBObject("insert-time",-1))) {
            System.out.println(cur.toJson());
        }
        System.out.println("~~~~~~~~~~~~");

        //SELECT .. where ; fetch all
        System.out.println("\n -----------------------");
        System.out.println("Fetching ALL documents having Field Account value as 999");
        FindIterable<Document> fi = dummyCol.find(eq("Account", "999"));
        //System.out.println(myDoc.toJson());
        for(Document doc: fi){
            System.out.println(doc.toJson());
        }

        //SELECT .. where ; only 1
        System.out.println("\n -----------------------");
        System.out.println("Fetching 1st document having Field Account value as 999");
        Document myDoc = dummyCol.find(eq("Account", "999")).first(); // getting 1st only
        System.out.println(myDoc.toJson());

        //no difference in formating of output
        //BsonDocument bsonDocument = myDoc.toBsonDocument(BsonDocument.class, MongoClient.getDefaultCodecRegistry());
        //System.out.println(bsonDocument);

        System.out.println("~~~~~~~~~~~~");


        //UPDATE - collection’s updateOne and updateMany methods.
        //update one

        dummyCol.updateOne(eq("Account", "999"), new Document("$set", new Document("Project", "Multiple projects-3")));

        //not working{$exists:true}
        //dummyCol.updateMany(eq("acct1", "{exists:false}"), new Document("$set", new Document("Last_updated_by", "Ranbir")));

        //Update all documents having Account = acctX
        //If stars and lastModified fields are not present, they will be added
        UpdateResult updateResult = dummyCol.updateMany( eq("Account", "acctX"),
                combine(set("stars", 0), currentDate("lastModified")));
        System.out.println("!!!! updateMany > updateResult:" + updateResult.getModifiedCount());

        //DELETE
        BasicDBObject searchQuery = new BasicDBObject();
        searchQuery.put("Project", "misc");
        DeleteResult deleteResult = dummyCol.deleteOne(searchQuery);
        System.out.println("deleteResult :" + deleteResult);

        mongoClient.close();
    }
}
