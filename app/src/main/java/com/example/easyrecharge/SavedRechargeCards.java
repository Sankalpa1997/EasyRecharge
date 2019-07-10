package com.example.easyrecharge;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import model.RechargeCard;

public class SavedRechargeCards extends Fragment {

    ArrayList<RechargeCard> rechargeCards;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_saved_cards, container, false);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rechargeCards = new ArrayList<>();
        rechargeCards.add(new RechargeCard(001,"Dialog","000012345678"));
        rechargeCards.add(new RechargeCard(002,"Mobitel","000012345678"));

        RecyclerView recyclerView = getView().findViewById(R.id.recycleView);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        RechargeCardViewAdapter rechargeCardViewAdapter = new RechargeCardViewAdapter(rechargeCards);
        recyclerView.setAdapter(rechargeCardViewAdapter);

    }
}


class RechargeCardViewHolder extends RecyclerView.ViewHolder{

    public ImageView imageView;
    public TextView providerName;
    public TextView rechargeNumber;


    public RechargeCardViewHolder(@NonNull View itemView) {
        super(itemView);

        imageView = itemView.findViewById(R.id.delete);
        providerName = itemView.findViewById(R.id.providerName);
        rechargeNumber = itemView.findViewById(R.id.rechargeNumber);
    }
}

class RechargeCardViewAdapter extends RecyclerView.Adapter{

    ArrayList<RechargeCard> list;

    public RechargeCardViewAdapter(ArrayList<RechargeCard> list){
        this.list = list;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.recharge_card_view, viewGroup, false);

        RechargeCardViewHolder rechargeCardViewHolder = new RechargeCardViewHolder(view);


        return rechargeCardViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int i) {

        RechargeCard rechargeCard = list.get(i);
        RechargeCardViewHolder viewHolder = (RechargeCardViewHolder) holder;
        viewHolder.providerName.setText(rechargeCard.getProviderName());
        viewHolder.rechargeNumber.setText(rechargeCard.getCardNumber());


    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
