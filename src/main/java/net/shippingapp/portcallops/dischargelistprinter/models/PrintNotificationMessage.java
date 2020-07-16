package net.shippingapp.portcallops.dischargelistprinter.models;

public class PrintNotificationMessage {

    private String portCode ;
    private String vesselCode ; 
    private String voyageCode ;
    private String dischargeListID ;
    private String dischargeListVersionID;
    private String dischargeReportURL ;

    public String getPortCode() {
        return portCode;
    }

    public void setPortCode(String portCode) {
        this.portCode = portCode;
    }

    public String getVesselCode() {
        return vesselCode;
    }

    public void setVesselCode(String vesselCode) {
        this.vesselCode = vesselCode;
    }

    public String getVoyageCode() {
        return voyageCode;
    }

    public void setVoyageCode(String voyageCode) {
        this.voyageCode = voyageCode;
    }

    public String getDischargeListID() {
        return dischargeListID;
    }

    public void setDischargeListID(String dischargeListID) {
        this.dischargeListID = dischargeListID;
    }

    public String getDischargeListVersionID() {
        return dischargeListVersionID;
    }

    public void setDischargeListVersionID(String dischargeListVersionID) {
        this.dischargeListVersionID = dischargeListVersionID;
    }

    public String getDischargeReportURL() {
        return dischargeReportURL;
    }

    public void setDischargeReportURL(String dischargeReportURL) {
        this.dischargeReportURL = dischargeReportURL;
    }

     
}