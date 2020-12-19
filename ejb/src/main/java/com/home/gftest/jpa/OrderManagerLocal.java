package com.home.gftest.jpa;

import java.util.List;

import com.home.gftest.jpa.model.Order;

public interface OrderManagerLocal {
	/**
	 * Persist the given Order
	 * 
	 * @param order the Order
	 */
	public void create(Order order);

	/**
	 * Get an order by its Primary Key id
	 * 
	 * @param id the Primary Key
	 * 
	 * @return the matching Order
	 */
	public Order getById(Long id);

	/**
	 * Remove an Order by Primary Key access
	 * 
	 * @param id the Primary Key
	 * 
	 * @return the deleted Order or null
	 */
	public Order delete(Long id);

	/**
	 * Remove an Order by object
	 * 
	 * @param order the Order
	 * 
	 * @return the deleted Order or null
	 */
	public Order delete(Order order);

	/**
	 * Get an unordered List of all Orders
	 * 
	 * @return the list of Orders
	 */
	public List<Order> getAll();
}
