package ro.fasttrackit.homework.curs20.frontend.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Transaction {

    @Id
    @GeneratedValue
    private Long id;

    private String product;
    private String type;
    private String amount;

    public Transaction() {
    }

    public Transaction(String product, String type, String amount) {
        this.product = product;
        this.type = type;
        this.amount = amount;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return String.format("Transaction[id=%d, product='%s', type='%s',amount='%s']", id, product, type, amount);
    }
}
