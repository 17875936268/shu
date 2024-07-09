package 书店信息管理系统;

public class User {
    private String username;
    private String role; // "admin" 或 "clerk"

    public User(String username, String role) {
        this.username = username;
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public String getRole() {
        return role;
    }

    // 通常这里还会有其他方法，比如setPassword（但密码不会在User对象中明文存储）
}