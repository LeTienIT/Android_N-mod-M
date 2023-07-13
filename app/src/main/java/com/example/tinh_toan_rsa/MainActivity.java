package com.example.tinh_toan_rsa;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    EditText edtSo1, edtSoMu, edtSo2;
    TextView tvDay, tvKQ;
    Button btnGo, btnReset;
    ArrayList<Integer> Dayso;
    ArrayList<Integer> Dayso3;
    ArrayList<Integer> giatriDay = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        edtSo1 = findViewById(R.id.edtKey1);
        edtSoMu = findViewById(R.id.edtKey2);
        edtSo2 = findViewById(R.id.edtKey3);
        tvDay = findViewById(R.id.tvDayNhiPhan);
        tvKQ = findViewById(R.id.tvTinhToan);
        btnGo = findViewById(R.id.btnGo);
        btnReset = findViewById(R.id.btnReset);
        giatriDay.add(1);giatriDay.add(2);giatriDay.add(4);giatriDay.add(8);
        giatriDay.add(16);giatriDay.add(32);giatriDay.add(64);giatriDay.add(128);

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edtSo1.setText("");
                edtSoMu.setText("");
                edtSo2.setText("");
                tvDay.setText("");
                tvKQ.setText("");
            }
        });

        btnGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Integer so1,somu,so2;
                Dayso = new ArrayList<>();
                Dayso3 = new ArrayList<>();
                try {
                    so1 = Integer.parseInt(edtSo1.getText().toString());
                    somu = Integer.parseInt(edtSoMu.getText().toString());
                    so2 = Integer.parseInt(edtSo2.getText().toString());
                }
                catch (Exception e)
                {
                    Snackbar.make(view,"Hãy nhập một số nguyên",Snackbar.LENGTH_SHORT).show();
                    return;
                }
                timdayMu(somu);
                String chuoiDay = chuyendoithanhchuoi();
                tvDay.setText(chuoiDay);
                tinhtoan(so1,so2);
            }
        });
    }
    private void timdayMu(int key)
    {
        if (key == 0) {
            Dayso.add(0);
        }

        while (key > 0) {
            Dayso.add(key % 2);
            key /= 2;
        }
    }
    private String chuyendoithanhchuoi()
    {
        String chuoi="";
        for(int i = 0; i <Dayso.size() ; i++)
        {
            if(Dayso.get(i) != 0)
            {
                int gt = giatriDay.get(i);
                if(i!=Dayso.size()-1)
                {
                    chuoi = chuoi + gt + "-";
                }
                else
                {
                    chuoi = chuoi + gt;
                }
            }
//            chuoi = Dayso.size() + " - " + giatriDay.size();

       }
        return chuoi;
    }
    private void tinhtoan(int a,int b)
    {
        String chuoi = "";
        double atmp = a;
        Double khoitao= (Math.pow(atmp,1)) % b;
        int j = Double.valueOf(khoitao).intValue();
        Dayso3.add(j);
        chuoi+= "("+a+"^1"+") mod "+b+" =" +khoitao + "\n";
        for(int i = 2 ; i <= 128; i=i*2)
        {
            Double tmp;
            tmp = (khoitao * khoitao) % b;
            j = Double.valueOf(tmp).intValue();
            Dayso3.add(j);
            khoitao = tmp;
            chuoi+= "("+a+"^"+i+" x "+ a+"^"+i+") mod "+b+" = "+tmp + "\n";
        }
        double tich=1;
        chuoi += "=> ";
        for(int i=0;i<Dayso.size();i++)
        {
            if(Dayso.get(i) != 0)
            {
                tich*=Dayso3.get(i);
                chuoi += Dayso3.get(i) +" x ";
            }
        }
        chuoi = chuoi.substring(0, chuoi.length() - 2);
        double kq = tich % b;
        DecimalFormat decimalFormat = new DecimalFormat("#,###.#########");
        String kqString = decimalFormat.format(kq);
        String tichString = decimalFormat.format(tich);
        chuoi += " mod "+b +" = "+tichString+" % "+ b +" = "+kqString;
        tvKQ.setText(chuoi);
    }
}