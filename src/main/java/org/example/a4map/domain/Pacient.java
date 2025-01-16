package org.example.a4map.domain;

public class Pacient extends Entity{
    private String nume;
    private String prenume;
    private int anulNasterii;

    public Pacient(int ID, String nume, String prenume, int anulNasterii) {
        this.ID = ID;
        this.nume = nume;
        this.prenume = prenume;
        this.anulNasterii = anulNasterii;
    }

    /// un pacient poate avea mai multe programari

    //getteri

    public int getID() {
        return ID;
    }

    public String getNumePacient() {
        return nume;
    }
    public String getPrenumePacient() {
        return prenume;
    }
    public int getAnulNasterii() {
        return anulNasterii;
    }


    //setteri

    public void setNumePacient(String nume) {
        this.nume = nume;
    }

    public void setPrenumePacient(String prenume) {
        this.prenume = prenume;
    }

    public void setAnulNasterii(int anulNasterii) {
        this.anulNasterii = anulNasterii;
    }

    public String toString() {
        return "ID Pacient: " + ID + "; si numele: " + nume + " " + prenume + "; anul nasterii: " + anulNasterii + "\n";
    }

}
