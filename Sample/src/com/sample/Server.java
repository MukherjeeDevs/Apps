package com.sample;

import java.io.IOException;
import java.util.Deque;
import java.util.List;

import javax.websocket.EncodeException;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import com.sample.model.Image;

@ServerEndpoint(value = "/hello/{room_pwd}", encoders = ChatMessageEncoder.class, decoders = ChatMessageDecoder.class)
public class Server {

	private Deque<Image> recent = null;
	private final int MAX_SIZE = 10;

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

	private void populateDeque(List<Image> items) {
		for (Image img : items) {
			if (recent.size() < MAX_SIZE)
				recent.add(img);

		}
	}

	@OnOpen
	public void onMyOpen(final Session session,
			@PathParam("room_pwd") final String room_pwd) {
		System.out.println("Websocket Opened :" + session.getId());

		String[] vals = room_pwd.split("&");
		String room = vals[0];
		String pwd = vals[1];
		System.out.println(room);
		System.out.println(pwd);

		for (Session s : session.getOpenSessions()) {
			if (s != null) {

				if (room.equals("family") && pwd.equals("Rambo@123")) {
					// recent = new LinkedList<Image>();
					// ArrayList<Image> images = XMLLoader.loadXML("username");
					// populateDeque(images);

					// if (recent.size() >= 1)
					// s.getUserProperties().put("recent", recent);

					System.out
							.println("Server recieved GOOD password, Start session");
					s.getUserProperties().put("room", room);
					s.getUserProperties().put("password", pwd);

					ChatMessage chatMessage = new ChatMessage();
					chatMessage.setSender("Server");
					chatMessage.setMessage("You are Authenticated");

					// StringBuffer buffer = new StringBuffer();
					//
					// for (Image img : recent) {
					// if (img.equals(recent.getLast())) {
					// buffer.append(img.getUrl() + ";");
					// } else {
					// buffer.append(img.getUrl() + ",");
					// }
					// }
					//
					// buffer.append("20px,20px");
					// System.out.println(buffer.toString());
					// chatMessage.setRecentImages(buffer.toString());

					try {
						s.getBasicRemote().sendObject(chatMessage);
					} catch (IOException e) {
						e.printStackTrace();
					} catch (EncodeException e) {
						e.printStackTrace();
					}
					return;
				}

				else if (room.equals("friends") && pwd.equals("Rambo@123")) {
					System.out
							.println("Server recieved GOOD password, Start session");
					s.getUserProperties().put("room", room);
					s.getUserProperties().put("password", pwd);
					ChatMessage chatMessage = new ChatMessage();
					chatMessage.setSender("Server");
					chatMessage.setMessage("You are Authenticated");
					try {
						s.getBasicRemote().sendObject(chatMessage);
					} catch (IOException e) {
						e.printStackTrace();
					} catch (EncodeException e) {
						e.printStackTrace();
					}
					return;

				} else {

					System.out
							.println("Server recieved wrong password, Closing");
					ChatMessage chatMessage = new ChatMessage();
					chatMessage.setSender("Server");
					chatMessage.setMessage("Sorry,Wrong Password");
					try {
						s.getBasicRemote().sendObject(chatMessage);
						s.close();
					} catch (IOException e) {
						e.printStackTrace();
					} catch (EncodeException e) {
						e.printStackTrace();
					}
					return;
				}

			}
		}

	}

	@OnClose
	public void onMyClose(Session session) {
		System.out.println("Websocket closed :" + session.getId());
	}

}
