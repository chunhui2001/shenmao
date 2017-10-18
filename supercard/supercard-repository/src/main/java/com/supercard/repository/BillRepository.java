package com.supercard.repository;

import com.mongodb.BasicDBObject;
import com.mongodb.Block;
import com.mongodb.client.MongoCollection;
import com.supercard.tour.BillEntity;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;
import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

public class BillRepository extends BaseRepository
{

//    private String defaultEnv = "local";

    private static final String _billDocumentName = "bill";
    private MongoCollection<BillEntity> billCollection = null;

    private BillRepository(String env) {
        if (env != null && !env.equals(defaultEnv)) repositoryHelper = new RepositoryHelper(env);
        billCollection = repositoryHelper.getCollection(_billDocumentName, BillEntity.class);
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

    public ObjectId exists(String bank, String billMonth) {
        try {
            BillEntity b = billCollection.find(and (eq("bank", bank), eq("billMonth", billMonth))).limit(1).first();
            return b == null ? null : b.getId();
        } catch (Exception e) {
            return null;
        }
    }

    public void save(BillEntity bill) {

        billCollection.insertOne(bill);

    }

    public void save(Collection<BillEntity> billList) {

        if (billList == null) return;

        try {
            this.save(billList.stream().collect(Collectors.toList()));
        } catch (Exception e) {
            String s = e.getMessage();

        }

    }

    public void save(List<BillEntity> billList) {

        // billCollection.insertMany(billList);
        int i = 0;

        billList.stream().filter( bill -> {
            ObjectId oid = this.exists(bill.getBank(), bill.getBillMonth());
            if (oid != null) bill.setId(oid);
            return oid == null;
        }).forEach( bill -> {
            this.save(bill);
        });

    }

    public static BillRepository newInstance() {
        return new BillRepository(null);
    }

}
