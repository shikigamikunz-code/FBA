package com.fba.builder;

import android.app.Activity;
import android.os.Bundle;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

    private LinearLayout container;
    private TextView statusText;

    private final int backgroundColor = Color.rgb(6, 16, 31);
    private final int cyanColor = Color.rgb(0, 217, 255);
    private final int textColor = Color.WHITE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showHome();
    }

    private void showHome() {
        ScrollView scrollView = new ScrollView(this);
        scrollView.setBackgroundColor(backgroundColor);

        container = new LinearLayout(this);
        container.setOrientation(LinearLayout.VERTICAL);
        container.setGravity(Gravity.CENTER_HORIZONTAL);
        container.setPadding(28, 48, 28, 32);
        container.setBackgroundColor(backgroundColor);

        scrollView.addView(container);
        setContentView(scrollView);

        TextView logo = new TextView(this);
        logo.setText("FBA");
        logo.setTextSize(48);
        logo.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        logo.setTextColor(cyanColor);
        logo.setGravity(Gravity.CENTER);
        container.addView(logo, matchWidth(90));

        TextView subtitle = new TextView(this);
        subtitle.setText("FREE BUILD ANDROID");
        subtitle.setTextSize(14);
        subtitle.setTextColor(textColor);
        subtitle.setGravity(Gravity.CENTER);
        container.addView(subtitle, matchWidth(40));

        addSpace(30);

        addButton("Buat Proyek", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showMessage(
                    "Fitur pembuatan proyek sedang dikembangkan."
                );
            }
        });

        addButton("Proyek Saya", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showMessage(
                    "Daftar proyek akan tersedia pada tahap berikutnya."
                );
            }
        });

        addButton("Tentang FBA", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showMessage(
                    "FBA adalah proyek builder Android yang sedang dikembangkan."
                );
            }
        });

        addSpace(24);

        statusText = new TextView(this);
        statusText.setText("FBA siap dijalankan.");
        statusText.setTextSize(14);
        statusText.setTextColor(cyanColor);
        statusText.setGravity(Gravity.CENTER);
        container.addView(statusText, matchWidth(60));
    }

    private LinearLayout.LayoutParams matchWidth(int height) {
        return new LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            height
        );
    }

    private void addButton(String title, View.OnClickListener listener) {
        Button button = new Button(this);
        button.setText(title);
        button.setTextSize(16);
        button.setAllCaps(false);
        button.setTextColor(textColor);
        button.setBackgroundTintList(
            android.content.res.ColorStateList.valueOf(
                Color.rgb(10, 45, 65)
            )
        );

        LinearLayout.LayoutParams params = matchWidth(58);
        params.setMargins(0, 10, 0, 10);

        container.addView(button, params);
        button.setOnClickListener(listener);
    }

    private void addSpace(int height) {
        View space = new View(this);
        container.addView(
            space,
            new LinearLayout.LayoutParams(1, height)
        );
    }

    private void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
        if (statusText != null) {
            statusText.setText(message);
        }
    }
}
