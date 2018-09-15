package com.silvio.algamoneyapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.silvio.algamoneyapi.model.Lancamento;

public interface LancamentoRepository extends JpaRepository<Lancamento, Long> {

}
