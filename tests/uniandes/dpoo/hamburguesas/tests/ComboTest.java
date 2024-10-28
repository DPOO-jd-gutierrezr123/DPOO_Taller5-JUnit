package uniandes.dpoo.hamburguesas.tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import uniandes.dpoo.hamburguesas.mundo.Combo;
import uniandes.dpoo.hamburguesas.mundo.ProductoMenu;

public class ComboTest 
{
	private static final int PRECIO_BASE_MAZORCADA = 7000;
	private static final int PRECIO_PAPAS_MEDIANAS = 5000;
	private static final int PRECIO_BEBIDA = 3500;
	
	private static final double DESCUENTO = 0.1;
	
	private Combo combo;
	private ArrayList<ProductoMenu> comboItems = agregarItems( );
	
	private ArrayList<ProductoMenu> agregarItems( )
	{
		ArrayList<ProductoMenu> items = new ArrayList<ProductoMenu>( );
		
		items.add( new ProductoMenu( "Mazorcada", PRECIO_BASE_MAZORCADA ) );
		items.add( new ProductoMenu( "Papas Medianas", PRECIO_PAPAS_MEDIANAS ) );
		items.add( new ProductoMenu( "Bebida", PRECIO_BEBIDA ) );
		
		return items;
	}
	
    @BeforeEach
    void setUp( ) throws Exception
    {
    	combo = new Combo( "Mazorca con papá", DESCUENTO, comboItems );
    }

    @AfterEach
    void tearDown( ) throws Exception
    {
    }

    @Test
    void testGetNombre( )
    {
        assertEquals( "Mazorca con papá", combo.getNombre( ), "El nombre del combo no es el esperado." );
    }
    
    @Test
    void testGetPrecioBase( )
    {
    	int precioEsperado = (int) ( DESCUENTO * (PRECIO_BASE_MAZORCADA + PRECIO_PAPAS_MEDIANAS + PRECIO_BEBIDA) );
    	
        assertEquals( precioEsperado, combo.getPrecio(), "El precio del combo no es el esperado." );
    }
    
    @Test
    void testGenerarTextoFactura(  )
    {
    	int precio = (int) ( DESCUENTO * (PRECIO_BASE_MAZORCADA + PRECIO_PAPAS_MEDIANAS + PRECIO_BEBIDA) );
    	
    	String factura = 
    		    "Combo Mazorca con papá\n" +
    		    " Descuento: " + DESCUENTO + "\n" +
    		    "            " + precio + "\n";

    	
    	assertEquals( factura, combo.generarTextoFactura(), "La factura del combo no es la esperada." );
    }
}
