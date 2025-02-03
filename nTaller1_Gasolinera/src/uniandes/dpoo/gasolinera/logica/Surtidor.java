package uniandes.dpoo.gasolinera.logica;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Esta clase representa a un surtidor de gasolina en la gasolinera.
 * 
 * Todos los surtidores entregan gasolina de los mismos tanques, pero cada surtidor lleva la cuenta de cuánto ha entregado, de cada tipo de gasolina.
 *
 */
public class Surtidor {
    private Map<String, Double> galonesVendidos;
    private Map<String, TipoGasolina> tiposGasolina;
    private Empleado empleadoAsignado;

    public Surtidor(Map<String, TipoGasolina> tiposGasolina, Empleado empleado) {
        this.empleadoAsignado = empleado;
        this.tiposGasolina = new HashMap<>(tiposGasolina);
        this.galonesVendidos = new HashMap<>();
        
        for (String nombreTipo : tiposGasolina.keySet()) {
            galonesVendidos.put(nombreTipo, 0.0);
        }
    }

    public Empleado getEmpleadoAsignado() {
        return empleadoAsignado;
    }

    public double getGalonesVendidos(String nombreTipoGasolina) {
        return galonesVendidos.getOrDefault(nombreTipoGasolina, 0.0);
    }

    public void cambiarGalonesVendidos(String nombreTipoGasolina, double cantidad) {
        galonesVendidos.put(nombreTipoGasolina, cantidad);
    }

    public String[] getTiposGasolina() {
        Set<String> conjuntoLlaves = tiposGasolina.keySet();
        return conjuntoLlaves.toArray(new String[0]);
    }

    public TipoGasolina getTipoGasolina(String nombreTipoGasolina) {
        return tiposGasolina.get(nombreTipoGasolina);
    }

    public int venderGasolina(String nombreTipoGasolina, double cantidadEntregada) {
        TipoGasolina tipo = tiposGasolina.get(nombreTipoGasolina);
        if (tipo == null) {
            throw new IllegalArgumentException("Tipo de gasolina no encontrado");
        }
        int precio = (int) Math.round(tipo.getPrecioPorGalon() * cantidadEntregada);
        empleadoAsignado.agregarDinero(precio);
        
        double cantidadAnterior = galonesVendidos.getOrDefault(nombreTipoGasolina, 0.0);
        galonesVendidos.put(nombreTipoGasolina, cantidadAnterior + cantidadEntregada);
        
        return precio;
    }
}
