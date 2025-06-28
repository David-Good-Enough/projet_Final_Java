@echo off
echo ========================================
echo    Creation du JAR - Collection Jeux Video
echo ========================================

echo.
echo 1. Nettoyage des anciens fichiers...
call mvn clean

echo.
echo 2. Compilation du projet...
call mvn compile

echo.
echo 3. Creation du JAR executable...
call mvn package

echo.
echo ========================================
echo JAR cree avec succes !
echo Localisation: target\collection-jeux-video-1.0.0-shaded.jar
echo ========================================

echo.
echo Voulez-vous executer le JAR maintenant ? (o/n)
set /p choice=
if /i "%choice%"=="o" (
    echo.
    echo Execution du JAR...
    java -jar target\collection-jeux-video-1.0.0-shaded.jar
)

pause 