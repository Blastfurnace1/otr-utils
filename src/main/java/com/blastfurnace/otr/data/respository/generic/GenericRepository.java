package com.blastfurnace.otr.data.respository.generic;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.blastfurnace.otr.rest.request.QueryData;
import com.blastfurnace.otr.util.reflection.FieldProperties;

/** Create a custom query for JPA using input values from a map and the object properties. */
public class GenericRepository<T> {
    
	/** Defines what fields the class has. */
    private List<FieldProperties> fieldProperties;
    
    /** The class of the Object. */
    private Class<T> type;
    
    public GenericRepository(Class<T> type, List<FieldProperties> fieldProperties) {
		this.fieldProperties = fieldProperties;
		this.type = type;
	}
	
    /** Construct a predicate based on the requested properties by type. */
    private Predicate getPredicate(CriteriaBuilder cb, Root<T> audioFile, QueryData queryData, FieldProperties fp) {
    	Predicate predicate = null;
    	
    	if (fp.getType().equalsIgnoreCase("java.lang.String")) {
    		predicate = cb.like(audioFile.get(fp.getName()), (queryData.getStringValue(fp.getName()) +"%"));
    	}
    	if (fp.getType().equalsIgnoreCase("java.lang.Long")) {
    		predicate = cb.equal(audioFile.get(fp.getName()), (queryData.getLongValue(fp.getName())));
    	}
    	if (fp.getType().equalsIgnoreCase("java.lang.Integer")) {
    		predicate = cb.equal(audioFile.get(fp.getName()), (queryData.getLongValue(fp.getName())));
    	}
    	if (fp.getType().equalsIgnoreCase("java.lang.Float")) {
    		predicate = cb.equal(audioFile.get(fp.getName()), (queryData.getLongValue(fp.getName())));
    	}
    	return predicate;
    }
    
    /** Add a predicate for each of the requested properties. */
    private Predicate addCriteria(CriteriaBuilder cb, Root<T> audioFile, QueryData queryData) {
    	boolean hasPredicate = false;
    	Predicate newPredicate = null;
    	Predicate fullPredicate = null;
    	for (FieldProperties fp : fieldProperties) {
    		if (queryData.hasProperty(fp.getName())) {
    			newPredicate = getPredicate(cb, audioFile, queryData, fp);
    			if (hasPredicate) {
    				if (queryData.isJoinAnd()) {
    					fullPredicate = cb.and(fullPredicate, newPredicate);
    				} else {
    					fullPredicate = cb.or(fullPredicate, newPredicate);
    				}
    			} else {
    				hasPredicate = true;
    				fullPredicate = newPredicate;
    			}
    		}
    	}
    	return fullPredicate;
    }
    
    /** Get a count of the Records based on the constructed where clause. */
    public Long getRecordCount(EntityManager em, QueryData queryData) {
    	Long value = 0l;
	    CriteriaBuilder cb = em.getCriteriaBuilder();
	    CriteriaQuery<Long> criteria = cb.createQuery(Long.class);
	    Root<T> series = criteria.from(type);
	    criteria.select(cb.count(series));
	    Predicate predicate = addCriteria(cb, series, queryData);
	    if (predicate != null) {
    		criteria.where(predicate);
    	} 
	    value = em.createQuery(criteria).getSingleResult();
	    return value;
    }

    /** Build the query. */
    private void createCriteria(CriteriaQuery<T> criteria, CriteriaBuilder cb, QueryData queryData) {
    	Root<T> audioFile = criteria.from(type);
    	criteria.select(audioFile);
    	Predicate predicate = addCriteria(cb, audioFile, queryData);
	    if (predicate != null) {
    		criteria.where(predicate);
    	}
    	if (queryData.isSortAscending()) {
    		criteria.orderBy(cb.asc(audioFile.get(queryData.getSort())));
    	} else {
    		criteria.orderBy(cb.desc(audioFile.get(queryData.getSort())));
    	}
    }
    
    /** find by dynamic query. */
    public List<T> find(EntityManager em, QueryData queryData) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<T> criteria = cb.createQuery(type);
        createCriteria(criteria, cb, queryData);
        return em.createQuery(criteria).setFirstResult(queryData.getOffset()).setMaxResults(queryData.getSize()).getResultList();
    }

    /** Find records. */
    public List<T> find(EntityManager em) {
      CriteriaBuilder cb = em.getCriteriaBuilder();
      CriteriaQuery<T> criteria = cb.createQuery(type);
      Root<T> audioFile = criteria.from(type);
      criteria.select(audioFile).orderBy(cb.asc(audioFile.get("title")));
      return em.createQuery(criteria).getResultList();
  }
}
