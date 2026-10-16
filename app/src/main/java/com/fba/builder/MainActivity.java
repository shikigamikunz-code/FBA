package com.fba.builder; import android.app.Activity; 
import android.os.Bundle; import android.graphics.Color; 
import android.graphics.Typeface; import 
android.graphics.drawable.GradientDrawable; import 
android.view.Gravity; import android.view.View; import 
android.widget.Button; import android.widget.LinearLayout; 
import android.widget.ScrollView; import 
android.widget.TextView; public class MainActivity extends 
Activity {
    int bg = Color.rgb(5, 12, 28); int cyan = Color.rgb(0, 
    217, 255); int white = Color.WHITE; GradientDrawable 
    shape(int color, int stroke) {
        GradientDrawable d = new GradientDrawable(); 
        d.setColor(color); d.setCornerRadius(24); if 
        (stroke != 0) d.setStroke(2, stroke); return d;
    }
    TextView text(String value, int size, int color) { 
        TextView t = new TextView(this); t.setText(value); 
        t.setTextSize(size); t.setTextColor(color); 
        t.setGravity(Gravity.CENTER); 
        t.setTypeface(Typeface.DEFAULT, Typeface.BOLD); 
        t.setPadding(12, 12, 12, 12); return t;
    }
    Button button(String label) { Button b = new 
        Button(this); b.setText(label); 
        b.setTextColor(white); b.setAllCaps(false); 
        b.setBackground(shape(Color.rgb(12, 31, 56), 
        cyan)); return b;
    }
    @Override protected void onCreate(Bundle state) { 
        super.onCreate(state); 
        getWindow().setStatusBarColor(bg); 
        getWindow().setNavigationBarColor(bg); ScrollView 
        scroll = new ScrollView(this); 
        scroll.setBackgroundColor(bg); LinearLayout root = 
        new LinearLayout(this); 
        root.setOrientation(LinearLayout.VERTICAL); 
        root.setPadding(22, 28, 22, 28); 
        scroll.addView(root); TextView logo = text("FBA", 
        42, cyan); root.addView(logo); root.addView(text(
            "FREE BUILD ANDROID", 14, white )); 
        root.addView(text(
            "Build your Android project", 19, white )); 
        root.addView(text(
            "Create • Build • Install", 13, cyan )); 
        Button create = button("＋ Buat Proyek"); 
        root.addView(create); Button projects = button("▣ 
        Proyek Saya"); root.addView(projects); Button 
        about = button("ⓘ Tentang FBA"); 
        root.addView(about); TextView status = text(
            "FBA ENGINE: READY FOR SETUP", 12, cyan ); 
        root.addView(status); create.setOnClickListener(v 
        -> {
            status.setText( "Proyek Android akan disiapkan 
                melalui build engine."
            );
        });
        projects.setOnClickListener(v -> { status.setText( 
                "Daftar proyek akan tersedia pada tahap 
                berikutnya."
            );
        });
        about.setOnClickListener(v -> { status.setText( 
                "FBA adalah proyek builder Android yang 
                sedang dikembangkan."
            );
        });
        setContentView(scroll);
    }
}
