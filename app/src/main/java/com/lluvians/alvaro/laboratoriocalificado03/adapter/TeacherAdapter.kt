package com.lluvians.alvaro.laboratoriocalificado03.adapter

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.lluvians.alvaro.laboratoriocalificado03.databinding.ItemTeacherBinding
import com.lluvians.alvaro.laboratoriocalificado03.model.Teacher

class TeacherAdapter(
    private val context: Context,
    private val teachers: List<Teacher>
) : RecyclerView.Adapter<TeacherAdapter.TeacherViewHolder>() {

    inner class TeacherViewHolder(private val binding: ItemTeacherBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(teacher: Teacher) {
            binding.textName.text = teacher.name
            binding.textLastName.text = teacher.lastName

            Glide.with(context)
                .load(teacher.imageUrl)
                .into(binding.imageTeacher)

            binding.root.setOnClickListener {
                val intent = Intent(Intent.ACTION_DIAL).apply {
                    data = Uri.parse("tel:${teacher.phoneNumber}")
                }
                context.startActivity(intent)
            }

            binding.root.setOnLongClickListener {
                val intent = Intent(Intent.ACTION_SENDTO).apply {
                    data = Uri.parse("mailto:${teacher.email}")
                }
                context.startActivity(intent)
                true
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeacherViewHolder {
        val binding =
            ItemTeacherBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TeacherViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TeacherViewHolder, position: Int) {
        holder.bind(teachers[position])
    }

    override fun getItemCount(): Int = teachers.size
}