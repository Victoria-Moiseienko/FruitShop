import java.util.List;

public class FruitShopMain {
    public static void main(String[] args) {
        FileReaderService fileReaderService = new FileReaderService();
        List<String> stringList = fileReaderService.readLinesFromFile("inputRecords.txt");

        RecordParserService recordParserService = new RecordParserService();
        List<Record> recordList = recordParserService.parseRecords(stringList);

        Storage storage = new Storage();
        ProductCalculatorService productCalculatorService = new ProductCalculatorService();
        productCalculatorService.calculateBalance(recordList, storage);

        ReportMakerService reportMakerService = new ReportMakerService();
        String report = reportMakerService.makeReport(storage);

        FileWriterService fileWriterService = new FileWriterService();
        fileWriterService.writeLinesToFile("report.txt", report);
    }
}
