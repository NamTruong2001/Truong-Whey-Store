package edu.hanu.truongwheystore.Fragment;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import edu.hanu.truongwheystore.Adapter.ProductAdapter;
import edu.hanu.truongwheystore.Model.Product;
import edu.hanu.truongwheystore.R;


public class SearchFragment extends Fragment {
    private RecyclerView recyclerView;
    private List<Product> productList;
    private ProductAdapter productAdapter;
    private SearchView searchView;

    public SearchFragment() {
        productList = new ArrayList<>();
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_search, container, false);
        recyclerView = v.findViewById(R.id.search_fragment_rcv);
        searchView = v.findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchProduct(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        productAdapter = new ProductAdapter(getContext());
        productAdapter.setData(productList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(productAdapter);
        return v;
    }

    private void searchProduct(String newText) {
        final ProgressDialog pd = new ProgressDialog(getContext());
        pd.setMessage("Please wait");
        pd.show();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("Products").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                pd.dismiss();
                productList.clear();
                for (DocumentSnapshot doc : task.getResult()) {
                    Product product = doc.toObject(Product.class);

                    product.setHomeType(false);
                    if (product.getName().toLowerCase().contains(newText.toLowerCase())) {
                        productList.add(product);
                        Log.d("Products", product.toString());
                    }
                }
                productAdapter.notifyDataSetChanged();
            }
        });
        /*
        db.collection("Products").whereGreaterThanOrEqualTo("name", newText.toLowerCase())
                .whereLessThanOrEqualTo("name", newText.toLowerCase() + "\uf8ff").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        productList.clear();
                        pd.dismiss();
                        for (DocumentSnapshot doc : task.getResult()) {
                            Product product = doc.toObject(Product.class);
                            Log.d("Products", product.toString());
                            product.setHomeType(false);
                            productList.add(product);
                            productAdapter.notifyDataSetChanged();

                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getContext(), "Error!", Toast.LENGTH_SHORT).show();
            }
        });
         */
    }
}