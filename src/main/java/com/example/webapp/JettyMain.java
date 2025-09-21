package com.example.webapp;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.WebAppContext;

public class JettyMain {
    public static void main(String[] args) throws Exception {
        Server server = new Server(8080);
        
        WebAppContext webapp = new WebAppContext();
        webapp.setContextPath("/");
        webapp.setWar("src/main/webapp");
        
        server.setHandler(webapp);
        
        System.out.println("Starting Sample Web App on http://localhost:8080");
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
