package net.diegolemos.bankapp.transaction;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Predicate;

public class Transactions {

    public List<Transaction> getTransactions() {
        return transactions;
    }

    @JsonProperty
    private List<Transaction> transactions = new LinkedList<>();

    public void add(Transaction transaction) {
        transactions.add(transaction);
    }

    public double balance() {
        Double deposit = transactions.stream().filter(transaction -> transaction.type().equals(Transaction.Type.DEPOSIT)).mapToDouble(Transaction::amount).sum();
        Double withdraw = transactions.stream().filter(transaction -> transaction.type().equals(Transaction.Type.WITHDRAW)).mapToDouble(Transaction::amount).sum();
        return deposit - withdraw;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Transactions that = (Transactions) o;

        return !(transactions != null ? !transactions.equals(that.transactions) : that.transactions != null);
    }

    @Override
    public int hashCode() {
        return transactions != null ? transactions.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "Transactions{" +
                "transactions=" + transactions +
                '}';
    }
}
