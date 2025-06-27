package fr.ynov.collection.controller;

import fr.ynov.collection.model.JeuVideo;
import fr.ynov.collection.model.Support;
import fr.ynov.collection.repository.JeuVideoRepository;
import fr.ynov.collection.repository.SupportRepository;
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
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class JeuxController implements Initializable {

    @FXML private ListView<JeuVideo> jeuxListView;
    @FXML private VBox jeuPreviewBox;
    @FXML private Label previewTitleLabel;
    @FXML private Label previewEditeurLabel;
    @FXML private Label previewDeveloppeurLabel;
    @FXML private Label previewAnneeLabel;
    @FXML private Label previewSupportLabel;
    @FXML private Label previewNoteLabel;
    @FXML private TextField titreField;
    @FXML private TextField editeurField;
    @FXML private TextField developpeurField;
    @FXML private TextField noteField;
    @FXML private TextField jaquetteField;
    @FXML private DatePicker datePicker;
    @FXML private ChoiceBox<String> supportChoiceBox;
    @FXML private Button addButton;
    @FXML private Button editButton;
    @FXML private Button deleteButton;
    @FXML private Label statusLabel;
    @FXML private TextField searchField;
    @FXML private ChoiceBox<String> filterChoiceBox;
    @FXML private ChoiceBox<String> sortChoiceBox;
    @FXML private ImageView jaquetteImageView;

    private JeuVideoRepository jeuVideoRepository;
    private SupportRepository supportRepository;
    private ObservableList<JeuVideo> jeuxList;
    private FilteredList<JeuVideo> filteredJeuxList;
    private JeuVideo selectedJeu;
    private boolean isEditMode = false;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initializeRepositories();
        initializeUI();
        loadData();
        setupEventHandlers();
    }

    private void initializeRepositories() {
        jeuVideoRepository = new JeuVideoRepository();
        supportRepository = new SupportRepository();
    }

    private void initializeUI() {
        // Initialiser les listes
        jeuxList = FXCollections.observableArrayList();
        filteredJeuxList = new FilteredList<>(jeuxList, s -> true);
        jeuxListView.setItems(filteredJeuxList);

        // Initialiser les ChoiceBox
        filterChoiceBox.getItems().addAll("Tous", "PC", "PS5", "Switch", "Xbox", "Mobile");
        filterChoiceBox.setValue("Tous");

        sortChoiceBox.getItems().addAll("Titre", "Année", "Note", "Éditeur");
        sortChoiceBox.setValue("Titre");

        // Configurer la ListView
        jeuxListView.setCellFactory(param -> new ListCell<JeuVideo>() {
            @Override
            protected void updateItem(JeuVideo item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item.getTitre() + " (" + item.getAnneeSortie() + ")");
                }
            }
        });

        // Désactiver les boutons initialement
        editButton.setDisable(true);
        deleteButton.setDisable(true);
    }

    private void loadData() {
        try {
            List<JeuVideo> jeux = jeuVideoRepository.findAll();
            jeuxList.clear();
            jeuxList.addAll(jeux);
            updateStatus("Données chargées avec succès");
        } catch (Exception e) {
            showError("Erreur lors du chargement des données", e.getMessage());
        }
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

        // Recherche
        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredJeuxList.setPredicate(jeu -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                return jeu.getTitre().toLowerCase().contains(lowerCaseFilter) ||
                       jeu.getEditeur().toLowerCase().contains(lowerCaseFilter) ||
                       jeu.getDeveloppeur().toLowerCase().contains(lowerCaseFilter);
            });
        });

        // Filtres
        filterChoiceBox.setOnAction(e -> applyFilters());
        sortChoiceBox.setOnAction(e -> applySorting());
    }

    @FXML
    private void onAdd() {
        if (isEditMode) {
            // Mode édition - sauvegarder les modifications
            saveJeu();
        } else {
            // Mode ajout - valider et ajouter
            if (validateForm()) {
                JeuVideo nouveauJeu = createJeuFromForm();
                try {
                    jeuVideoRepository.save(nouveauJeu);
                    jeuxList.add(nouveauJeu);
                    clearForm();
                    updateStatus("Jeu ajouté avec succès");
                } catch (Exception e) {
                    showError("Erreur lors de l'ajout", e.getMessage());
                }
            }
        }
    }

    @FXML
    private void onEdit() {
        if (selectedJeu != null) {
            isEditMode = true;
            populateForm(selectedJeu);
            addButton.setText("Sauvegarder");
            editButton.setText("Annuler");
            updateStatus("Mode édition activé");
        }
    }

    @FXML
    private void onDelete() {
        if (selectedJeu != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation de suppression");
            alert.setHeaderText("Supprimer le jeu");
            alert.setContentText("Êtes-vous sûr de vouloir supprimer \"" + selectedJeu.getTitre() + "\" ?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                try {
                    jeuVideoRepository.delete(selectedJeu.getId());
                    jeuxList.remove(selectedJeu);
                    clearForm();
                    updateStatus("Jeu supprimé avec succès");
                } catch (Exception e) {
                    showError("Erreur lors de la suppression", e.getMessage());
                }
            }
        }
    }

    @FXML
    private void onAbout() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("À propos");
        alert.setHeaderText("Gestionnaire de Collection de Jeux Vidéo");
        alert.setContentText("Version 1.0\n\nApplication de gestion de collection de jeux vidéo\nDéveloppée avec JavaFX, Hibernate et SQLite");
        alert.showAndWait();
    }

    private void saveJeu() {
        if (validateForm()) {
            try {
                JeuVideo jeuModifie = createJeuFromForm();
                jeuModifie.setId(selectedJeu.getId());
                jeuVideoRepository.update(jeuModifie);
                
                // Mettre à jour la liste
                int index = jeuxList.indexOf(selectedJeu);
                jeuxList.set(index, jeuModifie);
                
                exitEditMode();
                updateStatus("Jeu modifié avec succès");
            } catch (Exception e) {
                showError("Erreur lors de la modification", e.getMessage());
            }
        }
    }

    private void exitEditMode() {
        isEditMode = false;
        addButton.setText("Ajouter");
        editButton.setText("Modifier");
        clearForm();
    }

    private void displayJeuDetails(JeuVideo jeu) {
        previewTitleLabel.setText(jeu.getTitre());
        
        // Afficher la jaquette si disponible
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

        // Afficher les détails dans les champs de prévisualisation
        // (Ces champs ne sont pas modifiables en mode prévisualisation)
    }

    private void populateForm(JeuVideo jeu) {
        titreField.setText(jeu.getTitre());
        editeurField.setText(jeu.getEditeur());
        developpeurField.setText(jeu.getDeveloppeur());

        if (jeu.getAnneeSortie() > 0) {
            datePicker.setValue(LocalDate.of(jeu.getAnneeSortie(), 1, 1));
        }
        
        if (jeu.getSupport() != null) {
            supportChoiceBox.setValue(jeu.getSupport().getNom());
        }
        
        if (jeu.getNoteMetacritic() != null) {
            noteField.setText(jeu.getNoteMetacritic().toString());
        }
        
        if (jeu.getJaquette() != null) {
            jaquetteField.setText(jeu.getJaquette());
        }
    }

    private void clearForm() {
        titreField.clear();
        editeurField.clear();
        developpeurField.clear();
        noteField.clear();
        jaquetteField.clear();
        datePicker.setValue(null);
        jaquetteImageView.setImage(null);
        selectedJeu = null;
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
        if (datePicker.getValue() == null) {
            showError("Erreur de validation", "L'année de sortie est obligatoire");
            return false;
        }
        if (supportChoiceBox.getValue() == null || supportChoiceBox.getValue().trim().isEmpty()) {
            showError("Erreur de validation", "Le support est obligatoire");
            return false;
        }
        return true;
    }

    private JeuVideo createJeuFromForm() {
        String titre = titreField.getText().trim();
        String editeur = editeurField.getText().trim();
        String developpeur = developpeurField.getText().trim();
        int anneeSortie = datePicker.getValue().getYear();
        String supportNom = supportChoiceBox.getValue().trim();
        String jaquette = jaquetteField.getText().trim();

        // Gérer la note Metacritic
        Integer noteMetacritic = null;
        String noteText = noteField.getText().trim();
        if (!noteText.isEmpty()) {
            try {
                noteMetacritic = Integer.parseInt(noteText);
                if (noteMetacritic < 0 || noteMetacritic > 100) {
                    throw new NumberFormatException("Note invalide");
                }
            } catch (NumberFormatException e) {
                showError("Erreur de validation", "La note Metacritic doit être un nombre entre 0 et 100");
                return null;
            }
        }

        // Créer ou récupérer le support
        Support support = supportRepository.findByNom(supportNom);
        if (support == null) {
            support = new Support(supportNom);
            supportRepository.save(support);
        }

        return new JeuVideo(titre, editeur, developpeur, anneeSortie, support, noteMetacritic, jaquette);
    }

    private void applyFilters() {
        String selectedFilter = filterChoiceBox.getValue();
        filteredJeuxList.setPredicate(jeu -> {
            if ("Tous".equals(selectedFilter)) {
                return true;
            }
            return jeu.getSupport() != null && selectedFilter.equals(jeu.getSupport().getNom());
        });
    }

    private void applySorting() {
        String selectedSort = sortChoiceBox.getValue();
        switch (selectedSort) {
            case "Titre":
                jeuxList.sort((j1, j2) -> j1.getTitre().compareToIgnoreCase(j2.getTitre()));
                break;
            case "Année":
                jeuxList.sort((j1, j2) -> Integer.compare(j1.getAnneeSortie(), j2.getAnneeSortie()));
                break;
            case "Note":
                jeuxList.sort((j1, j2) -> {
                    Integer note1 = j1.getNoteMetacritic() != null ? j1.getNoteMetacritic() : 0;
                    Integer note2 = j2.getNoteMetacritic() != null ? j2.getNoteMetacritic() : 0;
                    return Integer.compare(note2, note1); // Ordre décroissant
                });
                break;
            case "Éditeur":
                jeuxList.sort((j1, j2) -> j1.getEditeur().compareToIgnoreCase(j2.getEditeur()));
                break;
        }
    }

    private void updateStatus(String message) {
        statusLabel.setText(message);
    }

    private void showError(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
