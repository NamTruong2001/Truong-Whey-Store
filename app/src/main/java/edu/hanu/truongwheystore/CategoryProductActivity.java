package edu.hanu.truongwheystore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import edu.hanu.truongwheystore.Adapter.ProductAdapter;
import edu.hanu.truongwheystore.Model.Category;
import edu.hanu.truongwheystore.Model.Product;

public class CategoryProductActivity extends AppCompatActivity {
    private List<Product> products;
    private ProductAdapter productAdapter;
    private RecyclerView recyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_product);
        products = new ArrayList<>();
        getAllProducts();
        productAdapter = new ProductAdapter(getApplicationContext());
        productAdapter.setData(products);

        recyclerView = findViewById(R.id.rcv_category_product);
        //LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), 2);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(productAdapter);
    }

    private void getAllProducts() {
        Intent intent = getIntent();
        Category category = (Category) intent.getSerializableExtra("category");
        final ProgressDialog pd = new ProgressDialog(this);
        pd.setMessage("Please wait");
        pd.show();
        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseFirestore.collection("Products").whereEqualTo("category", category.getName()).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        pd.dismiss();
                        if (task.isSuccessful()) {
                            for (DocumentSnapshot documentSnapshot : task.getResult()) {
                                Product product = documentSnapshot.toObject(Product.class);
                                product.setHomeType(true);
                                product.setId(documentSnapshot.getId());
                                products.add(product);
                            }
                        }
                        productAdapter.notifyDataSetChanged();

                    }
                });
    }
}