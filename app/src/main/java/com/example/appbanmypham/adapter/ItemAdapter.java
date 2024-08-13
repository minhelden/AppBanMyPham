package com.example.appbanmypham.adapter;

import com.bumptech.glide.Glide;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.appbanmypham.R;
import com.example.appbanmypham.activity.ChiTietSanPhamActivity;
import com.example.appbanmypham.activity.SuaSanPhamActivity;
import com.example.appbanmypham.db.SharedPref;
import com.example.appbanmypham.model.Item;

import java.text.DecimalFormat;
import java.util.List;
import java.io.Serializable;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemViewHolder> {
    private List<Item> mListItem;

    public ItemAdapter(List<Item> mListItem) {
        this.mListItem = mListItem;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sp, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        Item item = mListItem.get(position);
        if (item == null) {
            return;
        }

        Glide.with(holder.itemView.getContext())
                .load(item.getImageUrl())
                .placeholder(R.drawable.bongtaytrang1)
                .error(R.drawable.bongtaytrang2)
                .into(holder.imgItem);

        holder.tvNameItem.setText(item.getName());

        // Định dạng giá sản phẩm
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        String formattedPrice = decimalFormat.format(item.getPrice()) + "đ";
        holder.tvPriceItem.setText(formattedPrice);


        // Trong phương thức onBindViewHolder
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (SharedPref.read(SharedPref.IS_ADMIN, false)) {
                    Intent intent = new Intent(holder.itemView.getContext(), SuaSanPhamActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("data", mListItem.get(position));
                    intent.putExtras(bundle);
                    holder.itemView.getContext().startActivity(intent);
                } else {
                    int clickedPosition = holder.getAdapterPosition(); // Lấy vị trí của item được nhấn
                    if (clickedPosition != RecyclerView.NO_POSITION) {
                        // Get the clicked item
                        Item clickedItem = mListItem.get(clickedPosition);

                        // Open the ChiTietSanPhamActivity and pass data of the clicked item
                        Intent intent = new Intent(holder.itemView.getContext(), ChiTietSanPhamActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("data", mListItem.get(position));
                        intent.putExtra("id", clickedItem.getId()); // Truyền ID của sản phẩm được nhấn
                        intent.putExtras(bundle);
                        holder.itemView.getContext().startActivity(intent);
                    }

                }
            }
        });

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                if (onItemClickListener != null) {
                    onItemClickListener.onDelete(position);
                    return true;
                }
                return false;
            }
        });

    }

    public interface OnItemClickListener {
        void onDelete(int position);
    }

    OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public int getItemCount() {
        if (mListItem != null) {
            return mListItem.size();
        }
        return 0;
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgItem;
        private TextView tvNameItem;
        private TextView tvPriceItem;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            imgItem = itemView.findViewById(R.id.img_item);
            tvNameItem = itemView.findViewById(R.id.tv_name_item);
            tvPriceItem = itemView.findViewById(R.id.tv_price_item); // Đặt ID cho TextView giá sản phẩm trong layout của bạn
        }
    }
}
