import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductCalculatorService {
    private Map<RecordType, ShopOperation> dealMap = new HashMap<>();

    private void initDealMap() {
        dealMap.put(RecordType.s, new IncomeShopOperation());
        dealMap.put(RecordType.r, new IncomeShopOperation());
        dealMap.put(RecordType.b, new ExpendShopOperation());
    }

    public void calculateBalance(List<Record> recordList, Storage storage) {
        initDealMap();
        for (Record record : recordList) {
            ShopOperation operation = dealMap.get(record.getType());
            operation.transaction(record, storage);
        }
    }
}
