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
import mg.itu.tpbanquerasoaharisoa.entities.OperationBancaire;

/**
 *
 * @author ASUS
 */
@Named(value = "listeOperations")
@ViewScoped
public class ListeOperations implements Serializable {

    @EJB
    private GestionnaireCompte gestionnaireCompte;
    
    private Long id;
    
    private CompteBancaire compte;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    
    /**
     * Creates a new instance of ListeOperations
     */
    public ListeOperations() {
    }
    
    public void loadOperations(){
        compte = gestionnaireCompte.getCompteById(id);
    }
    
    public List<OperationBancaire> getOperations(){
        return compte.getOperations();
    }
}
