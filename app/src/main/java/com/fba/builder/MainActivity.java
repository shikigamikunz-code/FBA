package com.fba.builder;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.view.Gravity;
import android.view.View;
import android.widget.*;
import android.net.Uri;
import java.util.ArrayList;

public class MainActivity extends Activity {
    int bg = Color.rgb(3, 10, 24);
    int panel = Color.rgb(7, 23, 43);
    int cyan = Color.rgb(0, 210, 255);
    int blue = Color.rgb(0, 105, 255);
    int white = Color.WHITE;
    int muted = Color.rgb(150, 180, 210);

    LinearLayout page, body;
    EditText appName, developer;
    TextView iconText;
    Uri chosenIcon;
    ArrayList<String> projects = new ArrayList<>();
    Handler handler = new Handler();

    @Override
    public void onCreate(Bundle b) {
        super.onCreate(b);
        getWindow().setStatusBarColor(bg);
        getWindow().setNavigationBarColor(bg);
        splash();
    }

    GradientDrawable shape(int color, int stroke) {
        GradientDrawable d = new GradientDrawable();
        d.setColor(color);
        d.setCornerRadius(20);
        if (stroke != 0) d.setStroke(2, stroke);
        return d;
    }

    TextView label(String s, int size, int color) {
        TextView t = new TextView(this);
        t.setText(s);
        t.setTextSize(size);
        t.setTextColor(color);
        t.setPadding(5, 7, 5, 7);
        return t;
    }

    void splash() {
        LinearLayout l = new LinearLayout(this);
        l.setOrientation(1);
        l.setGravity(Gravity.CENTER);
        l.setPadding(25, 25, 25, 25);
        l.setBackgroundColor(bg);
        setContentView(l);

        TextView logo = label("FBA", 64, cyan);
        logo.setTypeface(null, Typeface.BOLD);
        logo.setGravity(Gravity.CENTER);
        l.addView(logo);

        TextView sub = label("FREE BUILD ANDROID", 15, white);
        sub.setGravity(Gravity.CENTER);
        l.addView(sub);

        ProgressBar p = new ProgressBar(this, null,
                android.R.attr.progressBarStyleHorizontal);
        p.setMax(100);
        p.setProgressTintList(
                android.content.res.ColorStateList.valueOf(cyan));
        LinearLayout.LayoutParams pp = new LinearLayout.LayoutParams(
                -1, 9);
        pp.setMargins(0, 35, 0, 0);
        l.addView(p, pp);

        TextView txt = label("INITIALIZING FBA...", 13, cyan);
        txt.setGravity(Gravity.CENTER);
        l.addView(txt);

        final int[] n = {0};
        Runnable r = new Runnable() {
            public void run() {
                n[0] += 5;
                p.setProgress(n[0]);
                txt.setText("INITIALIZING FBA... " + n[0] + "%");
                if (n[0] < 100) handler.postDelayed(this, 35);
                else handler.postDelayed(() -> home(), 250);
            }
        };
        handler.postDelayed(r, 100);
    }

    void setup(String title) {
        ScrollView sc = new ScrollView(this);
        sc.setFillViewport(true);
        sc.setBackgroundColor(bg);

        page = new LinearLayout(this);
        page.setOrientation(1);
        page.setPadding(16, 12, 16, 25);
        page.setBackgroundColor(bg);
        sc.addView(page);
        setContentView(sc);

        LinearLayout head = new LinearLayout(this);
        head.setGravity(Gravity.CENTER_VERTICAL);
        head.setPadding(4, 4, 4, 8);

        TextView mark = label("FBA", 26, cyan);
        mark.setTypeface(null, Typeface.BOLD);
        head.addView(mark, new LinearLayout.LayoutParams(70, 48));

        TextView titleView = label(title, 18, white);
        titleView.setTypeface(null, Typeface.BOLD);
        head.addView(titleView, new LinearLayout.LayoutParams(0, 48, 1));
        page.addView(head);

        View line = new View(this);
        line.setBackgroundColor(Color.rgb(20, 64, 100));
        page.addView(line, new LinearLayout.LayoutParams(-1, 1));

        body = page;
    }

    void space(int h) {
        View v = new View(this);
        body.addView(v, new LinearLayout.LayoutParams(1, h));
    }

    void heading(String s) {
        TextView t = label(s, 22, white);
        t.setTypeface(null, Typeface.BOLD);
        body.addView(t);
    }

    void note(String s) {
        body.addView(label(s, 13, muted));
    }

    void action(String title, String detail, String symbol,
                Runnable run) {
        LinearLayout card = new LinearLayout(this);
        card.setOrientation(0);
        card.setGravity(Gravity.CENTER_VERTICAL);
        card.setPadding(12, 9, 12, 9);
        card.setBackground(shape(panel, Color.rgb(14, 68, 105)));

        TextView ico = label(symbol, 25, cyan);
        ico.setGravity(Gravity.CENTER);
        card.addView(ico, new LinearLayout.LayoutParams(48, 52));

        LinearLayout texts = new LinearLayout(this);
        texts.setOrientation(1);
        TextView name = label(title, 15, white);
        name.setTypeface(null, Typeface.BOLD);
        TextView desc = label(detail, 11, muted);
        texts.addView(name);
        texts.addView(desc);
        card.addView(texts, new LinearLayout.LayoutParams(0, -2, 1));

        TextView arrow = label("›", 27, cyan);
        card.addView(arrow);

        LinearLayout.LayoutParams cp =
                new LinearLayout.LayoutParams(-1, -2);
        cp.setMargins(0, 6, 0, 6);
        body.addView(card, cp);
        card.setMinimumHeight(72);
        card.setClickable(true);
        card.setOnClickListener(v -> run.run());
    }

    void button(String text, Runnable run) {
        TextView b = label(text, 15, white);
        b.setGravity(Gravity.CENTER);
        b.setTypeface(null, Typeface.BOLD);
        b.setBackground(shape(Color.rgb(0, 91, 160), cyan));
        b.setPadding(12, 15, 12, 15);
        b.setMinimumHeight(54);
        LinearLayout.LayoutParams bp =
                new LinearLayout.LayoutParams(-1, -2);
        bp.setMargins(0, 6, 0, 6);
        body.addView(b, bp);
        b.setOnClickListener(v -> run.run());
    }

    EditText input(String hint) {
        EditText e = new EditText(this);
        e.setSingleLine(true);
        e.setTextColor(white);
        e.setHintTextColor(muted);
        e.setHint(hint);
        e.setTextSize(14);
        e.setPadding(14, 8, 14, 8);
        e.setBackground(shape(panel, Color.rgb(0, 110, 180)));
        LinearLayout.LayoutParams ip =
                new LinearLayout.LayoutParams(-1, 54);
        ip.setMargins(0, 5, 0, 12);
        body.addView(e, ip);
        return e;
    }

    void home() {
        setup("FBA Builder v4.7");
        space(12);
        TextView hero = label("Build Your App.", 27, cyan);
        hero.setTypeface(null, Typeface.BOLD);
        body.addView(hero);
        note("FREE BUILD ANDROID  •  CYBER WORKSPACE");
        space(14);

        LinearLayout banner = new LinearLayout(this);
        banner.setOrientation(1);
        banner.setPadding(16, 18, 16, 18);
        banner.setBackground(shape(panel, cyan));
        TextView logo = label("◈  FBA", 34, cyan);
        logo.setTypeface(null, Typeface.BOLD);
        logo.setGravity(Gravity.CENTER);
        banner.addView(logo);
        TextView slogan = label(
                "Dari Ide Menjadi Aplikasi\nDalam Genggamanmu",
                14, white);
        slogan.setGravity(Gravity.CENTER);
        banner.addView(slogan);
        body.addView(banner);

        space(14);
        heading("WORKSPACE");
        action("Buat APK", "Bangun aplikasi dari proyekmu", "✚",
                () -> create());
        action("Proyek Saya", "Kelola proyek aplikasi", "▣",
                () -> projectPage());
        action("Build", "Lihat status proses build", "⚡",
                () -> buildPage());
        action("Pengaturan", "Preferensi aplikasi", "⚙",
                () -> settings());
        action("Tentang FBA", "Informasi versi aplikasi", "ⓘ",
                () -> about());

        space(12);
        note("FBA • FREE BUILD ANDROID");
    }

    void create() {
        setup("Buat APK");
        button("←  Kembali ke Beranda", () -> home());
        space(8);
        heading("Detail Aplikasi");
        note("01 / NAMA APLIKASI");
        appName = input("MyApp_Pro");
        note("02 / NAMA DEVELOPER");
        developer = input("Nama developer");
        note("03 / IKON APLIKASI");

        iconText = label("◈  Ikon default FBA", 16, cyan);
        iconText.setGravity(Gravity.CENTER);
        iconText.setBackground(shape(panel, cyan));
        body.addView(iconText, new LinearLayout.LayoutParams(-1, 100));

        button("Pilih Foto Ikon", () -> pickIcon());
        note("04 / SUMBER PROYEK");
        action("Unggah ZIP source code",
                "Pilih file ZIP proyek Android", "▤",
                () -> pickZip());

        button("⚡  Bangun APK", () -> {
            String n = appName.getText().toString().trim();
            if (n.isEmpty()) {
                appName.setError("Isi nama aplikasi");
                return;
            }
            if (!projects.contains(n)) projects.add(n);
            buildPage();
        });

        note("Build sungguhan membutuhkan source code dan mesin build yang terhubung.");
    }

    void pickIcon() {
        Intent i = new Intent(Intent.ACTION_GET_CONTENT);
        i.setType("image/*");
        i.addCategory(Intent.CATEGORY_OPENABLE);
        startActivityForResult(i, 101);
    }

    void pickZip() {
        Intent i = new Intent(Intent.ACTION_GET_CONTENT);
        i.setType("application/zip");
        i.addCategory(Intent.CATEGORY_OPENABLE);
        startActivityForResult(i, 102);
    }

    @Override
    protected void onActivityResult(int req, int res, Intent data) {
        super.onActivityResult(req, res, data);
        if (res != RESULT_OK || data == null) return;
        Uri uri = data.getData();
        if (uri == null) return;

        if (req == 101) {
            chosenIcon = uri;
            if (iconText != null) iconText.setText("✓  Foto ikon dipilih");
            Toast.makeText(this, "Foto ikon dipilih", Toast.LENGTH_SHORT).show();
        } else if (req == 102) {
            Toast.makeText(this,
                    "ZIP dipilih. Mesin build belum terhubung.",
                    Toast.LENGTH_LONG).show();
        }
    }

    void projectPage() {
        setup("Proyek Saya");
        button("←  Kembali", () -> home());
        space(8);
        heading("Semua Proyek");
        if (projects.isEmpty()) {
            note("Belum ada proyek. Tekan Buat APK untuk memulai.");
        } else {
            for (String n : new ArrayList<>(projects)) {
                action(n, "Draft • Belum dibuild", "▣",
                        () -> projectDetail(n));
            }
        }
        button("+  Buat Proyek", () -> create());
    }

    void projectDetail(String name) {
        setup("Detail Proyek");
        button("←  Kembali", () -> projectPage());
        heading(name);
        note("Status: Draft");
        note("Belum ada file APK yang dihasilkan.");
        button("Lihat Build", () -> buildPage());
        button("Hapus Proyek", () -> {
            projects.remove(name);
            projectPage();
        });
    }

    void buildPage() {
        setup("Proses Build");
        button("←  Kembali ke Beranda", () -> home());
        space(10);
        heading("Status Pembuatan APK");
        note("Proyek belum dikirim ke mesin build.");
        action("1. Validasi proyek", "Menunggu source code", "○",
                () -> message("Pilih source code proyek terlebih dahulu."));
        action("2. Setup lingkungan", "Belum dijalankan", "○",
                () -> message("Mesin build belum terhubung."));
        action("3. Kompilasi Android", "Belum dijalankan", "○",
                () -> message("Belum ada proses kompilasi."));
        action("4. Hasil APK", "Belum tersedia", "○",
                () -> message("APK belum dihasilkan."));
        note("Status ini bukan progres build nyata.");
    }

    void settings() {
        setup("Pengaturan");
        button("←  Kembali", () -> home());
        heading("Preferensi");
        action("Bahasa", "Indonesia", "文",
                () -> message("Bahasa Indonesia aktif."));
        action("Tema", "Dark Cyber Blue", "◐",
                () -> message("Tema biru gelap sedang digunakan."));
        action("Notifikasi", "Pengaturan notifikasi", "♧",
                () -> message("Pengaturan notifikasi belum tersedia."));
        action("Keamanan", "Pengaturan keamanan", "♙",
                () -> message("Fitur keamanan lanjutan belum tersedia."));
        action("Data & Penyimpanan", "Kelola data aplikasi", "▤",
                () -> message("Penyimpanan proyek permanen belum dibuat."));
        action("Tentang", "FBA Builder v4.7", "ⓘ",
                () -> about());
    }

    void about() {
        setup("Tentang FBA");
        TextView logo = label("◈ FBA", 40, cyan);
        logo.setGravity(Gravity.CENTER);
        body.addView(logo);
        heading("FREE BUILD ANDROID");
        note("Antarmuka awal FBA Builder.");
        note("Login tidak digunakan. Aplikasi langsung membuka beranda.");
        note("Fitur build APK nyata masih perlu integrasi mesin build.");
        button("Kembali ke Beranda", () -> home());
    }

    void message(String s) {
        Toast.makeText(this, s, Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onDestroy() {
        handler.removeCallbacksAndMessages(null);
        super.onDestroy();
    }
}
