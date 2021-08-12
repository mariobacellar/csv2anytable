package br.com.macro.lab.model;

import java.sql.Timestamp;

public class PoolCtrl {

	private String type;
	private Timestamp  created;
	private String status;
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Timestamp getCreated() {
		return created;
	}
	public void setCreated(Timestamp created) {
		this.created = created;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	
}
