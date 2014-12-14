package com.sample.model;

public class Image {
	private String url;
	private final int WIDTH = 20;
	private final int HEIGHT = 20;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public int getWidth() {
		return WIDTH;
	}

	public int getHeight() {
		return HEIGHT;
	}
}
