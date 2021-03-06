import java.util.ArrayList;
import java.util.List;

public class RecordParserService {
    public static final String DATE_REGEXP = "\\d{4}-\\d{2}-\\d{2}";

    public List<Record> parseRecords(List<String> stringList) {
        List<Record> recordList = new ArrayList<>();
        for (String string : stringList) {
            string = string.trim();
            if(string.trim().matches("[sbr],[a-z]{3,},\\d+,\\d{4}-\\d{2}-\\d{2}")) {
                recordList.add(parseSingleRecord(string.trim()));
            }
            else if(!string.equals("type,fruit,quantity,date"))
                throw new RuntimeException("Unsupported line [" + string + "]");
        }
        return recordList;
    }

    private Record parseSingleRecord(String string) {
        String[] strings = string.split(",");
        return new Record(strings[0], strings[1], strings[2], strings[3]);
    }
}
