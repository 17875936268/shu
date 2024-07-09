package 书店信息管理系统.BookInfo;

public class BookInfo {
    private Long bookId; // 书籍ID
    private String category; // 类别
    private String bookname; // 书名
    private double price; // 价格
    private String description; // 描述
    private int store; // 库存量
    private int SaleId;

    // 默认无参构造函数
    public BookInfo() {
    }

    // 带有所有参数的构造函数
    public BookInfo(Long bookId, String category, String bookname, double price, String description, int store) {
        this.bookId = bookId;
        this.category = category;
        this.bookname = bookname;
        this.price = price;
        this.description = description;
        this.store = store;
        this.SaleId=SaleId;
    }

    // Getter和Setter方法
    public Long getBookId() {
        return bookId;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getBookname() {
        return bookname;
    }

    public void setBookname(String bookname) {
        this.bookname = bookname;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getStore() {
        return store;
    }

    public void setStore(int store) {
        this.store = store;
    }
    public int getSaleId() {
        return SaleId;
    }
    public int setSaleId(int SaleId) {
        this. SaleId =  SaleId;
        return SaleId;
    }
    // 重写toString方法，以便方便地输出书籍信息
    @Override
    public String toString() {
        return "BookInfo{" +
                "bookId=" + bookId +
                ", category='" + category + '\'' +
                ", bookname='" + bookname + '\'' +
                ", price=" + price +
                ", description='" + description + '\'' +
                ", store=" + store +
                '}';
    }
}
