package org.demre.carritocompraskotlin.modelos

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class AdministrarSharedPreferences(context: Context) {
    companion object{
        private const val SHARED_PREFERENCES_NOMBRE = "PreferencesCarrito"
        private const val CARRITO_LISTA_LLAVE = "CarritoLista"
    }

    private val sharedPreferences: SharedPreferences by lazy {
        context.getSharedPreferences(SHARED_PREFERENCES_NOMBRE, Context.MODE_PRIVATE)
    }

    //Esta función guarda la lista de productos del carrito de compras en SharedPreferences
    fun guardarListaCarrito(listaCarrito: ListaCarrito) {
        val listaProductoJson = Gson().toJson(listaCarrito.obtenerListaProducto())
        sharedPreferences.edit().putString(CARRITO_LISTA_LLAVE, listaProductoJson).apply()
    }
    //Esta función recupera la lista de productos del carrito de compras de SharedPreferences.
    fun obtenerListaCarrito(): ListaCarrito {
        val listaProductoJson = sharedPreferences.getString(CARRITO_LISTA_LLAVE, null)
        return if (!listaProductoJson.isNullOrEmpty()) {
            val tipo = object : TypeToken<List<Producto>>() {}.type
            val listaProducto = Gson().fromJson<List<Producto>>(listaProductoJson, tipo)
            ListaCarrito().apply {
                obtenerListaProducto().addAll(listaProducto)
            }
        } else {
            ListaCarrito()
        }
    }
    //Esta función vacía el carrito de compras.
    fun vaciarCarrito() {
        sharedPreferences.edit().remove(CARRITO_LISTA_LLAVE).apply()
    }

}
