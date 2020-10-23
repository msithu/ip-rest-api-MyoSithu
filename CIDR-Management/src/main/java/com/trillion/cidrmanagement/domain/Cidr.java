package com.trillion.cidrmanagement.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name="CIDR")

public class Cidr {
	
	public Cidr(){}
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	long id;
	
	@Column(name="IP")
	String ip;
	
	@Column(name="STATUS")
	String status;
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}



	public Cidr(long id, String ip, String status) {
		this.id = id;
		this.ip = ip;
		this.status = status;
		
	}

	@Override
	public String toString() {
		return "Cidr [ip=" + ip + ", status=" + status + "]";
	}
		
	
	
	
	
}
