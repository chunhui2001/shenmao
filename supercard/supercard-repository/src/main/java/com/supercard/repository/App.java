package com.supercard.repository;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.supercard.tour.Address;
import com.supercard.tour.Person;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;

import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {

//        MongoClientURI connectionString = new MongoClientURI("mongodb://localhost:27017");
//        MongoClient mongoClient = new MongoClient(connectionString);
//
//        MongoDatabase database = mongoClient.getDatabase("mydb");
//
//        MongoCollection<Document> collection = database.getCollection("test");
//
//        Document doc = new Document("name", "MongoDB")
//                .append("type", "database")
//                .append("count", 1)
//                .append("versions", Arrays.asList("v3.2", "v3.0", "v2.6"))
//                .append("info", new Document("x", 203).append("y", 102));
//
//
//        collection.insertOne(doc);

        CodecRegistry pojoCodecRegistry = fromRegistries(MongoClient.getDefaultCodecRegistry(),
                fromProviders(PojoCodecProvider.builder().automatic(true).build()));

        MongoClient mongoClient = new MongoClient("localhost",
                MongoClientOptions.builder().codecRegistry(pojoCodecRegistry).build());

        MongoDatabase database = mongoClient.getDatabase("mydb");
        MongoCollection<Person> collection = database.getCollection("people", Person.class);
        Person ada = new Person("Ada Byron", 20, new Address("St James Square", "London", "W1"));

        collection.insertOne(ada);

        System.out.println( "Hello World!" );
    }
}
