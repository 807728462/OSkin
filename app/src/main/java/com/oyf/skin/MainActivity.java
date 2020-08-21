package com.oyf.skin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.oyf.skin.adapter.MyFragmentPagerAdapter;
import com.oyf.skin.fragment.BuyFragment;
import com.oyf.skin.fragment.PersonalFragment;
import com.oyf.skin.view.CustomTableLayout;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        CustomTableLayout tableLayout = findViewById(R.id.tab_layout);

        ViewPager viewPager = findViewById(R.id.viewPager);

        List<Fragment> lists = new ArrayList<>();
        lists.add(new HomeFragment());
        lists.add(new BuyFragment());
        lists.add(new PersonalFragment());

        List<String> titles = new ArrayList<>();
        titles.add("首页");
        titles.add("购买");
        titles.add("个人");

        MyFragmentPagerAdapter myFragmentPagerAdapter =
                new MyFragmentPagerAdapter(getSupportFragmentManager(), lists, titles);
        viewPager.setAdapter(myFragmentPagerAdapter);

        tableLayout.setupWithViewPager(viewPager);
    }

    /**
     * 跳转到，设置皮肤换肤操作
     *
     * @param view
     */
    public void skinSelect(View view) {
        startActivity(new Intent(this, SkinActivity.class));
    }
}
