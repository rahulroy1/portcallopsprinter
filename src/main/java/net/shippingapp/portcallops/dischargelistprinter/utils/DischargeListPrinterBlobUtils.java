package net.shippingapp.portcallops.dischargelistprinter.utils;

import java.io.FileWriter;
import java.util.List;

import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobContainerClient;

import net.shippingapp.portcallops.dischargelistprinter.models.DischargeListContainer;
import net.shippingapp.portcallops.dischargelistprinter.models.PrintNotificationMessage;

public class DischargeListPrinterBlobUtils {

    private static final String NEW_LINE = System.lineSeparator() ;
    private static final String COMMA = " , " ;

    public String printDischargeList (PrintNotificationMessage  message, 
                                                        List containers) {

        
        try {

            String fileName = "dischargelist_" + java.util.UUID.randomUUID() + ".csv";
            FileWriter writer = new FileWriter(fileName, true);

            AzureStorageAccount storageAccount = new AzureStorageAccount() ;
            BlobContainerClient containerClient = storageAccount.getDischargeListBlobClient();

            StringBuffer header = new StringBuffer() ;
            header.append("CONTAINER_NUMBER").append(COMMA)
                  .append("CONTAINER_ISO_CODE").append(COMMA)
                  .append(NEW_LINE) ;

            StringBuffer body = new StringBuffer() ;

       
           writer.write(header.toString());

            for(int i =0 ; i < containers.size() ; i ++) {

                DischargeListContainer dlc = (DischargeListContainer) containers.get(i) ;
                body.append(dlc.getContainerNumber()).append(COMMA)
                    .append(dlc.getContainerIsoCode()).append(COMMA)
                    .append(NEW_LINE) ;
     
            }
   
            writer.write(body.toString());
            writer.close();
            BlobClient blobClient = containerClient.getBlobClient(fileName);
    
            System.out.println("\nUploading to Blob storage as blob:\n\t" + blobClient.getBlobUrl());
    
            // Upload the blob
            blobClient.uploadFromFile(fileName);
            String blobURL = blobClient.getBlobUrl() ;
            message.setDischargeReportURL(blobURL);
            return blobURL ;
            
         
            
        } catch (Exception e) {
            e.printStackTrace(); 
        }

        
        return "" ;

    }



    
}