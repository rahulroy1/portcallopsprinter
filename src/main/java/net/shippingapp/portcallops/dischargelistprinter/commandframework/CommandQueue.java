package net.shippingapp.portcallops.dischargelistprinter.commandframework;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CommandQueue {

    private static final Logger logger = LogManager.getLogger(CommandQueue.class);

    private List queue = new  ArrayList() ;

    public void addCommandToQueue(Command command) {
        queue.add(command) ;
    }

    public void runQueue() {

        long startTime = System.currentTimeMillis() ;
    
        try {

             for(int i=0 ; i < queue.size() ; i++) {
                 
                 long commandStartTime = System.currentTimeMillis() ;
                 Command command = (Command) queue.get(i) ;
                 command.execute(); 
                 long commandEndTime = System.currentTimeMillis() ;
                 logger.info("Command Succeeded. Completion time (millisec) = " + command.getClass().getName() + 
                                         (commandEndTime - commandStartTime));
             }

        long endTime = System.currentTimeMillis() ; 
        logger.info("Command Queue Succeeded. Completion time (millisec) = " + (endTime - startTime));

        } catch (Exception e) {
            e.printStackTrace(); 
            logger.error(e);
            logger.error("Command Queue Failed with exception = " + e.getMessage()) ;
        }


    }
    
}