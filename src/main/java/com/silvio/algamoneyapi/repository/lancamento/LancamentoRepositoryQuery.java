package com.silvio.algamoneyapi.repository.lancamento;

import java.util.List;

import com.silvio.algamoneyapi.model.Lancamento;
import com.silvio.algamoneyapi.repository.filter.LancamentoFilter;

public interface LancamentoRepositoryQuery {
	
	public List<Lancamento> filtrar(LancamentoFilter filter);

}
