package com.example.javi_.practicaandroid;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class ActividadPrincipal extends Activity implements View.OnClickListener {
    Button opcion_1;
    Button opcion_2;
    Button opcion_3;
    Button opcion_4;
    Button reset;
    String respuesta; //respuesta correcta a la pregunta
    Button continuar;
    TextView pregunta_inicial;
    ImageView iv;
    ImageView anim;
    MediaPlayer mp2;
    GridLayout gl;
    int PREGUNTAS=6;
    Cursor cuestiones;
    int ronda = 0;
    Animation as;
    int aciertos=0;
    int fallos=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        as = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.animacion);
        anim = (ImageView)findViewById(R.id.anim);
        as.setFillEnabled(true);
        as.setFillAfter(true);
        gl = (GridLayout) findViewById(R.id.panel);
        iv = (ImageView) findViewById(R.id.imagen);

        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        float dpHeight = displayMetrics.heightPixels / displayMetrics.density;
        float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
        int w = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpWidth, getResources().getDisplayMetrics());

// Gets the layout params that will allow you to resize the layout

// Changes the height and width to the specified *pixels*







        as.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                AlphaAnimation AA = new AlphaAnimation(1,0);
                AA.setDuration(1000);
                AA.setFillEnabled(true);
                AA.setFillAfter(true);
                pregunta_inicial.setAnimation(AA);
                gl.setAnimation(AA);

                AA.start();
                gl.setEnabled(false);
            }

            @Override
            public void onAnimationEnd(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });


        reset = (Button)findViewById(R.id.reset);
        continuar = (Button)findViewById(R.id.continuar);



        reset.setVisibility(View.INVISIBLE);
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                AlertDialog dialog = new AlertDialog.Builder(ActividadPrincipal.this).create();
                dialog.setMessage(cuestiones.getString(1));
                dialog.setTitle("Respuesta correcta");
                dialog.setCanceledOnTouchOutside(true);
                dialog.show();
                dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
                        Intent intento = new Intent(ActividadPrincipal.this, MenuActividad.class);
                        startActivity(intento);
                    }
                });

            }
        });

        continuar.setVisibility(View.INVISIBLE);
        continuar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ronda < PREGUNTAS-1) {
                    ronda++;
                    nextRound();
                }
                else {

                    Resultados.getInstance().addResult(aciertos,fallos);
                    Intent i= new Intent(ActividadPrincipal.this,ResultadosActivity.class);
                    startActivity(i);
                }
            }
        });


        //Recuperar el contexto para introducirle la pregunta

        this.pregunta_inicial = (TextView) this.findViewById(R.id.pregunta) ;
        this.opcion_1 = (Button) this.findViewById(R.id.A) ;
        this.opcion_2 = (Button) this.findViewById(R.id.B) ;
        this.opcion_3 = (Button) this.findViewById(R.id.C) ;
        this.opcion_4 = (Button) this.findViewById(R.id.D) ;

        Log.i("e", String.valueOf(w));
        ViewGroup.LayoutParams params = opcion_1.getLayoutParams();

        params.width= (int) (w/3.5);
        params.height=w/7;
        ViewGroup.LayoutParams params2 = opcion_2.getLayoutParams();
        ViewGroup.LayoutParams params3 = opcion_3.getLayoutParams();
        ViewGroup.LayoutParams params4 = opcion_4.getLayoutParams();

        params2.width= (int) (w/3.5);
        params3.width= (int) (w/3.5);
        params4.width= (int) (w/3.5);
        params2.height=w/7;
        params3.height=w/7;
        params4.height=w/7;
       /* opcion_1.setWidth(w / 4);
        opcion_2.setWidth(w / 4);
        opcion_3.setWidth(w / 4);
        opcion_4.setWidth(100);*/
        //nos crea el "puente" de acceso a la base de datos
        //this.db = new DBPref(this) ;

        cuestiones =  DBPref.getInstance(getApplicationContext()).getPreguntas(DBPref.Categoria.TECNOLOGIA, DBPref.Dificultad.FACIL, PREGUNTAS);

        nextRound();

        //se asigna el listener por defecto a los 4 botones de respuestas
        this.opcion_1.setOnClickListener(this);
        this.opcion_2.setOnClickListener(this);
        this.opcion_3.setOnClickListener(this);
        this.opcion_4.setOnClickListener(this);
    }

    @Override
    public void onClick ( View view ) {


        switch(view.getId()) {
            case R.id.A:
            case R.id.B:
            case R.id.C:
            case R.id.D:
                VerificaRespuesta(((Button) view).getText());
                break;

        }
    }

    private void VerificaRespuesta(CharSequence text) {
        if (mp2 != null) {
            mp2.stop();
            mp2.release();
            mp2 = null;
        }

        anim.bringToFront();
        if (respuesta.equals(text)) {
            mp2 = MediaPlayer.create(this, R.raw.acierto) ;
            anim.setImageResource(R.drawable.correcto);
            aciertos++;
        }
        else {
            mp2 = MediaPlayer.create(this, R.raw.fallo) ;
            anim.setImageResource(R.drawable.fallo);
            reset.setVisibility(View.VISIBLE);
            fallos++;
        }

        opcion_1.setEnabled(false);
        opcion_2.setEnabled(false);
        opcion_3.setEnabled(false);
        opcion_4.setEnabled(false);

        anim.startAnimation(as);
        continuar.setVisibility(View.VISIBLE);
        mp2.start();
    }



    private void nextRound() {

        opcion_1.setEnabled(true);
        opcion_2.setEnabled(true);
        opcion_3.setEnabled(true);
        opcion_4.setEnabled(true);
        iv.bringToFront();
        iv.setVisibility(View.VISIBLE);
        if(ronda==0)
            cuestiones.moveToFirst();
        else
            cuestiones.moveToNext();

        respuesta = cuestiones.getString(1);
        pregunta_inicial.setText(cuestiones.getString(0));
        ReasignaRespuestas(respuesta, cuestiones.getString(2), cuestiones.getString(3), cuestiones.getString(4));
        Log.i("info", cuestiones.getString(5));
        if (cuestiones.getString(5).equals("1")){
            //Toast.makeText(getApplicationContext(), "1!", Toast.LENGTH_SHORT).show();
            iv.setVisibility(View.VISIBLE);
            Resources res = getResources();
            String mDrawableName = cuestiones.getString(0);
            int resID = res.getIdentifier(mDrawableName , "drawable", getPackageName());
            Drawable drawable = res.getDrawable(resID );
            iv.setImageDrawable(drawable);
            pregunta_inicial.setText("¿De qué se trata?");
        }
        else if(cuestiones.getString(5).equals("2")){
            pregunta_inicial.setText("¿De qué se trata?");
            Toast.makeText(getApplicationContext(), "2", Toast.LENGTH_SHORT).show();
            iv.setVisibility(View.VISIBLE);
            iv.setImageResource(R.drawable.play);
            iv.setTag(R.drawable.play);
            iv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Integer integer = (Integer) v.getTag();
                    integer = integer == null ? 0 : integer;

                    if (integer != R.drawable.play) {
                        if (mp2 != null) {
                            mp2.stop();
                            mp2.release();
                            mp2 = null;
                        }
                        iv.setTag(R.drawable.play);
                        iv.setImageResource(R.drawable.play);


                    } else {
                        Resources res = getResources();
                        String name = cuestiones.getString(0);
                        int resID = res.getIdentifier(name, "raw", getPackageName());
                        mp2 = MediaPlayer.create(getApplicationContext(), resID);
                        mp2.start();
                        iv.setTag(R.drawable.stop);
                        iv.setImageResource(R.drawable.stop);
                        mp2.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                            @Override
                            public void onCompletion(MediaPlayer mp) {

                                iv.setTag(R.drawable.play);
                                iv.setImageResource(R.drawable.play);
                            }
                        });

                    }
                }
            });
        }
        else
            iv.setVisibility(View.INVISIBLE);

        Reinicio();

    }

    private void Reinicio() {
        gl.setEnabled(true);
        anim.clearAnimation();
        AlphaAnimation AA = new AlphaAnimation(0,1);
        AA.setDuration(1000);
        AA.setFillEnabled(true);
        AA.setFillAfter(true);
        pregunta_inicial.setAnimation(AA);
        gl.setAnimation(AA);
        AA.start();
        reset.setVisibility(View.INVISIBLE);
        continuar.setVisibility(View.INVISIBLE);

    }

    void ReasignaRespuestas(String s1, String s2, String s3, String s4){
        ArrayList<String> v = new ArrayList<>(4);
        v.add(s1);
        v.add(s2);
        v.add(s3);
        v.add(s4);

        Random r = new Random();
        opcion_1.setText(v.remove(r.nextInt(4)));
        opcion_2.setText(v.remove(r.nextInt(3)));
        opcion_3.setText(v.remove(r.nextInt(2)));
        opcion_4.setText(v.remove(0));

    }

}
