/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package unam.mx.cella.modelo;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author eduar
 */
@Entity
@Table(name = "profesor", schema = "cella")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Profesor.findAll", query = "SELECT p FROM Profesor p")
    , @NamedQuery(name = "Profesor.findById", query = "SELECT p FROM Profesor p WHERE p.id = :id")
    , @NamedQuery(name = "Profesor.findByNombreusuario", query = "SELECT p FROM Profesor p WHERE p.nombreusuario = :nombreusuario")
    , @NamedQuery(name = "Profesor.findByCorreo", query = "SELECT p FROM Profesor p WHERE p.correo = :correo")
    , @NamedQuery(name = "Profesor.findByContrasena", query = "SELECT p FROM Profesor p WHERE p.contrasena = :contrasena")
    , @NamedQuery(name = "Profesor.findByNombre", query = "SELECT p FROM Profesor p WHERE p.nombre = :nombre")
    , @NamedQuery(name = "Profesor.findByApellidop", query = "SELECT p FROM Profesor p WHERE p.apellidop = :apellidop")
    , @NamedQuery(name = "Profesor.findByApellidom", query = "SELECT p FROM Profesor p WHERE p.apellidom = :apellidom")
    , @NamedQuery(name = "Profesor.findByEdocuenta", query = "SELECT p FROM Profesor p WHERE p.edocuenta = :edocuenta")
    , @NamedQuery(name = "Profesor.findByRfc", query = "SELECT p FROM Profesor p WHERE p.rfc = :rfc")
    , @NamedQuery(name = "Profesor.findByNotrabajador", query = "SELECT p FROM Profesor p WHERE p.notrabajador = :notrabajador")})
public class Profesor implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "nombreusuario")
    private String nombreusuario;
    @Basic(optional = false)
    @Column(name = "correo")
    private String correo;
    @Basic(optional = false)
    @Column(name = "contrasena")
    private String contrasena;
    @Basic(optional = false)
    @Column(name = "nombre")
    private String nombre;
    @Basic(optional = false)
    @Column(name = "apellidop")
    private String apellidop;
    @Basic(optional = false)
    @Column(name = "apellidom")
    private String apellidom;
    @Lob
    @Column(name = "foto")
    private byte[] foto;
    @Basic(optional = false)
    @Column(name = "edocuenta")
    private boolean edocuenta;
    @Basic(optional = false)
    @Column(name = "rfc")
    private String rfc;
    @Basic(optional = false)
    @Column(name = "notrabajador")
    private String notrabajador;
    @OneToMany(mappedBy = "idProfesor")
    private Collection<Kit> kitCollection;

    public Profesor() {
    }

    public Profesor(Integer id) {
        this.id = id;
    }

    public Profesor(Integer id, String nombreusuario, String correo, String contrasena, String nombre, String apellidop, String apellidom, boolean edocuenta, String rfc, String notrabajador) {
        this.id = id;
        this.nombreusuario = nombreusuario;
        this.correo = correo;
        this.contrasena = contrasena;
        this.nombre = nombre;
        this.apellidop = apellidop;
        this.apellidom = apellidom;
        this.edocuenta = edocuenta;
        this.rfc = rfc;
        this.notrabajador = notrabajador;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombreusuario() {
        return nombreusuario;
    }

    public void setNombreusuario(String nombreusuario) {
        this.nombreusuario = nombreusuario;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidop() {
        return apellidop;
    }

    public void setApellidop(String apellidop) {
        this.apellidop = apellidop;
    }

    public String getApellidom() {
        return apellidom;
    }

    public void setApellidom(String apellidom) {
        this.apellidom = apellidom;
    }

    public byte[] getFoto() {
        return foto;
    }

    public void setFoto(byte[] foto) {
        this.foto = foto;
    }

    public boolean getEdocuenta() {
        return edocuenta;
    }

    public void setEdocuenta(boolean edocuenta) {
        this.edocuenta = edocuenta;
    }

    public String getRfc() {
        return rfc;
    }

    public void setRfc(String rfc) {
        this.rfc = rfc;
    }

    public String getNotrabajador() {
        return notrabajador;
    }

    public void setNotrabajador(String notrabajador) {
        this.notrabajador = notrabajador;
    }

    @XmlTransient
    public Collection<Kit> getKitCollection() {
        return kitCollection;
    }

    public void setKitCollection(Collection<Kit> kitCollection) {
        this.kitCollection = kitCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Profesor)) {
            return false;
        }
        Profesor other = (Profesor) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "unam.mx.cella.modelo.Profesor[ id=" + id + " ]";
    }
    
}
