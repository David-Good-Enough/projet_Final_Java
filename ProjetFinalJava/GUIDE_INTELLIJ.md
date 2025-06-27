# 🚀 Guide d'installation et d'utilisation avec IntelliJ IDEA

## 📋 Prérequis

- **IntelliJ IDEA** (version Community ou Ultimate)
- **Java 21** ou supérieur
- **Git** (optionnel, pour cloner le projet)

## 🔧 Installation dans IntelliJ

### 1. Ouvrir le projet

1. Lancez **IntelliJ IDEA**
2. Cliquez sur **"Open"** ou **"File" → "Open"**
3. Sélectionnez le dossier **`ProjetFinalJava`**
4. Cliquez sur **"OK"**

### 2. Configuration du projet

IntelliJ devrait automatiquement détecter que c'est un projet Maven. Si ce n'est pas le cas :

1. Clic droit sur le fichier `pom.xml`
2. Sélectionnez **"Add as Maven Project"**

### 3. Synchronisation des dépendances

1. IntelliJ va automatiquement télécharger les dépendances Maven
2. Si ce n'est pas le cas, cliquez sur l'icône **"Reload All Maven Projects"** dans la barre d'outils Maven

## 🎯 Configuration de l'exécution

### Option 1 : Exécution directe (Recommandée)

1. Ouvrez la classe `Main.java` dans `src/main/java/fr/ynov/collection/`
2. Cliquez sur la flèche verte à côté de la classe `Main`
3. Sélectionnez **"Run 'Main.main()'"**

### Option 2 : Configuration personnalisée

1. Cliquez sur **"Run" → "Edit Configurations..."**
2. Cliquez sur le **"+"** et sélectionnez **"Application"**
3. Configurez :
   - **Name** : `Main`
   - **Main class** : `fr.ynov.collection.Main`
   - **Module** : `ProjetFinalJava`
4. Cliquez sur **"OK"**

## 🏗️ Structure du projet dans IntelliJ

```
ProjetFinalJava/
├── src/
│   ├── main/
│   │   ├── java/fr/ynov/collection/
│   │   │   ├── controller/     # Contrôleurs JavaFX
│   │   │   ├── model/          # Entités JPA/Hibernate
│   │   │   ├── repository/     # Couche d'accès aux données
│   │   │   ├── service/        # Services métier
│   │   │   ├── utils/          # Utilitaires
│   │   │   ├── view/           # Vues JavaFX
│   │   │   └── Main.java       # Point d'entrée
│   │   └── resources/
│   │       ├── jeux-view.fxml  # Interface utilisateur
│   │       ├── hibernate.cfg.xml
│   │       └── config.properties
│   └── test/                   # Tests unitaires (optionnel)
├── target/                     # Fichiers compilés
├── pom.xml                     # Configuration Maven
└── README.md                   # Documentation
```

## 🚀 Exécution de l'application

### Première exécution

1. **Lancez l'application** via la classe `Main`
2. **La base de données** sera automatiquement créée
3. **Les supports par défaut** seront initialisés (PC, PS5, etc.)

### Utilisation de l'interface

1. **Ajouter un jeu** : Remplissez le formulaire à droite et cliquez "Ajouter"
2. **Modifier un jeu** : Sélectionnez un jeu dans la liste, modifiez le formulaire, cliquez "Modifier"
3. **Supprimer un jeu** : Sélectionnez un jeu et cliquez "Supprimer"
4. **Rechercher** : Utilisez la barre de recherche en haut
5. **Filtrer** : Utilisez les ComboBox pour filtrer par support, année, note
6. **Exporter** : Cliquez sur "Exporter JSON" pour sauvegarder votre collection

## 🔧 Résolution des problèmes

### Erreur "JavaFX runtime components are missing"

Si vous obtenez cette erreur :

1. **Vérifiez que JavaFX est dans les dépendances Maven** (déjà configuré)
2. **Redémarrez IntelliJ** après l'import du projet
3. **Synchronisez les dépendances Maven** (icône "Reload")

### Erreur de base de données

1. **Vérifiez les permissions** du dossier du projet
2. **Supprimez le fichier `collection.db`** pour recréer la base
3. **Relancez l'application**

### Problèmes de compilation

1. **File → Invalidate Caches and Restart**
2. **Vérifiez la version Java** : `File → Project Structure → Project SDK`
3. **Synchronisez Maven** : `View → Tool Windows → Maven`

## 📦 Création du JAR exécutable

### Via IntelliJ

1. **View → Tool Windows → Maven**
2. **Lifecycle → package**
3. Le JAR sera créé dans `target/collection-jeux-video-1.0.0.jar`

### Via ligne de commande

```bash
mvn clean package
```

## 🎨 Personnalisation

### Modifier l'interface

1. **Ouvrez `jeux-view.fxml`** dans IntelliJ
2. **Utilisez Scene Builder** (optionnel) pour modifier l'interface
3. **Modifiez le controller** `JeuxController.java` pour ajouter des fonctionnalités

### Ajouter des fonctionnalités

1. **Nouveau modèle** : Créez une classe dans `model/`
2. **Nouveau DAO** : Créez une classe dans `repository/`
3. **Nouveau service** : Créez une classe dans `service/`
4. **Nouveau controller** : Créez une classe dans `controller/`

## 📚 Ressources utiles

- **Documentation JavaFX** : https://openjfx.io/
- **Documentation Hibernate** : https://hibernate.org/
- **Documentation Maven** : https://maven.apache.org/

## 🆘 Support

En cas de problème :

1. **Vérifiez les logs** dans la console IntelliJ
2. **Consultez le README.md** principal
3. **Vérifiez la configuration** dans `config.properties`

---

**Bon développement ! 🎮** 