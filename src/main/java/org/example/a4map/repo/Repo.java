package org.example.a4map.repo;

import org.example.a4map.domain.Entity;
import java.util.ArrayList;
import java.util.List;

public class Repo<T extends Entity>  {
    private ArrayList<T> entitiesList;

    /// implementare Repo generic

    public Repo() {
        entitiesList = new ArrayList<>();
    }


    public void addEntity(T entity) {
        if (findByID(entity.getID()) != null) {
            throw new RuntimeException("Entitatea cu ID-ul " + entity.getID() + " există deja!");
        }
        entitiesList.add(entity);
    }

    public void removeEntityByID(int ID) {
        entitiesList.removeIf(entity -> entity.getID() == ID);
    }

    public T findByID(int ID) {
        return entitiesList.stream().filter(e -> e.getID() == ID).findFirst().orElse(null);
    }

    public ArrayList<T> findAll() {
        return new ArrayList<>(entitiesList);
    }

    public void updateEntity(int idToBeUpdated,T updatedEntity) {
        T existingEntity = findByID(idToBeUpdated);
        if (existingEntity == null) {
            throw new RuntimeException("Entitate cu ID-ul " + idToBeUpdated + " nu există!");
        }
        entitiesList.remove(existingEntity); // Elimină entitatea veche
        entitiesList.add(updatedEntity);    // Adaugă entitatea actualizată
    }
}
