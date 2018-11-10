/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package unam.mx.cella.modelo;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
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
@Table(name = "material", schema = "cella")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Material.findAll", query = "SELECT m FROM Material m")
    , @NamedQuery(name = "Material.findById", query = "SELECT m FROM Material m WHERE m.id = :id")
    , @NamedQuery(name = "Material.findByNombrematerial", query = "SELECT m FROM Material m WHERE m.nombrematerial = :nombrematerial")
    , @NamedQuery(name = "Material.findByDescripcion", query = "SELECT m FROM Material m WHERE m.descripcion = :descripcion")})
public class Material implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "nombrematerial")
    private String nombrematerial;
    @Basic(optional = false)
    @Column(name = "descripcion")
    private String descripcion;
    @Lob
    @Column(name = "foto")
    private byte[] foto;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idMaterial")
    private Collection<ContenerKitMaterial> contenerKitMaterialCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idMaterial")
    private Collection<Unidadmaterial> unidadmaterialCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idMaterial")
    private Collection<PertenecerMaterialCategoria> pertenecerMaterialCategoriaCollection;

    public Material() {
    }

    public Material(Integer id) {
        this.id = id;
    }

    public Material(Integer id, String nombrematerial, String descripcion) {
        this.id = id;
        this.nombrematerial = nombrematerial;
        this.descripcion = descripcion;
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

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public byte[] getFoto() {
        return foto;
    }

    public void setFoto(byte[] foto) {
        this.foto = foto;
    }

    @XmlTransient
    public Collection<ContenerKitMaterial> getContenerKitMaterialCollection() {
        return contenerKitMaterialCollection;
    }

    public void setContenerKitMaterialCollection(Collection<ContenerKitMaterial> contenerKitMaterialCollection) {
        this.contenerKitMaterialCollection = contenerKitMaterialCollection;
    }

    @XmlTransient
    public Collection<Unidadmaterial> getUnidadmaterialCollection() {
        return unidadmaterialCollection;
    }

    public void setUnidadmaterialCollection(Collection<Unidadmaterial> unidadmaterialCollection) {
        this.unidadmaterialCollection = unidadmaterialCollection;
    }

    @XmlTransient
    public Collection<PertenecerMaterialCategoria> getPertenecerMaterialCategoriaCollection() {
        return pertenecerMaterialCategoriaCollection;
    }

    public void setPertenecerMaterialCategoriaCollection(Collection<PertenecerMaterialCategoria> pertenecerMaterialCategoriaCollection) {
        this.pertenecerMaterialCategoriaCollection = pertenecerMaterialCategoriaCollection;
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
        if (!(object instanceof Material)) {
            return false;
        }
        Material other = (Material) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "unam.mx.cella.modelo.Material[ id=" + id + " ]";
    }
    
}
