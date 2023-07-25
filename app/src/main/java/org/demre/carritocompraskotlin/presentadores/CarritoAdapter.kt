package org.demre.carritocompraskotlin.presentadores

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import org.demre.carritocompraskotlin.R
import org.demre.carritocompraskotlin.databinding.CarritoItemLayoutBinding
import org.demre.carritocompraskotlin.modelos.AdministrarSharedPreferences
import org.demre.carritocompraskotlin.modelos.Producto

class CarritoAdapter (private val contexto: Context,
                      private val listaProductos: MutableList<Producto>,
                      private val recibirTotalPrecio: () -> Unit
): RecyclerView.Adapter<CarritoAdapter.CarritoViewHolder>(){

    private var totalPrecio: Double = 0.0

    //Esta función crea un nuevo CarritoViewHolder para cada elemento en la lista de productos
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarritoViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return CarritoViewHolder(layoutInflater.inflate(R.layout.carrito_item_layout, parent, false))
    }

    //Esta función actualiza el CarritoViewHolder con los datos del producto correspondiente
    override fun onBindViewHolder(holder: CarritoViewHolder, position: Int) {
        val product = listaProductos[position]

        holder.entregar(product)
        holder.eliminaProducto(this, position)
    }
    override fun getItemCount(): Int = listaProductos.size

    //Esta función elimina un producto de la lista de productos y actualiza la vista
    fun eliminarProducto(position: Int) {
        val zapatoParaEliminar = listaProductos[position]

        // Elimina y guarda lista actualizada del carrito
        val preferenciaGuardada = AdministrarSharedPreferences(contexto)
        val listaCarrito = preferenciaGuardada.obtenerListaCarrito()
        listaCarrito.eliminarProducto(zapatoParaEliminar.name)
        preferenciaGuardada.guardarListaCarrito(listaCarrito)

        // Eliminar el zapato de la lista de productos
        listaProductos.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, listaProductos.size)
        recibirTotalPrecio.invoke()
    }

    //Esta función actualiza la lista de productos con una nueva lista de productos
    fun enviarLista(newList: List<Producto>) {
        listaProductos.clear()
        listaProductos.addAll(newList)
        notifyDataSetChanged()
    }

    //Esta función calcula el precio total de la lista de productos
    fun calcularTotalPrecio(): Double {
        totalPrecio = 0.0
        for (product in listaProductos) {
            totalPrecio += product.price
        }
        return totalPrecio
    }

    class CarritoViewHolder(view: View): RecyclerView.ViewHolder(view) {
        private val binding = CarritoItemLayoutBinding.bind(view)
    //Esta función se encarga de tomar un objeto Producto, extraer su nombre, precio y URL de imagen
        fun entregar(producto: Producto) {
            binding.txtProductName.text = producto.name
            binding.txtProductPrice.text = String.format("%.2f", producto.price)
            binding.imgViewCart.load(producto.url)
        }

        //Esta función es responsable de eliminar un producto del carrito de compras
        fun eliminaProducto(carritoAdapter: CarritoAdapter, position: Int) {
            binding.iconButton.setOnClickListener {
                carritoAdapter.eliminarProducto(position)
                carritoAdapter.calcularTotalPrecio()
            }
        }
    }
}