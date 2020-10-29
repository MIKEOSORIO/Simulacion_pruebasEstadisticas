/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Metodos;

import java.text.NumberFormat;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JTable;
import javax.swing.JTextField;
import org.apache.commons.math3.distribution.ChiSquaredDistribution;
import org.apache.commons.math3.distribution.NormalDistribution;

/**
 *
 * @author Migue
 */
public class Metodos {

    public void media(JFrame fr, JTable tb, JTextField conf, JTextField suma, JTextField jtfalfa, JTextField prom, JTextField jtfz, JTextField jtfli, JTextField jtfls, JTextField res) {
//-------------------------------------------------------------Para obtener el valor de la sumatoria y la media---------------------------------------
        double sumatoria = 0;
        int i = 0; //nos servira como numeros de valores ingresados n (contador)
        for (i = 0; i < tb.getRowCount(); i++)//la sentencia se repetira mientras el .txt tenga valores
        {
            sumatoria = sumatoria + Double.parseDouble(tb.getValueAt(i, 0).toString()); //sumatoria de los datos del .txt y el contador en aumento
        }
        double promedio = sumatoria / i; // el resultado de la suma se divide entre el contador = media ó promedio
        suma.setText(String.valueOf(sumatoria)); //asinamos el valor al JTextField sumatoria
        prom.setText(String.valueOf(promedio)); //asignamos el valor al JTextField prom
//----------------------------------------------------------------------------------------------------------------------------------------------------------      

        //Obtenemos el valor de z
        double confianza = (Double.parseDouble(conf.getText()));
        double confianza_decimal = (confianza / 100); //obtenemos el valor del JTextField "confiabilidad" y se divide entre 100 
        double alfa = (1 - confianza_decimal);//a 1 restamos confianza decimal para obtener alfa
        //System.out.println(alfa);

        jtfalfa.setText(String.valueOf(alfa)); //asignamos el valor al JTextField jtfalfa
//----------------------------------------------------------------------------------------------------------------------------------------------------------------

//-------------------------------------------------------------------- Valor de Z-------------------------------------------------------------------------
        double z;

        NormalDistribution nd = new NormalDistribution();
        z = nd.inverseCumulativeProbability(1 - alfa / 2); //Obtenemos el valor Z de la tabla de distribución normal

        NumberFormat numberFormat = NumberFormat.getInstance(); //Para formatear un número para una configuración regional diferente
        z = Double.parseDouble(numberFormat.format(z));

        jtfz.setText(String.valueOf(z)); //le asigamos el valor al JTextField jtfz

        //-------------------------------------------------------------------- Calculamos los limites Inferior y Superior-------------------------------------------------------------------------       
        Double li = 0.5 - z * (1 / Math.sqrt(12 * i));//obtenemos el valor del limite inferior 
        jtfli.setText(String.valueOf(li)); //le asigamos el valor al JTextField jtfli

        Double ls = 0.5 + z * (1 / Math.sqrt(12 * i)); //obtenemos el valor del limite superior 
        jtfls.setText(String.valueOf(ls)); //le asigamos el valor al JTextField jtfls

//----------------------------------------------------------------------- Evaluacón entre los limites de aceptación -----------------------------------------------------------------------------------------------
        if (promedio > li & promedio < ls) // Si el promedio se encuentra entre los límites de aceptación, concluimos que no se puede rechazar que el conjunto 
        {
            res.setText("¡Hipótesis Aceptada!");
        } else {
            res.setText("Hipótesis Rechazada");
        }

    }

//-------------------------------------------------------------------------------- PRUEBA DE LA VARIANZA ----------------------------------------------------------------------------------------------  
    public void varianza(JFrame fr, JTable tb, JTextField conf, JTextField jtfV, JTextField jtfz2, JTextField jtfli2, JTextField jtfls2, JTextField res) {
        //variables que se utilizaran
        double promedio = 0;
        double sumaVarianza = 0;
        int i = 0;
        double varianza2 = 0;
        double sumatoria2 = 0;
        int i2 = 0;

        for (i2 = 0; i2 < tb.getRowCount(); i2++) //el i2 es el contador que usaremos con n elementos
        {
            sumatoria2 = sumatoria2 + Double.parseDouble(tb.getValueAt(i2, 0).toString()); // se suman todos los valores de la tabla y el contador aumentara segun las repeticiones de la sentencia
        }
        promedio = sumatoria2 / i2;

        //Calculamos la varianza
        for (i2 = 0; i2 < tb.getRowCount(); i2++) {
            sumaVarianza = sumaVarianza + Math.pow((Double.parseDouble(tb.getValueAt(i2, 0).toString()) - promedio), 2);
        } //la sentnencia explica que la sumaVarianza es igual a la lectura de un valor de la tabla tb y a cada valor restarle la media y elevar al cuadrado el resultado
        //al hacer la operacion se guarda en la variable sumaVarianza hasta que llegue el sig. valor, entonces se suma el valor anterior mas el nuevo, esto se repite hasta que la tabla no tenga valores por lecturar
        varianza2 = (sumaVarianza) / (i2 - 1); // ahora divimos la sumatoria de la varianza entre el numero de datos que es el contador i2 - 1
        jtfV.setText(String.valueOf(varianza2)); //asignamos el valor al JTextField jtfV

        int grados_libertad = i2 - 1;

        //Obtenemos el valor de z
        double confianza = (Double.parseDouble(conf.getText())) / 100; //obtenemos el valor del JTextField conf (confianza) y se dive entre 100
        double alfa = 1 - confianza; //para obtener alfa a 1 se le resta el % de confianza 
        double z2;

        NormalDistribution nd = new NormalDistribution();
        z2 = nd.inverseCumulativeProbability(1 - alfa / 2); //Obtenemos el valor Z de la tabla de distribución normal

        NumberFormat numberFormat = NumberFormat.getInstance();
        z2 = Double.parseDouble(numberFormat.format(z2));

        jtfz2.setText(String.valueOf(z2));//asignamos el valor de Z a jtfZ

        ChiSquaredDistribution chi = new ChiSquaredDistribution(grados_libertad);

//--------------------------------------------------------------------- Calculamos el valor de los limites inferior y superior------------------------------------------        
        Double li = chi.inverseCumulativeProbability(alfa / 2) / (12 * grados_libertad);
        jtfli2.setText(String.valueOf(li));

        Double ls = chi.inverseCumulativeProbability(1 - alfa / 2) / (12 * grados_libertad);
        jtfls2.setText(String.valueOf(ls));

//----------------------------------------------------------------------- Evaluacion del conjunto ri--------------------------------------------------------------
        if (varianza2 > li & varianza2 < ls) //la evaluacion consiste en que la varianza este entre el rango de limite inferior y superior
        {
            res.setText("¡Conjunto r(i) aceptada!"); //si cumple la condicion se acepta el conjunto ri
        } else {
            res.setText("¡Conjunto r(i) denegada!");// si no es rechazada
        }
    }
//---------------------------------------------------------------------------- Prueba de Corridas -------------------------------------------------------------------------------------

    public void corridas(JFrame fr, JTable tb, JTextField conf, JTextField cadena, JTextField jnc, JTextField jm, JTextField jv, JTextField jz, JTextField res) {
        double confianza = (Double.parseDouble(conf.getText())) / 100; //dividimos el valor % de confianza entre 100
        double alfa = 1 - confianza; //restamos 1 al resultado de la confianza 
        double suma = 0;
        int i = 0;
        for (i = 0; i < tb.getRowCount(); i++) {
            suma = suma + Double.parseDouble(tb.getValueAt(i, 0).toString()); //sacamos la sumatoria de los valores de la tabla tb
        }

        Double[] datos = new Double[i];

        for (int ip = 0; ip < i; ip++) // la sentencia acaba cuando el contador sea mayor que ip
        {
            datos[ip] = Double.parseDouble(tb.getValueAt(ip, 0).toString());  //asignamos los valores de la tabla en el arreglo datos
        }

        ArrayList<Integer> bits = new ArrayList<>(); //Creamos una lista para guardar los ceros y unos.
        int i3, corridas, dato;
        double media, varianza, z;

        for (i3 = 1; i3 < datos.length; i3++) {
            if (datos[i3] <= datos[i3 - 1]) {//Revisa si cada dato actual es menor al dato anterior. 
                bits.add(0);//Si es así, se guarda un 0
            } else {
                bits.add(1);// de lo contrario, se guarda un 1
            }
        }

        String binario = "";

        for (i = 0; i < bits.size(); i++) {
            //System.out.print(bits.get(i));
            binario = binario + bits.get(i);
        }

        cadena.setText(binario); //Imprimimos la cadena de ceros y unos en el jtfcadena

//----------------------------------------------------------- Contamos las corridas ---------------------------------------------------------------------
        corridas = 1;
        dato = bits.get(0);

        for (i = 1; i < bits.size(); i++) {
            if (bits.get(i) != dato) //Comparamos cada dígito con el observado, cuando cambia es una nueva corrida
            {
                corridas++;         //el contador de corridas aumentará si el digito observado actualmente es diferente al siguiente
                dato = bits.get(i); //obtenemos el nuevo valor de i por cada corrida
            }

        }

        jnc.setText(String.valueOf(corridas)); //asignamos al jnc el valor de las corridas

//------------------------------------------------------------------------------ Media de corrida---------------------------------------------------------
        media = (2 * datos.length - 1) / (double) 3; //(2*n - 1)/(3)
        jm.setText(String.valueOf(media)); //asignamos el valor de la media al JTextField jm

//------------------------------------------------------------------------------ Varianza de corrida---------------------------------------------------------
        varianza = (16 * datos.length - 29) / (double) 90; //(16n-29)/90
        jv.setText(String.valueOf(varianza));//asignamos el valor de la varianza al JTextField jv
//------------------------------------------------------------------------------ Valor de Z en corrida---------------------------------------------------------
        z = Math.abs((corridas - media) / Math.sqrt(varianza));
        jz.setText(String.valueOf(z));//asignamos el valor de Z al JTextField jz

//-------------------------------------------------------------------Obtenemos el valor Z de la tabla de distribución normal--------------------------------------------------------
        NormalDistribution normal = new NormalDistribution();
        double zn = normal.inverseCumulativeProbability(1 - alfa / 2);

//-------------------------------------------------Comparamos: si el estadítico Z0 es mayor Z(alfa)/2----------------------------------------
        if (z < zn) //Si cumple la comparacion anterior
        {

            res.setText("¡No se rechaza la hipótesis de independencia!"); //no se prechaza la hipótesis de independencia
        } else {
            //System.out.println("No Pasa la prueba de corridas"); //sino se rechaza
            res.setText("¡Hipótesis de Independencia rechazada!");
        }

    }

}
