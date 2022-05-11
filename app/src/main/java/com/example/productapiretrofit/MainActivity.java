package com.example.productapiretrofit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;

import com.example.productapiretrofit.API.APICall;
import com.example.productapiretrofit.API.ProductModelClass;
import com.example.productapiretrofit.ReclyerView.ProductAdapter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    List<ProductModelClass> productlist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView=findViewById(R.id.RecyclerviewID);
        productlist=new ArrayList<>();

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl("https://makeup-api.herokuapp.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();


        APICall apiCall=retrofit.create(APICall.class);

        Call<List<ProductModelClass>> call=apiCall.getProductData();


        call.enqueue(new Callback<List<ProductModelClass>>() {
            @Override
            public void onResponse(Call<List<ProductModelClass>> call, Response<List<ProductModelClass>> response) {
                if(response.code()!=200)
                {
                    return;
                }

                List<ProductModelClass> ProductDataList=response.body();


                for (ProductModelClass productDataList: ProductDataList){

                    productlist.add(productDataList);

                    recyclerView.setAdapter(new ProductAdapter(getApplicationContext(),productlist));



                }

            }

            @Override
            public void onFailure(Call<List<ProductModelClass>> call, Throwable t) {

            }
        });



    }
}