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
@Table(name = "pertenecer_material_categoria", schema = "cella")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PertenecerMaterialCategoria.findAll", query = "SELECT p FROM PertenecerMaterialCategoria p")
    , @NamedQuery(name = "PertenecerMaterialCategoria.findById", query = "SELECT p FROM PertenecerMaterialCategoria p WHERE p.id = :id")
    , @NamedQuery(name = "PertenecerMaterialCategoria.findByNombrematerial", query = "SELECT p FROM PertenecerMaterialCategoria p WHERE p.nombrematerial = :nombrematerial")
    , @NamedQuery(name = "PertenecerMaterialCategoria.findByNombrecategoria", query = "SELECT p FROM PertenecerMaterialCategoria p WHERE p.nombrecategoria = :nombrecategoria")})
public class PertenecerMaterialCategoria implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "nombrematerial")
    private String nombrematerial;
    @Column(name = "nombrecategoria")
    private String nombrecategoria;
    @JoinColumn(name = "id_categoria", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Categoria idCategoria;
    @JoinColumn(name = "id_material", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Material idMaterial;

    public PertenecerMaterialCategoria() {
    }

    public PertenecerMaterialCategoria(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombrematerial() {
        return nombrematerial;
    }

    public void setNombrematerial(String nombrematerial) {
        this.nombrematerial = nombrematerial;
    }

    public String getNombrecategoria() {
        return nombrecategoria;
    }

    public void setNombrecategoria(String nombrecategoria) {
        this.nombrecategoria = nombrecategoria;
    }

    public Categoria getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(Categoria idCategoria) {
        this.idCategoria = idCategoria;
    }

    public Material getIdMaterial() {
        return idMaterial;
    }

    public void setIdMaterial(Material idMaterial) {
        this.idMaterial = idMaterial;
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
        if (!(object instanceof PertenecerMaterialCategoria)) {
            return false;
        }
        PertenecerMaterialCategoria other = (PertenecerMaterialCategoria) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "unam.mx.cella.modelo.PertenecerMaterialCategoria[ id=" + id + " ]";
    }
    
}
