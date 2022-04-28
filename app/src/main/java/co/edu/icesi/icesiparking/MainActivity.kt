package co.edu.icesi.icesiparking

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import co.edu.icesi.icesiparking.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding:ActivityMainBinding
    private var users = HashMap<String, User>()

    override fun onCreate(savedInstanceState: Bundle?) {

        val user1 = User("111222333","Rigoberto","Botina","rigo@berto.com","qwerty123")
        val user2 = User("000222333","Facundo","La torre","facum@torre.com","qwerty456")

        users.put("rigo@berto.com",user1)
        users.put("facum@torre.com",user2)

        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.loginBtn.setOnClickListener {
            val i = Intent(this, IntroActivity::class.java)
            val user = binding.userLoginTextField.text.toString()
            val password = binding.pwdLoginTextField.text.toString()
            var currentUser = users.get(user)

            if(currentUser != null){
                if(user.equals(currentUser.email) and password.equals(currentUser.password)){
                    startActivity(i)
                } else{
                    Toast.makeText(this.baseContext,"Hubo un error. Verifica tu correo o contraseña y vuelve intentarlo", Toast.LENGTH_LONG).show()
                }
            } else{
                Toast.makeText(this.baseContext,"Bad credentials", Toast.LENGTH_LONG).show()
            }

        }
    }
}