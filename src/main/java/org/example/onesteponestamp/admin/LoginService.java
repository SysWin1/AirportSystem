package org.example.onesteponestamp.admin;

public class LoginService {

  private final AdminLoginDAO adminLoginDAO;
  private final AuthManager authManager;

  public LoginService() {
    this.adminLoginDAO = new AdminLoginDAO();
    this.authManager = AuthManager.getInstance();
  }

  public boolean login(String id, String password) {
    boolean success = adminLoginDAO.login(id, password);
    if (success) {
      authManager.login(AdminDTO.builder().id(id).build());
    }
    return success;
  }
}
