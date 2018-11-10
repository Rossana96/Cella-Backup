/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package unam.mx.cella.modelo;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author eduar
 */
@Entity
@Table(name = "kit", schema = "cella")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Kit.findAll", query = "SELECT k FROM Kit k")
    , @NamedQuery(name = "Kit.findById", query = "SELECT k FROM Kit k WHERE k.id = :id")
    , @NamedQuery(name = "Kit.findByNombrekit", query = "SELECT k FROM Kit k WHERE k.nombrekit = :nombrekit")
    , @NamedQuery(name = "Kit.findByMateria", query = "SELECT k FROM Kit k WHERE k.materia = :materia")})
public class Kit implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "nombrekit")
    private String nombrekit;
    @Basic(optional = false)
    @Column(name = "materia")
    private String materia;
    @JoinColumn(name = "id_profesor", referencedColumnName = "id")
    @ManyToOne
    private Profesor idProfesor;

    public Kit() {
    }

    public Kit(Integer id) {
        this.id = id;
    }

    public Kit(Integer id, String nombrekit, String materia) {
        this.id = id;
        this.nombrekit = nombrekit;
        this.materia = materia;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombrekit() {
        return nombrekit;
    }

    public void setNombrekit(String nombrekit) {
        this.nombrekit = nombrekit;
    }

    public String getMateria() {
        return materia;
    }

    public void setMateria(String materia) {
        this.materia = materia;
    }

    public Profesor getIdProfesor() {
        return idProfesor;
    }

    public void setIdProfesor(Profesor idProfesor) {
        this.idProfesor = idProfesor;
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
        if (!(object instanceof Kit)) {
            return false;
        }
        Kit other = (Kit) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "unam.mx.cella.modelo.Kit[ id=" + id + " ]";
    }
    
}
