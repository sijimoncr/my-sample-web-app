package com.example.webapp;

import org.apache.catalina.Context;
import org.apache.catalina.connector.Connector;
import org.apache.catalina.startup.Tomcat;
import java.io.File;

public class TomcatMain {
    
    public static void main(String[] args) {
        try {
            System.out.println("Creating Tomcat instance...");
            Tomcat tomcat = new Tomcat();
            
            // Explicitly create and configure connector
            System.out.println("Creating connector...");
            Connector connector = new Connector("org.apache.coyote.http11.Http11NioProtocol");
            connector.setPort(8081);
            tomcat.setConnector(connector);
            tomcat.getService().addConnector(connector);
            
            System.out.println("Setting up webapp...");
            tomcat.setBaseDir(System.getProperty("java.io.tmpdir"));
            
            File webappDir = new File("src/main/webapp");
            Context context = tomcat.addContext("", webappDir.getAbsolutePath());
            
            System.out.println("Registering servlet...");
            Tomcat.addServlet(context, "TestServlet", new com.example.webapp.TestServlet());
            context.addServletMappingDecoded("/test", "TestServlet");
            
            System.out.println("Starting Tomcat...");
            tomcat.start();
            
            // Verify connector is actually listening
            System.out.println("Connector state: " + connector.getState());
            System.out.println("Connector port: " + connector.getPort());
            System.out.println("Local port: " + connector.getLocalPort());
            
            System.out.println("SUCCESS: Tomcat should be listening on http://localhost:8080/test");
            
            tomcat.getServer().await();
            
        } catch (Exception e) {
            System.err.println("ERROR:");
            e.printStackTrace();
        }
    }
}