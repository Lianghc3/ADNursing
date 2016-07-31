package softwaredesign.adnursing.Activity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import cn.qqtheme.framework.picker.FilePicker;
import softwaredesign.adnursing.ApplicationManager;
import softwaredesign.adnursing.HttpApplication;
import softwaredesign.adnursing.Utils.HttpUtils;
import softwaredesign.adnursing.R;
import softwaredesign.adnursing.Data.UserData;
import softwaredesign.adnursing.Utils.ImageUtils;

public class MyActivity extends AppCompatActivity implements View.OnClickListener {

    private LinearLayout bottomHomeIcon;
    private LinearLayout bottomCommunicateIcon;
    private LinearLayout bottomTestIcon;
    private LinearLayout bottomMyIcon;

    private TextView my_name;
    private TextView my_id;
    private ImageView my_sculpture;

    private LinearLayout my_collection;
    private LinearLayout my_post;
    private LinearLayout my_review;
    private LinearLayout my_setting;
    private RelativeLayout my_basic_info;

    private UserData userData;              // 保存用户信息
    private Bitmap sculpture;

    /**
     * Handler用于显示获取到的用户信息
     */
    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case 0x001:
                    if (userData != null) {
                        my_name.setText(userData.getName());
                        my_id.setText("默友号："+ HttpApplication.getUserId());
                        if (userData.getImageDir().equals("")) {
                            my_sculpture.setImageResource(R.mipmap.sculpture_unknown_default);
                        } else {
                            ImageUtils.glideGetImage(MyActivity.this, userData.getImageDir(), my_sculpture, R.mipmap.sculpture_unknown_default);
                        }
                    }
                    break;
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ApplicationManager.getInstance().addActivity(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);

        initView();
        requestUserInfo();

    }


    /**
     * 获取用户具体信息
     */
    private void requestUserInfo() {
        new Thread() {
            public void run() {
                userData = HttpUtils.getUserInfoWithId(HttpApplication.getUserId());
                handler.sendEmptyMessage(0x001);
            };
        }.start();
    }


    /**
     * 初始化View
     */
    private void initView() {
        // 获取View
        View botton_bar = (View) findViewById(R.id.botton_bar);
        bottomHomeIcon = (LinearLayout) botton_bar.findViewById(R.id.bottom_bar_home);
        bottomCommunicateIcon = (LinearLayout) botton_bar.findViewById(R.id.bottom_bar_communicate);
        bottomTestIcon = (LinearLayout) botton_bar.findViewById(R.id.bottom_bar_test);
        bottomMyIcon = (LinearLayout) botton_bar.findViewById(R.id.bottom_bar_my);

        TextView tv = (TextView) botton_bar.findViewById(R.id.bottom_bar_my_txt);
        tv.setTextColor(Color.BLACK);

        my_collection = (LinearLayout) findViewById(R.id.my_collection);
        my_post = (LinearLayout) findViewById(R.id.my_post);
        my_review = (LinearLayout) findViewById(R.id.my_review);
        my_setting = (LinearLayout) findViewById(R.id.my_setting);
        my_basic_info = (RelativeLayout) findViewById(R.id.my_basic_info);

        my_name = (TextView) findViewById(R.id.my_name);
        my_id = (TextView) findViewById(R.id.my_id);
        my_sculpture = (ImageView) findViewById(R.id.my_sculpture);

        // 设置View的Listener
        bottomHomeIcon.setOnClickListener(this);
        bottomCommunicateIcon.setOnClickListener(this);
        bottomTestIcon.setOnClickListener(this);
        bottomMyIcon.setOnClickListener(this);

        my_collection.setOnClickListener(this);
        my_post.setOnClickListener(this);
        my_review.setOnClickListener(this);
        my_setting.setOnClickListener(this);
        my_basic_info.setOnClickListener(this);

        bottomMyIcon.setSelected(true);
    }


    /**
     * View的OnClick监听事件
     * @param view
     */
    @Override
    public void onClick(View view) {
        Intent intent;
        Bundle bundle;
        switch (view.getId()) {
            case R.id.bottom_bar_home:
                intent = new Intent(MyActivity.this, HomeActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.anim_none, R.anim.anim_none);
                break;
            case R.id.bottom_bar_communicate:
                intent = new Intent(MyActivity.this, CommunicateActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.anim_none, R.anim.anim_none);
                break;
            case R.id.bottom_bar_test:
                intent = new Intent(MyActivity.this, TestActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.anim_none, R.anim.anim_none);
                break;
            case R.id.my_collection:
                intent = new Intent(MyActivity.this, MyPostSetActivity.class);
                bundle = new Bundle();
                bundle.putString("title", "收藏");
                intent.putExtras(bundle);
                startActivity(intent);
                overridePendingTransition(R.anim.anim_none, R.anim.anim_none);
                break;
            case R.id.my_post:
                intent = new Intent(MyActivity.this, MyPostSetActivity.class);
                bundle = new Bundle();
                bundle.putString("title", "我的帖子");
                intent.putExtras(bundle);
                startActivity(intent);
                overridePendingTransition(R.anim.anim_none, R.anim.anim_none);
                break;
            case R.id.my_review:
                intent = new Intent(MyActivity.this, MyReviewSetActivity.class);
                bundle = new Bundle();
                bundle.putString("title", "我的评论");
                intent.putExtras(bundle);
                startActivity(intent);
                overridePendingTransition(R.anim.anim_none, R.anim.anim_none);
                break;
            case R.id.my_setting:
                intent = new Intent(MyActivity.this, SettingActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.anim_none, R.anim.anim_none);
                break;
            case R.id.my_basic_info:
                intent = new Intent(MyActivity.this, ProfileActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.anim_none, R.anim.anim_none);
                break;
        }
    }


    /**
     * 按下手机返回键的监听器
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK) {
            dialog();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    /**
     * 弹出对话框
     */
    protected void dialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("确定要退出吗?");
        builder.setTitle("提示");
        builder.setPositiveButton("确认",
                new android.content.DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        ApplicationManager.getInstance().exit();
                    }
                });
        builder.setNegativeButton("取消",
                new android.content.DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        builder.create().show();
    }


    @Override
    protected void onDestroy() {
        ApplicationManager.getInstance().deleteActivity(this);
        super.onDestroy();
    }
}
