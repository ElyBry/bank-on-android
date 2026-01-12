package com.example.bank2.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "admin_banking")
public class Zayavka {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "login_user", nullable = false)
    private String loginUser;

    @Column(nullable = false)
    private String problem;

    @Column(name = "problem_text", nullable = false, columnDefinition = "TEXT")
    private String problemText;

    @Column(name = "send_date", nullable = false)
    private String sendDate;

    @Column(name = "is_successful", nullable = false)
    private Boolean isSuccessful = false;

    @ManyToOne
    @JoinColumn(name = "login_user", referencedColumnName = "login", insertable = false, updatable = false)
    private User user;

    // Constructors
    public Zayavka() {
    }

    // Getters and Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLoginUser() {
        return loginUser;
    }

    public void setLoginUser(String loginUser) {
        this.loginUser = loginUser;
    }

    public String getProblem() {
        return problem;
    }

    public void setProblem(String problem) {
        this.problem = problem;
    }

    public String getProblemText() {
        return problemText;
    }

    public void setProblemText(String problemText) {
        this.problemText = problemText;
    }

    public String getSendDate() {
        return sendDate;
    }

    public void setSendDate(String sendDate) {
        this.sendDate = sendDate;
    }

    public Boolean getIsSuccessful() {
        return isSuccessful;
    }

    public void setIsSuccessful(Boolean isSuccessful) {
        this.isSuccessful = isSuccessful;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}

