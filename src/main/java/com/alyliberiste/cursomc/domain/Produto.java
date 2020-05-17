package com.alyliberiste.cursomc.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Produto implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	private String nome;
	private double preco;
	
	@JsonIgnore //todo que comeca c/ get é autom serielizado, aqui tem que ignorar isso
	public List<Pedido> getPedidos(){ //getPedidos => padrao javabeans > exigencia de java
		//Iniciando 1 lista de pedidos
		List<Pedido> lista = new ArrayList<>();
		//percorrendo todos os itens pedidos
		for(ItemPedido x: itens) {
			lista.add(x.getPedido());
		}
		return lista;
	}
	
	//Anotação JSON inversa da categoria, OU SEJA,DO OUTRO LADO DA ASSOCIACAO
	//JÁ FORAM BUSCADO OS OBJETOS. AQUI NAO TEM Q BUSCAR MAIS.
	//APENAS OMITIR A LISTA DE CATEGIAS P/ CADA PRODUTO
	@JsonIgnore
	@ManyToMany
	@JoinTable(name = "PRODUTO_CATEGORIA",
			joinColumns = @JoinColumn(name = "produto_id"),
			inverseJoinColumns = @JoinColumn(name = "categoria_id")
			)//mapeamto muitos p/ muitos
	
	private List<Categoria> categorias = new ArrayList<>();
	
	@JsonIgnore
	@OneToMany(mappedBy="id.produto") 						//foi mapeado no ItemPedido
	private Set<ItemPedido> itens = new HashSet<>(); 		// não vai ter item repetido pro msmo pedido
															//e produto sabe os itens associados a ele
	
	public Produto() {
		
	}

	public Produto(Integer id, String nome, double preco) {
		super();
		this.id = id;
		this.nome = nome;
		this.preco = preco;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public double getPreco() {
		return preco;
	}

	public void setPreco(double preco) {
		this.preco = preco;
	}

	public List<Categoria> getCategorias() {
		return categorias;
	}

	public void setCategorias(List<Categoria> categorias) {
		this.categorias = categorias;
	}
	public Set<ItemPedido> getItens() {
		return itens;
	}

	public void setItens(Set<ItemPedido> itens) {
		this.itens = itens;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Produto other = (Produto) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
}
