package com.example.bank2.dto;

public class ZayavkaCreateRequest {
    private String loginUser;
    private String problem;
    private String problemText;

    public ZayavkaCreateRequest() {
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
}

