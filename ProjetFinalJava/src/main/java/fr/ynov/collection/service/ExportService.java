package fr.ynov.collection.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import fr.ynov.collection.model.JeuVideo;
import fr.ynov.collection.repository.JeuVideoDao;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExportService {
    
    private final JeuVideoDao jeuVideoDao;
    private final ObjectMapper objectMapper;
    
    public ExportService() {
        this.jeuVideoDao = new JeuVideoDao();
        this.objectMapper = new ObjectMapper();
        this.objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
    }
    
    public void exportToJson(String filePath) throws IOException {
        List<JeuVideo> jeux = jeuVideoDao.findAll();
        
        Map<String, Object> exportData = new HashMap<>();
        exportData.put("exportDate", LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        exportData.put("totalGames", jeux.size());
        exportData.put("games", jeux);
        
        objectMapper.writeValue(new File(filePath), exportData);
    }
    
    public void exportToJsonWithStats(String filePath) throws IOException {
        List<JeuVideo> jeux = jeuVideoDao.findAll();
        
        // Calculer les statistiques
        Map<String, Object> stats = new HashMap<>();
        stats.put("totalGames", jeux.size());
        
        // Compter par support
        Map<String, Long> supportCount = jeux.stream()
            .collect(java.util.stream.Collectors.groupingBy(
                jeu -> jeu.getSupport().getNom(),
                java.util.stream.Collectors.counting()
            ));
        stats.put("bySupport", supportCount);
        
        // Compter par année
        Map<Integer, Long> yearCount = jeux.stream()
            .collect(java.util.stream.Collectors.groupingBy(
                JeuVideo::getAnneeSortie,
                java.util.stream.Collectors.counting()
            ));
        stats.put("byYear", yearCount);
        
        // Note moyenne
        double averageNote = jeux.stream()
            .filter(jeu -> jeu.getNoteMetacritic() != null)
            .mapToDouble(JeuVideo::getNoteMetacritic)
            .average()
            .orElse(0.0);
        stats.put("averageNote", averageNote);
        
        Map<String, Object> exportData = new HashMap<>();
        exportData.put("exportDate", LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        exportData.put("statistics", stats);
        exportData.put("games", jeux);
        
        objectMapper.writeValue(new File(filePath), exportData);
    }
} 