package br.com.fiap.helper;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.LockModeType;
import javax.persistence.Query;
import javax.swing.JOptionPane;

import br.com.fiap.entity.Produtos;

public class ProdutosHelper {
	
	private EntityManager em;
	
	public ProdutosHelper(EntityManager em) {
		this.em = em;
	}
	
	public void incluirProduto(Produtos produto) {
		
		EntityTransaction et = em.getTransaction();
		
		try{
			et.begin();
			em.persist(produto);
			em.lock(produto, LockModeType.PESSIMISTIC_WRITE);
			et.commit();
		}catch(Exception e){
			e.printStackTrace();
			try {
				JOptionPane.showMessageDialog(null, "Erro! Abortando transação.");
				et.rollback();
			} catch (Exception e1) {
				JOptionPane.showMessageDialog(null, "Erro! Transação não pode ser abortada.");
				e1.printStackTrace();
			}
		}
	}
	
	public Produtos buscarProduto (int id) {
		
		try{
			em.getTransaction().begin();
			Produtos produto = em.find(Produtos.class, id, LockModeType.PESSIMISTIC_READ);
			em.getTransaction().commit();
			return produto;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}		
	}
	
	@SuppressWarnings("unchecked")
	public List<Produtos> listarProdutos(){
		em.getTransaction().begin();
		Query produtos = em.createQuery("SELECT e FROM Produtos e");
		List<Produtos> produtosList = (List<Produtos>) produtos.setLockMode(LockModeType.PESSIMISTIC_READ).getResultList();
		em.getTransaction().commit();
		return produtosList;
	}
}
