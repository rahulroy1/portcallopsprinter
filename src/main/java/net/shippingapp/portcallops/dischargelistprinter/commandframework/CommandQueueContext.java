package net.shippingapp.portcallops.dischargelistprinter.commandframework;

import java.util.HashMap;
import java.util.Map;

public class CommandQueueContext {

    private Map commandContext = new HashMap() ;

    public void addToContext(String key, Object value) {
        commandContext.put(key, value) ;
    }

    public Object getFromContext (String key) {
        return commandContext.get(key) ;
    }
    
    
}