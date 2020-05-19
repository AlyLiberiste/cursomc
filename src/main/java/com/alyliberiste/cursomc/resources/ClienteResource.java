package com.alyliberiste.cursomc.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alyliberiste.cursomc.domain.Cliente;
import com.alyliberiste.cursomc.services.ClienteService;

@RestController
@RequestMapping(value="/clientes")
public class ClienteResource {
	
	@Autowired
	private ClienteService service;
	
	@RequestMapping(value="/{id}",method=RequestMethod.GET) //por ser atributo REST, tem que atribuir c/ algum verbo HTTP
	public ResponseEntity<Cliente> find(@PathVariable Integer id) { //entidade de tipo Cliente
		Cliente obj = service.find(id); //era service.(id);
		return ResponseEntity.ok().body(obj);
				
	
		
	}

}
