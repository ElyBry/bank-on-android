package com.example.bank2.models;

public class Zayavki {
    private String login_user;
    private String problem;
    private String send_date;
    private String problem_text;
    private String is_successful;

    public String getLogin_user() {
        return login_user;
    }

    public void setLogin(String login_user) {
        this.login_user = login_user;
    }

    public String getProblem() {
        return problem;
    }

    public void setProblem(String problem) {
        this.problem = problem;
    }

    public String getSend_date() {
        return send_date;
    }

    public void setDate(String send_date) {
        this.send_date = send_date;
    }

    public String getProblem_text() {
        return problem_text;
    }

    public void setTextProblem(String problem_text) {
        this.problem_text = problem_text;
    }

    public String getIs_successful() {
        return is_successful;
    }

    public void setIs_successful(String is_successful) {
        this.is_successful = is_successful;
    }
}
