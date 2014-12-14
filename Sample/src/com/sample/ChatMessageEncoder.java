package com.sample;

import java.util.Date;

import javax.json.Json;
import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

public class ChatMessageEncoder implements Encoder.Text<ChatMessage> {

	@Override
	public void init(final EndpointConfig arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

	@Override
	public String encode(final ChatMessage chatMessage) throws EncodeException {
		// TODO Auto-generated method stub

		return Json.createObjectBuilder()
				.add("message", chatMessage.getMessage())
				.add("sender", chatMessage.getSender())
				.add("received", new Date().toString())
				.add("recent", chatMessage.getRecentImages()).build()
				.toString();

	}
}
