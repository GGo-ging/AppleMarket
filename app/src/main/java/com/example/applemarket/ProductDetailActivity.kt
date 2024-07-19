package com.example.applemarket

import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.example.applemarket.Data.Product
import com.example.applemarket.databinding.ActivityProductDetailBinding

class ProductDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProductDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_product_detail)


        binding = ActivityProductDetailBinding.inflate(layoutInflater)

        val product = intent.getParcelableExtra<Product>("product")

        Log.d("ProductDetailActivity", "Received product: $product")

        product?.let {
            val productNameDetail: TextView = findViewById(R.id.product_name_detail)
            val productPriceDetail: TextView = findViewById(R.id.product_price_detail)
            val productSeller: TextView = findViewById(R.id.product_seller_detail)
            val productAddress: TextView = findViewById(R.id.product_address_detail)
            val productDescription: TextView = findViewById(R.id.product_description_detail)
            val productImageDetail: ImageView = findViewById(R.id.product_image_detail)

            productNameDetail.text = it.name
            productPriceDetail.text = String.format("%,d원", it.price)
            productSeller.text = "판매자: ${it.seller}"
            productAddress.text = "주소: ${it.address}"
            productDescription.text = it.description

            Glide.with(this)
                .load(it.imageUrl)
                .into(productImageDetail)
        }

        val back : ImageButton = findViewById(R.id.back_button)
        back.setOnClickListener {
            finish()
        }
    }
}