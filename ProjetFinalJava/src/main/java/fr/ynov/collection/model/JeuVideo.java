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

    // Getters
    public Long getId() {
        return id;
    }

    public String getTitre() {
        return titre;
    }

    public String getEditeur() {
        return editeur;
    }

    public String getDeveloppeur() {
        return developpeur;
    }

    public int getAnneeSortie() {
        return anneeSortie;
    }

    public Support getSupport() {
        return support;
    }

    public Integer getNoteMetacritic() {
        return noteMetacritic;
    }

    public String getJaquette() {
        return jaquette;
    }

    // Setters
    public void setId(Long id) {
        this.id = id;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public void setEditeur(String editeur) {
        this.editeur = editeur;
    }

    public void setDeveloppeur(String developpeur) {
        this.developpeur = developpeur;
    }

    public void setAnneeSortie(int anneeSortie) {
        this.anneeSortie = anneeSortie;
    }

    public void setSupport(Support support) {
        this.support = support;
    }

    public void setNoteMetacritic(Integer noteMetacritic) {
        this.noteMetacritic = noteMetacritic;
    }

    public void setJaquette(String jaquette) {
        this.jaquette = jaquette;
    }

    @Override
    public String toString() {
        return "JeuVideo{" +
                "id=" + id +
                ", titre='" + titre + '\'' +
                ", editeur='" + editeur + '\'' +
                ", developpeur='" + developpeur + '\'' +
                ", anneeSortie=" + anneeSortie +
                ", support=" + (support != null ? support.getNom() : "null") +
                ", noteMetacritic=" + noteMetacritic +
                ", jaquette='" + jaquette + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        JeuVideo jeuVideo = (JeuVideo) o;

        if (anneeSortie != jeuVideo.anneeSortie) return false;
        if (!titre.equals(jeuVideo.titre)) return false;
        if (!editeur.equals(jeuVideo.editeur)) return false;
        if (!developpeur.equals(jeuVideo.developpeur)) return false;
        if (support != null ? !support.equals(jeuVideo.support) : jeuVideo.support != null) return false;
        if (noteMetacritic != null ? !noteMetacritic.equals(jeuVideo.noteMetacritic) : jeuVideo.noteMetacritic != null) return false;
        return jaquette != null ? jaquette.equals(jeuVideo.jaquette) : jeuVideo.jaquette == null;
    }

    @Override
    public int hashCode() {
        int result = titre.hashCode();
        result = 31 * result + editeur.hashCode();
        result = 31 * result + developpeur.hashCode();
        result = 31 * result + anneeSortie;
        result = 31 * result + (support != null ? support.hashCode() : 0);
        result = 31 * result + (noteMetacritic != null ? noteMetacritic.hashCode() : 0);
        result = 31 * result + (jaquette != null ? jaquette.hashCode() : 0);
        return result;
    }
}
