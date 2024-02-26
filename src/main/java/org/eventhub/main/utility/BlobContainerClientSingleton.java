package org.eventhub.main.utility;

import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobContainerClientBuilder;
import lombok.Getter;

@Getter
public class BlobContainerClientSingleton {
    private static volatile BlobContainerClientSingleton instance;
    private final BlobContainerClient blobContainerClient;
    private BlobContainerClientSingleton() {
        String connectionString = String.format("DefaultEndpointsProtocol=https;AccountName=%s;AccountKey=%s;EndpointSuffix=core.windows.net", System.getenv("AccountName"), System.getenv("AccountKey"));

        this.blobContainerClient = new BlobContainerClientBuilder()
                .connectionString(connectionString)
                .containerName(System.getenv("container_name"))
                .buildClient();
    }
    public static BlobContainerClientSingleton getInstance() {
        if (instance == null) {
            synchronized (BlobContainerClientSingleton.class) {
                if (instance == null) {
                    instance = new BlobContainerClientSingleton();
                }
            }
        }
        return instance;
    }

}
