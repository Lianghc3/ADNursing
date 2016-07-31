package softwaredesign.adnursing.Activity;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import softwaredesign.adnursing.ApplicationManager;
import softwaredesign.adnursing.Custom.MyListView;
import softwaredesign.adnursing.Adapter.PostPreviewAdapter;
import softwaredesign.adnursing.Adapter.PostPreviewDetailAdapter;
import softwaredesign.adnursing.HttpApplication;
import softwaredesign.adnursing.R;
import softwaredesign.adnursing.Adapter.ReviewAdapter;
import softwaredesign.adnursing.Data.ReviewData;
import softwaredesign.adnursing.Data.UserData;
import softwaredesign.adnursing.Utils.HttpUtils;

public class TTTestActivity extends AppCompatActivity {

    private ImageView tttest_image;
    private TextView tttest_name;
    private UserData userData;


    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case 0x001:
                    tttest_name.setText(userData.getName());
                    loadImage();
                    break;
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        ApplicationManager.getInstance().addActivity(this);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tttest);

        tttest_image = (ImageView) findViewById(R.id.tttest_image);
        tttest_name = (TextView) findViewById(R.id.tttest_name);
        requestUserInfo();
    }


    private void requestUserInfo() {
        new Thread() {
            public void run() {
                userData = HttpUtils.getUserInfoWithId(HttpApplication.getUserId());
                handler.sendEmptyMessage(0x001);
            };
        }.start();
    }

    private void loadImage() {
        Log.e("imagepath", userData.getImageDir());
        Glide.with(this).load("http://120.76.115.235:8080/ADNursingServer/upload/222.jpg").placeholder(R.mipmap.image_default).into(tttest_image);
//        Glide.with(this).load(HttpUtils.getIp()+"/"+userData.getImageDir()).into(tttest_image);
    }

    @Override
    protected void onDestroy() {
        ApplicationManager.getInstance().deleteActivity(this);
        super.onDestroy();
    }

}
