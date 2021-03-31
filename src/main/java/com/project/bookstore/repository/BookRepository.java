package com.project.bookstore.repository;

import com.project.bookstore.model.EntityBookModel;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

//Repository for CRUD operation
public interface BookRepository extends CrudRepository<EntityBookModel, Integer> {

    //Custom method to get 10 books at a time
    @Query(value = "select * from BOOK limit 10 offset ?1",nativeQuery = true)
    List<EntityBookModel> findAllBook(Integer offset);

    @Query(value = "select * from book where category = ?1 limit 10 offset ?2", nativeQuery = true)
    List<EntityBookModel> getBooksByCategory(String category,Integer pageno);

    @Query(value = "select CATEGORY from book group by CATEGORY", nativeQuery = true)
    List<String> findAllCategory();

    @Query(value = "select * from book where bid = :bid", nativeQuery = true)
    EntityBookModel findEntityBookModelByBid(int bid);

    @Query(value = "select * from book where upper(title) like '%' || upper(?1) || '%' order by CATEGORY", nativeQuery = true)
    List<EntityBookModel> searchBooksByTitle(String title);


}
