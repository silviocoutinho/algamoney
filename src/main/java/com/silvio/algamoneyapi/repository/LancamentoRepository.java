package com.silvio.algamoneyapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.silvio.algamoneyapi.model.Lancamento;
import com.silvio.algamoneyapi.repository.lancamento.LancamentoRepositoryQuery;

public interface LancamentoRepository extends JpaRepository<Lancamento, Long>, LancamentoRepositoryQuery {

}
