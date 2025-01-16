package org.example.a4map.controller;

import org.example.a4map.domain.Entity;
import org.example.a4map.service.Service;

import java.util.ArrayList;

public class Controller {
    private final Service service;

    public Controller(Service service) {
        this.service = service;
    }

    public void addEntity(Entity entity) {
        try{
            service.addEntity(entity);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    public void delete(int id){
        try{
            service.removeEntityByID(id);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    public void update(int id, Entity entity){
        try{
            service.updateEntityByID(id, entity);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    public ArrayList<Entity> getAll(){
        try{
            return service.getAll();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    public void loadData() {
        try{
            service.loadData();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    public

}
