package org.olmedo.hibernateapp;

import jakarta.persistence.EntityManager;
import org.olmedo.hibernateapp.entity.Cliente;
import org.olmedo.hibernateapp.entity.Factura;
import org.olmedo.hibernateapp.util.JpaUtil;

public class HibernateAsociacionesOneToManyBidireccional {
    public static void main(String[] args) {

        EntityManager em = JpaUtil.getEntityManager();

        try {
            em.getTransaction().begin();
            Cliente cliente = new Cliente("Cata", "Edu");
            cliente.setFormaPago("paypal");

            Factura f1 = new Factura("compra de supermercado", 50000L);
            Factura f2 = new Factura("compra de tecnologia", 70000L);

            // ahora la tenemos que relacionar por los dos lados
            cliente.addFactura(f1).addFactura(f2);

            // despues persistimos el cliente lo guardamos
            em.persist(cliente);

            em.getTransaction().commit(); //commit() para refeljar los cambios en la base de datos
            System.out.println(cliente);

            em.getTransaction().begin();
            //buscamos por id la facutura
            //Factura f3 = em.find(Factura.class, 1L);
            //esta seria otra forma de eliminar
            Factura f3 = new Factura("compra de supermercado", 50000L);
            // le asiganmos el id
            f3.setId(1L);
            cliente.removeFactura(f3); //eliminamos primero por el lado del cliente


            em.getTransaction().commit();
            System.out.println(cliente);

        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }
    }
}
