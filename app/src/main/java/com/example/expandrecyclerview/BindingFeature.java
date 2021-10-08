package com.example.expandrecyclerview;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;

import com.example.expandrecyclerview.databinding.ActivityBindingFeatureBinding;

import java.util.ArrayList;

public class BindingFeature extends AppCompatActivity{

    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;
    PaymentDetailsAdapter paymentDetailsAdapter;
    ArrayList<PaymentDetails> payment = new ArrayList<>();
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_binding_feature);

        toolbar = findViewById(R.id.toolbar);

        this.setSupportActionBar(toolbar);

        recyclerView = findViewById(R.id.recycler);
        linearLayoutManager = new LinearLayoutManager(BindingFeature.this,LinearLayoutManager.VERTICAL,false);

        payment.add(new PaymentDetails("12-05-2021","299","deliveryed","Purchased products One"));
        payment.add(new PaymentDetails("11-06-2021","399","deliveryed","Purchased products Two"));
        payment.add(new PaymentDetails("10-07-2021","499","deliveryed","Purchased products Three"));
        payment.add(new PaymentDetails("9-08-2021","599","deliveryed","Purchased products Four"));

        paymentDetailsAdapter = new PaymentDetailsAdapter(BindingFeature.this,payment);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(paymentDetailsAdapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.serachedit,menu);
        MenuItem item = menu.findItem(R.id.serach);
        SearchView searchView = (SearchView) item.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                paymentDetailsAdapter.getFilter().filter(newText);
                return true;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();
        if(id==R.id.serach){
            return true;
        }else if(id==R.id.cart){
            Toast.makeText(this, "cart is click", Toast.LENGTH_SHORT).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}