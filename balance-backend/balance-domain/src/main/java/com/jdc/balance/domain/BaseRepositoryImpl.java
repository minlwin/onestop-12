package com.jdc.balance.domain;

import java.util.List;
import java.util.function.Function;

import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;

public class BaseRepositoryImpl<T, ID> extends SimpleJpaRepository<T, ID> implements BaseRepository<T, ID>{

	private EntityManager em;
	
	public BaseRepositoryImpl(JpaEntityInformation<T, ?> entityInformation, EntityManager entityManager) {
		super(entityInformation, entityManager);
		this.em = entityManager;
	}

	@Override
	public <R> R searchOne(Function<CriteriaBuilder, CriteriaQuery<R>> queryFunc) {
		var criteriaQuery = queryFunc.apply(em.getCriteriaBuilder());
		return em.createQuery(criteriaQuery).getSingleResult();
	}

	@Override
	public <R> List<R> search(Function<CriteriaBuilder, CriteriaQuery<R>> queryFunc) {
		var criteriaQuery = queryFunc.apply(em.getCriteriaBuilder());
		return em.createQuery(criteriaQuery).getResultList();
	}

	@Override
	public <R> PageResult<R> search(Function<CriteriaBuilder, CriteriaQuery<R>> queryFunc,
			Function<CriteriaBuilder, CriteriaQuery<Long>> countFunc, int page, int size) {
		
		var countQuery = em.createQuery(countFunc.apply(em.getCriteriaBuilder()));
		var count = countQuery.getSingleResult();
		
		var contentQuery = em.createQuery(queryFunc.apply(em.getCriteriaBuilder()));
		contentQuery.setFirstResult(page * size);
		contentQuery.setMaxResults(size);
		var contents = contentQuery.getResultList();
		
		return new PageResult<>(
				contents, 
				new Pager(page, size, count));
	}

	@Override
	@Transactional
	public T create(T entity) {
		em.persist(entity);
		return entity;
	}

}
