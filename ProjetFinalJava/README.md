# 🎮 Gestionnaire de Collection de Jeux Vidéo

Application JavaFX pour gérer une collection personnelle de jeux vidéo avec persistance des données via Hibernate et SQLite.

## 📋 Fonctionnalités

### Fonctionnalités principales

- ✅ **CRUD complet** : Ajouter, modifier, supprimer et afficher les jeux vidéo
- ✅ **Interface graphique intuitive** : Interface JavaFX avec 3 panneaux (liste, aperçu, formulaire)
- ✅ **Recherche et filtrage** : Recherche par titre, éditeur, développeur
- ✅ **Tri** : Par titre, année, note Metacritic, éditeur
- ✅ **Filtrage par support** : PC, PS5, Switch, Xbox, Mobile
- ✅ **Persistance des données** : Base SQLite avec Hibernate
- ✅ **Gestion des jaquettes** : Support des images locales et URLs

### Informations des jeux

- **Titre** (obligatoire)
- **Éditeur** (obligatoire)
- **Développeur** (obligatoire)
- **Année de sortie** (obligatoire)
- **Support** (obligatoire)
- **Note Metacritic** (optionnelle, 0-100)
- **Jaquette** (optionnelle, image locale ou URL)

## 🛠️ Technologies utilisées

- **Java 17** - Langage de programmation
- **JavaFX 21** - Interface graphique
- **Hibernate 6.5** - ORM pour la persistance
- **SQLite** - Base de données
- **Maven** - Gestion des dépendances

## 📦 Installation et exécution

### Prérequis

- Java 17 ou supérieur
- Maven 3.6 ou supérieur

### Compilation et exécution

1. **Cloner le projet**

   ```bash
   git clone <url-du-repo>
   cd ProjetFinalJava
   ```

2. **Compiler le projet**

   ```bash
   mvn clean compile
   ```

3. **Exécuter l'application**

   ```bash
   mvn javafx:run
   ```

4. **Créer un JAR exécutable**

   ```bash
   mvn clean package
   ```

5. **Exécuter le JAR**
   ```bash
   java -jar target/collection-jeux-video-1.0-SNAPSHOT.jar
   ```

## 🏗️ Architecture

Le projet suit une architecture **MVP (Model-View-Presenter)** :

```
src/main/java/fr/ynov/collection/
├── Main.java                    # Point d'entrée de l'application
├── model/                       # Modèles de données
│   ├── JeuVideo.java           # Entité jeu vidéo
│   └── Support.java            # Entité support
├── view/                        # Vues (JavaFX)
│   └── MainView.java           # Vue principale
├── controller/                  # Contrôleurs JavaFX
│   └── JeuxController.java     # Contrôleur principal
├── presenter/                   # Présentateurs (logique métier)
│   └── MainPresenter.java      # Présentateur principal
├── repository/                  # Couche d'accès aux données
│   ├── JeuVideoRepository.java # Repository jeux vidéo
│   └── SupportRepository.java  # Repository supports
└── utils/                       # Utilitaires
    └── HibernateUtil.java      # Configuration Hibernate
```

## 🗄️ Base de données

La base de données SQLite (`collection.db`) est créée automatiquement au premier lancement avec les tables :

- **jeu_video** : Stockage des informations des jeux
- **support** : Stockage des supports de jeu

### Schéma de la base de données

```sql
-- Table Support
CREATE TABLE support (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    nom VARCHAR(255) NOT NULL
);

-- Table JeuVideo
CREATE TABLE jeu_video (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    titre VARCHAR(255) NOT NULL,
    editeur VARCHAR(255) NOT NULL,
    developpeur VARCHAR(255) NOT NULL,
    annee_sortie INTEGER NOT NULL,
    note_metacritic INTEGER,
    jaquette VARCHAR(500),
    support_id INTEGER,
    FOREIGN KEY (support_id) REFERENCES support(id)
);
```

## 🎯 Utilisation

### Ajouter un jeu

1. Remplir le formulaire dans le panneau de droite
2. Cliquer sur "Ajouter"
3. Le jeu apparaît dans la liste à gauche

### Modifier un jeu

1. Sélectionner un jeu dans la liste
2. Cliquer sur "Modifier"
3. Modifier les champs dans le formulaire
4. Cliquer sur "Sauvegarder"

### Supprimer un jeu

1. Sélectionner un jeu dans la liste
2. Cliquer sur "Supprimer"
3. Confirmer la suppression

### Rechercher des jeux

- Utiliser la barre de recherche pour filtrer par titre, éditeur ou développeur
- Utiliser les filtres pour trier par support
- Utiliser le menu de tri pour ordonner les résultats

## 🔧 Configuration

Le fichier `config.properties` peut être utilisé pour configurer :

- Paramètres de la base de données
- Clés API (pour les fonctionnalités bonus)
- Paramètres d'email (pour les fonctionnalités bonus)

## 🚀 Fonctionnalités bonus (à implémenter)

- [ ] **Scan de code-barres** : Intégration API IGDB/RAWG
- [ ] **Import automatique** : Auto-remplissage via API
- [ ] **Export des données** : JSON, XML, CSV
- [ ] **Site Web local** : Génération HTML de la collection
- [ ] **Email de sauvegarde** : Envoi de la liste par email
- [ ] **Affichage avancé des jaquettes** : Prévisualisation dynamique

## 📝 Notes de développement

### Structure du projet

- **Clean Code** : Noms de classes et méthodes explicites
- **Séparation des responsabilités** : MVP architecture
- **Gestion d'erreurs** : Try-catch appropriés avec messages utilisateur
- **Validation** : Vérification des données avant sauvegarde

### Bonnes pratiques

- Utilisation d'annotations Hibernate pour le mapping ORM
- Gestion des transactions Hibernate
- Interface utilisateur responsive avec JavaFX
- Gestion des événements avec @FXML

## 🤝 Contribution

1. Fork le projet
2. Créer une branche pour votre fonctionnalité
3. Commiter vos changements
4. Pousser vers la branche
5. Ouvrir une Pull Request

## 📄 Licence

Ce projet est développé dans le cadre d'un projet final Java à Ynov.

## 👥 Auteurs

- Équipe de développement Ynov
- Projet final Java - Gestionnaire de Collection de Jeux Vidéo

---

**Version** : 1.0-SNAPSHOT  
**Date** : 2025  
**Statut** : En développement
