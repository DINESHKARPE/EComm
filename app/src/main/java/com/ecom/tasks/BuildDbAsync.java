package com.ecom.tasks;

import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import com.ecom.dao.CategoriesDAO;
import com.ecom.dao.CategoriesRelationDAO;
import com.ecom.dao.ProductDAO;
import com.ecom.dao.VariantsDAO;
import com.ecom.db.ECommerceDB;
import com.ecom.entity.Categories;
import com.ecom.entity.CategoriesRelation;
import com.ecom.entity.Product;
import com.ecom.entity.Variants;
import com.ecom.event.OnTaskCompleted;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

public class BuildDbAsync extends AsyncTask<JsonArray, Void, Void> {

    private String TAG = BuildDbAsync.class.getCanonicalName();

    private final CategoriesRelationDAO categoriesRelationDAO;
    private final CategoriesDAO categoriesDAO;
    private final ProductDAO productDAO;
    private final VariantsDAO variantsDAO;
    private OnTaskCompleted onTaskCompleted;

    public BuildDbAsync(ECommerceDB eCommerceDB, OnTaskCompleted onTaskCompleted) {

        categoriesRelationDAO = eCommerceDB.categoriesRelationDAO();
        categoriesDAO = eCommerceDB.categoriesDAO();
        productDAO = eCommerceDB.productDAO();
        variantsDAO = eCommerceDB.variantsDAO();

        this.onTaskCompleted = onTaskCompleted;

    }


    @Override
    protected Void doInBackground(JsonArray... jsonArrays) {

        categoriesRelationDAO.deleteAll();
        categoriesDAO.deleteAll();
        productDAO.deleteAll();
        variantsDAO.deleteAll();

        JsonArray asJsonObject = jsonArrays[0];

        List<Categories> categoriesList = new ArrayList<>();

        for (int i = 0; i < asJsonObject.size(); i++) {

            JsonArray subCategory = asJsonObject.get(i).getAsJsonObject().get("child_categories").getAsJsonArray();

            if (subCategory.size() != 0) {

                categoriesList.add(new Categories(asJsonObject.get(i).getAsJsonObject().get("id").getAsInt(), asJsonObject.get(i).getAsJsonObject().get("name").getAsString(),false));

                int mastCateID = asJsonObject.get(i).getAsJsonObject().get("id").getAsInt();

                List<CategoriesRelation> categoriesRelationList = new ArrayList<>();

                for (int j = 0; j < subCategory.size(); j++) {
                    categoriesRelationList.add(
                            new CategoriesRelation(mastCateID, subCategory.get(j).getAsInt()));
                }

                categoriesRelationDAO.insertCategoriesRelation(categoriesRelationList);
            }else {

                categoriesList.add(new Categories(asJsonObject.get(i).getAsJsonObject().get("id").getAsInt(), asJsonObject.get(i).getAsJsonObject().get("name").getAsString(),true));

            }


            JsonObject productDetails = asJsonObject.get(i).getAsJsonObject();
            JsonArray products = productDetails.get("products").getAsJsonArray();

            if (products.size() != 0) {
                List<Product> productList = new ArrayList<>();

                for (int k = 0; k < products.size(); k++) {
                    JsonObject projectObject = products.get(k).getAsJsonObject();

                    int productId = projectObject.get("id").getAsInt();
                    String producName = projectObject.get("name").getAsString();

                    productList.add(new Product(productId, producName, productDetails.get("id").getAsInt()));

                    JsonArray productVariants = projectObject.get("variants").getAsJsonArray();

                    List<Variants> variantsList = new ArrayList<>();

                    for (int v = 0; v < productVariants.size(); v++) {

                        JsonObject variantObject = productVariants.get(v).getAsJsonObject();
                        int variantId = variantObject.get("id").getAsInt();
                        String color = variantObject.get("color").getAsString();
                        String size = variantObject.get("size").isJsonNull() ? "" : variantObject.get("size").getAsString();
                        int price = variantObject.get("price").getAsInt();
                        variantsList.add(new Variants(variantId,color,size,price,productId));
                    }
                    variantsDAO.insert(variantsList);
                }
                productDAO.insert(productList);

                int mastCateID = asJsonObject.get(i).getAsJsonObject().get("id").getAsInt();
                String name = asJsonObject.get(i).getAsJsonObject().get("name").getAsString();

                List<CategoriesRelation> categoriesRelationList = new ArrayList<>();

                for (int j = 0; j < subCategory.size(); j++) {
                    categoriesRelationList.add(
                            new CategoriesRelation(mastCateID, subCategory.get(j).getAsInt()));
                }

                categoriesRelationDAO.insertCategoriesRelation(categoriesRelationList);
            }

        }
        categoriesDAO.insert(categoriesList);
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);

        LiveData<List<Categories>> data = categoriesDAO.fetchMasterCategories();
        onTaskCompleted.onTaskCompleted(""+data.getValue());
    }
}
