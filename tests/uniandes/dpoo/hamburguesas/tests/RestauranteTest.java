package uniandes.dpoo.hamburguesas.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import uniandes.dpoo.hamburguesas.excepciones.HamburguesaException;
import uniandes.dpoo.hamburguesas.excepciones.NoHayPedidoEnCursoException;
import uniandes.dpoo.hamburguesas.excepciones.YaHayUnPedidoEnCursoException;
import uniandes.dpoo.hamburguesas.mundo.Combo;
import uniandes.dpoo.hamburguesas.mundo.Ingrediente;
import uniandes.dpoo.hamburguesas.mundo.Pedido;
import uniandes.dpoo.hamburguesas.mundo.ProductoAjustado;
import uniandes.dpoo.hamburguesas.mundo.ProductoMenu;
import uniandes.dpoo.hamburguesas.mundo.Restaurante;

public class RestauranteTest 
{
	private static final int PRECIO_BASE_MAZORCADA = 7000;
	private static final int PRECIO_INGREDIENTE_POLLO = 6500;
	private static final int PRECIO_INGREDIENTE_QUESO = 4000;
	private static final int PRECIO_PAPAS_MEDIANAS = 5000;
	private static final int PRECIO_BEBIDA = 3500;
	
	private static final double DESCUENTO = 0.1;
	
	private Restaurante restaurante;
	
	private ProductoMenu producto1;
	private ProductoAjustado producto2;
	private Combo producto3;
	
	private ArrayList<ProductoMenu> comboItems;
	
    @BeforeEach
    void setUp( ) throws Exception
    {	
    	restaurante = new Restaurante( );
    	
    	producto1 = new ProductoMenu( "Mazorcada", PRECIO_BASE_MAZORCADA );
    	
    	producto2 = new ProductoAjustado( producto1 );
    	producto2.agregarIngrediente( new Ingrediente("Pollo", PRECIO_INGREDIENTE_POLLO) );
    	producto2.eliminarIngrediente( new Ingrediente("Queso", PRECIO_INGREDIENTE_QUESO) );
    	
    	comboItems= agregarItems( );
    	producto3 = new Combo( "Mazorca con papá", DESCUENTO, comboItems );
    }
    
    private ArrayList<ProductoMenu> agregarItems( )
    {
    	ArrayList<ProductoMenu> items = new ArrayList<ProductoMenu>( );
    	
    	items.add( producto1 );
    	items.add( new ProductoMenu( "Papas Medianas", PRECIO_PAPAS_MEDIANAS ) );
    	items.add( new ProductoMenu( "Bebida", PRECIO_BEBIDA ) );
    	
    	return items;
    }

    private void agregarProductos( Pedido pedido )
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
    void testIniciarPedido( ) 
    {
    	try {
			restaurante.iniciarPedido( "Ruben Doblas" , "Av. Meritxell, 93");
			
		} catch (YaHayUnPedidoEnCursoException e) {
			fail( "No se esperaba una YaHayUnPedidoEnCursoException" );
		}

    	agregarProductos( restaurante.getPedidoEnCurso( ) );
    }
    
    @Test
    void testYaHayUnPedidoEnCursoException() 
    {
        try {
            restaurante.iniciarPedido( "Ruben Doblas", "Av. Meritxell, 93" );
            
        } catch ( YaHayUnPedidoEnCursoException e ) {
            fail( "No se esperaba una excepción al iniciar el primer pedido." );
        }

        
        try {
            restaurante.iniciarPedido( "Radamel Falcao", "Calle 10 #11-24" );
            fail( "Se esperaba una YaHayUnPedidoEnCursoException." );
            
        } catch ( YaHayUnPedidoEnCursoException e ) {
            assertEquals( "Ya existe un pedido en curso, para el cliente Ruben Doblas así que no se puede crear un pedido para Radamel Falcao", e.getMessage(), "El mensaje de la excepción no es el esperado.");
        }
    }

    @Test
    void testCerrarYGuardarPedido( )
    {    	
    	try {
			restaurante.iniciarPedido( "Ruben Doblas" , "Av. Meritxell, 93");
			
		} catch (YaHayUnPedidoEnCursoException e) {
			fail( "No se esperaba una excepción al iniciar el primer pedido." );
		}
    	
    	Pedido pedidoEnCurso = restaurante.getPedidoEnCurso( );
    	agregarProductos( pedidoEnCurso );
    	
    	try {
			restaurante.cerrarYGuardarPedido( );
			
			assertTrue( restaurante.getPedidos( ).contains( pedidoEnCurso ), "El pedido cerrado no ha sido agregado a la lista correctamente." );
			assertEquals( pedidoEnCurso, restaurante.getPedido( 0 ), "No se pudo obtener un pedido dado su ID." );
			
		} catch (NoHayPedidoEnCursoException e) {
			fail( "No esperaba una NoHayPedidoEnCursoException." );
			
		} catch (IOException e) {
			fail( "No se esperaba una IOException." );
		}
    }
    
    @Test
    void testNoHayPedidoEnCursoException( )
    {
    	try {
			restaurante.cerrarYGuardarPedido( );
			fail( "Se esperaba una NoHayPedidoEnCursoException." );
			
		} catch ( NoHayPedidoEnCursoException e ) {
			assertEquals( "Actualmente no hay un pedido en curso", e.getMessage(), "El mensaje de la excepción no es el esperado." );
			
		} catch (IOException e) {
			fail( "No se esperaba una IOException." );
		}
    }
    
    @Test
    void testCargarInformacionRestaurante( )
    {
    	try {
			restaurante.cargarInformacionRestaurante( new File("./data/ingredientes.txt"), new File("./data/menu.txt"), new File("./data/combos.txt") );
			
			assertFalse( restaurante.getMenuBase( ).isEmpty( ), "No se ha agregado el menu correctamente a la lista." );
	    	assertFalse( restaurante.getIngredientes( ).isEmpty( ), "No se han agregado los ingredientes correctamente a la lista." );
	    	assertFalse( restaurante.getMenuCombos( ).isEmpty( ), "No se han agregado los combos correctamente a la lista." );
			
		} catch (NumberFormatException e) {
			fail( "No se esperaba una NumberFormatException." );
			
		} catch (HamburguesaException e) {
			fail( "No se esperaba una HamburguesaException." );
			
		} catch (IOException e) {
			fail( "No se esperaba una IOException." );
		}
    }
    
    @Test
    void testProductoRepetidoEnComboException( )
    {
    	try {
			restaurante.cargarInformacionRestaurante( new File("./data/ingredientes.txt"), new File("./data/menu.txt"), new File("./dataTests/combosInvalidos.txt") );
			
		} catch (NumberFormatException e) {
			fail( "No se esperaba una NumberFormatException." );
			
		} catch (HamburguesaException e) {
			assertEquals( "El producto combo todoterreno está repetido", e.getMessage(), "El mensaje de la excepción no es el esperado." );
			
		} catch (IOException e) {
			fail( "No se esperaba una IOException." );
		}
    }
    
    @Test
    void testProductoRepetidoEnMenuException( )
    {
    	try {
			restaurante.cargarInformacionRestaurante( new File("./data/ingredientes.txt"), new File("./dataTests/menuRepetido.txt"), new File("./data/combos.txt") );
			
		} catch (NumberFormatException e) {
			fail( "No se esperaba una NumberFormatException." );
			
		} catch (HamburguesaException e) {
			assertEquals( "El producto mexicana está repetido", e.getMessage(), "El mensaje de la excepción no es el esperado." );
			
		} catch (IOException e) {
			fail( "No se esperaba una IOException." );
		}
    }
    
    @Test
    void testIngredienteRepetidoException( )
    {
    	try {
			restaurante.cargarInformacionRestaurante( new File("./dataTests/ingredientesInvalidos.txt"), new File("./data/menu.txt"), new File("./data/combos.txt") );
			
		} catch (NumberFormatException e) {
			fail( "No se esperaba una NumberFormatException." );
			
		} catch (HamburguesaException e) {
			assertEquals( "El ingrediente pepinillos está repetido", e.getMessage(), "El mensaje de la excepción no es el esperado." );
			
		} catch (IOException e) {
			fail( "No se esperaba una IOException." );
		}
    }
    
    @Test
    void testProductoFaltanteException( )
    {
    	try {
			restaurante.cargarInformacionRestaurante( new File("./data/ingredientes.txt"), new File("./dataTests/menuInvalido.txt"), new File("./data/combos.txt") );
			
		} catch (NumberFormatException e) {
			fail( "No se esperaba una NumberFormatException." );
			
		} catch (HamburguesaException e) {
			assertEquals( "El producto todoterreno no aparece en la información del restaurante", e.getMessage(), "El mensaje de la excepción no es el esperado." );
			
		} catch (IOException e) {
			fail( "No se esperaba una IOException." );
		}
    }
}
