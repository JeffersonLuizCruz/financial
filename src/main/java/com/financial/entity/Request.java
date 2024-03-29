package com.financial.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
public class Request implements Serializable{
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@JsonFormat(pattern="dd/MM/yyyy HH:mm")
	private OffsetDateTime instant;
	
	@OneToOne(cascade = CascadeType.ALL, mappedBy = "request")
	private Payment payment;
	
	@ManyToOne
	@JoinColumn(name = "address_id")
	private Address address;
	
	@ManyToOne
	@JoinColumn(name = "customer_id")
	private Customer customer;

	@OneToMany(mappedBy = "id.request")
	private Set<ItemRequest> items = new HashSet<>();
			
	public Request() {
	}

	public Request(Long id, OffsetDateTime instant, Address address, Customer customer) {
		super();
		this.id = id;
		this.instant = instant;
		this.address = address;
		this.customer = customer;
	}
	
	public BigDecimal getValueTotal() {
		BigDecimal soma = new BigDecimal("0.0");
		
		for(ItemRequest ir: items) {
			soma = soma.add(ir.getSubTotal());
		}
		return soma;
	}
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public OffsetDateTime getInstant() {
		return instant;
	}

	public void setInstant(OffsetDateTime instant) {
		this.instant = instant;
	}

	public Payment getPayment() {
		return payment;
	}

	public void setPayment(Payment payment) {
		this.payment = payment;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}
	
	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public Set<ItemRequest> getItems() {
		return items;
	}

	public void setItems(Set<ItemRequest> items) {
		this.items = items;
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
		Request other = (Request) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
		OffsetDateTime odt = OffsetDateTime.now();
		builder.append("Pedido número: ");
		builder.append(getId());
		builder.append(", Instante: ");
		builder.append(odt.format(formatter));
		builder.append(", Cliente: ");
		builder.append(getCustomer().getName());
		builder.append(", Situação do pagamento: ");
		builder.append(getPayment().getStage().getDescription());
		builder.append("\nDetalhes:\n");
		
		for (ItemRequest ip : getItems()) {
			builder.append(ip.toString());
		}
		builder.append("Valor total: ");
		builder.append(getValueTotal());
		return builder.toString();
	}
	
	
	
	
}
