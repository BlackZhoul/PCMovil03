package com.lluvians.alvaro.laboratoriocalificado03

import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.lluvians.alvaro.laboratoriocalificado03.adapter.TeacherAdapter
import com.lluvians.alvaro.laboratoriocalificado03.databinding.ActivityEjercicio01Binding
import com.lluvians.alvaro.laboratoriocalificado03.model.Teacher

class Ejercicio01 : AppCompatActivity() {
    private lateinit var binding: ActivityEjercicio01Binding
    private val teacherList = mutableListOf<Teacher>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEjercicio01Binding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.recyclerView.layoutManager = LinearLayoutManager(this)

        fetchTeachers()
    }

    private fun fetchTeachers() {
        val url = "https://private-effe28-tecsup1.apiary-mock.com/list/teacher"
        val requestQueue = Volley.newRequestQueue(this)

        val request = JsonObjectRequest(Request.Method.GET, url, null, { response ->
            val teachersArray = response.getJSONArray("teachers")
            for (i in 0 until teachersArray.length()) {
                val teacherJson = teachersArray.getJSONObject(i)
                teacherList.add(
                    Teacher(
                        name = teacherJson.getString("name"),
                        lastName = teacherJson.getString("last_name"),
                        phoneNumber = teacherJson.getString("phone"),
                        email = teacherJson.getString("email"),
                        imageUrl = teacherJson.getString("imageUrl")
                    )
                )
            }
            binding.recyclerView.adapter = TeacherAdapter(this, teacherList)
        }, { error ->
            // En caso de error, mostrar un Toast y un mensaje en el TextView
            Toast.makeText(this, getString(R.string.error_fetching_data), Toast.LENGTH_SHORT).show()
            binding.errorTextView.text = getString(R.string.error_fetching_data) // Mostrar en TextView
            binding.errorTextView.visibility = android.view.View.VISIBLE // Hacer visible el TextView de error
        })

        requestQueue.add(request)

        // Mostrar el mensaje de bienvenida y ocultarlo después de 3 segundos
        binding.welcomeTextView.visibility = android.view.View.VISIBLE
        // Usamos Handler para ejecutar el código después de 3 segundos
        Handler().postDelayed({
            binding.welcomeTextView.visibility = android.view.View.GONE
        }, 3000) // 3000 milisegundos = 3 segundos
    }
}