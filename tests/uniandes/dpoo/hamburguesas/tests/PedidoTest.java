package uniandes.dpoo.hamburguesas.tests;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import uniandes.dpoo.hamburguesas.mundo.Combo;
import uniandes.dpoo.hamburguesas.mundo.Ingrediente;
import uniandes.dpoo.hamburguesas.mundo.Pedido;
import uniandes.dpoo.hamburguesas.mundo.ProductoAjustado;
import uniandes.dpoo.hamburguesas.mundo.ProductoMenu;

public class PedidoTest 
{
	private static final int PRECIO_BASE_MAZORCADA = 7000;
	private static final int PRECIO_INGREDIENTE_POLLO = 6500;
	private static final int PRECIO_INGREDIENTE_QUESO = 4000;
	private static final int PRECIO_PAPAS_MEDIANAS = 5000;
	private static final int PRECIO_BEBIDA = 3500;
	
	private static final double DESCUENTO = 0.1;
    private static final double IVA = 0.19;
    
    private static final String CARPETA_FACTURAS = "./facturas/";
    private static final String PREFIJO_FACTURAS = "factura_";
	
	private Pedido pedido;
	
	private ProductoMenu producto1;
	private ProductoAjustado producto2;
	private Combo producto3;
	
	private ArrayList<ProductoMenu> comboItems;
	
    @BeforeEach
    void setUp( ) throws Exception
    {
    	pedido = new Pedido( "Ruben Doblas", "Av. Meritxell, 93" );
    	
    	producto1 = new ProductoMenu( "Mazorcada", PRECIO_BASE_MAZORCADA );
    	
    	producto2 = new ProductoAjustado( producto1 );
    	producto2.agregarIngrediente( new Ingrediente("Pollo", PRECIO_INGREDIENTE_POLLO) );
    	producto2.eliminarIngrediente( new Ingrediente("Queso", PRECIO_INGREDIENTE_QUESO) );
    	
    	comboItems= agregarItems( );
    	producto3 = new Combo( "Mazorca con papá", DESCUENTO, comboItems );
    	
    	agregarProductos( );
    }
	
	private ArrayList<ProductoMenu> agregarItems( )
	{
		ArrayList<ProductoMenu> items = new ArrayList<ProductoMenu>( );
		
		items.add( producto1 );
		items.add( new ProductoMenu( "Papas Medianas", PRECIO_PAPAS_MEDIANAS ) );
		items.add( new ProductoMenu( "Bebida", PRECIO_BEBIDA ) );
		
		return items;
	}
	
	private void agregarProductos( )
	{
		pedido.agregarProducto( producto1 );
		pedido.agregarProducto( producto2 );
		pedido.agregarProducto( producto3 );
	}

    @AfterEach
    void tearDown( ) throws Exception
    {
    }

    @Test
    void testGetIdPedido( )
    {
        assertEquals( 6, pedido.getIdPedido( ), "El ID del pedido no es el esperado." );
    }
    
    @Test
    void testGetNombreCliente( )
    {
        assertEquals( "Ruben Doblas", pedido.getNombreCliente(), "El nombre del cliente no es el esperado." );
    }
    
    @Test
    void testAgregarProducto( )
    {
    	assertTrue( pedido.getProductos( ).contains( producto1 ), "No se ha agregado correctamente el Producto Menú." );
    	assertTrue( pedido.getProductos( ).contains( producto2 ), "No se ha agregado correctamente el Producto Ajustado." );
    	assertTrue( pedido.getProductos( ).contains( producto3 ), "No se ha agregado correctamente el Combo." );
    }
    
    @Test
    void testGetPrecioTotalPedido( )
    {
    	int precioNeto = producto1.getPrecio( ) + producto2.getPrecio( ) + producto3.getPrecio( );
    	int precioEsperado = (int) ( IVA * precioNeto ) + precioNeto;
    	
    	assertEquals( precioEsperado, pedido.getPrecioTotalPedido(), "El precio total del pedido no es el esperado." );
    }
    
    @Test
    void testGenerarTextoFactura(  )
    {
    	String factura = 
    		    "Cliente: Ruben Doblas\n"
    		    + "Dirección: Av. Meritxell, 93\n"
    		    + "----------------\n"
    		    + "Mazorcada\n"
    		    + "            7000\n"
    		    + "Mazorcada\n"
    		    + "    +Pollo                6500\n"
    		    + "    -Queso\n"
    		    + "            13500\n"
    		    + "Combo Mazorca con papá\n"
    		    + " Descuento: 0.1\n"
    		    + "            1550\n"
    		    + "----------------\n"
    		    + "Precio Neto:  22050\n"
    		    + "IVA:          4189\n"
    		    + "Precio Total: 26239\n";
    	
    	assertEquals( factura, pedido.generarTextoFactura(), "La factura de producto no es la esperada." );
    	
    	String nombreArchivo = PREFIJO_FACTURAS + pedido.getIdPedido( ) + ".txt";
    	try {
			pedido.guardarFactura( new File( CARPETA_FACTURAS + nombreArchivo ) );
			
		} catch (FileNotFoundException e) {
			fail( "No se esperaba una FileNotFoundException." );
		}
    }
}
