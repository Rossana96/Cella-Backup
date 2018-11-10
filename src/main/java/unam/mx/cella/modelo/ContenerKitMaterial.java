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
@Table(name = "contener_kit_material", schema = "cella")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ContenerKitMaterial.findAll", query = "SELECT c FROM ContenerKitMaterial c")
    , @NamedQuery(name = "ContenerKitMaterial.findById", query = "SELECT c FROM ContenerKitMaterial c WHERE c.id = :id")
    , @NamedQuery(name = "ContenerKitMaterial.findByNombrematerial", query = "SELECT c FROM ContenerKitMaterial c WHERE c.nombrematerial = :nombrematerial")
    , @NamedQuery(name = "ContenerKitMaterial.findByNombrekit", query = "SELECT c FROM ContenerKitMaterial c WHERE c.nombrekit = :nombrekit")
    , @NamedQuery(name = "ContenerKitMaterial.findByNounidades", query = "SELECT c FROM ContenerKitMaterial c WHERE c.nounidades = :nounidades")})
public class ContenerKitMaterial implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "nombrematerial")
    private String nombrematerial;
    @Column(name = "nombrekit")
    private String nombrekit;
    @Basic(optional = false)
    @Column(name = "nounidades")
    private int nounidades;
    @JoinColumn(name = "id_material", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Material idMaterial;

    public ContenerKitMaterial() {
    }

    public ContenerKitMaterial(Integer id) {
        this.id = id;
    }

    public ContenerKitMaterial(Integer id, int nounidades) {
        this.id = id;
        this.nounidades = nounidades;
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

    public String getNombrekit() {
        return nombrekit;
    }

    public void setNombrekit(String nombrekit) {
        this.nombrekit = nombrekit;
    }

    public int getNounidades() {
        return nounidades;
    }

    public void setNounidades(int nounidades) {
        this.nounidades = nounidades;
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
        if (!(object instanceof ContenerKitMaterial)) {
            return false;
        }
        ContenerKitMaterial other = (ContenerKitMaterial) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "unam.mx.cella.modelo.ContenerKitMaterial[ id=" + id + " ]";
    }
    
}
