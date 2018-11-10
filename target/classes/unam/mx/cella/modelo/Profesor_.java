package unam.mx.cella.modelo;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import unam.mx.cella.modelo.Kit;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2018-10-22T15:33:25")
@StaticMetamodel(Profesor.class)
public class Profesor_ { 

    public static volatile SingularAttribute<Profesor, String> nombreusuario;
    public static volatile SingularAttribute<Profesor, Boolean> edocuenta;
    public static volatile SingularAttribute<Profesor, String> notrabajador;
    public static volatile SingularAttribute<Profesor, byte[]> foto;
    public static volatile SingularAttribute<Profesor, String> apellidop;
    public static volatile SingularAttribute<Profesor, String> correo;
    public static volatile SingularAttribute<Profesor, String> contrasena;
    public static volatile SingularAttribute<Profesor, String> apellidom;
    public static volatile ListAttribute<Profesor, Kit> kitList;
    public static volatile SingularAttribute<Profesor, Integer> id;
    public static volatile SingularAttribute<Profesor, String> nombre;
    public static volatile SingularAttribute<Profesor, String> rfc;

}