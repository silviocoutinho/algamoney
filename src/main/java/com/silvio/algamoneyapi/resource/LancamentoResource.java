package com.silvio.algamoneyapi.resource;

import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.silvio.algamoneyapi.event.RecursoCriadoEvent;
import com.silvio.algamoneyapi.exceptionhandler.AlgamoneyExceptionHandler.Erro;
import com.silvio.algamoneyapi.model.Lancamento;
import com.silvio.algamoneyapi.repository.LancamentoRepository;
import com.silvio.algamoneyapi.repository.filter.LancamentoFilter;
import com.silvio.algamoneyapi.service.LancamentoService;
import com.silvio.algamoneyapi.service.exception.LancamentoInexistenteException;
import com.silvio.algamoneyapi.service.exception.PessoaInexistenteOuInativaException;

@RestController
@RequestMapping("/lancamentos")
public class LancamentoResource {

	@Autowired
	private LancamentoRepository repo;
	
	@Autowired
	private LancamentoService service;
	
	@Autowired
	private ApplicationEventPublisher publisher;
	
	@Autowired
	private MessageSource messageSource;

	@GetMapping
	public List<Lancamento> pesquisar(LancamentoFilter lancamentoFilter) {
		
		return repo.filtrar(lancamentoFilter);
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<Lancamento> criar(@Valid @RequestBody Lancamento obj, HttpServletResponse response) {
		Lancamento objSalvo = service.salvar(obj);
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
	
	@DeleteMapping("/{codigo}")
	public void apagar(@PathVariable Long codigo){
		service.excluir(codigo);	
			
		
	}
	
	@ExceptionHandler({PessoaInexistenteOuInativaException.class})
	public ResponseEntity<Object> handlerPessoaInexistenteOuInativaException(PessoaInexistenteOuInativaException ex){
		String mensagemUsuario = messageSource.getMessage("pessoa.inexistente-ou-inativa", null,
				LocaleContextHolder.getLocale());
		String mensagemDesenvolvedor =  ex.toString();
		List<Erro> erros = Arrays.asList(new Erro(mensagemUsuario, mensagemDesenvolvedor));
		return ResponseEntity.badRequest().body(erros);
	}
	
	@ExceptionHandler({LancamentoInexistenteException.class})
	public ResponseEntity<Object> handlerLancamentoInexistenteException(LancamentoInexistenteException ex){
		String mensagemUsuario = messageSource.getMessage("lancamento.inexistente", null,
				LocaleContextHolder.getLocale());
		String mensagemDesenvolvedor =  ex.toString();
		List<Erro> erros = Arrays.asList(new Erro(mensagemUsuario, mensagemDesenvolvedor));
		return ResponseEntity.badRequest().body(erros);
		//return ResponseEntity.notFound().build();
	}

}
