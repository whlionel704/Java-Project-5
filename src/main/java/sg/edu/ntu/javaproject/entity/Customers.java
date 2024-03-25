package sg.edu.ntu.javaproject.entity;

import org.springframework.stereotype.Component;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Table(name="customer")
@Entity
public class Customers {
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column
    private Integer customer_id;

    @Column
    private Integer account_id;

    @Column
    private String customer_name;

    @Column
    private String customer_email;

    @Column
    private String customer_contact;

    @Column
    private String customer_address;


}
