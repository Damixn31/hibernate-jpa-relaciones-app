package org.olmedo.hibernateapp.entity;

import jakarta.persistence.*;



@Entity
@Table(name = "facturas")
public class Factura {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String descripcion;
    private Long total;

    @ManyToOne // -> mucho a uno. ej: muchas factura un cliente
    @JoinColumn(name = "id_cliente") // con esto le indicamos de modo manual la foreign keys(llave foranea)
    //cuando hacemos este cambio de foreign key y estamos usando drop  para que tome la nueva llave forenea
    //es mejor eliminar las tablas de la base de datos para que la vuelva a crear desde cero
    private Cliente cliente;

    public Factura() {
    }

    // solamente le pasamos en el constructor el descripcion y el total, porque el id se maneja
    // de manera automatica y el cliente es la relacion
    public Factura(String descripcion, Long total) {
        this.descripcion = descripcion;
        this.total = total;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    @Override
    public String toString() {
        return "{" +
                "id=" + id +
                ", descripcion='" + descripcion + '\'' +
                ", total=" + total +
                ", cliente=" + cliente +
                '}';
    }
}
