package com.home.gftest.jpa.model;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Version;

/**
 * The sample order item class
 */
@Entity
@Table(name = "ORDERITEM")
public class OrderItem implements Serializable {
	private static final long serialVersionUID = 6695356386904131476L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "ITEM_ID") // must not be ID because of @JoinColumn(name = "ID", ...
	private Long id;

	@ManyToOne( targetEntity = Order.class, fetch = FetchType.LAZY) // LAZY for better performance)
	@JoinColumn(name = "ORDER_ID", nullable = false)
	private Order order;

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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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
		return Objects.hash(id, order, quantity, version);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		OrderItem other = (OrderItem) obj;
		return Objects.equals(id, other.id) && Objects.equals(order, other.order)
				&& Objects.equals(quantity, other.quantity) && version == other.version;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("OrderItem [id=");
		builder.append(id);
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
