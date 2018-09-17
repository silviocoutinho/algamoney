package com.silvio.algamoneyapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.silvio.algamoneyapi.model.Pessoa;
import com.silvio.algamoneyapi.repository.pessoa.PessoaRepositoryQuery;

public interface PessoaRepository extends JpaRepository<Pessoa, Long>, PessoaRepositoryQuery {

}
