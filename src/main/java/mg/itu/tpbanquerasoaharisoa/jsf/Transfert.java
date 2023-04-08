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
@Named(value = "transfert")
@ViewScoped
public class Transfert implements Serializable {

    @EJB
    private GestionnaireCompte gestionnaireCompte;

    private Long idSource;

    private Long idDestinataire;

    private int montant;

    public Long getIdSource() {
        return idSource;
    }

    public void setIdSource(Long idSource) {
        this.idSource = idSource;
    }

    public Long getIdDestinataire() {
        return idDestinataire;
    }

    public void setIdDestinataire(Long idDestinataire) {
        this.idDestinataire = idDestinataire;
    }

    public int getMontant() {
        return montant;
    }

    public void setMontant(int montant) {
        this.montant = montant;
    }

    /**
     * Creates a new instance of Transfert
     */
    public Transfert() {
    }

    public String transferer() {
        CompteBancaire source = gestionnaireCompte.getCompteById(idSource);
        CompteBancaire destinataire = gestionnaireCompte.getCompteById(idDestinataire);
        gestionnaireCompte.transfertArgent(source, destinataire, montant);
        return "listeComptes";
    }
}
