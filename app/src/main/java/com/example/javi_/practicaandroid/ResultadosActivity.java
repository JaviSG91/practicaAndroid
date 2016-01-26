package com.example.javi_.practicaandroid;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.TableRow.LayoutParams;

public class ResultadosActivity extends Activity {
    private TableLayout tl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultados);

        //Se recogen los datos
        tl = (TableLayout)findViewById(R.id.tableLayout);

        int lastTen= Resultados.getInstance().getTamanio()-10;
        if(lastTen<0)
            lastTen=0;


        for(int i=lastTen; i< Resultados.getInstance().getTamanio();i++)
            createTableRow(Resultados.getInstance().getRow(i).get(0), Resultados.getInstance().getRow(i).get(1), Resultados.getInstance().getRow(i).get(2));



        Button volver = (Button)findViewById(R.id.volver);
        volver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(ResultadosActivity.this,MenuActividad.class);
                startActivity(i);
            }
        });
    }

    @TargetApi(Build.VERSION_CODES.M)
    public void createTableRow(String a, String f, String p) {

        TableRow tr = new TableRow(this);
        LayoutParams lp = new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
        lp.weight = 1;

        tr.setLayoutParams(lp);

        TextView aciertos = new TextView(this);

        aciertos.setLayoutParams(lp);
        aciertos.setText(a);
        aciertos.setGravity(Gravity.CENTER);
        TextView fallos = new TextView(this);
        fallos.setLayoutParams(lp);
        fallos.setText(f);
        fallos.setGravity(Gravity.CENTER);
        TextView percent = new TextView(this);

        percent.setLayoutParams(lp);
        percent.setGravity(Gravity.CENTER);
        percent.setText(p);


        tr.addView(aciertos);
        tr.addView(fallos);
        tr.addView(percent);

        tl.addView(tr,new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
    }
}
