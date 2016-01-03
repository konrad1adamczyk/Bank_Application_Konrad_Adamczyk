package com.luxoft.bankapp.service;

import com.luxoft.bankapp.commands.*;
import com.luxoft.bankapp.ecxeptions.ClientExistsException;
import com.luxoft.bankapp.model.Bank;
import com.luxoft.bankapp.model.Client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by KAdamczyk on 2015-12-18.
 */
public class BankCommander {

    public static Bank currentBank;

    public static Client currentClient;
    private static Map<String, Command> commandMap = new TreeMap<String, Command>();


    static Command[] commands = {            new AddClientCommand(), // 0    ***************

            new FindClientCommand(), // 1   ***************
            new OpenAccountCommand(), // 2  ***************
//            new CloseAccountCommand(), // 3
            new GetAccountsCommand(), // 4  ***************
            new SetActiveAccountCommand(), // 5 ***************

            new DepositCommand(), // 6 ***************
            new WithdrawCommand(), // 7 ***************
            new TransferCommand(), // 8

            new Command() { // 9 - Exit Command
                @Override
                public void execute() {
                    System.out.println("Closing the program!");
                    System.exit(0);
                }
                @Override
                public void printCommandInfo() {
                    System.out.println("Exit");
                }
            }
    };

    static {
        int i = 0;
        for(Command cmd : commands) {
            commandMap.put(String.valueOf(i), cmd);
            i++;
        }
    }

    public static void registerCommand(String name, Command command) {
        commandMap.put(name, command);
    }

    public static void removeCommand(String name) {
        commandMap.remove(name);
    }

    private static void showMenu() {
        System.out.print("\n BANK MENU: Active client: ");
        if (currentClient != null) {
            System.out.print(currentClient.getName());
        } else {
            System.out.print("none");
        }
        System.out.println();

        for (int i = 0; i < commands.length; i++) {
            System.out.print(i + ") ");
            String k = ""+ i;
            commandMap.get(k).printCommandInfo();
            System.out.println();
        }
        System.out.println("Choose a number: ");
    }

    public static void main(String args[]) throws ClientExistsException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        BankApplication bankApp = new BankApplication();

        currentBank = new Bank("MyBank");
        bankApp.initialize(currentBank);

        currentBank.printReport();

        while (true) {
            showMenu();
            try {
                String commandString = reader.readLine();
                commandMap.get(commandString.trim()).execute();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ArrayIndexOutOfBoundsException e) {
                System.out.println("Invalid number selected!");
            }
        }
    }
}
