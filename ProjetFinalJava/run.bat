@echo off
echo ========================================
echo Gestionnaire de Collection de Jeux Vidéo
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

REM Vérifier si le JAR existe
if not exist "target\collection-jeux-video-1.0.0.jar" (
    echo Le JAR n'existe pas. Compilation en cours...
    echo.
    echo Si Maven n'est pas installé, veuillez ouvrir le projet dans IntelliJ
    echo et exécuter la classe Main.java directement.
    echo.
    pause
    exit /b 1
)

echo Lancement de l'application...
echo.
java -jar target\collection-jeux-video-1.0.0.jar

if errorlevel 1 (
    echo.
    echo ERREUR lors du lancement de l'application
    pause
) else (
    echo.
    echo Application fermée avec succès
) 