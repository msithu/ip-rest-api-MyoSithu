package com.trillion.cidrmanagement.repository;
import java.util.Collection;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.trillion.cidrmanagement.domain.Cidr;

@Repository
public interface CidrRepository extends CrudRepository<Cidr, Long>{

    public Optional<Cidr> findById(Long Id);

    public Collection<Cidr> findAll();
    
    public Optional<Cidr> findByIp(String Ip);

//	public EventsDomain save(EventsDomain event);

	//public void delete(Cidr cidr);


}
