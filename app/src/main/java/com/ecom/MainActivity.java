package com.ecom;

import android.app.ProgressDialog;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import com.ecom.db.ECommerceDB;
import com.ecom.event.OnFragmentInteractionListener;
import com.ecom.event.OnTaskCompleted;
import com.ecom.tasks.BuildDbAsync;
import com.ecom.ui.Home;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements OnTaskCompleted,OnFragmentInteractionListener {


    private android.support.v4.app.FragmentManager fragmentManager;
    private android.support.v4.app.FragmentTransaction fragmentTransaction;
    private Fragment currentFragment;
    private Toolbar toolbar;
    public ProgressDialog mProgressDialog;
    private ECommerceDB eCommerceDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        showProgressDialog();

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        setToolbar(false);



        eCommerceDB = (ECommerceDB) ECommerceDB.getDatabase(MainActivity.this);
        EcommApp.get().getProductJson().enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                parseResponse(response.body().getAsJsonObject().get("categories").getAsJsonArray());
            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {
                hideProgressDialog();
            }
        });

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("VIEW","DEFAULFT");
        jsonObject.addProperty("SUBCATEGORY",0);
        jsonObject.addProperty("ISPRODUCTS",false);

        updateView(jsonObject);

    }

    private void parseResponse(JsonArray asJsonObject) {

        BuildDbAsync buildDbAsync = new BuildDbAsync(eCommerceDB, this);
        buildDbAsync.execute(asJsonObject);
    }


    public void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);

            mProgressDialog.setIndeterminate(true);
        }

        mProgressDialog.show();
    }

    public void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

    @Override
    public void onTaskCompleted(String result) {
        hideProgressDialog();
    }


    private void updateView(JsonObject jsonObject) {

        String currentView = null;
        currentView = jsonObject.get("VIEW").getAsString();

        boolean productsAvilable = jsonObject.get("ISPRODUCTS").getAsBoolean();


        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();

        /**
         * Load any fragment using switch chase
         */
        switch (currentView) {
            case "PRODUCT_SUBCATEGORY":
                currentFragment = Home.newInstance(jsonObject.get("SUBCATEGORY").getAsInt(),productsAvilable);
                fragmentTransaction.replace(R.id.fragment_container, currentFragment);
                break;
            default:
                currentFragment = Home.newInstance(jsonObject.get("SUBCATEGORY").getAsInt(),productsAvilable);
                fragmentTransaction.replace(R.id.fragment_container, currentFragment);

        }
        fragmentTransaction.commit();
    }



    public void setToolbar(boolean enabled) {
        if (enabled) {
            toolbar.setVisibility(View.VISIBLE);
        } else {
            toolbar.setVisibility(View.INVISIBLE);
        }

    }


    public  Toolbar getToolbar(){
        return toolbar;
    }

    @Override
    public void onFragmentInteraction(JsonObject jsonObject) {
        updateView(jsonObject);
    }
}
