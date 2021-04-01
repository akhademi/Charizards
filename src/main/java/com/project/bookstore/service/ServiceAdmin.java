package com.project.bookstore.service;

import com.project.bookstore.miscellaneous.ErrorCodes;
import com.project.bookstore.model.BooksSoldModel;
import com.project.bookstore.repository.AdminRepository;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServiceAdmin {

  @Autowired
  ServiceUser userService;
  @Autowired
  AdminRepository adminRepository;

  public String generateReport(String adminUserId){
    JSONObject json = new JSONObject();
    // make sure the userId belongs to admin
    if (!userService.isUserAdmin(adminUserId)){
      json.put("status", ErrorCodes.RESPONSE_FAIL);
      json.put("message", "Unauthorized access denied.");
      return json.toString(4);
    }

    List<BooksSoldModel> list = adminRepository.returnBooksSold();

    StringBuilder table = new StringBuilder("<table><tr><th>BID</th><th>TITLE</th><th>PRICE</th><th>QUANTITY SOLD</th></tr>");
    for(BooksSoldModel book: list){
      table.append("<tr><td>").append(book.getBid()).append("</td><td>").append(book.getTitle()).append("</td><td>")
              .append(book.getPrice()).append("</td><td>").append(book.getQuantity()).append("</td></tr>");
    }
    table.append("</table>");

    return table.toString();
  }

  public String getTopSoldBooks(String adminUserId){
    JSONObject json = new JSONObject();
    // make sure the userId belongs to admin
    if (!userService.isUserAdmin(adminUserId)){
      json.put("status", ErrorCodes.RESPONSE_FAIL);
      json.put("message", "Unauthorized access denied.");
      return json.toString(4);
    }

    List<BooksSoldModel> list = adminRepository.topSoldBooks();

    StringBuilder table = new StringBuilder("<table><tr><th>BID</th><th>TITLE</th><th>QUANTITY SOLD</th></tr>");
    for(BooksSoldModel book: list){
      table.append("<tr><td>").append(book.getBid()).append("</td><td>").append(book.getTitle()).append("</td><td>")
              .append(book.getQuantity()).append("</td></tr>");
    }
    table.append("</table>");

    return table.toString();
  }


}
