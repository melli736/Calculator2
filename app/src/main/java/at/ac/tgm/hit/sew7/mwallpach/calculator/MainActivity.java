package at.ac.tgm.hit.sew7.mwallpach.calculator;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;

public class MainActivity extends AppCompatActivity {
    // Deklariert UI-Elemente
    private EditText feld1;
    private EditText feld2;
    private TextView ergebnis;
    private Spinner operationSpinner; // Spinner für die Rechenoperationen

    // Deklariert SharedPreferences
    private SharedPreferences sharedPreferences;
    private static final String PREF_KEY = "saved_value";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Initialisiert SharedPreferences
        sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);

        // Initialisiert UI-Elemente nach Festlegen des Layouts
        feld1 = findViewById(R.id.z1);
        feld2 = findViewById(R.id.z2);
        ergebnis = findViewById(R.id.textview_first);
        operationSpinner = findViewById(R.id.operationSpinner); // Spinner initialisieren

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

        // In Ihrer onCreate-Methode
        // Initialisieren Sie den Spinner und füllen Sie ihn mit den Optionen aus den Ressourcen
        Spinner operationSpinner = findViewById(R.id.operationSpinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.operation_options, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        operationSpinner.setAdapter(adapter);

        // Fügen Sie einen Auswahl-Listener für den Spinner hinzu
        operationSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // Hier können Sie Code ausführen, wenn eine Operation ausgewählt wurde
                // Zum Beispiel: enableCalculateButton();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Hier können Sie Code ausführen, wenn keine Operation ausgewählt wurde
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.options_menu, menu);
        return true;
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

            int result = 0;

            // Ruft die ausgewählte Operation aus dem Spinner ab
            String selectedOperation = operationSpinner.getSelectedItem().toString();

            // Führt Berechnungen basierend auf der ausgewählten Operation durch
            switch (selectedOperation) {
                case "Addition":
                    result = z1 + z2;
                    break;
                case "Subtraktion":
                    result = z1 - z2;
                    break;
                case "Multiplikation":
                    result = z1 * z2;
                    break;
                case "Division":
                    if (z2 != 0) {
                        result = z1 / z2;
                    } else {
                        Toast.makeText(this, "Division durch Null ist nicht erlaubt.", Toast.LENGTH_SHORT).show();
                        return; // Methode ohne Ändern der Schaltflächenfarbe verlassen
                    }
                    break;
            }

            // Setzen Sie das Ergebnis im TextView
            setErgebnis(result);

            // Finden Sie die Schaltfläche "Berechnen" nach ID
            AppCompatButton calculateButton = findViewById(R.id.button1);

            // Ändern Sie die Schaltflächenfarbe basierend auf dem Ergebnis
            if (result < 0) {
                // Wenn das Ergebnis negativ ist, setzen Sie die Hintergrundfarbe auf rot
                ergebnis.setBackgroundResource(R.drawable.button_square_red);
            } else {
                // Wenn das Ergebnis nicht negativ ist, setzen Sie die Hintergrundfarbe auf die genaue Farbe #4B84F6
                ergebnis.setBackgroundColor(0xFF4B84F6);
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_reset) {
            resetFieldsAndSharedPreferences();
            return true;
        } else if (id == R.id.action_info) {
            showAppInfo(); // Hier rufen Sie die Methode zur Anzeige der App-Info auf
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void resetFieldsAndSharedPreferences() {
        feld1.setText("");
        feld2.setText("");
        ergebnis.setText("");

        // Löschen Sie die SharedPreferences
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();

        // Setzen Sie die Hintergrundfarbe des Ergebnisfeldes auf blau
        ergebnis.setBackgroundColor(0xFF4B84F6);
    }

    // Show app information as a toast message
    private void showAppInfo() {
        String appInfo = "Author: Melissa Wallpach\nClass: 4BHIT";
        Toast.makeText(this, appInfo, Toast.LENGTH_LONG).show();
    }
}
