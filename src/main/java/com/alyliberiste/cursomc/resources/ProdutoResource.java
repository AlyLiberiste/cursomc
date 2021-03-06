package com.alyliberiste.cursomc.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alyliberiste.cursomc.domain.Produto;
import com.alyliberiste.cursomc.dto.ProdutoDTO;
import com.alyliberiste.cursomc.resources.utils.URL;
import com.alyliberiste.cursomc.services.ProdutoService;

@RestController
@RequestMapping(value="/produtos")
public class ProdutoResource {
	
	@Autowired
	private ProdutoService service;
	
	@RequestMapping(value="/{id}",method=RequestMethod.GET) //por ser atributo REST, tem que atribuir c/ algum verbo HTTP
	public ResponseEntity<Produto> find(@PathVariable Integer id) {//entidade de tipo Produto
		Produto obj = service.find(id); //era service.buscar(id);
		return ResponseEntity.ok().body(obj);
				
	}

		//busca paginada
		@RequestMapping(method=RequestMethod.GET) 
		public ResponseEntity<Page<ProdutoDTO>> findPage(
				//Tornar esses param opcionais
				@RequestParam(value="nome", defaultValue = "0") String nome,
				@RequestParam(value="categorias", defaultValue = "0") String categorias,
				@RequestParam(value="page", defaultValue = "0") Integer page, 
				@RequestParam(value="linesPerPage", defaultValue = "24") Integer linesPerPage, 
				@RequestParam(value="orderBy", defaultValue = "nome") String orderBy, 
				@RequestParam(value="direction", defaultValue = "ASC") String direction) {
			String nemoDecoded = URL.decodeParam(nome);
			List<Integer> ids = URL.decodeIntList(categorias); // pega nome e cat com string
			Page<Produto> list = service.search(nemoDecoded, ids, page, linesPerPage, orderBy, direction); 
			//converter cada obj lista p/ page DTO
			Page<ProdutoDTO> listDTO = list.map(obj -> new ProdutoDTO(obj));
			return ResponseEntity.ok().body(listDTO);
		}
}
