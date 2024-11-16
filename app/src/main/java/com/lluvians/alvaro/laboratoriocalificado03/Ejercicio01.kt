package com.lluvians.alvaro.laboratoriocalificado03

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.lluvians.alvaro.laboratoriocalificado03.adapter.TeacherAdapter
import com.lluvians.alvaro.laboratoriocalificado03.databinding.ActivityEjercicio01Binding
import com.lluvians.alvaro.laboratoriocalificado03.databinding.ActivityMainBinding
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
        }, {
            it.printStackTrace()
        })

        requestQueue.add(request)
    }
}