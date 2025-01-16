package org.example.a4map.service;
import org.example.a4map.domain.Entity;
import org.example.a4map.domain.Pacient;
import org.example.a4map.domain.Programare;
import org.example.a4map.repo.Repo;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Service<T extends Entity>{
    private final Repo<T> repository;

    public Service(Repo repository) {
        this.repository = repository;
    }

    public void addEntity(T entity) {
        repository.addEntity(entity);
    }

    public void removeEntityByID(int ID) {
        repository.removeEntityByID(ID);
    }

    public T findByID(int ID) {
        return repository.findByID(ID);
    }

    public void updateEntityByID(int idToBeUpdated,T updatedEntity) {
        repository.updateEntity(idToBeUpdated, updatedEntity);
    }

    public ArrayList<?> getAll() {
        return repository.findAll();
    }
    public void loadData() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        repository.getClass().getMethod("loadData").invoke(repository);

    }

    public void sortByID() {
        this.repository.findAll().sort(
                Comparator.comparing(Programare::getId : Pacient::getId)
        );
    }


}
