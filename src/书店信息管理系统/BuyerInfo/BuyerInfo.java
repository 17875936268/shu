package 书店信息管理系统.BuyerInfo;

public class BuyerInfo {
    private Long purchaseId; // 购书者ID（或称为会员ID）
    // 注意：通常购书者信息不会包含BookId，除非有特殊需求
    // 如果要表示最近购买的书籍ID，可以作为一个额外的字段，但通常不推荐这样做
    private Long bookId; // 如果需要，可以表示最近购买的书籍ID
    private String name; // 姓名
    private String gender; // 性别
    private int age; // 年龄
    private String phone; // 电话号码

    // 默认无参构造函数
    public BuyerInfo() {
    }

    // 带有参数的构造函数
    public BuyerInfo(Long purchaseId, Long bookId, String name, String gender, int age, String phone) {
        this.purchaseId = purchaseId;
        this.bookId = bookId; // 谨慎使用此字段，通常不推荐在购书者信息中包含书籍ID
        this.name = name;
        this.gender = gender;
        this.age = age;
        this.phone = phone;
    }

    // Getter和Setter方法
    public Long getPurchaseId() {
        return purchaseId;
    }

    public void setPurchaseId(Long purchaseId) {
        this.purchaseId = purchaseId;
    }

    public Long getBookId() {
        return bookId;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    // 重写toString方法
    @Override
    public String toString() {
        return "BuyerInfo{" +
                "purchaseId=" + purchaseId +
                ", bookId=" + bookId + // 如果不需要显示，可以移除这一行
                ", name='" + name + '\'' +
                ", gender='" + gender + '\'' +
                ", age=" + age +
                ", phone='" + phone + '\'' +
                '}';
    }
}