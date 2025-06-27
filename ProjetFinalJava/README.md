# 🎮 Gestionnaire de Collection de Jeux Vidéo

Application JavaFX pour gérer une collection personnelle de jeux vidéo avec persistance des données via Hibernate et H2 Database.

## 📋 Fonctionnalités

### ✅ Fonctionnalités de base
- **Gestion CRUD complète** : Ajouter, modifier, supprimer des jeux vidéo
- **Interface graphique intuitive** : JavaFX avec FXML
- **Persistance des données** : Base H2 Database avec Hibernate
- **Recherche et filtrage** : Par titre, support, année, note
- **Affichage des jaquettes** : Support des images locales et URLs

### 🎯 Informations des jeux
- Titre du jeu
- Éditeur
- Développeur
- Année de sortie
- Support (PC, PS5, Switch, etc.)
- Note Metacritic (optionnelle)
- Jaquette (image locale ou URL)

## 🛠️ Technologies utilisées

- **Java 21** : Langage de programmation
- **JavaFX 21** : Interface graphique
- **Hibernate 6.5** : ORM pour la persistance
- **H2 Database** : Base de données embarquée
- **Maven** : Gestion des dépendances et build

## 📦 Installation et exécution

### Prérequis
- Java 21 ou supérieur
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

3. **Exécuter avec Maven**
   ```bash
   mvn javafx:run
   ```

4. **Créer un JAR exécutable**
   ```bash
   mvn clean package
   ```

5. **Exécuter le JAR**
   ```bash
   java -jar target/collection-jeux-video-1.0.0.jar
   ```

## 🏗️ Architecture

Le projet suit l'architecture **MVP (Model-View-Presenter)** :

```
src/main/java/fr/ynov/collection/
├── model/           # Entités JPA/Hibernate
│   ├── JeuVideo.java
│   └── Support.java
├── repository/      # Couche d'accès aux données
│   ├── JeuVideoDao.java
│   └── SupportDao.java
├── controller/      # Contrôleurs JavaFX
│   └── JeuxController.java
├── presenter/       # Logique métier
│   └── MainPresenter.java
├── view/           # Vues JavaFX
│   └── MainView.java
├── utils/          # Utilitaires
│   ├── HibernateUtil.java
│   └── dialect/
└── Main.java       # Point d'entrée
```

## 🎨 Interface utilisateur

L'interface est divisée en trois panneaux :

1. **Panneau gauche** : Liste des jeux avec recherche et filtres
2. **Panneau central** : Aperçu détaillé du jeu sélectionné
3. **Panneau droit** : Formulaire pour ajouter/modifier les jeux

### Fonctionnalités de l'interface
- **Recherche en temps réel** par titre
- **Filtrage** par support, année, note
- **Sélection d'images** via explorateur de fichiers
- **Validation des formulaires** avec messages d'erreur
- **Confirmation de suppression** avec dialogue

## 🗄️ Base de données

### Structure
- **Table `jeux_video`** : Informations des jeux
- **Table `supports`** : Types de supports (PC, PS5, etc.)
- **Relation** : ManyToOne entre JeuVideo et Support

### Initialisation
Les supports par défaut sont automatiquement créés au premier lancement :
- PC, PS5, PS4, Xbox Series X, Xbox One, Nintendo Switch, Nintendo 3DS, Mobile

### Fichiers de base de données
- **H2** : `collection.mv.db` (créé automatiquement)
- **Console H2** : Accessible via `http://localhost:8082` (optionnel)

## 🔧 Configuration

### Fichier de configuration Hibernate
`src/main/resources/hibernate.cfg.xml`

### Propriétés de l'application
`src/main/resources/config.properties`

## 🚀 Utilisation

### Ajouter un jeu
1. Remplir le formulaire dans le panneau droit
2. Cliquer sur "Ajouter"
3. Le jeu apparaît dans la liste

### Modifier un jeu
1. Sélectionner un jeu dans la liste
2. Modifier les champs dans le formulaire
3. Cliquer sur "Modifier"

### Supprimer un jeu
1. Sélectionner un jeu dans la liste
2. Cliquer sur "Supprimer"
3. Confirmer la suppression

### Rechercher et filtrer
- Utiliser la barre de recherche pour filtrer par titre
- Utiliser les ComboBox pour filtrer par support, année, note

## 📝 Notes de développement

### Points d'amélioration possibles
- [ ] Import automatique depuis des APIs (IGDB, RAWG)
- [ ] Export des données (JSON, XML, CSV)
- [ ] Génération de site web statique
- [ ] Envoi par email de la collection
- [ ] Scan de codes-barres

### Bonnes pratiques implémentées
- ✅ Architecture MVP propre
- ✅ Gestion des exceptions
- ✅ Logging avec SLF4J
- ✅ Validation des données
- ✅ Interface responsive
- ✅ Code documenté et structuré

## 👥 Équipe

Projet réalisé dans le cadre du cours Java à Ynov.

## 📄 Licence

Ce projet est destiné à un usage éducatif.

---

**Version** : 1.0.0  
**Date** : 2024  
**Auteur** : Équipe Ynov Java 