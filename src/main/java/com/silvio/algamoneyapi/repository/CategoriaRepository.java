package com.silvio.algamoneyapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.silvio.algamoneyapi.model.Categoria;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {

}
