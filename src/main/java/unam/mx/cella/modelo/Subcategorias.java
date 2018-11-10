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
@Table(name = "subcategorias", schema = "cella")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Subcategorias.findAll", query = "SELECT s FROM Subcategorias s")
    , @NamedQuery(name = "Subcategorias.findById", query = "SELECT s FROM Subcategorias s WHERE s.id = :id")
    , @NamedQuery(name = "Subcategorias.findByNombrecategoria", query = "SELECT s FROM Subcategorias s WHERE s.nombrecategoria = :nombrecategoria")
    , @NamedQuery(name = "Subcategorias.findByNombresubcategoria", query = "SELECT s FROM Subcategorias s WHERE s.nombresubcategoria = :nombresubcategoria")})
public class Subcategorias implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "nombrecategoria")
    private String nombrecategoria;
    @Column(name = "nombresubcategoria")
    private String nombresubcategoria;
    @JoinColumn(name = "id_categoria", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Categoria idCategoria;

    public Subcategorias() {
    }

    public Subcategorias(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombrecategoria() {
        return nombrecategoria;
    }

    public void setNombrecategoria(String nombrecategoria) {
        this.nombrecategoria = nombrecategoria;
    }

    public String getNombresubcategoria() {
        return nombresubcategoria;
    }

    public void setNombresubcategoria(String nombresubcategoria) {
        this.nombresubcategoria = nombresubcategoria;
    }

    public Categoria getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(Categoria idCategoria) {
        this.idCategoria = idCategoria;
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
        if (!(object instanceof Subcategorias)) {
            return false;
        }
        Subcategorias other = (Subcategorias) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "unam.mx.cella.modelo.Subcategorias[ id=" + id + " ]";
    }
    
}
