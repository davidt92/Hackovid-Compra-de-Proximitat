package org.hackovid.compradeproximitat.product;

import android.widget.ImageView;

public class Product
{
    private ImageView image;
    private String title;
    private String description;

    public Product(ImageView image, String title, String description)
    {
        this.image = image;
        this.title = title;
        this.description = description;
    }

    public ImageView getImage()
    {
        return image;
    }

    public String getTitle()
    {
        return title;
    }

    public String getDescription()
    {
        return description;
    }
}
