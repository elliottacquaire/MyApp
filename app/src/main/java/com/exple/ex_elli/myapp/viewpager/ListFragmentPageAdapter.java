package com.exple.ex_elli.myapp.viewpager;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * 项目名称：MyApp
 * 类描述：
 * 创建人：ex-lixuyang
 * 创建时间：2017/9/18 10:18
 * 修改人：ex-lixuyang
 * 修改时间：2017/9/18 10:18
 * 修改备注：
 */

public class ListFragmentPageAdapter extends FragmentPagerAdapter {
    private List<Fragment> fragmentList;

    public ListFragmentPageAdapter(FragmentManager fm,List<Fragment> fragmentList) {
        super(fm);
        this.fragmentList = fragmentList;
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }
}
