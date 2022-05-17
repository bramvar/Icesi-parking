package co.edu.icesi.icesiparking

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import co.edu.icesi.icesiparking.databinding.ActivityMainBinding
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.gson.Gson

class MainActivity : AppCompatActivity() {

    private lateinit var binding:ActivityMainBinding
    private var users = HashMap<String, User>()

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.signUpHyperlink.setOnClickListener {
            startActivity(Intent(this, SignupActivity::class.java))
        }

        binding.loginBtn.setOnClickListener {
            val email = binding.userLoginTextField.text.toString()
            val password = binding.pwdLoginTextField.text.toString()

            Firebase.auth.signInWithEmailAndPassword(email,password)
                .addOnSuccessListener {
                    val currentUser = Firebase.auth.currentUser

                    Firebase.firestore.collection("users").document(currentUser!!.uid).get()
                        .addOnSuccessListener {
                            val user = it.toObject(User::class.java)

                            saveUser(user!!)
                            startActivity(Intent(this,IntroActivity::class.java))
                            finish()
                        }.addOnFailureListener{
                            Toast.makeText(this.baseContext,it.message, Toast.LENGTH_LONG).show()
                        }
                }
        }
    }
    private fun saveUser(user: User){
        val sp = getSharedPreferences("icesi-parking", MODE_PRIVATE)
        val json = Gson().toJson(user)
        sp.edit().putString("user",json).apply()

    }
}