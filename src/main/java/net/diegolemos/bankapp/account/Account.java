package net.diegolemos.bankapp.account;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import net.diegolemos.bankapp.client.Client;
import net.diegolemos.bankapp.transaction.Transaction;
import net.diegolemos.bankapp.transaction.Transactions;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Account {

    @JsonProperty
    private Client client;

    @JsonProperty
    private Transactions transactions = new Transactions();

    // Required by Jackson
    @SuppressWarnings("unused")
    private Account() {
    }

    public Account(Client client) {
        this.client = client;
    }

    public Client holder() {
        return client;
    }

    @JsonProperty("balance")
    public double balance() {
        return transactions.balance();
    }

    public void deposit(double amount) {
        if (amount > 0) {
            transactions.add(Transaction.deposit(amount));
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Account account = (Account) o;

        return !(client != null ? !client.equals(account.client) : account.client != null);
    }

    @Override
    public int hashCode() {
        return client != null ? client.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "Account{" +
                "client=" + client +
                ", balance=" + balance() +
                ", transactions=" + transactions +
                '}';
    }

    public void withdraw(double amount) {
        if (amount > 0) {
            transactions.add(Transaction.withdraw(balance() >= amount ? amount : balance()));
        }
    }

    public Account play() {
        Account result = new Account(this.holder());
        for (Transaction transaction : transactions.getTransactions()) {
            if (transaction.type().equals(Transaction.Type.WITHDRAW)) {
                result.withdraw(transaction.amount());
            } else {
                result.deposit(transaction.amount());
            }
        }
        return result;
    }
}
