package com.example.appbanmypham.fragment;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {
    public ViewPagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return new TrangChuFragment();
        } else if (position == 1) {
            return new DanhMucFragment();
        } else if (position == 2) {
            return new GioHangFragment();
        } else if (position == 3) {
            return new TaiKhoanFragment();
        } else if (position == 4) {
            return new DanhMucFragment();
        }
        return null;
    }

    @Override
    public int getCount() {
        return 5;
    }
    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        String title = "";
        if (position == 0) {
            title = "Trang chủ";
        } else if (position == 1) {
            title = "Danh mục";
        }
        else  if (position == 2){
            title = "Giỏ hàng";
        }
        else  if (position == 3){
            title = "Tài khoản";
        }
        return title;
    }

}
