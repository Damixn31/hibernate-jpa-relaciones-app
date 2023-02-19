package org.olmedo.hibernateapp;

import jakarta.persistence.EntityManager;
import org.olmedo.hibernateapp.entity.Cliente;
import org.olmedo.hibernateapp.util.JpaUtil;

public class HibernateFetchLazyOneToManyJoinFetch {
    public static void main(String[] args) {

        EntityManager em = JpaUtil.getEntityManager();

        // como son consultas aca no va el try catch

        // buscar por id
        // con left join enlazamos y hacemos un join entre cliente y las direcciones
        /* left join -> va a traer a todos los clientes que tengas o no direcciones, por eso es mejor usar
        left join */
        // left outer join -> es lo mismo left join opcional
        /* inner join -> va atraer solamente los clientes que tengan direcciones, pero si no tienen
        direcciones asiociadas no los va a traer*/
        // fetch -> para ir a buscar direcciones y se lo pasamos al objeto cliente
        Cliente cliente = em.createQuery("select c from Cliente c left outer join fetch c.direcciones left join fetch c.detalle where c.id=:id", Cliente.class)
                .setParameter("id", 1L)
                .getSingleResult();
        System.out.println(cliente.getNombre() + ", direcciones: " + cliente.getDirecciones());
        System.out.println(cliente.getNombre() + ", detalle: " + cliente.getDetalle());

        em.close();
    }
}
