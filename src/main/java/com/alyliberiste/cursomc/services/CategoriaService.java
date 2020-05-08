package com.alyliberiste.cursomc.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alyliberiste.cursomc.domain.Categoria;
import com.alyliberiste.cursomc.repositorires.CategoriaRepository;

@Service
public class CategoriaService {
	//dependencia de 1 objeto de tipo categoria repo
	@Autowired //obj q vai ser autom instanciado pelo spring
	private CategoriaRepository repo;
	//criando 1 operação capaz de buscar 1 categoria por código
	public Categoria buscar(Integer id) {
		Optional <Categoria> obj = repo.findById(id);
		return obj.orElse(null);
		
	}

}