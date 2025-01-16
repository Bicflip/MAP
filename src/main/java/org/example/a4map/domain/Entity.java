package org.example.a4map.domain;

import java.io.Serializable;

public abstract class Entity implements Serializable {
    protected int ID;
    private static final long serialVersionUID = 1L; // Adaugă un serialVersionUID pentru a evita erorile de compatibilitate


    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Entity entity = (Entity) obj;
        return ID == entity.ID;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(ID);
    }
}
