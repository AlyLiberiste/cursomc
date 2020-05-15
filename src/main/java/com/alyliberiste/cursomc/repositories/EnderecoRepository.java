package com.alyliberiste.cursomc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.alyliberiste.cursomc.domain.Endereco;

@Repository
public interface EnderecoRepository extends JpaRepository<Endereco, Integer>{ //apaga class e coloca interface
//objeto desse tipo, vai ser capaz de realizar operações(CRUD) e/ou acess a dados
}
