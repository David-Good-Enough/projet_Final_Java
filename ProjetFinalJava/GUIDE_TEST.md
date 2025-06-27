# 🧪 Guide de Test - Gestionnaire de Collection de Jeux Vidéo

## 📋 Prérequis pour les tests

- ✅ IntelliJ IDEA ouvert avec le projet
- ✅ Java 21 installé
- ✅ Dépendances Maven téléchargées

## 🚀 Test 1 : Lancement de l'application

### Étapes :
1. **Ouvrir IntelliJ IDEA**
2. **Ouvrir le projet** `ProjetFinalJava`
3. **Attendre** que Maven télécharge les dépendances
4. **Ouvrir** `src/main/java/fr/ynov/collection/Main.java`
5. **Cliquer** sur la flèche verte ▶️ à côté de `Main`

### ✅ Résultat attendu :
- L'application se lance sans erreur
- Une fenêtre JavaFX s'ouvre avec 3 panneaux
- La base de données H2 se crée automatiquement
- Les supports par défaut sont initialisés

### 🔍 Vérifications :
- [ ] Pas d'erreurs dans la console IntelliJ
- [ ] Interface graphique s'affiche correctement
- [ ] Fichier `collection.mv.db` créé dans le dossier du projet

---

## 🎮 Test 2 : Fonctionnalités CRUD

### Test 2.1 : Ajouter un jeu

#### Étapes :
1. **Remplir le formulaire** (panneau droit) :
   - Titre : `The Legend of Zelda: Breath of the Wild`
   - Éditeur : `Nintendo`
   - Développeur : `Nintendo EPD`
   - Année : `2017`
   - Support : `Nintendo Switch`
   - Note : `9.7`
   - Jaquette : `https://example.com/zelda.jpg`

2. **Cliquer** sur "Ajouter"

#### ✅ Résultat attendu :
- Le jeu apparaît dans la liste (panneau gauche)
- Le formulaire se vide
- Message de statut : "Jeu ajouté avec succès"

### Test 2.2 : Ajouter un deuxième jeu

#### Étapes :
1. **Remplir le formulaire** :
   - Titre : `God of War`
   - Éditeur : `Sony Interactive Entertainment`
   - Développeur : `Santa Monica Studio`
   - Année : `2018`
   - Support : `PS4`
   - Note : `9.4`
   - Jaquette : (laisser vide)

2. **Cliquer** sur "Ajouter"

#### ✅ Résultat attendu :
- Deux jeux dans la liste
- Interface fonctionne correctement

### Test 2.3 : Modifier un jeu

#### Étapes :
1. **Sélectionner** un jeu dans la liste
2. **Modifier** la note dans le formulaire (ex: 9.8)
3. **Cliquer** sur "Modifier"

#### ✅ Résultat attendu :
- La modification est sauvegardée
- Message : "Jeu modifié avec succès"

### Test 2.4 : Supprimer un jeu

#### Étapes :
1. **Sélectionner** un jeu dans la liste
2. **Cliquer** sur "Supprimer"
3. **Confirmer** la suppression dans la boîte de dialogue

#### ✅ Résultat attendu :
- Dialogue de confirmation s'affiche
- Le jeu est supprimé après confirmation
- Message : "Jeu supprimé avec succès"

---

## 🔍 Test 3 : Recherche et filtrage

### Test 3.1 : Recherche par titre

#### Étapes :
1. **Taper** "Zelda" dans la barre de recherche
2. **Observer** la liste se filtrer

#### ✅ Résultat attendu :
- Seuls les jeux contenant "Zelda" s'affichent
- Recherche en temps réel

### Test 3.2 : Filtrage par support

#### Étapes :
1. **Sélectionner** "Nintendo Switch" dans le filtre Support
2. **Observer** la liste se filtrer

#### ✅ Résultat attendu :
- Seuls les jeux sur Nintendo Switch s'affichent

### Test 3.3 : Filtrage par année

#### Étapes :
1. **Sélectionner** "2017" dans le filtre Année
2. **Observer** la liste se filtrer

#### ✅ Résultat attendu :
- Seuls les jeux de 2017 s'affichent

### Test 3.4 : Filtrage par note

#### Étapes :
1. **Sélectionner** "9+" dans le filtre Note
2. **Observer** la liste se filtrer

#### ✅ Résultat attendu :
- Seuls les jeux avec note ≥ 9 s'affichent

---

## 📊 Test 4 : Fonctionnalités avancées

### Test 4.1 : Sélection d'image locale

#### Étapes :
1. **Cliquer** sur le bouton "..." à côté du champ Jaquette
2. **Sélectionner** une image sur votre ordinateur
3. **Vérifier** que le chemin s'affiche dans le champ

#### ✅ Résultat attendu :
- L'explorateur de fichiers s'ouvre
- Le chemin de l'image s'affiche dans le champ

### Test 4.2 : Export JSON

#### Étapes :
1. **Ajouter** quelques jeux à la collection
2. **Cliquer** sur "Exporter JSON"
3. **Choisir** un emplacement pour sauvegarder
4. **Ouvrir** le fichier JSON généré

#### ✅ Résultat attendu :
- Dialogue de sauvegarde s'ouvre
- Fichier JSON créé avec :
  - Date d'export
  - Statistiques (total, par support, par année, note moyenne)
  - Liste complète des jeux

### Test 4.3 : Effacer le formulaire

#### Étapes :
1. **Remplir** partiellement le formulaire
2. **Cliquer** sur "Effacer le formulaire"

#### ✅ Résultat attendu :
- Tous les champs se vident
- Sélection dans la liste se désélectionne

---

## 🎯 Test 5 : Validation des données

### Test 5.1 : Validation des champs obligatoires

#### Étapes :
1. **Laisser** le titre vide
2. **Cliquer** sur "Ajouter"

#### ✅ Résultat attendu :
- Message d'erreur : "Le titre est obligatoire"
- Formulaire ne se soumet pas

### Test 5.2 : Validation de l'année

#### Étapes :
1. **Remplir** tous les champs sauf l'année
2. **Cliquer** sur "Ajouter"

#### ✅ Résultat attendu :
- Message d'erreur : "L'année de sortie est obligatoire"

### Test 5.3 : Validation du support

#### Étapes :
1. **Remplir** tous les champs sauf le support
2. **Cliquer** sur "Ajouter"

#### ✅ Résultat attendu :
- Message d'erreur : "Le support est obligatoire"

---

## 🔧 Test 6 : Gestion des erreurs

### Test 6.1 : URL d'image invalide

#### Étapes :
1. **Ajouter** un jeu avec une URL d'image invalide
2. **Sélectionner** ce jeu dans la liste

#### ✅ Résultat attendu :
- L'application ne plante pas
- L'image ne s'affiche pas (normal)

### Test 6.2 : Note invalide

#### Étapes :
1. **Taper** "abc" dans le champ Note
2. **Ajouter** le jeu

#### ✅ Résultat attendu :
- La note est ignorée (null)
- Le jeu est ajouté sans erreur

---

## 📋 Checklist de validation finale

### ✅ Interface utilisateur
- [ ] Interface s'affiche correctement
- [ ] 3 panneaux visibles (liste, aperçu, formulaire)
- [ ] Barre de recherche et filtres fonctionnels
- [ ] Boutons d'action visibles

### ✅ Fonctionnalités CRUD
- [ ] Ajouter un jeu ✅
- [ ] Modifier un jeu ✅
- [ ] Supprimer un jeu ✅
- [ ] Afficher la liste ✅

### ✅ Recherche et filtrage
- [ ] Recherche par titre ✅
- [ ] Filtrage par support ✅
- [ ] Filtrage par année ✅
- [ ] Filtrage par note ✅

### ✅ Fonctionnalités avancées
- [ ] Sélection d'image ✅
- [ ] Export JSON ✅
- [ ] Effacer formulaire ✅

### ✅ Validation
- [ ] Champs obligatoires ✅
- [ ] Gestion des erreurs ✅
- [ ] Messages d'erreur clairs ✅

### ✅ Base de données
- [ ] Fichier H2 créé ✅
- [ ] Données persistées ✅
- [ ] Relations fonctionnelles ✅

---

## 🎉 Résultat final

Si tous les tests passent ✅, votre application est **100% fonctionnelle** et prête pour la remise !

### 📊 Score estimé :
- **Fonctionnalités de base** : 8/8 points
- **Qualité du code** : 4/4 points  
- **Ergonomie** : 2/2 points
- **Packaging** : 2/2 points
- **Bonus** : 4/4 points

**Total estimé : 20/20** 🏆

---

## 🆘 En cas de problème

1. **Vérifiez les logs** dans la console IntelliJ
2. **Redémarrez** l'application
3. **Vérifiez** que H2 est bien dans les dépendances Maven
4. **Consultez** le README.md pour plus d'informations

**Bon test ! 🎮** 