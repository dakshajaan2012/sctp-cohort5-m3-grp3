package sg.edu.ntu.m3p3.utils;

import java.util.List;

import lombok.Data;

@Data
public class FilterCriteria {
    private List<String> fieldNames;
    private ComparisonOperator comparisonOperator;
    private Object value;

    public FilterCriteria(List<String> fieldName, ComparisonOperator comparisonOperator, Object value) {
        this.fieldNames = fieldName;
        this.comparisonOperator = comparisonOperator;
        this.value = value;
    }
}
