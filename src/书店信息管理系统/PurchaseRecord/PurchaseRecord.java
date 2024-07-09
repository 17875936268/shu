package 书店信息管理系统.PurchaseRecord;

import java.util.Date; // 导入Date类用于表示日期

public class PurchaseRecord {
    private Long purchaseId; // 购买记录ID
    private Long bookId; // 书籍ID
    private Date purchaseDate; // 购买日期
    private double payment; // 支付金额
    private String shippingMethod; // 配送方式

    // 默认无参构造函数
    public PurchaseRecord() {
    }

    // 带有参数的构造函数
    public PurchaseRecord(Long purchaseId, Long bookId, Date purchaseDate, double payment, String shippingMethod) {
        this.purchaseId = purchaseId;
        this.bookId = bookId;
        this.purchaseDate = purchaseDate;
        this.payment = payment;
        this.shippingMethod = shippingMethod;
    }

    // Getter方法
    public Long getPurchaseId() {
        return purchaseId;
    }

    public Long getBookId() {
        return bookId;
    }

    public Date getPurchaseDate() {
        return purchaseDate;
    }

    public double getPayment() {
        return payment;
    }

    public String getShippingMethod() {
        return shippingMethod;
    }

    // Setter方法
    public void setPurchaseId(Long purchaseId) {
        this.purchaseId = purchaseId;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }

    public void setPurchaseDate(Date purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public void setPayment(double payment) {
        this.payment = payment;
    }

    public void setShippingMethod(String shippingMethod) {
        this.shippingMethod = shippingMethod;
    }

    // 重写toString方法以便于输出信息
    @Override
    public String toString() {
        return "PurchaseRecord{" +
                "purchaseId=" + purchaseId +
                ", bookId=" + bookId +
                ", purchaseDate=" + purchaseDate +
                ", payment=" + payment +
                ", shippingMethod='" + shippingMethod + '\'' +
                '}';
    }
}