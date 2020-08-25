import java.time.LocalDate;

public class Product {
    private String productName;
    private int count;
    private LocalDate expirationDate;

    public Product(String productName, int count, LocalDate expirationDate) {
        this.productName = productName;
        this.count = count;
        this.expirationDate = expirationDate;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public LocalDate getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(LocalDate expirationDate) {
        this.expirationDate = expirationDate;
    }

    @Override
    public String toString() {
        return "Product{" +
                "productName='" + productName + '\'' +
                ", count=" + count +
                ", expirationDate=" + expirationDate +
                '}';
    }
}
