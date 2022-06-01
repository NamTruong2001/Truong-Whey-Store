package edu.hanu.truongwheystore.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.hanu.truongwheystore.FormatHelper;
import edu.hanu.truongwheystore.Fragment.FavouriteFragment;
import edu.hanu.truongwheystore.Model.Product;
import edu.hanu.truongwheystore.ProductDetailActivity;
import edu.hanu.truongwheystore.R;

public class ProductAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Product> products;
    private Context context;
    private final static int PRODUCT_HOME = 1;
    private final static int PRODUCT_SEARCH = 2;

    public ProductAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<Product> products) {
        this.products = products;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == PRODUCT_HOME) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product_cat, parent, false);
            return new ProductHomeViewHolder(view);
        }
        if (viewType == PRODUCT_SEARCH) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product_list, parent, false);
            return new ProductSearchViewHolder(view);
        }

        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Product product = products.get(position);
        if (holder instanceof ProductHomeViewHolder) {
            ProductHomeViewHolder productHomeViewHolder = (ProductHomeViewHolder) holder;
            productHomeViewHolder.name.setText(product.getName());
            Glide.with(context).load(product.getImg_url()).into(productHomeViewHolder.thumbnail);
            productHomeViewHolder.price.setText(FormatHelper.getFormatHelper().format(product.getPrice()));
            productHomeViewHolder.fav.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
                    FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
                    DocumentReference documentReference = firebaseFirestore.collection("Favourite").document(firebaseAuth.getUid());
                    documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if (task.isSuccessful()) {
                                DocumentSnapshot documentSnapshot = task.getResult();

                                List<String> favProduct = (List<String>) documentSnapshot.get("my_fav");
                                if (favProduct != null) {
                                    if (!favProduct.contains(product.getId())) {
                                        favProduct.add(product.getId());
                                        documentReference.update("my_fav", favProduct).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    Toast.makeText(context, "Add new success", Toast.LENGTH_SHORT).show();
                                                    FavouriteFragment.favPro.add(product);
                                                    FavouriteFragment.productAdapter.notifyDataSetChanged();
                                                } else {
                                                    Toast.makeText(context, "Add new fail", Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });
                                    } else {
                                        favProduct.remove(product.getId());
                                        documentReference.update("my_fav", favProduct).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    Toast.makeText(context, "Delete from fav", Toast.LENGTH_SHORT).show();
                                                    FavouriteFragment.favPro.remove(product);
                                                    FavouriteFragment.productAdapter.notifyDataSetChanged();
                                                } else {
                                                    Toast.makeText(context, "Delete fail", Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });
                                    }
                                } else {
                                    List<String> newFav = new ArrayList<>();
                                    newFav.add(product.getId());
                                    Map<String, List<String>> myFav = new HashMap<>();
                                    myFav.put("my_fav", newFav);
                                    documentReference.set(myFav).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                Toast.makeText(context, "Create new success", Toast.LENGTH_SHORT).show();
                                                FavouriteFragment.favPro.add(product);
                                                FavouriteFragment.productAdapter.notifyDataSetChanged();
                                            } else {
                                                Toast.makeText(context, "Create new fail", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                                }

                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            e.printStackTrace();
                        }
                    });
                }
            });

        }
        if (holder instanceof ProductSearchViewHolder) {
            ProductSearchViewHolder productSearchViewHolder = (ProductSearchViewHolder) holder;
            productSearchViewHolder.name.setText(product.getName());
            Glide.with(context).load(product.getImg_url()).into(productSearchViewHolder.thumbnail);
            productSearchViewHolder.price.setText(FormatHelper.getFormatHelper().format(product.getPrice()));
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ProductDetailActivity.class);
                intent.putExtra("productDetail", product);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemViewType(int position) {
        Product product = products.get(position);
        if (product.isHomeType()) {
            return PRODUCT_HOME;
        } else {
            return PRODUCT_SEARCH;
        }
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public class ProductHomeViewHolder extends RecyclerView.ViewHolder {
        private ImageView thumbnail;
        private TextView name;
        private TextView price;
        private ImageView fav;

        public ProductHomeViewHolder(@NonNull View itemView) {
            super(itemView);
            thumbnail = itemView.findViewById(R.id.product_img);
            name = itemView.findViewById(R.id.product_title);
            price = itemView.findViewById(R.id.product_price);
            fav = itemView.findViewById(R.id.product_favourite);
        }
    }

    public class ProductSearchViewHolder extends RecyclerView.ViewHolder {
        private ImageView thumbnail;
        private TextView name;
        private TextView price;


        public ProductSearchViewHolder(@NonNull View itemView) {
            super(itemView);
            thumbnail = itemView.findViewById(R.id.prolist_img);
            name = itemView.findViewById(R.id.prolist_title);
            price = itemView.findViewById(R.id.prolist_price);

        }
    }
}