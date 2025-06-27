package fr.ynov.collection.presenter;

import fr.ynov.collection.model.JeuVideo;
import fr.ynov.collection.model.Support;
import fr.ynov.collection.repository.JeuVideoRepository;
import fr.ynov.collection.repository.SupportRepository;
import fr.ynov.collection.utils.HibernateUtil;

import java.util.List;

public class MainPresenter {
    
    private final JeuVideoRepository jeuVideoRepository;
    private final SupportRepository supportRepository;

    public MainPresenter() {
        this.jeuVideoRepository = new JeuVideoRepository();
        this.supportRepository = new SupportRepository();
    }

    public void initialize() {
        // Initialiser Hibernate
        HibernateUtil.getSessionFactory();
        
        // Créer quelques supports par défaut s'ils n'existent pas
        initializeDefaultSupports();
    }

    private void initializeDefaultSupports() {
        String[] defaultSupports = {"PC", "PS5", "Switch", "Xbox", "Mobile"};
        
        for (String supportNom : defaultSupports) {
            if (!supportRepository.existsByNom(supportNom)) {
                Support support = new Support(supportNom);
                supportRepository.save(support);
            }
        }
    }

    // Méthodes pour les jeux vidéo
    public void saveJeuVideo(JeuVideo jeuVideo) {
        jeuVideoRepository.save(jeuVideo);
    }

    public void updateJeuVideo(JeuVideo jeuVideo) {
        jeuVideoRepository.update(jeuVideo);
    }

    public void deleteJeuVideo(Long id) {
        jeuVideoRepository.delete(id);
    }

    public JeuVideo findJeuVideoById(Long id) {
        return jeuVideoRepository.findById(id).orElse(null);
    }

    public List<JeuVideo> findAllJeuxVideo() {
        return jeuVideoRepository.findAll();
    }

    public List<JeuVideo> findJeuxVideoByTitre(String titre) {
        return jeuVideoRepository.findByTitre(titre);
    }

    public List<JeuVideo> findJeuxVideoByEditeur(String editeur) {
        return jeuVideoRepository.findByEditeur(editeur);
    }

    public List<JeuVideo> findJeuxVideoByAnneeSortie(int annee) {
        return jeuVideoRepository.findByAnneeSortie(annee);
    }

    public List<JeuVideo> findJeuxVideoBySupport(String supportNom) {
        return jeuVideoRepository.findBySupport(supportNom);
    }

    public List<JeuVideo> findJeuxVideoByNoteMetacriticRange(int minNote, int maxNote) {
        return jeuVideoRepository.findByNoteMetacriticRange(minNote, maxNote);
    }

    public long countJeuxVideo() {
        return jeuVideoRepository.count();
    }

    // Méthodes pour les supports
    public void saveSupport(Support support) {
        supportRepository.save(support);
    }

    public void updateSupport(Support support) {
        supportRepository.update(support);
    }

    public void deleteSupport(Long id) {
        supportRepository.delete(id);
    }

    public Support findSupportById(Long id) {
        return supportRepository.findById(id).orElse(null);
    }

    public Support findSupportByNom(String nom) {
        return supportRepository.findByNom(nom);
    }

    public List<Support> findAllSupports() {
        return supportRepository.findAll();
    }

    public List<String> findAllSupportNoms() {
        return supportRepository.findAllNoms();
    }

    public boolean supportExistsByNom(String nom) {
        return supportRepository.existsByNom(nom);
    }

    public long countSupports() {
        return supportRepository.count();
    }

    // Méthodes utilitaires
    public Support getOrCreateSupport(String nom) {
        Support support = supportRepository.findByNom(nom);
        if (support == null) {
            support = new Support(nom);
            supportRepository.save(support);
        }
        return support;
    }

    public void cleanup() {
        HibernateUtil.shutdown();
    }
}
