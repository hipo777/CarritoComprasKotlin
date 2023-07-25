package org.demre.carritocompraskotlin.modelos

data class Producto(val name: String, val url: String, val price: Double)

class ListaCarrito {

    private val listaProducto: MutableList<Producto> = mutableListOf()

    //Esta función agrega un producto a la lista del carrito de compras
    fun agregarProducto(name: String, url: String, price: Double) {
        listaProducto.add(Producto(name, url, price))
    }

    //Esta función recupera la lista de productos del carrito de compras
    fun obtenerListaProducto(): MutableList<Producto> {
        return listaProducto
    }

    //Esta función elimina un producto de la lista del carrito de compras
    fun eliminarProducto(name: String) {
        val producto = listaProducto.find { it.name == name }
        listaProducto.remove(producto)
    }

    //Esta función borra la lista del carrito de compras.
    fun limpiar(){
        listaProducto.clear()
    }
}
