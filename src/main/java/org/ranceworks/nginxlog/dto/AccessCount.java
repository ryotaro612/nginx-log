package org.ranceworks.nginxlog.dto;

public class AccessCount {

	private String url;

	private long count;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public long getCount() {
		return count;
	}

	public void setCount(long count) {
		this.count = count;
	}

}
