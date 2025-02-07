package uniandes.dpoo.estructuras.logica;

import java.util.*;

/**
 * Esta clase tiene un conjunto de métodos para practicar operaciones sobre mapas.
 *
 * Todos los métodos deben operar sobre el atributo mapaCadenas que se declara como un Map.
 * 
 * En este mapa, las llaves serán cadenas y los valores serán también cadenas. La relación entre los dos será que cada llave será igual a la cadena del valor, pero invertida.
 * 
 * El objetivo de usar el tipo Map es que sólo puedan usarse métodos de esa interfaz y no métodos adicionales provistos por la implementación concreta (HashMap).
 * 
 * No pueden agregarse nuevos atributos.
 */
public class SandboxMapas
{
    private Map<String, String> mapaCadenas;

    public SandboxMapas( )
    {
        mapaCadenas = new HashMap<String, String>( );
    }

    public List<String> getValoresComoLista( )
    {
        List<String> valores = new ArrayList<>(mapaCadenas.values());
        Collections.sort(valores);
        return valores;
    }

    public List<String> getLlavesComoListaInvertida( )
    {
        List<String> llaves = new ArrayList<>(mapaCadenas.keySet());
        llaves.sort(Collections.reverseOrder());
        return llaves;
    }

    public String getPrimera( )
    {
        if (mapaCadenas.isEmpty()) return null;
        return Collections.min(mapaCadenas.keySet());
    }

    public String getUltima( )
    {
        if (mapaCadenas.isEmpty()) return null;
        return Collections.max(mapaCadenas.values());
    }

    public Collection<String> getLlaves( )
    {
        List<String> llavesMayusculas = new ArrayList<>();
        for (String llave : mapaCadenas.keySet()) {
            llavesMayusculas.add(llave.toUpperCase());
        }
        return llavesMayusculas;
    }

    public int getCantidadCadenasDiferentes( )
    {
        return mapaCadenas.size();
    }

    public void agregarCadena( String cadena )
    {
        String llave = new StringBuilder(cadena).reverse().toString();
        mapaCadenas.putIfAbsent(llave, cadena);
    }

    public void eliminarCadenaConLLave( String llave )
    {
        mapaCadenas.remove(llave);
    }

    public void eliminarCadenaConValor( String valor )
    {
        String llaveInvertida = new StringBuilder(valor).reverse().toString();
        mapaCadenas.entrySet().removeIf(entry -> entry.getValue().equals(valor));
    }

    public void reiniciarMapaCadenas( List<Object> objetos )
    {
        mapaCadenas.clear();
        for (Object obj : objetos) {
            String cadena = obj.toString();
            String llave = new StringBuilder(cadena).reverse().toString();
            mapaCadenas.put(llave, cadena);
        }
    }

    public void volverMayusculas( )
    {
        Map<String, String> nuevoMapa = new HashMap<>();
        for (Map.Entry<String, String> entry : mapaCadenas.entrySet()) {
            nuevoMapa.put(entry.getKey().toUpperCase(), entry.getValue());
        }
        mapaCadenas = nuevoMapa;
    }

    public boolean compararValores( String[] otroArreglo )
    {
        Set<String> valoresMapa = new HashSet<>(mapaCadenas.values());
        for (String valor : otroArreglo) {
            if (!valoresMapa.contains(valor)) {
                return false;
            }
        }
        return true;
    }
} 
