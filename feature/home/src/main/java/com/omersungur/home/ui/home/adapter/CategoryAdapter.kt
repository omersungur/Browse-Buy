package com.omersungur.home.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.omersungur.domain.model.category.Category
import com.omersungur.home.databinding.RowCategoryBinding

class CategoryAdapter(
    private val categories: List<Category>,
): RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {

    class CategoryViewHolder(val binding: RowCategoryBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val binding = RowCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CategoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val category = categories[position]

        holder.binding.btnCategory.text = category.name
    }

    override fun getItemCount(): Int {
        return categories.size
    }
}