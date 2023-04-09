/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package mg.itu.tpbanquerasoaharisoa.jsf;

import jakarta.ejb.EJB;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.validator.ValidatorException;
import jakarta.inject.Named;
import jakarta.faces.view.ViewScoped;
import java.io.Serializable;
import mg.itu.tpbanquerasoaharisoa.ejb.GestionnaireCompte;
import mg.itu.tpbanquerasoaharisoa.entities.CompteBancaire;

/**
 *
 * @author ASUS
 */
@Named(value = "modification")
@ViewScoped
public class ModificationCompte implements Serializable {

    @EJB
    private GestionnaireCompte gestionnaireCompte;

    private Long id;

    private CompteBancaire compte;

    /**
     * Creates a new instance of ModificationCompte
     */
    public ModificationCompte() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CompteBancaire getCompte() {
        return compte;
    }

    public void setCompte(CompteBancaire compte) {
        this.compte = compte;
    }

    public void loadCompte() {
        compte = gestionnaireCompte.getCompteById(id);
    }

    public String enregistrerModifications() {
        gestionnaireCompte.update(compte);
        Util.addFlashInfoMessage("Compte modifié nom : " + compte.getNom() + " solde : " + compte.getSolde());
        return "listeComptes?faces-redirect=true";
    }

    public void validateSolde(FacesContext fc, UIComponent composant, Object valeur) {
        if ((int) valeur <= 0) {
            FacesMessage message
                    = new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            "Le solde d'un compte doit être un entier supérieur à 0",
                            "Le solde d'un compte doit être un entier supérieur à 0");
            throw new ValidatorException(message);
        }
    }

}
