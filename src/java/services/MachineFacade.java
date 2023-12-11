/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import entities.Employe;
import entities.Machine;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.Tuple;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Root;

/**
 *
 * @author hp
 */
@Stateless
public class MachineFacade extends AbstractFacade<Machine> {
    @PersistenceContext(unitName = "controle-ejb-jsfPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public MachineFacade() {
        super(Machine.class);
    }
    public List<Machine> getMachinesForEmployee(Employe employe) {
        TypedQuery<Machine> query = em.createNamedQuery("Machine.findByEmploy", Machine.class);
        query.setParameter("employe", employe);
        return query.getResultList();
    }
    public Map<Integer, Long> getMachinesCountByYear() {
    CriteriaBuilder cb = em.getCriteriaBuilder();
    CriteriaQuery<Tuple> cq = cb.createTupleQuery();
    Root<Machine> machineRoot = cq.from(Machine.class);

    // Include all years in the result
    Expression<Integer> yearFunction = cb.function("YEAR", Integer.class, machineRoot.get("dateAchat"));
    cq.multiselect(
            yearFunction.alias("year"),
            cb.count(machineRoot).alias("count")
    );

    cq.groupBy(yearFunction);

    List<Tuple> resultList = em.createQuery(cq).getResultList();

    Map<Integer, Long> result = new HashMap<>();
    for (Tuple tuple : resultList) {
        result.put((Integer) tuple.get("year"), (Long) tuple.get("count"));
    }

    return result;
}

}
