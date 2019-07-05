package com.yzc.bigdata.competitor.repository;


import com.yzc.bigdata.competitor.entity.node.CompanyCp;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Component;

@Component
public interface BehaviorRepository extends Neo4jRepository<CompanyCp, Long> {



}
