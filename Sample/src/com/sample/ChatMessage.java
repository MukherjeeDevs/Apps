package com.sample;

public class ChatMessage {
	private String message;
	private String sender;
	private String received;
	private String recentImages = "";

	public String getMessage() {
		return message;
	}

	public void setRecentImages(String recentImages) {
		this.recentImages = recentImages;
	}

	public String getRecentImages() {
		return recentImages;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	public String getReceived() {
		return received;
	}

	public void setReceived(String received) {
		this.received = received;
	}

}
