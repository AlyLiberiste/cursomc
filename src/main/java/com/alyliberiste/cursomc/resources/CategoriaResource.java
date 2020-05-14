package com.alyliberiste.cursomc.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alyliberiste.cursomc.domain.Categoria;
import com.alyliberiste.cursomc.services.CategoriaService;

@RestController
@RequestMapping(value="/categorias")
public class CategoriaResource {
	
	@Autowired
	private CategoriaService service;
	
	@RequestMapping(value="/{id}",method=RequestMethod.GET) //por ser atributo REST, tem que atribuir c/ algum verbo HTTP
	public ResponseEntity<?> find(@PathVariable Integer id) { //ResponseEntity<?> entidade de qq cat
		Categoria obj = service.find(id); //era service.buscar(id);
		return ResponseEntity.ok().body(obj);
				
	
		
	}

}
