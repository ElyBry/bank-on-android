package com.example.bank2.database;

import android.content.Context;
import android.util.Log;

import com.example.bank2.models.Card;
import com.example.bank2.models.CardHistory;
import com.example.bank2.models.CardsNUMB;
import com.example.bank2.models.Percents;
import com.example.bank2.models.User;
import com.example.bank2.models.Vklad;
import com.example.bank2.models.Zayavki;

import org.mindrot.jbcrypt.BCrypt;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

public class DataBaseHandler extends Config {
    private static final String TAG = "DataBaseHandler";
    private Context context;

    public DataBaseHandler(Context context) {
        this.context = context;
    }

    public Connection getDbConnection() throws ClassNotFoundException, SQLException {
        String DB_URL = "jdbc:mysql://" + dbHost + ":" + dbPort + "/" + dbName + "?useSSL=false&serverTimezone=UTC&connectTimeout=5000&allowPublicKeyRetrieval=true";
        try {
            // Используем старый драйвер для совместимости с Android (версия 5.1.49)
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection(DB_URL, dbUser, dbPass);
            return conn;
        } catch (Exception ex) {
            Log.e(TAG, "Error connecting to database", ex);
            throw new SQLException("Не удалось подключиться к базе данных: " + ex.getMessage(), ex);
        }
    }
    
    private Connection getConnectionSafely() {
        try {
            Connection conn = getDbConnection();
            if (conn == null || conn.isClosed()) {
                Log.e(TAG, "Connection is null or closed");
                return null;
            }
            return conn;
        } catch (Exception e) {
            Log.e(TAG, "Failed to get database connection", e);
            return null;
        }
    }

    public boolean signUp(String loginText, String passText, String fioText, String passportText, String sex, LocalDate birthdate) {
        String checkReg = "SELECT COUNT(*) FROM users WHERE login = ? OR passport = ?";
        String insert = "INSERT INTO users (login,password,fio,passport,date_of_birth,gender,age,number) VALUES(?,?,?,?,?,?,?,?)";
        Connection conn = null;
        PreparedStatement selectStatement = null;
        ResultSet resultSet = null;
        try {
            conn = getConnectionSafely();
            if (conn == null) {
                Log.e(TAG, "Failed to get database connection");
                return false;
            }
            selectStatement = conn.prepareStatement(checkReg);
            selectStatement.setString(1, loginText);
            selectStatement.setString(2, passportText);
            resultSet = selectStatement.executeQuery();
            if (resultSet.next() && resultSet.getInt(1) > 0) {
                return false;
            }
            resultSet.close();
            selectStatement.close();
            
            LocalDate currentDate = LocalDate.now();
            Period period = Period.between(birthdate, currentDate);
            String hashedPassword = BCrypt.hashpw(passText, BCrypt.gensalt());
            PreparedStatement prst = conn.prepareStatement(insert);
            prst.setString(1, loginText);
            prst.setString(2, hashedPassword);
            prst.setString(3, fioText);
            prst.setString(4, passportText);
            prst.setString(5, birthdate.toString());
            prst.setString(6, sex);
            prst.setString(7, String.valueOf(period.getYears()));
            prst.setString(8, "0");
            prst.executeUpdate();
            prst.close();
            return true;
        } catch (SQLException e) {
            Log.e(TAG, "Error in signUp", e);
            return false;
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (selectStatement != null) selectStatement.close();
                if (conn != null && !conn.isClosed()) {
                    conn.close();
                }
            } catch (SQLException e) {
                Log.e(TAG, "Error closing connection", e);
            }
        }
    }

    public String logIn(String loginText, String passText, String passportText) {
        String checkUser = "SELECT * FROM users WHERE login = ? AND passport = ?";
        Connection conn = null;
        PreparedStatement selectStatement = null;
        ResultSet resultSet = null;
        try {
            conn = getConnectionSafely();
            if (conn == null) {
                return "-2"; // Ошибка подключения
            }
            selectStatement = conn.prepareStatement(checkUser);
            selectStatement.setString(1, loginText);
            selectStatement.setString(2, passportText);
            resultSet = selectStatement.executeQuery();
            if (resultSet.next()) {
                if (BCrypt.checkpw(passText, resultSet.getString("password"))) {
                    User.setId(resultSet.getInt("id"));
                    User.setFio(resultSet.getString("fio"));
                    User.setLogin(resultSet.getString("login"));
                    User.setPassport(resultSet.getString("passport"));
                    User.setDateOfBirth(resultSet.getString("date_of_birth"));
                    User.setNumberPhone(resultSet.getString("number"));
                    User.setSex(resultSet.getString("gender"));
                    User.setAdmin(resultSet.getInt("admin"));
                    return resultSet.getString("login");
                } else {
                    return "-1";
                }
            } else {
                return "-1";
            }
        } catch (SQLException e) {
            Log.e(TAG, "Error in logIn", e);
            return "-2"; // Ошибка подключения
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (selectStatement != null) selectStatement.close();
                if (conn != null && !conn.isClosed()) conn.close();
            } catch (SQLException e) {
                Log.e(TAG, "Error closing resources", e);
            }
        }
    }

    public int checkCards(String login) throws SQLException, ClassNotFoundException {
        String checkCard = "SELECT * FROM cards WHERE holder_name = ?";
        Connection conn = null;
        PreparedStatement selectStatement = null;
        ResultSet resultSet = null;
        try {
            conn = getConnectionSafely();
            if (conn == null) {
                throw new SQLException("Не удалось подключиться к базе данных");
            }
            selectStatement = conn.prepareStatement(checkCard);
            selectStatement.setString(1, login);
            resultSet = selectStatement.executeQuery();
            if (resultSet.next()) {
                return 1;
            } else {
                return 0;
            }
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (selectStatement != null) selectStatement.close();
                if (conn != null && !conn.isClosed()) conn.close();
            } catch (SQLException e) {
                Log.e(TAG, "Error closing resources", e);
            }
        }
    }

    public StringBuilder generatorCardNum() {
        Random random = new Random();
        StringBuilder cardNumber = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            cardNumber.append(random.nextInt(10));
        }
        for (int i = 0; i < 10; i++) {
            cardNumber.append(random.nextInt(10));
        }
        return cardNumber;
    }

    public StringBuilder generatorCVV() {
        Random random = new Random();
        StringBuilder cvv = new StringBuilder();
        for (int i = 0; i < 3; i++) {
            cvv.append(random.nextInt(10));
        }
        return cvv;
    }

    public String LoadCards() {
        String card = "SELECT * FROM cards WHERE holder_name = ?";
        Connection conn = null;
        PreparedStatement selectStatement = null;
        ResultSet resultSet = null;
        try {
            conn = getConnectionSafely();
            if (conn == null) {
                return "";
            }
            selectStatement = conn.prepareStatement(card);
            selectStatement.setString(1, User.getLogin());
            resultSet = selectStatement.executeQuery();
            String cardNumbers = "";
            while (resultSet.next()) {
                String cardNumber = resultSet.getString("card_number");
                cardNumbers += cardNumber + " ";
            }
            CardsNUMB.setnumbercards(cardNumbers.trim());
            return cardNumbers;
        } catch (SQLException e) {
            Log.e(TAG, "Error in LoadCards", e);
            return "";
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (selectStatement != null) selectStatement.close();
                if (conn != null && !conn.isClosed()) conn.close();
            } catch (SQLException e) {
                Log.e(TAG, "Error closing resources", e);
            }
        }
    }

    public boolean createCard() {
        String createCard = "INSERT INTO cards (card_number,holder_name,expiry_date,cvv,balance,is_active,number) VALUES(?,?,?,?,?,?,?)";
        String checkNumCard = "SELECT COUNT(*) FROM cards WHERE card_number = ?";
        Connection conn = null;
        try {
            conn = getConnectionSafely();
            if (conn == null) {
                return false;
            }
            StringBuilder finalCardNumber = null;
            PreparedStatement selectStatement = null;
            ResultSet resultSet = null;
            while (true) {
                selectStatement = conn.prepareStatement(checkNumCard);
                finalCardNumber = generatorCardNum();
                selectStatement.setString(1, finalCardNumber.toString());
                resultSet = selectStatement.executeQuery();
                if (resultSet.next() && resultSet.getInt(1) == 0) {
                    resultSet.close();
                    selectStatement.close();
                    break;
                }
                resultSet.close();
                selectStatement.close();
            }
            PreparedStatement prst = conn.prepareStatement(createCard);
            prst.setString(1, finalCardNumber.toString());
            prst.setString(2, User.getLogin());
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.YEAR, 10);
            SimpleDateFormat sdf = new SimpleDateFormat("MM/yyyy");
            prst.setString(3, sdf.format(calendar.getTime()));
            StringBuilder cvv = generatorCVV();
            prst.setString(4, cvv.toString());
            prst.setInt(5, 0);
            prst.setBoolean(6, true);
            prst.setString(7, "0");
            prst.executeUpdate();
            prst.close();
            Card.setNumber(finalCardNumber.toString());
            Card.setHolder_name(User.getLogin());
            Card.setExpiry_date(sdf.format(calendar.getTime()));
            Card.setCvv(cvv.toString());
            Card.setBalance(String.valueOf(0.00));
            Card.setIs_active(true);
            return true;
        } catch (SQLException e) {
            Log.e(TAG, "Error in createCard", e);
            return false;
        } finally {
            try {
                if (conn != null && !conn.isClosed()) conn.close();
            } catch (SQLException e) {
                Log.e(TAG, "Error closing connection", e);
            }
        }
    }

    public String LoadCard(String numCard) {
        String cardz = "SELECT * FROM cards WHERE card_number = ?";
        Connection conn = null;
        PreparedStatement selectStatement = null;
        ResultSet resultSet = null;
        try {
            conn = getConnectionSafely();
            if (conn == null) {
                return "---------------";
            }
            selectStatement = conn.prepareStatement(cardz);
            selectStatement.setString(1, numCard.trim());
            resultSet = selectStatement.executeQuery();
            if (resultSet.next()) {
                Card.setId_card(resultSet.getInt(1));
                Card.setNumber(resultSet.getString(2));
                Card.setCvv(resultSet.getString("cvv"));
                Card.setExpiry_date(resultSet.getString("expiry_date"));
                Card.setBalance(resultSet.getBigDecimal("balance").toString());
                return resultSet.getBigDecimal("balance").toString();
            } else {
                return "---------------";
            }
        } catch (SQLException e) {
            Log.e(TAG, "Error in LoadCard", e);
            return "---------------";
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (selectStatement != null) selectStatement.close();
                if (conn != null && !conn.isClosed()) conn.close();
            } catch (SQLException e) {
                Log.e(TAG, "Error closing resources", e);
            }
        }
    }

    public boolean updateNumberPhone(String numberphone) {
        String number = "update users SET number = ? WHERE login = ?";
        String checkNum = "SELECT count(*) FROM users WHERE number = ?";
        String sbpActive = "update cards SET sbp = ? WHERE holder_name = ?";
        Connection conn = null;
        PreparedStatement selectStatement = null;
        ResultSet resultSet = null;
        try {
            conn = getConnectionSafely();
            if (conn == null) {
                return false;
            }
            selectStatement = conn.prepareStatement(checkNum);
            selectStatement.setString(1, numberphone);
            resultSet = selectStatement.executeQuery();
            if (resultSet.next() && resultSet.getInt(1) != 0) {
                return false;
            }
            resultSet.close();
            selectStatement.close();
            
            selectStatement = conn.prepareStatement(number);
            selectStatement.setString(1, numberphone.trim());
            selectStatement.setString(2, User.getLogin());
            selectStatement.executeUpdate();
            selectStatement.close();

            selectStatement = conn.prepareStatement(sbpActive);
            selectStatement.setBoolean(1, true);
            selectStatement.setString(2, User.getLogin());
            selectStatement.executeUpdate();
            selectStatement.close();
            return true;
        } catch (SQLException e) {
            Log.e(TAG, "Error in updateNumberPhone", e);
            return false;
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (selectStatement != null) selectStatement.close();
                if (conn != null && !conn.isClosed()) conn.close();
            } catch (SQLException e) {
                Log.e(TAG, "Error closing resources", e);
            }
        }
    }

    public boolean disconnectSBP() {
        String sbpActive = "update cards SET sbp = ? WHERE holder_name = ?";
        Connection conn = null;
        PreparedStatement selectStatement = null;
        try {
            conn = getConnectionSafely();
            if (conn == null) {
                return false;
            }
            selectStatement = conn.prepareStatement(sbpActive);
            selectStatement.setBoolean(1, false);
            selectStatement.setString(2, User.getLogin());
            selectStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            Log.e(TAG, "Error in disconnectSBP", e);
            return false;
        } finally {
            try {
                if (selectStatement != null) selectStatement.close();
                if (conn != null && !conn.isClosed()) conn.close();
            } catch (SQLException e) {
                Log.e(TAG, "Error closing resources", e);
            }
        }
    }

    public double getBalance(String card_number) {
        String cardBalance = "SELECT * FROM cards WHERE card_number = ?";
        Connection conn = null;
        PreparedStatement selectStatement = null;
        ResultSet resultCard = null;
        try {
            conn = getConnectionSafely();
            if (conn == null) {
                return 0;
            }
            selectStatement = conn.prepareStatement(cardBalance);
            selectStatement.setString(1, card_number);
            resultCard = selectStatement.executeQuery();
            if (resultCard.next()) {
                return Double.parseDouble(resultCard.getBigDecimal("balance").toString());
            } else {
                return 0;
            }
        } catch (SQLException e) {
            Log.e(TAG, "Error in getBalance", e);
            return 0;
        } finally {
            try {
                if (resultCard != null) resultCard.close();
                if (selectStatement != null) selectStatement.close();
                if (conn != null && !conn.isClosed()) conn.close();
            } catch (SQLException e) {
                Log.e(TAG, "Error closing resources", e);
            }
        }
    }

    public boolean perevod(String kakperevod, String number, String value, String numberotkuda) {
        Double balance = getBalance(numberotkuda.trim());
        Double valuePer = Double.parseDouble(value);
        if (balance <= valuePer) {
            return false;
        } else {
            if (kakperevod.equals("Система быстрых платежей(По номеру)")) {
                String user = "SELECT * FROM users WHERE number = ?";
                Connection conn = null;
                PreparedStatement selectStatement = null;
                ResultSet resultUser = null;
                ResultSet resultCard = null;
                ResultSet resultFindNumCard = null;
                try {
                    conn = getConnectionSafely();
                    if (conn == null) {
                        return false;
                    }
                    selectStatement = conn.prepareStatement(user);
                    selectStatement.setString(1, number);
                    resultUser = selectStatement.executeQuery();
                    if (!resultUser.next()) {
                        return false;
                    }
                    String card = "SELECT * FROM cards WHERE holder_name = ? LIMIT 1";
                    selectStatement = conn.prepareStatement(card);
                    selectStatement.setString(1, resultUser.getString("login"));
                    resultCard = selectStatement.executeQuery();
                    if (!resultCard.next()) {
                        return false;
                    }
                    if (!resultCard.getBoolean("sbp")) {
                        return false;
                    } else {
                        String addHistory = "INSERT INTO historytransfer (sender_number_card,recipient_number_card,transfer_amount,date) VALUES(?,?,?,?)";
                        String updateBalancePrihod = "update cards SET balance = ? WHERE holder_name = ? LIMIT 1";
                        String updateBalanceOtpr = "update cards SET balance = ? WHERE card_number = ? LIMIT 1";
                        String findNumCard = "Select * From cards where holder_name = ? LIMIT 1";
                        Double newBalanceOtpr = balance - valuePer;
                        Double newBalancePrihod = Double.parseDouble(String.valueOf(resultCard.getBigDecimal("balance"))) + valuePer;
                        PreparedStatement selectStatementoptr = conn.prepareStatement(updateBalanceOtpr);
                        selectStatementoptr.setBigDecimal(1, BigDecimal.valueOf(newBalanceOtpr));
                        selectStatementoptr.setString(2, numberotkuda);
                        selectStatementoptr.executeUpdate();
                        selectStatementoptr.close();
                        PreparedStatement selectStatementprhiod = conn.prepareStatement(updateBalancePrihod);
                        selectStatementprhiod.setBigDecimal(1, BigDecimal.valueOf(newBalancePrihod));
                        selectStatementprhiod.setString(2, resultUser.getString("login"));
                        selectStatementprhiod.executeUpdate();
                        selectStatementprhiod.close();
                        PreparedStatement findNumCardState = conn.prepareStatement(findNumCard);
                        findNumCardState.setString(1, resultUser.getString("login"));
                        resultFindNumCard = findNumCardState.executeQuery();
                        if (!resultFindNumCard.next()) {
                            findNumCardState.close();
                            return false;
                        }
                        PreparedStatement addHistoryState = conn.prepareStatement(addHistory);
                        addHistoryState.setString(1, numberotkuda);
                        addHistoryState.setString(2, resultFindNumCard.getString("card_number"));
                        addHistoryState.setBigDecimal(3, BigDecimal.valueOf(valuePer));
                        Calendar calendar = Calendar.getInstance();
                        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                        addHistoryState.setString(4, sdf.format(calendar.getTime()));
                        addHistoryState.executeUpdate();
                        addHistoryState.close();
                        findNumCardState.close();
                        return true;
                    }
                } catch (SQLException e) {
                    Log.e(TAG, "Error in perevod SBP", e);
                    return false;
                } finally {
                    try {
                        if (resultFindNumCard != null) resultFindNumCard.close();
                        if (resultCard != null) resultCard.close();
                        if (resultUser != null) resultUser.close();
                        if (selectStatement != null) selectStatement.close();
                        if (conn != null && !conn.isClosed()) conn.close();
                    } catch (SQLException e) {
                        Log.e(TAG, "Error closing resources", e);
                    }
                }
            } else {
                String card = "SELECT * FROM cards WHERE card_number = ?";
                Connection conn = null;
                PreparedStatement selectStatement = null;
                ResultSet resultCard = null;
                try {
                    conn = getConnectionSafely();
                    if (conn == null) {
                        return false;
                    }
                    selectStatement = conn.prepareStatement(card);
                    selectStatement.setString(1, number);
                    resultCard = selectStatement.executeQuery();
                    if (!resultCard.next()) {
                        return false;
                    }
                    String addHistory = "INSERT INTO historytransfer (sender_number_card,recipient_number_card,transfer_amount,date) VALUES(?,?,?,?)";
                    String updateBalancePrihod = "update cards SET balance = ? WHERE card_number = ? LIMIT 1";
                    String updateBalanceOtpr = "update cards SET balance = ? WHERE card_number = ? LIMIT 1";
                    Double newBalanceOtpr = balance - valuePer;
                    Double newBalancePrihod = Double.parseDouble(String.valueOf(resultCard.getBigDecimal("balance"))) + valuePer;
                    selectStatement.close();
                    PreparedStatement selectStatementoptr = conn.prepareStatement(updateBalanceOtpr);
                    selectStatementoptr.setBigDecimal(1, BigDecimal.valueOf(newBalanceOtpr));
                    selectStatementoptr.setString(2, numberotkuda);
                    selectStatementoptr.executeUpdate();
                    selectStatementoptr.close();
                    PreparedStatement selectStatementprhiod = conn.prepareStatement(updateBalancePrihod);
                    selectStatementprhiod.setBigDecimal(1, BigDecimal.valueOf(newBalancePrihod));
                    selectStatementprhiod.setString(2, number);
                    selectStatementprhiod.executeUpdate();
                    selectStatementprhiod.close();
                    PreparedStatement addHistoryState = conn.prepareStatement(addHistory);
                    addHistoryState.setString(1, numberotkuda);
                    addHistoryState.setString(2, number);
                    addHistoryState.setBigDecimal(3, BigDecimal.valueOf(valuePer));
                    Calendar calendar = Calendar.getInstance();
                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                    addHistoryState.setString(4, sdf.format(calendar.getTime()));
                    addHistoryState.executeUpdate();
                    addHistoryState.close();
                    return true;
                } catch (SQLException e) {
                    Log.e(TAG, "Error in perevod", e);
                    return false;
                } finally {
                    try {
                        if (resultCard != null) resultCard.close();
                        if (selectStatement != null) selectStatement.close();
                        if (conn != null && !conn.isClosed()) conn.close();
                    } catch (SQLException e) {
                        Log.e(TAG, "Error closing resources", e);
                    }
                }
            }
        }
    }

    public List<CardHistory> LoadHistory(String numCard) {
        List<CardHistory> historyList = new ArrayList<>();
        String history = "SELECT * FROM historytransfer WHERE sender_number_card = ? || recipient_number_card = ?";
        Connection conn = null;
        PreparedStatement selectStatement = null;
        ResultSet resultSet = null;
        try {
            conn = getConnectionSafely();
            if (conn == null) {
                return historyList;
            }
            selectStatement = conn.prepareStatement(history);
            selectStatement.setString(1, numCard);
            selectStatement.setString(2, numCard);
            resultSet = selectStatement.executeQuery();
            while (resultSet.next()) {
                CardHistory historyEntry = new CardHistory();
                historyEntry.setDate(resultSet.getString("date"));
                historyEntry.setNumbercardotpr(resultSet.getString("sender_number_card"));
                historyEntry.setNumbercardpol(resultSet.getString("recipient_number_card"));
                historyEntry.setSumma(resultSet.getString("transfer_amount"));
                String senderNumberCard = resultSet.getString("sender_number_card");
                if (senderNumberCard.equals(numCard)) {
                    historyEntry.setOperation("Отправление");
                } else {
                    historyEntry.setOperation("Зачисление");
                }
                historyList.add(historyEntry);
            }
            return historyList;
        } catch (SQLException e) {
            Log.e(TAG, "Error in LoadHistory", e);
            return historyList;
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (selectStatement != null) selectStatement.close();
                if (conn != null && !conn.isClosed()) conn.close();
            } catch (SQLException e) {
                Log.e(TAG, "Error closing resources", e);
            }
        }
    }

    public List<CardHistory> LoadHistoryAdmin(String numCard) {
        List<CardHistory> historyList = new ArrayList<>();
        String history = "SELECT * FROM historytransfer WHERE sender_number_card LIKE CONCAT('%', ?, '%') OR recipient_number_card LIKE CONCAT('%', ?, '%')";
        Connection conn = null;
        PreparedStatement selectStatement = null;
        ResultSet resultSet = null;
        try {
            conn = getConnectionSafely();
            if (conn == null) {
                return historyList;
            }
            selectStatement = conn.prepareStatement(history);
            selectStatement.setString(1, numCard);
            selectStatement.setString(2, numCard);
            resultSet = selectStatement.executeQuery();
            while (resultSet.next()) {
                CardHistory historyEntry = new CardHistory();
                historyEntry.setDate(resultSet.getString("date"));
                historyEntry.setNumbercardotpr(resultSet.getString("sender_number_card"));
                historyEntry.setNumbercardpol(resultSet.getString("recipient_number_card"));
                historyEntry.setSumma(resultSet.getString("transfer_amount"));
                String senderNumberCard = resultSet.getString("sender_number_card");
                if (senderNumberCard.equals(numCard)) {
                    historyEntry.setOperation("Отправление");
                } else {
                    historyEntry.setOperation("Зачисление");
                }

                String getnick = "SELECT * FROM cards WHERE card_number = ?";
                selectStatement = conn.prepareStatement(getnick);
                selectStatement.setString(1, resultSet.getString("sender_number_card"));
                ResultSet senderResult = selectStatement.executeQuery();
                String userStr = "select * FROM users WHERE login = ?";
                if (senderResult.next()) {
                    String nickOtpr = senderResult.getString("holder_name");
                    selectStatement = conn.prepareStatement(userStr);
                    selectStatement.setString(1, nickOtpr);
                    ResultSet Otpr = selectStatement.executeQuery();
                    if (Otpr.next()) {
                        historyEntry.setFioOtpr(Otpr.getString("fio"));
                        historyEntry.setNumPassOtpr(Otpr.getString("passport"));
                    } else {
                        historyEntry.setFioOtpr("-");
                        historyEntry.setNumPassOtpr("-");
                    }
                }
                selectStatement = conn.prepareStatement(getnick);
                selectStatement.setString(1, resultSet.getString("recipient_number_card"));
                ResultSet recipientResult = selectStatement.executeQuery();
                if (recipientResult.next()) {
                    String nickPol = recipientResult.getString("holder_name");
                    selectStatement = conn.prepareStatement(userStr);
                    selectStatement.setString(1, nickPol);
                    ResultSet Poluch = selectStatement.executeQuery();
                    if (Poluch.next()) {
                        historyEntry.setFioPol(Poluch.getString("fio"));
                        historyEntry.setNumPassPol(Poluch.getString("passport"));
                    } else {
                        historyEntry.setFioPol("-");
                        historyEntry.setNumPassPol("-");
                    }
                }
                historyList.add(historyEntry);
            }
            return historyList;
        } catch (SQLException e) {
            Log.e(TAG, "Error in LoadHistoryAdmin", e);
            return historyList;
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (selectStatement != null) selectStatement.close();
                if (conn != null && !conn.isClosed()) conn.close();
            } catch (SQLException e) {
                Log.e(TAG, "Error closing resources", e);
            }
        }
    }

    public boolean sendProblem(String textproblem, Object valuewhereproblem) {
        String createCard = "INSERT INTO admin_banking (login_user, problem,problem_text ,send_date ,is_successful) VALUES(?,?,?,?,?)";
        Connection conn = null;
        PreparedStatement selectStatement = null;
        try {
            conn = getConnectionSafely();
            if (conn == null) {
                return false;
            }
            selectStatement = conn.prepareStatement(createCard);
            selectStatement.setString(1, User.getLogin());
            selectStatement.setString(2, (String) valuewhereproblem);
            selectStatement.setString(3, textproblem);
            Calendar calendar = Calendar.getInstance();
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            selectStatement.setString(4, sdf.format(calendar.getTime()));
            selectStatement.setBoolean(5, false);
            int resultSet = selectStatement.executeUpdate();
            return resultSet > 0;
        } catch (SQLException e) {
            Log.e(TAG, "Error in sendProblem", e);
            return false;
        } finally {
            try {
                if (selectStatement != null) selectStatement.close();
                if (conn != null && !conn.isClosed()) conn.close();
            } catch (SQLException e) {
                Log.e(TAG, "Error closing resources", e);
            }
        }
    }

    public List<Zayavki> LoadZayavki(String loginUser) {
        List<Zayavki> zayavkaList = new ArrayList<>();
        String history = "SELECT * FROM admin_banking WHERE login_user LIKE CONCAT('%', ?, '%')";
        Connection conn = null;
        PreparedStatement selectStatement = null;
        ResultSet resultSet = null;
        try {
            conn = getConnectionSafely();
            if (conn == null) {
                return zayavkaList;
            }
            selectStatement = conn.prepareStatement(history);
            selectStatement.setString(1, loginUser);
            resultSet = selectStatement.executeQuery();
            while (resultSet.next()) {
                Zayavki zayavkiEnter = new Zayavki();
                zayavkiEnter.setLogin(resultSet.getString("login_user"));
                zayavkiEnter.setDate(resultSet.getString("send_date"));
                zayavkiEnter.setProblem(resultSet.getString("problem"));
                zayavkiEnter.setTextProblem(resultSet.getString("problem_text"));
                int check = resultSet.getInt("is_successful");
                if (check == 1) {
                    zayavkiEnter.setIs_successful("Решено");
                } else {
                    zayavkiEnter.setIs_successful("Нерешено");
                }
                zayavkaList.add(zayavkiEnter);
            }
            return zayavkaList;
        } catch (SQLException e) {
            Log.e(TAG, "Error in LoadZayavki", e);
            return zayavkaList;
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (selectStatement != null) selectStatement.close();
                if (conn != null && !conn.isClosed()) conn.close();
            } catch (SQLException e) {
                Log.e(TAG, "Error closing resources", e);
            }
        }
    }

    public Boolean CheckVklad(String loginUser) {
        String checkVklad = "SELECT * FROM vklads WHERE user_login = ?";
        String percentdate = "SELECT * FROM interest_rates";
        Connection conn = null;
        PreparedStatement selectStatement = null;
        ResultSet resultVklad = null;
        ResultSet resultSet = null;
        try {
            conn = getConnectionSafely();
            if (conn == null) {
                return false;
            }
            selectStatement = conn.prepareStatement(checkVklad);
            selectStatement.setString(1, loginUser);
            resultVklad = selectStatement.executeQuery();
            selectStatement = conn.prepareStatement(percentdate);
            resultSet = selectStatement.executeQuery();
            String perc = "";
            while (resultSet.next()) {
                String VkladPerc = "К-во мес: " + resultSet.getString("term") + " Процент: " + resultSet.getDouble("interest") + "%";
                perc += VkladPerc + ";";
            }
            Percents.setPercents(perc.trim());
            if (resultVklad.next()) {
                Vklad.setSum(resultVklad.getDouble("sum"));
                Vklad.setPercent(resultVklad.getInt("percent"));
                Vklad.setGetmon(resultVklad.getDouble("getmon"));
                Vklad.setNumber_vklad(resultVklad.getString("number_vklad"));
                Vklad.setCreate_date(String.valueOf(resultVklad.getDate("create_date")));
                Vklad.setEnd_date(String.valueOf(resultVklad.getDate("end_date")));
                Vklad.setUser_login(resultVklad.getString("user_login"));
                return true;
            }
        } catch (SQLException e) {
            Log.e(TAG, "Error in CheckVklad", e);
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (resultVklad != null) resultVklad.close();
                if (selectStatement != null) selectStatement.close();
                if (conn != null && !conn.isClosed()) conn.close();
            } catch (SQLException e) {
                Log.e(TAG, "Error closing resources", e);
            }
        }
        return false;
    }

    public boolean CreateVklad(String login, String percent, String c_month) {
        String createVklad = "INSERT INTO vklads (sum,percent,getmon,number_vklad,create_date,end_date,user_login) VALUES(?,?,?,?,?,?,?)";
        String checkNumVklad = "SELECT COUNT(*) FROM vklads WHERE number_vklad = ?";
        Connection conn = null;
        try {
            conn = getConnectionSafely();
            if (conn == null) {
                return false;
            }
            StringBuilder finalVkladNum = null;
            PreparedStatement selectStatement = null;
            ResultSet resultSet = null;
            while (true) {
                selectStatement = conn.prepareStatement(checkNumVklad);
                finalVkladNum = generatorCardNum();
                selectStatement.setString(1, finalVkladNum.toString());
                resultSet = selectStatement.executeQuery();
                if (resultSet.next() && resultSet.getInt(1) == 0) {
                    resultSet.close();
                    selectStatement.close();
                    break;
                }
                resultSet.close();
                selectStatement.close();
            }
            PreparedStatement prst = conn.prepareStatement(createVklad);
            prst.setInt(1, 0);
            prst.setDouble(2, Double.parseDouble(percent));
            Calendar calendar = Calendar.getInstance();
            prst.setInt(3, 0);
            String vkladNum = finalVkladNum.toString();
            prst.setString(4, vkladNum);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String createDate = sdf.format(calendar.getTime());
            prst.setDate(5, Date.valueOf(createDate));
            calendar.add(Calendar.MONTH, Integer.parseInt(c_month));
            String endDate = sdf.format(calendar.getTime());
            prst.setDate(6, Date.valueOf(endDate));
            prst.setString(7, login);
            prst.executeUpdate();
            prst.close();
            Vklad.setNumber_vklad(vkladNum);
            Vklad.setSum(0);
            Vklad.setGetmon(0);
            Vklad.setPercent((int) Double.parseDouble(percent));
            Vklad.setEnd_date(endDate);
            Vklad.setUser_login(login);
            return true;
        } catch (SQLException e) {
            Log.e(TAG, "Error in CreateVklad", e);
            return false;
        } finally {
            try {
                if (conn != null && !conn.isClosed()) conn.close();
            } catch (SQLException e) {
                Log.e(TAG, "Error closing connection", e);
            }
        }
    }

    public boolean PopolnitVklad(String summ, String login) {
        String addHistory = "INSERT INTO historytransfer (sender_number_card,recipient_number_card,transfer_amount,date) VALUES(?,?,?,?)";
        String updateBalanceVklad = "update vklads SET sum = ? WHERE number_vklad = ? LIMIT 1";
        String updateBalanceCard = "update cards SET balance = ? WHERE card_number = ? LIMIT 1";
        Connection conn = null;
        PreparedStatement selectStatementoptr = null;
        PreparedStatement selectStatementprhiod = null;
        PreparedStatement addHistoryState = null;
        try {
            conn = getConnectionSafely();
            if (conn == null) {
                return false;
            }
            Double newBalanceCard = Double.parseDouble(Card.getBalance()) - Double.parseDouble(summ);
            Double newBalanceVklad = Vklad.getSum() + Double.parseDouble(summ);
            selectStatementoptr = conn.prepareStatement(updateBalanceCard);
            selectStatementoptr.setBigDecimal(1, BigDecimal.valueOf(newBalanceCard));
            selectStatementoptr.setString(2, Card.getNumber());
            selectStatementoptr.executeUpdate();
            selectStatementprhiod = conn.prepareStatement(updateBalanceVklad);
            selectStatementprhiod.setBigDecimal(1, BigDecimal.valueOf(newBalanceVklad));
            selectStatementprhiod.setString(2, Vklad.getNumber_vklad());
            selectStatementprhiod.executeUpdate();
            addHistoryState = conn.prepareStatement(addHistory);
            addHistoryState.setString(1, Card.getNumber());
            addHistoryState.setString(2, Vklad.getNumber_vklad());
            addHistoryState.setBigDecimal(3, BigDecimal.valueOf(Double.parseDouble(summ)));
            Calendar calendar = Calendar.getInstance();
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            addHistoryState.setString(4, sdf.format(calendar.getTime()));
            addHistoryState.executeUpdate();
            return true;
        } catch (SQLException e) {
            Log.e(TAG, "Error in PopolnitVklad", e);
            return false;
        } finally {
            try {
                if (addHistoryState != null) addHistoryState.close();
                if (selectStatementprhiod != null) selectStatementprhiod.close();
                if (selectStatementoptr != null) selectStatementoptr.close();
                if (conn != null && !conn.isClosed()) conn.close();
            } catch (SQLException e) {
                Log.e(TAG, "Error closing resources", e);
            }
        }
    }
}
