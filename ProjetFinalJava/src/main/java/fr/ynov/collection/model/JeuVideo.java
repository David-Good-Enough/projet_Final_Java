package fr.ynov.collection.model;

import jakarta.persistence.*;

@Entity
public class JeuVideo {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String titre;
    private String editeur;
    private String developpeur;
    private int anneeSortie;
    @ManyToOne
    private Support support;
    private Integer noteMetacritic;
    private String jaquette;
    public JeuVideo() {}
    public JeuVideo(String titre, String editeur, String developpeur, int anneeSortie, Support support, Integer noteMetacritic, String jaquette) {
        this.titre = titre;
        this.editeur = editeur;
        this.developpeur = developpeur;
        this.anneeSortie = anneeSortie;
        this.support = support;
        this.noteMetacritic = noteMetacritic;
        this.jaquette = jaquette;
    }
    // Getters/Setters ici...
}
