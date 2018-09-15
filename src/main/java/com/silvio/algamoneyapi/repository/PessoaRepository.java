package com.silvio.algamoneyapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.silvio.algamoneyapi.model.Pessoa;

public interface PessoaRepository extends JpaRepository<Pessoa, Long> {

}
