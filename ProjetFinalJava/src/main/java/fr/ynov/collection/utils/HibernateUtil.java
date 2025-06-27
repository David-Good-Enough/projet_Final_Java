package fr.ynov.collection.utils;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HibernateUtil {
    private static final Logger logger = LoggerFactory.getLogger(HibernateUtil.class);
    private static SessionFactory sessionFactory;

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            initializeSessionFactory();
        }
        return sessionFactory;
    }

    private static synchronized void initializeSessionFactory() {
        if (sessionFactory != null) {
            return;
        }

        try {
            logger.info("Initialisation de la SessionFactory Hibernate (SQLite)...");
            
            Configuration configuration = new Configuration();
            
            // Configuration pour SQLite
            configuration.setProperty("hibernate.connection.url", "jdbc:sqlite:collection.db");
            configuration.setProperty("hibernate.connection.driver_class", "org.sqlite.JDBC");
            configuration.setProperty("hibernate.dialect", "org.hibernate.community.dialect.SQLiteDialect");
            configuration.setProperty("hibernate.hbm2ddl.auto", "update");
            configuration.setProperty("hibernate.show_sql", "true");
            configuration.setProperty("hibernate.format_sql", "true");
            configuration.setProperty("hibernate.connection.autocommit", "true");
            configuration.setProperty("hibernate.jdbc.batch_size", "0");
            
            // Ajouter les classes annotées
            configuration.addAnnotatedClass(fr.ynov.collection.model.JeuVideo.class);
            configuration.addAnnotatedClass(fr.ynov.collection.model.Support.class);
            
            // Construire la SessionFactory
            sessionFactory = configuration.buildSessionFactory();
            
            logger.info("SessionFactory Hibernate initialisée avec succès (SQLite)");
            
        } catch (Exception ex) {
            logger.error("Erreur lors de l'initialisation de la SessionFactory", ex);
            ex.printStackTrace();
            throw new RuntimeException("Impossible d'initialiser Hibernate: " + ex.getMessage(), ex);
        }
    }

    public static void shutdown() {
        if (sessionFactory != null && !sessionFactory.isClosed()) {
            sessionFactory.close();
            logger.info("SessionFactory Hibernate fermée");
        }
    }
}
