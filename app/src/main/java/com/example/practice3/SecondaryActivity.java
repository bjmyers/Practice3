package com.example.practice3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.practice3.product.DetailsProductAdapter;
import com.example.practice3.product.Product;
import com.example.practice3.product.SelectProductAdapter;
import com.example.practice3.product.ProductSpacingDecorator;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class SecondaryActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private Button mMailButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_secondary);

        recyclerView = findViewById(R.id.secondary_recycler_view);
        mMailButton = findViewById(R.id.email_button);

        // Get the products from the intent
        final Intent intent = getIntent();
        final Parcelable[] parcelableArray = intent.getParcelableArrayExtra(MainActivity.PRODUCTS_KEY);
        // Convert the parcelables into a list of Products
        final List<Product> products = Arrays.stream(parcelableArray).map(p -> (Product) p)
                .collect(Collectors.toList());

        // Initialize adapter
        final DetailsProductAdapter adapter = new DetailsProductAdapter(products);
        // Setup the recycler view
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new ProductSpacingDecorator(0));
        layoutManager = new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        // Setup listener to mail product info
        mMailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String subject = "Product Information";
                //final String email = "sweng888mobileapps@gmail.com";
                final String email = "bmyers0217@gmail.com";

                // Create the email body with the product information for each product
                final StringBuilder messageBuilder = new StringBuilder()
                        .append("Here is the product information you requested:\n");
                products.forEach(p -> messageBuilder.append(p.toString()));
                final String message = messageBuilder.toString();

                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("*/*");
                intent.putExtra(Intent.EXTRA_EMAIL, email);
                intent.putExtra(Intent.EXTRA_SUBJECT, subject);
                intent.putExtra(Intent.EXTRA_TEXT, message);

                // Start the implicit intent to send the email
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(intent);
                }

                // Clear out the products from this activity
                ((DetailsProductAdapter) recyclerView.getAdapter()).clearProducts();

                // Show a toast to the user that the action was successful
                Toast.makeText(SecondaryActivity.this, "Sending Details via Email",
                        Toast.LENGTH_LONG).show();
            }
        });
    }
}