package fr.ynov.collection;

import fr.ynov.collection.model.JeuVideo;
import fr.ynov.collection.model.Support;
import fr.ynov.collection.repository.JeuVideoDao;
import fr.ynov.collection.repository.SupportDao;
import fr.ynov.collection.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;

/**
 * Classe de test pour vérifier rapidement les fonctionnalités de base
 * À exécuter avant de lancer l'application JavaFX
 */
public class TestApplication {
    
    public static void main(String[] args) {
        System.out.println("🧪 Test de l'application Gestionnaire de Collection de Jeux Vidéo");
        System.out.println("=" .repeat(60));
        
        try {
            // Test 1: Initialisation Hibernate
            testHibernateInitialization();
            
            // Test 2: Gestion des supports
            testSupportManagement();
            
            // Test 3: CRUD des jeux vidéo
            testJeuVideoCRUD();
            
            // Test 4: Recherche et filtrage
            testSearchAndFilter();
            
            System.out.println("\n🎉 Tous les tests sont passés avec succès !");
            System.out.println("✅ L'application est prête à être lancée.");
            
        } catch (Exception e) {
            System.err.println("\n❌ Erreur lors des tests :");
            e.printStackTrace();
        } finally {
            // Fermer Hibernate
            HibernateUtil.shutdown();
        }
    }
    
    private static void testHibernateInitialization() {
        System.out.println("\n1️⃣ Test d'initialisation d'Hibernate...");
        
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        
        System.out.println("✅ SessionFactory créée avec succès");
        System.out.println("✅ Session ouverte avec succès");
        
        session.close();
        System.out.println("✅ Session fermée avec succès");
    }
    
    private static void testSupportManagement() {
        System.out.println("\n2️⃣ Test de gestion des supports...");
        
        SupportDao supportDao = new SupportDao();
        
        // Initialiser les supports par défaut
        supportDao.initializeDefaultSupports();
        System.out.println("✅ Supports par défaut initialisés");
        
        // Récupérer tous les supports
        List<Support> supports = supportDao.findAll();
        System.out.println("✅ " + supports.size() + " supports trouvés :");
        supports.forEach(support -> System.out.println("   - " + support.getNom()));
        
        // Tester la création d'un nouveau support
        Support newSupport = supportDao.findOrCreateByNom("Test Support");
        System.out.println("✅ Nouveau support créé : " + newSupport.getNom());
    }
    
    private static void testJeuVideoCRUD() {
        System.out.println("\n3️⃣ Test CRUD des jeux vidéo...");
        
        JeuVideoDao jeuVideoDao = new JeuVideoDao();
        SupportDao supportDao = new SupportDao();
        
        // Récupérer un support pour le test
        Support support = supportDao.findByNom("PC").orElse(supportDao.findAll().get(0));
        
        // Créer un jeu de test
        JeuVideo testJeu = new JeuVideo();
        testJeu.setTitre("Jeu de Test");
        testJeu.setEditeur("Éditeur Test");
        testJeu.setDeveloppeur("Développeur Test");
        testJeu.setAnneeSortie(2024);
        testJeu.setSupport(support);
        testJeu.setNoteMetacritic(8.5);
        testJeu.setJaquette("https://example.com/test.jpg");
        
        // Test CREATE
        jeuVideoDao.save(testJeu);
        System.out.println("✅ Jeu créé avec succès (ID: " + testJeu.getId() + ")");
        
        // Test READ
        List<JeuVideo> jeux = jeuVideoDao.findAll();
        System.out.println("✅ " + jeux.size() + " jeux trouvés dans la base");
        
        // Test UPDATE
        testJeu.setNoteMetacritic(9.0);
        jeuVideoDao.update(testJeu);
        System.out.println("✅ Jeu mis à jour avec succès");
        
        // Test SEARCH
        List<JeuVideo> searchResults = jeuVideoDao.findByTitre("Test");
        System.out.println("✅ Recherche par titre : " + searchResults.size() + " résultat(s)");
        
        // Test DELETE
        jeuVideoDao.delete(testJeu);
        System.out.println("✅ Jeu supprimé avec succès");
        
        // Vérifier la suppression
        jeux = jeuVideoDao.findAll();
        System.out.println("✅ " + jeux.size() + " jeux restants dans la base");
    }
    
    private static void testSearchAndFilter() {
        System.out.println("\n4️⃣ Test de recherche et filtrage...");
        
        JeuVideoDao jeuVideoDao = new JeuVideoDao();
        SupportDao supportDao = new SupportDao();
        
        // Créer quelques jeux de test
        Support pcSupport = supportDao.findByNom("PC").orElse(supportDao.findAll().get(0));
        Support ps5Support = supportDao.findByNom("PS5").orElse(supportDao.findAll().get(0));
        
        // Jeu 1
        JeuVideo jeu1 = new JeuVideo("Zelda Test", "Nintendo", "Nintendo", 2023, pcSupport);
        jeu1.setNoteMetacritic(9.5);
        jeuVideoDao.save(jeu1);
        
        // Jeu 2
        JeuVideo jeu2 = new JeuVideo("God of War Test", "Sony", "Santa Monica", 2022, ps5Support);
        jeu2.setNoteMetacritic(9.0);
        jeuVideoDao.save(jeu2);
        
        // Jeu 3
        JeuVideo jeu3 = new JeuVideo("Mario Test", "Nintendo", "Nintendo", 2024, pcSupport);
        jeu3.setNoteMetacritic(8.5);
        jeuVideoDao.save(jeu3);
        
        System.out.println("✅ 3 jeux de test créés");
        
        // Test recherche par titre
        List<JeuVideo> zeldaResults = jeuVideoDao.findByTitre("Zelda");
        System.out.println("✅ Recherche 'Zelda' : " + zeldaResults.size() + " résultat(s)");
        
        // Test filtrage par support
        List<JeuVideo> pcResults = jeuVideoDao.findBySupport(pcSupport);
        System.out.println("✅ Jeux sur PC : " + pcResults.size() + " résultat(s)");
        
        // Test filtrage par année
        List<JeuVideo> year2023Results = jeuVideoDao.findByAnneeSortie(2023);
        System.out.println("✅ Jeux de 2023 : " + year2023Results.size() + " résultat(s)");
        
        // Test filtrage par note
        List<JeuVideo> highScoreResults = jeuVideoDao.findByNoteMetacriticMin(9.0);
        System.out.println("✅ Jeux avec note ≥ 9.0 : " + highScoreResults.size() + " résultat(s)");
        
        // Test recherche avancée
        List<JeuVideo> advancedResults = jeuVideoDao.searchAdvanced("Test", pcSupport, 2023, 2024, 9.0);
        System.out.println("✅ Recherche avancée : " + advancedResults.size() + " résultat(s)");
        
        // Nettoyer les jeux de test
        jeuVideoDao.delete(jeu1);
        jeuVideoDao.delete(jeu2);
        jeuVideoDao.delete(jeu3);
        System.out.println("✅ Jeux de test supprimés");
    }
} 