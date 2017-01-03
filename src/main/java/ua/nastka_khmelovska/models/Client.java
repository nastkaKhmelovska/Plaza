package ua.nastka_khmelovska.models;

import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.JoinColumn;

@Entity
public class Client {

	private Long idClient;
	
	private Set<Order> orders;
	
	private String name;
	private String number;
	private Date date;
	private Double allPays;
	private Double pay;
	private Double debts;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long getIdClient() {
		return idClient;
	}
	
	public void setIdClient(Long idClient) {
		this.idClient = idClient;
	}
	
	public String getName() {
		return name;
	}
	
	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	@JoinTable(name = "Client_has_Order", joinColumns = @JoinColumn(name = "idClient"), inverseJoinColumns = @JoinColumn(name = "idOrder"))
	public Set<Order> getOrders() {
		return orders;
	}

	public void setOrders(Set<Order> orders) {
		this.orders = orders;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getNumber() {
		return number;
	}
	
	public void setNumber(String number) {
		this.number = number;
	}
	
	@Temporal(TemporalType.DATE)
	public Date getDate() {
		return date;
	}
	
	public void setDate(Date date) {
		this.date = date;
	}
	
	public Double getAllPays() {
		return allPays;
	}
	
	public void setAllPays(Double allPays) {
		this.allPays = allPays;
	}
	
	public Double getPay() {
		return pay;
	}
	
	public void setPay(Double pay) {
		this.pay = pay;
	}
	
	public Double getDebts() {
		return debts;
	}
	
	public void setDebts(Double debts) {
		this.debts = debts;
	}

}
