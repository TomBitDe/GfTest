package com.home.gftest.war.websocket;

import java.io.StringReader;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import org.apache.log4j.Logger;

import com.home.gftest.war.websocket.model.Device;

@ApplicationScoped
@ServerEndpoint("/actions")
public class DeviceWebSocketServer {
	private static final Logger LOG = Logger.getLogger(DeviceWebSocketServer.class);

    @Inject
    private DeviceSessionHandler sessionHandler;

	@OnOpen
	public void open(Session session) {
		LOG.debug("--> open");

		sessionHandler.addSession(session);

		LOG.debug("<-- open");
	}

	@OnClose
	public void close(Session session) {
		LOG.debug("--> close");

		sessionHandler.removeSession(session);

		LOG.debug("<-- close");
	}

	@OnError
	public void onError(Throwable error) {
		LOG.fatal(error);
	}

	@OnMessage
	public void handleMessage(String message, Session session) {
		LOG.debug("--> handleMessage");

        try (JsonReader reader = Json.createReader(new StringReader(message))) {
            JsonObject jsonMessage = reader.readObject();

            LOG.info("jsonMessage action = <" + jsonMessage.getString("action") + ">");

            if ("add".equals(jsonMessage.getString("action"))) {
                Device device = new Device();
                device.setName(jsonMessage.getString("name"));
                device.setDescription(jsonMessage.getString("description"));
                device.setType(jsonMessage.getString("type"));
                device.setStatus("Off");
                sessionHandler.addDevice(device);
            }

            if ("remove".equals(jsonMessage.getString("action"))) {
                int id = jsonMessage.getInt("id");
                sessionHandler.removeDevice(id);
            }

            if ("toggle".equals(jsonMessage.getString("action"))) {
                int id = jsonMessage.getInt("id");
                sessionHandler.toggleDevice(id);
            }
        }

        LOG.debug("<-- handleMessage");
	}
}
