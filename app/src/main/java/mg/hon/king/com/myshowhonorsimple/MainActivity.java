package mg.hon.king.com.myshowhonorsimple;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import mg.hon.king.com.myshowhonorsimple.view.HeartHonorLayout;
/**
 * 类功能描述：</br>
 * 仿今日头条点赞飘动效果
 * 博客地址：http://blog.csdn.net/androidstarjack
 * 公众号：终端研发部
 * @author yuyahao
 * @version 1.0 </p> 修改时间：</br> 修改备注：</br>
 */
public class MainActivity extends Activity {

    private Random mRandom = new Random();
    private Timer mTimer =null;
    private HeartHonorLayout mHeartLayout;
    private Button btn_onclick,btn_onclick2;
    private boolean isPouse = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn_onclick = findViewById(R.id.btn_onclick);
        btn_onclick2 = findViewById(R.id.btn_onclick2);
        mHeartLayout = (HeartHonorLayout) findViewById(R.id.heart_layout);
//        这里要记住为什么用view.post
//        View.post(Runnable)方法。在post(Runnable action)方法里，View获得当前线程（即UI线程）的Handler，
//        然后将action对象post到Handler里。在Handler里，它将传递过来的action对象包装成一个Message（Message的callback为action），
//        然后将其投入UI线程的消息循环中。在Handler再次处理该Message时，有一条分支（未解释的那条）就是为它所设，
//        直接调用runnable的run方法。而此时，已经路由到UI线程里，因此，我们可以毫无顾虑的来更新UI。
        btn_onclick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //添加
                mHeartLayout.addHeart(randomColor());
            }
        });
        btn_onclick2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mHeartLayout.removeAllViews();
                if(isPouse){
                    mTimer = new Timer();
                    mTimer.scheduleAtFixedRate(new TimerTask() {
                        @Override
                        public void run() {
                            isPouse = false;
                            mHeartLayout.post(new Runnable() {
                                @Override
                                public void run() {
                                    //添加
                                    mHeartLayout.addHeart(randomColor());
                                }
                            });
                        }
                    }, 500,250);
                }else{
                    isPouse = true;
                    if(mTimer != null){
                        mTimer.cancel();
                    }
                    mTimer = null;
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mTimer != null){
            mTimer.cancel();
        }
    }

    private int randomColor() {
        return Color.rgb(mRandom.nextInt(255), mRandom.nextInt(255), mRandom.nextInt(255));
    }
}
