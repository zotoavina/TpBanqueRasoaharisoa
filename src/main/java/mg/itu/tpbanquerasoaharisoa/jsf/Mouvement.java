/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package mg.itu.tpbanquerasoaharisoa.jsf;

import jakarta.ejb.EJB;
import jakarta.ejb.EJBException;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.component.UIComponent;
import jakarta.faces.component.UIInput;
import jakarta.faces.context.FacesContext;
import jakarta.faces.validator.ValidatorException;
import jakarta.inject.Named;
import jakarta.faces.view.ViewScoped;
import jakarta.persistence.OptimisticLockException;
import java.io.Serializable;
import mg.itu.tpbanquerasoaharisoa.ejb.GestionnaireCompte;
import mg.itu.tpbanquerasoaharisoa.entities.CompteBancaire;

/**
 *
 * @author ASUS
 */
@Named(value = "mouvement")
@ViewScoped
public class Mouvement implements Serializable {

    @EJB
    private GestionnaireCompte gestionnaireCompte;

    private Long id;

    private int montant;

    private String typeMouvement;

    private CompteBancaire compte;

    /**
     * Creates a new instance of Mouvement
     */
    public Mouvement() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getMontant() {
        return montant;
    }

    public void setMontant(int montant) {
        this.montant = montant;
    }

    public CompteBancaire getCompte() {
        return compte;
    }

    public void setCompte(CompteBancaire compte) {
        this.compte = compte;
    }

    public String getTypeMouvement() {
        return typeMouvement;
    }

    public void setTypeMouvement(String typeMouvement) {
        this.typeMouvement = typeMouvement;
    }

    /**
     * Selection du compte cliquez dans la liste des comptes
     */
    public void loadCompte() {
        this.compte = gestionnaireCompte.getCompteById(id);
    }

    /**
     * enregistremnt du mouvement effectuez
     *
     * @return
     */
    public String enregistrerMouvement() {
        try {
            if ("retrait".equals(typeMouvement)) {
                gestionnaireCompte.retirer(compte, montant);
            } else {
                gestionnaireCompte.deposer(compte, montant);
            }
            Util.addFlashInfoMessage("Mouvement enregistré sur compte de " + compte.getNom());
            return "listeComptes?faces-redirect=true";
        } catch (EJBException exception) {
            Throwable cause = exception.getCause();
            if (cause != null) {
                if (cause instanceof OptimisticLockException) {
                    Util.messageErreur("Le compte de " + compte.getNom()
                            + " a été modifié ou supprimé par un autre utilisateur !",
                            "Le compte de " + compte.getNom()
                            + " a été modifié ou supprimé par un autre utilisateur !",
                            "form:panel");
                } else { // Afficher le message de ex si la cause n'est pas une OptimisticLockException
                    Util.messageErreur(cause.getMessage());
                }
            } else { // Pas de cause attachée à l'EJBException
                Util.messageErreur(exception.getMessage());
            }
            return null; // pour rester sur la page s'il y a une exception
        }
    }

    /**
     * Validation du parametre montant
     *
     * @param fc
     * @param composant
     * @param valeur
     */
    public void validateSolde(FacesContext fc, UIComponent composant, Object valeur) {
        UIInput composantTypeMouvement = (UIInput) composant.findComponent("typeMouvement");
        // Sans entrer dans les détails, il faut parfois utiliser
        // getSubmittedValue() à la place de getLocalValue.
        // typeMouvement n'est pas encore mis tant que la validation n'est pas finie.
        String valeurTypeMouvement = (String) composantTypeMouvement.getLocalValue();
        if (valeurTypeMouvement == null) {
            // Pour le cas où l'utilisateur a soumis le formulaire sans indiquer le type du mouvement
            return;
        }
        if (valeurTypeMouvement.equals("retrait")) {
            int retrait = (int) valeur;
            if (compte.getSolde() < retrait) {
                FacesMessage message
                        = new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                "Le retrait doit être inférieur au solde du compte",
                                "Le retrait doit être inférieur au solde du compte");
                throw new ValidatorException(message);
            }
        }
    }

}
