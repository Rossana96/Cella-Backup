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
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
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
@Table(name = "unidadmaterial", schema = "cella")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Unidadmaterial.findAll", query = "SELECT u FROM Unidadmaterial u")
    , @NamedQuery(name = "Unidadmaterial.findById", query = "SELECT u FROM Unidadmaterial u WHERE u.id = :id")
    , @NamedQuery(name = "Unidadmaterial.findByNombrematerial", query = "SELECT u FROM Unidadmaterial u WHERE u.nombrematerial = :nombrematerial")
    , @NamedQuery(name = "Unidadmaterial.findByEstado", query = "SELECT u FROM Unidadmaterial u WHERE u.estado = :estado")})
public class Unidadmaterial implements Serializable {

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
    @Column(name = "estado")
    private String estado;
    @Lob
    @Column(name = "foto")
    private byte[] foto;
    @JoinColumn(name = "id_material", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Material idMaterial;
    @OneToMany(mappedBy = "idUnidadMaterial")
    private Collection<Solicitarprestamoprofesor> solicitarprestamoprofesorCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idUnidadMaterial")
    private Collection<Solicitarprestamoalumno> solicitarprestamoalumnoCollection;

    public Unidadmaterial() {
    }

    public Unidadmaterial(Integer id) {
        this.id = id;
    }

    public Unidadmaterial(Integer id, String nombrematerial, String estado) {
        this.id = id;
        this.nombrematerial = nombrematerial;
        this.estado = estado;
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

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public byte[] getFoto() {
        return foto;
    }

    public void setFoto(byte[] foto) {
        this.foto = foto;
    }

    public Material getIdMaterial() {
        return idMaterial;
    }

    public void setIdMaterial(Material idMaterial) {
        this.idMaterial = idMaterial;
    }

    @XmlTransient
    public Collection<Solicitarprestamoprofesor> getSolicitarprestamoprofesorCollection() {
        return solicitarprestamoprofesorCollection;
    }

    public void setSolicitarprestamoprofesorCollection(Collection<Solicitarprestamoprofesor> solicitarprestamoprofesorCollection) {
        this.solicitarprestamoprofesorCollection = solicitarprestamoprofesorCollection;
    }

    @XmlTransient
    public Collection<Solicitarprestamoalumno> getSolicitarprestamoalumnoCollection() {
        return solicitarprestamoalumnoCollection;
    }

    public void setSolicitarprestamoalumnoCollection(Collection<Solicitarprestamoalumno> solicitarprestamoalumnoCollection) {
        this.solicitarprestamoalumnoCollection = solicitarprestamoalumnoCollection;
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
        if (!(object instanceof Unidadmaterial)) {
            return false;
        }
        Unidadmaterial other = (Unidadmaterial) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "unam.mx.cella.modelo.Unidadmaterial[ id=" + id + " ]";
    }
    
}
