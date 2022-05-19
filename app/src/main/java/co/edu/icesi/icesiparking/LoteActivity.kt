package co.edu.icesi.icesiparking

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import co.edu.icesi.icesiparking.databinding.ActivityLoteBinding
import co.edu.icesi.icesiparking.model.Lote
import com.bumptech.glide.Glide
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage

class LoteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoteBinding
    private lateinit var lote: Lote

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        lote = intent.extras?.get("lote") as Lote

        binding.actLoteNameTextView.text = lote.name
        binding.actLoteCompleteDescTextView.text = lote.completeDescription

        Firebase.storage.reference.child("lote-images").child(lote.imageID).downloadUrl
            .addOnSuccessListener {
                Glide.with(binding.actLoteImage).load(it).into(binding.actLoteImage)
            }
    }
}