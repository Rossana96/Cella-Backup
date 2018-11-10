package unam.mx.cella.modelo;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import unam.mx.cella.modelo.PertenecerMaterialCategoria;
import unam.mx.cella.modelo.Subcategorias;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2018-10-22T15:33:25")
@StaticMetamodel(Categoria.class)
public class Categoria_ { 

    public static volatile SingularAttribute<Categoria, String> descripcion;
    public static volatile ListAttribute<Categoria, PertenecerMaterialCategoria> pertenecerMaterialCategoriaList;
    public static volatile SingularAttribute<Categoria, String> nombrecategoria;
    public static volatile ListAttribute<Categoria, Subcategorias> subcategoriasList;
    public static volatile SingularAttribute<Categoria, Integer> id;

}