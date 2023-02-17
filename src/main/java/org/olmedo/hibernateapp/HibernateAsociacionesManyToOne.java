package org.olmedo.hibernateapp;

import jakarta.persistence.EntityManager;
import org.olmedo.hibernateapp.entity.Cliente;
import org.olmedo.hibernateapp.entity.Factura;
import org.olmedo.hibernateapp.util.JpaUtil;

public class HibernateAsociacionesManyToOne {
    public static void main(String[] args) {

        EntityManager em = JpaUtil.getEntityManager();

        try {
            em.getTransaction().begin();
            Cliente cliente = new Cliente("Cata", "Edu"); // creamos el cliente
            cliente.setFormaPago("credito");
            em.persist(cliente); // guardamos en la base de datos el cliente que creamos por eso el persist

            Factura factura = new Factura("Compra de oficina", 1000L); // creamos la factura
            factura.setCliente(cliente); // le asociamos al cliente, cliente tiene que ser un cliente que exista en la base de datos
            em.persist(factura); //guardamos la factura

            System.out.println(factura.getCliente());

            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }

    }
}
