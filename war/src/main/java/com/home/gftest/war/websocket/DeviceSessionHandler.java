package com.home.gftest.war.websocket;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.enterprise.context.ApplicationScoped;
import javax.json.JsonObject;
import javax.json.spi.JsonProvider;
import javax.websocket.Session;

import org.apache.log4j.Logger;

import com.home.gftest.war.websocket.model.Device;

@ApplicationScoped
public class DeviceSessionHandler {
	private static final Logger LOG = Logger.getLogger(DeviceSessionHandler.class);

	private int deviceId = 0;
    private final Set<Session> sessions = new HashSet<>();
    private final Set<Device> devices = new HashSet<>();

    public void addSession(Session session) {
    	LOG.debug("--> addSession");

        sessions.add(session);
        for (Device device : devices) {
            JsonObject addMessage = createAddMessage(device);
            sendToSession(session, addMessage);
        }

    	LOG.debug("<-- addSession");
    }

    public void removeSession(Session session) {
    	LOG.debug("--> removeSession");

    	sessions.remove(session);

    	LOG.debug("<-- removeSession");
    }

    public List<Device> getDevices() {
    	LOG.debug("--> getDevices");

    	LOG.debug("<-- getDevices");
        return new ArrayList<>(devices);
    }

    public void addDevice(Device device) {
    	LOG.debug("--> addDevice");

        device.setId(deviceId);
        devices.add(device);
        deviceId++;
        JsonObject addMessage = createAddMessage(device);
        sendToAllConnectedSessions(addMessage);

        LOG.debug("<-- addDevice");
    }

    public void removeDevice(int id) {
    	LOG.debug("--> removeDevice");

        Device device = getDeviceById(id);
        if (device != null) {
            devices.remove(device);
            JsonProvider provider = JsonProvider.provider();
            JsonObject removeMessage = provider.createObjectBuilder()
                    .add("action", "remove")
                    .add("id", id)
                    .build();
            sendToAllConnectedSessions(removeMessage);
        }

        LOG.debug("<-- removeDevice");
    }

    public void toggleDevice(int id) {
    	LOG.debug("--> toggleDevice");

        JsonProvider provider = JsonProvider.provider();
        Device device = getDeviceById(id);
        if (device != null) {
            if ("On".equals(device.getStatus())) {
                device.setStatus("Off");
            } else {
                device.setStatus("On");
            }
            JsonObject updateDevMessage = provider.createObjectBuilder()
                    .add("action", "toggle")
                    .add("id", device.getId())
                    .add("status", device.getStatus())
                    .build();
            sendToAllConnectedSessions(updateDevMessage);
        }

        LOG.debug("<-- toggleDevice");
    }

    private Device getDeviceById(int id) {
    	LOG.debug("--> getDeviceById");

    	for (Device device : devices) {
            if (device.getId() == id) {
                LOG.debug("<-- getDeviceById");

                return device;
            }
        }

        LOG.debug("<-- getDeviceById");

        return null;
    }

    private JsonObject createAddMessage(Device device) {
    	LOG.debug("--> createAddMessage");

        JsonProvider provider = JsonProvider.provider();
        JsonObject addMessage = provider.createObjectBuilder()
                .add("action", "add")
                .add("id", device.getId())
                .add("name", device.getName())
                .add("type", device.getType())
                .add("status", device.getStatus())
                .add("description", device.getDescription())
                .build();

        LOG.debug("<-- createAddMessage");

        return addMessage;
    }

    private void sendToAllConnectedSessions(JsonObject message) {
    	LOG.debug("--> sendToAllConnectedSessions");

        for (Session session : sessions) {
            sendToSession(session, message);
        }

        LOG.debug("<-- sendToAllConnectedSessions");
    }

    private void sendToSession(Session session, JsonObject message) {
    	LOG.debug("--> sendToSession");

    	try {
            session.getBasicRemote().sendText(message.toString());
        }
        catch (IOException ex) {
            sessions.remove(session);
            LOG.fatal(ex);
        }
    	finally {
        	LOG.debug("<-- sendToSession");
    	}
    }
}
