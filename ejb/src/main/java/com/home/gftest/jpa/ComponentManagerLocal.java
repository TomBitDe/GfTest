package com.home.gftest.jpa;

import java.util.List;

import com.home.gftest.jpa.model.Component;

public interface ComponentManagerLocal {
	public void create(Component component);

	public Component delete(Long componentId);

	public Component delete(Component component);

	public Component getById(Long componentId);

	public List<Component> getAll();
}