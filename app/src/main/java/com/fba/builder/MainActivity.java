package com.fba.builder;

import android.app.*;
import android.os.*;
import android.content.*;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.view.*;
import android.widget.*;
import java.util.*;
import org.json.*;

public class MainActivity extends Activity {
    final int BG = Color.rgb(5,11,24);
    final int CARD = Color.rgb(11,21,39);
    final int BLUE = Color.rgb(8,124,240);
    final int CYAN = Color.rgb(34,200,255);
    final int WHITE = Color.rgb(239,246,255);
    final int MUTED = Color.rgb(145,169,201);
    final int LINE = Color.rgb(29,49,77);

    LinearLayout root, body, nav;
    EditText nameInput, codeInput;
    TextView iconPreview, zipLabel, status;
    Uri iconUri, zipUri;
    String selectedTab = "Create App";
    ArrayList<String> apps = new ArrayList<>();
    android.content.SharedPreferences prefs;

    static final int PICK_ICON = 101;
    static final int PICK_ZIP = 102;

    @Override
    public void onCreate(Bundle b) {
        super.onCreate(b);
        getWindow().setStatusBarColor(BG);
        getWindow().setNavigationBarColor(BG);
        getWindow().getDecorView().setSystemUiVisibility(0);
        prefs = getSharedPreferences("fba_blue", MODE_PRIVATE);
        loadApps();
        splash();
    }

    GradientDrawable shape(int color, int stroke, int radius) {
        GradientDrawable d = new GradientDrawable();
        d.setColor(color);
        d.setCornerRadius(radius);
        if (stroke != 0) d.setStroke(dp(1), stroke);
        return d;
    }

    int dp(float v) {
        return (int)(v * getResources().getDisplayMetrics().density + 0.5f);
    }

    TextView text(String s, int size, int color) {
        TextView t = new TextView(this);
        t.setText(s);
        t.setTextSize(size);
        t.setTextColor(color);
        t.setGravity(Gravity.CENTER_VERTICAL);
        return t;
    }

    LinearLayout column() {
        LinearLayout l = new LinearLayout(this);
        l.setOrientation(LinearLayout.VERTICAL);
        return l;
    }

    LinearLayout row() {
        LinearLayout l = new LinearLayout(this);
        l.setOrientation(LinearLayout.HORIZONTAL);
        l.setGravity(Gravity.CENTER_VERTICAL);
        return l;
    }

    LinearLayout.LayoutParams lp(int w, int h) {
        return new LinearLayout.LayoutParams(w < 0 ? w : dp(w),
                h < 0 ? h : dp(h));
    }

    void pad(View v, int l, int t, int r, int b) {
        v.setPadding(dp(l), dp(t), dp(r), dp(b));
    }

    TextView button(String label, boolean primary) {
        TextView b = text(label, 14, primary ? Color.WHITE : CYAN);
        b.setGravity(Gravity.CENTER);
        b.setTypeface(null, Typeface.BOLD);
        b.setPadding(dp(14), dp(13), dp(14), dp(13));
        b.setBackground(shape(primary ? BLUE : CARD,
                primary ? BLUE : LINE, 14));
        b.setClickable(true);
        b.setFocusable(true);
        return b;
    }

    TextView label(String s) {
        TextView t = text(s, 13, MUTED);
        LinearLayout.LayoutParams p = lp(-1, -2);
        p.setMargins(0, dp(14), 0, dp(7));
        return t;
    }

    EditText edit(String hint, boolean multi) {
        EditText e = new EditText(this);
        e.setTextColor(WHITE);
        e.setHintTextColor(MUTED);
        e.setTextSize(14);
        e.setSingleLine(!multi);
        e.setHint(hint);
        e.setPadding(dp(14), dp(12), dp(14), dp(12));
        e.setBackground(shape(Color.rgb(5,13,28), LINE, 12));
        if (multi) {
            e.setGravity(Gravity.TOP | Gravity.START);
            e.setMinLines(7);
        }
        return e;
    }

    void splash() {
        LinearLayout s = column();
        s.setGravity(Gravity.CENTER);
        s.setBackgroundColor(BG);
        pad(s, 28, 28, 28, 28);
        setContentView(s);

        TextView logo = text("FBA", 58, CYAN);
        logo.setGravity(Gravity.CENTER);
        logo.setTypeface(null, Typeface.BOLD);
        s.addView(logo, lp(-1, 85));

        TextView subtitle = text("FREE BUILD ANDROID", 15, WHITE);
        subtitle.setGravity(Gravity.CENTER);
        s.addView(subtitle, lp(-1, 30));

        TextView desc = text("Your apps. Your creativity.", 12, MUTED);
        desc.setGravity(Gravity.CENTER);
        s.addView(desc, lp(-1, 32));

        ProgressBar progress = new ProgressBar(this, null,
                android.R.attr.progressBarStyleHorizontal);
        progress.setMax(100);
        progress.setProgressTintList(ColorStateList.valueOf(CYAN));
        progress.setProgressBackgroundTintList(ColorStateList.valueOf(LINE));
        LinearLayout.LayoutParams pp = lp(-1, 6);
        pp.setMargins(0, dp(35), 0, dp(12));
        s.addView(progress, pp);

        TextView loading = text("Preparing workspace...", 12, MUTED);
        loading.setGravity(Gravity.CENTER);
        s.addView(loading, lp(-1, 28));

        Handler h = new Handler();
        final int[] value = {0};
        Runnable task = new Runnable() {
            @Override public void run() {
                value[0] += 10;
                progress.setProgress(value[0]);
                loading.setText("Preparing workspace... " + value[0] + "%");
                if (value[0] < 100) h.postDelayed(this, 35);
                else h.postDelayed(() -> showScreen("Create App"), 180);
            }
        };
        h.postDelayed(task, 100);
    }

    void setup(String title, String subtitle) {
        root = column();
        root.setBackgroundColor(BG);
        setContentView(root);

        LinearLayout header = row();
        pad(header, 18, 12, 18, 10);

        LinearLayout brand = column();
        TextView name = text("FBA", 23, CYAN);
        name.setTypeface(null, Typeface.BOLD);
        brand.addView(name);
        TextView sub = text("FREE BUILD ANDROID", 9, MUTED);
        brand.addView(sub);
        header.addView(brand, new LinearLayout.LayoutParams(0, -2, 1));

        TextView badge = text("● BLUE EDITION", 10, CYAN);
        badge.setGravity(Gravity.CENTER);
        badge.setPadding(dp(10), dp(8), dp(10), dp(8));
        badge.setBackground(shape(CARD, LINE, 20));
        header.addView(badge);
        root.addView(header);

        View divider = new View(this);
        divider.setBackgroundColor(LINE);
        root.addView(divider, lp(-1, 1));

        ScrollView scroll = new ScrollView(this);
        scroll.setFillViewport(true);
        scroll.setClipToPadding(false);
        body = column();
        pad(body, 18, 18, 18, 22);
        scroll.addView(body);
        root.addView(scroll, new LinearLayout.LayoutParams(-1, 0, 1));

        nav = row();
        nav.setBackgroundColor(CARD);
        nav.setElevation(dp(8));
        addNavItem("⌂", "Home", title.equals("Create App") ||
                title.equals("My Apps"));
        addNavItem("⚙", "Settings", title.equals("Settings"));
        root.addView(nav, lp(-1, 62));
    }

    void addNavItem(String symbol, String title, boolean active) {
        LinearLayout item = column();
        item.setGravity(Gravity.CENTER);
        TextView a = text(symbol, 21, active ? CYAN : MUTED);
        a.setGravity(Gravity.CENTER);
        TextView b = text(title, 11, active ? CYAN : MUTED);
        b.setGravity(Gravity.CENTER);
        item.addView(a);
        item.addView(b);
        nav.addView(item, new LinearLayout.LayoutParams(0, -1, 1));
        item.setOnClickListener(v -> {
            if (title.equals("Home")) showScreen("Create App");
            else showScreen("Settings");
        });
    }

    void heading(String title, String subtitle) {
        TextView t = text(title, 24, WHITE);
        t.setTypeface(null, Typeface.BOLD);
        body.addView(t);
        TextView s = text(subtitle, 12, MUTED);
        LinearLayout.LayoutParams p = lp(-1, -2);
        p.setMargins(0, dp(5), 0, dp(17));
        body.addView(s, p);
    }

    LinearLayout card() {
        LinearLayout c = column();
        pad(c, 15, 15, 15, 15);
        c.setBackground(shape(CARD, LINE, 16));
        LinearLayout.LayoutParams p = lp(-1, -2);
        p.setMargins(0, 0, 0, dp(12));
        body.addView(c, p);
        return c;
    }

    void showScreen(String screen) {
        selectedTab = screen;
        if (screen.equals("Settings")) {
            showSettings();
        } else if (screen.equals("My Apps")) {
            showApps();
        } else {
            showCreate();
        }
    }

    void showCreate() {
        setup("Create App", "");
        heading("Create your app", "Build your next idea with FBA.");

        LinearLayout info = card();
        TextView small = text("●  WORKSPACE READY", 11, CYAN);
        info.addView(small);
        TextView desc = text("Prepare your app name, icon, ZIP or source code.",
                12, MUTED);
        LinearLayout.LayoutParams dp1 = lp(-1, -2);
        dp1.setMargins(0, dp(7), 0, 0);
        info.addView(desc, dp1);

        LinearLayout form = card();
        form.addView(label("APP NAME"));
        nameInput = edit("Enter your application name", false);
        form.addView(nameInput, lp(-1, 48));

        form.addView(label("APPLICATION ICON"));
        LinearLayout iconRow = row();
        iconPreview = text("＋", 30, CYAN);
        iconPreview.setGravity(Gravity.CENTER);
        iconPreview.setBackground(shape(Color.rgb(5,13,28), LINE, 14));
        iconRow.addView(iconPreview, lp(68, 68));

        LinearLayout iconButtons = column();
        pad(iconButtons, 12, 0, 0, 0);
        TextView chooseIcon = button("Choose Icon", false);
        iconButtons.addView(chooseIcon, lp(-1, 44));
        TextView iconHint = text("PNG / JPG / WebP", 11, MUTED);
        iconButtons.addView(iconHint, lp(-1, 28));
        iconRow.addView(iconButtons, new LinearLayout.LayoutParams(0, -2, 1));
        form.addView(iconRow);
        chooseIcon.setOnClickListener(v -> pickFile("image/*", PICK_ICON));

        form.addView(label("PROJECT SOURCE"));
        zipLabel = text("No ZIP file selected", 12, MUTED);
        zipLabel.setPadding(dp(12), dp(12), dp(12), dp(12));
        zipLabel.setBackground(shape(Color.rgb(5,13,28), LINE, 12));
        form.addView(zipLabel, lp(-1, -2));

        TextView chooseZip = button("＋  Select ZIP File", false);
        LinearLayout.LayoutParams zp = lp(-1, 46);
        zp.setMargins(0, dp(9), 0, 0);
        form.addView(chooseZip, zp);
        chooseZip.setOnClickListener(v -> pickFile(
                "application/zip", PICK_ZIP));

        form.addView(label("OR WRITE CODE MANUALLY"));
        codeInput = edit("Paste or write your HTML, CSS or JavaScript here...", true);
        codeInput.setTypeface(Typeface.MONOSPACE);
        codeInput.setTextSize(12);
        form.addView(codeInput, lp(-1, 190));

        TextView note = text("ZIP or manual code can be used as the project source.",
                11, MUTED);
        LinearLayout.LayoutParams np = lp(-1, -2);
        np.setMargins(0, dp(9), 0, 0);
        form.addView(note, np);

        TextView build = button("⚒   Build Application", true);
        LinearLayout.LayoutParams bp = lp(-1, 52);
        bp.setMargins(0, dp(5), 0, dp(10));
        body.addView(build, bp);
        build.setOnClickListener(v -> saveProject());

        TextView myApps = button("View My Apps  →", false);
        body.addView(myApps, lp(-1, 46));
        myApps.setOnClickListener(v -> showScreen("My Apps"));
    }

    void pickFile(String type, int request) {
        try {
            Intent i = new Intent(Intent.ACTION_OPEN_DOCUMENT);
            i.addCategory(Intent.CATEGORY_OPENABLE);
            i.setType(type);
            i.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            startActivityForResult(i, request);
        } catch (Exception e) {
            toast("Unable to open file picker.");
        }
    }

    @Override
    protected void onActivityResult(int request, int result, Intent data) {
        super.onActivityResult(request, result, data);
        if (result != RESULT_OK || data == null || data.getData() == null) return;

        Uri uri = data.getData();
        try {
            getContentResolver().takePersistableUriPermission(uri,
                    Intent.FLAG_GRANT_READ_URI_PERMISSION);
        } catch (Exception ignored) {}

        if (request == PICK_ICON) {
            iconUri = uri;
            if (iconPreview != null) {
                try {
                    iconPreview.setText("");
                    iconPreview.setBackground(shape(Color.rgb(5,13,28), LINE, 14));
                    iconPreview.setCompoundDrawables(null, null, null, null);
                    android.graphics.Bitmap bitmap =
                            android.provider.MediaStore.Images.Media.getBitmap(
                                    getContentResolver(), uri);
                    android.graphics.drawable.BitmapDrawable drawable =
                            new android.graphics.drawable.BitmapDrawable(
                                    getResources(), bitmap);
                    iconPreview.setBackground(drawable);
                } catch (Exception e) {
                    iconPreview.setText("✓");
                    toast("Icon selected.");
                }
            }
            toast("Icon selected.");
        } else if (request == PICK_ZIP) {
            zipUri = uri;
            String file = uri.getLastPathSegment();
            if (file == null) file = "ZIP file selected";
            if (zipLabel != null) zipLabel.setText("✓  " + file);
            toast("ZIP file selected.");
        }
    }

    void saveProject() {
        String n = nameInput == null ? "" : nameInput.getText().toString().trim();
        String code = codeInput == null ? "" : codeInput.getText().toString();

        if (n.isEmpty()) {
            toast("Enter an app name first.");
            return;
        }
        if (zipUri == null && code.trim().isEmpty()) {
            toast("Select a ZIP file or enter code first.");
            return;
        }

        try {
            JSONObject project = new JSONObject();
            project.put("name", n);
            project.put("source", zipUri != null ? "ZIP project" : "Manual code");
            project.put("icon", iconUri == null ? "" : iconUri.toString());
            project.put("zip", zipUri == null ? "" : zipUri.toString());
            project.put("code", code);
            project.put("status", "Project saved");
            apps.add(project.toString());
            saveApps();
            new AlertDialog.Builder(this)
                    .setTitle("Project saved")
                    .setMessage("Project \"" + n +
                            "\" has been saved to My Apps.\n\n" +
                            "This prototype does not yet compile the source into an APK. " +
                            "The real Android build engine still needs to be connected.")
                    .setPositiveButton("View My Apps", (d, w) -> showApps())
                    .setNegativeButton("Continue editing", (d, w) -> {})
                    .show();
        } catch (Exception e) {
            toast("Could not save project.");
        }
    }

    void showApps() {
        setup("My Apps", "");
        heading("My Apps", "Your saved FBA projects.");

        TextView create = button("＋  Create New App", true);
        LinearLayout.LayoutParams cp = lp(-1, 48);
        cp.setMargins(0, 0, 0, dp(16));
        body.addView(create, cp);
        create.setOnClickListener(v -> showCreate());

        if (apps.isEmpty()) {
            LinearLayout empty = card();
            pad(empty, 18, 32, 18, 32);
            TextView symbol = text("▱", 45, CYAN);
            symbol.setGravity(Gravity.CENTER);
            empty.addView(symbol, lp(-1, 64));
            TextView title = text("No apps yet", 18, WHITE);
            title.setGravity(Gravity.CENTER);
            title.setTypeface(null, Typeface.BOLD);
            empty.addView(title, lp(-1, 35));
            TextView desc = text("Your saved projects will appear here.",
                    12, MUTED);
            desc.setGravity(Gravity.CENTER);
            empty.addView(desc, lp(-1, 32));
            return;
        }

        for (int i = apps.size() - 1; i >= 0; i--) {
            final int index = i;
            try {
                JSONObject project = new JSONObject(apps.get(i));
                LinearLayout c = card();
                LinearLayout r = row();

                TextView appIcon = text("F", 22, CYAN);
                appIcon.setGravity(Gravity.CENTER);
                appIcon.setTypeface(null, Typeface.BOLD);
                appIcon.setBackground(shape(Color.rgb(5,31,58), BLUE, 13));
                r.addView(appIcon, lp(52, 52));

                LinearLayout info = column();
                pad(info, 12, 0, 0, 0);
                TextView appTitle = text(project.optString("name"), 15, WHITE);
                appTitle.setTypeface(null, Typeface.BOLD);
                info.addView(appTitle);
                TextView source = text(project.optString("source"), 11, MUTED);
                info.addView(source);
                TextView st = text("●  " + project.optString("status"),
                        10, CYAN);
                info.addView(st);
                r.addView(info, new LinearLayout.LayoutParams(0, -2, 1));
                c.addView(r);

                LinearLayout actions = row();
                TextView details = button("Details", false);
                TextView delete = button("Delete", false);
                LinearLayout.LayoutParams ap = new LinearLayout.LayoutParams(
                        0, dp(40), 1);
                ap.setMargins(0, dp(12), dp(5), 0);
                actions.addView(details, ap);
                LinearLayout.LayoutParams dp2 = new LinearLayout.LayoutParams(
                        0, dp(40), 1);
                dp2.setMargins(dp(5), dp(12), 0, 0);
                actions.addView(delete, dp2);
                c.addView(actions);

                details.setOnClickListener(v -> showProjectDetails(index));
                delete.setOnClickListener(v ->
                        new AlertDialog.Builder(this)
                                .setTitle("Delete project?")
                                .setMessage("This removes the saved project from My Apps.")
                                .setNegativeButton("Cancel", null)
                                .setPositiveButton("Delete", (d, w) -> {
                                    apps.remove(index);
                                    saveApps();
                                    showApps();
                                }).show());
            } catch (Exception ignored) {}
        }
    }

    void showProjectDetails(int index) {
        try {
            JSONObject p = new JSONObject(apps.get(index));
            new AlertDialog.Builder(this)
                    .setTitle(p.optString("name"))
                    .setMessage("Source: " + p.optString("source") +
                            "\nStatus: " + p.optString("status") +
                            "\n\nThis project is saved locally. APK compilation is not connected yet.")
                    .setPositiveButton("OK", null)
                    .show();
        } catch (Exception e) {
            toast("Unable to open project.");
        }
    }

    void showSettings() {
        setup("Settings", "");
        heading("Settings", "Customize your FBA workspace.");

        LinearLayout appearance = card();
        appearance.addView(text("APPEARANCE", 11, CYAN));
        TextView theme = text("Blue Edition", 17, WHITE);
        theme.setTypeface(null, Typeface.BOLD);
        LinearLayout.LayoutParams tp = lp(-1, 38);
        tp.setMargins(0, dp(7), 0, 0);
        appearance.addView(theme, tp);
        appearance.addView(text("Dark navy background with blue accents.",
                12, MUTED));

        LinearLayout about = card();
        about.addView(text("ABOUT FBA", 11, CYAN));
        TextView version = text("Free Build Android", 17, WHITE);
        version.setTypeface(null, Typeface.BOLD);
        LinearLayout.LayoutParams vp = lp(-1, 38);
        vp.setMargins(0, dp(7), 0, 0);
        about.addView(version, vp);
        about.addView(text("Version 1.0 • Blue Edition", 12, MUTED));

        TextView clear = button("Clear saved projects", false);
        LinearLayout.LayoutParams clp = lp(-1, 48);
        clp.setMargins(0, dp(3), 0, dp(12));
        body.addView(clear, clp);
        clear.setOnClickListener(v ->
                new AlertDialog.Builder(this)
                        .setTitle("Clear all projects?")
                        .setMessage("Saved projects will be removed from this device.")
                        .setNegativeButton("Cancel", null)
                        .setPositiveButton("Clear", (d, w) -> {
                            apps.clear();
                            saveApps();
                            toast("Saved projects cleared.");
                            showSettings();
                        }).show());

        TextView help = text(
                "Note: selecting a ZIP or entering code saves a project. " +
                "A real APK build engine must be connected before FBA can generate installable APK files.",
                12, MUTED);
        pad(help, 4, 8, 4, 8);
        body.addView(help);
    }

    void loadApps() {
        apps.clear();
        String raw = prefs.getString("projects", "[]");
        try {
            JSONArray a = new JSONArray(raw);
            for (int i = 0; i < a.length(); i++) {
                apps.add(a.getString(i));
            }
        } catch (Exception ignored) {}
    }

    void saveApps() {
        JSONArray a = new JSONArray();
        for (String item : apps) a.put(item);
        prefs.edit().putString("projects", a.toString()).apply();
    }

    void toast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
