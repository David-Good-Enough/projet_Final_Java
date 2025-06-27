package fr.ynov.collection.repository;

import fr.ynov.collection.model.Support;
import fr.ynov.collection.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;
import java.util.Optional;

public class SupportDao {

    public void save(Support support) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.persist(support);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Erreur lors de la sauvegarde du support", e);
        }
    }

    public void update(Support support) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.merge(support);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Erreur lors de la mise à jour du support", e);
        }
    }

    public void delete(Support support) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.remove(support);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Erreur lors de la suppression du support", e);
        }
    }

    public Optional<Support> findById(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Support support = session.get(Support.class, id);
            return Optional.ofNullable(support);
        }
    }

    public Optional<Support> findByNom(String nom) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Support> query = session.createQuery(
                "FROM Support s WHERE s.nom = :nom", 
                Support.class
            );
            query.setParameter("nom", nom);
            return query.uniqueResultOptional();
        }
    }

    public List<Support> findAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Support> query = session.createQuery("FROM Support s ORDER BY s.nom", Support.class);
            return query.list();
        }
    }

    public Support findOrCreateByNom(String nom) {
        Optional<Support> existing = findByNom(nom);
        if (existing.isPresent()) {
            return existing.get();
        } else {
            Support newSupport = new Support(nom);
            save(newSupport);
            return newSupport;
        }
    }

    public void initializeDefaultSupports() {
        String[] defaultSupports = {"PC", "PS5", "PS4", "Xbox Series X", "Xbox One", "Nintendo Switch", "Nintendo 3DS", "Mobile"};
        
        for (String supportNom : defaultSupports) {
            if (findByNom(supportNom).isEmpty()) {
                save(new Support(supportNom));
            }
        }
    }
} 