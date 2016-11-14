package org.ranceworks.nginxlog.entities;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "access_log", schema = "ranceworks")
public class AccessLog {

	@Id
	@GeneratedValue
	private int id;
	private Time timeGmt;
	private Date dateGmt;
	private String city;
	private String countryCode;
	private String uri;
	private String remoteAddr;
	private String httpUserAgent;
	private Timestamp storeTime;

	public AccessLog() {
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Time getTimeGmt() {
		return timeGmt;
	}

	public void setTimeGmt(Time timeGmt) {
		this.timeGmt = timeGmt;
	}

	public Date getDateGmt() {
		return dateGmt;
	}

	public void setDateGmt(Date dateGmt) {
		this.dateGmt = dateGmt;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public String getRemoteAddr() {
		return remoteAddr;
	}

	public void setRemoteAddr(String remoteAddr) {
		this.remoteAddr = remoteAddr;
	}

	public String getHttpUserAgent() {
		return httpUserAgent;
	}

	public void setHttpUserAgent(String httpUserAgent) {
		this.httpUserAgent = httpUserAgent;
	}

	public Timestamp getStoreTime() {
		return storeTime;
	}

	public void setStoreTime(Timestamp storeTime) {
		this.storeTime = storeTime;
	}

}
