package com.home.gftest.war.websocket.model;

import org.apache.log4j.Logger;

public class Device {
	private static final Logger LOG = Logger.getLogger(Device.class);

    private int id;
    private String name;
    private String status;
    private String type;
    private String description;

    public Device() {
    	super();

    	LOG.debug("--> Device");
    	LOG.debug("<-- Device");
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getStatus() {
        return status;
    }

    public String getType() {
        return type;
    }

    public String getDescription() {
        return description;
    }

    public void setId(int id) {
    	LOG.info("setId=[" + id + "]");
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}