package br.com.fiap.entity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Cacheable(true)
@Table (name = "PRODUTOS")
public class Produtos implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "IDPRODUTO")
	private int id;
	
	@Column(name = "NOME", length = 45)
	private String nome;
	
	@Column(name = "VENDAS")
	private int vendas;
	
	@Column(name = "PRECO")
	private double preco;
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "idProduto")
	private Set<Pedidos> pedidos = new HashSet<Pedidos>();
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getVendas() {
		return vendas;
	}
	public void setVendas(int vendas) {
		this.vendas = vendas;
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
	public Set<Pedidos> getPedidos() {
		return pedidos;
	}
	public void setPedidos(Set<Pedidos> pedidos) {
		this.pedidos = pedidos;
	}

}
