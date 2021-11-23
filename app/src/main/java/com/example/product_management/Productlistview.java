package com.example.product_management;

import static java.lang.Math.round;

import java.text.DecimalFormat;

public class Productlistview {

    private String productname;

    public String getProductname() {
        return productname;
    }


    public Productlistview(String productname,String StockOnHand,String StockInTransit,String ReorderQuantity,String ReorderAmount)
    {
       // Float val=price*Integer.parseInt(StockOnHand);
       // Float in_val=price*Integer.parseInt(StockInTransit);
        this.productname=productname+"\n" +"StockOnHand:"+StockOnHand+"\n"+
        "StockInTransit:"+StockInTransit +"\n"+"ReorderQuantity:"+ReorderQuantity+"\n"
        +"ReorderAmount:"+ReorderAmount;
        //+"Valuation:$"+val.toString()+"\n"+"In-Transit Valuation:$"+in_val.toString();

    }

    public Productlistview(String productname,String StockOnHand,String StockInTransit,double price,String ReorderQuantity,String ReorderAmount)
    {
         double val=price*Integer.parseInt(StockOnHand);
         double in_val= price*Integer.parseInt(StockInTransit);
        DecimalFormat df=new DecimalFormat("###.##");
        this.productname=productname+"\n" +"StockOnHand:"+StockOnHand+"\n"+
                "StockInTransit:"+StockInTransit +"\n"+"ReorderQuantity:"+ReorderQuantity+"\n"
                +"ReorderAmount:"+ReorderAmount +"\n"+"Valuation:$"+df.format(val)+"\n"+"In-Transit Valuation:$"+df.format(in_val);


    }


}
