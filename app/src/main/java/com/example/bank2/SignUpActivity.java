package com.example.bank2;

import android.app.DatePickerDialog;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.bank2.database.DataBaseHandler;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;

public class SignUpActivity extends AppCompatActivity {
    private EditText etLogin, etPassword, etFio, etPassportSeries, etPassportNumber, etBirthDate;
    private RadioGroup rgGender;
    private Button btnSignUp;
    private DataBaseHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        etLogin = findViewById(R.id.etLogin);
        etPassword = findViewById(R.id.etPassword);
        etFio = findViewById(R.id.etFio);
        etPassportSeries = findViewById(R.id.etPassportSeries);
        etPassportNumber = findViewById(R.id.etPassportNumber);
        etBirthDate = findViewById(R.id.etBirthDate);
        rgGender = findViewById(R.id.rgGender);
        btnSignUp = findViewById(R.id.btnSignUp);

        dbHandler = new DataBaseHandler(this);

        // DatePicker для выбора даты рождения
        etBirthDate.setOnClickListener(v -> showDatePickerDialog());
        etBirthDate.setFocusable(false);
        etBirthDate.setClickable(true);

        btnSignUp.setOnClickListener(v -> signUpUser());
    }

    private void showDatePickerDialog() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                (view, selectedYear, selectedMonth, selectedDay) -> {
                    String dateStr = String.format("%04d-%02d-%02d", selectedYear, selectedMonth + 1, selectedDay);
                    etBirthDate.setText(dateStr);
                }, year, month, day);
        
        // Устанавливаем максимальную дату (сегодня)
        datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
        // Устанавливаем минимальную дату (100 лет назад)
        calendar.add(Calendar.YEAR, -100);
        datePickerDialog.getDatePicker().setMinDate(calendar.getTimeInMillis());
        
        datePickerDialog.show();
    }

    private void signUpUser() {
        String login = etLogin.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        String fio = etFio.getText().toString().trim();
        String passportSeries = etPassportSeries.getText().toString().trim();
        String passportNumber = etPassportNumber.getText().toString().trim();
        String birthDateStr = etBirthDate.getText().toString().trim();

        if (login.isEmpty() || password.isEmpty() || fio.isEmpty() || 
            passportSeries.isEmpty() || passportNumber.isEmpty() || birthDateStr.isEmpty()) {
            Toast.makeText(this, "Заполните все поля", Toast.LENGTH_SHORT).show();
            return;
        }

        // Валидация пароля
        if (password.length() < 8) {
            etPassword.setError("Пароль должен содержать не менее 8 символов");
            Toast.makeText(this, "Пароль должен содержать не менее 8 символов", Toast.LENGTH_SHORT).show();
            return;
        }

        // Валидация паспорта
        if (passportSeries.length() != 4 || !passportSeries.matches("\\d+")) {
            etPassportSeries.setError("Серия паспорта должна содержать 4 цифры");
            Toast.makeText(this, "Серия паспорта должна содержать 4 цифры", Toast.LENGTH_SHORT).show();
            return;
        }

        if (passportNumber.length() != 6 || !passportNumber.matches("\\d+")) {
            etPassportNumber.setError("Номер паспорта должен содержать 6 цифр");
            Toast.makeText(this, "Номер паспорта должен содержать 6 цифр", Toast.LENGTH_SHORT).show();
            return;
        }

        int selectedId = rgGender.getCheckedRadioButtonId();
        if (selectedId == -1) {
            Toast.makeText(this, "Выберите пол", Toast.LENGTH_SHORT).show();
            return;
        }

        RadioButton selectedGender = findViewById(selectedId);
        String gender = selectedGender.getText().toString();

        try {
            LocalDate birthDate = LocalDate.parse(birthDateStr, DateTimeFormatter.ISO_LOCAL_DATE);
            String passport = passportSeries + " " + passportNumber;
            new SignUpTask().execute(login, password, fio, passport, gender, birthDateStr);
        } catch (Exception e) {
            Toast.makeText(this, "Неверный формат даты", Toast.LENGTH_SHORT).show();
        }
    }

    private class SignUpTask extends AsyncTask<String, Void, Boolean> {
        @Override
        protected Boolean doInBackground(String... params) {
            try {
                LocalDate birthDate = LocalDate.parse(params[5], DateTimeFormatter.ISO_LOCAL_DATE);
                return dbHandler.signUp(params[0], params[1], params[2], params[3], params[4], birthDate);
            } catch (Exception e) {
                android.util.Log.e("SignUpActivity", "Error in signUp", e);
                return null; // null означает ошибку подключения
            }
        }

        @Override
        protected void onPostExecute(Boolean result) {
            if (result == null) {
                Toast.makeText(SignUpActivity.this, "Ошибка подключения к базе данных. Проверьте интернет-соединение.", Toast.LENGTH_LONG).show();
                return;
            }
            if (result) {
                Toast.makeText(SignUpActivity.this, "Вы успешно зарегистрировались", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(SignUpActivity.this, "Пользователь с таким логином или паспортом уже существует", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
