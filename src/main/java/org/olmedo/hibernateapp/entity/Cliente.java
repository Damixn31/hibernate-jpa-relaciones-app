package org.olmedo.hibernateapp.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="clientes")
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //aca le estamos diciendo que auto incremental el id
    private Long id;

    private String nombre;
    private String apellido;

    @Column(name = "forma_pago") // como en la tabla lo tenemos con _ le tenemos que pasar el nombre que tenemos en la tabla de la base de datos
    private String formaPago;

    // todo lo que contenga esta clase, atributos son tambien parte de esta clase entity
    @Embedded
    private Auditoria audit = new Auditoria(); // simpre es importante crear la instancia con el newAuditoria para usarla

    /*tenemos que colocar el tipo de cascasda -> colocamos All: esto quiere decir que cada ves que se guarda
    * o se crea un cliente automaticamente tambien va crear a su dependiente a sus relacionados a sus direcciones
    * si desbiculamos o eliminamos del cliente alguna direccion va a quedar con la llave foranea en null no va a estar asiganada
    * va aquedar huerfana, por eso le pasamos como segundo parametro orphanRemoval en true para que la remueva de manera automatica
    * */
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true) // @OneToMany -> uno a muchos. ej: un cliente muchas direcciones
    //@JoinColumn(name = "id_cliente") // Nota : la tabla intermedia solamente la crea cuando maneja de manera automatica la foreign keys(llave foranea)
    // por eso mejor pasarle al @JoinColumn(name = "algunNombre") asi desacoplamos las tablas, queda mas limpio

    // aca creamos una tabla intermedia pero personalizada
    // "tbl_clientes_direcciones" -> es el nombre de nuestra tabla intermedia
    // "id_cliente" -> es el nombre de la foreign key(llave foranea) principal
    // "id_direccion" -> llave foranea secuntaria, recordar que la secundaria es unica que los clientes se pueden repetir
    // y el cliente puede teener muchas direcciones
    @JoinTable(name = "tbl_clientes_direcciones", joinColumns = @JoinColumn(name = "id_cliente")
    , inverseJoinColumns = @JoinColumn(name = "id_direccion")
    , uniqueConstraints = @UniqueConstraint(columnNames = {"id_direccion"}))

    private List<Direccion> direcciones;

    //Relacion @OneToMany Bidireccional
     /*cualquier relacion bidireccional solo en el OneToMany de tercer parametro tenemos que indicar la relacion
      inversa con mappedBy -> con esto indicamos cual es el atributo o contraparte en este caso la clase factuta
      cual es el atributo mapeado a cliente, "factura" es la @Table donde esta la foreign keys(llave foranea)
      es la duenia de la relacion   */
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "cliente")
    private List<Factura> facturas; // en factura tenemos la foreing keys con @JoinColumn(name = "id_cliente")

    // uno a uno
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "cliente") // nunca puede estar
    // @JoinColumn cuando colocamos el mappedBy
    private ClienteDetalle detalle;

    // cuando tenemos un constructor que recive parametros siempre tenemos que tener un constructor vacio
    public Cliente() {
        facturas = new ArrayList<>(); // inicializamos facuturas
        direcciones = new ArrayList<>(); // otra manera de inicializar en el constructor
    }

    // aca pasamos un contructor con estos dos parametros para pasarlo a la consulta
    // que puebal y devuelve un objeto de una clase personalizada
    public Cliente(String nombre, String apellido) {
        this(); // con this llamamos al constructor vacio que contiene direcciones tambien para que lo inicialize
        this.nombre = nombre;
        this.apellido = apellido;
    }

    public Cliente(Long id, String nombre, String apellido, String formaPago) {
        this(); // tambien se le tiene que pasar el this en este constructor
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.formaPago = formaPago;
    }

    public ClienteDetalle getDetalle() {
        return detalle;
    }

    public void setDetalle(ClienteDetalle detalle) {
        this.detalle = detalle;
    }

    public void addDetalle(ClienteDetalle detalle) {
        this.detalle = detalle;
        detalle.setCliente(this);
    }

    public void removeDetalle() {
        detalle.setCliente(null);
        this.detalle = null;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getFormaPago() {
        return formaPago;
    }

    public void setFormaPago(String formaPago) {
        this.formaPago = formaPago;
    }

    public List<Direccion> getDirecciones() {
        return direcciones;
    }

    public void setDirecciones(List<Direccion> direcciones) {
        this.direcciones = direcciones;
    }

    public Auditoria getAudit() {
        return audit;
    }

    public void setAudit(Auditoria audit) {
        this.audit = audit;
    }

    public List<Factura> getFacturas() {
        return facturas;
    }

    public void setFacturas(List<Factura> facturas) {
        this.facturas = facturas;
    }

    //este metodo lo usamos en el HibernateAsociacionesOneToManyBidireccional
    public Cliente addFactura(Factura factura){
        this.facturas.add(factura);
        factura.setCliente(this);
        return this;
    }

    public void removeFactura(Factura factura){
        this.facturas.remove(factura);
        factura.setCliente(null);
    }

    @Override
    public String toString() {
        /* cuando generamos una consulta preguntamos si la tiene fecha null, para que no nos tire error, como teniamos
        en la base de datos Clientes con el creado y editado en null nos lanzaba un error*/
        LocalDateTime creado = this.audit != null ? audit.getCreadoEn() : null;
        LocalDateTime editado = this.audit != null ? audit.getEditadoEn() : null;
        return "{" + "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", apellido='" + apellido + '\'' +
                ", formaPago='" + formaPago + '\'' +
                ", creadoEn='" + creado + '\'' +
                ", editadoEn='" + editado + '\'' +
                ", direcciones='" + direcciones + '\'' +
                ", facturas='" + facturas + '\'' +
                ", detalle='" + detalle + '\'' +
                '}';
    }
}
