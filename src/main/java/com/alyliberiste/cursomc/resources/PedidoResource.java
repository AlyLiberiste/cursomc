package com.alyliberiste.cursomc.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alyliberiste.cursomc.domain.Pedido;
import com.alyliberiste.cursomc.services.PedidoService;

@RestController
@RequestMapping(value="/pedidos")
public class PedidoResource {
	
	@Autowired
	private PedidoService service;
	
	@RequestMapping(value="/{id}",method=RequestMethod.GET) //por ser atributo REST, tem que atribuir c/ algum verbo HTTP
	public ResponseEntity<Pedido> find(@PathVariable Integer id) {
		Pedido obj = service.find(id); //era service.buscar(id);
		return ResponseEntity.ok().body(obj);
				
	
		
	}

}
