package com.trillion.cidrmanagement.service;

import java.util.Collection;
import java.util.Optional;

import org.springframework.http.ResponseEntity;

import com.trillion.cidrmanagement.domain.Cidr;

public interface CidrService {

	public Optional<Cidr> findById(Long id);
	
	public Optional<Cidr> findByIp(String ip);

	public Collection<Cidr> findAll();
	
	public  Iterable<Cidr> saveIpRanges(String cidrBlock);
	
	//public Boolean deleteById(Long id);
	public Boolean deleteByIp(String ip);
	
	public void deleteAll();
	
	public Cidr saveIp(Cidr cidr);

}
