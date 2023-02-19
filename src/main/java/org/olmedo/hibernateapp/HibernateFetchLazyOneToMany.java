package org.olmedo.hibernateapp;

import jakarta.persistence.EntityManager;
import org.olmedo.hibernateapp.entity.Cliente;
import org.olmedo.hibernateapp.util.JpaUtil;

public class HibernateFetchLazyOneToMany {
    public static void main(String[] args) {

        EntityManager em = JpaUtil.getEntityManager();

        // como son consultas aca no va el try catch

        // buscar por id
        Cliente cliente = em.find(Cliente.class, 1L);
        System.out.println(cliente.getNombre() + ", direcciones: " + cliente.getDirecciones());

        em.close();
    }
}
