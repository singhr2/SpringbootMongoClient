MongoDB is an Open Source  document-oriented database written in C++.


Created project using Spring Initializer with following info:
https://start.spring.io
Group: singhr2.examples.mongo.client
Artifact : SpringbootMongoClient
Dependencies: Web, MongoDB
========================================
Mongo tools:
    mongod: mongoDB server

    mongod --bind_ip  yourIPadress  --logpath  "C:\data\dbConf\mongodb.log"  --logappend  --dbpath  "C:\data\db"  --port yourPortNumber --serviceName "YourServiceName" --serviceDisplayName "YourServiceName" --install

    mongo: mongoDB client/shell
     When you connect MongoDB immediately after installation, it connects to the test document (database).

    mongostat: MongoDB monitoring tools
    mongotop: MongoDB monitoring tools
    mongos: MongoDB Router
========================================
Let’s see the analogies between Mongo and a traditional MySQL system:
    Table in MySQL becomes a Collection in Mongo
    Row becomes a Document
    Column becomes a Field
    Joins are defined as linking and embedded documents
========================================
There are two approaches through which we can connect to MongoDB database
	– MongoRepository and MongoTemplate.

http://www.mkyong.com/mongodb/how-to-install-mongodb-on-windows/
========================================

In MongoDB, documents stored in a collection must have a unique _id field that acts as a primary key.
Collections are analogous to tables in relational databases.
Unlike a table, however, a collection does not require its documents to have the same schema.

MongoDB documents are similar to JSON objects.



#start server
D:\MongoDB3.6\bin>mongod.exe

#start client
D:\MongoDB3.6\bin>mongo.exe
	MongoDB shell version v3.6.4
	connecting to: mongodb://127.0.0.1:27017
	MongoDB server version: 3.6.4

	switch to the test database:
	$ use test

	Check current db:
	$ db

    List all databases.
	$ show dbs

    Switches to db_name.
    If there is no existing database, the following command is used to create a new database.
	$ use db_name

    to access another database without switching databases - db.getSiblingDB()
    $ db.getSiblingDB('admin').getCollectionNames();

    Drop database
    $ db.dropDatabase()

    List all tables in the current selected database
	$ show collections


CREATE collection
    Implicitly created on first insert() operation. The primary key _id is automatically added if _id field is not specified.

    explicitly create a collection:
    $ db.createCollection("customers", null);

DROP collection
    $ db.customers.drop()

SHOW Collections
    $ show collections

INSERT
	$ db.tablename.insert({data})
	or
	$ db.tablename.save({data})

	e.g.,
	$ db.users.save({username:"google",password:"google123"})
	  db.users.save({username:"Ranbir",password:"test123"})

	$ db.users.find()
		{ "_id" : ObjectId("5add95feedbcd3e9565d227c"), "username" : "google", "password" : "google123" }
		{ "_id" : ObjectId("5add9660edbcd3e9565d227d"), "username" : "Ranbir", "password" : "test123" }

	$ db.users.find({username:"Ranbir"}) #case-sensitive
	$ db.users.find({$where:"this.username.length<=2"})
	$ db.users.find({username:{$exists : true}})

SELECT
    Return all documents
    $ db.COLLECTION_NAME.find()

    formatted result - .pretty()
    $ db.COLLECTION_NAME.find().pretty()

    Return 1 document
    $ db.COLLECTION_NAME.findOne()

    Return n documents
    $ db.COLLECTION_NAME.find().limit(NUMBER)

    condition
    $ db.COLLECTION_NAME.find( { item: "canvas" } )
    $ db.DummyCollection.find({"Project" : "Payment Initiation"},{Account:1});

    This will return all documents with a key called "Project", and a non-null value.
    $ db.DummyCollection.find({"Project":{$ne:null}})

    This will return all documents with a key called "Project", but they may still have a null value.
    $ db.DummyCollection.find({"Project":{$exists:true}})

    select where Project is NULL
    $ db.DummyCollection.find({"Project":{$eq:null}})


SORT
     1 is used for ascending order sorting.
    -1 is used for descending order sorting.

    $ db.COLLECTION_NAME.find().sort({KEY:1})

UPDATE
    $ db.COLLECTION_NAME.update(SELECTIOIN_CRITERIA, UPDATED_DATA)
    e.g.,
    $ db.dummyColl.update({'course':'java'},{$set:{'course':'android'}})


DELETE
    Syntax:
    db.collection_name.remove (DELETION_CRITERIA)

	$ db.users.remove({username:"google"})
	$ db.users.remove() : To delete all records from a table
	$ db.users.drop() : To drop the table
    $ db.things.remove({_id: ObjectId("4f6f244f6f35438788aa138f")});

DELETE ALL matching documents
    db.dummyColl.remove( { type : "programming language" } )

DELETE single  matching document
    db.dummyColl.remove( { type : "programming language" }, 1 )



DELETE ALL
    db.dummyColl.remove({})

INDEX:
	$ db.users.getIndexes()

	To create an index, uses db.tablename.ensureIndex(column)
	$ db.user.ensureIndex("password")