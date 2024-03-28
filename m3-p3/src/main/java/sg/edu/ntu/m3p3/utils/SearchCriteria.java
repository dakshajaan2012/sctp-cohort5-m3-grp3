package sg.edu.ntu.m3p3.utils;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class SearchCriteria {
    private List<FilterCriteria> filterCriteriaList;
    private LogicalOperator logicalOperator;

    public SearchCriteria() {
        this.filterCriteriaList = new ArrayList<>();
    }

    public void addFilterCriteria(FilterCriteria filterCriteria) {
        this.filterCriteriaList.add(filterCriteria);
    }
}