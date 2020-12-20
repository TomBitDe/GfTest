package com.home.gftest.jpa.model;

import java.io.Serializable;
import java.util.Objects;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Version;

/**
 * Entity implementation class for Entity: Component
 *
 */
@Entity
@Table(name="COMPONENT")
public class Component implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "COMPONENT_ID", nullable = false)
	private Long componentId;

	@Column(name = "COMMENT", nullable = false)
	private String comment;

	@Version
	private int version;

	@ManyToMany( mappedBy = "components", fetch = FetchType.LAZY )
    private Set<Delivery> deliveries;

	public Component() {
		super();
	}

	public Long getComponentId() {
		return componentId;
	}

	public void setComponentId(Long componentId) {
		this.componentId = componentId;
	}

	public String getComment() {
		return this.comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public int getVersion() {
		return this.version;
	}

	protected void setVersion(int version) {
		this.version = version;
	}

	public Set<Delivery> getDeliveries() {
		return deliveries;
	}

	public void setDeliveries(Set<Delivery> deliveries) {
		this.deliveries = deliveries;
	}

	public void addDelivery(Delivery delivery) {
		deliveries.add(delivery);
		delivery.addComponent(this);
	}

	@Override
	public int hashCode() {
		return Objects.hash(comment, componentId);
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
		Component other = (Component) obj;
		return Objects.equals(comment, other.comment) && Objects.equals(componentId, other.componentId);
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Component [componentId=");
		builder.append(componentId);
		builder.append(", comment=");
		builder.append(comment);
		builder.append(", version=");
		builder.append(version);
		builder.append(", deliveries=");
		builder.append(deliveries);
		builder.append("]");
		return builder.toString();
	}
}
