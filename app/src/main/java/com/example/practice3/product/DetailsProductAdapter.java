package com.example.practice3.product;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.practice3.R;

import java.util.List;

/**
 * Class to draw Products on the secondary activity, which includes the full product details
 */
public class DetailsProductAdapter extends RecyclerView.Adapter<DetailsProductAdapter.DetailsViewHolder> {

    private List<Product> products;

    public DetailsProductAdapter(final List<Product> products) {
        this.products = products;
    }

    @NonNull
    @Override
    public DetailsProductAdapter.DetailsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.details_product_item,
                parent, false);
        return new DetailsProductAdapter.DetailsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DetailsProductAdapter.DetailsViewHolder holder, int index) {
        // Find the product to draw
        final Product product = products.get(index);

        // Draw the product
        holder.idTextView.setText("ID: " + product.getId());
        holder.nameTextView.setText("Model: " + product.getName());
        holder.descriptionTextView.setText("Type: " + product.getDescription());
        holder.sellerTextView.setText("Make: " + product.getSeller());
        holder.priceTextView.setText(String.format("Price: $%.2f", product.getPrice()));
    }

    public void clearProducts() {
        // Removes all products from this recyclerView
        final int oldSize = products.size();
        products.clear();
        notifyItemRangeRemoved(0, oldSize);
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public class DetailsViewHolder extends RecyclerView.ViewHolder {
        public TextView idTextView, nameTextView, descriptionTextView, sellerTextView, priceTextView;

        public DetailsViewHolder(View itemView) {
            super(itemView);
            idTextView = itemView.findViewById(R.id.product_id);
            nameTextView = itemView.findViewById(R.id.product_name);
            descriptionTextView = itemView.findViewById(R.id.product_description);
            sellerTextView = itemView.findViewById(R.id.product_seller);
            priceTextView = itemView.findViewById(R.id.product_price);
        }
    }
}
