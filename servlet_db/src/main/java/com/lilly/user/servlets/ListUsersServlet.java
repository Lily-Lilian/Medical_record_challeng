package com.lilly.user.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lilly.database.Users;
import com.lilly.user.models.User;
import com.lilly.user.utils.ApiResponse;
import com.lilly.user.utils.ResponseFormat;

@WebServlet("/users/all")
@WebInitParam(name = "email", value = "Not provided")
public class ListUsersServlet extends HttpServlet {
  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
    try {
      List<User> result = Users.getUsers();
      ResponseFormat.response(res, new ApiResponse<List<User>>("Users retrieved", result), HttpServletResponse.SC_OK);
    } catch (Exception e) {
      e.printStackTrace();
      ResponseFormat.response(res, new ApiResponse<>(e.getMessage(), null), HttpServletResponse.SC_FORBIDDEN);
    }
  }

}
