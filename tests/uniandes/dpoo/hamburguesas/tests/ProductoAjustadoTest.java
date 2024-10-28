package uniandes.dpoo.hamburguesas.tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import uniandes.dpoo.hamburguesas.mundo.Ingrediente;
import uniandes.dpoo.hamburguesas.mundo.ProductoAjustado;
import uniandes.dpoo.hamburguesas.mundo.ProductoMenu;

public class ProductoAjustadoTest 
{
	private static final int PRECIO_BASE_MAZORCADA = 7000;
	private static final int PRECIO_INGREDIENTE_POLLO = 6500;
	private static final int PRECIO_INGREDIENTE_QUESO = 4000;
	private static final int PRECIO_INGREDIENTE_SALSA = 2500;
	
	private ProductoMenu base;
	private ProductoAjustado productoAjustado;
	
	private Ingrediente ingredienteAgregar1;
	private Ingrediente ingredienteAgregar2;
	private Ingrediente ingredienteEliminar;
	
    @BeforeEach
    void setUp( ) throws Exception
    {
    	base = new ProductoMenu( "Mazorcada", PRECIO_BASE_MAZORCADA );
    	productoAjustado = new ProductoAjustado( base );
    	
    	ingredienteAgregar1 = new Ingrediente( "Pollo", PRECIO_INGREDIENTE_POLLO );
    	ingredienteAgregar2 = new Ingrediente( "Queso", PRECIO_INGREDIENTE_QUESO );
    	ingredienteEliminar = new Ingrediente( "Salsa de la casa", PRECIO_INGREDIENTE_SALSA );
    	
    	productoAjustado.agregarIngrediente( ingredienteAgregar1 );
    	productoAjustado.agregarIngrediente( ingredienteAgregar2 );
    	productoAjustado.eliminarIngrediente( ingredienteEliminar );
    }

    @AfterEach
    void tearDown( ) throws Exception
    {
    }

    @Test
    void testGetNombre( )
    {
        assertEquals( "Mazorcada", productoAjustado.getNombre( ), "El nombre del producto no es el esperado." );
    }
    
    @Test
    void testAgregarIngrediente( )
    {
    	assertTrue( productoAjustado.getAgregados( ).contains( ingredienteAgregar1 ), "El ingrediente 'Pollo' no fue agregado correctamente." );
    	assertTrue( productoAjustado.getAgregados( ).contains( ingredienteAgregar2 ), "El ingrediente 'Queso' no fue agregado correctamente." );
    }
    
    @Test
    void testEliminarIngrediente( )
    {
    	assertTrue( productoAjustado.getEliminados( ).contains( ingredienteEliminar ), "El ingrediente no fue eliminado correctamente." );
    }
    
    @Test
    void testGetPrecio( )
    {    
    	int precioEsperado = PRECIO_BASE_MAZORCADA + PRECIO_INGREDIENTE_POLLO + PRECIO_INGREDIENTE_QUESO;
    	
    	assertEquals( precioEsperado, productoAjustado.getPrecio(), "El precio del producto no es el esperado." );
    }
    
    @Test
    void testGenerarTextoFactura(  )
    {	
    	int precio = PRECIO_BASE_MAZORCADA + PRECIO_INGREDIENTE_POLLO + PRECIO_INGREDIENTE_QUESO;
    	
    	String factura = 
    		    "Mazorcada\n" +
    		    "    +Pollo                " + PRECIO_INGREDIENTE_POLLO + "\n" +
    		    "    +Queso                " + PRECIO_INGREDIENTE_QUESO + "\n" +
    		    "    -Salsa de la casa\n" +
    		    "            " + precio + "\n";

    	
    	assertEquals( factura, productoAjustado.generarTextoFactura(), "La factura de producto no es la esperada." );
    }
}
