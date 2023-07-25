package org.demre.carritocompraskotlin.vista

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import org.demre.carritocompraskotlin.R
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import org.demre.carritocompraskotlin.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navegacionInferior = binding.bottomNavigation
        val navegacionFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        val controlarNavegacion = navegacionFragment.navController
        navegacionInferior.setupWithNavController(controlarNavegacion)
        navegacionInferior.selectedItemId = R.id.itemProductos

        navegacionInferior.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.itemProductos -> {
                    when (controlarNavegacion.currentDestination?.id) {
                        R.id.Producto -> {
                            controlarNavegacion.navigate(R.id.action_itemSingleProduct_to_itemProducts)
                            true
                        }

                        R.id.listaCarrito -> {
                            controlarNavegacion.navigate(R.id.action_cart_to_itemProducts)
                            true
                        }

                        else -> {
                            false
                        }
                    }
                }

                R.id.listaCarrito -> {
                    when (controlarNavegacion.currentDestination?.id) {
                        R.id.Producto -> {
                            controlarNavegacion.navigate(R.id.action_itemSingleProduct_to_cart)
                            true
                        }

                        R.id.itemProductos -> {
                            controlarNavegacion.navigate(R.id.action_itemProducts_to_cart)
                            true
                        }

                        else -> {
                            false
                        }
                    }
                }

                else -> false
            }
        }
        controlarNavegacion.addOnDestinationChangedListener { _, destination, _ ->
            supportActionBar?.title = when (destination.id) {
                R.id.itemProductos -> "Nuestros Productos"
                R.id.Producto -> ""
                R.id.listaCarrito -> "Mi Carrito"
                else -> ""
            }
        }
    }
}