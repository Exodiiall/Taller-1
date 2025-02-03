package uniandes.dpoo.gasolinera.logica;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import uniandes.dpoo.gasolinera.exceptions.GasolinaInsuficienteException;
import uniandes.dpoo.gasolinera.utils.Sorteo;

/**
 * Esta clase tiene la información de una Gasolinera incluyendo los tipos de gasolina, los empleados y los surtidores
 */
public class Gasolinera {
    private Surtidor[] surtidores;
    private Map<String, TipoGasolina> tiposGasolina;
    private Map<String, Empleado> empleados;

    public Gasolinera(int cantidadSurtidores, List<TipoGasolina> listaTiposGasolina, String[] nombresEmpleados) {
        this.tiposGasolina = new HashMap<>();
        for (TipoGasolina tipo : listaTiposGasolina) {
            this.tiposGasolina.put(tipo.getNombre(), tipo);
        }

        this.empleados = new HashMap<>();
        for (String nombre : nombresEmpleados) {
            this.empleados.put(nombre, new Empleado(nombre));
        }

        this.surtidores = new Surtidor[cantidadSurtidores];
        for (int i = 0; i < cantidadSurtidores; i++) {
            Empleado seleccionado = Sorteo.seleccionarAlAzar(empleados.values().toArray(new Empleado[0]));
            this.surtidores[i] = new Surtidor(tiposGasolina, seleccionado);
        }
    }

    private Gasolinera(List<Surtidor> surtidores, Collection<TipoGasolina> tiposGasolina, Collection<Empleado> empleados) {
        this.tiposGasolina = new HashMap<>();
        for (TipoGasolina tipo : tiposGasolina) {
            this.tiposGasolina.put(tipo.getNombre(), tipo);
        }

        this.empleados = new HashMap<>();
        for (Empleado empleado : empleados) {
            this.empleados.put(empleado.getNombre(), empleado);
        }

        this.surtidores = surtidores.toArray(new Surtidor[0]);
    }

    public int getCantidadSurtidores() {
        return surtidores.length;
    }

    public Surtidor getSurtidor(int numSurtidor) {
        if (numSurtidor < 0 || numSurtidor >= surtidores.length) {
            throw new IllegalArgumentException("Número de surtidor inválido");
        }
        return surtidores[numSurtidor];
    }

    public TipoGasolina getTipoGasolina(String nombreTipoGasolina) {
        return tiposGasolina.get(nombreTipoGasolina);
    }

    public Collection<TipoGasolina> getTiposGasolina() {
        return tiposGasolina.values();
    }

    public Empleado getEmpleado(String nombreEmpleado) {
        return empleados.get(nombreEmpleado);
    }

    public Collection<Empleado> getEmpleados() {
        return empleados.values();
    }

    public int venderGasolinaPorCantidad(String nombreTipoGasolina, double cantidadSolicitada, int numeroSurtidor) {
        if (!tiposGasolina.containsKey(nombreTipoGasolina)) {
            throw new IllegalArgumentException("Tipo de gasolina no encontrado");
        }
        if (numeroSurtidor < 0 || numeroSurtidor >= surtidores.length) {
            throw new IllegalArgumentException("Número de surtidor inválido");
        }

        Surtidor surtidor = surtidores[numeroSurtidor];
        TipoGasolina tipo = tiposGasolina.get(nombreTipoGasolina);
        double cantidadEntregada;

        try {
            tipo.despacharGasolina(cantidadSolicitada);
            cantidadEntregada = cantidadSolicitada;
        } catch (GasolinaInsuficienteException e) {
            cantidadEntregada = e.getCantidadDisponible();
            try {
                tipo.despacharGasolina(cantidadEntregada);
            } catch (GasolinaInsuficienteException ignored) {}
        }

        return surtidor.venderGasolina(nombreTipoGasolina, cantidadEntregada);
    }

    public int venderGasolinaPorPrecio(String nombreTipoGasolina, int valorSolicitado, int numeroSurtidor) {
        if (!tiposGasolina.containsKey(nombreTipoGasolina)) {
            throw new IllegalArgumentException("Tipo de gasolina no encontrado");
        }
        if (numeroSurtidor < 0 || numeroSurtidor >= surtidores.length) {
            throw new IllegalArgumentException("Número de surtidor inválido");
        }

        TipoGasolina tipo = tiposGasolina.get(nombreTipoGasolina);
        double cantidadSolicitada = valorSolicitado / (double) tipo.getPrecioPorGalon();
        return venderGasolinaPorCantidad(nombreTipoGasolina, cantidadSolicitada, numeroSurtidor);
    }
    /**
     * Guarga la información actual de la gasolinera en un archivo.
     * 
     * Si el archivo ya existe, se sobreescribe.
     * @param archivo El archivo donde se guardará la información
     * @throws IOException Se lanza esta excepción si hay problemas escribiendo en el archivo
     */
    public void guardarEstado( File archivo ) throws IOException
    {
        PrintWriter writer = new PrintWriter( archivo );

        // Guardar la información de los tipos de gasolina
        for( TipoGasolina tipo : tiposGasolina.values( ) )
        {
            writer.println( "tipo:" + tipo.getNombre( ) + ":" + tipo.getPrecioPorGalon( ) + ":" + tipo.getCantidadDisponible( ) );
        }

        // Guardar la información de los surtidores
        for( int i = 0; i < surtidores.length; i++ )
        {
            Surtidor surtidor = surtidores[ i ];
            writer.print( "surtidor:" + surtidor.getEmpleadoAsignado( ).getNombre( ) );
            for( TipoGasolina tipo : tiposGasolina.values( ) )
            {
                writer.print( ":" + tipo.getNombre( ) + ":" + surtidor.getGalonesVendidos( tipo.getNombre( ) ) );
            }
            writer.println( );
        }

        // Guardar la información de los empleados
        for( Empleado emp : empleados.values( ) )
        {
            writer.println( "empleado:" + emp.getNombre( ) + ":" + emp.getCantidadDinero( ) );
        }

        writer.close( );
    }

    /**
     * Carga toda la información de una gasolinera a partir de un archivo y retorna una nueva Gasolinera inicializada con esa información
     * @param archivo El archivo que contiene la información que se va a cargar
     * @return Una nueva gasolinera con su estado inicializado con la información del archivo
     * @throws FileNotFoundException Se lanza esta excepción si el archivo no se encuentra
     * @throws IOException Se lanza esta excepción si el archivo no se puede leer
     * @throws NumberFormatException Se lanza esta excepción si alguno de los números dentro del archivo tiene el formato equivocado
     */
    public static Gasolinera cargarEstado( File archivo ) throws FileNotFoundException, IOException, NumberFormatException
    {
        Map<String, TipoGasolina> tipos = new HashMap<String, TipoGasolina>( );
        Map<String, Empleado> empleados = new HashMap<String, Empleado>( );
        List<Surtidor> surtidores = new LinkedList<Surtidor>( );

        BufferedReader br = new BufferedReader( new FileReader( archivo ) );
        String line = br.readLine( );
        while( line != null )
        {
            String[] partes = line.split( ":" );
            if( partes[ 0 ].equals( "tipo" ) )
            {
                String nombre = partes[ 1 ];
                int precio = Integer.parseInt( partes[ 2 ] );
                double cantidad = Double.parseDouble( partes[ 3 ] );
                tipos.put( nombre, new TipoGasolina( nombre, precio, cantidad ) );
            }
            else if( partes[ 0 ].equals( "surtidor" ) )
            {
                String nombreEmpleado = partes[ 1 ];
                if( !empleados.containsKey( nombreEmpleado ) )
                {
                    empleados.put( nombreEmpleado, new Empleado( nombreEmpleado ) );
                }
                Empleado empleadoAsignado = empleados.get( nombreEmpleado );
                Surtidor nuevoSurtidor = new Surtidor( tipos, empleadoAsignado );
                for( int pos = 2; pos < partes.length; pos += 2 )
                {
                    String tipo = partes[ pos ];
                    double cantidad = Double.parseDouble( partes[ pos + 1 ] );
                    nuevoSurtidor.cambiarGalonesVendidos( tipo, cantidad );
                }
                surtidores.add( nuevoSurtidor );
            }
            else if( partes[ 0 ].equals( "empleado" ) )
            {
                String nombreEmpleado = partes[ 1 ];
                int dinero = Integer.parseInt( partes[ 2 ] );
                if( !empleados.containsKey( nombreEmpleado ) )
                {
                    empleados.put( nombreEmpleado, new Empleado( nombreEmpleado ) );
                }
                Empleado nuevoEmpleado = empleados.get( nombreEmpleado );
                nuevoEmpleado.agregarDinero( dinero );
            }

            line = br.readLine( );
        }
        br.close( );

        Gasolinera nuevaGasolinera = new Gasolinera( surtidores, tipos.values( ), empleados.values( ) );
        return nuevaGasolinera;
    }

}
