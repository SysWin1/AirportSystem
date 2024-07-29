package org.example.onesteponestamp.admin;

import lombok.Getter;

@Getter
public class AuthManager {

  private static AuthManager instance;
  private AdminDTO loggedInAdmin;

  public static AuthManager getInstance() {
    if (instance == null) {
      instance = new AuthManager();
    }
    return instance;
  }

  public void login(AdminDTO admin) {
    loggedInAdmin = admin;
  }

  public void logout() {
    loggedInAdmin = null;
  }

  public boolean isAuthenticated() {
    return loggedInAdmin != null;
  }
}
