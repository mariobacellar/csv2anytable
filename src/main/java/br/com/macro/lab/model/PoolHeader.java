package br.com.macro.lab.model;

import java.sql.Timestamp;

public class PoolHeader {

	private String type;
	private Timestamp  created;
	private String line;
	
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
	public String getLine() {
		return line;
	}
	public void setLine(String line) {
		this.line = line;
	}
	
}
