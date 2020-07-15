package net.shippingapp.portcallops.dischargelistprinter.commandframework;

import java.util.Map;

public interface Command {

    public String getName();

    public void setName(String commandName);

    public void setContext(Map commandContext);

    public Map getContext();

    public void execute() throws CommandException;

    public CommandQueueContext getQueueContext() ;

    public void setQueueContext (CommandQueueContext queueContext) ;

}