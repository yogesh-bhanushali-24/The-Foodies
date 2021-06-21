package com.heaven.foodie.model;

public class OrderModel {
    String Address, CustomerEmail, CustomerMobile, CustomerName, GrandTotal, ItemNames, ItemPrice, ItemQuantity, ItemTotal, OrderID, Status;

    OrderModel() {
    }

    public OrderModel(String address, String customerEmail, String customerMobile, String customerName, String grandTotal, String itemNames, String itemPrice, String itemQuantity, String itemTotal, String orderID, String status) {
        Address = address;
        CustomerEmail = customerEmail;
        CustomerMobile = customerMobile;
        CustomerName = customerName;
        GrandTotal = grandTotal;
        ItemNames = itemNames;
        ItemPrice = itemPrice;
        ItemQuantity = itemQuantity;
        ItemTotal = itemTotal;
        OrderID = orderID;
        Status = status;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getCustomerEmail() {
        return CustomerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        CustomerEmail = customerEmail;
    }

    public String getCustomerMobile() {
        return CustomerMobile;
    }

    public void setCustomerMobile(String customerMobile) {
        CustomerMobile = customerMobile;
    }

    public String getCustomerName() {
        return CustomerName;
    }

    public void setCustomerName(String customerName) {
        CustomerName = customerName;
    }

    public String getGrandTotal() {
        return GrandTotal;
    }

    public void setGrandTotal(String grandTotal) {
        GrandTotal = grandTotal;
    }

    public String getItemNames() {
        return ItemNames;
    }

    public void setItemNames(String itemNames) {
        ItemNames = itemNames;
    }

    public String getItemPrice() {
        return ItemPrice;
    }

    public void setItemPrice(String itemPrice) {
        ItemPrice = itemPrice;
    }

    public String getItemQuantity() {
        return ItemQuantity;
    }

    public void setItemQuantity(String itemQuantity) {
        ItemQuantity = itemQuantity;
    }

    public String getItemTotal() {
        return ItemTotal;
    }

    public void setItemTotal(String itemTotal) {
        ItemTotal = itemTotal;
    }

    public String getOrderID() {
        return OrderID;
    }

    public void setOrderID(String orderID) {
        OrderID = orderID;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }
}
