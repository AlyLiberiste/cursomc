package com.alyliberiste.cursomc.resources;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.alyliberiste.cursomc.domain.Categoria;
import com.alyliberiste.cursomc.dto.CategoriaDTO;
import com.alyliberiste.cursomc.services.CategoriaService;

@RestController
@RequestMapping(value="/categorias")
public class CategoriaResource {
	
	@Autowired
	private CategoriaService service;
	
	@RequestMapping(value="/{id}",method=RequestMethod.GET) 
	public ResponseEntity<Categoria> find(@PathVariable Integer id) { //entidade de tipo Cat
		Categoria obj = service.find(id); 
		return ResponseEntity.ok().body(obj);
	}
	
	//INSERINDO 1 NOVA CAT		
	@RequestMapping(method=RequestMethod.POST) 
	//Validation
	ResponseEntity<Void> insert(@Valid @RequestBody CategoriaDTO objDTO){
		//convert objDTO p/ obj entity
		Categoria obj = service.fromDTO(objDTO);
		obj = service.insert(obj);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}").buildAndExpand(obj.getId()).toUri();
		return ResponseEntity.created(uri).build();
		
	}
	//Atualizando 1 categoria   
	@RequestMapping(value="/{id}", method=RequestMethod.PUT)
	//validation
	ResponseEntity<Void> update(@Valid @RequestBody CategoriaDTO objDTO, @PathVariable Integer id){
		Categoria obj = service.fromDTO(objDTO);
		obj.setId(id);
		obj = service.update(obj);
		return ResponseEntity.noContent().build();
	}
	
	//Deletando 1 categoria
	@RequestMapping(value="/{id}",method=RequestMethod.DELETE) 
	public ResponseEntity<Void> delete(@PathVariable Integer id) {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}
	
	//Listando Cat
	@RequestMapping(method=RequestMethod.GET) 
	public ResponseEntity<List<CategoriaDTO>> findAll() { //retornar 1 lista de Cat DTO em vez de Cat
		List<Categoria> list = service.findAll(); 
		//percorrer a lista e p/ cada el, instance 1 lista DTO correspondente
		//e converter 1 lista p/ 1 outra
		List<CategoriaDTO> listDTO = list.stream().map(obj -> new CategoriaDTO(obj)).collect(Collectors.toList());
		return ResponseEntity.ok().body(listDTO);
	}
	
	
	//pagination - endpoint p/ pegar 1 req e chamar o método de service
	@RequestMapping(value="/page", method=RequestMethod.GET) 
	public ResponseEntity<Page<CategoriaDTO>> findPage(
			//Tornar esses param opcionais
			@RequestParam(value="page", defaultValue = "0") Integer page, 
			@RequestParam(value="linesPerPage", defaultValue = "24") Integer linesPerPage, 
			@RequestParam(value="orderBy", defaultValue = "nome") String orderBy, 
			@RequestParam(value="direction", defaultValue = "ASC") String direction) {
		Page<Categoria> list = service.findPage(page, linesPerPage, orderBy, direction); 
		//converter cada obj lista p/ page DTO
		Page<CategoriaDTO> listDTO = list.map(obj -> new CategoriaDTO(obj));
		return ResponseEntity.ok().body(listDTO);
	}
}