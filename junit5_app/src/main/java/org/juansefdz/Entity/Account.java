package org.juansefdz.Entity;

import java.math.BigDecimal;

public class Account {
    private String user;
    private BigDecimal balance;

    public Account(String user, BigDecimal balance) {
        this.balance = balance;
        this.user = user;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }


    @Override
    public boolean equals(Object obj) {//vamos a comparar por atributos y no por objeto
        Account c= (Account) obj;
        if(obj == null ){
            return false;
        }

        if(this.user == null || this.balance == null){
            return false;
        }
        return this.user.equals(c.getUser())&& this.balance.equals(c.getBalance());
    }
}
