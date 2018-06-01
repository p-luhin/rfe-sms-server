package by.bsu.rfe.smsservice.bulk;

/**
 * Created by pluhin on 7/13/16.
 */
public enum ExcelColumnName {
    MOBILE_NUMBER("Mobile Number"),
    MESSAGE("Message");

    private String columnKey;

    ExcelColumnName(String columnKey) {
        this.columnKey = columnKey;
    }

    public String getColumnKey() {
        return columnKey;
    }
}
