package com.silvio.algamoneyapi.repository.pessoa;

import java.util.List;

import com.silvio.algamoneyapi.model.Pessoa;
import com.silvio.algamoneyapi.repository.filter.PessoaFilter;

public interface PessoaRepositoryQuery {
	
	public List<Pessoa> filtrar(PessoaFilter filter);

}
