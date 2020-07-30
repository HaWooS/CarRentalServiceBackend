package com.backend.backend.modeldebtorchecker;


import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name="debtorchecker")
public class DebtorChecker implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private LocalDateTime last_check;

    public DebtorChecker(int id,LocalDateTime last_check) {
        this.id=id;
        this.last_check = last_check;
    }

    public DebtorChecker(){};

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDateTime getLast_check() {
        return last_check;
    }

    public void setLast_check(LocalDateTime last_check) {
        this.last_check = last_check;
    }
}
