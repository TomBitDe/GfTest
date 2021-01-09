package com.home.gftest.jpa.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Version;

/**
 * Entity implementation class for Entity: Delivery
 *
 */
@Entity
@Table(name = "DELIVERY")
public class Delivery implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "DELIVERY_ID", nullable = false)
	private Long deliveryId;

	@Column(name = "CUSTOMER", nullable = false)
	private String customer;

	@Version
	private int version;

	@ManyToMany( cascade = { CascadeType.ALL },
			fetch = FetchType.EAGER )
    @JoinTable( name = "DELIVERY_COMPONENT",
        joinColumns = @JoinColumn( name = "DELIVERY_ID" ),
        inverseJoinColumns = @JoinColumn( name = "COMPONENT_ID" ) )
    private Set<Component> components = new HashSet<>();

	public Delivery() {
		super();
	}

	public Delivery(Long deliveryId) {
		super();
		this.deliveryId = deliveryId;
	}

	public Delivery(Long deliveryId, String customer) {
		super();
		this.deliveryId = deliveryId;
		this.customer = customer;
	}

	public Delivery(Long deliveryId, String customer, Component component) {
		super();
		this.deliveryId = deliveryId;
		this.customer = customer;
		addComponent(component);
	}

	public Delivery(Long deliveryId, String customer, Set<Component> components) {
		super();
		this.deliveryId = deliveryId;
		this.customer = customer;
		this.components = components;
	}

	public Long getDeliveryId() {
		return deliveryId;
	}

	public void setDeliveryId(Long deliveryId) {
		this.deliveryId = deliveryId;
	}

	public String getCustomer() {
		return this.customer;
	}

	public void setCustomer(String customer) {
		this.customer = customer;
	}

	public int getVersion() {
		return this.version;
	}

	protected void setVersion(int version) {
		this.version = version;
	}

	public Set<Component> getComponents() {
		return components;
	}

	public void setComponents(Set<Component> components) {
		this.components = components;
	}

	public void addComponent(Component component) {
		component.addDelivery(this);
		components.add(component);
	}

	@Override
	public int hashCode() {
		return Objects.hash(customer, deliveryId, version);
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
		Delivery other = (Delivery) obj;
		return Objects.equals(customer, other.customer) && Objects.equals(deliveryId, other.deliveryId)
				&& version == other.version;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Delivery [deliveryId=");
		builder.append(deliveryId);
		builder.append(", customer=");
		builder.append(customer);
		builder.append(", version=");
		builder.append(version);
		builder.append(", components=");
		builder.append(components);
		builder.append("]");
		return builder.toString();
	}
}
