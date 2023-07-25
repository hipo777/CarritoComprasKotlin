package org.demre.carritocompraskotlin.vista

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import org.demre.carritocompraskotlin.databinding.FragmentItemProductoBinding
import org.demre.carritocompraskotlin.modelos.ProveedorZapatos
import org.demre.carritocompraskotlin.presentadores.ZapatosAdapter

class ItemProductoFragment : Fragment() {

    private lateinit var binding: FragmentItemProductoBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentItemProductoBinding.inflate(inflater, container, false)

        binding.recyclerItem.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.recyclerItem.adapter = ZapatosAdapter(ProveedorZapatos.shoeList)


        return binding.root
    }

}