package net.shippingapp.portcallops.dischargelistprinter.models;

public class DischargeListContainer {

    private String containerNumber ;
    private String containerIsoCode ;
    private String containerOperator ;

    public String getContainerNumber() {
        return containerNumber;
    }

    public void setContainerNumber(String containerNumber) {
        this.containerNumber = containerNumber;
    }

    public String getContainerIsoCode() {
        return containerIsoCode;
    }

    public void setContainerIsoCode(String containerIsoCode) {
        this.containerIsoCode = containerIsoCode;
    }

    public String getContainerOperator() {
        return containerOperator;
    }

    public void setContainerOperator(String containerOperator) {
        this.containerOperator = containerOperator;
    }
    
}