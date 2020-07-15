package net.shippingapp.portcallops.dischargelistprinter.command;

import java.util.Map;

import net.shippingapp.portcallops.dischargelistprinter.commandframework.Command;
import net.shippingapp.portcallops.dischargelistprinter.commandframework.CommandException;
import net.shippingapp.portcallops.dischargelistprinter.commandframework.CommandQueueContext;


public class BaseCommand implements Command {

    protected String name ;
    protected Map  context ;
    protected CommandQueueContext queueContext ;


 
    public String getName() {
        
        return name;
    }

    
    public void setName(String commandName) {
        this.name = commandName ;

    }

    
    public void setContext(Map commandContext) {
        this.context = commandContext ;

    }

    
    public Map getContext() {
        
        return context;
    }

    public CommandQueueContext getQueueContext() {

        return queueContext ;

    }


    public void setQueueContext (CommandQueueContext queueContext) {
        this.queueContext = queueContext ;
    }

   
    public void execute() throws CommandException {
        

    }
    
}