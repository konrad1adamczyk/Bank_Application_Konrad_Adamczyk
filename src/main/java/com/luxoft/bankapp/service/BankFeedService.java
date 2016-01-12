package com.luxoft.bankapp.service;

import com.luxoft.bankapp.model.Bank;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Konrad on 2016-01-03.
 */
public class BankFeedService {
    private Bank activeBank;

    public void setBank(Bank bank) {
        activeBank = bank;
    }

    public void loadFeed(String folderStr) {
        File folder = new File(folderStr);

        for (File fileEntry: folder.listFiles()) {
            if (fileEntry.isFile()) {
                readFile(fileEntry);
            }
        }
    }

    private void readFile(File file) {

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line = null;
            while ((line = reader.readLine()) != null) {
                Map<String, String> feedMap = new HashMap<String, String>();
                String[] record = line.split(";");
                for (String data: record) {
                    String[] property = data.split("=");
                    feedMap.put(property[0], property[1]);
                }
                activeBank.parseFeed(feedMap);
            }
        } catch (FileNotFoundException fnfe) {
            System.out.println("File: " + file.getName() + " doesn't exists!");
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }


}
