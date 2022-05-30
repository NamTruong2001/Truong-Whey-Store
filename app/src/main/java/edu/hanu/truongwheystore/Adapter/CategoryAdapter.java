package edu.hanu.truongwheystore.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import edu.hanu.truongwheystore.CategoryProductActivity;
import edu.hanu.truongwheystore.Model.Category;
import edu.hanu.truongwheystore.ProductDetailActivity;
import edu.hanu.truongwheystore.R;

public class CategoryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<Category> categories;
    private Context context;

    public CategoryAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<Category> categories) {
        this.categories = categories;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category, parent, false);
        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Category category = categories.get(position);
        if (holder instanceof CategoryViewHolder) {
            CategoryViewHolder categoryViewHolder = (CategoryViewHolder) holder;
            categoryViewHolder.name.setText(category.getName());
            Glide.with(context).load(category.getImg_url()).into(categoryViewHolder.thumbnail);
            categoryViewHolder.description.setText(category.getDescription());
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, CategoryProductActivity.class);
                intent.putExtra("category", category);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (categories == null) {
            return 0;
        }

        return categories.size();
    }

    public class CategoryViewHolder extends RecyclerView.ViewHolder {
        private ImageView thumbnail;
        private TextView name;
        private TextView description;

        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            thumbnail = itemView.findViewById(R.id.category_img);
            name = itemView.findViewById(R.id.category_name);
            description = itemView.findViewById(R.id.category_description);

        }
    }
}
