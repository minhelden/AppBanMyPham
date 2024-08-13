package com.example.appbanmypham.adapter;

import com.bumptech.glide.Glide;
import com.example.appbanmypham.R;
import com.example.appbanmypham.activity.ChiTietSanPhamActivity;
import com.example.appbanmypham.model.Item;
import com.example.appbanmypham.model.Item_Search;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ItemSearchAdapter extends RecyclerView.Adapter<ItemSearchAdapter.ItemViewHolder> implements Filterable {

    private List<Item_Search> mListItems;
    private List<Item_Search> mListItemsOld;
    private Context mContext;
    private OnItemClickListener listener; // Thêm biến listener

    // Interface cho sự kiện click
    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    // Phương thức để thiết lập listener
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public ItemSearchAdapter(List<Item_Search> mListItems, Context context) {
        this.mListItems = mListItems;
        this.mListItemsOld = mListItems;
        this.mContext = context;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_search, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        Item_Search item = mListItems.get(position);
        if (item == null) {
            return;
        }

        holder.tvName_item.setText(item.getName());
        holder.tvAddress_item.setText(item.getCategory());
        // Load hình ảnh từ URL bằng Glide
        Glide.with(mContext)
                .load(item.getImageUrl()) // Đường dẫn của hình ảnh trong Firebase
                .placeholder(R.drawable.taytrang_loreal) // Hình ảnh placeholder (nếu cần)
                .error(R.drawable.bongtaytrang2) // Hình ảnh lỗi (nếu có lỗi khi tải)
                .into(holder.imgItem); // ImageView để hiển thị hình ảnh

        // Gọi sự kiện click khi một item được nhấp vào
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int clickedPosition = holder.getAdapterPosition(); // Lấy vị trí của item được nhấn
                if (clickedPosition != RecyclerView.NO_POSITION) {
                    // Get the clicked item
                    Item_Search clickedItem = mListItems.get(clickedPosition);

                    // Open the ChiTietSanPhamActivity and pass data of the clicked item
                    Intent intent = new Intent(holder.itemView.getContext(), ChiTietSanPhamActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("data", mListItems.get(position));
                    intent.putExtra("id", clickedItem.getId()); // Truyền ID của sản phẩm được nhấn
                    intent.putExtras(bundle);
                    holder.itemView.getContext().startActivity(intent);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        if (mListItems != null) {
            return mListItems.size();
        }
        return 0;
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        private CircleImageView imgItem;
        private TextView tvName_item;
        private TextView tvAddress_item;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            imgItem = itemView.findViewById(R.id.img_itemsearch);
            tvName_item = itemView.findViewById(R.id.tv_nameSearch);
            tvAddress_item = itemView.findViewById(R.id.tv_addressSearch);
        }
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String strSearch = constraint.toString();
                if (strSearch.isEmpty()) {
                    mListItems = mListItemsOld;
                } else {
                    List<Item_Search> list = new ArrayList<>();
                    for (Item_Search item_search : mListItemsOld) {
                        if(item_search.getName().toLowerCase().contains(strSearch.toLowerCase())) {
                            list.add(item_search);
                        }
                    }
                    mListItems = list;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = mListItems;

                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                mListItems = (List<Item_Search>) results.values;
                notifyDataSetChanged();
            }
        };
    }
}
