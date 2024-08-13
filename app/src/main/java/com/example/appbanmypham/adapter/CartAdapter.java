package com.example.appbanmypham.adapter;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.appbanmypham.R;
import com.example.appbanmypham.model.Cart;

import java.text.DecimalFormat;
import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {

    List<Cart> list;


    public CartAdapter(List<Cart> list) {
        this.list = list;
    }

    public List<Cart> getList() {
        return list;
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CartViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cart, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        holder.onBind(position);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    private void initView() {

    }

    class CartViewHolder extends RecyclerView.ViewHolder {

        private AppCompatCheckBox radioButton;
        private ImageView imageView2;
        private TextView name;
        private TextView description;
        private TextView price;
        private ImageView imgDelete;
        private TextView btnTru;
        private TextView tvSoLuong;
        private TextView btnCong;

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            radioButton = itemView.findViewById(R.id.radioButton);
            imageView2 = itemView.findViewById(R.id.imageView2);
            name = itemView.findViewById(R.id.name);
            description = itemView.findViewById(R.id.description);
            price = itemView.findViewById(R.id.price);
            imgDelete = itemView.findViewById(R.id.imgDelete);
            btnTru = itemView.findViewById(R.id.btnTru);
            tvSoLuong = itemView.findViewById(R.id.tvSoLuong);
            btnCong = itemView.findViewById(R.id.btnCong);
        }

        public void onBind(int position) {
            boolean checked = false;
            Cart item = list.get(position);
            Glide.with(itemView.getContext()).load(item.getItem().getImageUrl()).into(imageView2);
            name.setText(item.getItem().getName());
            description.setText(item.getItem().getDescription());
            DecimalFormat formatter = new DecimalFormat("#,###");
            price.setText(formatter.format(item.getItem().getPrice()) + " VNĐ");
            tvSoLuong.setText(item.getSoluong() + "");

            btnCong.setOnClickListener(view -> {
                int soluong = Integer.parseInt(tvSoLuong.getText().toString());
                soluong++;
                tvSoLuong.setText(soluong + "");
                item.setSoluong(soluong);
            });

            btnTru.setOnClickListener(view -> {
                int soluong = Integer.parseInt(tvSoLuong.getText().toString());
                if (soluong == 1) {
                    item.setSoluong(soluong);
                    return;
                }
                soluong--;
                tvSoLuong.setText(soluong + "");
                item.setSoluong(soluong);

            });

            imgDelete.setOnClickListener(v -> {

                if (onItemClickListener != null) {
                    onItemClickListener.onDelete(position);
                }
            });
            radioButton.setOnClickListener(v -> {
                item.setCheck(!item.isCheck());
                if (onItemClickListener != null) {
                    onItemClickListener.setTotalPrice();
                }
            });
        }
    }

    OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {
        void onDelete(int position);

        void setTotalPrice();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}
