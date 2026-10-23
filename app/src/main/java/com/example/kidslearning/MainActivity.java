package com.example.kidslearning;

import android.app.Activity;
import android.os.Bundle;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.view.Gravity;
import android.view.View;
import android.widget.*;
import java.util.*;

public class MainActivity extends Activity {
    LinearLayout root, content, header;
    final String[] activities = {"Up & Down","Left & Right","Clockwise & Anti-clockwise","Colouring","Same & Different","In & Out","Near & Far","Open & Closed","Big & Small","Short & Tall","Heavy & Light","Thick & Thin","Long & Short","Number Learning & Tracing"};
    final String[] emojis = {"⬆️⬇️","⬅️➡️","🔄","🎨","🍎","📦","🏡","🚪","🐘","🦒","⚖️","📚","📏","🔢"};
    final int[] colors = {0xFFFF6B8A,0xFF29A9F5,0xFF9B59D0,0xFFFFB52E,0xFF00BFA5,0xFF53B64A,0xFF3F9FEF,0xFFE88A32};
    int page = -1;
    @Override public void onCreate(Bundle b) { super.onCreate(b); getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN|View.SYSTEM_UI_FLAG_HIDE_NAVIGATION|View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY); showHome(); }
    GradientDrawable bg(int color, int radius) { GradientDrawable d = new GradientDrawable(); d.setColor(color); d.setCornerRadius(radius); d.setStroke(3, Color.WHITE); return d; }
    TextView label(String s, int size, int color, boolean bold) { TextView t = new TextView(this); t.setText(s); t.setTextSize(size); t.setTextColor(color); if(bold)t.setTypeface(Typeface.DEFAULT,Typeface.BOLD); t.setGravity(Gravity.CENTER); t.setPadding(8,8,8,8); return t; }
    void setup(String title) {
        root = new LinearLayout(this); root.setOrientation(LinearLayout.VERTICAL);
        root.setBackground(new GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM,new int[]{0xFF8DEBFF,0xFFDAF9FF,0xFF8BE15B}));
        setContentView(root);
        header = new LinearLayout(this); header.setGravity(Gravity.CENTER_VERTICAL); header.setBackgroundColor(0xFF087FF5);
        TextView home = label("⌂",30,Color.WHITE,true); home.setBackground(bg(0xFF0564C8,80)); header.addView(home,new LinearLayout.LayoutParams(64,54)); home.setOnClickListener(v->showHome());
        TextView ttl = label(title,25,Color.WHITE,true); header.addView(ttl,new LinearLayout.LayoutParams(0,58,1));
        TextView sound = label("🔊",24,Color.WHITE,true); header.addView(sound,new LinearLayout.LayoutParams(64,54)); sound.setOnClickListener(v->Toast.makeText(this,"Sound button",Toast.LENGTH_SHORT).show());
        root.addView(header);
        ScrollView scroll = new ScrollView(this); root.addView(scroll,new LinearLayout.LayoutParams(-1,0,1));
        content = new LinearLayout(this); content.setOrientation(LinearLayout.VERTICAL); content.setPadding(12,10,12,10); scroll.addView(content);
    }
    void showHome() {
        page=-1; setup("Kids Learning Activities");
        TextView intro=label("🌈  Learn • Play • Grow  🌈",22,0xFF064A92,true); content.addView(intro);
        int cols=4; LinearLayout row=null;
        for(int i=0;i<activities.length;i++){
            if(i%cols==0){row=new LinearLayout(this); row.setGravity(Gravity.CENTER); content.addView(row);}
            final int idx=i;
            LinearLayout card=new LinearLayout(this); card.setOrientation(LinearLayout.VERTICAL); card.setGravity(Gravity.CENTER); card.setPadding(5,8,5,8); card.setBackground(bg(colors[i%colors.length],22));
            TextView icon=label(emojis[i],28,Color.WHITE,true); TextView name=label(activities[i],14,Color.WHITE,true); card.addView(icon); card.addView(name);
            LinearLayout.LayoutParams cp=new LinearLayout.LayoutParams(0,100,1); cp.setMargins(5,5,5,5); row.addView(card,cp);
            card.setOnClickListener(v->showActivity(idx));
        }
    }
    void showActivity(int idx) {
        page=idx; setup(activities[idx]);
        TextView instruction=label(instructionFor(idx),20,0xFF073B78,true); instruction.setBackground(bg(0xFFFFF29A,18)); LinearLayout.LayoutParams ip=new LinearLayout.LayoutParams(-1,-2); ip.setMargins(0,0,0,10); content.addView(instruction,ip);
        if(idx==13){ numberScreen(); return; }
        if(idx==3){ colouringScreen(); return; }
        String[][] pairs = pairData(idx);
        for(int r=0;r<pairs.length;r++){
            LinearLayout row=new LinearLayout(this); row.setGravity(Gravity.CENTER); content.addView(row);
            for(int c=0;c<pairs[r].length;c++){
                final String choice=pairs[r][c];
                TextView tile=label(choice,28,0xFF124A88,true); tile.setBackground(bg(colors[(r+c)%colors.length],20));
                LinearLayout.LayoutParams lp=new LinearLayout.LayoutParams(0,130,1); lp.setMargins(6,5,6,5); row.addView(tile,lp);
                tile.setOnClickListener(v->{ tile.setBackground(bg(0xFF55D879,20)); Toast.makeText(this,"Well done! ⭐",Toast.LENGTH_SHORT).show(); });
            }
        }
        Button next=new Button(this); next.setText("Next ➜"); content.addView(next); next.setOnClickListener(v->{int n=(idx+1)%activities.length;showActivity(n);});
    }
    String instructionFor(int i){String[] a={"Tap the object that is up or down.","Tap the object on the left or right.","Choose the correct direction.","Choose a colour and tap the picture.","Find the same or different picture.","Tap the object that is inside or outside.","Choose the object that is near or far.","Tap the open or closed object.","Choose the big or small object.","Choose the short or tall object.","Choose the heavy or light object.","Choose the thick or thin object.","Choose the long or short object.","Tap a number and trace it."};return a[i];}
    String[][] pairData(int i){switch(i){
        case 0:return new String[][]{{"🎈 ⬆️","🧸 ⬇️"},{"🐦 ⬆️","🐱 ⬇️"}};
        case 1:return new String[][]{{"🚗 ⬅️","🌳 ➡️"},{"🐟 ⬅️","🌸 ➡️"}};
        case 2:return new String[][]{{"🔄 Clockwise","↺ Anti-clockwise"}};
        case 4:return new String[][]{{"🍎","🍎","🍏"},{"🐱","🐱","🐈‍⬛"}};
        case 5:return new String[][]{{"🐱 📦 In","📦 🐱 Out"}};
        case 6:return new String[][]{{"🏡 🌳 Near","🏡 · · · 🌳 Far"}};
        case 7:return new String[][]{{"🚪 Open","🚪 Closed"}};
        case 8:return new String[][]{{"🐘 Big","🐘 Small"},{"🚗 Big","🚙 Small"}};
        case 9:return new String[][]{{"🦒 Tall","🦒 Short"},{"🧍 Short","🧍 Tall"}};
        case 10:return new String[][]{{"🎒 Heavy","🪶 Light"}};
        case 11:return new String[][]{{"📕 Thick","📄 Thin"}};
        case 12:return new String[][]{{"✏️ Long","✏️ Short"}};
        default:return new String[][]{{"⭐","🌈"}};
    }}
    void numberScreen(){
        String[] nums={"1","2","3","4","5","6","7","8","9","10"}; String[] words={"One","Two","Three","Four","Five","Six","Seven","Eight","Nine","Ten"}; String[] objects={"🍎","🦆🦆","🐱🐱🐱","🏐🏐🏐🏐","🐞🐞🐞🐞🐞","⭐ ⭐ ⭐ ⭐ ⭐ ⭐","🐟🐟🐟🐟🐟🐟🐟","🍓🍓🍓🍓🍓🍓🍓🍓","🦋🦋🦋🦋🦋🦋🦋🦋🦋","🥕🥕🥕🥕🥕🥕🥕🥕🥕🥕"};
        LinearLayout row=null;
        for(int i=0;i<10;i++){ if(i%5==0){row=new LinearLayout(this);content.addView(row);}
            final int n=i; LinearLayout card=new LinearLayout(this);card.setOrientation(LinearLayout.VERTICAL);card.setGravity(Gravity.CENTER);card.setBackground(bg(colors[i%colors.length],18));
            card.addView(label(nums[i],34,Color.WHITE,true));card.addView(label(objects[i],20,Color.BLACK,false));card.addView(label(words[i],18,Color.WHITE,true));
            LinearLayout.LayoutParams lp=new LinearLayout.LayoutParams(0,170,1);lp.setMargins(4,4,4,4);row.addView(card,lp);
            card.setOnClickListener(v->{Toast.makeText(this,words[n]+"! Trace number "+nums[n],Toast.LENGTH_SHORT).show(); showTrace(n+1);});
        }
    }
    void showTrace(int n){
        setup("Number Tracing");
        content.addView(label("Trace the number "+n+" by following the dotted line.",22,0xFF073B78,true));
        content.addView(label("●  ·  ·  ·  ·  ·  ·  ·  ·  ·  ·  ↓",24,0xFF333333,true));
        TextView big=label(String.valueOf(n),150,colors[(n-1)%colors.length],true);content.addView(big,new LinearLayout.LayoutParams(-1,220));
        Button back=new Button(this);back.setText("← Numbers");content.addView(back);back.setOnClickListener(v->showActivity(13));
    }
    void colouringScreen(){
        TextView pic=label("🦋",140,Color.BLACK,true);pic.setBackground(bg(Color.WHITE,20));content.addView(pic,new LinearLayout.LayoutParams(-1,250));
        LinearLayout row=new LinearLayout(this);content.addView(row);
        int[] cs={Color.RED,0xFFFFD600,Color.BLUE,Color.GREEN,0xFF8E24AA,0xFFFF9800};
        for(int c:cs){TextView sw=label("●",30,c,true);row.addView(sw,new LinearLayout.LayoutParams(0,60,1));sw.setOnClickListener(v->{pic.setTextColor(c);Toast.makeText(this,"Colour selected",Toast.LENGTH_SHORT).show();});}
    }
}