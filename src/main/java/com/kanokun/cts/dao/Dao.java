package com.kanokun.cts.dao;

import java.util.List;

import com.kanokun.cts.entity.SerializableEntity;

public interface Dao<T extends SerializableEntity, I> {

	List<T> findAll();

	T find(I id);

	T save(T entity);

	void delete(I id);

}