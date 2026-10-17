package com.fba.builder;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.BitmapFactory;
import android.graphics.Bitmap;
import android.graphics.drawable.GradientDrawable;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import android.net.Uri;

import java.util.ArrayList;

public class MainActivity extends Activity {

    private final int BG = Color.rgb(5, 12, 27);
    private final int PANEL = Color.rgb(12, 27, 48);
    private final int CYAN = Color.rgb(0, 217, 255);
    private final int WHITE = Color.WHITE;
    private final int MUTED = Color.rgb(160, 180, 205);

    private LinearLayout root;
    private LinearLayout content;
    private TextView status;
    private EditText nameInput;
    private TextView iconLabel;
    private ImageView iconPreview;
    private Uri selectedImage;
    private final ArrayList<String> projects = new ArrayList<>();
    private final Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setStatusBarColor(BG);
        getWindow().setNavigationBarColor(BG);
        showSplash();
    }

    private GradientDrawable shape(int color, int stroke) {
        GradientDrawable d = new GradientDrawable();
        d.setColor(color);
        d.setCornerRadius(22);
        if (stroke != 0) d.setStroke(2, stroke);
        return d;
    }

    private TextView text(String value, int size, int color) {
        TextView t = new TextView(this);
        t.setText(value);
        t.setTextSize(size);
        t.setTextColor(color);
        t.setGravity(Gravity.CENTER_VERTICAL);
        t.setPadding(4, 5, 4, 5);
        return t;
    }

    private void showSplash() {
        LinearLayout splash = new LinearLayout(this);
        splash.setOrientation(LinearLayout.VERTICAL);
        splash.setGravity(Gravity.CENTER);
        splash.setPadding(30, 30, 30, 30);
        splash.setBackgroundColor(BG);
        setContentView(splash);

        TextView logo = text("FBA", 58, CYAN);
        logo.setGravity(Gravity.CENTER);
        logo.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        splash.addView(logo);

        TextView sub = text("FREE BUILD ANDROID", 15, WHITE);
        sub.setGravity(Gravity.CENTER);
        splash.addView(sub);

        TextView tagline = text(
            "CREATE YOUR ANDROID PROJECT",
            11, MUTED
        );
        tagline.setGravity(Gravity.CENTER);
        splash.addView(tagline);

        View space = new View(this);
        splash.addView(space, new LinearLayout.LayoutParams(1, 35));

        android.widget.ProgressBar progress =
            new android.widget.ProgressBar(
                this, null,
                android.R.attr.progressBarStyleHorizontal
            );
        progress.setMax(100);
        progress.setProgress(0);
        progress.setProgressTintList(
            android.content.res.ColorStateList.valueOf(CYAN)
        );
        splash.addView(progress,
            new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, 8
            ));

        TextView loading = text("INITIALIZING FBA...", 12, CYAN);
        loading.setGravity(Gravity.CENTER);
        splash.addView(loading);

        final int[] value = {0};
        Runnable task = new Runnable() {
            @Override
            public void run() {
                value[0] += 5;
                progress.setProgress(value[0]);
                loading.setText("INITIALIZING FBA... " + value[0] + "%");
                if (value[0] < 100) {
                    handler.postDelayed(this, 45);
                } else {
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            showHome();
                        }
                    }, 250);
                }
            }
        };
        handler.postDelayed(task, 100);
    }

    private void setupPage(String title) {
        ScrollView scroll = new ScrollView(this);
        scroll.setFillViewport(true);
        scroll.setBackgroundColor(BG);

        root = new LinearLayout(this);
        root.setOrientation(LinearLayout.VERTICAL);
        root.setPadding(20, 18, 20, 28);
        root.setBackgroundColor(BG);
        scroll.addView(root);
        setContentView(scroll);

        LinearLayout header = new LinearLayout(this);
        header.setGravity(Gravity.CENTER_VERTICAL);
        header.setOrientation(LinearLayout.HORIZONTAL);

        TextView logo = text("FBA", 25, CYAN);
        logo.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        header.addView(logo,
            new LinearLayout.LayoutParams(75, 54));

        TextView pageTitle = text(title, 18, WHITE);
        pageTitle.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        header.addView(pageTitle,
            new LinearLayout.LayoutParams(0, 54, 1));

        root.addView(header);
        addLine(root);
        content = root;
    }

    private void addLine(LinearLayout parent) {
        View line = new View(this);
        line.setBackgroundColor(Color.rgb(25, 65, 90));
        LinearLayout.LayoutParams p =
            new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, 1
            );
        p.setMargins(0, 8, 0, 15);
        parent.addView(line, p);
    }

    private void gap(int height) {
        View v = new View(this);
        content.addView(v,
            new LinearLayout.LayoutParams(1, height));
    }

    private TextView heading(String value) {
        TextView t = text(value, 23, WHITE);
        t.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        content.addView(t);
        return t;
    }

    private TextView paragraph(String value) {
        TextView t = text(value, 14, MUTED);
        t.setGravity(Gravity.TOP);
        content.addView(t);
        return t;
    }

    private Button button(String label, Runnable action) {
        Button b = new Button(this);
        b.setText(label);
        b.setTextSize(15);
        b.setAllCaps(false);
        b.setTextColor(WHITE);
        b.setBackgroundTintList(
            android.content.res.ColorStateList.valueOf(PANEL)
        );

        LinearLayout.LayoutParams p =
            new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, 58
            );
        p.setMargins(0, 7, 0, 7);
        content.addView(b, p);
        b.setOnClickListener(v -> action.run());
        return b;
    }

    private EditText edit(String hint) {
        EditText e = new EditText(this);
        e.setSingleLine(true);
        e.setTextColor(WHITE);
        e.setHintTextColor(MUTED);
        e.setHint(hint);
        e.setTextSize(15);
        e.setPadding(15, 8, 15, 8);
        e.setBackground(shape(PANEL, CYAN));

        LinearLayout.LayoutParams p =
            new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, 55
            );
        p.setMargins(0, 8, 0, 12);
        content.addView(e, p);
        return e;
    }

    private void showHome() {
        setupPage("Dashboard");
        gap(18);

        TextView hero = heading("Build Something Amazing.");
        hero.setTextColor(CYAN);
        paragraph("Your Android workspace starts here.");
        gap(15);

        LinearLayout card = new LinearLayout(this);
        card.setOrientation(LinearLayout.VERTICAL);
        card.setPadding(18, 18, 18, 18);
        card.setBackground(shape(PANEL, CYAN));

        TextView badge = text("FBA BUILDER  /  VERSION 1.0", 12, CYAN);
        card.addView(badge);

        TextView desc = text(
            "Create projects, manage your work, and prepare Android builds.",
            15, WHITE
        );
        desc.setPadding(4, 15, 4, 15);
        card.addView(desc);
        content.addView(card);
        gap(18);

        heading("WORKSPACE");
        button("＋   Buat APK", () -> showCreate());
        button("▣   APK Saya", () -> showProjects());
        button("⚙   Pengaturan", () -> showSettings());
        button("ⓘ   Tentang FBA", () -> showAbout());

        gap(15);
        status = paragraph("SYSTEM READY  •  FBA is running");
        status.setTextColor(CYAN);
        gap(12);
        paragraph("FBA • FREE BUILD ANDROID");
    }

    private void showCreate() {
        setupPage("Create Project");
        button("←   Kembali ke Beranda", () -> showHome());
        gap(12);
        heading("Buat Proyek Baru");
        paragraph("Masukkan nama dan pilih ikon aplikasi.");
        gap(10);

        nameInput = edit("Nama aplikasi");

        iconPreview = new ImageView(this);
        iconPreview.setImageDrawable(
            getDrawable(android.R.drawable.sym_def_app_icon)
        );
        iconPreview.setScaleType(ImageView.ScaleType.CENTER_CROP);
        iconPreview.setBackground(shape(PANEL, CYAN));

        LinearLayout.LayoutParams ip =
            new LinearLayout.LayoutParams(120, 120);
        ip.gravity = Gravity.CENTER_HORIZONTAL;
        ip.setMargins(0, 12, 0, 8);
        content.addView(iconPreview, ip);

        iconLabel = text("Ikon default", 13, MUTED);
        iconLabel.setGravity(Gravity.CENTER);
        content.addView(iconLabel);

        button("Pilih Foto Ikon", () -> pickImage());

        button("Impor ZIP Proyek", () -> {
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("application/zip");
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            try {
                startActivityForResult(intent, 202);
            } catch (Exception e) {
                showToast("Pemilih file tidak tersedia.");
            }
        });

        button("Buka Editor Kode", () -> {
            showEditor();
        });

        gap(12);
        button("Simpan Proyek", () -> {
            String name = nameInput.getText().toString().trim();
            if (name.isEmpty()) {
                nameInput.setError("Masukkan nama aplikasi");
                return;
            }
            projects.add(name);
            showToast("Proyek " + name + " berhasil ditambahkan.");
            showProjects();
        });

        paragraph(
            "Catatan: menyimpan proyek di versi ini mencatat namanya dalam sesi aplikasi. Impor ZIP belum menjalankan build."
        );
    }

    private void pickImage() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        try {
            startActivityForResult(intent, 101);
        } catch (Exception e) {
            showToast("Galeri tidak tersedia.");
        }
    }

    @Override
    protected void onActivityResult(
        int requestCode, int resultCode, Intent data
    ) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK || data == null) return;

        Uri uri = data.getData();
        if (uri == null) return;

        if (requestCode == 101) {
            try {
                selectedImage = uri;
                Bitmap bitmap = BitmapFactory.decodeStream(
                    getContentResolver().openInputStream(uri)
                );
                if (bitmap != null) {
                    iconPreview.setImageBitmap(bitmap);
                    iconLabel.setText("Foto dipilih • pratinjau ikon");
                    showToast("Foto ikon berhasil dipilih.");
                }
            } catch (Exception e) {
                showToast("Gagal membuka foto.");
            }
        } else if (requestCode == 202) {
            showToast("ZIP dipilih. Build ZIP belum diaktifkan.");
        }
    }

    private void showEditor() {
        setupPage("Code Editor");
        button("←   Kembali", () -> showCreate());
        heading("Editor Kode");
        paragraph("Tulis catatan atau kode awal proyek di bawah.");
        EditText code = new EditText(this);
        code.setGravity(Gravity.TOP | Gravity.START);
        code.setMinLines(10);
        code.setTextColor(WHITE);
        code.setHintTextColor(MUTED);
        code.setHint("Tulis kode di sini...");
        code.setPadding(14, 14, 14, 14);
        code.setBackground(shape(PANEL, CYAN));
        content.addView(code,
            new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, 260
            ));
        button("Simpan Catatan", () ->
            showToast("Editor ini belum terhubung ke mesin build.")
        );
        paragraph("Kode yang ditulis belum dikompilasi menjadi APK.");
    }

    private void showProjects() {
        setupPage("My Projects");
        button("←   Kembali ke Beranda", () -> showHome());
        gap(10);
        heading("APK Saya");
        paragraph("Daftar proyek yang ditambahkan pada sesi ini.");
        gap(10);

        if (projects.isEmpty()) {
            LinearLayout empty = new LinearLayout(this);
            empty.setOrientation(LinearLayout.VERTICAL);
            empty.setGravity(Gravity.CENTER);
            empty.setPadding(15, 28, 15, 28);
            empty.setBackground(shape(PANEL, 0));

            TextView symbol = text("▣", 40, CYAN);
            symbol.setGravity(Gravity.CENTER);
            empty.addView(symbol);

            TextView msg = text("Belum ada proyek", 18, WHITE);
            msg.setGravity(Gravity.CENTER);
            empty.addView(msg);

            TextView detail = text(
                "Buat proyek pertamamu untuk memulai.",
                13, MUTED
            );
            detail.setGravity(Gravity.CENTER);
            empty.addView(detail);
            content.addView(empty);
        } else {
            for (int i = 0; i < projects.size(); i++) {
                final int index = i;
                LinearLayout card = new LinearLayout(this);
                card.setOrientation(LinearLayout.VERTICAL);
                card.setPadding(15, 12, 15, 12);
                card.setBackground(shape(PANEL, CYAN));

                TextView name = text(projects.get(i), 18, WHITE);
                card.addView(name);

                TextView state = text(
                    "Draft • Belum dibuild", 13, MUTED
                );
                card.addView(state);

                content.addView(card,
                    new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                    ));

                button("Lihat Status", () ->
                    showToast("Proyek masih berupa draft.")
                );
                button("Hapus Proyek", () -> {
                    projects.remove(index);
                    showProjects();
                });
                gap(8);
            }
        }

        gap(12);
        button("＋   Buat Proyek Baru", () -> showCreate());
    }

    private void showSettings() {
        setupPage("Settings");
        button("←   Kembali ke Beranda", () -> showHome());
        gap(12);
        heading("Pengaturan");
        paragraph("Sesuaikan informasi dasar aplikasi.");
        gap(10);

        button("Bahasa: Indonesia", () ->
            showToast("Bahasa Indonesia dipilih.")
        );
        button("Tema: Blue Cyber", () ->
            showToast("Tema biru cyber sedang digunakan.")
        );
        button("Info Aplikasi", () -> showAbout());

        gap(18);
        paragraph("FBA Builder • Version 1.0");
        paragraph("Dibuat untuk membantu pengembangan proyek Android.");
    }

    private void showAbout() {
        setupPage("About FBA");
        gap(25);

        TextView logo = text("FBA", 48, CYAN);
        logo.setGravity(Gravity.CENTER);
        logo.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        content.addView(logo);

        TextView sub = text("FREE BUILD ANDROID", 15, WHITE);
        sub.setGravity(Gravity.CENTER);
        content.addView(sub);

        gap(20);
        paragraph(
            "FBA adalah antarmuka awal untuk mengelola proyek aplikasi Android."
        );
        gap(10);
        paragraph(
            "Versi ini menyediakan navigasi, pemilih foto, form proyek, dan daftar draft. Mesin build otomatis dan ekspor APK masih perlu dikembangkan."
        );
        gap(20);
        button("←   Kembali ke Beranda", () -> showHome());
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onDestroy() {
        handler.removeCallbacksAndMessages(null);
        super.onDestroy();
    }
}
