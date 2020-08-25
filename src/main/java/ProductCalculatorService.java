import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class ProductCalculatorService {

    public List<Product> calculateBalance(List<Record> recordList) {
        List<Product> productList = new ArrayList<>();
        for (Record record : recordList) {
            RecordType recordType = record.getType();
            switch (recordType) {
                case s: {
                    addProduct(record, productList);
                    break;
                }
                case r: {
                    addProduct(record, productList);
                    break;
                }
                case b: {
                    buyProduct(record, productList);
                    break;
                }
                default:
                    System.out.println(record.getProduct());
            }
        }
        return productList;
    }

    private void buyProduct(Record record, List<Product> productList) {
        ArrayList<Product> remains = productList.stream()
                .filter(product -> product.getProductName().equals(record.getProduct()))
                .collect(Collectors.toCollection(ArrayList::new));

        for (Product product : remains) {
            if (product.getCount() >= record.getCount()
                && isFresh(record.getDate(), product.getExpirationDate())) {
                product.setCount(product.getCount() - record.getCount());
                return;
            }
        }
        throw new RuntimeException("We don't have enough fresh [" + record.getProduct() + "] for sale ["
                + record.getDate() + " need count " + record.getCount() + "]");
    }

    public boolean isFresh(LocalDate recordDate, LocalDate productDate) {
        return recordDate.compareTo(productDate) < 0;
    }

    private void addProduct(Record currentRecord, List<Product> productList) {
        for (Product product : productList) {
            if (currentRecord.getProduct().equals(product.getProductName())
                && currentRecord.getDate().equals(product.getExpirationDate())) {
                product.setCount(product.getCount() + currentRecord.getCount());
                return;
            }
        }

        Product product = new Product(currentRecord.getProduct(), currentRecord.getCount(),
                currentRecord.getDate());
        productList.add(product);
    }
}
