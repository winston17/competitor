package com.yzc.bigdata.competitor.repository;

import com.yzc.bigdata.competitor.entity.node.Person;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Component
public interface PersonRepository extends Neo4jRepository<Person, Long> {

    @Query("MERGE (n:Person{name: {name}, age: {age}}) RETURN n")
    Person addPersonNode(@Param("name") String name, @Param("age") Integer age);

    @Query("MERGE (n:Person{name: {name}}) RETURN n LIMIT 1")
    Person addPersonNode(@Param("name") String name);

    @Query("MERGE (n:Person{name: {name}}) RETURN n")
    List<Person> addPersonNodeName(@Param("name") String name);

    @Query("MERGE (n:Person{name: {name}}) SET n.nickName = {nickName}, n.height = {height} RETURN n")
    Person setPersonNode(@Param("name") String name, @Param("age") Integer age,
                         @Param("nickName") String nickName, @Param("height") Integer height);

    @Query("MERGE (n:Person{name: '马武', age: 25}) RETURN 1")
    int testCql();

    @Query("MATCH (n:Person{name: {name}}) RETURN n LIMIT 1")
    List<Person> addPersonEx(String name);

    @Query("MERGE (n:Person{name: {name}, isSmart: {isSmart}, weight: {weight}}) RETURN n LIMIT 1")
    Person addPersonTest(@Param("name") String name,
                         @Param("isSmart") boolean isSmart,
                         @Param("weight") BigDecimal weight);




    @Query("MATCH (n:Person{name: {name}}) RETURN n.name")
    List<String> matchPersonTest(@Param("name") String name);

    @Query("MATCH (n:Person{name: {name}}) RETURN id(n) AS id, labels(n) AS labels, n.name AS name, n.age AS age")
    List<Map<String, Object>> matchPersonMap(@Param("name") String name);


    @Query("MERGE (n:Person{name: {name}, money: {money}}) RETURN n")
    Person addPersonEx2(@Param("name") String name, @Param("money") String money);

}
