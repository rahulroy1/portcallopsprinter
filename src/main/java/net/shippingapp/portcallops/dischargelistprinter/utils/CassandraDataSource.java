package net.shippingapp.portcallops.dischargelistprinter.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.security.KeyStore;

import javax.annotation.PostConstruct;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.JdkSSLOptions;
import com.datastax.driver.core.RemoteEndpointAwareJdkSSLOptions;
import com.datastax.driver.core.Session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class CassandraDataSource {

    @Value("${appdb.cosmosdb.host}")
    private String cosmosDbHost ;

    @Value("${appdb.cosmosdb.port}")
    private String cosmosDbPort  ;

    @Value("${appdb.cosmosdb.userid}")
    private String userId ;

    @Value("${appdb.cosmosdb.password}")
    private String password ;

    @Value("${appdb.cosmosdb.keyspace}")
    private String cosmosDbKeyspace ;

    private static Cluster dbCluster ;

    private static CassandraDataSource dataSource;

    @Autowired
    private Environment env;

    @PostConstruct
    public void initDataSource() throws Exception {

        String sslKeyStorePassword = "changeit";
        String keyStoreLoc = System.getProperty("app.keystore") ;
        File sslKeyStoreFilePath ;
        if (keyStoreLoc == null || keyStoreLoc.length() ==0) {
            String defaultFilePath = env.getProperty("JAVA_HOME") + "jre/lib/security/cacerts";
            sslKeyStoreFilePath = 
            new File(defaultFilePath) ;

        } else {
            sslKeyStoreFilePath = 
            new File(keyStoreLoc) ;
        }

         
        final KeyStore keyStore = KeyStore.getInstance("JKS");
        try (final InputStream is = new FileInputStream(sslKeyStoreFilePath)) {
               keyStore.load(is, sslKeyStorePassword.toCharArray());
           }

        final KeyManagerFactory kmf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
        kmf.init(keyStore, sslKeyStorePassword.toCharArray());
        final TrustManagerFactory tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
        tmf.init(keyStore);

        // Creates a socket factory for HttpsURLConnection using JKS contents.
        final SSLContext sc = SSLContext.getInstance("TLSv1.2");
        sc.init(kmf.getKeyManagers(), tmf.getTrustManagers(), new java.security.SecureRandom());

        JdkSSLOptions sslOptions = RemoteEndpointAwareJdkSSLOptions.builder().withSSLContext(sc).build();

        dbCluster = Cluster.builder().addContactPoint(cosmosDbHost).withoutMetrics()
                                     .withPort(Integer.parseInt(cosmosDbPort))
                                    .withCredentials(userId, password)
                                    .withSSL(sslOptions).build();

       
    }


    public CassandraDataSource() {

      
    }

    public  Session getCQLSession() {

        Session session = dbCluster.connect(cosmosDbKeyspace);
        System.out.println("Connected to Azure Cosmos DB - Cassandra API " + session.getCluster().getClusterName());
        return session ;
        
    }

    public static CassandraDataSource getInstance() throws Exception {
       
        if (dataSource == null) {
            System.out.println("CassandraDataSource is singleton and not yet instantiated, instantiating & sending the instantiated one");
            dataSource = new CassandraDataSource();
        } else {
            System.out.println("CassandraDataSource is singleton and already instantiated, sending the instantiated one");
        }
        return dataSource;
    }



    
}