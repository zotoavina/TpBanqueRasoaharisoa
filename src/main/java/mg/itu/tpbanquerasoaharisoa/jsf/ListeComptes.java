/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package mg.itu.tpbanquerasoaharisoa.jsf;

import jakarta.ejb.EJB;
import jakarta.inject.Named;
import jakarta.faces.view.ViewScoped;
import java.io.Serializable;
import java.util.List;
import mg.itu.tpbanquerasoaharisoa.ejb.GestionnaireCompte;
import mg.itu.tpbanquerasoaharisoa.entities.CompteBancaire;

/**
 *
 * @author ASUS
 */
@Named(value = "listeComptes")
@ViewScoped
public class ListeComptes implements Serializable {

    @EJB
    private GestionnaireCompte gestionnaireCompte;

    private List<CompteBancaire> listeComptes;

    /**
     * Creates a new instance of ListeComptes
     */
    public ListeComptes() {
    }

    public List<CompteBancaire> getAllComptes() {
        return (listeComptes == null) ? gestionnaireCompte.getAllComptes() : listeComptes;
    }

    public String supprimerCompte(CompteBancaire compte) {
        gestionnaireCompte.supprimerCompte(compte);
        Util.addFlashInfoMessage("Compte de " + compte.getNom() + " supprimé");
        return "listeComptes?faces-redirect=true";
    }

}
