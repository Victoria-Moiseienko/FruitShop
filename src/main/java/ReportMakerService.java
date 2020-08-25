import java.util.ArrayList;
import java.util.List;

public class ReportMakerService {

    public String makeReport(List<Product> balanceOfProducts) {
        List<Product> productList = new ArrayList<>();

        for(int i=0; i<balanceOfProducts.size(); i++) {
            productList.add(balanceOfProducts.get(i));
            for (int j = i+1; j < balanceOfProducts.size(); j++) {
               if(balanceOfProducts.get(i).getProductName().equals(balanceOfProducts.get(j).getProductName())) {
                   balanceOfProducts.get(i).setCount(balanceOfProducts.get(i).getCount()
                           + balanceOfProducts.get(j).getCount());
                   balanceOfProducts.remove(j);
               }
            }
        }

        StringBuilder result = new StringBuilder("fruit,quantity\r");
        for (Product product : productList) {
            result.append(product.getProductName() + "," + product.getCount() + "\r");
        }

        return result.toString();
    }
}
