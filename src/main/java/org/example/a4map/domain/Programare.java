package org.example.a4map.domain;

import java.util.Date;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class Programare extends Entity{
    private Pacient pacient;
    private LocalDateTime data;
    private String scopulProgramarii;

    public Programare(int ID, Pacient pacient, LocalDateTime data, String scopulProgramarii) {
        this.ID = ID;
        this.pacient = pacient;
        this.data = data;
        this.scopulProgramarii = scopulProgramarii;
    }

    /// o programare are o ora si nu se poate suprapune cu alta programare
    /// o programare are un singur pacient


    public int getID() {
        return ID;
    }

    public int getPacientInfo() {
        return pacient.getID();
    }
    public LocalDateTime getDataProgramarii() {
        return data;
    }
    public String getScopulProgramarii() {
        return scopulProgramarii;
    }


    public void setPacient(Pacient pacient) {
        this.pacient = pacient;
    }

    public void setDataProgramarii(LocalDateTime data) {
        this.data = data;
    }

    public void setScopulProgramarii(String scopulProgramarii) {
        this.scopulProgramarii = scopulProgramarii;
    }


    public String toString() {
        return "ID Programare: " + ID + "; pacientul de ID: " + pacient.getID() + "; data programarii: " + data + "; scopul programarii: " + scopulProgramarii + "\n";
    }
}
