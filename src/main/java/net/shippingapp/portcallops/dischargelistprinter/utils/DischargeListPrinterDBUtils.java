package net.shippingapp.portcallops.dischargelistprinter.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.datastax.driver.core.BoundStatement;
import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;

import net.shippingapp.portcallops.dischargelistprinter.models.DischargeListContainer;
import net.shippingapp.portcallops.dischargelistprinter.models.PrintNotificationMessage;

public class DischargeListPrinterDBUtils {


    public void updateBlobURL (PrintNotificationMessage notificationDetails, String blobURL) throws Exception {

      

        Session cqlSession = CassandraDataSource.getInstance().getCQLSession();
        PreparedStatement updateDischargeListPreparedStmt = cqlSession.prepare(
            "UPDATE cargo_handling_list_cockpit.discharge_list_by_port_vessel_voyage " + 
            "set blob_url = ? " + 
            " where port_code= ? and vessel_code = ? and voyage_code= ? " +
            " and discharge_list_id = ? and discharge_list_version_id = ? ");

        BoundStatement updateDischargeListBoundStmt = updateDischargeListPreparedStmt.bind()
            .setString("blob_url" ,  blobURL)
            .setString("port_code", notificationDetails.getPortCode())
            .setString("vessel_code", notificationDetails.getVesselCode())
            .setString("voyage_code", notificationDetails.getVoyageCode()) 
            .setUUID("discharge_list_id", UUID.fromString(notificationDetails.getDischargeListID()))
            .setUUID("discharge_list_version_id" , UUID.fromString(notificationDetails.getDischargeListVersionID()));    
    
        cqlSession.execute(updateDischargeListBoundStmt)  ;

    }

    public List getDischargeListContainer (PrintNotificationMessage notificationDetails) {
        
        List containers = new ArrayList() ;

        try {

            Session cqlSession = CassandraDataSource.getInstance().getCQLSession();
            PreparedStatement selectDischargeListPreparedStmt = cqlSession.prepare(
                "SELECT container_number, container_iso_code, container_operator from cargo_handling_list_cockpit.discharge_list_containers_by_port_vessel_voyage " + 
                " where port_code= ? and vessel_code = ? and voyage_code= ? " +
                " and discharge_list_id = ? and discharge_list_version_id = ? ");
             

            BoundStatement selectDischargeListBoundStmt = selectDischargeListPreparedStmt.bind()
                .setString("port_code", notificationDetails.getPortCode())
                .setString("vessel_code", notificationDetails.getVesselCode())
                .setString("voyage_code", notificationDetails.getVoyageCode()) 
                .setUUID("discharge_list_id", UUID.fromString(notificationDetails.getDischargeListID()))
                .setUUID("discharge_list_version_id" , UUID.fromString(notificationDetails.getDischargeListVersionID()));
             

            ResultSet dischargeListRs =  cqlSession.execute(selectDischargeListBoundStmt)  ;
           
            for(Row row:dischargeListRs.all()){
              
                DischargeListContainer container = new DischargeListContainer() ;
                container.setContainerNumber(row.getString("container_number"));
                container.setContainerIsoCode(row.getString("container_iso_code"));
                container.setContainerOperator(row.getString("container_operator"));


                containers.add(container) ;
               
            }


        }catch(Exception e) {
              e.printStackTrace();
        }

    return containers;
}

}