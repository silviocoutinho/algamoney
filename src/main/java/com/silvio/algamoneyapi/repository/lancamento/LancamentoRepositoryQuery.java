package com.silvio.algamoneyapi.repository.lancamento;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.silvio.algamoneyapi.model.Lancamento;
import com.silvio.algamoneyapi.repository.filter.LancamentoFilter;
import com.silvio.algamoneyapi.repository.projection.ResumoLancamento;

public interface LancamentoRepositoryQuery {
	
	public Page<Lancamento> filtrar(LancamentoFilter filter, Pageable pageable);
	public Page<ResumoLancamento> resumir(LancamentoFilter filter, Pageable pageable);
	

}
