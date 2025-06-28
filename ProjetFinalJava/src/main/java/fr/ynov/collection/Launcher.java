package fr.ynov.collection;

/**
 * Classe Launcher pour éviter les problèmes de modules JavaFX
 * Cette classe permet de lancer l'application sans que le ClassLoader
 * essaie de charger JavaFX avant que l'application ne soit prête
 */
public class Launcher {
    
    public static void main(String[] args) {
        // Lance l'application JavaFX via la classe Main
        // Cette approche évite les problèmes de modules JavaFX
        Main.main(args);
    }
} 