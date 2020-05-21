package com.alyliberiste.cursomc.services;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.alyliberiste.cursomc.domain.Cidade;
import com.alyliberiste.cursomc.domain.Cliente;
import com.alyliberiste.cursomc.domain.Endereco;
import com.alyliberiste.cursomc.domain.enums.TipoCliente;
import com.alyliberiste.cursomc.dto.ClienteDTO;
import com.alyliberiste.cursomc.dto.ClienteNewDTO;
import com.alyliberiste.cursomc.repositories.ClienteRepository;
import com.alyliberiste.cursomc.repositories.EnderecoRepository;
import com.alyliberiste.cursomc.services.exceptions.DataIntegrityException;
import com.alyliberiste.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class ClienteService {
	@Autowired 
	private ClienteRepository repo;

	@Autowired 
	private EnderecoRepository enderecoRepository;
	
	public Cliente find(Integer id) {
		Optional<Cliente> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Cliente.class.getName()));
	}
		//inserindo cli
		public Cliente insert(Cliente obj) {
			obj.setId(null);
			obj = repo.save(obj);
			enderecoRepository.saveAll(obj.getEnderecos());
			return obj;
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
		public Cliente fromDTO(ClienteDTO objDto) {
			return new Cliente(objDto.getId(), objDto.getNome(), objDto.getEmail(), null, null);
		}
		//inserindo clienteNewDTO
		@Transactional
		public Cliente fromDTO(ClienteNewDTO objDto) {
			Cliente cli = new Cliente(null, objDto.getNome(), objDto.getEmail(), objDto.getCpfOucnpj(),
					TipoCliente.toEnum(objDto.getTipo()));
			//Cidade cid = cidadeRepository.findOne(objDto.getCidadeId());
			Cidade cid = new Cidade(objDto.getCidadeId(), null, null);
			Endereco end = new Endereco(null, objDto.getLogradouro(), objDto.getNumero(), objDto.getComplemento(), 
					objDto.getBairro(), objDto.getCep(), cli, cid);
			cli.getEnderecos().add(end);
			cli.getTelefones().add(objDto.getTelefone1());
			if(objDto.getTelefone2()!=null) {
				cli.getTelefones().add(objDto.getTelefone2());
			}
			if(objDto.getTelefone3()!=null) {
				cli.getTelefones().add(objDto.getTelefone3());
			}
			return cli;
		}
		
	//atualizacao do cli buscado no BD conforme declado em cima
		private void updateData(Cliente newObj, Cliente obj) {
			newObj.setNome(obj.getNome());
			newObj.setEmail(obj.getEmail());
		}
}
