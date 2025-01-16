package org.example.a4map.util;
import org.example.a4map.domain.Entity;
import org.example.a4map.repo.BinaryFileRepository;
import org.example.a4map.repo.Repo;
import org.example.a4map.repo.TextFileRepository;
import org.example.a4map.repo.DBRepo;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Proprietati {
    private static Properties properties = new Properties();

    static {
        try {
            properties.load(new FileInputStream("src/settings.properties"));
        } catch (IOException e) {
            System.out.println("Eroare la citirea fișierului settings.properties: " + e.getMessage());
        }
    }

    public static <T extends Entity> Repo<T> createRepository(String entityType, Class<T> clasa) {
        String repositoryType = properties.getProperty("Repository").trim();
        String filePath = properties.getProperty(entityType).trim();

        if ("binary".equalsIgnoreCase(repositoryType)) {
            return new BinaryFileRepository<>(filePath);
        } else if ("text".equalsIgnoreCase(repositoryType)) {
            return new TextFileRepository<>(filePath, clasa);
        } else {
            throw new RuntimeException("Tip de repository necunoscut: " + repositoryType);
        }
    }
}
