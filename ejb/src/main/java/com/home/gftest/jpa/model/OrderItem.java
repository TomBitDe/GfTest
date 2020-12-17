package com.home.gftest.jpa.model;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Version;

/**
 * The sample order item class
 */
@Entity
@Table(name = "ORDERITEM")
public class OrderItem implements Serializable {
	private static final long serialVersionUID = 6695356386904131476L;

	@EmbeddedId
	private OrderItemPK id;

	@Column(name = "PRODUCTCODE", nullable = false)
	private Integer productCode;

	@Column(name = "QUANTITY", nullable = false)
	private Integer quantity;

	@Version
	private int version;

	public OrderItem() { super(); }

	public OrderItem(OrderItemPK id, Integer productCode, Integer quantity) {
		super();

		this.id = id;
		this.productCode = productCode;
		this.quantity = quantity;
	}

	public OrderItemPK getId() {
		return id;
	}

	protected void setId(OrderItemPK id) {
		this.id = id;
	}

	public Integer getProductCode() {
		return productCode;
	}

	public void setProductCode(Integer productCode) {
		this.productCode = productCode;
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
		return Objects.hash(id, productCode, quantity, version);
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
		return Objects.equals(id, other.id) && Objects.equals(productCode, other.productCode)
				&& Objects.equals(quantity, other.quantity) && version == other.version;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("OrderItem [id=");
		builder.append(id);
		builder.append(", productCode=");
		builder.append(productCode);
		builder.append(", quantity=");
		builder.append(quantity);
		builder.append(", version=");
		builder.append(version);
		builder.append("]");
		return builder.toString();
	}
}
