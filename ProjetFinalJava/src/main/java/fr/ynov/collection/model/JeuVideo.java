package fr.ynov.collection.model;

import jakarta.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "jeux_video")
public class JeuVideo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String titre;
    
    @Column(nullable = false)
    private String editeur;
    
    @Column(nullable = false)
    private String developpeur;
    
    @Column(name = "annee_sortie", nullable = false)
    private int anneeSortie;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "support_id", nullable = false)
    private Support support;
    
    @Column(name = "note_metacritic")
    private Double noteMetacritic;
    
    @Column(name = "jaquette_url")
    private String jaquette;

    public JeuVideo() {}

    public JeuVideo(String titre, String editeur, String developpeur, int anneeSortie, Support support) {
        this.titre = titre;
        this.editeur = editeur;
        this.developpeur = developpeur;
        this.anneeSortie = anneeSortie;
        this.support = support;
    }

    // Getters et Setters
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

    public Support getSupport() {
        return support;
    }

    public void setSupport(Support support) {
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

    @Override
    public String toString() {
        return titre + " (" + anneeSortie + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        JeuVideo jeuVideo = (JeuVideo) o;
        return id == jeuVideo.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
