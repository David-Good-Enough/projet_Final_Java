package fr.ynov.collection.repository;

import fr.ynov.collection.model.Support;
import fr.ynov.collection.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;
import java.util.Optional;

public class SupportRepository {

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

    public void delete(Long id) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Support support = session.get(Support.class, id);
            if (support != null) {
                session.remove(support);
            }
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
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la recherche du support", e);
        }
    }

    public Support findByNom(String nom) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Support> query = session.createQuery(
                "FROM Support s WHERE s.nom = :nom", 
                Support.class
            );
            query.setParameter("nom", nom);
            return query.uniqueResult();
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la recherche du support par nom", e);
        }
    }

    public List<Support> findAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Support> query = session.createQuery("FROM Support s ORDER BY s.nom", Support.class);
            return query.list();
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la récupération de tous les supports", e);
        }
    }

    public List<String> findAllNoms() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<String> query = session.createQuery("SELECT s.nom FROM Support s ORDER BY s.nom", String.class);
            return query.list();
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la récupération des noms de supports", e);
        }
    }

    public boolean existsByNom(String nom) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Long> query = session.createQuery(
                "SELECT COUNT(s) FROM Support s WHERE s.nom = :nom", 
                Long.class
            );
            query.setParameter("nom", nom);
            return query.uniqueResult() > 0;
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la vérification de l'existence du support", e);
        }
    }

    public long count() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Long> query = session.createQuery("SELECT COUNT(s) FROM Support s", Long.class);
            return query.uniqueResult();
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors du comptage des supports", e);
        }
    }
} 