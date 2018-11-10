package unam.mx.cella.modelo;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import unam.mx.cella.modelo.Categoria;
import unam.mx.cella.modelo.Material;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2018-10-22T15:33:25")
@StaticMetamodel(PertenecerMaterialCategoria.class)
public class PertenecerMaterialCategoria_ { 

    public static volatile SingularAttribute<PertenecerMaterialCategoria, String> nombrematerial;
    public static volatile SingularAttribute<PertenecerMaterialCategoria, String> nombrecategoria;
    public static volatile SingularAttribute<PertenecerMaterialCategoria, Material> idMaterial;
    public static volatile SingularAttribute<PertenecerMaterialCategoria, Integer> id;
    public static volatile SingularAttribute<PertenecerMaterialCategoria, Categoria> idCategoria;

}