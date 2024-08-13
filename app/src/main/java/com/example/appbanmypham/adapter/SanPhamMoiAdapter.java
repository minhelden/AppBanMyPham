package com.example.appbanmypham.adapter;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.appbanmypham.R;
import com.example.appbanmypham.model.SanPhamMoi;

import java.util.List;

public class SanPhamMoiAdapter extends RecyclerView.Adapter<SanPhamMoiAdapter.MyViewHolder> {
    Context context;
    List<SanPhamMoi> array;

    public SanPhamMoiAdapter(Context context, List<SanPhamMoi> array) {
        this.context = context;
        this.array = array;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sp,parent,false);
        return new MyViewHolder(item);
//        LayoutInflater layoutInflater = LayoutInflater.from(context);
//        View viewItem = layoutInflater.inflate(R.layout.item_sp_moi, parent, false);
//        return new MyViewHolder(viewItem);

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        SanPhamMoi sanPhamMoi = array.get(position);
        holder.tv_name_item.setText(sanPhamMoi.getTensp());
        holder.tv_price_item.setText(sanPhamMoi.getGiasp());
        Glide.with(context)
                .load(sanPhamMoi.getHinhanh())
                .into(holder.img_item);
    }

    @Override
    public int getItemCount() {
        if (array != null)
        {
            return array.size();
        }
        return 0;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        //        TextView txtgia, txtten;
//        ImageView imghinhanh;
        TextView tv_name_item, tv_price_item;
        ImageView img_item;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
//            txtgia = itemView.findViewById(R.id.itemsp_gia);
//            txtten = itemView.findViewById(R.id.itemsp_ten);
//            imghinhanh = itemView.findViewById(R.id.itemsp_image);
            tv_name_item=itemView.findViewById(R.id.tv_name_item);
            tv_price_item=itemView.findViewById(R.id.tv_price_item);
            img_item=itemView.findViewById(R.id.img_item);
        }
    }
}
