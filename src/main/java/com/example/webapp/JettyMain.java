package com.example.webapp;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.ee10.webapp.WebAppContext;  // Note: ee10 package

public class JettyMain {
    public static void main(String[] args) throws Exception {
        Server server = new Server(8080);
        
        // Use Jakarta EE 10 WebAppContext
        WebAppContext webapp = new WebAppContext();
        webapp.setContextPath("/");
        webapp.setWar("src/main/webapp");
        
        // Configure for Jakarta EE 10
        webapp.setConfigurationClasses(new String[]{
            "org.eclipse.jetty.ee10.webapp.WebInfConfiguration",
            "org.eclipse.jetty.ee10.webapp.WebXmlConfiguration",
            "org.eclipse.jetty.ee10.webapp.MetaInfConfiguration",
            "org.eclipse.jetty.ee10.webapp.FragmentConfiguration",
            "org.eclipse.jetty.ee10.annotations.AnnotationConfiguration",
            "org.eclipse.jetty.ee10.webapp.JettyWebXmlConfiguration"
        });
        
        server.setHandler(webapp);
        
        System.out.println("Starting Jakarta EE 10 Web App with JSP on http://localhost:8080");
        System.out.println("Running on Java " + System.getProperty("java.version"));
        System.out.println("Using Jetty " + org.eclipse.jetty.util.Jetty.VERSION);
        System.out.println("Pages available:");
        System.out.println("  - Home: http://localhost:8080/");
        System.out.println("  - Users: http://localhost:8080/users");
        System.out.println("  - Products: http://localhost:8080/products");
        System.out.println("  - About: http://localhost:8080/about");
        System.out.println("Press Ctrl+C to stop the server");
        
        server.start();
        server.join();
    }
}