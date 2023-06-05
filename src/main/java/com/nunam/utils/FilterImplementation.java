package com.nunam.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.lang.model.util.ElementScanner6;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class FilterImplementation<T> {

	@PersistenceContext
	private EntityManager entityManager;

	public List<T> filter(Map<String, Object> filter, String orderby, Integer offset, Integer limit, T to) {
		
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<T> query = (CriteriaQuery<T>) cb.createQuery(to.getClass());
        Root<T> t = (Root<T>) query.from(to.getClass());
        List<Predicate> predicates = new ArrayList();
        Predicate finalPredicate = null;
        if(filter.containsKey("and"))
        {
            Map<String, Object> andCriteria = (Map<String, Object>) filter.get("and");
            for (Map.Entry<String, Object> criterion : andCriteria.entrySet()) {
                String key = criterion.getKey();
                Path<String> attributePath = t.get(key);
                Predicate predicate = cb.equal(attributePath, criterion.getValue());
                predicates.add(predicate);
            }
            finalPredicate = cb.and(predicates.toArray(new Predicate[predicates.size()]));
        }
        else if(filter.containsKey("or"))
        {
            Map<String, Object> orCriteria = (Map<String, Object>) filter.get("or");
            for (Map.Entry<String, Object> criterion : orCriteria.entrySet()) {
                String key = criterion.getKey();
                Path<String> attributePath = t.get(key);
                Predicate predicate = cb.equal(attributePath, criterion.getValue());
                predicates.add(predicate);
            }
            finalPredicate = cb.or(predicates.toArray(new Predicate[predicates.size()]));
        }
        else
        {
            for(String key: filter.keySet()) {
                Path<String> party = t.get(key);
                predicates.add(cb.equal(party,filter.get(key)));
            }
            finalPredicate = cb.and(predicates.toArray(new Predicate[predicates.size()]));
        }
       
        query.select(t)
            .where(finalPredicate);
        
        if (orderby != null && !orderby.isEmpty()) {
            Path<?> orderPath = t.get(orderby);
            query.orderBy(cb.desc(orderPath));
        }
        return entityManager.createQuery(query)
            .setFirstResult(offset).setMaxResults(limit).getResultList();
	}

	public Long filterCount(Map<String, Object> filter, T to) {
		
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Long> cq = cb.createQuery(Long.class);
        Root<T> t = (Root<T>) cq.from(to.getClass());
        List<Predicate> predicates = new ArrayList();
        Predicate finalPredicate = null;
        if(filter.containsKey("and"))
        {
            Map<String, Object> andCriteria = (Map<String, Object>) filter.get("and");
            for (Map.Entry<String, Object> criterion : andCriteria.entrySet()) {
                String key = criterion.getKey();
                Path<String> attributePath = t.get(key);
                Predicate predicate = cb.equal(attributePath, criterion.getValue());
                predicates.add(predicate);
            }
            finalPredicate = cb.and(predicates.toArray(new Predicate[predicates.size()]));
        }
        else if(filter.containsKey("or"))
        {
            Map<String, Object> orCriteria = (Map<String, Object>) filter.get("or");
            for (Map.Entry<String, Object> criterion : orCriteria.entrySet()) {
                String key = criterion.getKey();
                Path<String> attributePath = t.get(key);
                Predicate predicate = cb.equal(attributePath, criterion.getValue());
                predicates.add(predicate);
            }
            finalPredicate = cb.or(predicates.toArray(new Predicate[predicates.size()]));
        }
        else
        {
            for(String key: filter.keySet()) {
                Path<String> party = t.get(key);
                predicates.add(cb.equal(party,filter.get(key)));
            }
            finalPredicate = cb.and(predicates.toArray(new Predicate[predicates.size()]));
        }
        
        
        cq.select(cb.count(t));
        cq.where(finalPredicate);
        return entityManager.createQuery(cq).getSingleResult();
	}
	
	public List<T> filter(Map<String, Object> filter, T to) {
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<T> query = (CriteriaQuery<T>) cb.createQuery(to.getClass());
        Root<T> t = (Root<T>) query.from(to.getClass());
        List<Predicate> predicates = new ArrayList();
        Predicate finalPredicate = null;
        if(filter.containsKey("and"))
        {
            Map<String, Object> andCriteria = (Map<String, Object>) filter.get("and");
            for (Map.Entry<String, Object> criterion : andCriteria.entrySet()) {
                String key = criterion.getKey();
                Path<String> attributePath = t.get(key);
                Predicate predicate = cb.equal(attributePath, criterion.getValue());
                predicates.add(predicate);
            }
            finalPredicate = cb.and(predicates.toArray(new Predicate[predicates.size()]));
        }
        else if(filter.containsKey("or"))
        {
            Map<String, Object> orCriteria = (Map<String, Object>) filter.get("or");
            for (Map.Entry<String, Object> criterion : orCriteria.entrySet()) {
                String key = criterion.getKey();
                Path<String> attributePath = t.get(key);
                Predicate predicate = cb.equal(attributePath, criterion.getValue());
                predicates.add(predicate);
            }
            finalPredicate = cb.or(predicates.toArray(new Predicate[predicates.size()]));
        }
        else
        {
            for(String key: filter.keySet()) {
                Path<String> party = t.get(key);
                predicates.add(cb.equal(party,filter.get(key)));
            }
            finalPredicate = cb.and(predicates.toArray(new Predicate[predicates.size()]));
        }
        
        query.select(t)
        .where(finalPredicate);
        return entityManager.createQuery(query).getResultList();
	}
}
