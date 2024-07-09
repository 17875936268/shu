package 书店信息管理系统.user;




public class Admin extends Clerk {
    public Admin() {
        super(0, "admin", "admin"); // 默认构造一个 Admin 用户，可以根据实际情况修改默认值
    }

    public Admin(int id, String username, String password) {
        super(id, username, password);
    }
}