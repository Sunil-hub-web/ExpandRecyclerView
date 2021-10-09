package com.example.expandrecyclerview;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collection;

public class PaymentDetailsAdapter extends RecyclerView.Adapter<PaymentDetailsAdapter.MyViewHolder> implements Filterable {

    Context context;
    ArrayList<PaymentDetails> payment1;
    ArrayList<PaymentDetails> payment2;
    ArrayList<PaymentDetails> payment3;

    RecyclerViewClickInterface recyclerViewClickInterface;
    private OnItemClickListener mListener;


    public PaymentDetailsAdapter(MainActivity mainActivity, ArrayList<PaymentDetails> payment, RecyclerViewClickInterface recyclerViewClickInterface) {

        this.context = mainActivity;
        this.payment1 = payment;
        this.recyclerViewClickInterface = recyclerViewClickInterface;
        this.payment2 = new ArrayList<>();
        this.payment2.addAll(payment);
    }

    public PaymentDetailsAdapter(BindingFeature bindingFeature, ArrayList<PaymentDetails> payment) {

        this.context = bindingFeature;
        this.payment1 = payment;
        this.payment3 = new ArrayList<>();
        this.payment3.addAll(payment);
    }

   /* public void filterList(ArrayList<PaymentDetails> filteredList) {

        this.payment1 = filteredList;
        notifyDataSetChanged();

    }*/


    public interface OnItemClickListener {
        void onDeleteClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    @NonNull
    @Override
    public PaymentDetailsAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product, parent, false);
        return new MyViewHolder(view, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull PaymentDetailsAdapter.MyViewHolder holder, int position) {

        PaymentDetails paymentDetails = payment1.get(position);

        holder.date.setText(paymentDetails.getDate());
        holder.price.setText(paymentDetails.getPrice());
        holder.delivery.setText(paymentDetails.getDelivery());
        holder.purchased.setText(paymentDetails.getPurchased());
        holder.settext.setText(String.valueOf(position));

        boolean isExpand = paymentDetails.isExpanded();
        holder.expandableLayout.setVisibility(isExpand ? View.VISIBLE : View.GONE);


    }

    @Override
    public int getItemCount() {
        return payment1.size();
    }


    @Override
    public Filter getFilter() {

        Filter filter = new Filter() {

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {


                ArrayList<PaymentDetails> resultData = new ArrayList<>();

                if(constraint.toString().isEmpty()){

                    resultData.addAll(payment3);

                }else{

                    for(PaymentDetails item : payment3){

                        if(item.getPurchased().toLowerCase().contains((constraint))){
                            resultData.add(item);
                        }
                    }
                }

                FilterResults filterResults = new FilterResults();
                filterResults.count = resultData.size();
                filterResults.values = resultData;
                return filterResults;

            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {

                payment1.clear();
                payment1.addAll((Collection<? extends PaymentDetails>) results.values);
                notifyDataSetChanged();

            }
        };

        return filter;
    }



    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView date, price, delivery, settext,purchased;
        LinearLayout expandableLayout;
        RelativeLayout product;
        ImageView btn_delete;

        public MyViewHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);

            date = itemView.findViewById(R.id.date);
            price = itemView.findViewById(R.id.price);
            delivery = itemView.findViewById(R.id.delivery);
            purchased = itemView.findViewById(R.id.purchased);
            expandableLayout = itemView.findViewById(R.id.expandableLayout);
            product = itemView.findViewById(R.id.product);
            settext = itemView.findViewById(R.id.settext);
            btn_delete = itemView.findViewById(R.id.delete);

            btn_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onDeleteClick(position);
                        }
                    }
                }
            });

            product.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    PaymentDetails paymentDetails = payment1.get(getAdapterPosition());
                    paymentDetails.setExpanded(!paymentDetails.isExpanded());
                    notifyItemChanged(getAdapterPosition());
                }
            });



            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    recyclerViewClickInterface.onItemClick(getAdapterPosition());
                }
            });

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {

                    recyclerViewClickInterface.onLongItemClick(getAdapterPosition());
                    return true;
                }
            });


        }
    }

    public void filter(CharSequence charSequence){

        ArrayList<PaymentDetails> tempArrayList = new ArrayList<PaymentDetails>();

        if(!TextUtils.isEmpty(charSequence)){

            for(PaymentDetails item : payment1){

                if(item.getPurchased().toLowerCase().contains((charSequence))){
                    tempArrayList.add(item);
                }
            }

        }else{
            payment1.addAll(payment2);
        }

        payment1.clear();
        payment1.addAll(tempArrayList);
        notifyDataSetChanged();
        tempArrayList.clear();
    }


}
