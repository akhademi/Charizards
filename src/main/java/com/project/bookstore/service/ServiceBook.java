package com.project.bookstore.service;

import com.project.bookstore.model.EntityBookModel;
import com.project.bookstore.repository.BookRepository;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Service
public class ServiceBook {

    org.slf4j.Logger log = LoggerFactory.getLogger(ServiceBook.class);

    @Autowired
    private BookRepository bookRepository;

    public List<EntityBookModel> getAllBooks(Integer offset) throws Exception {
        if (offset > 0) {
            List<EntityBookModel> books = new ArrayList<>();
            try {
                books = bookRepository.findAllBook((offset - 1) * 10);

            }catch (Exception e) {
                throw new Exception(e);
            }
            return books;
        }
        else {
            log.error("Page no. cannot be 0");
            return new ArrayList<>();
        }
    }

    public List<EntityBookModel> getBooksByCategory(String category,Integer pageno) throws Exception{
        if(StringUtils.isEmpty(category)){
            return new ArrayList<>();
        }
        try{
            return bookRepository.getBooksByCategory(category,pageno);
        } catch (Exception e){
            log.error(e.getMessage(), e);
            return new ArrayList<>();
        }
    }

    public List<String> getAllCategory() throws Exception {
        List<String> categories;
        try {
            categories = bookRepository.findAllCategory();
        }
        catch(Exception e) {
            throw new Exception(e);
        }
        return categories;
    }

    public EntityBookModel getBookInfo(int bid){
        if(bid < 0){
            return null;
        }
        return bookRepository.findEntityBookModelByBid(bid);
    }


    public List<EntityBookModel> searchBooksByTitle(String title) throws Exception{
        List<EntityBookModel> books;
        try{
             books = bookRepository.searchBooksByTitle(title);
        } catch (Exception e){
            throw new Exception(e);
        }
        return books;
    }

}
