package org.olmedo.hibernateapp;

import jakarta.persistence.EntityManager;
import org.olmedo.hibernateapp.entity.Cliente;
import org.olmedo.hibernateapp.entity.Factura;
import org.olmedo.hibernateapp.util.JpaUtil;

public class HibernateAsociacionesManyToOneFind {
    public static void main(String[] args) {

        EntityManager em = JpaUtil.getEntityManager();

        try {
            em.getTransaction().begin();
            Cliente cliente = em.find(Cliente.class, 1L); // para buscarlo a un cliente existente

            Factura factura = new Factura("Compra de oficina", 1000L); // depues creamos la factura
            factura.setCliente(cliente);
            em.persist(factura);

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
