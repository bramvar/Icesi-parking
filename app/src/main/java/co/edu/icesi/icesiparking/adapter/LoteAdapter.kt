package co.edu.icesi.icesiparking.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import co.edu.icesi.icesiparking.R
import co.edu.icesi.icesiparking.model.Lote
import co.edu.icesi.icesiparking.viewHolder.LoteVH

class LoteAdapter: RecyclerView.Adapter<LoteVH>() {

    private val lotes = ArrayList<Lote>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LoteVH {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.lote_row,parent,false)
        val loteVH = LoteVH(view)

        return loteVH
    }

    override fun onBindViewHolder(holder: LoteVH, position: Int) {
        val loten = lotes[position]

        holder.bindLote(loten)
    }

    override fun getItemCount(): Int {
        return lotes.size
    }

    fun addLote(lote: Lote) {
        lotes.add(lote)
        notifyItemInserted(lotes.size-1)
    }

    fun clear() {
        val size = lotes.size
        lotes.clear()
        notifyItemRangeRemoved(0,size)
    }
}