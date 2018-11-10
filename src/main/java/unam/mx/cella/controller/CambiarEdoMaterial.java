/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package unam.mx.cella.controller;

import java.util.Locale;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import unam.mx.cella.modelo.EntityProvider;
import unam.mx.cella.modelo.Unidadmaterial;
import unam.mx.cella.modelo.UnidadmaterialJpaController;
import unam.mx.cella.modelo.exceptions.NonexistentEntityException;


/**
 *
 * @author eduar
 */
@ManagedBean
@RequestScoped
public class CambiarEdoMaterial {

     private final EntityManagerFactory emf;
     private String id;
     private String estado;
     private Unidadmaterial unidadmaterial;
     
    /**
     * Creates a new instance of CambiarEdoMaterial
     */
    public CambiarEdoMaterial() {
        emf = EntityProvider.provider();
      
        FacesContext.getCurrentInstance().getViewRoot().setLocale(
                new Locale("es-Mx"));
        this.estado = "";
        this.id = "";
        this.unidadmaterial = new Unidadmaterial();
    }
    
     public String getEstado(){
        return estado;
    }
    
    public void setEstado(String estado){
        this.estado = estado;
    }
    
    public Unidadmaterial getUnidadMaterial(){
        return unidadmaterial;
    }
    
    public void setUnidadMatrial(Unidadmaterial unidadmaterial){
        this.unidadmaterial = unidadmaterial;
    }
    
     public String getId(){
        return id;
    }
    
    public void setId(String id){
        this.id = id;
    }
    
    public String cambiaEdo() throws NonexistentEntityException, Exception{
        UnidadmaterialJpaController umjpa = new UnidadmaterialJpaController(emf);
        Unidadmaterial umt = new Unidadmaterial();
        int idn = Integer.parseInt(id);
        umt = umjpa.findUnidadmaterial(idn);
        umt.setEstado(estado);
        umjpa.edit(umt);
        
         FacesContext.getCurrentInstance().addMessage(null,
                                            new FacesMessage(FacesMessage.SEVERITY_INFO,
                                               "Se ha cambiado el estado del material con id: " + id + " a estado: "+ umt.getEstado() , ""));
        return null;
    }
    
}
