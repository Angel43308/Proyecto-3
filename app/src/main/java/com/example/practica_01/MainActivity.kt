 package com.example.practica_01

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.PopupMenu
import android.widget.Toast
import androidx.core.view.get
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.practica_01.databinding.ActivityMainBinding
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.card_view_design.*

 class MainActivity : AppCompatActivity() {
     private lateinit var binding: ActivityMainBinding
     val data = ArrayList<Alumno>()
     val adapter = AlumnoAdapter(this, data)
     var idAlumno: Int =0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //abrimos conexión
        val dbconx =DBHelperAlumno(this)

        //abrimos la base
        val db= dbconx.readableDatabase

        val cursor = db.rawQuery("SELECT * FROM alumnos", null)

        if(cursor.moveToFirst()){
            do{

            idAlumno = cursor.getInt(cursor.getColumnIndexOrThrow("id"))
                var itemNom=cursor.getString(cursor.getColumnIndexOrThrow("nombre"))
                var itemCue=cursor.getString(cursor.getColumnIndexOrThrow("cuenta"))
                var itemCorr=cursor.getString(cursor.getColumnIndexOrThrow("correo"))
                var itemImg=cursor.getString(cursor.getColumnIndexOrThrow("imagen"))

                data.add(
                    Alumno("$itemNom",
                        "$itemCue",
                        "$itemCorr",
                        "$itemImg"
                    )
                )
            }while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        dbconx.close()
        // getting the recyclerview by its id
        val recyclerview = findViewById<RecyclerView>(R.id.recyclerview)

        // this creates a vertical layout Manager
        recyclerview.layoutManager = LinearLayoutManager(this)

        // Setting the Adapter with the recyclerview
        recyclerview.adapter = adapter

        adapter.setOnItemClickListener(object : AlumnoAdapter.ClickListener{
            override fun onItemClick(view: View, position:Int){
               // Toast.makeText(this@MainActivity,"Click en el Item ${position}",Toast.LENGTH_LONG).show()
                 itemOptionsMenu(position)
            }
        })

        //variable para recibir todos los extras
        val parExtra = intent.extras
        //variables que recibimos todos los extras
        val msje = parExtra?.getString("mensaje")
        val nombre = parExtra?.getString("nombre")
        val cuenta = parExtra?.getString("cuenta")
        val correo = parExtra?.getString("correo")
        val imagen = parExtra?.getString("imagen")
        //Toast.makeText(this,"${nombre}",Toast.LENGTH_LONG).show()


        ///agregar

        if(msje=="nuevo"){
            val insertIndex:Int=data.count()
            data.add(insertIndex,
                Alumno(
                    "${nombre}",
                    "${cuenta}",
                    "${correo}",
                    "${imagen}"
                )
            )
            adapter.notifyItemInserted(insertIndex)
        }

        //Click en agregar
        faButton.setOnClickListener{
            val intento2= Intent(this,MainActivityNuevo::class.java)
            //intento2.putExtra("valor1","Hola mundo")
            startActivity(intento2)
        }
    }

     private fun itemOptionsMenu(position: Int) {
         val popupMenu = PopupMenu(this,binding.recyclerview[position].findViewById(R.id.textViewOptions))
         popupMenu.inflate(R.menu.options_menu)
//Para cambiarnos de activity
         val intento2 = Intent(this,MainActivityNuevo::class.java)
//Implementar el click en el item
         popupMenu.setOnMenuItemClickListener(object : PopupMenu.OnMenuItemClickListener{
             override fun onMenuItemClick(item: MenuItem?): Boolean {
                 when(item?.itemId){
                     R.id.borrar -> {
                         val tmpAlum = data[position]
                         data.remove(tmpAlum)
                         adapter.notifyDataSetChanged()
                         return true
                     }
                     R.id.editar ->{
                         //Tomamos los datos de la cuenta, en la posición de la lista donde hicieron click
                         val cuenta = data[position].cuenta

                         val dbconex = DBHelperAlumno(this@MainActivity)
                         val db = dbconex.readableDatabase

                         val cursor= db.rawQuery("SELECT*FROM alumnos WHERE cuenta='$cuenta'",null)
                         if(cursor.moveToFirst()){
                            idAlumno=cursor.getString(0).toInt()
                         }

                         cursor.close()
                         db.close()
                         dbconex.close()
                         //Pasamos por extras el idAlum para poder saber cual editar de la lista (ArrayList)
                         intento2.putExtra("idA",idAlumno)
                         startActivity(intento2)
                         return true
                     }
                 }
                 return false
             }
         })
         popupMenu.show()
     }
 }


