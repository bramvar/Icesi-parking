package co.edu.icesi.icesiparking

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import co.edu.icesi.icesiparking.adapter.LoteAdapter
import co.edu.icesi.icesiparking.databinding.ActivityUserHomeBinding
import co.edu.icesi.icesiparking.model.Lote
import co.edu.icesi.icesiparking.model.User
import com.bumptech.glide.Glide
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.google.gson.Gson

class UserHomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUserHomeBinding
    private lateinit var user: User
    private lateinit var adapter: LoteAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        user = loadUser()!!
        binding.userNameTextView.text = user.name+" "+user.surname
        getUpdatedUser()

        adapter = LoteAdapter()
        binding.lotesRecycler.adapter = adapter
        binding.lotesRecycler.layoutManager = LinearLayoutManager(this)
        binding.lotesRecycler.setHasFixedSize(true)

        Firebase.firestore.collection("lotes").get()
            .addOnCompleteListener { lote ->
                for(doc in lote.result!!){
                    val lote = doc.toObject(Lote::class.java)
                    adapter.addLote(lote)
                }
            }

        binding.userSettingsButton.setOnClickListener {
            startActivity(Intent(this,SettingsActivity::class.java))
        }
    }

    fun getUpdatedUser(){
        Firebase.firestore.collection("users").document(user.id).get()
            .addOnSuccessListener {
                val updatedUser = it.toObject(User::class.java)
                val imageID = updatedUser?.imageID
                downloadProfileImage(imageID)

            }
    }

    fun downloadProfileImage(imageID: String?){

        if(imageID == null) return

        Firebase.storage.getReference().child("user-image-profile").child(imageID).downloadUrl
            .addOnSuccessListener {
                Glide.with(binding.userImage).load(it).into(binding.userImage)
            }
    }


    fun loadUser(): User?{
        val sp = getSharedPreferences("icesi-parking", MODE_PRIVATE)
        val json = sp.getString("user","NO_USER")

        if(json == "NO_USER"){
            return null
        } else{
            return Gson().fromJson(json, User::class.java)
        }
    }
}