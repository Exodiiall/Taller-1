package uniandes.dpoo.estructuras.logica;

import java.util.*;
/**
 * Esta clase tiene un conjunto de métodos para practicar operaciones sobre conjuntos implementados usando un árbol (TreeSet).
 *
 * Todos los métodos deben operar sobre el atributo arbolCadenas que se declara como un NavigableSet.
 * 
 * El objetivo de usar el tipo NavigableSet es que sólo puedan usarse métodos de esa interfaz y no métodos adicionales provistos por la implementación concreta (TreeSet).
 * 
 * A diferencia de un Set, en un NavigableSet existe una noción de orden que en este caso corresponde al órden lexicográfico.
 * 
 * No pueden agregarse nuevos atributos.
 */
public class SandboxConjuntos
{
    private NavigableSet<String> arbolCadenas;

    public SandboxConjuntos( )
    {
        arbolCadenas = new TreeSet<String>( );
    }

    public List<String> getCadenasComoLista( )
    {
        return new ArrayList<>(arbolCadenas);
    }

    public List<String> getCadenasComoListaInvertida( )
    {
        return new ArrayList<>(arbolCadenas.descendingSet());
    }

    public String getPrimera( )
    {
        return arbolCadenas.isEmpty() ? null : arbolCadenas.first();
    }

    public String getUltima( )
    {
        return arbolCadenas.isEmpty() ? null : arbolCadenas.last();
    }

    public Collection<String> getSiguientes( String cadena )
    {
        return arbolCadenas.tailSet(cadena, true);
    }

    public int getCantidadCadenas( )
    {
        return arbolCadenas.size();
    }

    public void agregarCadena( String cadena )
    {
        arbolCadenas.add(cadena);
    }

    public void eliminarCadena( String cadena )
    {
        arbolCadenas.remove(cadena);
    }

    public void eliminarCadenaSinMayusculasOMinusculas( String cadena )
    {
        arbolCadenas.removeIf(s -> s.equalsIgnoreCase(cadena));
    }

    public void eliminarPrimera( )
    {
        if (!arbolCadenas.isEmpty()) {
            arbolCadenas.pollFirst();
        }
    }

    public void reiniciarConjuntoCadenas( List<Object> objetos )
    {
        arbolCadenas.clear();
        for (Object obj : objetos) {
            arbolCadenas.add(obj.toString());
        }
    }

    public void volverMayusculas( )
    {
        NavigableSet<String> mayusculas = new TreeSet<>();
        for (String cadena : arbolCadenas) {
            mayusculas.add(cadena.toUpperCase());
        }
        arbolCadenas = mayusculas;
    }

    public TreeSet<String> invertirCadenas( )
    {
        return new TreeSet<>(arbolCadenas.descendingSet());
    }

    public boolean compararElementos( String[] otroArreglo )
    {
        for (String cadena : otroArreglo) {
            if (!arbolCadenas.contains(cadena)) {
                return false;
            }
        }
        return true;
    }
} 
