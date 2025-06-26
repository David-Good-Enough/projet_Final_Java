package fr.ynov.collection.utils;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class HibernateUtil {
    private static final SessionFactory sessionFactory;

    static {
        try {
            Properties props = new Properties();
            try (InputStream input = HibernateUtil.class.getClassLoader()
                    .getResourceAsStream("config.properties")) {
                if (input == null) {
                    throw new RuntimeException("Cannot find config.properties");
                }
                props.load(input);
            }

            Configuration configuration = new Configuration();
            configuration.setProperty("hibernate.connection.url", props.getProperty("db.url"));
            configuration.setProperty("hibernate.dialect", "org.hibernate.dialect.SQLiteDialect");
            configuration.setProperty("hibernate.connection.driver_class", "org.sqlite.JDBC");
            configuration.setProperty("hibernate.show_sql", props.getProperty("hibernate.show_sql"));
            configuration.setProperty("hibernate.hbm2ddl.auto", props.getProperty("hibernate.hbm2ddl.auto"));

            configuration.addAnnotatedClass(fr.ynov.collection.model.JeuVideo.class);
            configuration.addAnnotatedClass(fr.ynov.collection.model.Support.class);

            sessionFactory = configuration.buildSessionFactory();
        } catch (Exception ex) {
            throw new ExceptionInInitializerError("Initial SessionFactory creation failed: " + ex);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}
