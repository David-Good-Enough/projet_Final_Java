package fr.ynov.collection.controller;

import fr.ynov.collection.model.JeuVideo;
import fr.ynov.collection.model.Support;
import fr.ynov.collection.repository.JeuVideoDao;
import fr.ynov.collection.repository.SupportDao;
import fr.ynov.collection.service.ExportService;
import fr.ynov.collection.service.RawgImporter;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class JeuxController implements Initializable {

    @FXML private ListView<JeuVideo> jeuxListView;
    @FXML private VBox previewBox;
    @FXML private ImageView jaquetteImageView;
    @FXML private Label previewTitleLabel;
    @FXML private Label previewEditeurLabel;
    @FXML private Label previewDeveloppeurLabel;
    @FXML private Label previewAnneeLabel;
    @FXML private Label previewSupportLabel;
    @FXML private Label previewNoteLabel;
    
    @FXML private TextField titreField;
    @FXML private TextField editeurField;
    @FXML private TextField developpeurField;
    @FXML private DatePicker anneeDatePicker;
    @FXML private ComboBox<Support> supportComboBox;
    @FXML private TextField noteField;
    @FXML private TextField jaquetteField;
    
    @FXML private TextField searchField;
    @FXML private ComboBox<Support> filterSupportComboBox;
    @FXML private ComboBox<String> filterAnneeComboBox;
    @FXML private ComboBox<String> filterNoteComboBox;
    
    @FXML private Button addButton;
    @FXML private Button editButton;
    @FXML private Button deleteButton;
    @FXML private Button clearButton;
    @FXML private Button browseImageButton;
    @FXML private Button exportButton;
    
    @FXML private Label statusLabel;

    private final JeuVideoDao jeuVideoDao = new JeuVideoDao();
    private final SupportDao supportDao = new SupportDao();
    private final ExportService exportService = new ExportService();
    private final ObservableList<JeuVideo> jeuxList = FXCollections.observableArrayList();
    private final FilteredList<JeuVideo> filteredJeuxList = new FilteredList<>(jeuxList);
    private JeuVideo selectedJeu;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initializeData();
        setupUI();
        setupEventHandlers();
        loadJeuxDepuisRAWG();
        updateStatus("Prêt");
    }

    private void initializeData() {
        // Initialiser les supports par défaut
        supportDao.initializeDefaultSupports();
        
        // Charger les supports dans les ComboBox
        List<Support> supports = supportDao.findAll();
        supportComboBox.setItems(FXCollections.observableArrayList(supports));
        filterSupportComboBox.setItems(FXCollections.observableArrayList(supports));
        
        // Initialiser les filtres d'année
        ObservableList<String> annees = FXCollections.observableArrayList();
        annees.add("Toutes les années");
        for (int i = 2024; i >= 1980; i--) {
            annees.add(String.valueOf(i));
        }
        filterAnneeComboBox.setItems(annees);
        filterAnneeComboBox.getSelectionModel().selectFirst();
        
        // Initialiser les filtres de note
        ObservableList<String> notes = FXCollections.observableArrayList();
        notes.add("Toutes les notes");
        for (int i = 10; i >= 1; i--) {
            notes.add(i + "+");
        }
        filterNoteComboBox.setItems(notes);
        filterNoteComboBox.getSelectionModel().selectFirst();
    }

    private void setupUI() {
        // Configuration de la ListView
        jeuxListView.setItems(filteredJeuxList);
        jeuxListView.setCellFactory(param -> new ListCell<JeuVideo>() {
            @Override
            protected void updateItem(JeuVideo item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item.getTitre() + " (" + item.getAnneeSortie() + ") - " + item.getSupport().getNom());
                }
            }
        });
        
        // Configuration des champs
        anneeDatePicker.setEditable(false);
        supportComboBox.setEditable(true);
        
        // Désactiver les boutons au démarrage
        editButton.setDisable(true);
        deleteButton.setDisable(true);
    }

    private void setupEventHandlers() {
        // Sélection dans la liste
        jeuxListView.getSelectionModel().selectedItemProperty().addListener(
            (observable, oldValue, newValue) -> {
                selectedJeu = newValue;
                if (newValue != null) {
                    displayJeuDetails(newValue);
                    editButton.setDisable(false);
                    deleteButton.setDisable(false);
                } else {
                    clearForm();
                    editButton.setDisable(true);
                    deleteButton.setDisable(true);
                }
            }
        );

        // Recherche en temps réel
        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            applyFilters();
        });

        // Filtres
        filterSupportComboBox.valueProperty().addListener((observable, oldValue, newValue) -> {
            applyFilters();
        });

        filterAnneeComboBox.valueProperty().addListener((observable, oldValue, newValue) -> {
            applyFilters();
        });

        filterNoteComboBox.valueProperty().addListener((observable, oldValue, newValue) -> {
            applyFilters();
        });
    }

    @FXML
    private void onAdd() {
        if (validateForm()) {
            try {
                JeuVideo nouveauJeu = createJeuFromForm();
                jeuVideoDao.save(nouveauJeu);
                loadJeux();
                clearForm();
                updateStatus("Jeu ajouté avec succès");
            } catch (Exception e) {
                showError("Erreur lors de l'ajout", e.getMessage());
            }
        }
    }

    @FXML
    private void onEdit() {
        if (selectedJeu == null) {
            showError("Erreur", "Aucun jeu sélectionné");
            return;
        }
        
        if (validateForm()) {
            try {
                updateJeuFromForm(selectedJeu);
                jeuVideoDao.update(selectedJeu);
                loadJeux();
                clearForm();
                updateStatus("Jeu modifié avec succès");
            } catch (Exception e) {
                showError("Erreur lors de la modification", e.getMessage());
            }
        }
    }

    @FXML
    private void onDelete() {
        if (selectedJeu == null) {
            showError("Erreur", "Aucun jeu sélectionné");
            return;
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation de suppression");
        alert.setHeaderText("Supprimer le jeu");
        alert.setContentText("Êtes-vous sûr de vouloir supprimer \"" + selectedJeu.getTitre() + "\" ?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            try {
                jeuVideoDao.delete(selectedJeu);
                loadJeux();
                clearForm();
                updateStatus("Jeu supprimé avec succès");
            } catch (Exception e) {
                showError("Erreur lors de la suppression", e.getMessage());
            }
        }
    }

    @FXML
    private void onClear() {
        clearForm();
        jeuxListView.getSelectionModel().clearSelection();
    }

    @FXML
    private void onBrowseImage() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Sélectionner une image");
        fileChooser.getExtensionFilters().addAll(
            new FileChooser.ExtensionFilter("Images", "*.png", "*.jpg", "*.jpeg", "*.gif", "*.bmp")
        );
        
        File selectedFile = fileChooser.showOpenDialog(getStage());
        if (selectedFile != null) {
            jaquetteField.setText(selectedFile.toURI().toString());
        }
    }

    @FXML
    private void onExport() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Exporter la collection");
        fileChooser.getExtensionFilters().addAll(
            new FileChooser.ExtensionFilter("JSON", "*.json")
        );
        
        // Nom de fichier par défaut avec timestamp
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
        fileChooser.setInitialFileName("collection_jeux_" + timestamp + ".json");
        
        File selectedFile = fileChooser.showSaveDialog(getStage());
        if (selectedFile != null) {
            try {
                exportService.exportToJsonWithStats(selectedFile.getAbsolutePath());
                updateStatus("Collection exportée avec succès vers " + selectedFile.getName());
                
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Export réussi");
                alert.setHeaderText("Collection exportée");
                alert.setContentText("La collection a été exportée avec succès vers :\n" + selectedFile.getAbsolutePath());
                alert.showAndWait();
                
            } catch (Exception e) {
                showError("Erreur lors de l'export", e.getMessage());
            }
        }
    }

    @FXML
    private void onImportRAWG() {
        try {
            RawgImporter importer = new RawgImporter();
            List<JeuVideo> importedGames = importer.fetchTopGames();

            for (JeuVideo jeu : importedGames) {
                Support support = supportDao.findOrCreateByNom(jeu.getSupport().getNom());
                jeu.setSupport(support);
                jeuVideoDao.save(jeu);
            }

            loadJeux();
            updateStatus(importedGames.size() + " jeux importés via RAWG");
            showInformation("Import réussi", importedGames.size() + " jeux ont été ajoutés à votre collection.");
        } catch (Exception e) {
            showError("Erreur lors de l'import", e.getMessage());
        }
    }

    private void loadJeuxDepuisRAWG() {
        try {
            RawgImporter importer = new RawgImporter();
            List<JeuVideo> jeuxRAWG = importer.fetchTopGames();

            for (JeuVideo jeu : jeuxRAWG) {
                Support support = supportDao.findOrCreateByNom(jeu.getSupport().getNom());
                jeu.setSupport(support);
                jeuVideoDao.save(jeu);
            }

            loadJeux(); // recharge depuis la BDD
            updateStatus(jeuxRAWG.size() + " jeux importés automatiquement depuis RAWG");
        } catch (Exception e) {
            showError("Erreur lors de l'import RAWG", e.getMessage());
        }
    }

    private void showInformation(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private void loadJeux() {
        try {
            List<JeuVideo> jeux = jeuVideoDao.findAll();
            jeuxList.clear();
            jeuxList.addAll(jeux);
            updateStatus(jeux.size() + " jeux chargés");
        } catch (Exception e) {
            showError("Erreur lors du chargement", e.getMessage());
        }
    }

    private void displayJeuDetails(JeuVideo jeu) {
        previewTitleLabel.setText(jeu.getTitre());
        previewEditeurLabel.setText("Éditeur: " + jeu.getEditeur());
        previewDeveloppeurLabel.setText("Développeur: " + jeu.getDeveloppeur());
        previewAnneeLabel.setText("Année: " + jeu.getAnneeSortie());
        previewSupportLabel.setText("Support: " + jeu.getSupport().getNom());
        previewNoteLabel.setText("Note Metacritic: " + 
            (jeu.getNoteMetacritic() != null ? jeu.getNoteMetacritic() + "/10" : "Non renseignée"));

        // Charger l'image de la jaquette
        if (jeu.getJaquette() != null && !jeu.getJaquette().isEmpty()) {
            try {
                Image image = new Image(jeu.getJaquette());
                jaquetteImageView.setImage(image);
            } catch (Exception e) {
                jaquetteImageView.setImage(null);
            }
        } else {
            jaquetteImageView.setImage(null);
        }

        // Remplir le formulaire
        titreField.setText(jeu.getTitre());
        editeurField.setText(jeu.getEditeur());
        developpeurField.setText(jeu.getDeveloppeur());
        anneeDatePicker.setValue(LocalDate.of(jeu.getAnneeSortie(), 1, 1));
        supportComboBox.setValue(jeu.getSupport());
        noteField.setText(jeu.getNoteMetacritic() != null ? jeu.getNoteMetacritic().toString() : "");
        jaquetteField.setText(jeu.getJaquette() != null ? jeu.getJaquette() : "");
    }

    private void clearForm() {
        titreField.clear();
        editeurField.clear();
        developpeurField.clear();
        anneeDatePicker.setValue(null);
        supportComboBox.setValue(null);
        noteField.clear();
        jaquetteField.clear();
        jaquetteImageView.setImage(null);
        
        previewTitleLabel.setText("");
        previewEditeurLabel.setText("");
        previewDeveloppeurLabel.setText("");
        previewAnneeLabel.setText("");
        previewSupportLabel.setText("");
        previewNoteLabel.setText("");
    }

    private JeuVideo createJeuFromForm() {
        JeuVideo jeu = new JeuVideo();
        updateJeuFromForm(jeu);
        return jeu;
    }

    private void updateJeuFromForm(JeuVideo jeu) {
        jeu.setTitre(titreField.getText().trim());
        jeu.setEditeur(editeurField.getText().trim());
        jeu.setDeveloppeur(developpeurField.getText().trim());
        
        if (anneeDatePicker.getValue() != null) {
            jeu.setAnneeSortie(anneeDatePicker.getValue().getYear());
        }
        
        Object supportValue = supportComboBox.getValue();
        Support support = null;
        if (supportValue instanceof Support) {
            support = (Support) supportValue;
        } else if (supportValue instanceof String) {
            support = new SupportDao().findOrCreateByNom((String) supportValue);
        }
        jeu.setSupport(support);
        
        String noteText = noteField.getText().trim();
        if (!noteText.isEmpty()) {
            try {
                jeu.setNoteMetacritic(Double.parseDouble(noteText));
            } catch (NumberFormatException e) {
                jeu.setNoteMetacritic(null);
            }
        } else {
            jeu.setNoteMetacritic(null);
        }
        
        String jaquetteText = jaquetteField.getText().trim();
        jeu.setJaquette(jaquetteText.isEmpty() ? null : jaquetteText);
    }

    private boolean validateForm() {
        if (titreField.getText().trim().isEmpty()) {
            showError("Erreur de validation", "Le titre est obligatoire");
            return false;
        }
        if (editeurField.getText().trim().isEmpty()) {
            showError("Erreur de validation", "L'éditeur est obligatoire");
            return false;
        }
        if (developpeurField.getText().trim().isEmpty()) {
            showError("Erreur de validation", "Le développeur est obligatoire");
            return false;
        }
        if (anneeDatePicker.getValue() == null) {
            showError("Erreur de validation", "L'année de sortie est obligatoire");
            return false;
        }
        if (supportComboBox.getValue() == null) {
            showError("Erreur de validation", "Le support est obligatoire");
            return false;
        }
        return true;
    }

    private void applyFilters() {
        filteredJeuxList.setPredicate(jeu -> {
            // Filtre par titre (recherche)
            String searchText = searchField.getText().toLowerCase();
            if (!searchText.isEmpty() && !jeu.getTitre().toLowerCase().contains(searchText)) {
                return false;
            }
            
            // Filtre par support
            Support selectedSupport = filterSupportComboBox.getValue();
            if (selectedSupport != null && !jeu.getSupport().equals(selectedSupport)) {
                return false;
            }
            
            // Filtre par année
            String selectedAnnee = filterAnneeComboBox.getValue();
            if (selectedAnnee != null && !selectedAnnee.equals("Toutes les années")) {
                try {
                    int annee = Integer.parseInt(selectedAnnee);
                    if (jeu.getAnneeSortie() != annee) {
                        return false;
                    }
                } catch (NumberFormatException e) {
                    // Ignorer si l'année n'est pas un nombre
                }
            }
            
            // Filtre par note
            String selectedNote = filterNoteComboBox.getValue();
            if (selectedNote != null && !selectedNote.equals("Toutes les notes")) {
                try {
                    int noteMin = Integer.parseInt(selectedNote.replace("+", ""));
                    if (jeu.getNoteMetacritic() == null || jeu.getNoteMetacritic() < noteMin) {
                        return false;
                    }
                } catch (NumberFormatException e) {
                    // Ignorer si la note n'est pas un nombre
                }
            }
            
            return true;
        });
    }

    private void showError(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private void updateStatus(String message) {
        statusLabel.setText(message);
    }

    private Stage getStage() {
        return (Stage) statusLabel.getScene().getWindow();
    }
} 