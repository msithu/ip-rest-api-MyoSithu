package com.trillion.cidrmanagement.service;

import java.util.ArrayList;
import java.util.Collection;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trillion.cidrmanagement.domain.Cidr;
import com.trillion.cidrmanagement.repository.CidrRepository;

import inet.ipaddr.IPAddress;
import inet.ipaddr.IPAddressString;

import org.apache.commons.net.util.*;

@Service
public class CidrServiceImpl implements CidrService {
	
	@Autowired
	private CidrRepository cidrRepo;
	private Iterable<Cidr> cidrArray;
	
	public CidrServiceImpl(CidrRepository cidrRepository) {
		this.cidrRepo = cidrRepository;
	}
	
	public Optional<Cidr> findById(Long id) {
		return cidrRepo.findById(id);
	}

	public Collection<Cidr> findAll() {
		return cidrRepo.findAll();
	}
	
	public Cidr saveIp(Cidr cidr) {
		return cidrRepo.save(cidr);
	}
	
	public Boolean deleteByIp(String ip) {
		Optional<Cidr> found = cidrRepo.findByIp(ip); 
			if (found.isPresent()) {
				Cidr delete = found.get();
				cidrRepo.delete(delete);
				return true;
		}
		return false;
	}
	public Optional<Cidr> findByIp(String ipAddress){
		
		return cidrRepo.findByIp(ipAddress);
	}
	
	public void deleteAll(){
		
		cidrRepo.deleteAll();
		
	}
	
	public  Iterable<Cidr> saveIpRanges(String cidrBlock) {
		
		
		SubnetUtils utils = new SubnetUtils(cidrBlock);
		String[] allIps = utils.getInfo().getAllAddresses();
		cidrArray = new ArrayList<Cidr>();
 		for (int index = 0; index < allIps.length; index ++) {
			
			Cidr cidrItem = new Cidr();
			cidrItem.setIp(allIps[index]);
			cidrItem.setStatus("AVAILABLE");
			((ArrayList<Cidr>) cidrArray).add(cidrItem);
		}
 		return cidrRepo.saveAll(cidrArray);
		
	}
	/*
	static String[] toRange(String str) {
	    IPAddressString string = new IPAddressString(str);
	    IPAddress addr = string.getAddress();
	    System.out.println("starting with CIDR " + addr);
	    IPAddress lower = addr.getLower(), upper = addr.getUpper();
	    System.out.println("range is " + lower + " to " + upper);
	    return new String[] {lower.toString(), upper.toString()};
	
	}*/



	
	

}
