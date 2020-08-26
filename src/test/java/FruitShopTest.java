import org.junit.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

public class FruitShopTest {
    public static final DateTimeFormatter YYYY_MM_DD = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    Record testRecordIncome = new Record("s", "pineapple", "10", "2020-08-26");
    Record testRecordIncome2 = new Record("r", "pineapple", "100", "2020-08-26");
    Record testRecordIncome3 = new Record("r", "banana", "30", "2020-10-26");
    Record testRecordExpend = new Record("b", "pineapple", "5", "2020-08-25");

    Storage testStorage;

    String correctLine = "s,apple,200,2020-10-30";
    String correctLine2 = "s,apple,2,2020-10-30";
    String incorrectLine = "Hello world!";
    String incorrectLine2 = "n,apple,200,2020-10-30";
    String incorrectLine3 = "s,apple,200,2020-20-30";

    Product product1 = new Product("banana", 10, LocalDate.parse("2020-10-11", YYYY_MM_DD));
    Product product2 = new Product("pear", 20, LocalDate.parse("2020-09-10", YYYY_MM_DD));
   // Product product3 = new Product("pear", 30, LocalDate.parse("2020-09-20", YYYY_MM_DD));

    @Before
    public void init() {
        testStorage = new Storage();
    }

   @Test
    public void FileReaderServiceTestOk() {
        FileReaderService fileReaderService = new FileReaderService();
        List<String> result = fileReaderService.readLinesFromFile("test1.txt");

        Assert.assertEquals(2, result.size());
        Assert.assertEquals("hello world!", result.get(0).trim());
        Assert.assertEquals("I'm test file", result.get(1).trim());
    }

    @Test(expected = RuntimeException.class)
    public void FileReaderServiceTestAbsendFile() {
        FileReaderService fileReaderService = new FileReaderService();
        fileReaderService.readLinesFromFile("absent_file.txt");
    }

    @Test
    public void FileWriterServiceTestOk() {
        FileWriterService fileWriterService = new FileWriterService();
        fileWriterService.writeLinesToFile("file_writer_test_file.txt", "Text for FileWriterService test");

        FileReaderService fileReaderService = new FileReaderService();
        List<String> result = fileReaderService.readLinesFromFile("file_writer_test_file.txt");

        Assert.assertEquals("Text for FileWriterService test", result.get(0).trim());
    }

    @Test(expected = RuntimeException.class)
    public void FileWriterServiceTestNullValue() {
        FileWriterService fileWriterService = new FileWriterService();
        fileWriterService.writeLinesToFile("file_writer_test_file.txt", null);

        FileReaderService fileReaderService = new FileReaderService();
        fileReaderService.readLinesFromFile("file_writer_test_file.txt");
    }


    @Test(expected = RuntimeException.class)
    public void FileWriterServiceTestNullFileName() {
        FileWriterService fileWriterService = new FileWriterService();
        fileWriterService.writeLinesToFile(null, "Text for FileWriterService test");

        FileReaderService fileReaderService = new FileReaderService();
        fileReaderService.readLinesFromFile("file_writer_test_file.txt");
    }

    @Test
    public void IncomeShopOperationTestOk() {
        LocalDate date = LocalDate.parse("2020-08-26", YYYY_MM_DD);

        IncomeShopOperation incomeShopOperation = new IncomeShopOperation();
        incomeShopOperation.transaction(testRecordIncome, testStorage);

        Assert.assertEquals("pineapple", testStorage.getFruitSupplies().get(0).getProductName());
        Assert.assertEquals(10, testStorage.getFruitSupplies().get(0).getCount());
        Assert.assertEquals(date, testStorage.getFruitSupplies().get(0).getExpirationDate());

        incomeShopOperation.transaction(testRecordIncome2, testStorage);
        Assert.assertEquals(110, testStorage.getFruitSupplies().get(0).getCount());
    }

    @Test
    public void ExpendShopOperationTestOk() {
        IncomeShopOperation incomeShopOperation = new IncomeShopOperation();
        incomeShopOperation.transaction(testRecordIncome2, testStorage);

        ExpendShopOperation expendShopOperation = new ExpendShopOperation();
        expendShopOperation.transaction(testRecordExpend, testStorage);

        Assert.assertEquals(95, testStorage.getFruitSupplies().get(0).getCount());
        testStorage = new Storage();
    }

    @Test
    public void RecordParserServiceTestOk(){
        LocalDate date = LocalDate.parse("2020-10-30", YYYY_MM_DD);

        List<String> stringList = new ArrayList<>();
        stringList.add(correctLine);
        stringList.add(correctLine2);

        RecordParserService recordParserService = new RecordParserService();
        List<Record> records = recordParserService.parseRecords(stringList);

        Assert.assertEquals(2, records.size());
        Assert.assertEquals(200, records.get(0).getCount());
        Assert.assertEquals("apple", records.get(0).getProductName());
        Assert.assertEquals(date, records.get(0).getDate());
    }

    @Test(expected = RuntimeException.class)
    public void RecordParserServiceTestNoMatchesInputLine(){
        List<String> stringList = new ArrayList<>();
        stringList.add(incorrectLine);
        RecordParserService recordParserService = new RecordParserService();
        recordParserService.parseRecords(stringList);
    }

    @Test(expected = RuntimeException.class)
    public void RecordParserServiceTestIncorrectType(){
        List<String> stringList = new ArrayList<>();
        stringList.add(incorrectLine2);
        RecordParserService recordParserService = new RecordParserService();
        recordParserService.parseRecords(stringList);
    }

    @Test
    public void RecordParserServiceTestIncorrectDate(){
        List<String> stringList = new ArrayList<>();
        stringList.add(incorrectLine3);
        RecordParserService recordParserService = new RecordParserService();
        try {
            recordParserService.parseRecords(stringList);
        } catch (DateTimeParseException e) {
            Assert.assertEquals("Text '2020-20-30' could not be parsed: Invalid value for MonthOfYear (valid values 1 - 12): 20",
                    e.getMessage());
        }
    }

    @Test
    public void ReportMakerServiceTestOk() {
        testStorage.getFruitSupplies().add(product1);
        testStorage.getFruitSupplies().add(product2);

        ReportMakerService reportMakerService = new ReportMakerService();
        String result = reportMakerService.makeReport(testStorage);

        Assert.assertEquals("fruit,quantity\rbanana,10\rpear,20\r", result);
    }

    @Test
    public void ReportMakerServiceTestEmptyStorage() {
        ReportMakerService reportMakerService = new ReportMakerService();
        String result = reportMakerService.makeReport(testStorage);

        Assert.assertEquals("fruit,quantity\r", result);
    }

    @Test
    public void ProductCalculatorServiceTestOk(){
        List<Record> recordList = new ArrayList<>();
        recordList.add(testRecordIncome);
        recordList.add(testRecordIncome2);
        recordList.add(testRecordIncome3);
        recordList.add(testRecordExpend);

        ProductCalculatorService productCalculatorService = new ProductCalculatorService();
        productCalculatorService.calculateBalance(recordList, testStorage);

        Assert.assertEquals(2, testStorage.getFruitSupplies().size());
        Assert.assertEquals(105, testStorage.getFruitSupplies().get(0).getCount());
    }
}
