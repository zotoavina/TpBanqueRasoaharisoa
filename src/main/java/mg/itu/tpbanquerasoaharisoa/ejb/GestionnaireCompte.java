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
@DataSourceDefinition (
    className="com.mysql.cj.jdbc.MysqlDataSource",
    name="java:app/jdbc/banque",
    serverName="localhost",
    portNumber=3306,
    user="root",              
    password="j@kartaE3",
    databaseName="banque",
    properties = {
      "useSSL=false",
      "allowPublicKeyRetrieval=true"
    }
)
@Stateless
public class GestionnaireCompte {
    
    @PersistenceContext(unitName = "banquePU")
    private EntityManager em;
    
    
    public void creerCompte(CompteBancaire compteBancaire){
        em.persist(compteBancaire);
    }
    
    public List<CompteBancaire> getAllComptes(){
        TypedQuery<CompteBancaire> query = em.createNamedQuery("CompteBancaire.findAll", CompteBancaire.class);
        return query.getResultList();
    }
}
