/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mg.itu.tpbanquerasoaharisoa.ejb;

import jakarta.annotation.sql.DataSourceDefinition;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import java.util.List;
import mg.itu.tpbanquerasoaharisoa.entities.CompteBancaire;

/**
 *
 * @author ASUS
 */
@DataSourceDefinition(
        className = "com.mysql.cj.jdbc.MysqlDataSource",
        name = "java:app/jdbc/banque",
        serverName = "localhost",
        portNumber = 3306,
        user = "root",
        password = "j@kartaE3",
        databaseName = "banque",
        properties = {
            "useSSL=false",
            "allowPublicKeyRetrieval=true"
        }
)
@Stateless
public class GestionnaireCompte {

    @PersistenceContext(unitName = "banquePU")
    private EntityManager em;

    public void creerCompte(CompteBancaire compteBancaire) {
        em.persist(compteBancaire);
    }

    /**
     * Selection de tous les comptes
     * @return 
     */
    public List<CompteBancaire> getAllComptes() {
        TypedQuery<CompteBancaire> query = em.createNamedQuery("CompteBancaire.findAll", CompteBancaire.class);
        return query.getResultList();
    }

    /**
     * Selection d'une compte à partir de son id
     * @param id
     * @return 
     */
    public CompteBancaire getCompteById(Long id) {
        return em.find(CompteBancaire.class, id);
    }


    /**
     * Transfert d'argent entre deux comtpes
     * @param source
     * @param destinataire
     * @param montant 
     */
    public void transfertArgent(CompteBancaire source, CompteBancaire destinataire, int montant) {
        source.retirer(montant);
        destinataire.deposer(montant);
        update(source);
        update(destinataire);
    }

    /**
     * Mise à jour d'une compte
     * @param compteBancaire
     * @return 
     */
    public CompteBancaire update(CompteBancaire compteBancaire) {
        return em.merge(compteBancaire);
    }
    

    /**
     * Retirer un certain montant d'une compte
     * @param compte
     * @param montant 
     */
    public void retirer(CompteBancaire compte, int montant){
        compte.retirer(montant);
        update(compte);
    }
    
    /**
     * Deposer un certain montant dans une compte
     * @param compte
     * @param montant 
     */
    public void deposer(CompteBancaire compte, int montant) {
        compte.deposer(montant);
        update(compte);
    }
    
    public void supprimerCompte(CompteBancaire compte){
        compte = em.merge(compte);
        em.remove(compte);
    }
}
