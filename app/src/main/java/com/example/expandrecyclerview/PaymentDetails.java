package com.example.expandrecyclerview;

public class PaymentDetails {

   String date,price,delivery,purchased;
   private boolean expanded;

    public boolean isExpanded() {
        return expanded;
    }

    public void setExpanded(boolean expanded) {
        this.expanded = expanded;
    }

    public PaymentDetails(String date, String price, String delivery,String purchased) {
        this.date = date;
        this.price = price;
        this.delivery = delivery;
        this.purchased = purchased;
        this.expanded = false;
    }

    public String getPurchased() {
        return purchased;
    }

    public void setPurchased(String purchased) {
        this.purchased = purchased;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDelivery() {
        return delivery;
    }

    public void setDelivery(String delivery) {
        this.delivery = delivery;
    }
}
