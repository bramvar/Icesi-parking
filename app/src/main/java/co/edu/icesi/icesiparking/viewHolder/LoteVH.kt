package co.edu.icesi.icesiparking.viewHolder

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import co.edu.icesi.icesiparking.R
import co.edu.icesi.icesiparking.model.Lote
import com.bumptech.glide.Glide
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage

class LoteVH(itemView:View): RecyclerView.ViewHolder(itemView) {

    //STATE
    var lote: Lote? = null

    //UI
    val loteName: TextView = itemView.findViewById(R.id.loteNameTextView)
    val loteShortDescription: TextView = itemView.findViewById(R.id.shortDescriptionTextView)
    val loteState: TextView = itemView.findViewById(R.id.loteStateTextView)
    val loteImage: ImageView = itemView.findViewById(R.id.loteImage)

    fun bindLote(lote:Lote){
        this.lote = lote

        loteName.text = lote.name
        loteShortDescription.text = lote.shortDescription
        if(lote.isAvailable){
            loteState.text = "Disponible"
        }else{
            loteState.text = "Ocupado"
        }

        //Download image
        if(lote.imageID != null){
            Firebase.storage.reference.child("lote-images").child(lote.imageID!!).downloadUrl
                .addOnSuccessListener {
                    Glide.with(loteImage).load(it).into(loteImage)
                }
        }

    }
}