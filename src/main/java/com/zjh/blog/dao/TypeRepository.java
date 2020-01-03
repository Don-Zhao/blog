package com.zjh.blog.dao;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.zjh.blog.po.Type;

@Repository
public interface TypeRepository extends JpaRepository<Type, Long>{
	Type findByName(String name);
	
	@Query("select t from Type t")
	List<Type> findTop(Pageable pageable);
}
