package at.ac.tgm.hit.sew7.mwallpach.calculator;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

public class MainActivity extends AppCompatActivity {
    // Deklariert UI-Elemente
    private EditText feld1;
    private EditText feld2;
    private TextView ergebnis;
    private RadioButton plus;
    private RadioButton minus;
    private RadioButton mal;
    private RadioButton dividiert;

    // Deklariert SharedPreferences
    private SharedPreferences sharedPreferences;
    private static final String PREF_KEY = "saved_value";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialisiert SharedPreferences
        sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);

        // Initialisiert UI-Elemente nach Festlegen des Layouts
        feld1 = findViewById(R.id.z1);
        feld2 = findViewById(R.id.z2);
        ergebnis = findViewById(R.id.textview_first);
        plus = findViewById(R.id.radioButton1);
        minus = findViewById(R.id.radioButton2);
        mal = findViewById(R.id.radioButton3);
        dividiert = findViewById(R.id.radioButton4);

        // Fügt einen Touch-Listener hinzu, um das Feld 'ergebnis' bei Berührung zu leeren
        ergebnis.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // Leert das 'ergebnis'-Feld bei Berührung (ACTION_DOWN)
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    ergebnis.setText("");
                }
                return false;
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        // Aktiviert den "Berechnen"-Button
        enableCalculateButton();
    }

    private void enableCalculateButton() {
        // Findet den "Berechnen"-Button anhand seiner ID
        AppCompatButton calculateButton = findViewById(R.id.button1);

        // Aktiviert den Button
        calculateButton.setEnabled(true);
    }

    public void berechnen(View v) {
        try {
            // Holt Werte aus den Eingabefeldern
            int z1 = Integer.parseInt(feld1.getText().toString());
            int z2 = Integer.parseInt(feld2.getText().toString());

            // Führt Berechnungen basierend auf ausgewähltem Radio-Button durch
            if (plus.isChecked()) {
                setErgebnis(z1 + z2);
            } else if (minus.isChecked()) {
                setErgebnis(z1 - z2);
            } else if (mal.isChecked()) {
                setErgebnis(z1 * z2);
            } else if (dividiert.isChecked()) {
                if (z2 != 0) {
                    setErgebnis(z1 / z2);
                } else {
                    Toast.makeText(this, "Division durch Null ist nicht erlaubt.", Toast.LENGTH_SHORT).show();
                }
            }
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Ungültige Eingabe", Toast.LENGTH_SHORT).show();
        }
    }

    private void storeValuesInSharedPreferences() {
        try {
            // Holt Werte aus den Eingabefeldern
            int z1 = Integer.parseInt(feld1.getText().toString());
            int z2 = Integer.parseInt(feld2.getText().toString());

            // Speichert beide Eingabewerte in SharedPreferences
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt("z1", z1);
            editor.putInt("z2", z2);
            editor.apply();
            Toast.makeText(this, "Gespeichert", Toast.LENGTH_SHORT).show();
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Ungültige Eingabe", Toast.LENGTH_SHORT).show();
        }
    }

    public void memoryRecall(View v) {
        int storedZ1 = sharedPreferences.getInt("z1", 0); // 0 ist der Standardwert, falls kein Wert gefunden wird
        int storedZ2 = sharedPreferences.getInt("z2", 0); // 0 ist der Standardwert, falls kein Wert gefunden wird

        feld1.setText(String.valueOf(storedZ1));
        feld2.setText(String.valueOf(storedZ2));
    }

    public void setErgebnis(int zahl) {
        ergebnis.setText(String.valueOf(zahl));
    }

    // Diese Methode wurde in storeValuesInSharedPreferences() umbenannt, um Konflikte zu vermeiden
    public void storeValuesInSharedPreferences(View view) {
        try {
            // Holt Werte aus den Eingabefeldern
            int z1 = Integer.parseInt(feld1.getText().toString());
            int z2 = Integer.parseInt(feld2.getText().toString());

            // Speichert beide Eingabewerte in SharedPreferences
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt("z1", z1);
            editor.putInt("z2", z2);
            editor.apply();
            Toast.makeText(this, "Gespeichert", Toast.LENGTH_SHORT).show();
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Ungültige Eingabe", Toast.LENGTH_SHORT).show();
        }
    }
}
