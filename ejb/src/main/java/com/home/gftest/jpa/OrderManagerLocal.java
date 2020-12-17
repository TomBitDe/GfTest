package com.home.gftest.jpa;

import com.home.gftest.jpa.model.TravelOrder;

public interface OrderManagerLocal {
	public void create(TravelOrder order);

	public TravelOrder getById(Long id);

	public TravelOrder delete(Long id);
}
