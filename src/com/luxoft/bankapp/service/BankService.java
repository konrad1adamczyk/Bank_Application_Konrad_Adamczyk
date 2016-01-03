package com.luxoft.bankapp.service;


import com.luxoft.bankapp.ecxeptions.ClientExistsException;
import com.luxoft.bankapp.ecxeptions.ClientNotExistsException;
import com.luxoft.bankapp.model.Account;
import com.luxoft.bankapp.model.Bank;
import com.luxoft.bankapp.model.Client;

public interface BankService {
    public void addClient2(Bank bank,Client client) throws ClientExistsException;
    public void removeClient(Bank bank,Client client);
//    public void removeClientByIndex(Bank bank,int index);
    public void addAccount(Client client, Account account);
    public void setActiveAccount(Client client, Account account);

    public Client getClient(Bank bank, String clientName) throws ClientNotExistsException;
    public void saveClient(Client client);
    public Client loadClient();
}
