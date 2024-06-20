package com.omersungur.home.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.omersungur.home.databinding.RowProductBinding

class ProductAdapter(): RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    class ProductViewHolder(val binding: RowProductBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val binding = RowProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {

    }

    override fun getItemCount(): Int {
        return 0
    }
}