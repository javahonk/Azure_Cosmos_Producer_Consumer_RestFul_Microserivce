package com.solstice.azure.springcloud.inventory;


import com.microsoft.azure.cosmosdb.*;
import com.microsoft.azure.cosmosdb.rx.AsyncDocumentClient;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import rx.Observable;

import javax.annotation.PostConstruct;
import java.util.Iterator;
import java.util.List;

@Component
public class FetchDataByQuery {

    @Value("${azure.cosmosdb.uri}")
    private String azureCosomsdbUri;
    @Value("${azure.cosmosdb.key}")
    private String azureCosmosdbKey;
    @Value("${azure.cosmosdb.database}")
    private String azureCosmosdbDatabase;
    @Value("${azure.cosmosdb.inventory.collection.id}")
    private String azureCosmosdbInventoryCollectionId;

    private AsyncDocumentClient client;

    @PostConstruct
    public void init() {
        client = new AsyncDocumentClient.Builder().withServiceEndpoint(azureCosomsdbUri)
                .withMasterKeyOrResourceToken(azureCosmosdbKey)
                .withConnectionPolicy(ConnectionPolicy.GetDefault()).withConsistencyLevel(ConsistencyLevel.Eventual)
                .build();
    }

    public void fetchRecordFromDb() throws InterruptedException, JSONException {
        FeedOptions options = new FeedOptions();
        // as this is a multi collection enable cross partition query
        options.setEnableCrossPartitionQuery(true);
        // note that setMaxItemCount sets the number of items to return in a single page
        // result
        options.setMaxItemCount(5);
        //String sql = "SELECT * FROM Inventory I where I.id=\"FRIDAY_TEST\"";
        String sql = "SELECT * FROM Inventory";
        Observable<FeedResponse<Document>> documentQueryObservable = client
                .queryDocuments("dbs/" + azureCosmosdbDatabase + "/colls/" + azureCosmosdbInventoryCollectionId, sql, options);
        // observable to an iterator
        Iterator<FeedResponse<Document>> it = documentQueryObservable.toBlocking().getIterator();

        while (it.hasNext()) {
            FeedResponse<Document> page = it.next();
            List<Document> results = page.getResults();
            // here we iterate over all the items in the page result
            for (Object doc : results) {
                System.out.println(doc);
            }
        }

    }
}
