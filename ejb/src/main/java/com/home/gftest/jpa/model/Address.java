package com.home.gftest.jpa.model;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Version;

@Entity
@Table(name = "address")
public class Address implements Serializable {
	private static final long serialVersionUID = -4035242926798461410L;

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "ZIPCODE", nullable = false)
    private String zipCode;

    @Version
    private int version;

    @OneToOne(mappedBy = "address")
    private User user;

   	public Address() {
		super();
	}

	public Address(String zipCode) {
		super();
		this.zipCode = zipCode;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Long getId() {
		return id;
	}

	public int getVersion() {
		return version;
	}

	protected void setVersion(int version) {
		this.version = version;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, zipCode, version);
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
		Address other = (Address) obj;
		return Objects.equals(id, other.id) && Objects.equals(zipCode, other.zipCode)
				&& Objects.equals(version, other.version);
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Address [id=");
		builder.append(id);
		builder.append(", zipCode=");
		builder.append(zipCode);
		builder.append(", user=");
		builder.append(user == null ? user : user.getId());
		builder.append(", version=");
		builder.append(version);
		builder.append("]");
		return builder.toString();
	}
}
