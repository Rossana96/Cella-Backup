/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package unam.mx.cella.controller;
import java.util.Locale;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.application.FacesMessage;
import javax.persistence.EntityManagerFactory;
import unam.mx.cella.modelo.Profesor;
import unam.mx.cella.modelo.EntityProvider;
import unam.mx.cella.modelo.ProfesorJpaController;
/**
 *
 * @author eduar
 */
@ManagedBean
@RequestScoped
public class PreRegistroController {

    private final EntityManagerFactory emf;
    private Profesor profesor;
    private String confirmacion;
    
    
    public String getConfirmacion(){
        return confirmacion;
    }
    
    public void setConfirmacion(String confirmacion){
        this.confirmacion = confirmacion;
    }
    
    public Profesor getProfesor() {
        return profesor;
    }
    
    

    public void setProfesor(Profesor profesor) {
        this.profesor = profesor;
    }
    /**
     * Creates a new instance of PreRegistroController
     */
    public PreRegistroController() {
        emf = EntityProvider.provider();
        System.out.println("creado");
        FacesContext.getCurrentInstance().getViewRoot().setLocale(
                new Locale("es-Mx"));
        this.profesor = new Profesor();
        confirmacion = "";
    
    }
    
     public boolean verificaUsuario(String userName){
        
        ProfesorJpaController ajpa = new ProfesorJpaController(emf);
        return ajpa.findProfesor(userName) == null;
    }
    
     public boolean verificaCorreo(String correo){
        
        ProfesorJpaController ajpa = new ProfesorJpaController(emf);
        return ajpa.findCorreo(correo) == null;
    }
    
    public String addUser() {
         
        if(!(verificaUsuario(profesor.getNombreusuario()))){
            FacesContext.getCurrentInstance().addMessage(null
            , new FacesMessage(FacesMessage.SEVERITY_ERROR, 
                    "Fallo de registro: Nombre de usuario existente, elige otro", ""));
        } 
        
        else if(!(verificaCorreo(profesor.getCorreo()))){
            FacesContext.getCurrentInstance().addMessage(null
            , new FacesMessage(FacesMessage.SEVERITY_ERROR, 
                    "Fallo de registro: correo ya registrado en el sistema", ""));
        }  
        else if (!profesor.getContrasena().equals(confirmacion)) {
            FacesContext.getCurrentInstance().addMessage(null
            , new FacesMessage(FacesMessage.SEVERITY_ERROR, 
                    "Fallo de registro: Las contraseñas deben coincidir", ""));
        } else {
           // LoginJpaController ljpa = new LoginJpaController(emf);
            ProfesorJpaController pjpa = new ProfesorJpaController(emf);

           // Login login = new Login();
            //login.setUsuario(usuario);
            //login.setPassword(contraseña);
            //ljpa.create(login);
            //login = ljpa.findLoginByUsuario(usuario);

            Profesor prof = new Profesor();
            //alum.setLoginId(login.getId());
           
            prof.setNombre(profesor.getNombre());
            prof.setApellidop(profesor.getApellidop());
            prof.setApellidom(profesor.getApellidom());
            prof.setCorreo(profesor.getCorreo());
            prof.setNombreusuario(profesor.getNombreusuario());
            prof.setContrasena(profesor.getContrasena());
            prof.setEdocuenta(true);
            prof.setNotrabajador(profesor.getNotrabajador());
            prof.setRfc(profesor.getRfc());
            
            pjpa.create(prof);

            FacesContext.getCurrentInstance().addMessage(null,
                                                         new FacesMessage (FacesMessage.SEVERITY_INFO,
                                                                          "Felicidades, el registro se ha realizado correctamente", ""));
        } 
        return null;
    }

}
