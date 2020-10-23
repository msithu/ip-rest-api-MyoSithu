package com.trillion.cidrmanagement.api;

import java.net.URI;
import java.util.Collection;
import java.util.Optional;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriComponentsBuilder;

import com.trillion.cidrmanagement.domain.Cidr;
import com.trillion.cidrmanagement.service.CidrService;

import io.opentracing.Span;
import io.opentracing.Tracer;

@RestController
@RequestMapping("/cidr")
public class CidrResource {
	@Autowired
	private CidrService cidrService;

	
	@GetMapping("/{id}")
	public Optional<Cidr> findEventById(@PathVariable("id") Long id) {
		return cidrService.findById(id);
	}
	
	@GetMapping
	public Collection<Cidr> findAllCidr() {
		
		
		Collection<Cidr> cidrs = cidrService.findAll();
	

		return cidrs;
	}
	
	@PostMapping()
	public ResponseEntity<?> addCidrBlock(@RequestBody String cidrBlock, UriComponentsBuilder uri) {
		String cidrBlockString = cidrBlock;
		int slashIndex = cidrBlockString.indexOf("/");
		if (slashIndex == 0 ) {
			return  new ResponseEntity<Iterable<Cidr>>(HttpStatus.BAD_REQUEST);
		}
		else {
			
			String ipString = cidrBlockString.substring(0, slashIndex);
			if (validIPAddress(ipString).equalsIgnoreCase("IPv4")) {
				
				Iterable<Cidr> response = cidrService.saveIpRanges(cidrBlockString);
				return  new ResponseEntity<Iterable<Cidr>>(HttpStatus.ACCEPTED);
			}
			else {
				
				return  new ResponseEntity<Iterable<Cidr>>(HttpStatus.BAD_REQUEST);
			}
		}
		
		
	}
	
	@PutMapping("/acquire/{cidrIp}")
	public ResponseEntity<?> acquireIp(@PathVariable("cidrIp") String cidrIp) {
		
		Optional<Cidr> currCidr = cidrService.findByIp(cidrIp);
		if (currCidr.isPresent()) {
		    Cidr tmpCidr = currCidr.get();	
		    tmpCidr.setStatus("ACQUIRED");
			tmpCidr = cidrService.saveIp(tmpCidr);
			return ResponseEntity.ok().build();
		}
		else {
			return ResponseEntity.notFound().build();
		}
	}
	
	@PutMapping("/release/{cidrIp}")
	public ResponseEntity<?> releaseIp(@PathVariable("cidrIp") String cidrIp) {
		
		Optional<Cidr> currCidr = cidrService.findByIp(cidrIp);
		if (currCidr.isPresent()) {
		    Cidr tmpCidr = currCidr.get();	
		    tmpCidr.setStatus("AVAILABLE");
			tmpCidr = cidrService.saveIp(tmpCidr);
			return ResponseEntity.ok().build();
		}
		else {
			return ResponseEntity.notFound().build();
		}
	}
	
	@DeleteMapping("/{cidrIp}")
	public ResponseEntity<?> deleteEvent(@PathVariable("cidrIp") String cidrIp) {
		Boolean isRemoved = cidrService.deleteByIp(cidrIp);
		
		if (!isRemoved) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		return ResponseEntity.ok().build();
	}
	
	@DeleteMapping()
	public ResponseEntity<?> deleteAll(){
		cidrService.deleteAll();
		
		
		return ResponseEntity.ok().build();
	}
	

	  

	  private String validIPAddress(String IP) {
		  String chunkIPv4 = "([0-9]|[1-9][0-9]|1[0-9][0-9]|2[0-4][0-9]|25[0-5])";
		  Pattern pattenIPv4 =
		          Pattern.compile("^(" + chunkIPv4 + "\\.){3}" + chunkIPv4 + "$");  
		  String chunkIPv6 = "([0-9a-fA-F]{1,4})";
		  Pattern pattenIPv6 =
		          Pattern.compile("^(" + chunkIPv6 + "\\:){7}" + chunkIPv6 + "$");
	    if (pattenIPv4.matcher(IP).matches()) return "IPv4";
	    return (pattenIPv6.matcher(IP).matches()) ? "IPv6" : "Neither";
	  }
	
	
}
