package com.example;

public class MyClass {
    private final static int BIAOZHI = 0;
    private final static int MINI_ONE = 1;
    private final static int SMART_TWO = 2;
    private final static int SMART_FOUR = 3;
    private final static int XUETIELONG = 4;


    public static void main(String[] args) {
        int minutes = 30;
        int mills = 10;
        int type = XUETIELONG;
    }

    private static void getPrice(int minutes, int mills, int type) {
        float millPrice = 1.88f;
        float minutePrice = 0.28f;
        switch (type) {
            case BIAOZHI:
                millPrice = 1.88f;
                minutePrice = 0.28f;
                break;
            case MINI_ONE:
                millPrice = 2.80f;
                minutePrice = 0.48f;
                break;
            case SMART_TWO:
                millPrice = 1.88f;
                minutePrice = 0.28f;
                break;
            case SMART_FOUR:
                millPrice = 1.88f;
                minutePrice = 0.28f;
                break;
            case XUETIELONG:
                millPrice = 1.88f;
                minutePrice = 0.28f;
                break;
        }
        float allPrice = 0;
        if (minutes <= 30) {
            allPrice = mills * millPrice + 15;
        } else {
            allPrice = 15 + mills * millPrice + (minutes - 30) * minutePrice;
        }
        System.out.println("allPrice =====>>>>   " + allPrice);
    }

}
