package fr.ynov.collection.utils;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import fr.ynov.collection.model.JeuVideo;
import fr.ynov.collection.model.Support;

public class HibernateUtil {
    private static SessionFactory sessionFactory;

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                // Configuration Hibernate
                Configuration configuration = new Configuration();
                
                // Configuration de la base de données SQLite
                configuration.setProperty("hibernate.connection.driver_class", "org.sqlite.JDBC");
                configuration.setProperty("hibernate.connection.url", "jdbc:sqlite:collection.db");
                configuration.setProperty("hibernate.connection.username", "");
                configuration.setProperty("hibernate.connection.password", "");
                
                // Configuration du dialecte SQLite
                configuration.setProperty("hibernate.dialect", "org.hibernate.community.dialect.SQLiteDialect");
                
                // Configuration du pool de connexions
                configuration.setProperty("hibernate.connection.pool_size", "1");
                configuration.setProperty("hibernate.connection.autocommit", "true");
                
                // Configuration du cache
                configuration.setProperty("hibernate.cache.provider_class", "org.hibernate.cache.internal.NoCacheProvider");
                configuration.setProperty("hibernate.cache.use_second_level_cache", "false");
                configuration.setProperty("hibernate.cache.use_query_cache", "false");
                
                // Configuration du schéma
                configuration.setProperty("hibernate.hbm2ddl.auto", "update");
                configuration.setProperty("hibernate.show_sql", "false");
                configuration.setProperty("hibernate.format_sql", "true");
                
                // Ajouter les entités
                configuration.addAnnotatedClass(JeuVideo.class);
                configuration.addAnnotatedClass(Support.class);
                
                // Créer la SessionFactory
                sessionFactory = configuration.buildSessionFactory();
                
            } catch (Exception e) {
                System.err.println("Erreur lors de l'initialisation de Hibernate: " + e.getMessage());
                e.printStackTrace();
                throw new RuntimeException("Impossible d'initialiser Hibernate", e);
            }
        }
        return sessionFactory;
    }

    public static void shutdown() {
        if (sessionFactory != null && !sessionFactory.isClosed()) {
            sessionFactory.close();
        }
    }
}
