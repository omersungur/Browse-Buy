package com.omersungur.home.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.omersungur.domain.model.product.ProductX
import com.omersungur.home.databinding.RowProductBinding

class ProductAdapter(
    private val products : List<ProductX>
): RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    class ProductViewHolder(val binding: RowProductBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val binding = RowProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = products[position]
        holder.binding.apply {
            tvPrice.text = "$${product.price}"
            tvTitle.text = product.title
            tvStarRating.text = product.rating.toString()
            Glide.with(holder.itemView).load(product.thumbnail).into(ivProduct)
        }
    }

    override fun getItemCount(): Int {
        return products.size
    }
}