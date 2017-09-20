package com.supercard.repository;

import com.mongodb.BasicDBObject;
import com.mongodb.Block;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.supercard.repository.tour.Person;
import com.supercard.tour.BillEntity;
import com.supercard.tour.BillItemEntity;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;
import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

public class BillRepository
{

    private static final String _billDocumentName = "bill";
    private MongoCollection<BillEntity> billCollection = null;

    public BillRepository() {
        billCollection = RepositoryHelper.getCollection(_billDocumentName, BillEntity.class);
    }

    public List<BillEntity> lists(String bank) {

        List<BillEntity> result = new ArrayList<>();

        billCollection.find(eq("bank", bank)).sort(new BasicDBObject("billMonth", -1)).forEach(new Block<BillEntity>() {
            @Override
            public void apply(final BillEntity billEntity) {
                result.add(billEntity);
            }
        });

        return result;

    }

    public boolean exists(String bank, String billMonth) {
        try {
            BillEntity b = billCollection.find(and (eq("bank", bank), eq("billMonth", billMonth))).limit(1).first();
            return b != null;
        } catch (Exception e) {
            return false;
        }
    }

    public void save(BillEntity bill) {

        billCollection.insertOne(bill);

    }

    public void save(Collection<BillEntity> billList) {
        this.save(billList.stream().collect(Collectors.toList()));
    }

    public void save(List<BillEntity> billList) {

        // billCollection.insertMany(billList);

        billList.stream().filter( bill -> {
            return !this.exists(bill.getBank(), bill.getBillMonth());
        }).forEach( bill -> {
            this.save(bill);
        });

    }

}
