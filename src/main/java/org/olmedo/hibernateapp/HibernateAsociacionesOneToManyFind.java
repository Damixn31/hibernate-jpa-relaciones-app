package org.olmedo.hibernateapp;

import jakarta.persistence.EntityManager;
import org.olmedo.hibernateapp.entity.Cliente;
import org.olmedo.hibernateapp.entity.Direccion;
import org.olmedo.hibernateapp.util.JpaUtil;

public class HibernateAsociacionesOneToManyFind {
    public static void main(String[] args) {

        EntityManager em = JpaUtil.getEntityManager();

        try {
            em.getTransaction().begin();
            Cliente cliente = em.find(Cliente.class, 2L);

            //creamos direcciones para el cliente
            Direccion d1 = new Direccion("el vergel", 123);
            Direccion d2 = new Direccion("vasco de gama", 456);

            //aca establecemos la relacion entre cliente y ambas direcciones
            cliente.getDirecciones().add(d1);
            cliente.getDirecciones().add(d2);

            em.merge(cliente); // merge es para modificar a un cliente


            em.getTransaction().commit();

            System.out.println(cliente);

            // para actualizar el borrado de direccion
            em.getTransaction().begin();
            d1 = em.find(Direccion.class, 1L);
            cliente.getDirecciones().remove(d1);
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
