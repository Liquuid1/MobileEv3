package com.example.snkr_app.views

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.performClick
import com.example.snkr_app.data.models.Zapatilla
import com.example.snkr_app.ui.theme.SNKR_AppTheme
import org.junit.Rule
import org.junit.Test
import java.util.concurrent.atomic.AtomicBoolean

class VistaDetalleProductoTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    // Datos de prueba para la zapatilla
    private val zapatillaDePrueba = Zapatilla(
        id = "test-001",
        nombre = "Zapatilla de Prueba",
        marca = "TestBrand",
        talla = 42.0,
        color = "Blanco Test",
        precio = "100 USD",
        fotoUrl = ""
    )

    @Test
    fun vistaMuestraTodosLosDetalles() {
        // 1. Preparación (Arrange)
        composeTestRule.setContent {
            SNKR_AppTheme {
                VistaDetalleProducto(zapatilla = zapatillaDePrueba, onAddToCart = {})
            }
        }

        // 2. Verificación (Assert)
        composeTestRule.onNodeWithText("Zapatilla de Prueba").assertIsDisplayed()
        composeTestRule.onNodeWithText("Marca: TestBrand").assertIsDisplayed()
        composeTestRule.onNodeWithText("Talla: 42.0").assertIsDisplayed()
        composeTestRule.onNodeWithText("Color: Blanco Test").assertIsDisplayed()
        composeTestRule.onNodeWithText("Precio: 100 USD").assertIsDisplayed()
        composeTestRule.onNodeWithContentDescription("Foto de la zapatilla").assertIsDisplayed()
        composeTestRule.onNodeWithText("Agregar al carrito").assertIsDisplayed()
    }

    @Test
    fun clickEnAgregarAlCarritoLlamaLaFuncion() {
        // 1. Preparación (Arrange)
        val fueClickeado = AtomicBoolean(false)

        composeTestRule.setContent {
            SNKR_AppTheme {
                VistaDetalleProducto(
                    zapatilla = zapatillaDePrueba,
                    onAddToCart = { fueClickeado.set(true) } // Cuando se haga clic, cambia el valor
                )
            }
        }

        // 2. Acción (Act)
        composeTestRule.onNodeWithText("Agregar al carrito").performClick()

        // 3. Verificación (Assert)
        assert(fueClickeado.get()) { "La función onAddToCart no fue llamada al hacer clic." }
    }
}