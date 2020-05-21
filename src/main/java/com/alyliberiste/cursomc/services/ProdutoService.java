package com.alyliberiste.cursomc.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.alyliberiste.cursomc.domain.Categoria;
import com.alyliberiste.cursomc.domain.Produto;
import com.alyliberiste.cursomc.repositories.CategoriaRepository;
import com.alyliberiste.cursomc.repositories.ProdutoRepository;
import com.alyliberiste.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class ProdutoService {
	//dependencia de 1 objeto de tipo categoria repo
	@Autowired //obj q vai ser autom instanciado pelo spring
	private ProdutoRepository repo;

	@Autowired
	private CategoriaRepository catecoriaRepository;
	public Produto find(Integer id) {
		Optional<Produto> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Produto.class.getName()));
	}
	
	//busca paginada
	public Page<Produto> search(String nome, List<Integer> ids, Integer page, Integer linesPerPage, String orderBy, String direction) {
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		List<Categoria> categorias = catecoriaRepository.findAllById(ids);
		//return repo.search(nome, categorias, pageRequest);
			//OU
		return repo.findDistinctByNomeContainingAndCategoriasIn(nome, categorias, pageRequest);
		
	}
}
