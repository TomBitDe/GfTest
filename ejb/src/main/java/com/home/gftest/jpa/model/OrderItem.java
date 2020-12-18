package com.home.gftest.jpa.model;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Version;

/**
 * The sample order item lass
 */
@Entity
@Table(name = "ORDERITEM")
public class OrderItem implements Serializable {
	private static final long serialVersionUID = 6695356386904131476L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "ITEM")
	private Long item;

	@ManyToOne( targetEntity = Order.class )
	@JoinColumn(name = "ID", nullable = false)
	Order order;

	@Column(name = "QUANTITY", nullable = false)
	private Integer quantity;

	@Version
	private int version;

	public OrderItem() { super(); }

	public OrderItem(Order order, Integer quantity) {
		super();
		this.order = order;
		this.quantity = quantity;
	}

	public Long getItem() {
		return item;
	}

	public void setItem(Long item) {
		this.item = item;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public int getVersion() {
		return version;
	}

	protected void setVersion(int version) {
		this.version = version;
	}

	@Override
	public int hashCode() {
		return Objects.hash(item, order, quantity, version);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		OrderItem other = (OrderItem) obj;
		return Objects.equals(item, other.item) && Objects.equals(order, other.order)
				&& Objects.equals(quantity, other.quantity) && version == other.version;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("OrderItem [item=");
		builder.append(item);
		builder.append(", order=");
		builder.append(order);
		builder.append(", quantity=");
		builder.append(quantity);
		builder.append(", version=");
		builder.append(version);
		builder.append("]");
		return builder.toString();
	}
}
