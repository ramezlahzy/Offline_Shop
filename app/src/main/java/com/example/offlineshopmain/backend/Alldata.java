package com.example.offlineshopmain.backend;


import android.content.Context;
import android.content.SharedPreferences;

import com.example.offlineshopmain.backend.UsedClass.Category_For_User;
import com.example.offlineshopmain.backend.UsedClass.Product;
import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class Alldata {
    static private final String All_Product_Key = "All_Product_Key";
//    public static TreeMap<Integer, User> users;
    public static ArrayList<Product> allproduct;
    private static SharedPreferences sharedPreferences;
    private static ArrayList<Product> cartproducts;
    private static Gson gson;
    private static SharedPreferences.Editor editor;
    private static final String db_fake = "fake_data_base";
    private static Type type;
    private static final String TAG = "Alldata";
    private static ArrayList<Category_For_User> allCategories;
    public static void load(Context context) {
        setint(context);
        setusers();
//        if(getAllproduct(context)==null)
        setcateg(context);
        setproducts(context);
    }

    private static void setcateg(Context context) {
//        Bitmap bm1 = BitmapFactory.decodeResource(context.getResources(), R.drawable.me);
//
//        Category_For_User pants = new Category_For_User(new ArrayList<>(), "ProductKind.PANTS", bm1);
//        Category_For_User bodies = new Category_For_User(new ArrayList<>(), "ProductKind.BODIES", bm1);
//        Category_For_User shirts = new Category_For_User(new ArrayList<>(), "ProductKind.SHIRTS", bm1);
//        Category_For_User fashion = new Category_For_User(new ArrayList<>(), "ProductKind.ACCESOIRES", bm1);
//        Category_For_User socks = new Category_For_User(new ArrayList<>(), "ProductKind.SOCKES", bm1);
//        allCategories.add(pants);
//        allCategories.add(bodies);
//        allCategories.add(shirts);
//        allCategories.add(fashion);
//        allCategories.add(socks);
    }

    public static void setint(Context context) {
//        gson = new Gson();
//        users = new TreeMap<>();
//        cartproducts = new ArrayList<>();
//        allproduct = new ArrayList<>();
//        type = (new TypeToken<ArrayList<Product>>() {
//        }).getType();
//        allCategories = new ArrayList<>();
    }

    private static void setusers() {
//        users.put(275817179, new User(275817179, "rameznashaat"));
//        users.put(0, new User(0, ""));
    }

    private static void setproducts(Context context) {
//        sharedPreferences = context.getSharedPreferences(db_fake, Context.MODE_PRIVATE);
//        editor = sharedPreferences.edit();
//        Bitmap bm1 = BitmapFactory.decodeResource(context.getResources(), R.drawable.me);
//        Product p1 = new Product(33,5,222,"T_shirts","this is only one i have now",bm1,
//                new ArrayList<>(),new ArrayList<>(),new ArrayList<>(),null,"T_shirts");
//        p1.toFirestore(context);
//                new Product("https://i.pinimg.com/564x/09/52/d6/0952d6b22484c2e11ac302553106be5d.jpg",
//                49, "this is only one i have now", "Onile T_shirts", allCategories.get(1));
//        Product p2 = new Product("https://i.pinimg.com/236x/ec/6c/99/ec6c9975666a72d1f3827975cf0478c1.jpg",
//                244, "i don't have to say anythings", "bantalons", allCategories.get(1));
//        Product p3 = new Product("https://i.pinimg.com/236x/b1/b6/a9/b1b6a92bd3095591c2c1740196e12b16.jpg",
//                149, "i can kill you", "T_shirts exilusive", allCategories.get(0));
//        Product p4 = new Product("https://i.pinimg.com/236x/e4/d2/50/e4d25053279d97bb4a12517ed1612ba9.jpg",
//                555, "it's the best of the best", "best T_shirts", allCategories.get(1));
//        Product p5 = new Product("https://i.pinimg.com/236x/e1/ba/39/e1ba3944524a5a494e31f57acc5a30d7.jpg",
//                955, "not to say", "T bage", allCategories.get(4));
//        Product p6 = new Product("https://i.pinimg.com/236x/c9/c0/a8/c9c0a8fed1ccbc0862cec33a3b6a4d79.jpg",
//                98, "have relative ", "Half cool", allCategories.get(4));
//        Product p7 = new Product("https://i.pinimg.com/236x/43/b1/7b/43b17b66da3480411086267558c62e9d.jpg",
//                899, "oh shit", "missi", allCategories.get(3));
//        Product p8 = new Product("https://www.realmenrealstyle.com/wp-content/uploads/2021/11/sweatpants-tracksuit-pants-joggers.jpg",
//                444, "best men pants", "inception", allCategories.get(3));
//        Product p9 = new Product("https://www.realmenrealstyle.com/wp-content/uploads/2021/11/cargo-pants-for-men.jpg",
//                9083, "best of the best men pants", "low price", allCategories.get(2));
//        Product p10 = new Product("https://www.realmenrealstyle.com/wp-content/uploads/2021/11/odd-trousers-and-suit-pants.jpg",
//                3, "worst pants", "hight price", allCategories.get(2));
//        Product p11 = new Product("https://lcw.akinoncdn.com/products/2021/10/07/1600683/ab76dc0b-bc9c-4f60-ba83-8ca14350a24b_size561x730.jpg",
//                300, "worst shirt", "logo shirt", allCategories.get(0));
//        Product p12 = new Product("https://lcw.akinoncdn.com/products/2022/05/09/3354938/86d504cb-4b1c-4283-acc4-2fa22612c3fd_quality90.jpg",
//                294, "best shirt", "meky shirts", allCategories.get(0));
//        Review review = new Review(33, "dsfdf", "dfs;df", "ds;fljs");
////     p12.getReviews().add(review);
//        allCategories.get(1).getProducts().add(p1);
//        allCategories.get(1).getProducts().add(p2);
//        allCategories.get(1).getProducts().add(p4);
//        allCategories.get(0).getProducts().add(p11);
//        allCategories.get(0).getProducts().add(p12);
//        allCategories.get(0).getProducts().add(p3);
//        allCategories.get(2).getProducts().add(p9);
//        allCategories.get(2).getProducts().add(p10);
//        allCategories.get(3).getProducts().add(p8);
//        allCategories.get(3).getProducts().add(p7);
//        allCategories.get(4).getProducts().add(p5);
//        allCategories.get(4).getProducts().add(p6);





//        allproduct.add(p1);
//        allproduct.add(p2);
//        allproduct.add(p3);
//        allproduct.add(p4);
//        allproduct.add(p5);
//        allproduct.add(p6);
//        allproduct.add(p7);
//        allproduct.add(p8);
//        allproduct.add(p9);
//        allproduct.add(p10);
//        allproduct.add(p11);
//        allproduct.add(p12);
//        editor.putString(All_Product_Key, gson.toJson(allproduct));
//        editor.commit();
//        editor.apply();

    }


//    public static void addProduct(Product product){
//
//    }
//    public static void addProduct(Product product, Context context) {
////        ArrayList<Product> products = getAllproduct(context);
////        products.add(product);
////        SharedPreferences sharedPreferences = context.getSharedPreferences(db_fake, Context.MODE_PRIVATE);
////        SharedPreferences.Editor editor = sharedPreferences.edit();
////        editor.putString(All_Product_Key, gson.toJson(products));
////        editor.commit();
//        allproduct.add(product);
////        editor.apply();
//    }
//
//    public static ArrayList<Product> getAllproduct(Context context) {
////        SharedPreferences sharedPreferences = context.getSharedPreferences(db_fake, Context.MODE_PRIVATE);
////        ArrayList<Product> allproductfromgasonofficial = gson.fromJson(sharedPreferences.getString(All_Product_Key, null), type);
////        return allproductfromgasonofficial;
//        return allproduct;
//    }
//
//    public static void addPoints(Product item,int points){
//        Log.d(TAG, "addPoints: "+points);
//        item.setPopularityPoint(item.getPopularityPoint()+points);
//    }
//
//    public static ArrayList<Product> getCartproducts() {
//        return cartproducts;
//    }
//
//    public static void setCartproducts(ArrayList<Product> cartproducts) {
//        Alldata.cartproducts = cartproducts;
//    }
//
//    public static ArrayList<Product> getSimilarProduct(String s, Context context) {
//        ArrayList<Product> similar = new ArrayList<>();
//        for (Product product : getAllproduct(context)) {
//            if (similar(product.getProductname(), s)) {
//                similar.add(product);
//            }
//        }
//
//        return similar;
//    }
//
//    private static boolean similar(String productname, String s) {
//        for (int i = 0; i <= productname.length(); i++) {
//            for (int j = i + 1; j < productname.length(); j++) {
//                String temp = productname.substring(i, j);
////                Log.d(TAG, "similar: " + temp);
//                if (temp.equalsIgnoreCase(s))
//                    return true;
//            }
//        }
//        if (productname.equals(s))
//            return true;
//
//
//        return false;
//    }
//
//    public static ArrayList<Category> getAllCategories() {
//        return allCategories;
//    }
//
//    public static void setAllCategories(ArrayList<Category> allCategories) {
//        Alldata.allCategories = allCategories;
//    }
}
