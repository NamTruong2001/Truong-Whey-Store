package edu.hanu.truongwheystore.Fragment;

import static android.content.ContentValues.TAG;



import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import edu.hanu.truongwheystore.Adapter.ProductAdapter;
import edu.hanu.truongwheystore.HomeActivity;
import edu.hanu.truongwheystore.Model.Product;
import edu.hanu.truongwheystore.R;


public class HomeFragment extends Fragment {
    private ProductAdapter productAdapter;
    private RecyclerView recyclerView;
    private List<Product> products;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        products = new ArrayList<>();

        super.onCreate(savedInstanceState);


    }

    private void getProducts() {
        products.clear();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("Products").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                        Product product = documentSnapshot.toObject(Product.class);
                        Log.d(TAG, documentSnapshot.getId() + " => " + documentSnapshot.getData());
                        product.setHomeType(true);
                        product.setId(documentSnapshot.getId());
                        products.add(product);

                        productAdapter.notifyDataSetChanged();
                    }
                } else {
                    Toast.makeText(getActivity(), "Fail", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //getProducts();
        //Log.d("Products", products.toString());
        View v = inflater.inflate(R.layout.fragment_home, container, false);
        productAdapter = new ProductAdapter(getActivity());
        productAdapter.setData(products);
        recyclerView = v.findViewById(R.id.rcv_popular);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2);
        recyclerView.setAdapter(productAdapter);
        recyclerView.setLayoutManager(gridLayoutManager);
        getProducts();

        return v;
    }
}