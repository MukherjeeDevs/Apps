package com.sample;

import java.io.StringReader;

import javax.json.Json;
import javax.json.JsonObject;
import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;

public class ChatMessageDecoder implements Decoder.Text<ChatMessage> {

	@Override
	public boolean willDecode(String arg0) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public ChatMessage decode(final String encodedMessage)
			throws DecodeException {
		// TODO Auto-generated method stub
		ChatMessage chatMessage = new ChatMessage();

		JsonObject jsonObj = Json
				.createReader(new StringReader(encodedMessage)).readObject();
		chatMessage.setMessage(jsonObj.getString("message"));
		chatMessage.setReceived(jsonObj.getString("received"));
		chatMessage.setSender(jsonObj.getString("sender"));
		chatMessage.setRecentImages(jsonObj.getString("recent"));

		return chatMessage;
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

	@Override
	public void init(final EndpointConfig arg0) {
		// TODO Auto-generated method stub

	}
}
