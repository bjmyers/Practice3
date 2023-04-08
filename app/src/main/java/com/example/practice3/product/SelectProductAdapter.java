package com.example.practice3.product;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.practice3.R;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Class to draw Products on the main activity, which includes a checkbox and only the basic
 * information
 */
public class SelectProductAdapter extends RecyclerView.Adapter<SelectProductAdapter.ViewHolder> {

    private List<Product> products;
    private Set<Product> selectedProducts;

    public SelectProductAdapter(final List<Product> products) {
        this.products = products;
        this.selectedProducts = new HashSet<>();
    }

    @NonNull
    @Override
    public SelectProductAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.select_product_item,
                parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SelectProductAdapter.ViewHolder holder, int index) {
        // Find the product to draw
        final Product product = products.get(index);

        // Draw the product, only include the details needed for this view
        holder.nameTextView.setText("Model: " + product.getName());
        holder.sellerTextView.setText("Make: " + product.getSeller());
        holder.priceTextView.setText(String.format("$%.2f", product.getPrice()));
        holder.imageView.setImageResource(product.getImageId());

        // Make a listener for the checkbox, will add or remove the product from the set of
        // selected items
        holder.productSelect.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
                if (checked) {
                    selectedProducts.add(product);
                }
                else {
                    selectedProducts.remove(product);
                }
            }
        });
    }

    public Set<Product> getSelectedProducts() {
        return selectedProducts;
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public CheckBox productSelect;
        public TextView nameTextView, sellerTextView, priceTextView;
        public ImageView imageView;

        public ViewHolder(View itemView) {
            super(itemView);
            productSelect = itemView.findViewById(R.id.product_select);
            nameTextView = itemView.findViewById(R.id.select_product_name);
            sellerTextView = itemView.findViewById(R.id.select_product_seller);
            priceTextView = itemView.findViewById(R.id.select_product_price);
            imageView = itemView.findViewById(R.id.select_product_image);
        }
    }
}
