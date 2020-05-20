package com.alyliberiste.cursomc.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.alyliberiste.cursomc.domain.Categoria;
import com.alyliberiste.cursomc.repositories.CategoriaRepository;
import com.alyliberiste.cursomc.services.exceptions.DataIntegrityException;
import com.alyliberiste.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class CategoriaService {
	//dependencia de 1 objeto de tipo categoria repo
	@Autowired //obj q vai ser autom instanciado pelo spring
	private CategoriaRepository repo;
	//criando 1 operação capaz de buscar 1 categoria por código
	public Categoria find(Integer id) {
		Optional<Categoria> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Categoria.class.getName()));
	}
	
	//inserindo Cat
	public Categoria insert(Categoria obj) {
		obj.setId(null);				//garantir q esta inserindo 1 objeto novo
		return repo.save(obj);
	}
	//Update Cat
	public Categoria update(Categoria obj) {
		find(obj.getId());			//buscar o obj no BD e se não existe, lança 1 e
		return repo.save(obj);
	}

	public void delete(Integer id) {
		find(id);			// se id nao existe, lança 1 e
		try {
			repo.deleteById(id);		
		}
		catch (DataIntegrityViolationException e) {
//segungo implemt c/ camada, é preciso lançar 1 e personalizada no package ...exceptions
			throw new DataIntegrityException("Não é possível excluir uma categoria que tem produtos");
			
			
		}
	}

	//Listagem de Cat
	public List<Categoria> findAll() {
		return repo.findAll();
	}
	//pagination
	public Page<Categoria> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return repo.findAll(pageRequest);
	}
	
}
