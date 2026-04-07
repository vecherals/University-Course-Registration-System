package com.university.repository;
import java.util.List;

public interface Repository<T, ID> { //<T> is a placeholder, when we use this later we will replace >T> and ID is a string
	void save(T item); // i must be able to save an item,
	
	T findById(ID id); // i must be able to find an item by its ID
	
	List<T> findAll(); // i must be able to list everything
	
	void delete(ID id); //i must be able delete an item

}
