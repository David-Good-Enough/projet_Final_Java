@echo off
echo ========================================
echo Test de l'application Jeux Video
echo ========================================
echo.

REM Vérifier si Java est installé
java -version >nul 2>&1
if errorlevel 1 (
    echo ERREUR: Java n'est pas installé ou pas dans le PATH
    echo Veuillez installer Java 21 ou supérieur
    pause
    exit /b 1
)

echo Lancement des tests automatiques...
echo.

REM Compiler et exécuter les tests
echo Compilation en cours...
javac -cp "target/classes;%USERPROFILE%\.m2\repository\*" src/main/java/fr/ynov/collection/TestApplication.java

if errorlevel 1 (
    echo ERREUR: Compilation échouée
    echo Veuillez d'abord compiler le projet dans IntelliJ
    pause
    exit /b 1
)

echo.
echo Exécution des tests...
echo.

java -cp "target/classes;%USERPROFILE%\.m2\repository\*" fr.ynov.collection.TestApplication

if errorlevel 1 (
    echo.
    echo ERREUR: Tests échoués
    pause
) else (
    echo.
    echo Tests terminés avec succès !
    echo Vous pouvez maintenant lancer l'application principale.
)

pause 