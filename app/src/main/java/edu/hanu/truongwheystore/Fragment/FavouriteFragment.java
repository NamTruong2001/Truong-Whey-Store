package edu.hanu.truongwheystore.Fragment;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldPath;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import edu.hanu.truongwheystore.Adapter.ProductAdapter;
import edu.hanu.truongwheystore.Model.Product;
import edu.hanu.truongwheystore.R;


public class FavouriteFragment extends Fragment {
    public static List<String> fav = new ArrayList<>();
    public static List<Product> favPro = new ArrayList<>();
    private RecyclerView recyclerView;
    public static ProductAdapter productAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_favourite, container, false);
        ;
        recyclerView = v.findViewById(R.id.fav_product);
        productAdapter = new ProductAdapter(getContext());
        productAdapter.setData(favPro);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(productAdapter);

        final ProgressDialog pd = new ProgressDialog(getContext());
        pd.setMessage("Please wait");
        pd.show();
        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseFirestore.collection("Favourite").document(FirebaseAuth.getInstance().getUid()).get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            fav = (List<String>) task.getResult().get("my_fav");
                            firebaseFirestore.collection("Products").whereIn(FieldPath.documentId(), fav).get()
                                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                            pd.dismiss();
                                            if (task.isSuccessful()) {
                                                for (QueryDocumentSnapshot doc : task.getResult()) {
                                                    Product product = doc.toObject(Product.class);
                                                    product.setId(doc.getId());
                                                    product.setHomeType(true);
                                                    favPro.add(product);
                                                }
                                                productAdapter.notifyDataSetChanged();
                                            }
                                        }
                                    });
                        }
                    }
                });




        return v;
    }
}