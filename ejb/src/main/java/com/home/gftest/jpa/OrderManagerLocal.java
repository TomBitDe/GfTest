package com.home.gftest.jpa;

import java.util.List;

import com.home.gftest.jpa.model.Order;

public interface OrderManagerLocal {
	public void create(Order order);

	public Order getById(Long id);

	public Order delete(Long id);

	public List<Order> getAll();
}
