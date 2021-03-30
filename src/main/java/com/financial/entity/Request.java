package com.financial.entity;

import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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
	
//	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
//	@JoinColumn(name = "customer_id")
//	private Customer customer;

	@OneToMany(mappedBy = "id.request", fetch = FetchType.EAGER)
	private Set<ItemRequest> items = new HashSet<>();
			
	public Request() {
	}

	public Request(Long id, OffsetDateTime instant, Address address) {
		super();
		this.id = id;
		this.instant = instant;
		this.address = address;
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
	
	
}