package com.example.practice3.product;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.io.Serializable;
import java.util.Objects;

public class Product implements Parcelable {

    private int id;
    private String name;
    private String description;
    private String seller;
    private double price;
    private int imageId;

    public Product(int id, String name, String description, String seller, double price, int imageId) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.seller = seller;
        this.price = price;
        this.imageId = imageId;
    }

    protected Product(Parcel in) {
        id = in.readInt();
        name = in.readString();
        description = in.readString();
        seller = in.readString();
        price = in.readDouble();
        imageId = in.readInt();
    }

    public static final Creator<Product> CREATOR = new Creator<Product>() {
        @Override
        public Product createFromParcel(Parcel in) {
            return new Product(in);
        }

        @Override
        public Product[] newArray(int size) {
            return new Product[size];
        }
    };

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getSeller() {
        return seller;
    }

    public double getPrice() {
        return price;
    }

    public int getImageId() {
        return imageId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(name);
        parcel.writeString(description);
        parcel.writeString(seller);
        parcel.writeDouble(price);
        parcel.writeInt(imageId);
    }

    // IDs are guaranteed to be unique, thus we only need to check IDs in the equals and hashCode
    // methods
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Product)) return false;
        Product product = (Product) o;
        return id == product.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "\nProduct{" +
                "\nid=" + id +
                "\nname='" + name + '\'' +
                "\ndescription='" + description + '\'' +
                "\nseller='" + seller + '\'' +
                "\nprice=" + String.format("$%.2f", price) +
                "\n}";
    }
}
