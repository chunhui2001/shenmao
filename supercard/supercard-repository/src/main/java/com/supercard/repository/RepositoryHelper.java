package com.supercard.repository;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.supercard.tour.BillEntity;
import com.supercard.tour.BillItemEntity;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

public class RepositoryHelper {

    private static final CodecRegistry pojoCodecRegistry = fromRegistries(MongoClient.getDefaultCodecRegistry(), fromProviders(PojoCodecProvider.builder().automatic(true).build()));
    private static final String _mongoServerHost = "localhost";
//    private static final String _mongoServerHost = "192.168.189.197";
    private static final int _mongoPort = 27017;
    private static final String _dbname = "supercard_db";
    private static final String _usename = "root";
    private static final String _passwd = "Cc";

    // use admin;
    // db.createUser({user:'root', pwd: 'Cc', roles: ['root']});
    // /usr/bin/mongod --auth --quiet --config /etc/mongod.conf
    // db.runCommand({create:"supercard_db"};
    // use supercard_db;
    // db.createUser({user:'root', pwd: 'Cc', roles: ['readWrite','dbAdmin']});

    public static MongoCollection<BillEntity>  getCollection(String docname, Class<BillEntity> tClass) {

//        MongoClient mongoClient = getMongoClientWithAuth();
        MongoClient mongoClient = getMongoClientNormal();
        MongoDatabase database = mongoClient.getDatabase(_dbname);
        return database.getCollection(docname, tClass);

    }

    public static MongoClient getMongoClientWithAuth() {

        return new MongoClient(
                new ServerAddress(_mongoServerHost,_mongoPort),      // server address
                Arrays.asList(MongoCredential.createScramSha1Credential(_usename, _dbname, _passwd.toCharArray())),     // credential
                MongoClientOptions.builder().codecRegistry(pojoCodecRegistry).build());     // options

    }

    public static MongoClient getMongoClientNormal() {

        return new MongoClient(
                new ServerAddress(_mongoServerHost,_mongoPort),
                MongoClientOptions.builder().codecRegistry(pojoCodecRegistry).build());

    }

}
