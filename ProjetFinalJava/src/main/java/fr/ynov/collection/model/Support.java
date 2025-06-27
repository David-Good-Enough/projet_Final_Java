package fr.ynov.collection.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "supports")
public class Support {
    
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, unique = true)
    private String nom;
    
    @OneToMany(mappedBy = "support", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<JeuVideo> jeuxVideo = new ArrayList<>();
    
    public Support() {}
    
    public Support(String nom) { 
        this.nom = nom; 
    }
    
    public Long getId() { 
        return id; 
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getNom() { 
        return nom; 
    }
    
    public void setNom(String nom) { 
        this.nom = nom; 
    }
    
    public List<JeuVideo> getJeuxVideo() {
        return jeuxVideo;
    }
    
    public void setJeuxVideo(List<JeuVideo> jeuxVideo) {
        this.jeuxVideo = jeuxVideo;
    }
    
    @Override
    public String toString() {
        return nom;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Support support = (Support) o;
        return Objects.equals(id, support.id);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
