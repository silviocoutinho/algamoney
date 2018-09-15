package com.silvio.algamoneyapi.resource;

import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.silvio.algamoneyapi.event.RecursoCriadoEvent;
import com.silvio.algamoneyapi.model.Lancamento;
import com.silvio.algamoneyapi.repository.LancamentoRepository;
import com.silvio.algamoneyapi.service.LancamentoService;

@RestController
@RequestMapping("/lancamentos")
public class LancamentoResource {

	@Autowired
	private LancamentoRepository repo;
	
	@Autowired
	private LancamentoService service;
	
	@Autowired
	private ApplicationEventPublisher publisher;

	@GetMapping
	public List<Lancamento> listar() {
		return repo.findAll();
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<Lancamento> criar(@Valid @RequestBody Lancamento obj, HttpServletResponse response) {
		Lancamento objSalvo = service.criar(obj);
		publisher.publishEvent(new RecursoCriadoEvent(this, response, objSalvo.getCodigo()));
		return ResponseEntity.status(HttpStatus.CREATED).body(objSalvo);
	}

	@GetMapping("/{codigo}")
	public ResponseEntity<Lancamento> buscarPeloCodigo(@PathVariable Long codigo) {
		Lancamento obj = repo.findOne(codigo);
		if (obj == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok().body(obj);

	}

}
