package fr.ynov.collection.model;

import jakarta.persistence.*;

@Entity
@Table(name = "jeux_video")
public class JeuVideo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String titre;
    private String editeur;
    private String developpeur;
    private int anneeSortie;
    private String support; // PC, PS5, Switch, etc.
    private Double noteMetacritic;
    private String jaquette;

    public JeuVideo() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getEditeur() {
        return editeur;
    }

    public void setEditeur(String editeur) {
        this.editeur = editeur;
    }

    public String getDeveloppeur() {
        return developpeur;
    }

    public void setDeveloppeur(String developpeur) {
        this.developpeur = developpeur;
    }

    public int getAnneeSortie() {
        return anneeSortie;
    }

    public void setAnneeSortie(int anneeSortie) {
        this.anneeSortie = anneeSortie;
    }

    public String getSupport() {
        return support;
    }

    public void setSupport(String support) {
        this.support = support;
    }

    public Double getNoteMetacritic() {
        return noteMetacritic;
    }

    public void setNoteMetacritic(Double noteMetacritic) {
        this.noteMetacritic = noteMetacritic;
    }

    public String getJaquette() {
        return jaquette;
    }

    public void setJaquette(String jaquette) {
        this.jaquette = jaquette;
    }
}
