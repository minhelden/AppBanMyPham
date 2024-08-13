package com.example.appbanmypham.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appbanmypham.R;
import com.example.appbanmypham.activity.ChiTietSanPhamActivity;
import com.example.appbanmypham.adapter.CartAdapter;
import com.example.appbanmypham.db.SharedPref;
import com.example.appbanmypham.model.Cart;
import com.example.appbanmypham.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.function.Consumer;

import de.hdodenhof.circleimageview.CircleImageView;


public class GioHangFragment extends Fragment implements CartAdapter.OnItemClickListener {

    FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
    User user;
    List<Cart> list = new ArrayList<>();
    CartAdapter adapter;
    RecyclerView recyclerView;
    View view;
    private CircleImageView logoImageView;
    private TextView titleTxt;
    private LinearLayout linearLayoutThanhToan;
    private Button button;
    private TextView textView11;
    private ConstraintLayout ctl;
    private ImageView imageView2;
    private TextView muangay;

    public GioHangFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_gio_hang, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
        user = new Gson().fromJson(SharedPref.read(SharedPref.USER_DATA, ""), User.class);
        getCart();
    }

    private void initView() {
        recyclerView = view.findViewById(R.id.list);
        logoImageView = view.findViewById(R.id.logoImageView);
        titleTxt = view.findViewById(R.id.titleTxt);
        linearLayoutThanhToan = view.findViewById(R.id.linearLayoutThanhToan);
        button = view.findViewById(R.id.button);
        ctl = view.findViewById(R.id.ctl);
        imageView2 = view.findViewById(R.id.imageView2);
        muangay = view.findViewById(R.id.muangay);

        muangay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (getListDachon().size() == 0) {
                    Toast.makeText(requireContext(), "Bạn chưa chọn sản phẩm để đặt hàng", Toast.LENGTH_SHORT).show();
                } else {
                    HashMap<String, Object> order = new HashMap<>();
                    order.put("ngaydat", new SimpleDateFormat("dd-MMM-yyyy").format(new Date()));
                    order.put("username", user.getUsername());
                    order.put("sanpham", getListDachon());
                    FirebaseDatabase.getInstance().getReference("order").child(String.valueOf(System.currentTimeMillis())).setValue(order).addOnCompleteListener(task -> {
                        Toast.makeText(getContext(), "Đặt hàng thành công", Toast.LENGTH_SHORT).show();
                        getListDachon().forEach(new Consumer<Cart>() {
                            @Override
                            public void accept(Cart cart) {
                                delete(cart);
                            }
                        });
                    }).addOnFailureListener(e -> {
                        Toast.makeText(getContext(), e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    });
                }
            }
        });
    }

    private List<Cart> getListDachon() {
        List<Cart> list1 = new ArrayList<>();
        if (list.size() > 0) {
            list.forEach(cart -> {
                if (cart.isCheck()) {
                    list1.add(cart);
                }
            });
        }
        return list1;
    }

    private void getCart() {
        if (user != null) {
            list = new ArrayList<>();
            FirebaseDatabase.getInstance().getReference().child("cart").orderByChild("username").equalTo(user.getUsername())
                    .addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {

                            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                Cart cart = snapshot.getValue(Cart.class);
                                if (cart != null) list.add(cart);
                            }
                            if (list.size() == 0) {
                                imageView2.setVisibility(View.VISIBLE);
                                view.findViewById(R.id.tvChuacosanpham).setVisibility(View.VISIBLE);
                            }
                            adapter = new CartAdapter(list);
                            adapter.setOnItemClickListener(GioHangFragment.this);
                            recyclerView.setAdapter(adapter);
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            // Xử lý khi có lỗi xảy ra
                        }
                    });
        }


    }

    void delete(Cart cartDelete) {
        FirebaseDatabase.getInstance().getReference().child("cart").orderByChild("username").equalTo(user.getUsername())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot appleSnapshot : snapshot.getChildren()) {
                            Cart cart = appleSnapshot.getValue(Cart.class);
                            if (cart != null) {
                                if (cart.getIdsanpham().equals(cartDelete.getIdsanpham())) {
                                    appleSnapshot.getRef().removeValue();
                                    list.remove(cartDelete);
                                }
                            }
                        }
                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }

    @Override
    public void onDelete(int position) {
        new AlertDialog.Builder(getContext()).setMessage("Bạn có muốn xóa không ?").setPositiveButton("Có", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                FirebaseDatabase.getInstance().getReference().child("cart").orderByChild("username").equalTo(user.getUsername())
                        .addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                for (DataSnapshot appleSnapshot : snapshot.getChildren()) {
                                    Cart cart = appleSnapshot.getValue(Cart.class);
                                    if (cart != null) {
                                        if (list.get(position).getIdsanpham().equals(cart.getIdsanpham())) {
                                            appleSnapshot.getRef().removeValue();
                                            list.remove(position);
                                            adapter.notifyItemRemoved(position);
                                        }
                                    }
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
            }
        }).setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        }).setIcon(android.R.drawable.ic_dialog_alert).show();

    }

    @Override
    public void setTotalPrice() {

    }
}