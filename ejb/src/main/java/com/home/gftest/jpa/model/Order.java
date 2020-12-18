package com.home.gftest.jpa.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Version;

/**
 * The sample order class
 */
@Entity
@Table(name = "`ORDER`") // NEVER use name="ORDER" because ORDER is a reserved keyword in SQL
public class Order implements Serializable {
	private static final long serialVersionUID = 6695356386904131476L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "ID")
	private Long id;

	@Column(name = "CUSTOMER", nullable = false)
	private String customer;

	@Version
	private int version;

	@OneToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE }, mappedBy = "order")
	private Set<OrderItem> items = new HashSet<>();

	public Order() { super(); }

	public Order(String customer) {
		super();

		this.customer = customer;
	}

	public Long getId() {
		return id;
	}

	protected void setId(Long id) {
		this.id = id;
	}

	public String getCustomer() {
		return customer;
	}

	public void setCustomer(String customer) {
		this.customer = customer;
	}

	public int getVersion() {
		return version;
	}

	protected void setVersion(int version) {
		this.version = version;
	}

	public Set<OrderItem> getItems() {
		return items;
	}

	public void setItems(Set<OrderItem> items) {
		this.items = items;
	}

	public void addItem(OrderItem item) {
		item.setOrder(this);
		items.add(item);
	}

	@Override
	public int hashCode() {
		return Objects.hash(customer, id, version);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Order other = (Order) obj;
		return Objects.equals(customer, other.customer) && Objects.equals(id, other.id) && version == other.version;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Order [id=");
		builder.append(id);
		builder.append(", customer=");
		builder.append(customer);
		builder.append(", version=");
		builder.append(version);
		builder.append("]");
		return builder.toString();
	}
}
