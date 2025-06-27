package fr.ynov.collection.presenter;

import fr.ynov.collection.utils.HibernateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MainPresenter {
    
    private static final Logger logger = LoggerFactory.getLogger(MainPresenter.class);

    public void initialize() {
        try {
            // Initialiser Hibernate
            HibernateUtil.getSessionFactory();
            logger.info("Application initialisée avec succès");
        } catch (Exception e) {
            logger.error("Erreur lors de l'initialisation de l'application", e);
            throw new RuntimeException("Impossible d'initialiser l'application", e);
        }
    }
}
