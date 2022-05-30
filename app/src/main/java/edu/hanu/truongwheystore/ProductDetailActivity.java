package edu.hanu.truongwheystore;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import edu.hanu.truongwheystore.Model.Product;

public class ProductDetailActivity extends AppCompatActivity {
    private ImageView productImg;
    private TextView name, desc, price;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);
        initView();

        Intent intent = getIntent();
        Product product =  (Product) intent.getSerializableExtra("productDetail");
        name.setText(product.getName());
        desc.setText(product.getDescription());
        price.setText(FormatHelper.getFormatHelper().format(product.getPrice()));
        Glide.with(this).load(product.getImg_url()).into(productImg);

    }

    private void initView() {
        productImg = findViewById(R.id.pro_act_img);
        name = findViewById(R.id.pro_act_name);
        desc = findViewById(R.id.pro_act_desc);
        price = findViewById(R.id.pro_act_price);
    }

}