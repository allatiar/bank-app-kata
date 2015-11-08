package net.diegolemos.bankapp.account;

import net.diegolemos.bankapp.client.Client;
import org.junit.Before;
import org.junit.Test;

import static net.diegolemos.bankapp.client.ClientBuilder.aClient;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class AccountTest {

    private static final Client BOB = aClient().withUsername("bob").build();

    private Account account;

    @Before
    public void initialize() {
        account = new Account(BOB);
    }

    @Test
    public void
    should_calculate_balance_for_transactions() {
        account.deposit(10.0);
        account.deposit(20.0);

        assertThat(account.balance(), is(30.0));
    }

    @Test
    public void
    should_deposit_money_into_account() {
        account.deposit(10.0);

        assertThat(account.balance(), is(10.0));
    }

    @Test
    public void should_withdraw_money_from_account() {
        account.deposit(10.0);
        account.withdraw(10.0);
        assertThat(account.balance(), is(0.0));
    }

    @Test
    public void should_withdraw_money_from_account_overdraft() {
        account.deposit(10.0);
        account.withdraw(30.0);
        assertThat(account.balance(), is(0.0));
    }

//    @Test
//    public void should_play_transactions_from_empty_account() {
//        account.deposit(10.0);
//        account.play(null);
//
//        assertThat(account.balance(), is(0.0));
//    }

    @Test
    public void should_not_update_account_if_amount_equals_0() {
        account.deposit(10.0);

        account.deposit(0);
        assertThat(account.balance(), is(10.0));

        account.withdraw(0);
        assertThat(account.balance(), is(10.0));
    }

    @Test
    public void should_not_update_account_if_amount_is_negative() {
        account.deposit(10.0);

        account.deposit(-20);
        assertThat(account.balance(), is(10.0));

        account.withdraw(-50);
        assertThat(account.balance(), is(10.0));
    }
}