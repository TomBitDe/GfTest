package com.home.gftest.jpa;

import com.home.gftest.jpa.model.Order;

public interface OrderManagerLocal {
	public void create(Order order);

	public Order getById(long id);

	public Order delete(long id);
}
