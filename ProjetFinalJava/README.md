# 🎮 Gestionnaire de Collection de Jeux Vidéo

Application JavaFX moderne pour gérer votre collection personnelle de jeux vidéo avec base de données SQLite, import depuis l'API RAWG, et fonctionnalités d'export.

## 📋 Table des Matières

- [Fonctionnalités](#-fonctionnalités)
- [Technologies Utilisées](#-technologies-utilisées)
- [Prérequis](#-prérequis)
- [Installation](#-installation)
- [Lancement du Projet](#-lancement-du-projet)
- [Structure du Projet](#-structure-du-projet)
- [Base de Données](#-base-de-données)
- [Fonctionnalités Détaillées](#-fonctionnalités-détaillées)
- [Configuration](#-configuration)
- [Développement](#-développement)

## ✨ Fonctionnalités

- **Gestion de Collection** : Ajout, modification, suppression et consultation de jeux vidéo
- **Supports Multiples** : Gestion de différents supports (PC, PlayStation, Xbox, Nintendo, etc.)
- **Import RAWG** : Import automatique de jeux depuis l'API RAWG avec métadonnées
- **Export JSON** : Export de votre collection au format JSON
- **Envoi d'Emails** : Partage de votre collection par email
- **Interface Moderne** : Interface graphique JavaFX intuitive et responsive
- **Persistance SQLite** : Base de données légère et portable
- **Notes Metacritic** : Affichage des notes critiques des jeux
- **Jaquettes** : Gestion des images de jaquettes

## 🛠 Technologies Utilisées

- **Java 21** - Langage de programmation
- **JavaFX 21** - Interface utilisateur
- **Hibernate 6.5.2** - ORM pour la persistance
- **SQLite** - Base de données locale
- **Maven** - Gestionnaire de dépendances
- **Jackson/Gson** - Sérialisation JSON
- **Jakarta Mail** - Envoi d'emails
- **RAWG API** - Import de données de jeux

## 📦 Prérequis

- **Java 21** ou version supérieure
- **Maven 3.6+** 
- **Git** (pour cloner le projet)

## 🚀 Installation

1. **Cloner le repository**
```bash
git clone <url-du-repository>
cd projet_Final_Java
```

2. **Naviguer vers le répertoire du projet Maven**
```bash
cd ProjetFinalJava
```

3. **Installer les dépendances**
```bash
mvn clean install
```

## ▶️ Lancement du Projet

**⚠️ IMPORTANT : Pour lancer le projet, vous devez exécuter la classe `Launcher` :**

### Méthode 1 : Via Maven (Recommandée)
```bash
mvn javafx:run
```

### Méthode 2 : Via IDE
- Ouvrez le projet dans votre IDE (IntelliJ IDEA, Eclipse, VS Code)
- Exécutez la classe `fr.ynov.collection.Launcher`
- **Ne pas** lancer directement la classe `Main` (problèmes de modules JavaFX)

### Méthode 3 : JAR Exécutable
```bash
# Construire le JAR
mvn clean package

# Exécuter le JAR (depuis le répertoire ProjetFinalJava)
java -jar target/collection-jeux-video-1.0.0.jar
```

## 📁 Structure du Projet

```
ProjetFinalJava/
├── src/main/java/fr/ynov/collection/
│   ├── Launcher.java              # 🚀 POINT D'ENTRÉE - Classe à exécuter
│   ├── Main.java                  # Application JavaFX principale
│   ├── controller/                # Contrôleurs JavaFX
│   │   └── JeuxController.java
│   ├── model/                     # Entités JPA/Hibernate
│   │   ├── JeuVideo.java          # Entité Jeu Vidéo
│   │   └── Support.java           # Entité Support (PC, Console, etc.)
│   ├── repository/                # Couche d'accès aux données (DAO)
│   │   ├── JeuVideoDao.java
│   │   └── SupportDao.java
│   ├── service/                   # Services métier
│   │   ├── EmailSender.java       # Service d'envoi d'emails
│   │   ├── ExportService.java     # Service d'export JSON
│   │   └── RawgImporter.java      # Service d'import RAWG
│   ├── presenter/                 # Couche de présentation (MVP)
│   │   └── MainPresenter.java
│   ├── utils/                     # Utilitaires
│   │   └── HibernateUtil.java     # Configuration Hibernate
│   └── view/                      # Vues JavaFX
│       └── MainView.java
├── src/main/resources/
│   ├── hibernate.cfg.xml          # Configuration Hibernate
│   └── jeux-view.fxml            # Interface FXML
├── pom.xml                        # Configuration Maven
└── collection.db                  # Base de données SQLite (générée automatiquement)
```

## 🗄 Base de Données

L'application utilise **SQLite** avec **Hibernate** pour la persistance :

- **Fichier** : `collection.db` (créé automatiquement à la racine)
- **Tables** : `jeux_video`, `support`
- **Configuration** : Auto-génération des tables via Hibernate (`hbm2ddl.auto=update`)

### Modèle de Données

**JeuVideo**
- `id` (PK, auto-incrémenté)
- `titre` (obligatoire)
- `editeur` (obligatoire)
- `developpeur` (obligatoire)
- `annee_sortie` (obligatoire)
- `support_id` (FK vers Support)
- `note_metacritic` (optionnel)
- `jaquette_url` (optionnel)

**Support**
- `id` (PK, auto-incrémenté)
- `nom` (obligatoire, unique)

## 🎯 Fonctionnalités Détaillées

### Gestion de Collection
- **CRUD complet** : Créer, lire, modifier, supprimer des jeux
- **Recherche et filtrage** par titre, éditeur, développeur, support
- **Tri** par différents critères (titre, année, note, etc.)

### Import RAWG
- **API Integration** : Import automatique depuis la base RAWG
- **Métadonnées complètes** : Titre, éditeur, développeur, année, note, jaquette
- **Gestion des erreurs** : Validation et nettoyage des données importées

### Export et Partage
- **Export JSON** : Sauvegarde de la collection au format JSON
- **Envoi par Email** : Partage de la collection via Jakarta Mail
- **Formats multiples** : Support d'exports dans différents formats

## ⚙️ Configuration

### Configuration Hibernate
Modifiez `src/main/resources/hibernate.cfg.xml` pour personnaliser :
- Emplacement de la base de données
- Niveau de logging SQL
- Dialecte de base de données

### Configuration Email
Pour utiliser la fonctionnalité d'envoi d'emails, configurez les paramètres SMTP dans `EmailSender.java`.

### Configuration RAWG API
Pour utiliser l'import RAWG, obtenez une clé API gratuite sur [RAWG.io](https://rawg.io/apidocs) et configurez-la dans `RawgImporter.java`.

## 👨‍💻 Développement

### Commandes Maven Utiles

```bash
# Compilation
mvn compile

# Tests
mvn test

# Packaging
mvn package

# Nettoyage
mvn clean

# Lancement de l'application
mvn javafx:run

# Compilation + packaging
mvn clean package
```

## 📝 Notes Importantes

1. **⚠️ Lancez toujours via `Launcher.java`** - évite les problèmes de modules JavaFX
2. La base de données SQLite est créée automatiquement au premier lancement
3. L'application nécessite Java 21 minimum
4. Les dépendances JavaFX sont gérées par Maven

---