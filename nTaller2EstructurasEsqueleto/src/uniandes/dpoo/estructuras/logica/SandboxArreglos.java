package uniandes.dpoo.estructuras.logica;

import java.util.*;

/**
 * Esta clase tiene un conjunto de métodos para practicar operaciones sobre arreglos de enteros y de cadenas.
 *
 * Todos los métodos deben operar sobre los atributos arregloEnteros y arregloCadenas.
 *  
 * No pueden agregarse nuevos atributos.
 *  
 * Implemente los métodos usando operaciones sobre arreglos (ie., no haga cosas como construir listas para evitar la manipulación de arreglos).
 */
public class SandboxArreglos {
    private int[] arregloEnteros;
    private String[] arregloCadenas;
    private HashMap<Integer, Integer> histogramaEnteros;
    private HashMap<String, Integer> histogramaCadenas;

    public SandboxArreglos() {
        arregloEnteros = new int[0];
        arregloCadenas = new String[0];
        histogramaEnteros = new HashMap<>();
        histogramaCadenas = new HashMap<>();
    }

    public int[] getCopiaEnteros() {
        return Arrays.copyOf(arregloEnteros, arregloEnteros.length);
    }

    public String[] getCopiaCadenas() {
        return Arrays.copyOf(arregloCadenas, arregloCadenas.length);
    }

    public int getCantidadEnteros() {
        return arregloEnteros.length;
    }

    public int getCantidadCadenas() {
        return arregloCadenas.length;
    }

    public void agregarEntero(int entero) {
        arregloEnteros = Arrays.copyOf(arregloEnteros, arregloEnteros.length + 1);
        arregloEnteros[arregloEnteros.length - 1] = entero;
        histogramaEnteros.put(entero, histogramaEnteros.getOrDefault(entero, 0) + 1);
    }

    public void agregarCadena(String cadena) {
        arregloCadenas = Arrays.copyOf(arregloCadenas, arregloCadenas.length + 1);
        arregloCadenas[arregloCadenas.length - 1] = cadena;
        histogramaCadenas.put(cadena.toLowerCase(), histogramaCadenas.getOrDefault(cadena.toLowerCase(), 0) + 1);
    }

    public void eliminarEntero(int valor) {
        int count = histogramaEnteros.getOrDefault(valor, 0);
        if (count == 0) return;
        
        int[] nuevoArreglo = new int[arregloEnteros.length - count];
        int idx = 0;
        for (int i : arregloEnteros) {
            if (i != valor) nuevoArreglo[idx++] = i;
        }
        arregloEnteros = nuevoArreglo;
        histogramaEnteros.remove(valor);
    }

    public void eliminarCadena(String cadena) {
        int count = histogramaCadenas.getOrDefault(cadena.toLowerCase(), 0);
        if (count == 0) return;

        String[] nuevoArreglo = new String[arregloCadenas.length - count];
        int idx = 0;
        for (String s : arregloCadenas) {
            if (!s.equalsIgnoreCase(cadena)) nuevoArreglo[idx++] = s;
        }
        arregloCadenas = nuevoArreglo;
        histogramaCadenas.remove(cadena.toLowerCase());
    }

    public void insertarEntero(int entero, int posicion) {
        if (posicion < 0) posicion = 0;
        if (posicion > arregloEnteros.length) posicion = arregloEnteros.length;
        
        int[] nuevoArreglo = new int[arregloEnteros.length + 1];
        for (int i = 0, j = 0; i < nuevoArreglo.length; i++) {
            if (i == posicion) {
                nuevoArreglo[i] = entero;
            } else {
                nuevoArreglo[i] = arregloEnteros[j++];
            }
        }
        arregloEnteros = nuevoArreglo;
        histogramaEnteros.put(entero, histogramaEnteros.getOrDefault(entero, 0) + 1);
    }

    public void eliminarEnteroPorPosicion(int posicion) {
        if (posicion < 0 || posicion >= arregloEnteros.length) return;

        int valor = arregloEnteros[posicion];
        int[] nuevoArreglo = new int[arregloEnteros.length - 1];
        for (int i = 0, j = 0; i < arregloEnteros.length; i++) {
            if (i != posicion) {
                nuevoArreglo[j++] = arregloEnteros[i];
            }
        }
        arregloEnteros = nuevoArreglo;

        int count = histogramaEnteros.getOrDefault(valor, 0);
        if (count > 1) {
            histogramaEnteros.put(valor, count - 1);
        } else {
            histogramaEnteros.remove(valor);
        }
    }

    public void reiniciarArregloEnteros(double[] valores) {
        arregloEnteros = new int[valores.length];
        histogramaEnteros.clear();
        for (int i = 0; i < valores.length; i++) {
            arregloEnteros[i] = (int) valores[i];
            histogramaEnteros.put(arregloEnteros[i], histogramaEnteros.getOrDefault(arregloEnteros[i], 0) + 1);
        }
    }

    public void reiniciarArregloCadenas(Object[] objetos) {
        arregloCadenas = new String[objetos.length];
        histogramaCadenas.clear();
        for (int i = 0; i < objetos.length; i++) {
            String cadena = objetos[i].toString();
            arregloCadenas[i] = cadena;
            histogramaCadenas.put(cadena.toLowerCase(), histogramaCadenas.getOrDefault(cadena.toLowerCase(), 0) + 1);
        }
    }

    public void volverPositivos() {
        for (int i = 0; i < arregloEnteros.length; i++) {
            if (arregloEnteros[i] < 0) {
                int valorOriginal = arregloEnteros[i];
                int valorPositivo = -valorOriginal;

                arregloEnteros[i] = valorPositivo;
                histogramaEnteros.put(valorPositivo, histogramaEnteros.getOrDefault(valorPositivo, 0) + 1);
                int countOriginal = histogramaEnteros.get(valorOriginal);
                if (countOriginal == 1) {
                    histogramaEnteros.remove(valorOriginal);
                } else {
                    histogramaEnteros.put(valorOriginal, countOriginal - 1);
                }
            }
        }
    }

    public void organizarEnteros() {
        Arrays.sort(arregloEnteros);
    }

    public void organizarCadenas() {
        Arrays.sort(arregloCadenas);
    }

    public int contarApariciones(int valor) {
        return histogramaEnteros.getOrDefault(valor, 0);
    }

    public int contarApariciones(String cadena) {
        return histogramaCadenas.getOrDefault(cadena.toLowerCase(), 0);
    }

    public int[] buscarEntero(int valor) {
        int count = contarApariciones(valor);
        int[] posiciones = new int[count];
        int idx = 0;
        for (int i = 0; i < arregloEnteros.length; i++) {
            if (arregloEnteros[i] == valor) posiciones[idx++] = i;
        }
        return posiciones;
    }

    public HashMap<Integer, Integer> calcularHistograma() {
        return new HashMap<>(histogramaEnteros);
    }

    public int[] calcularRangoEnteros() {
        if (arregloEnteros.length == 0) return new int[0];
        int min = Integer.MAX_VALUE;
        int max = Integer.MIN_VALUE;
        for (int valor : arregloEnteros) {
            if (valor < min) min = valor;
            if (valor > max) max = valor;
        }
        return new int[]{min, max};
    }

    public int contarEnterosRepetidos() {
        int count = 0;
        for (int valor : histogramaEnteros.values()) {
            if (valor > 1) count++;
        }
        return count;
    }

    public boolean compararArregloEnteros(int[] otroArreglo) {
        return Arrays.equals(arregloEnteros, otroArreglo);
    }

    public boolean mismosEnteros(int[] otroArreglo) {
        if (arregloEnteros.length != otroArreglo.length) return false;

        HashMap<Integer, Integer> otroHistograma = new HashMap<>();
        for (int valor : otroArreglo) {
            otroHistograma.put(valor, otroHistograma.getOrDefault(valor, 0) + 1);
        }

        return histogramaEnteros.equals(otroHistograma);
    }

    public void generarEnteros(int cantidad, int minimo, int maximo) {
        Random rand = new Random();
        arregloEnteros = new int[cantidad];
        histogramaEnteros.clear();
        for (int i = 0; i < cantidad; i++) {
            int valor = rand.nextInt((maximo - minimo + 1)) + minimo;
            arregloEnteros[i] = valor;
            histogramaEnteros.put(valor, histogramaEnteros.getOrDefault(valor, 0) + 1);
        }
    }
} 