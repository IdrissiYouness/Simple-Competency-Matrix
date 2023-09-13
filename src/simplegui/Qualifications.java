/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package simplegui;

/**
 *
 * @author DELL
 */
public class Qualifications {
    
    private int  id_qualification;
    private String nom;
    
    public Qualifications(int id_qualification, String nom ){
        this.id_qualification = id_qualification;
        this.nom = nom;
    }

    public int getId_qualification() {
        return id_qualification;
    }

    public String getNom() {
        return nom;
    }

    public void setId_qualification(int id_qualification) {
        this.id_qualification = id_qualification;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }
    
    
    
}
