package uniandes.dpoo.hamburguesas.tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import uniandes.dpoo.hamburguesas.mundo.ProductoMenu;

public class ProductoMenuTest 
{
	private static final int PRECIO_BASE_MAZORCADA = 7000;
	
	private ProductoMenu productoMenu;
	
    @BeforeEach
    void setUp( ) throws Exception
    {
    	productoMenu = new ProductoMenu( "Mazorcada", PRECIO_BASE_MAZORCADA );
    }

    @AfterEach
    void tearDown( ) throws Exception
    {
    }

    @Test
    void testGetNombre( )
    {
        assertEquals( "Mazorcada", productoMenu.getNombre( ), "El nombre del producto no es el esperado." );
    }
    
    @Test
    void testGetPrecioBase( )
    {
        assertEquals( PRECIO_BASE_MAZORCADA, productoMenu.getPrecio(), "El precio del producto no es el esperado." );
    }
    
    @Test
    void testGenerarTextoFactura(  )
    {
    	String factura = 
    		    "Mazorcada\n" +
    		    "            " + PRECIO_BASE_MAZORCADA + "\n";
    	
    	assertEquals( factura, productoMenu.generarTextoFactura(), "La factura de producto no es la esperada." );
    }
}
