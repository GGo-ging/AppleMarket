package com.example.applemarket.adapter

import android.annotation.SuppressLint
//import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.applemarket.Data.Product
import com.example.applemarket.R


class ProductAdapter (private val products : List<Product>, private val onItemClick : (Product) -> Unit) :

    RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {




    override fun onCreateViewHolder(postion: ViewGroup, viewType: Int): ProductViewHolder {
        val view = LayoutInflater.from(postion.context).inflate(R.layout.item_product, postion, false)
        return ProductViewHolder(view)
    }

    override fun getItemCount(): Int {
        return products.size
    }

    override fun onBindViewHolder(holder: ProductViewHolder, postion: Int) {
        holder.bind(products[postion])
    }


    inner class ProductViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        private val productImage : ImageView = itemView.findViewById(R.id.product_image)
        private val productName : TextView = itemView.findViewById(R.id.product_name)
        private val productPrice : TextView = itemView.findViewById(R.id.product_prcie)
//        private val productSeller : TextView = itemView.findViewById(R.id.product_seller_detail)
//        private val productAddress : TextView = itemView.findViewById(R.id.product_address_detail)
//        private val productDescription : TextView = itemView.findViewById(R.id.product_description_detail)

        @SuppressLint("DefaultLocale")
        fun bind(product: Product) {
            productName.text = product.name
            productPrice.text = String.format("%,d", product.price)

            Glide.with(itemView.context)
                .load(product.imageUrl)
                .into(productImage)

            itemView.setOnClickListener {
                onItemClick(product)
            }
        }
    }

}

