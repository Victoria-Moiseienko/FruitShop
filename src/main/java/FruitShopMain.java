import java.util.List;

public class FruitShopMain {
    public static void main(String[] args) {
        FileReaderService fileReaderService = new FileReaderService();
        List<String> stringList = fileReaderService.readLinesFromFile("inputRecords.txt");

        RecordParserService recordParserService = new RecordParserService();
        List<Record> recordList = recordParserService.parseRecords(stringList);
        for (Record record : recordList) {
            System.out.println(record);
        }

        ProductCalculatorService productCalculatorService = new ProductCalculatorService();
        List<Product> balanceOfProducts = productCalculatorService.calculateBalance(recordList);
        System.out.println("==================================================");
        for (Product product : balanceOfProducts) {
            System.out.println(product);
        }

        ReportMakerService reportMakerService = new ReportMakerService();
        String report = reportMakerService.makeReport(balanceOfProducts);

        FileWriterService fileWriterService = new FileWriterService();
        fileWriterService.writeLinesToFile("report", report);

    }
}
