package fr.ynov.collection.repository;

import fr.ynov.collection.model.JeuVideo;
import fr.ynov.collection.model.Support;
import fr.ynov.collection.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;
import java.util.Optional;

public class JeuVideoDao {

    public void save(JeuVideo jeuVideo) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.persist(jeuVideo);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Erreur lors de la sauvegarde du jeu vidéo", e);
        }
    }

    public void update(JeuVideo jeuVideo) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.merge(jeuVideo);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Erreur lors de la mise à jour du jeu vidéo", e);
        }
    }

    public void delete(JeuVideo jeuVideo) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.remove(jeuVideo);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Erreur lors de la suppression du jeu vidéo", e);
        }
    }

    public Optional<JeuVideo> findById(int id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            JeuVideo jeuVideo = session.get(JeuVideo.class, id);
            return Optional.ofNullable(jeuVideo);
        }
    }

    public List<JeuVideo> findAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<JeuVideo> query = session.createQuery("FROM JeuVideo j ORDER BY j.titre", JeuVideo.class);
            return query.list();
        }
    }

    public List<JeuVideo> findByTitre(String titre) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<JeuVideo> query = session.createQuery(
                "FROM JeuVideo j WHERE LOWER(j.titre) LIKE LOWER(:titre) ORDER BY j.titre", 
                JeuVideo.class
            );
            query.setParameter("titre", "%" + titre + "%");
            return query.list();
        }
    }

    public List<JeuVideo> findBySupport(Support support) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<JeuVideo> query = session.createQuery(
                "FROM JeuVideo j WHERE j.support = :support ORDER BY j.titre", 
                JeuVideo.class
            );
            query.setParameter("support", support);
            return query.list();
        }
    }

    public List<JeuVideo> findByAnneeSortie(int annee) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<JeuVideo> query = session.createQuery(
                "FROM JeuVideo j WHERE j.anneeSortie = :annee ORDER BY j.titre", 
                JeuVideo.class
            );
            query.setParameter("annee", annee);
            return query.list();
        }
    }

    public List<JeuVideo> findByAnneeSortieRange(int anneeMin, int anneeMax) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<JeuVideo> query = session.createQuery(
                "FROM JeuVideo j WHERE j.anneeSortie BETWEEN :anneeMin AND :anneeMax ORDER BY j.anneeSortie, j.titre", 
                JeuVideo.class
            );
            query.setParameter("anneeMin", anneeMin);
            query.setParameter("anneeMax", anneeMax);
            return query.list();
        }
    }

    public List<JeuVideo> findByNoteMetacriticMin(double noteMin) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<JeuVideo> query = session.createQuery(
                "FROM JeuVideo j WHERE j.noteMetacritic >= :noteMin ORDER BY j.noteMetacritic DESC, j.titre", 
                JeuVideo.class
            );
            query.setParameter("noteMin", noteMin);
            return query.list();
        }
    }

    public List<JeuVideo> searchAdvanced(String titre, Support support, Integer anneeMin, Integer anneeMax, Double noteMin) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            StringBuilder hql = new StringBuilder("FROM JeuVideo j WHERE 1=1");
            
            if (titre != null && !titre.trim().isEmpty()) {
                hql.append(" AND LOWER(j.titre) LIKE LOWER(:titre)");
            }
            if (support != null) {
                hql.append(" AND j.support = :support");
            }
            if (anneeMin != null) {
                hql.append(" AND j.anneeSortie >= :anneeMin");
            }
            if (anneeMax != null) {
                hql.append(" AND j.anneeSortie <= :anneeMax");
            }
            if (noteMin != null) {
                hql.append(" AND j.noteMetacritic >= :noteMin");
            }
            
            hql.append(" ORDER BY j.titre");
            
            Query<JeuVideo> query = session.createQuery(hql.toString(), JeuVideo.class);
            
            if (titre != null && !titre.trim().isEmpty()) {
                query.setParameter("titre", "%" + titre + "%");
            }
            if (support != null) {
                query.setParameter("support", support);
            }
            if (anneeMin != null) {
                query.setParameter("anneeMin", anneeMin);
            }
            if (anneeMax != null) {
                query.setParameter("anneeMax", anneeMax);
            }
            if (noteMin != null) {
                query.setParameter("noteMin", noteMin);
            }
            
            return query.list();
        }
    }
}
