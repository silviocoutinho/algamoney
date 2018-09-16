package com.silvio.algamoneyapi.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.silvio.algamoneyapi.model.Lancamento;
import com.silvio.algamoneyapi.model.Pessoa;
import com.silvio.algamoneyapi.repository.LancamentoRepository;
import com.silvio.algamoneyapi.repository.PessoaRepository;
import com.silvio.algamoneyapi.service.exception.PessoaInexistenteOuInativaException;

@Service
public class LancamentoService {

	@Autowired
	private LancamentoRepository repo;
	
	@Autowired
	private PessoaRepository pessoaRepository;

	public Lancamento atualizar(Lancamento obj, Long codigo) {
		Lancamento objSalvo = buscarLancamentoPeloCodigo(codigo);
		BeanUtils.copyProperties(obj, objSalvo, "codigo");
		return repo.save(objSalvo);
	}


	private Lancamento buscarLancamentoPeloCodigo(Long codigo) {
		Lancamento objSalvo = repo.findOne(codigo);
		if (objSalvo == null) {
			throw new EmptyResultDataAccessException(1); // Espera pelo menos 1 campo, no teste teremos null
		}
		return objSalvo;
	}


	public Lancamento salvar(Lancamento obj) {
		Pessoa pessoa = pessoaRepository.findOne(obj.getPessoa().getCodigo());
		if(pessoa == null || pessoa.isInativo()) {
			throw new PessoaInexistenteOuInativaException();
		}
		
		return repo.save(obj);
		
	}

}
