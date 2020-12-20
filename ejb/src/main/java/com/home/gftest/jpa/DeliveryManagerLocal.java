package com.home.gftest.jpa;

import java.util.List;

import com.home.gftest.jpa.model.Delivery;

public interface DeliveryManagerLocal {
	public void create(Delivery delivery);

	public Delivery delete(Long deliveryId);

	public Delivery delete(Delivery delivery);

	public Delivery getById(Long deliveryId);

	public List<Delivery> getAll();
}
