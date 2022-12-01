package com.example.practica_01

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.practica_01.databinding.ActivityLoginBinding
import com.example.practica_01.databinding.ActivityMainBinding

class Login : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnSesion.setOnClickListener {
            if (binding.edtUsuario.length() == 0 || binding.edtPass.length() == 0) {
                Toast.makeText(this@Login, "Falta llenar usuario o contraseña", Toast.LENGTH_LONG)
                    .show()
            } else {
                if ((binding.edtUsuario.text.toString() == "Angel" && binding.edtPass.text.toString() == "AngelArmando")||(binding.edtUsuario.text.toString() == "Admin" && binding.edtPass.text.toString() == "123456")) {
                    val intento2 = Intent(this, MainActivity::class.java)
                    startActivity(intento2)
                } else {
                    Toast.makeText(
                        this@Login,
                        "Usuario o contraseña incorrecto",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }

    }
}
