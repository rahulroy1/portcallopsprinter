package net.shippingapp.portcallops.dischargelistprinter.utils;

import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobServiceClient;
import com.azure.storage.blob.BlobServiceClientBuilder;
import com.azure.storage.queue.QueueClient;
import com.azure.storage.queue.QueueClientBuilder;

public class AzureStorageAccount {

    public QueueClient getNotificationQueueClient() {

        String connectionString = "DefaultEndpointsProtocol=https;AccountName=swararoydev;AccountKey=9Tt9LZBZ32Ifz5DjG4ZJc/jfT1gxDArcPB5k4vf+FT/BOIZZNlF4UwJ/hPxJhdKZci0isEXsYWhqCsQsCt1qWQ==;EndpointSuffix=core.windows.net";
        String queueName = "portcallopsprinter";

        QueueClient queueClient = new QueueClientBuilder().connectionString(connectionString).queueName(queueName)
                .buildClient();
        return queueClient;

    }

    public BlobContainerClient getDischargeListBlobClient() {

        String connectionString = "DefaultEndpointsProtocol=https;AccountName=swararoydev;AccountKey=9Tt9LZBZ32Ifz5DjG4ZJc/jfT1gxDArcPB5k4vf+FT/BOIZZNlF4UwJ/hPxJhdKZci0isEXsYWhqCsQsCt1qWQ==;EndpointSuffix=core.windows.net";
       
         
        BlobServiceClient blobServiceClient = new BlobServiceClientBuilder().connectionString(connectionString)
                .buildClient();

        System.out.println(blobServiceClient.getAccountName());
        System.out.println(blobServiceClient.getAccountInfo());
        System.out.println(blobServiceClient.getAccountUrl());

        // Create the container and return a container client object
        BlobContainerClient containerClient = blobServiceClient.getBlobContainerClient("dischargelist");
        System.out.println(containerClient.getBlobContainerName());
        System.out.println(containerClient.getBlobContainerUrl());
        System.out.println(containerClient.getServiceVersion());

        return containerClient;
    }

}