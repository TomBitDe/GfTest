package com.home.gftest.jpa;

import java.util.List;

import com.home.gftest.jpa.model.Address;
import com.home.gftest.jpa.model.User;

public interface UserAddressManagerLocal {
	public void create(User user);
	public User delete(Long id);
	public User delete(User user);
	public User getById(Long id);
	public List<User> getAll();
	public Address getByUserId(Long id);
	public Address getByUser(User user);
	public void setUserInAddress(User user);
}
