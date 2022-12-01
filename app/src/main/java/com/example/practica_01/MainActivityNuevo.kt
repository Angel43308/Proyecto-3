package com.example.practica_01

import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.practica_01.databinding.ActivityMainBinding
import com.example.practica_01.databinding.ActivityMainNuevoBinding
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main_nuevo.*

class MainActivityNuevo : AppCompatActivity()
{
    private lateinit var binding: ActivityMainNuevoBinding
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        binding= ActivityMainNuevoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val parExtra = intent.extras
        val idAlumno = parExtra?.getInt("idA")
        //abrimos conexion
        val dbconx= DBHelperAlumno(this)

        if(idAlumno!=0){
            val db=dbconx.readableDatabase
            val cursor= db.rawQuery("SELECT*FROM alumnos WHERE id=$idAlumno",null)
            if(cursor.moveToFirst()){
                var itemnom=cursor.getString(1)
                var itemstipo=cursor.getString(2)
                var itempuerto=cursor.getString(3)

                binding.txtNombre?.setText("$itemnom")
                binding.txtTipo?.setText("$itemstipo")
                binding.txtPuerto?.setText("$itempuerto")
            }
            db.close()
            cursor.close()
        }

        val intento1 = Intent(this,MainActivity::class.java)
        binding.btnguardar.setOnClickListener {
            //abrimos la base de datos para escribir
            val db = dbconx.writableDatabase

            val txtNom = binding.txtNombre.text.toString()
            val txtTip = binding.txtTipo.text.toString()
            val txtPue = binding.txtPuerto.text.toString()
            /*
        //alternativa 1
        val sql ="INSERT INTO alumnos  (nombre,cuenta,correo,imagen) VALUES ('$txtNom','$txtCue','$txtCorr','$txtImg')"
        val res= db.execSQL(sql)*/
            //Alternativa 2 de insert
            val newReg = ContentValues()
            newReg.put("nombre", txtNom)
            newReg.put("cuenta", txtTip)
            newReg.put("correo", txtPue)

            if(idAlumno!=0){
                val campollave= "id=?"
                val res= db.update("alumnos",newReg,campollave, arrayOf(idAlumno.toString()))
                //binding.txtDato.text("Hola")
                if (res.toInt() == -1) {
                    Toast.makeText(this, "Error al modificar", Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(this, "Registro modificado con exito", Toast.LENGTH_LONG).show()
                    startActivity(intento1)
                }
            }else {

                val res = db.insert("alumnos", null, newReg)
                if (res.toInt() == -1) {
                    Toast.makeText(this, "Error al insertar", Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(this, "Registro insertado con exito", Toast.LENGTH_LONG).show()
                    startActivity(intento1)
                }
            }
        }
    }
}