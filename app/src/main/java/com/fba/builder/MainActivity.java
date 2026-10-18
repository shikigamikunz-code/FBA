package com.fba.builder;

import android.app.*;
import android.os.*;
import android.content.*;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.view.*;
import android.widget.*;
import java.util.ArrayList;

public class MainActivity extends Activity {
    final int BG = Color.rgb(4, 9, 20);
    final int CARD = Color.rgb(10, 18, 34);
    final int CYAN = Color.rgb(0, 210, 220);
    final int BLUE = Color.rgb(0, 145, 230);
    final int YELLOW = Color.rgb(225, 197, 20);
    final int WHITE = Color.rgb(235, 241, 250);
    final int MUTED = Color.rgb(130, 145, 170);

    LinearLayout root, content, nav;
    EditText nameInput, codeInput;
    TextView iconPreview, status;
    Uri iconUri, zipUri;
    String appName = "";
    final ArrayList<String> apps = new ArrayList<>();
    final Handler handler = new Handler();
    int screen = 0;

    @Override
    public void onCreate(Bundle b) {
        super.onCreate(b);
        getWindow().setStatusBarColor(BG);
        getWindow().setNavigationBarColor(BG);
        splash();
    }

    GradientDrawable shape(int color, int stroke, int radius) {
        GradientDrawable d = new GradientDrawable();
        d.setColor(color);
        d.setCornerRadius(radius);
        if (stroke != 0) d.setStroke(1, stroke);
        return d;
    }

    TextView text(String s, int size, int color) {
        TextView t = new TextView(this);
        t.setText(s);
        t.setTextSize(size);
        t.setTextColor(color);
        t.setGravity(Gravity.CENTER_VERTICAL);
        return t;
    }

    void splash() {
        LinearLayout l = new LinearLayout(this);
        l.setOrientation(1);
        l.setGravity(Gravity.CENTER);
        l.setPadding(28, 28, 28, 28);
        l.setBackgroundColor(BG);
        setContentView(l);

        TextView logo = text("FBA", 60, CYAN);
        logo.setTypeface(null, Typeface.BOLD);
        logo.setGravity(Gravity.CENTER);
        l.addView(logo);

        TextView sub = text("FREE BUILD ANDROID", 16, WHITE);
        sub.setGravity(Gravity.CENTER);
        l.addView(sub);

        ProgressBar p = new ProgressBar(this, null,
                android.R.attr.progressBarStyleHorizontal);
        p.setMax(100);
        p.setProgressTintList(
                android.content.res.ColorStateList.valueOf(CYAN));
        LinearLayout.LayoutParams pp =
                new LinearLayout.LayoutParams(-1, 8);
        pp.setMargins(0, 30, 0, 0);
        l.addView(p, pp);

        TextView loading = text("Loading FBA...", 13, MUTED);
        loading.setGravity(Gravity.CENTER);
        l.addView(loading);

        final int[] value = {0};
        Runnable r = new Runnable() {
            public void run() {
                value[0] += 10;
                p.setProgress(value[0]);
                loading.setText("Loading FBA... " + value[0] + "%");
                if (value[0] < 100) {
                    handler.postDelayed(this, 60);
                } else {
                    handler.postDelayed(() -> showScreen(0), 250);
                }
            }
        };
        handler.postDelayed(r, 100);
    }

    void base(String title) {
        root = new LinearLayout(this);
        root.setOrientation(1);
        root.setBackgroundColor(BG);
        setContentView(root);

        LinearLayout header = new LinearLayout(this);
        header.setGravity(Gravity.CENTER_VERTICAL);
        header.setPadding(18, 10, 18, 10);
        TextView mark = text("♟ FBA", 23, CYAN);
        mark.setTypeface(null, Typeface.BOLD);
        header.addView(mark, new LinearLayout.LayoutParams(105, 48));

        TextView heading = text(title, 17, WHITE);
        heading.setTypeface(null, Typeface.BOLD);
        header.addView(heading);
        root.addView(header);

        View line = new View(this);
        line.setBackgroundColor(Color.rgb(24, 44, 65));
        root.addView(line, new LinearLayout.LayoutParams(-1, 1));

        ScrollView scroll = new ScrollView(this);
        scroll.setFillViewport(true);
        root.addView(scroll, new LinearLayout.LayoutParams(-1, 0, 1));

        content = new LinearLayout(this);
        content.setOrientation(1);
        content.setPadding(16, 16, 16, 22);
        scroll.addView(content);

        nav = new LinearLayout(this);
        nav.setPadding(8, 6, 8, 6);
        nav.setBackgroundColor(Color.rgb(6, 13, 27));
        root.addView(nav, new LinearLayout.LayoutParams(-1, 60));

        navButton("⌂\nHome", 0);
        navButton("⚙\nSettings", 3);
    }

    void navButton(String title, int target) {
        TextView b = text(title, 12, screen == target ? CYAN : MUTED);
        b.setGravity(Gravity.CENTER);
        nav.addView(b, new LinearLayout.LayoutParams(0, -1, 1));
        b.setOnClickListener(v -> showScreen(target));
    }

    void gap(int h) {
        View v = new View(this);
        content.addView(v, new LinearLayout.LayoutParams(1, h));
    }

    void title(String s) {
        TextView t = text(s, 22, WHITE);
        t.setTypeface(null, Typeface.BOLD);
        content.addView(t);
    }

    void caption(String s) {
        TextView t = text(s, 13, YELLOW);
        content.addView(t);
        gap(5);
    }

    EditText field(String hint) {
        EditText e = new EditText(this);
        e.setSingleLine(true);
        e.setTextSize(14);
        e.setTextColor(WHITE);
        e.setHintTextColor(MUTED);
        e.setHint(hint);
        e.setPadding(14, 5, 14, 5);
        e.setBackground(shape(Color.rgb(5, 12, 25),
                Color.rgb(25, 52, 76), 14));
        LinearLayout.LayoutParams lp =
                new LinearLayout.LayoutParams(-1, 52);
        lp.setMargins(0, 0, 0, 18);
        content.addView(e, lp);
        return e;
    }

    void button(String label, Runnable action) {
        TextView b = text(label, 15, WHITE);
        b.setGravity(Gravity.CENTER);
        b.setTypeface(null, Typeface.BOLD);
        b.setBackground(shape(
                Color.rgb(0, 130, 190), CYAN, 14));
        LinearLayout.LayoutParams lp =
                new LinearLayout.LayoutParams(-1, 54);
        lp.setMargins(0, 7, 0, 7);
        content.addView(b, lp);
        b.setOnClickListener(v -> action.run());
    }

    void card(String symbol, String title, String subtitle,
              Runnable action) {
        LinearLayout row = new LinearLayout(this);
        row.setGravity(Gravity.CENTER_VERTICAL);
        row.setPadding(12, 10, 12, 10);
        row.setBackground(shape(CARD, Color.rgb(21, 54, 78), 14));

        TextView ico = text(symbol, 24, CYAN);
        ico.setGravity(Gravity.CENTER);
        row.addView(ico, new LinearLayout.LayoutParams(45, 48));

        LinearLayout labels = new LinearLayout(this);
        labels.setOrientation(1);
        TextView a = text(title, 15, WHITE);
        a.setTypeface(null, Typeface.BOLD);
        TextView b = text(subtitle, 11, MUTED);
        labels.addView(a);
        labels.addView(b);
        row.addView(labels, new LinearLayout.LayoutParams(0, -2, 1));

        TextView arrow = text("›", 25, CYAN);
        row.addView(arrow);

        LinearLayout.LayoutParams lp =
                new LinearLayout.LayoutParams(-1, -2);
        lp.setMargins(0, 5, 0, 5);
        content.addView(row, lp);
        row.setMinimumHeight(70);
        row.setOnClickListener(v -> action.run());
    }

    void showScreen(int target) {
        screen = target;
        if (target == 0) home();
        else if (target == 1) createApp();
        else if (target == 2) myApps();
        else if (target == 3) settings();
        else if (target == 4) buildStatus();
    }

    void home() {
        base("Builder");
        gap(8);
        title("Create Something.");
        TextView sub = text("Your Android workspace starts here.", 14, MUTED);
        content.addView(sub);
        gap(15);

        LinearLayout banner = new LinearLayout(this);
        banner.setOrientation(1);
        banner.setPadding(16, 17, 16, 17);
        banner.setBackground(shape(
                Color.rgb(8, 25, 42), CYAN, 18));
        TextView logo = text("◈  FBA", 32, CYAN);
        logo.setTypeface(null, Typeface.BOLD);
        logo.setGravity(Gravity.CENTER);
        banner.addView(logo);
        TextView tagline = text(
                "FREE BUILD ANDROID  •  YOUR APP WORKSPACE",
                11, WHITE);
        tagline.setGravity(Gravity.CENTER);
        banner.addView(tagline);
        content.addView(banner);

        gap(22);
        title("WORKSPACE");
        gap(8);
        card("⊕", "Create App", "Buat proyek aplikasi baru",
                () -> showScreen(1));
        card("▦", "My Apps", "Lihat proyek yang tersimpan",
                () -> showScreen(2));
        card("⚡", "Build Status", "Periksa status build APK",
                () -> showScreen(4));
        card("⚙", "Settings", "Pengaturan aplikasi",
                () -> showScreen(3));
        gap(14);
        TextView footer = text("FBA • FREE BUILD ANDROID", 12, MUTED);
        footer.setGravity(Gravity.CENTER);
        content.addView(footer);
    }

    void createApp() {
        base("Create App");
        gap(6);

        LinearLayout tabs = new LinearLayout(this);
        tabs.setBackground(shape(CARD, Color.rgb(24, 44, 65), 14));
        TextView create = text("⊕  Create App", 14, WHITE);
        create.setGravity(Gravity.CENTER);
        create.setBackground(shape(
                Color.rgb(0, 135, 195), CYAN, 12));
        tabs.addView(create, new LinearLayout.LayoutParams(0, 48, 1));
        TextView appsTab = text("▦  My Apps", 14, MUTED);
        appsTab.setGravity(Gravity.CENTER);
        tabs.addView(appsTab, new LinearLayout.LayoutParams(0, 48, 1));
        content.addView(tabs);
        appsTab.setOnClickListener(v -> showScreen(2));

        LinearLayout form = new LinearLayout(this);
        form.setOrientation(1);
        form.setPadding(14, 15, 14, 15);
        form.setBackground(shape(CARD, Color.rgb(21, 37, 57), 20));
        content.addView(form);

        gap(12);
        caption("APP NAME");
        nameInput = field("Nama aplikasi buatanmu...");
        content.removeView(nameInput);
        form.addView(nameInput);

        caption("APP ICON (CUSTOM)");
        iconPreview = text("▧   Pilih ikon aplikasi", 14, MUTED);
        iconPreview.setGravity(Gravity.CENTER);
        iconPreview.setBackground(shape(
                Color.rgb(5, 15, 29), CYAN, 14));
        form.addView(iconPreview, new LinearLayout.LayoutParams(-1, 75));
        TextView chooseIcon = text("＋  Choose / Crop Photo", 14, CYAN);
        chooseIcon.setGravity(Gravity.CENTER);
        form.addView(chooseIcon, new LinearLayout.LayoutParams(-1, 48));
        chooseIcon.setOnClickListener(v -> pick(101));

        gap(14);
        caption("SELECT ZIP FILE");
        TextView zip = text("▤  Choose App ZIP File...", 14, CYAN);
        zip.setGravity(Gravity.CENTER);
        zip.setBackground(shape(
                Color.rgb(5, 15, 29), CYAN, 14));
        form.addView(zip, new LinearLayout.LayoutParams(-1, 55));
        zip.setOnClickListener(v -> pick(102));

        gap(14);
        caption("MANUAL CODING (HTML / JS)");
        codeInput = new EditText(this);
        codeInput.setTextColor(WHITE);
        codeInput.setHintTextColor(MUTED);
        codeInput.setHint("Masukkan kode HTML/JS untuk aplikasimu...");
        codeInput.setGravity(Gravity.TOP);
        codeInput.setTextSize(13);
        codeInput.setMinLines(4);
        codeInput.setPadding(12, 12, 12, 12);
        codeInput.setBackground(shape(
                Color.rgb(5, 12, 25), 0, 14));
        form.addView(codeInput, new LinearLayout.LayoutParams(-1, 130));

        gap(14);
        TextView build = text("⚒  Build Application", 16, WHITE);
        build.setGravity(Gravity.CENTER);
        build.setTypeface(null, Typeface.BOLD);
        build.setBackground(shape(
                Color.rgb(0, 160, 180), YELLOW, 15));
        form.addView(build, new LinearLayout.LayoutParams(-1, 58));
        build.setOnClickListener(v -> startBuild());
        gap(10);
        TextView warning = text(
                "Mesin build APK belum terhubung. Proyek ini belum menghasilkan APK.",
                11, MUTED);
        form.addView(warning);
    }

    void pick(int request) {
        Intent i = new Intent(Intent.ACTION_GET_CONTENT);
        i.addCategory(Intent.CATEGORY_OPENABLE);
        i.setType(request == 101 ? "image/*" : "application/zip");
        startActivityForResult(i, request);
    }

    @Override
    protected void onActivityResult(int req, int res, Intent data) {
        super.onActivityResult(req, res, data);
        if (res != RESULT_OK || data == null || data.getData() == null) return;
        Uri uri = data.getData();
        if (req == 101) {
            iconUri = uri;
            iconPreview.setText("✓  Ikon berhasil dipilih");
            Toast.makeText(this,
                    "Foto dipilih. Pemotongan ikon belum tersedia.",
                    Toast.LENGTH_LONG).show();
        } else if (req == 102) {
            zipUri = uri;
            Toast.makeText(this,
                    "ZIP dipilih. Mesin build belum terhubung.",
                    Toast.LENGTH_LONG).show();
        }
    }

    void startBuild() {
        if (nameInput == null ||
                nameInput.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, "Isi nama aplikasi dahulu",
                    Toast.LENGTH_SHORT).show();
            return;
        }
        appName = nameInput.getText().toString().trim();
        if (!apps.contains(appName)) apps.add(appName);
        showScreen(4);
    }

    void myApps() {
        base("My Apps");
        gap(8);
        title("My Applications");
        TextView sub = text("Kelola proyek yang kamu buat.", 13, MUTED);
        content.addView(sub);
        gap(15);

        if (apps.isEmpty()) {
            TextView empty = text("▱", 54, MUTED);
            empty.setGravity(Gravity.CENTER);
            content.addView(empty);
            TextView msg = text("Belum ada aplikasi yang dibuat.", 14, MUTED);
            msg.setGravity(Gravity.CENTER);
            content.addView(msg);
        } else {
            for (String a : new ArrayList<>(apps)) {
                card("▣", a, "Draft • APK belum dibuat",
                        () -> showScreen(4));
            }
        }
        gap(12);
        button("＋  Create App", () -> showScreen(1));
    }

    void buildStatus() {
        base("Build Status");
        gap(8);
        title("Application Build");
        TextView sub = text(
                "Status proyek dan pembuatan APK.", 13, MUTED);
        content.addView(sub);
        gap(16);

        statusCard("1", "Project validation", "Menunggu mesin build");
        statusCard("2", "Setup build environment", "Belum dimulai");
        statusCard("3", "Android compilation", "Belum dimulai");
        statusCard("4", "Generate APK", "Belum tersedia");
        gap(12);
        TextView info = text(
                "Belum ada proses build nyata. Hubungkan proyek ke mesin build sebelum APK dapat dibuat.",
                13, YELLOW);
        content.addView(info);
        gap(12);
        button("←  Back to Home", () -> showScreen(0));
        button("Create App", () -> showScreen(1));
    }

    void statusCard(String n, String title, String detail) {
        card("○", n + ". " + title, detail,
                () -> Toast.makeText(this,
                        "Mesin build belum terhubung.",
                        Toast.LENGTH_SHORT).show());
    }

    void settings() {
        base("Settings");
        gap(8);
        title("Settings");
        TextView sub = text("Atur preferensi aplikasi.", 13, MUTED);
        content.addView(sub);
        gap(15);
        card("文", "Language", "Indonesia", () -> message("Bahasa Indonesia"));
        card("◐", "Theme", "Dark Cyber Blue", () -> message("Tema gelap aktif"));
        card("♧", "Notifications", "Preferensi notifikasi",
                () -> message("Pengaturan notifikasi belum tersedia"));
        card("ⓘ", "About FBA", "Free Build Android",
                () -> message("FBA Builder • versi awal"));
    }

    void message(String s) {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        handler.removeCallbacksAndMessages(null);
        super.onDestroy();
    }
}
