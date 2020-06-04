package com.alyliberiste.cursomc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.alyliberiste.cursomc.domain.Pedido;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Integer> { 
}

//objeto desse tipo, é capaz de realizar operações(CRUD) e/ou acess a dados
