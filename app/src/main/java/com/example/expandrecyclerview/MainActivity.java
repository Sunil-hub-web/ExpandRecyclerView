package com.example.expandrecyclerview;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Canvas;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.expandrecyclerview.databinding.ActivityMainBinding;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;

public class MainActivity extends AppCompatActivity implements RecyclerViewClickInterface {

    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;
    PaymentDetailsAdapter paymentDetailsAdapter;

    ArrayList<PaymentDetails> payment = new ArrayList<>();

    //ActivityMainBinding activityMainBinding;

    EditText searchView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       /* activityMainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = activityMainBinding.getRoot();
        setContentView(view);*/

        searchView = findViewById(R.id.serach_view);

        recyclerView = findViewById(R.id.recycler);
        linearLayoutManager = new LinearLayoutManager(MainActivity.this,LinearLayoutManager.VERTICAL,false);

        payment.add(new PaymentDetails("12-05-2021","299","deliveryed","Purchased products One"));
        payment.add(new PaymentDetails("11-06-2021","399","deliveryed","Purchased products Two"));
        payment.add(new PaymentDetails("10-07-2021","499","deliveryed","Purchased products Three"));
        payment.add(new PaymentDetails("9-08-2021","599","deliveryed","Purchased products Four"));

        paymentDetailsAdapter = new PaymentDetailsAdapter(MainActivity.this,payment,this);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(paymentDetailsAdapter);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this,DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);

        paymentDetailsAdapter.setOnItemClickListener(new PaymentDetailsAdapter.OnItemClickListener() {
            @Override
            public void onDeleteClick(int position) {
                removeItem(position);
            }
        });

        searchView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                paymentDetailsAdapter.filter(s);

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

      /*  searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                filter(newText);
                return true;
            }
        });*/



    }

    /*public void filter(CharSequence charSequence){

        ArrayList<PaymentDetails> tempArrayList = new ArrayList<PaymentDetails>();

        if(!TextUtils.isEmpty(charSequence)){

            for(PaymentDetails item : payment){

                if(item.getPurchased().toLowerCase().contains((charSequence))){
                    tempArrayList.add(item);
                }
                paymentDetailsAdapter.filterList(tempArrayList);
            }
        }
    }*/

   /* private void filter(String newText) {

        ArrayList<PaymentDetails> filteredList = new ArrayList<>();
        for(PaymentDetails item : payment){

            if(item.getPurchased().toLowerCase().contains((newText.toLowerCase()))){
                filteredList.add(item);
            }
        }

        paymentDetailsAdapter.filterList(filteredList);
    }*/

    @Override
    public void onItemClick(int position) {

        Toast.makeText(this, payment.get(position).toString(), Toast.LENGTH_SHORT).show();
        payment.remove(position);
        paymentDetailsAdapter.notifyItemRemoved(position);
    }

    @Override
    public void onLongItemClick(int position) {

        payment.remove(position);
        paymentDetailsAdapter.notifyItemRemoved(position);

    }

    public void removeItem(int position) {

        payment.remove(position);
        paymentDetailsAdapter.notifyItemRemoved(position);
    }

    PaymentDetails deleteMovie = null;

    List<PaymentDetails> achiveMovies = new ArrayList<>();

     ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
         @Override
         public boolean onMove(@NonNull  RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull  RecyclerView.ViewHolder target) {
             return false;
         }

         @Override
         public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

             int position = viewHolder.getAdapterPosition();

             switch (direction){

                 case ItemTouchHelper.LEFT:
                      deleteMovie = payment.get(position);
                     payment.remove(position);
                     paymentDetailsAdapter.notifyItemRemoved(position);
                     Snackbar.make(recyclerView,deleteMovie.toString(),Snackbar.LENGTH_LONG)
                             .setAction("undo", new View.OnClickListener() {
                                 @Override
                                 public void onClick(View v) {

                                     payment.add(position,deleteMovie);
                                     paymentDetailsAdapter.notifyItemInserted(position);
                                 }
                             }).show();
                     break;

                 case ItemTouchHelper.RIGHT:

                     final PaymentDetails moviname = payment.get(position);
                     achiveMovies.add(moviname);

                     payment.remove(position);
                     paymentDetailsAdapter.notifyItemRemoved(position);

                     Snackbar.make(recyclerView,moviname+", .Achived",Snackbar.LENGTH_LONG)
                             .setAction("undo", new View.OnClickListener() {
                                 @Override
                                 public void onClick(View v) {

                                     achiveMovies.remove(achiveMovies.lastIndexOf(moviname));
                                     payment.add(position,moviname);
                                     paymentDetailsAdapter.notifyItemInserted(position);

                                 }
                             }).show();

                      break;
             }

         }

         @Override
         public void onChildDraw(@NonNull  Canvas c, @NonNull  RecyclerView recyclerView, @NonNull  RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
             super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);

             new RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                     .addSwipeLeftBackgroundColor(ContextCompat.getColor(MainActivity.this,R.color.purple_500))
                     .addSwipeLeftActionIcon(R.drawable.baseline_delete)
                     .addSwipeRightBackgroundColor(ContextCompat.getColor(MainActivity.this,R.color.purple_700))
                     .addSwipeRightActionIcon(R.drawable.baseline_archive)
                     .create()
                     .decorate();
         }
     };
}