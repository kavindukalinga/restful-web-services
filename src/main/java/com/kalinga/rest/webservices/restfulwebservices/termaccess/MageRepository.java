package com.kalinga.rest.webservices.restfulwebservices.termaccess;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MageRepository extends JpaRepository<Mages, Integer> {
}
