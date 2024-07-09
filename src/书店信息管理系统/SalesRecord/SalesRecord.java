package 书店信息管理系统.SalesRecord;

import java.util.Date;

public class SalesRecord {
    private long saleId;
    private long bookId;
    private Date saleDate;
    private int quantity;

    public SalesRecord(long bookId, Date saleDate, int quantity) {
        this.bookId = bookId;
        this.saleDate = saleDate;
        this.quantity = quantity;
    }

    public long getSaleId() {
        return saleId;
    }

    public void setSaleId(long saleId) {
        this.saleId = saleId;
    }

    public long getBookId() {
        return bookId;
    }

    public void setBookId(long bookId) {
        this.bookId = bookId;
    }

    public Date getSaleDate() {
        return saleDate;
    }

    public void setSaleDate(Date saleDate) {
        this.saleDate = saleDate;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "SalesRecord{" +
                "saleId=" + saleId +
                ", bookId=" + bookId +
                ", saleDate=" + saleDate +
                ", quantity=" + quantity +
                '}';
    }
}