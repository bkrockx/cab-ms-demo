package com.demo.cab.ms.utils;

import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Component;

@Component
public class PasswordUtil {

  private static int workload = 12;

  public String hashPassword(String password_plaintext) {
    String salt = BCrypt.gensalt(workload);
    String hashedPassword = BCrypt.hashpw(password_plaintext, salt);
    return(hashedPassword);
  }


  public boolean checkPassword(String password_plaintext, String stored_hash) {
    boolean passwordVerified = false;
    if(null == stored_hash)
      throw new java.lang.IllegalArgumentException("Invalid hash provided for comparison");
    passwordVerified = BCrypt.checkpw(password_plaintext, stored_hash);
    return(passwordVerified);
  }

}
