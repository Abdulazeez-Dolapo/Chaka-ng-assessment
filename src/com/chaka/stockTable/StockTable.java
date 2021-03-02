package com.chaka.stockTable;

//import java.util.HashMap;
import java.time.LocalDateTime;
import java.util.*;
import java.lang.*;
//import java.util.UUID;

public class StockTable {
    public HashMap<Integer, TableData> stockTable;

    private String generateUID() {
        UUID uniqueKey = UUID.randomUUID();
        return uniqueKey.toString();
    }

    private void stored (String merchantId, String userId ) {
        if (merchantId.isEmpty() || userId.isEmpty()) return;
        if(this.stockTable.containsKey(userId)) return;

        Integer index = this.stockTable.size() + 1;
        TableData table = new TableData(merchantId, userId);

        this.stockTable.put(index, new TableData(merchantId, userId));
    }

    public HashMap<Integer, TableData> viewStockTable() {
        return this.stockTable;
    }

    public StockTable () {
        this.stockTable = new HashMap<>();
    }

    private static HashMap<Integer, TableData> sortByDate(HashMap<Integer, TableData> dataToSort, String orderBy) {
        // Create a list from elements of HashMap
        List<Map.Entry<Integer, TableData> > list =
                new LinkedList<Map.Entry<Integer, TableData> >(dataToSort.entrySet());

        // Sort the list
        Collections.sort(list, new Comparator<Map.Entry<Integer, TableData> >() {
            public int compare(Map.Entry<Integer, TableData> first, Map.Entry<Integer, TableData> second) {
                if (orderBy.equals("asc")) {
                    return (first.getValue().getCreatedAt()).compareTo(second.getValue().getCreatedAt());
                } else {
                    return (second.getValue().getCreatedAt()).compareTo(first.getValue().getCreatedAt());
                }
            }
        });

        // put data from sorted list to hashmap
        HashMap<Integer, TableData> temp = new LinkedHashMap<Integer, TableData>();
        for (Map.Entry<Integer, TableData> item : list) {
            temp.put(item.getKey(), item.getValue());
        }

        return temp;
    }

    public HashMap<Integer, TableData> filterTable(String filterBy, String filterValue) {
        // Validate filterBy and filterValue values (check if empty)
        // Validate filterBy to be either "userId" or "merchantId"
        // Validate filterValue to be either "asc" or "desc"

        // initialize a new HashMap
        HashMap<Integer, TableData> newMap = new HashMap<>();

        // iterate through stockTable to get values that correspond with filterValue
        for (Map.Entry<Integer, TableData> entry : this.stockTable.entrySet()) {
            if(filterBy.equals("userId")) {
                if(entry.getValue().getUserId().equals(filterValue)) {
                    newMap.put(entry.getKey(), entry.getValue());
                }
            }

            if(filterBy.equals("merchantId")) {
                System.out.println(entry.getValue().getMerchantId());
                if(entry.getValue().getMerchantId().equals(filterValue)) {
                    newMap.put(entry.getKey(), entry.getValue());
                }
            }
        }

        // sort the new hashmap by date
        HashMap<Integer, TableData> sortedData = this.sortByDate(newMap, "desc");

        return sortedData;
    }

    public static void main(String[] args) {
        StockTable stockTable = new StockTable();
        stockTable.stored("merchantId", "userId3");
        stockTable.stored("merchantId2", "userId2");
        stockTable.stored("merchantId", "userId3");
        System.out.println(stockTable.filterTable("userId", "userId3"));
    }
}

