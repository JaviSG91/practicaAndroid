package com.example.javi_.practicaandroid;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MenuActividad extends Activity implements View.OnClickListener {
    private Button botonJugar ;
    private Button botonEstadisticas;
    private Button botonAjustes ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        this.botonJugar = ( Button ) this.findViewById (R.id.btn_jugar) ;
        this.botonEstadisticas = ( Button ) this.findViewById (R.id.btn_resultados);
        this.botonAjustes = ( Button ) this.findViewById (R.id.btn_ajustes) ;
        //instalar el manejador de 3 botones
        this.botonJugar.setOnClickListener(this);
        this.botonEstadisticas.setOnClickListener(this);
        this.botonAjustes.setOnClickListener(this);




    }

    @Override
    public void onClick ( View vista ) {
        switch(vista.getId()) {
            case R.id.btn_jugar:
                this.startActivity(new Intent(MenuActividad.this, ActividadPrincipal.class));
                break;

            case R.id.btn_resultados:
                this.startActivity(new Intent(MenuActividad.this, ResultadosActivity.class));
                Log.i("intent", "entra");
                break;

            case R.id.btn_ajustes:
                this.startActivity(new Intent(MenuActividad.this, presentacion.class));
                break;

        }
    }

}
