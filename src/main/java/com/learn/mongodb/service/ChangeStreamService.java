package com.learn.mongodb.service;

import com.learn.mongodb.model.Address;
import com.learn.mongodb.model.Product;
import com.learn.mongodb.model.User;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.UpdateOptions;
import com.mongodb.client.model.Updates;
import com.mongodb.client.result.UpdateResult;
import lombok.AllArgsConstructor;
import org.bson.Document;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.bson.conversions.Bson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

import static com.mongodb.client.model.Aggregates.match;
import static com.mongodb.client.model.Filters.in;
import static java.util.Collections.singletonList;
import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

@Service
@AllArgsConstructor
public class ChangeStreamService {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Async
    public void runChangeStreamOnAddressesCollection() {
        System.out.println("In change stream service for addresses collection");
        CodecRegistry pojoCodecRegistry = fromRegistries(MongoClientSettings.getDefaultCodecRegistry(),
                fromProviders(PojoCodecProvider.builder().automatic(true).build()));

        MongoCollection<Address> addressCollection = mongoTemplate.getDb().withCodecRegistry(pojoCodecRegistry).getCollection("addresses", Address.class);
        System.out.println("no of documents in addresses collection :: " + addressCollection.countDocuments());

        List<Bson> pipeline = singletonList(
                match(in("operationType", "replace", "update"))
        );

        addressCollection.watch(pipeline).forEach(addressChangeStreamDocument -> {
            Address address = addressChangeStreamDocument.getFullDocument();
            String cityId = addressChangeStreamDocument.getDocumentKey().get("_id").asString().getValue();
            address.setCity(cityId);
            System.out.println("Updated address entity :: " + address);
            updateQueryCollectionForAddressChanges(address);
        });
    }

    @Async
    public void runChangeStreamOnProductsCollection() {
        System.out.println("In change stream service for products collection");
        CodecRegistry pojoCodecRegistry = fromRegistries(MongoClientSettings.getDefaultCodecRegistry(),
                fromProviders(PojoCodecProvider.builder().automatic(true).build()));

        MongoCollection<Product> productsCollection = mongoTemplate.getDb().withCodecRegistry(pojoCodecRegistry).getCollection("products", Product.class);
        System.out.println("no of documents in products collection :: " + productsCollection.countDocuments());

        List<Bson> pipeline = singletonList(
                match(in("operationType", "replace", "update"))
        );

        productsCollection.watch(pipeline).forEach(productChangeStreamDocument -> {
            Product product = productChangeStreamDocument.getFullDocument();
            String productNameId = productChangeStreamDocument.getDocumentKey().get("_id").asString().getValue();
            product.setName(productNameId);
            System.out.println("Updated product entity :: " + product);
            updateQueryCollectionForProductChanges(product);
        });
    }

    public void updateQueryCollectionForAddressChanges(Address address) {
        System.out.println("Updating query collection for the updates done in addresses collection...");
        Query query = new Query(Criteria.where("address._id").is(address.getCity()));

        Update update = new Update()
                .set("address.state", address.getState())
                .set("address.pinCode", address.getPinCode());

        mongoTemplate.updateFirst(query, update, User.class);
        System.out.println("Updated query collection for the updates done in addresses collection...");
    }

    public void updateQueryCollectionForProductChanges(Product product) {
        System.out.println("Updating query collection for the updates done in products collection...");
        MongoCollection<User> queryCollection = mongoTemplate.getDb().getCollection("order", User.class);
        UpdateResult updateResult = queryCollection.updateMany(
                new Document(),  // Empty document means all documents
                Updates.combine(
                        Updates.set("products.$[elem].quantity", product.getQuantity()),  // Update the 'quantity' field inside the array
                        Updates.set("products.$[elem].price", product.getPrice())  // Update the 'price' field inside the array
                ),
                new UpdateOptions().arrayFilters(Arrays.asList(
                        Filters.eq("elem._id", product.getName())  // Condition for elements in the array
                ))
        );
        System.out.println("Updated query collection for the updates done in products collection...");
    }
}
