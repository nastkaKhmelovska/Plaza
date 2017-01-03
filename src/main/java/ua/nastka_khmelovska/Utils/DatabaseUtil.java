package ua.nastka_khmelovska.Utils;

import java.io.File;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.cfg.Configuration;

import ua.nastka_khmelovska.models.Client;
import ua.nastka_khmelovska.models.Order;

public class DatabaseUtil {

	private static final SessionFactory sessionFactory = buildSessionFactory(); 
	 
    private static SessionFactory buildSessionFactory() { 
    	
        try {
        	AnnotationConfiguration aconf = new AnnotationConfiguration()
        		      .addAnnotatedClass(Client.class)
        		      .addAnnotatedClass(Order.class);
        		      Configuration conf = aconf.configure();
            return conf.buildSessionFactory(); 
 
        } catch (Throwable ex) { 
            // Make sure you log the exception, as it might be swallowed 
            System.err.println("Initial SessionFactory creation failed." + ex); 
            throw new ExceptionInInitializerError(ex); 
        } 
    }
 
    public static SessionFactory getSessionFactory() { 
        return sessionFactory; 
    } 
 
    public static void shutdown() { 
        // Close caches and connection pools 
        getSessionFactory().close(); 
    } 
	
}
