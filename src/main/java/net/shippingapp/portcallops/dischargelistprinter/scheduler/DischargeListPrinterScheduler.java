package net.shippingapp.portcallops.dischargelistprinter.scheduler;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import net.shippingapp.portcallops.dischargelistprinter.command.DischargeReportPrintCommand;
import net.shippingapp.portcallops.dischargelistprinter.commandframework.CommandQueue;
import net.shippingapp.portcallops.dischargelistprinter.commandframework.CommandQueueContext;

@Component
public class DischargeListPrinterScheduler {

    private static final Logger logger = LogManager.getLogger(DischargeListPrinterScheduler.class);


    // every 45 seconds
    @Scheduled(cron = "*/45 * * * * *")
    public void cronJobSch() {

        try {

            UUID uuid = UUID.randomUUID() ;

            logger.info("Starting DischargeList Printer Scheduler componenent for traceID = " + uuid.toString())  ;

            CommandQueue dischargeListPrintQueue = new CommandQueue();
            CommandQueueContext queueContext = new CommandQueueContext();
            DischargeReportPrintCommand printCommand = new DischargeReportPrintCommand();
            printCommand.setQueueContext(queueContext);
            dischargeListPrintQueue.addCommandToQueue(printCommand);
            dischargeListPrintQueue.runQueue();

            logger.info("Completed DischargeList Printer Scheduler componenent for traceID = " + uuid.toString())  ;


        } catch (Exception e) {

            e.printStackTrace();

        }

    }

}