package com.silvio.algamoneyapi.repository.pessoa;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.util.StringUtils;

import com.silvio.algamoneyapi.model.Endereco;
import com.silvio.algamoneyapi.model.Pessoa;
import com.silvio.algamoneyapi.repository.filter.PessoaFilter;

public class PessoaRepositoryImpl implements PessoaRepositoryQuery {

	@PersistenceContext
	private EntityManager manager;

	@Override
	public List<Pessoa> filtrar(PessoaFilter filter) {

		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Pessoa> criteria = builder.createQuery(Pessoa.class);
		Root<Pessoa> root = criteria.from(Pessoa.class);
		//Criacao de uma criteria para Join, nessa cada o campo endereco
		//eh embedded attribute
		Join<Pessoa, Endereco> criteriaJoin = root.join("endereco");

		// Criar restrincoes
		Predicate[] predicates = criarRestricoes(filter, builder, root, criteriaJoin);
		criteria.where(predicates);

		TypedQuery<Pessoa> query = manager.createQuery(criteria);
		return query.getResultList();
	}

	private Predicate[] criarRestricoes(PessoaFilter filter, CriteriaBuilder builder, Root<Pessoa> root, Join<Pessoa, Endereco> criteriaJoin) {
		
		List<Predicate> predicates = new ArrayList<>();
		
		if (!StringUtils.isEmpty(filter.getNome())) {
			predicates.add(builder.like(
					builder.lower(root.get("nome")), "%" + filter.getNome().toLowerCase() + "%"
					));
		}
		
		if (filter.getAtivo() != null && filter.getAtivo() ) {
			predicates.add(builder.isTrue(root.get("ativo")));			
		}
					
		if (filter.getAtivo() != null && !filter.getAtivo() ) {
			predicates.add(builder.isFalse(root.get("ativo")));			
		}
		
		//Teste para cidade
		if (!StringUtils.isEmpty(filter.getCidade())) {
			predicates.add(builder.like(
					builder.lower(criteriaJoin.get("cidade")), "%" + filter.getCidade().toLowerCase() + "%"
					));
		}
		//Fim teste para cidade

		
		return predicates.toArray(new Predicate[predicates.size()]);
	}

}
