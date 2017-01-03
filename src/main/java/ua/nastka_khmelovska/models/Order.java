package ua.nastka_khmelovska.models;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

@Entity
public class Order {

	private Long idOrder;
	
	private Set<Client> clients;
	
	private String name;
	private Double price;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long getIdOrder() {
		return idOrder;
	}
	
	public void setIdOrder(Long idOrder) {
		this.idOrder = idOrder;
	}
	
	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	@JoinTable(name = "Client_has_Order", joinColumns = @JoinColumn(name = "idOrder"), inverseJoinColumns = @JoinColumn(name = "idClient"))
	public Set<Client> getClients() {
		return clients;
	}
	
	public void setClients(Set<Client> clients) {
		this.clients = clients;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public Double getPrice() {
		return price;
	}
	
	public void setPrice(Double price) {
		this.price = price;
	}

}
