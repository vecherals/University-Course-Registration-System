package com.university.repository;
import java.util.ArrayList;
import java.util.List;
public abstract class InMemoryRepository<T> implements Repository<T, String> { 
	//it uses <T> so it can work for students or courses, and this class follows the rules of repository
	protected List<T> storage = new ArrayList<>();
	
	@Override 
	public void save(T item) {
		String newId = getId(item); //checking if its already there, to avoid duplicates.  looking for an item with the same id.
		delete(newId); //if it exists, delete the old one
		storage.add(item); // add the new item
	}
	
	@Override
	public T findById(String id) {
		for(T item : storage) {
			if (getId(item).equals(id)) {
				return item;
			}
		}
		return null;
	}
	
	@Override
	public List<T> findAll() {
		return storage; //return the whole list
	}
	
	@Override
	public void delete(String id) {
		T itemToRemove = findById(id); 
		if(itemToRemove != null) { //to avoid errors while looping
			storage.remove(itemToRemove);
		}
	}
	
	protected abstract String getId(T item); //abstract method so we know how to check id's inside the loops;

}
