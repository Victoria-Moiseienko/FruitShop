public class IncomeShopOperation implements ShopOperation {
    @Override
    public void transaction(Record record, Storage storage) {
        for (Product product : storage.getFruitSupplies()) {
            if (record.getProductName().equals(product.getProductName())
                    && record.getDate().equals(product.getExpirationDate())) {
                product.setCount(product.getCount() + record.getCount());
                return;
            }
        }

        Product product = new Product(record.getProductName(), record.getCount(), record.getDate());
        storage.getFruitSupplies().add(product);
    }
}
