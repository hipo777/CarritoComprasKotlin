package org.demre.carritocompraskotlin.vista

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import org.demre.carritocompraskotlin.databinding.FragmentProductoBinding
import org.demre.carritocompraskotlin.modelos.AdministrarSharedPreferences

class ProductoFragment : Fragment() {

    private lateinit var binding: FragmentProductoBinding
    private val argumento: ProductoFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentProductoBinding.inflate(inflater, container, false)
        val nombreZapato = argumento.Name
        val urlZapato = argumento.url
        val precioZapato = argumento.Price
        binding.txtItemNombre.text = nombreZapato
        binding.txtPrecio.text = precioZapato.toString()
        
        Glide.with(binding.imgViewProduct.context)
            .load(urlZapato)
            .into(binding.imgViewProduct)


        binding.btnAgregar.setOnClickListener {
            guardarDatos()
        }


        return binding.root
    }

    //Esta función es responsable de guardar los datos del carrito de compras en las preferencias compartidas
    private fun guardarDatos() {
        val shared = AdministrarSharedPreferences(requireContext())
        val listaCarrito = shared.obtenerListaCarrito()
        val nombreZapato = argumento.Name
        val urlZapato = argumento.url
        val precioZapato = argumento.Price

        listaCarrito.agregarProducto(nombreZapato, urlZapato, precioZapato.toDouble())

        shared.guardarListaCarrito(listaCarrito)
    }

}