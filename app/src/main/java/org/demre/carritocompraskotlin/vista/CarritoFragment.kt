package org.demre.carritocompraskotlin.vista

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import org.demre.carritocompraskotlin.databinding.FragmentCarritoBinding
import org.demre.carritocompraskotlin.modelos.AdministrarSharedPreferences
import org.demre.carritocompraskotlin.modelos.Producto
import org.demre.carritocompraskotlin.presentadores.CarritoAdapter

class CarritoFragment : Fragment() {

    private lateinit var binding: FragmentCarritoBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: CarritoAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentCarritoBinding.inflate(inflater, container, false)

        recyclerView = binding.recyclerItemCart
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        adapter = CarritoAdapter(requireContext(), obtenerListaCarrito(), ::actualizarTotalPrecio)
        recyclerView.adapter = adapter



        binding.btnVaciar.setOnClickListener {

            if(obtenerListaCarrito().isNotEmpty()){

                MaterialAlertDialogBuilder(requireContext())
                    .setTitle(("Vaciar Carrito"))
                    .setMessage(("¿Esta seguro de vaciar el carrito?"))

                    .setNegativeButton("Cancelar") { _, _ ->

                    }
                    .setPositiveButton("Aceptar") { _, _ ->
                        vaciarCarrito()
                        adapter.enviarLista(obtenerListaCarrito())
                        adapter.notifyDataSetChanged()
                        //adapter.notifyItemChanged()
                        //adapter.notifyItemRangeInserted()
                        actualizarTotalPrecio()
                    }
                    .show()
            }else{
                Snackbar.make(binding.root, "El carrito esta vacío", Snackbar.LENGTH_SHORT)

                    .show()
            }
        }
        actualizarTotalPrecio()

        return binding.root
    }

    //Esta función recupera la lista de productos del carrito de compras del modelo
    private fun obtenerListaCarrito(): MutableList<Producto>{
        val shared = AdministrarSharedPreferences(requireContext())
        val listaCarrito = shared.obtenerListaCarrito()
        return listaCarrito.obtenerListaProducto()
    }

    //Esta función vacía el carrito de compras del modelo
    private fun vaciarCarrito(){
        val shared = AdministrarSharedPreferences(requireContext())
        val listaCarrito = shared.obtenerListaCarrito()
        shared.vaciarCarrito()
        listaCarrito.limpiar()

    }

    //Esta función actualiza el precio total del carrito de compras
    private fun actualizarTotalPrecio() {
        val totalPrecio = adapter.calcularTotalPrecio()
        binding.txtTotal.text = "Total = %.2f".format(totalPrecio)


    }
}