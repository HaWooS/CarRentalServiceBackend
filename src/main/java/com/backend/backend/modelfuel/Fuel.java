package com.backend.backend.modelfuel;
import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name="Fuel")
public class Fuel implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private Double price;

    private LocalDateTime last_modified;

    public Fuel(){}

    public Fuel(Double price){
        this.price=price;
    }

    public Fuel(int id,Double price) {
        this.id=id;
        this.price = price;
    }

    public Fuel(Double price, LocalDateTime last_modified){
        this.price=price;
        this.last_modified=last_modified;
    }

    public Fuel(int i,Double price, LocalDateTime last_modified){
        this.id=i;
        this.price=price;
        this.last_modified=last_modified;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public LocalDateTime getLast_modified() {
        return last_modified;
    }

    public void setLast_modified(LocalDateTime last_modified) {
        this.last_modified = last_modified;
    }
}

