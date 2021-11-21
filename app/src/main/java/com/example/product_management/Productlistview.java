package com.example.product_management;

public class Productlistview {

    private String productname;

    public String getProductname() {
        return productname;
    }


    public Productlistview(String productname,String StockOnHand,String StockInTransit,String ReorderQuantity,String ReorderAmount)
    {
        this.productname=productname+"\n"
        +"StockOnHand:"+StockOnHand+"\n"+"StockInTransit:"+StockInTransit
                +"\n"+"ReorderQuantity:"+ReorderQuantity+"\n"+"ReorderAmount:"+ReorderAmount;

    }




}
