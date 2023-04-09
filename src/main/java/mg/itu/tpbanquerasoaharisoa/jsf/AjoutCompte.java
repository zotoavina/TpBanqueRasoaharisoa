/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package mg.itu.tpbanquerasoaharisoa.jsf;

import jakarta.ejb.EJB;
import jakarta.inject.Named;
import jakarta.faces.view.ViewScoped;
import java.io.Serializable;
import mg.itu.tpbanquerasoaharisoa.ejb.GestionnaireCompte;
import mg.itu.tpbanquerasoaharisoa.entities.CompteBancaire;

/**
 *
 * @author ASUS
 */
@Named(value = "ajout")
@ViewScoped
public class AjoutCompte implements Serializable {

    @EJB
    private GestionnaireCompte gestionnaireCompte;
    
    private String nom;
    
    private int montant;

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public int getMontant() {
        return montant;
    }

    public void setMontant(int montant) {
        this.montant = montant;
    }
    
    
    
    /**
     * Creates a new instance of CreerCompte
     */
    public AjoutCompte() {
    }
    
    public String ajoutCompte(){
        if(montant <= 0){
            Util.messageErreur("Le montant doit être supérieur à 0", "Le montant doit être supérieur à 0", "form:montant");
            return null;
        }
        gestionnaireCompte.creerCompte(new CompteBancaire(nom, montant));
        Util.addFlashInfoMessage("Ajout nouveau compte effectué avec nom : " + nom + " montant : " + montant);
        return "listeComptes?faces-redirect=true";
    }
    
}
