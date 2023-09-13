/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package simplegui;

/**
 *
 * @author DELL
 */
public class Operateurs {
     private int  id_operateur;
     private int  matricules;
     private String nom;
     private String prenom;
     private String Datedembauche;
     private String equipe;

    public Operateurs(int id_operateur, int matricules, String nom, String prenom, String Datedembauche, String equipe) {
        this.id_operateur = id_operateur;
        this.matricules = matricules;
        this.nom = nom;
        this.prenom = prenom;
        this.Datedembauche = Datedembauche;
        this.equipe = equipe;
    }

    public int getId_operateur() {
        return id_operateur;
    }

    public void setId_operateur(int id_operateur) {
        this.id_operateur = id_operateur;
    }

    public int getMatricules() {
        return matricules;
    }

    public void setMatricules(int matricules) {
        this.matricules = matricules;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getDatedembauche() {
        return Datedembauche;
    }

    public void setDatedembauche(String Datedembauche) {
        this.Datedembauche = Datedembauche;
    }

    public String getEquipe() {
        return equipe;
    }

    public void setEquipe(String equipe) {
        this.equipe = equipe;
    }
     
     
     
}
