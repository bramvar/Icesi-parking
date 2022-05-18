package co.edu.icesi.icesiparking

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import co.edu.icesi.icesiparking.databinding.ActivityLoteBinding

class LoteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoteBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoteBinding.inflate(layoutInflater)
        setContentView(binding.root)


    }
}