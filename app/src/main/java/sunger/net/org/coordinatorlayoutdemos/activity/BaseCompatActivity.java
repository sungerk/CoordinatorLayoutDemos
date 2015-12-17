package sunger.net.org.coordinatorlayoutdemos.activity;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import sunger.net.org.coordinatorlayoutdemos.R;
import sunger.net.org.coordinatorlayoutdemos.utils.SystemBarTintManager;


/**
 * Created by sunger on 2015/10/27.
 */
public class BaseCompatActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStateBarColor(R.color.colorPrimary);
    }

    protected void setStateBarColor(int resId) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window win = getWindow();
            WindowManager.LayoutParams winParams = win.getAttributes();
            final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
            winParams.flags |= bits;
            win.setAttributes(winParams);
            SystemBarTintManager tintManager = new SystemBarTintManager(this);
            tintManager.setStatusBarTintEnabled(true);
            tintManager.setStatusBarTintResource(resId);
            tintManager.setStatusBarDarkMode(true, this);
        }
    }


    protected void setUpCommonBackTooblBar(int toolbarId, String title) {
        Toolbar mToolbar = (Toolbar) findViewById(toolbarId);
        mToolbar.setTitle(title);
        setSupportActionBar(mToolbar);
        toobarAsBackButton(mToolbar);
    }

    protected void setUpCommonBackTooblBar(int toolbarId, int titleId) {
        setUpCommonBackTooblBar(toolbarId, getString(titleId));
    }

    public int getActionBarSize() {
        TypedValue tv = new TypedValue();
        if (getTheme().resolveAttribute(android.R.attr.actionBarSize, tv, true)) {
            return TypedValue.complexToDimensionPixelSize(tv.data, getResources().getDisplayMetrics());
        }
        return 0;
    }


    public View getRootView() {
        return findViewById(android.R.id.content);
    }


    /**
     * toolbar点击返回，模拟系统返回
     * 设置toolbar 为箭头按钮
     * app:navigationIcon="?attr/homeAsUpIndicator"
     *
     * @param toolbar
     */
    public void toobarAsBackButton(Toolbar toolbar) {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }


}
