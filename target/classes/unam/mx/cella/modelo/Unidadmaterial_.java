package unam.mx.cella.modelo;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import unam.mx.cella.modelo.Material;
import unam.mx.cella.modelo.Solicitarprestamoalumno;
import unam.mx.cella.modelo.Solicitarprestamoprofesor;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2018-10-22T15:33:25")
@StaticMetamodel(Unidadmaterial.class)
public class Unidadmaterial_ { 

    public static volatile SingularAttribute<Unidadmaterial, String> estado;
    public static volatile SingularAttribute<Unidadmaterial, String> nombrematerial;
    public static volatile SingularAttribute<Unidadmaterial, byte[]> foto;
    public static volatile ListAttribute<Unidadmaterial, Solicitarprestamoprofesor> solicitarprestamoprofesorList;
    public static volatile ListAttribute<Unidadmaterial, Solicitarprestamoalumno> solicitarprestamoalumnoList;
    public static volatile SingularAttribute<Unidadmaterial, Material> idMaterial;
    public static volatile SingularAttribute<Unidadmaterial, Integer> id;

}