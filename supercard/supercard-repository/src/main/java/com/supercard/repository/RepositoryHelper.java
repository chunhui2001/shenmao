package com.supercard.repository;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.supercard.tour.BillEntity;
import com.supercard.tour.BillItemEntity;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;

import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

public class RepositoryHelper {

    private static final String _mongoServerHost = "localhost";
    private static final String _dbname = "supercard_db";
    private static final String _usename = "root";
    private static final String _passwd = "Cc";

    public static MongoCollection<BillEntity>  getCollection(String docname, Class<BillEntity> tClass) {

        CodecRegistry pojoCodecRegistry = fromRegistries(MongoClient.getDefaultCodecRegistry(),
                fromProviders(PojoCodecProvider.builder().automatic(true).build()));

        MongoClient mongoClient = new MongoClient(_mongoServerHost,
                MongoClientOptions.builder().codecRegistry(pojoCodecRegistry).build());

        MongoDatabase database = mongoClient.getDatabase(_dbname);

        return database.getCollection(docname, tClass);

    }
}
