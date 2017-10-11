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

import java.io.*;
import java.util.*;

import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

public class RepositoryHelper {

    private static final CodecRegistry _PojoCodecRegistry = fromRegistries(MongoClient.getDefaultCodecRegistry(), fromProviders(PojoCodecProvider.builder().automatic(true).build()));

    //    private static final String _mongoServerHost = "localhost";
    private String _mongoServerHost = "192.168.189.197";
    private Integer _mongoPort = 27017;
    private String _dbname = "supercard_db";
    private String _usename = null;
    private String _passwd = null;

    public RepositoryHelper(String env) {

        Properties prop = new Properties();

        try {

            InputStream in = this.getClass().getClassLoader().getResourceAsStream("env." + env +".properties");
            prop.load(in);
            Iterator<String> it=prop.stringPropertyNames().iterator();

            while(it.hasNext()){

                String key=it.next();

                if (key.equals("mongo_server_host")) _mongoServerHost = prop.getProperty(key);
                if (key.equals("mongo_port")) _mongoPort = Integer.parseInt(prop.getProperty(key));
                if (key.equals("mongo_dbname")) _dbname = prop.getProperty(key);
                if (key.equals("mongo_usename")) _usename = prop.getProperty(key);
                if (key.equals("mongo_passwd")) _passwd = prop.getProperty(key);

            }

            in.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // use admin;
    // db.createUser({user:'root', pwd: 'Cc', roles: ['root']});
    // /usr/bin/mongod --auth --quiet --config /etc/mongod.conf
    // db.runCommand({create:"supercard_db"};
    // use supercard_db;
    // db.createUser({user:'root', pwd: 'Cc', roles: ['readWrite','dbAdmin']});

    public MongoCollection<BillEntity>  getCollection(String docname, Class<BillEntity> tClass) {

        MongoClient mongoClient = _passwd == null ? getMongoClientNormal() : getMongoClientWithAuth();
        MongoDatabase database = mongoClient.getDatabase(_dbname);
        return database.getCollection(docname, tClass);

    }

    public MongoClient getMongoClientWithAuth() {

        return new MongoClient(
                new ServerAddress(_mongoServerHost, _mongoPort),      // server address
                Arrays.asList(MongoCredential.createScramSha1Credential(_usename, _dbname, _passwd.toCharArray())),     // credential
                MongoClientOptions.builder().codecRegistry(_PojoCodecRegistry).build());     // options

    }

    public MongoClient getMongoClientNormal() {

        return new MongoClient(
                new ServerAddress(_mongoServerHost,_mongoPort),
                MongoClientOptions.builder().codecRegistry(_PojoCodecRegistry).build());

    }

}
