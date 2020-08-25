import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class Record {
    private RecordType type;
    private String product;
    private int count;
    private LocalDate date;

    public Record(String inputType, String inputProduct, String inputCount, String inputDate) {
        setType(inputType);
        product = inputProduct;
        setCount(Integer.parseInt(inputCount));
        setDate(LocalDate.parse(inputDate, DateTimeFormatter.ofPattern("yyyy-MM-dd")));
    }

    public RecordType getType() {
        return type;
    }

    public void setType(String stringType) {
        switch (stringType) {
            case "s" : type = RecordType.s; break;
            case "b" : type = RecordType.b; break;
            case "r" : type = RecordType.r; break;
            default: {
                throw new RuntimeException("Unsupported record type: [" + stringType + "]");
            }
        }
    }

    public String getProduct() {
        return product;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Record{" +
                "type=" + type +
                ", product='" + product + '\'' +
                ", count=" + count +
                ", date=" + date +
                '}';
    }
}
