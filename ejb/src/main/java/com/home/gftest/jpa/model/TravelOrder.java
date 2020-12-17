package com.home.gftest.jpa.model;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Version;

/**
 * The sample move order class
 */
@Entity
@Table(name = "TRAVELORDER") // NEVER use name="ORDER" because ORDER is a reserved keyword in SQL
public class TravelOrder implements Serializable {
	private static final long serialVersionUID = 6695356386904131476L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;

	@Column(name = "DESTINATION", nullable = false)
	private String destination;

	@Version
	private int version;

	public TravelOrder() { super(); }

	public TravelOrder(String destination) {
		super();

		this.destination = destination;
	}

	public Long getId() {
		return id;
	}

	protected void setId(Long id) {
		this.id = id;
	}

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	public int getVersion() {
		return version;
	}

	protected void setVersion(int version) {
		this.version = version;
	}

	@Override
	public int hashCode() {
		return Objects.hash(destination, id, version);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TravelOrder other = (TravelOrder) obj;

		return Objects.equals(destination, other.destination) && id == other.id && version == other.version;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("TravelOrder [id=");
		builder.append(id);
		builder.append(", destination=");
		builder.append(destination);
		builder.append(", version=");
		builder.append(version);
		builder.append("]");
		return builder.toString();
	}
}
