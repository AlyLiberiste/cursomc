package com.alyliberiste.cursomc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.alyliberiste.cursomc.domain.Pedido;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Integer>{ //apaga class e coloca interface
//objeto desse tipo, vai ser capaz de realizar operações(CRUD) e/ou acess a dados
}
