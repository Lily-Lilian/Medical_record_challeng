package com.lilly.user.servlets;

import com.lilly.user.enums.ERole;
import com.lilly.user.models.Admin;
import com.lilly.user.models.Patient;
import com.lilly.user.models.Pharmacist;
import com.lilly.user.models.Physician;
import com.lilly.user.models.User;
import com.lilly.user.utils.ApiResponse;
import com.lilly.user.utils.JsonUtil;
import com.lilly.user.utils.ResponseFormat;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/user/signup")
public class registerServlet extends HttpServlet {

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    try {
      User user;
      user = new JsonUtil().parseBodyJson(req, Patient.class);
      // VALIDATIONS
      if (user.getFirstName() == null) throw new RuntimeException("First Name is required");
      if (user.getLastName() == null) throw new RuntimeException("Last Name is required");
      if (user.getEmail() == null) throw new RuntimeException("Email is required");
      if (user.getPassword() == null) throw new RuntimeException("Password is required");
      if (user.getRole() == null) throw new RuntimeException("Role is required");
      if (user.getAge() == null) throw new RuntimeException("Age is required");
      if (user.getGender() == null) throw new RuntimeException("Gender is required");
      if (user.getCountry() == null || user.getCountry().isEmpty()) throw new RuntimeException("Country is required");

      ApiResponse<User> result = null;

      User newUser = null;
      if (user.getRole() == ERole.Admin) newUser = new Admin();
      else if (user.getRole() == ERole.Patient) newUser = new Patient();
      else if (user.getRole() == ERole.Pharmacist) newUser = new Pharmacist();
      else newUser = new Physician();
    
      newUser.setFirstName(user.getFirstName());
      newUser.setGender(user.getGender());
      newUser.setCountry(user.getCountry());
      newUser.setEmail(user.getEmail());
      newUser.setRole(user.getRole());
      newUser.setLastName(user.getLastName());
      newUser.setAge(user.getAge());
      newUser.setPassword(user.getPassword());

      result = newUser.signup();
      ResponseFormat.response(resp, result, HttpServletResponse.SC_CREATED);
    } catch (Exception error) {
      error.printStackTrace();
      System.out.println("========>>>>> " + error);
      ResponseFormat.response(resp, new ApiResponse<>(error.getMessage(), null), HttpServletResponse.SC_BAD_REQUEST);
    }
  }
}
