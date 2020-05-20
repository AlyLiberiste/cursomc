package com.alyliberiste.cursomc.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.alyliberiste.cursomc.domain.Cliente;
import com.alyliberiste.cursomc.dto.ClienteDTO;
import com.alyliberiste.cursomc.repositories.ClienteRepository;
import com.alyliberiste.cursomc.services.exceptions.DataIntegrityException;
import com.alyliberiste.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class ClienteService {
	//dependencia de 1 objeto de tipo categoria repo
	@Autowired //obj q vai ser autom instanciado pelo spring
	private ClienteRepository repo;
	//criando 1 operação capaz de buscar 1 categoria por código
	public Cliente find(Integer id) {
		Optional<Cliente> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Cliente.class.getName()));
	}
	//Update Cli
		public Cliente update(Cliente obj) {
			//atualizar cli a partir do BD
			Cliente newObj = find(obj.getId());	
			updateData(newObj, obj);
			return repo.save(newObj);
		}

		public void delete(Integer id) {
			find(id);			// se id nao existe, lança 1 e
			try {
				repo.deleteById(id);		
			}
			catch (DataIntegrityViolationException e) {
	//seguindo implemt c/ camada, é preciso lançar 1 e personalizada no package ...exceptions
				throw new DataIntegrityException("Não é possível excluir porque há entidade relacional");
				
				
			}
		}

		//Listagem de Cat
		public List<Cliente> findAll() {
			return repo.findAll();
		}
		//pagination
		public Page<Cliente> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
			PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
			return repo.findAll(pageRequest);
		}
		
		//método de validation
		public Cliente fromDTO(ClienteDTO objDTO) {
			return new Cliente(objDTO.getId(), objDTO.getNome(), objDTO.getEmail(), null, null);
		}
		
	//atualizacao do cli buscado no BD conforme declado em cima
		private void updateData(Cliente newObj, Cliente obj) {
			newObj.setNome(obj.getNome());
			newObj.setEmail(obj.getEmail());
		}
}
