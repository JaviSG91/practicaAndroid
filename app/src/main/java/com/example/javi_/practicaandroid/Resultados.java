package com.example.javi_.practicaandroid;

import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by javi_ on 16/01/2016.
 */
public class Resultados {
    private ArrayList<ArrayList<String>> ResultMatrix;
    private static Resultados mInstance = null;
    private String fallos;
    private String aciertos;
    private String percent;


    private Resultados(){

        aciertos = "0";
        fallos = "0";
        percent="0";

        ResultMatrix= new ArrayList<>();


    }


    public static Resultados getInstance(){
        if(mInstance == null) {
            mInstance = new Resultados();
        }
        return mInstance;
    }


    public int getTamanio(){
        return ResultMatrix.size();
    }
    public ArrayList<String> getRow(int i){
        return ResultMatrix.get(i);
    }

    public void addResult(double a, double f){
        double perc= (a/(a+f))*100.0;
        Log.i("info", String.valueOf(perc));
        percent=Double.toString(perc);
        aciertos= String.valueOf(a);
        fallos= String.valueOf(f);
        ResultMatrix.add(new ArrayList<>(Arrays.asList(aciertos,fallos,percent)));

    }


}
