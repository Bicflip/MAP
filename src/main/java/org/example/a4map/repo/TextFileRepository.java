package org.example.a4map.repo;

import org.example.a4map.domain.Entity;
import org.example.a4map.repo.Repo;
import org.example.a4map.domain.Pacient;
import org.example.a4map.domain.Programare;
import org.example.a4map.service.Service;

import java.io.*;
import java.time.LocalDateTime;


public class TextFileRepository<T extends Entity> extends Repo<T> {
    private final String filePath;
    private final Class<T> entityType;

    public TextFileRepository(String filePath, Class<T> entityType) {
        this.filePath = filePath;
        this.entityType = entityType;
        loadFromFile();
    }

    private void loadFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                T entity = parseEntity(line);
                if (entity != null) {
                    super.addEntity(entity);
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Fișierul text nu există, va fi creat unul nou: " + filePath);
        } catch (IOException e) {
            System.out.println("Eroare la citirea fișierului text: " + filePath);
        }
    }

    private void saveToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (T entity : super.findAll()) {
                writer.write(serializeEntity(entity));
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Eroare la scrierea în fișierul text: " + filePath);
        }
    }

    @Override
    public void addEntity(T entity) {
        super.addEntity(entity);
        saveToFile();
    }

    @Override
    public void removeEntityByID(int id) {
        super.removeEntityByID(id);
        saveToFile();
    }

    @Override
    public void updateEntity(int idTobeUpdated, T entity) {
        super.updateEntity(idTobeUpdated,entity);
        saveToFile();
    }

    private T parseEntity(String line) {
        try {
            String[] parts = line.split(",");
            if (entityType == Pacient.class) {
                int id = Integer.parseInt(parts[0]);
                String nume = parts[1];
                String prenume = parts[2];
                int anulNasterii = Integer.parseInt(parts[3]);
                return entityType.cast(new Pacient(id, nume, prenume, anulNasterii));
            } else if (entityType == Programare.class) {
                int id = Integer.parseInt(parts[0]);
                int idPacient = Integer.parseInt(parts[1]);
                String data = parts[2];
                String scopulProgramarii = parts[3];
                Service service = new Service(new Repo());
                Entity pacient = service.findByID(idPacient);
                return entityType.cast(new Programare(id, (Pacient)pacient, LocalDateTime.parse(data), scopulProgramarii));
            }
        } catch (Exception e) {
            System.out.println("Eroare la parsarea liniei: " + line);
        }
        return null;
    }

    private String serializeEntity(T entity) {
        if (entity instanceof Pacient pacient) {
            return pacient.getID() + "," + pacient.getNumePacient() + "," + pacient.getPrenumePacient() + "," + pacient.getAnulNasterii();
        } else if (entity instanceof Programare programare) {
            return programare.getID() + "," + programare.getPacientInfo() + "," + programare.getDataProgramarii() + "," + programare.getScopulProgramarii();
        }
        return "";
    }
}
