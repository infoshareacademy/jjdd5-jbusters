package com.infoshareacademy.jbusters.data;

import javax.enterprise.context.ApplicationScoped;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

@ApplicationScoped
public class SearchOfData {


    public List<String> showCity() {

        Data data = new Data();
        List<Transaction> transactionList = data.fileToData();
        return data.cityList(transactionList);
    }

    public List<String> showDistrict(String city) {
        Data data = new Data();

        List<Transaction> transactionsBase = data.fileToData();
        List<String> districts = new ArrayList<>();
        for (int i = 0; i < transactionsBase.size(); i++) {
            if (city.equals(transactionsBase.get(i).getCity())) {
                districts.add(transactionsBase.get(i).getDistrict().trim());
            }
        }
        Set<String> noDuplicates = new TreeSet<>(districts);
        districts = new ArrayList<>(noDuplicates);

        return districts;
    }

}
