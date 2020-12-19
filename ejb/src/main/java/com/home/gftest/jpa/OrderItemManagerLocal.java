package com.home.gftest.jpa;

import java.util.List;

import com.home.gftest.jpa.model.OrderItem;

public interface OrderItemManagerLocal {
	public OrderItem getById(Long id);

	public OrderItem delete(Long id);

	public OrderItem delete(OrderItem orderItem);

	public List<OrderItem> getAll();
}
