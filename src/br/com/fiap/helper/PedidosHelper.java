package br.com.fiap.helper;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.LockModeType;
import javax.persistence.Query;
import javax.swing.JOptionPane;

import br.com.fiap.entity.Pedidos;
import br.com.fiap.entity.Produtos;

public class PedidosHelper {
	
private EntityManager em;
	
	public PedidosHelper(EntityManager em) {
		this.em = em;
	}
	
	public void incluirPedido (Pedidos pedido) {
		
		EntityTransaction et = em.getTransaction();
		
		try{
			Produtos produto = em.find(Produtos.class, pedido.getIdProduto().getId());
			
			et.begin();
			em.persist(pedido);
			em.lock(pedido, LockModeType.PESSIMISTIC_WRITE);
			produto.setVendas(produto.getVendas()+1);
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
	
	public Pedidos buscarPedidosPorIdPed (int id) {
		
		try{
			em.getTransaction().begin();			
			Pedidos pedido = em.find(Pedidos.class, id, LockModeType.PESSIMISTIC_READ);
			em.getTransaction().commit();
			return pedido;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}		
	}
	
	@SuppressWarnings("unchecked")
	public List<Pedidos> buscarPedidosPorIdProd (int idProd) {
		
		try{
			em.getTransaction().begin();
			Query pedidos = em.createQuery("SELECT e FROM Pedidos e WHERE e.idProduto.id = :id");
			pedidos.setParameter("id", idProd);
			List<Pedidos> pedidosList = (List<Pedidos>) pedidos.setLockMode(LockModeType.PESSIMISTIC_READ).getResultList();
			em.getTransaction().commit();
			return pedidosList;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}		
	}
	
	@SuppressWarnings("unchecked")
	public List<Pedidos> listarPedidos(){
		em.getTransaction().begin();
		Query pedidos = em.createQuery("SELECT e FROM Pedidos e");
		List<Pedidos> pedidosList = (List<Pedidos>) pedidos.setLockMode(LockModeType.PESSIMISTIC_READ).getResultList();
		em.getTransaction().commit();
		return pedidosList;
	}

}
