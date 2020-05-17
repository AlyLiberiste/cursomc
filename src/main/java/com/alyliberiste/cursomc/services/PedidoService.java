package com.alyliberiste.cursomc.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alyliberiste.cursomc.domain.Pedido;
import com.alyliberiste.cursomc.repositories.PedidoRepository;
import com.alyliberiste.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class PedidoService {
	//dependencia de 1 objeto de tipo categoria repo
	@Autowired //obj q vai ser autom instanciado pelo spring
	private PedidoRepository repo;
	//criando 1 operação capaz de buscar 1 categoria por código
	public Pedido find(Integer id) {
		Optional<Pedido> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Pedido.class.getName()));
	}
}
