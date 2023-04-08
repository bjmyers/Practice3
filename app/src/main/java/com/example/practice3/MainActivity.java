package com.example.practice3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.practice3.db.ProductDatabaseHelper;
import com.example.practice3.product.Product;
import com.example.practice3.product.SelectProductAdapter;
import com.example.practice3.product.ProductSpacingDecorator;

import java.util.List;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    public static final String PRODUCTS_KEY = "products";
    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private Button mMainButton;
    private ProductDatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recycler_view);
        mMainButton = findViewById(R.id.main_button);

        // Initialize DB and load products
        dbHelper = new ProductDatabaseHelper(this);
        if (dbHelper.isDatabaseEmpty()) {
            // Only populate the database if its empty
            dbHelper.populateMoviesDatabase();
        }
        final List<Product> products = dbHelper.getAllProducts();

        // Initialize adapter
        final SelectProductAdapter adapter = new SelectProductAdapter(products);
        // Setup the recycler view
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new ProductSpacingDecorator(0));
        layoutManager = new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        // Add onClick listener to the button
        mMainButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                // Go to the secondary activity, adding the products so they can be displayed there
                final Intent intent = new Intent(MainActivity.this, SecondaryActivity.class);

                // Find the selected products
                final SelectProductAdapter selectProductAdapter = (SelectProductAdapter) recyclerView.getAdapter();
                final Set<Product> selectedProducts = selectProductAdapter.getSelectedProducts();

                // Convert list of products into an array
                Product[] productArray = new Product[selectedProducts.size()];
                productArray = selectedProducts.toArray(productArray);

                // Transition to the secondary activity, passing along the selected products
                intent.putExtra(PRODUCTS_KEY, productArray);
                startActivity(intent);
            }
        });
    }
}