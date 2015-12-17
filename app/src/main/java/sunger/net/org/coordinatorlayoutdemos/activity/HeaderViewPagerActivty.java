package sunger.net.org.coordinatorlayoutdemos.activity;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.Toolbar;

import java.util.ArrayList;
import java.util.List;

import sunger.net.org.coordinatorlayoutdemos.R;
import sunger.net.org.coordinatorlayoutdemos.adapter.MainTabAdapter;
import sunger.net.org.coordinatorlayoutdemos.fragment.NestedscrollFragment;
import sunger.net.org.coordinatorlayoutdemos.fragment.RecyclerFragment;
import sunger.net.org.coordinatorlayoutdemos.utils.Utils;

/**
 * Created by sunger on 15/12/15.
 */
public class HeaderViewPagerActivty extends BaseCompatActivity implements SwipeRefreshLayout.OnRefreshListener, AppBarLayout.OnOffsetChangedListener {
    private ViewPager mViewPager;
    private TabLayout mTabLayout;
    private MainTabAdapter mAdapter;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private AppBarLayout appBarLayout;
    private RecyclerFragment fragment;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_header_viewpager);

        CollapsingToolbarLayout collapsingToolbar = (CollapsingToolbarLayout) findViewById(
                R.id.collapse_toolbar);
        collapsingToolbar.setTitleEnabled(false);
        appBarLayout = (AppBarLayout) findViewById(R.id.appBarLayout);
        appBarLayout.addOnOffsetChangedListener(this);

        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_container);
        mSwipeRefreshLayout.setProgressViewOffset(false, 0, 100);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
        mSwipeRefreshLayout.setOnRefreshListener(this);

        toolbar = (Toolbar) findViewById(R.id.toolbar);

        //set the toolbar
        int toolbar_hight = Utils.getToolbarHeight(this) * 2;
        CollapsingToolbarLayout.LayoutParams params = (CollapsingToolbarLayout.LayoutParams) toolbar.getLayoutParams();
        params.height = toolbar_hight;
        toolbar.setLayoutParams(params);

        setUpCommonBackTooblBar(R.id.toolbar, "HeaderViewPager");
        mViewPager = (ViewPager) findViewById(R.id.view_pager);
        mTabLayout = (TabLayout) findViewById(R.id.tab_layout);

        List<Fragment> fragments = new ArrayList<>();
        fragment = new RecyclerFragment();
        fragments.add(fragment);
        fragments.add(new NestedscrollFragment());
        fragments.add(new NestedscrollFragment());

        List<String> titles = new ArrayList<>();
        titles.add("Item1");
        titles.add("Item2");
        titles.add("Item3");

        mTabLayout.addTab(mTabLayout.newTab().setText(titles.get(0)));
        mTabLayout.addTab(mTabLayout.newTab().setText(titles.get(1)));
        mTabLayout.addTab(mTabLayout.newTab().setText(titles.get(2)));


        mAdapter = new MainTabAdapter(getSupportFragmentManager(), fragments, titles);
        mViewPager.setAdapter(mAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.setTabsFromPagerAdapter(mAdapter);
    }


    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int i) {
        mSwipeRefreshLayout.setEnabled(i == 0);
        float alpha = (float) Math.abs(i) / (float) appBarLayout.getTotalScrollRange() * 1.0f;
        toolbar.setAlpha(alpha);
    }


    @Override
    public void onRefresh() {
       if(mViewPager.getCurrentItem()==0) {
           fragment.refresh();
       }
        mSwipeRefreshLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                mSwipeRefreshLayout.setRefreshing(false);
            }
        }, 2000);

    }

    @Override
    protected void onResume() {
        super.onResume();
        appBarLayout.addOnOffsetChangedListener(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        appBarLayout.removeOnOffsetChangedListener(this);
    }
}
