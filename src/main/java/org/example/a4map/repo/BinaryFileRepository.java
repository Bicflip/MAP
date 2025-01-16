package org.example.a4map.repo;

import org.example.a4map.domain.Entity;
import java.io.*;
import java.util.List;

public class BinaryFileRepository<T extends Entity> extends Repo<T> {
    private final String filePath;

    public BinaryFileRepository(String filePath) {
        this.filePath = filePath;
        loadFromFile();
    }

    private void loadFromFile() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath))) {
            List<T> entities = (List<T>) ois.readObject();
            for (T entity : entities) {
                super.addEntity(entity);
            }
        } catch (FileNotFoundException e) {
            System.out.println("Fișierul binar nu există, va fi creat unul nou: " + filePath);
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Eroare la citirea fișierului binar: " + filePath);
        }
    }

    private void saveToFile() {
        try (ObjectOutputStream oos
                     = new ObjectOutputStream(new FileOutputStream(filePath))) {
            oos.writeObject(findAll());
        } catch (IOException e) {
            System.out.println("Eroare la scrierea în fișierul binar: " + filePath);
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
    public void updateEntity(int idToBeUpdated,T entity) {
        super.updateEntity(idToBeUpdated,entity);
        saveToFile();
    }

}