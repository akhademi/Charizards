package com.project.bookstore.repository;

import com.project.bookstore.model.EntityBookModel;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

// book repository uses the CRUD repository framework
public interface BookRepo extends CrudRepository<EntityBookModel, Integer> {

	// CRUD repository allows you query creation by looking for the prefixes: 
	// find...By, read...By, get...By
	
	// these methods will be called from the BookCtrl and BookService class
	
    // method used to get 10 books at a time
    @Query(value = "SELECT * FROM BOOK LIMIT 10 OFFSET ?1", nativeQuery = true)
    List<EntityBookModel> findAllBooks(Integer offset);
    
    // method used to search for book by title
    @Query(value = "SELECT * FROM book WHERE UPPER(title) LIKE '%' || UPPER(?1) || '%' ORDER BY CATEGORY", nativeQuery = true)
    List<EntityBookModel> searchBooksByTitle(String title);

    // method used to get 10 books in a category
    @Query(value = "SELECT * FROM book WHERE category = ?1 LIMIT 10 OFFSET ?2", nativeQuery = true)
    List<EntityBookModel> getBooksByCategory(String category,Integer pageno);

    // method used to get all categories
    @Query(value = "SELECT CATEGORY FROM book GROUP BY CATEGORY", nativeQuery = true)
    List<String> findAllCategories();

    // method used to select a book by BID (book ID)
    @Query(value = "SELECT * FROM book WHERE bid = :bid", nativeQuery = true)
    EntityBookModel findBookEntityByBid(int bid);

}
