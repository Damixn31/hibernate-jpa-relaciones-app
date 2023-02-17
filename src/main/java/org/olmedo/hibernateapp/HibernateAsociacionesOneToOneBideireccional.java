package org.olmedo.hibernateapp;

import jakarta.persistence.EntityManager;
import org.olmedo.hibernateapp.entity.Cliente;
import org.olmedo.hibernateapp.entity.ClienteDetalle;
import org.olmedo.hibernateapp.util.JpaUtil;

public class HibernateAsociacionesOneToOneBideireccional {
    public static void main(String[] args) {

        EntityManager em = JpaUtil.getEntityManager();

        try {
            em.getTransaction().begin();
            Cliente cliente = new Cliente("Cata", "Edu");
            cliente.setFormaPago("paypal");

            ClienteDetalle detalle = new ClienteDetalle(true, 8000L);

            cliente.addDetalle(detalle);

            em.persist(cliente);
            em.getTransaction().commit();
            System.out.println(cliente);
        } catch (Exception e ) {
            em.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }
    }
}
