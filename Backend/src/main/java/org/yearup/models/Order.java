package org.yearup.models;

import jakarta.persistence.*;

@Entity
@Table(name = "orders")
public class Order {
    ;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }



    @Id
    private Long id;
    @Column(name = "order_id")
    private  int orderId;
    @Column(name = "user_id")
    private  int userId;
}
