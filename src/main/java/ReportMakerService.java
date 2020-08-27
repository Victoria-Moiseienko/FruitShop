import java.util.ArrayList;
import java.util.List;

public class ReportMakerService {

    public String makeReport(Storage storage) {
        List<Product> productList = new ArrayList<>();
        for (int i = 0; i < storage.getFruitSupplies().size(); i++) {
            Product product = storage.getFruitSupplies().get(i);
            productList.add(product);
            for (int j = i + 1; j < storage.getFruitSupplies().size(); j++) {
                Product currentProduct = storage.getFruitSupplies().get(j);
                if (product.getProductName().equals(currentProduct.getProductName())) {
                    product.setCount(product.getCount() + currentProduct.getCount());
                    storage.getFruitSupplies().remove(j);
                }
            }
        }

        StringBuilder result = new StringBuilder("fruit,quantity\r");
        for (Product product : productList) {
            result.append(product.getProductName())
                    .append(",")
                    .append(product.getCount())
                    .append("\r");
        }
        return result.toString();
    }
}
