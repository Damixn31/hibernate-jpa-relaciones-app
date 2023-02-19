package org.olmedo.hibernateapp;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.*;
import org.olmedo.hibernateapp.entity.Cliente;
import org.olmedo.hibernateapp.entity.Factura;
import org.olmedo.hibernateapp.util.JpaUtil;

import java.util.List;

public class HibernateFetchManyToOneCriteria {
    public static void main(String[] args) {
        EntityManager em = JpaUtil.getEntityManager();


        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Factura> query = cb.createQuery(Factura.class);
        Root<Factura> factura = query.from(Factura.class);
        //factura.fetch("cliente", JoinType.LEFT); // join de factura a cliente
        Join<Factura, Cliente> cliente = (Join) factura.fetch("cliente", JoinType.LEFT);
        cliente.fetch("detalle", JoinType.LEFT);// join de cliente a detalle vamos a tener una sola consulta

        query.select(factura).where(cb.equal(cliente.get("id"), 1L));
        List<Factura> facturas = em.createQuery(query).getResultList();
        facturas.forEach(f -> System.out.println(f.getDescripcion() + ", cliente: " + f.getCliente().getNombre()));
        em.close();
    }
}
