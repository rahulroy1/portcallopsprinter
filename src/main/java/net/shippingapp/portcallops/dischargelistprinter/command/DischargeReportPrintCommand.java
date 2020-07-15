package net.shippingapp.portcallops.dischargelistprinter.command;

import java.util.Iterator;
import java.util.List;

import com.azure.core.http.rest.PagedIterable;
import com.azure.storage.queue.QueueClient;
import com.azure.storage.queue.QueueClientBuilder;
import com.azure.storage.queue.models.QueueMessageItem;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.shippingapp.portcallops.dischargelistprinter.commandframework.CommandException;
import net.shippingapp.portcallops.dischargelistprinter.models.PrintNotificationMessage;
import net.shippingapp.portcallops.dischargelistprinter.utils.AzureStorageAccount;
import net.shippingapp.portcallops.dischargelistprinter.utils.DischargeListPrinterBlobUtils;
import net.shippingapp.portcallops.dischargelistprinter.utils.DischargeListPrinterDBUtils;

public class DischargeReportPrintCommand extends BaseCommand {

    private static final Logger logger = LogManager.getLogger(DischargeReportPrintCommand.class);

    String connectionString = "DefaultEndpointsProtocol=https;AccountName=swararoydev;AccountKey=9Tt9LZBZ32Ifz5DjG4ZJc/jfT1gxDArcPB5k4vf+FT/BOIZZNlF4UwJ/hPxJhdKZci0isEXsYWhqCsQsCt1qWQ==;EndpointSuffix=core.windows.net";
    String queueName = "portcallopsprinter";

    public void execute() throws CommandException {


        try {


            AzureStorageAccount azureStorageAccount = new AzureStorageAccount() ;

            QueueClient queueClient = azureStorageAccount.getNotificationQueueClient() ;
            PagedIterable<QueueMessageItem>  printRequests = queueClient.receiveMessages(5) ;

            Iterator it = printRequests.iterator() ;

            while(it.hasNext()) {
    
                QueueMessageItem queuedMessageItem = (QueueMessageItem) it.next() ;
                String messageContent = queuedMessageItem.getMessageText() ;
                String messageID = queuedMessageItem.getMessageId() ;
    
                logger.info("Message Content to process = " + messageContent) ;
    
                ObjectMapper mapper = new ObjectMapper();
                PrintNotificationMessage  message  = mapper.readValue(messageContent, 
                                                     PrintNotificationMessage.class); 

                try {

                    logger.info("Port = " + message.getPortCode());
                    logger.info("Vessel = " + message.getVesselCode());
                    logger.info("Voyage = " + message.getVoyageCode());
                    logger.info("dischargeListID = " + message.getDischargeListID());
                    logger.info("dischargeListVersionID = " + message.getDischargeListVersionID());
        
                    DischargeListPrinterDBUtils dischargeListPrinterDBUtils =
                       new DischargeListPrinterDBUtils() ;
                    List containers  = dischargeListPrinterDBUtils.
                                       getDischargeListContainer(message) ;

                    logger.info("Number of containers to print = " + containers.size()) ;

                    DischargeListPrinterBlobUtils printUtils = new DischargeListPrinterBlobUtils() ;
                    String blobURL = printUtils.printDischargeList(message, containers);
                    logger.info("Discharge List Print URL = " + blobURL) ;

                    dischargeListPrinterDBUtils.updateBlobURL(message, blobURL);
                    logger.info("Succuessfully updated Print URL into database = " + blobURL) ;

                    queueClient.deleteMessage(queuedMessageItem.getMessageId(), 
                                              queuedMessageItem.getPopReceipt());
 
                    logger.info("Succuessfully deleted message from queue" ) ;                   

                } catch (Exception e) {

                    e.printStackTrace(); 
                    logger.error(e) ;
                    logger.error("Failed to print discharge list for the notification message " + e.getMessage()) ;

                }                                  


            }

        } catch (Exception e) {
            e.printStackTrace(); 
            logger.error(e) ;
            throw new CommandException (e) ;
        }
       
        

    }

    public static void main (String args[]) throws Exception {

        String connectionString = "DefaultEndpointsProtocol=https;AccountName=swararoydev;AccountKey=9Tt9LZBZ32Ifz5DjG4ZJc/jfT1gxDArcPB5k4vf+FT/BOIZZNlF4UwJ/hPxJhdKZci0isEXsYWhqCsQsCt1qWQ==;EndpointSuffix=core.windows.net";
        String queueName = "portcallopsprinter";


        QueueClient queueClient = new QueueClientBuilder().connectionString(connectionString)
                                     .queueName(queueName).buildClient();
                                     
        PagedIterable<QueueMessageItem>  printRequests = queueClient.receiveMessages(5) ;
        Iterator it = printRequests.iterator() ;
        while(it.hasNext()) {

            QueueMessageItem queuedMessageItem = (QueueMessageItem) it.next() ;
            String messageContent = queuedMessageItem.getMessageText() ;
            String messageID = queuedMessageItem.getMessageId() ;

            System.out.println("Message Content = " + messageContent) ;

            ObjectMapper mapper = new ObjectMapper();
            PrintNotificationMessage  message  = mapper.readValue(messageContent, 
                                                 PrintNotificationMessage.class);  

            System.out.println("Port = " + message.getPortCode());
            System.out.println("Vessel = " + message.getVesselCode());
            System.out.println("Voyage = " + message.getVoyageCode());
            System.out.println("dischargeListID = " + message.getDischargeListID());
            System.out.println("dischargeListVersionID = " + message.getDischargeListVersionID());

            DischargeListPrinterDBUtils dischargeListPrinterDBUtils =
               new DischargeListPrinterDBUtils() ;
            List containers  = dischargeListPrinterDBUtils.
                               getDischargeListContainer(message) ;




        }
    }
    
}