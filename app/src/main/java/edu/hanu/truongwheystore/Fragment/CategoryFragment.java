package edu.hanu.truongwheystore.Fragment;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
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
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import edu.hanu.truongwheystore.Adapter.CategoryAdapter;
import edu.hanu.truongwheystore.Model.Category;
import edu.hanu.truongwheystore.R;

public class CategoryFragment extends Fragment {

    private CategoryAdapter categoryAdapter;
    private List<Category> categories;
    private RecyclerView recyclerView;

    public CategoryFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        categories = new ArrayList<>();

    }

    private void getAllCategories() {
        final ProgressDialog pd = new ProgressDialog(getContext());

        pd.setMessage("Please wait");
        pd.show();
        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseFirestore.collection("Category").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        pd.dismiss();
                        if (task.isSuccessful()) {
                            for (DocumentSnapshot documentSnapshot : task.getResult()) {
                                Category category = documentSnapshot.toObject(Category.class);
                                category.setDocRef(documentSnapshot.getReference().getPath());
                                categories.add(category);

                            }
                            categoryAdapter.notifyDataSetChanged();
                        } else {
                            Toast.makeText(getContext(), "Fail", Toast.LENGTH_SHORT).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        categoryAdapter = new CategoryAdapter(getActivity());
        categoryAdapter.setData(categories);

        View v = inflater.inflate(R.layout.fragment_category, container, false);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView = v.findViewById(R.id.rcv_category);
        recyclerView.setAdapter(categoryAdapter);
        recyclerView.setLayoutManager(linearLayoutManager);

        getAllCategories();
        return v;
    }
}