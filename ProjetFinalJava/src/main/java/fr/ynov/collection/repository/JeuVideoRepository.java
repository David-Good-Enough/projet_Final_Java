package fr.ynov.collection.repository;

import fr.ynov.collection.model.JeuVideo;
import fr.ynov.collection.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;
import java.util.Optional;

public class JeuVideoRepository {

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

    public void delete(Long id) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            JeuVideo jeuVideo = session.get(JeuVideo.class, id);
            if (jeuVideo != null) {
                session.remove(jeuVideo);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Erreur lors de la suppression du jeu vidéo", e);
        }
    }

    public Optional<JeuVideo> findById(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            JeuVideo jeuVideo = session.get(JeuVideo.class, id);
            return Optional.ofNullable(jeuVideo);
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la recherche du jeu vidéo", e);
        }
    }

    public List<JeuVideo> findAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<JeuVideo> query = session.createQuery("FROM JeuVideo j ORDER BY j.titre", JeuVideo.class);
            return query.list();
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la récupération de tous les jeux vidéo", e);
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
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la recherche par titre", e);
        }
    }

    public List<JeuVideo> findByEditeur(String editeur) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<JeuVideo> query = session.createQuery(
                "FROM JeuVideo j WHERE LOWER(j.editeur) LIKE LOWER(:editeur) ORDER BY j.titre", 
                JeuVideo.class
            );
            query.setParameter("editeur", "%" + editeur + "%");
            return query.list();
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la recherche par éditeur", e);
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
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la recherche par année", e);
        }
    }

    public List<JeuVideo> findBySupport(String supportNom) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<JeuVideo> query = session.createQuery(
                "FROM JeuVideo j WHERE j.support.nom = :supportNom ORDER BY j.titre", 
                JeuVideo.class
            );
            query.setParameter("supportNom", supportNom);
            return query.list();
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la recherche par support", e);
        }
    }

    public List<JeuVideo> findByNoteMetacriticRange(int minNote, int maxNote) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<JeuVideo> query = session.createQuery(
                "FROM JeuVideo j WHERE j.noteMetacritic BETWEEN :minNote AND :maxNote ORDER BY j.noteMetacritic DESC", 
                JeuVideo.class
            );
            query.setParameter("minNote", minNote);
            query.setParameter("maxNote", maxNote);
            return query.list();
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la recherche par note Metacritic", e);
        }
    }

    public long count() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Long> query = session.createQuery("SELECT COUNT(j) FROM JeuVideo j", Long.class);
            return query.uniqueResult();
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors du comptage des jeux vidéo", e);
        }
    }
} 