package org.olmedo.hibernateapp;

import jakarta.persistence.EntityManager;
import org.olmedo.hibernateapp.entity.Cliente;
import org.olmedo.hibernateapp.util.JpaUtil;

import java.util.List;

public class HibernateFetchResultList {
    public static void main(String[] args) {

        EntityManager em = JpaUtil.getEntityManager();
        // con el distinct el cliente no se va a repetir
        // va a tirar error si hago muchas consultas en una misma lo mejor es generar otra apartes
        List<Cliente> clientes = em.createQuery("select distinct c from Cliente c left outer join fetch c.direcciones left outer join fetch c.detalle", Cliente.class).getResultList();

        clientes.forEach(c -> System.out.println(c.getNombre() + ", direcciones: " + c.getDirecciones()));
        em.close();
    }
}
