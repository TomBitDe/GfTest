package com.home.gftest.jpa;

import java.util.List;

import com.home.gftest.jpa.model.OrderItem;

public interface OrderItemManagerLocal {
	/**
	 * Get an OrderItem by its Primary Key id
	 *
	 * @param id the Primary Key
	 *
	 * @return the matching OrderItem
	 */
	public OrderItem getById(Long id);

	/**
	 * Remove an OrderItem by Primary Key access
	 *
	 * @param id the Primary Key
	 *
	 * @return the deleted OrderItem or null
	 */
	public OrderItem delete(Long id);

	/**
	 * Remove an OrderItem by object
	 *
	 * @param orderItem the OrderItem
	 *
	 * @return the deleted OrderItem or null
	 */
	public OrderItem delete(OrderItem orderItem);

	/**
	 * Get an unordered List of all OrderItems
	 *
	 * @return the list of OrdersItems
	 */
	public List<OrderItem> getAll();
}
