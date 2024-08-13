package com.example.appbanmypham.activity;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.widget.Toast;

import com.example.appbanmypham.R;
import com.example.appbanmypham.adapter.ItemAdapter;
import com.example.appbanmypham.adapter.ItemSearchAdapter;
import com.example.appbanmypham.model.Item;
import com.example.appbanmypham.model.Item_Search;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity {
    private RecyclerView rcvItems;
    private ItemSearchAdapter itemSearchAdapter;
    private SearchView searchView;
    private List<Item> itemList;

    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        setTitle("Tìm kiếm sản phẩm"); // đổi tên cho title
        rcvItems = findViewById(R.id.rcv_itemsSearch);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rcvItems.setLayoutManager(linearLayoutManager);
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(this,DividerItemDecoration.VERTICAL);
        rcvItems.addItemDecoration(itemDecoration);
        // Kết nối Firebase Realtime Database
        databaseReference = FirebaseDatabase.getInstance().getReference("items");
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu, menu);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);

        // Tìm kiếm khi nhập văn bản vào SearchView
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                itemSearchAdapter.getFilter().filter(newText);
                return true;
            }
        });
        return true;
    }
    @Override
    protected void onStart() {
        super.onStart();
        // Lắng nghe sự thay đổi của dữ liệu trên Firebase Realtime Database
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<Item_Search> itemList = new ArrayList<>();
                for (DataSnapshot itemSnapshot : dataSnapshot.getChildren()) {
                    Item_Search item = itemSnapshot.getValue(Item_Search.class);
                    itemList.add(item);
                }
                // Khởi tạo adapter sau khi có dữ liệu
                itemSearchAdapter = new ItemSearchAdapter(itemList, SearchActivity.this);
                // Cập nhật RecyclerView với adapter mới
                rcvItems.setAdapter(itemSearchAdapter);
                // Bắt sự kiện khi nhấp vào một sản phẩm trong danh sách
                itemSearchAdapter.setOnItemClickListener(new ItemSearchAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(int position) {
                        // Lấy sản phẩm được nhấp vào từ danh sách
                        Item_Search clickedItem = itemList.get(position);

                        // Tạo Intent để chuyển sang ChiTietSanPhamActivity và truyền dữ liệu của sản phẩm
                        Intent intent = new Intent(SearchActivity.this, ChiTietSanPhamActivity.class);
                        intent.putExtra("item_search", clickedItem); // Truyền dữ liệu của sản phẩm
                        startActivity(intent);
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(SearchActivity.this, "Failed to load items.", Toast.LENGTH_SHORT).show();
            }
        });
    }
    //    public void onItemClick(int position) {
//        // Lấy ID của sản phẩm được nhấp vào từ danh sách sản phẩm
//        String productId = itemList.get(position).getId(); // Giả sử có một đối tượng sản phẩm là itemList và có phương thức getId() để lấy ID của sản phẩm
//
//        // Tạo Intent để chuyển sang ChiTietSanPhamActivity và truyền ID của sản phẩm
//        Intent intent = new Intent(SearchActivity.this, ChiTietSanPhamActivity.class);
//        intent.putExtra("id", productId);
//        startActivity(intent);
//    }
    // xu ly an back thu thanh search
    @Override
    public void onBackPressed() {
        if (!searchView.isIconified()) {
            searchView.setIconified(true);
            return;
        }
        super.onBackPressed();
    }
}