package org.demre.carritocompraskotlin.presentadores

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import coil.load
import org.demre.carritocompraskotlin.R
import org.demre.carritocompraskotlin.databinding.ZapatoItemBinding
import org.demre.carritocompraskotlin.modelos.DataClassZapatos
class ZapatosAdapter(private val listaZapatos: List<DataClassZapatos>): RecyclerView.Adapter<ZapatosAdapter.ZapatosViewHolder>() {

    //Esta función crea un nuevo ZapatosViewHolder para cada elemento en la lista de zapatos
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ZapatosViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ZapatosViewHolder(layoutInflater.inflate(R.layout.zapato_item, parent, false))
    }

    //Esta función devuelve el número de elementos de la lista.
    override fun getItemCount(): Int = listaZapatos.size

    //Esta función actualiza el ZapatosViewHolder con los datos del zapato correspondiente
    override fun onBindViewHolder(holder: ZapatosViewHolder, position: Int) {
        val item = listaZapatos[position]
        holder.mostrarZapato(item)
    }

    class ZapatosViewHolder (private val view: View):RecyclerView.ViewHolder(view){

        private val binding = ZapatoItemBinding.bind(view)

        //Esta función muestra los datos del zapato en la vista
        fun mostrarZapato(zapato: DataClassZapatos) {
            binding.txtNombre.text = zapato.nombre
            binding.txtPrecio.text = zapato.precio.toString()
            binding.imgView.load(zapato.url)
            itemView.setOnClickListener {
                val action = org.demre.carritocompraskotlin.vista.ItemProductoFragmentDirections.actionItemProductsToItemSingleProduct(zapato.nombre, zapato.precio.toFloat(), zapato.url)
                Navigation.findNavController(view).navigate(action)
            }
        }
    }
}