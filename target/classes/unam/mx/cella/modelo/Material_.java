package unam.mx.cella.modelo;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import unam.mx.cella.modelo.ContenerKitMaterial;
import unam.mx.cella.modelo.PertenecerMaterialCategoria;
import unam.mx.cella.modelo.Unidadmaterial;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2018-10-22T15:33:25")
@StaticMetamodel(Material.class)
public class Material_ { 

    public static volatile SingularAttribute<Material, String> descripcion;
    public static volatile ListAttribute<Material, Unidadmaterial> unidadmaterialList;
    public static volatile SingularAttribute<Material, String> nombrematerial;
    public static volatile SingularAttribute<Material, byte[]> foto;
    public static volatile ListAttribute<Material, ContenerKitMaterial> contenerKitMaterialList;
    public static volatile ListAttribute<Material, PertenecerMaterialCategoria> pertenecerMaterialCategoriaList;
    public static volatile SingularAttribute<Material, Integer> id;

}