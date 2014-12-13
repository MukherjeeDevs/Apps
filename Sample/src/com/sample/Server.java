package com.sample;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint(value = "/hello/{room}", encoders = ChatMessageEncoder.class, decoders = ChatMessageDecoder.class)
public class Server {

	@OnError
	public void error(Throwable error) {
		System.out.println(error.getMessage());
	}

	// @OnMessage
	// public void doMessage(String msg, Session session) {
	// System.out.println("Message received :" + msg);
	// try {
	// session.getBasicRemote().sendText("Ok Buddy!!!");
	// } catch (IOException e) {
	// e.printStackTrace();
	// }
	// }

	@OnMessage
	public void doChatMessage(final Session session,
			final ChatMessage chatMessage) {
		System.out.println("Received by server");
		try {
			String room = (String) session.getUserProperties().get("room");
			for (Session s : session.getOpenSessions()) {
				if (s.isOpen()
						&& room.equals((String) s.getUserProperties().get(
								"room"))) {
					System.out.println("Message received :"
							+ chatMessage.getMessage() + "  - sent by "
							+ chatMessage.getSender());
					s.getBasicRemote().sendObject(chatMessage);

				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@OnOpen
	public void onMyOpen(final Session session,
			@PathParam("room") final String room) {
		System.out.println("Websocket Opened :" + session.getId());
		session.getUserProperties().put("room", room);
	}

	@OnClose
	public void onMyClose(Session session) {
		System.out.println("Websocket closed :" + session.getId());
	}

}
