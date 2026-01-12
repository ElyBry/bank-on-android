package com.example.bank2.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "vklads")
public class Vklad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private BigDecimal sum = BigDecimal.ZERO;

    @Column(nullable = false)
    private Integer percent;

    @Column(nullable = false)
    private BigDecimal getmon = BigDecimal.ZERO;

    @Column(name = "number_vklad", unique = true, nullable = false)
    private String numberVklad;

    @Column(name = "create_date", nullable = false)
    private LocalDate createDate;

    @Column(name = "end_date", nullable = false)
    private LocalDate endDate;

    @Column(name = "user_login", nullable = false)
    private String userLogin;

    @ManyToOne
    @JoinColumn(name = "user_login", referencedColumnName = "login", insertable = false, updatable = false)
    private User user;

    // Constructors
    public Vklad() {
    }

    // Getters and Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public BigDecimal getSum() {
        return sum;
    }

    public void setSum(BigDecimal sum) {
        this.sum = sum;
    }

    public Integer getPercent() {
        return percent;
    }

    public void setPercent(Integer percent) {
        this.percent = percent;
    }

    public BigDecimal getGetmon() {
        return getmon;
    }

    public void setGetmon(BigDecimal getmon) {
        this.getmon = getmon;
    }

    public String getNumberVklad() {
        return numberVklad;
    }

    public void setNumberVklad(String numberVklad) {
        this.numberVklad = numberVklad;
    }

    public LocalDate getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDate createDate) {
        this.createDate = createDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public String getUserLogin() {
        return userLogin;
    }

    public void setUserLogin(String userLogin) {
        this.userLogin = userLogin;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}

