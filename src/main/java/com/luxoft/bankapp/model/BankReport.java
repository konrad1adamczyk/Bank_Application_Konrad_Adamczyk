package com.luxoft.bankapp.model;

import java.util.*;

/**
 * Created by Konrad on 2015-12-20.
 */
public class BankReport {
    public void getNumberOfClients (Bank bank){
        System.out.println("Bank " + bank.getBankName() + " has " + bank.getListOfClients().size() + " happy clients.");
    }
    public void getAccountsNumber (Bank bank){
        int accountsNumber=0;

        for(Client client:bank.getListOfClients())
            accountsNumber+=client.getListOfAccounts().size();
        System.out.println("Bank has " + accountsNumber + " accounts.");
    }
    public void getClientsSorted (Bank bank){
        for(Client client: bank.getListOfClients())
            System.out.println(client.getName());
    }
    public void getBankCreditSum (Bank bank){
        float credit=0;
        for(Client client:bank.getListOfClients())
            for(Account account: client.getListOfAccounts())
                if(account.getBalance()>0)
                    credit+=account.getBalance();
        System.out.println("\nBank credit sum: " + credit);
    }

//    ************************************************************************************************************************************************************************
    public void getClientsByCity(Bank bank) {
        Map<String, List<Client>> clientsCityMap = new HashMap<>();


        for (Client client : bank.getListOfClients()) {
            String cityOfClient = client.getCity();
            List<Client> newList = clientsCityMap.get(cityOfClient);
            if( newList == null ){
                newList = new ArrayList<>();
                clientsCityMap.put(cityOfClient , newList);
            }
            newList.add(client);
        }


        StringBuilder stringBuilder = new StringBuilder();
        for (Map.Entry<String, List<Client>> entry : clientsCityMap.entrySet()) {
            stringBuilder.append(entry.getKey()).append(": ");
            for (Client client : entry.getValue()) {
                stringBuilder.append(client.getName()).append(", ");
            }
            stringBuilder.append("\n");
        }
        System.out.println(stringBuilder);

    }







}
