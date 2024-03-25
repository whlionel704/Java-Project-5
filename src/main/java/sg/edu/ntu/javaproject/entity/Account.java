package sg.edu.ntu.javaproject.entity;

import java.util.Date;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "account")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "account_id")
    private Integer accountId;

    @Column(name = "account_no")
    private Integer accountNumber;

    @Column(name = "balance")
    private Integer balance;

    @Column(name = "account_type_id")
    private Integer accountTypeId;

    @Column(name = "created_date")
    @CreationTimestamp
    private Date createdDate;

    @Column(name = "updated_date")
    @UpdateTimestamp
    private Date updatedDate;

    @Column(name = "customer_id")
    private Integer customerId;

    // @ManyToOne
    // @JoinColumn(name = "account_type_id", referencedColumnName =
    // "account_type_id")
    // @JsonBackReference
    // private AccountType accountType;

    // @Transient
    // private Integer accountTypeId;

    @Transient
    private String accountTypeName;

}
