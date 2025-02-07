package uniandes.dpoo.estructuras.logica;

import java.util.*;

/**
 * Esta clase tiene un conjunto de métodos para practicar operaciones sobre listas de enteros y de cadenas.
 *
 * Todos los métodos deben operar sobre los atributos listaEnteros y listaCadenas.
 * 
 * No pueden agregarse nuevos atributos.
 * 
 * Implemente los métodos usando operaciones sobre listas (ie., no haga cosas como construir arreglos para evitar la manipulación de listas).
 * 
 * Intente usar varias formas de recorrer las listas (while, for, for each, iteradores ... )
 */
public class SandboxListas
{
    private List<Integer> listaEnteros;
    private List<String> listaCadenas;

    public SandboxListas( )
    {
        listaEnteros = new ArrayList<Integer>( );
        listaCadenas = new LinkedList<String>( );
    }

    public List<Integer> getCopiaEnteros( )
    {
        return new ArrayList<>(listaEnteros);
    }

    public List<String> getCopiaCadenas( )
    {
        return new LinkedList<>(listaCadenas);
    }

    public int[] getEnterosComoArreglo( )
    {
        return listaEnteros.stream().mapToInt(Integer::intValue).toArray();
    }

    public int getCantidadEnteros( )
    {
        return listaEnteros.size();
    }

    public int getCantidadCadenas( )
    {
        return listaCadenas.size();
    }

    public void agregarEntero( int entero )
    {
        listaEnteros.add(entero);
    }

    public void agregarCadena( String cadena )
    {
        listaCadenas.add(cadena);
    }

    public void eliminarEntero( int valor )
    {
        listaEnteros.removeIf(e -> e == valor);
    }

    public void eliminarCadena( String cadena )
    {
        listaCadenas.removeIf(s -> s.equals(cadena));
    }

    public void insertarEntero( int entero, int posicion )
    {
        if (posicion < 0) {
            listaEnteros.add(0, entero);
        } else if (posicion >= listaEnteros.size()) {
            listaEnteros.add(entero);
        } else {
            listaEnteros.add(posicion, entero);
        }
    }

    public void eliminarEnteroPorPosicion( int posicion )
    {
        if (posicion >= 0 && posicion < listaEnteros.size()) {
            listaEnteros.remove(posicion);
        }
    }

    public void reiniciarArregloEnteros( double[] valores )
    {
        listaEnteros.clear();
        for (double valor : valores) {
            listaEnteros.add((int) valor);
        }
    }

    public void reiniciarArregloCadenas( List<Object> objetos )
    {
        listaCadenas.clear();
        for (Object obj : objetos) {
            listaCadenas.add(obj.toString());
        }
    }

    public void volverPositivos( )
    {
        ListIterator<Integer> iter = listaEnteros.listIterator();
        while (iter.hasNext()) {
            int valor = iter.next();
            if (valor < 0) {
                iter.set(-valor);
            }
        }
    }

    public void organizarEnteros( )
    {
        listaEnteros.sort(Collections.reverseOrder());
    }

    public void organizarCadenas( )
    {
        Collections.sort(listaCadenas);
    }

    public int contarApariciones( int valor )
    {
        int count = 0;
        for (int num : listaEnteros) {
            if (num == valor) count++;
        }
        return count;
    }

    public int contarApariciones( String cadena )
    {
        int count = 0;
        for (String s : listaCadenas) {
            if (s.equalsIgnoreCase(cadena)) count++;
        }
        return count;
    }

    public int contarEnterosRepetidos( )
    {
        Map<Integer, Integer> frecuencia = new HashMap<>();
        for (int num : listaEnteros) {
            frecuencia.put(num, frecuencia.getOrDefault(num, 0) + 1);
        }
        int count = 0;
        for (int ocurrencias : frecuencia.values()) {
            if (ocurrencias > 1) count++;
        }
        return count;
    }

    public boolean compararArregloEnteros( int[] otroArreglo )
    {
        if (otroArreglo.length != listaEnteros.size()) return false;
        for (int i = 0; i < otroArreglo.length; i++) {
            if (otroArreglo[i] != listaEnteros.get(i)) return false;
        }
        return true;
    }

    public void generarEnteros( int cantidad, int minimo, int maximo )
    {
        listaEnteros.clear();
        Random rand = new Random();
        for (int i = 0; i < cantidad; i++) {
            int valor = rand.nextInt(maximo - minimo + 1) + minimo;
            listaEnteros.add(valor);
        }
    }
}