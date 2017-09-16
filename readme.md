### 一个优美炫酷的点赞效果

效果图如下：


![](http://upload-images.jianshu.io/upload_images/4614633-e1e2636646e05cf2.gif?imageMogr2/auto-orient/strip)
![](http://upload-images.jianshu.io/upload_images/4614633-cd59e0c2f37ed976.gif?imageMogr2/auto-orient/strip)




### 攻克难点：
- 心形图片的路径等走向
-  心形图片的控制范围


部分代码如下：

通过AbstractPathAnimator定义飘心动画控制器

```
   @Override
    public void start(final View child, final ViewGroup parent) {
        parent.addView(child, new ViewGroup.LayoutParams(mConfig.heartWidth, mConfig.heartHeight));
        FloatAnimation anim = new FloatAnimation(createPath(mCounter, parent, 2), randomRotation(), parent, child);
        anim.setDuration(mConfig.animDuration);
        anim.setInterpolator(new LinearInterpolator());//启动动画
        anim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationEnd(Animation animation) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        parent.removeView(child);
                    }
                });
                mCounter.decrementAndGet();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }

            @Override
            public void onAnimationStart(Animation animation) {
                mCounter.incrementAndGet();
            }
        });
        anim.setInterpolator(new LinearInterpolator());
        child.startAnimation(anim);
    }
```

```
  /**
     * 根据图片设置bitmap
     * @param color
     * @return
     */
    public Bitmap createHeart(int color) {
        if (sHeart == null) {
            sHeart = BitmapFactory.decodeResource(getResources(), mHeartResId);
        }
        if (sHeartBorder == null) {
            sHeartBorder = BitmapFactory.decodeResource(getResources(), mHeartBorderResId);
        }
        Bitmap heart = sHeart;
        Bitmap heartBorder = sHeartBorder;
        Bitmap bm = createBitmapSafely(heartBorder.getWidth(), heartBorder.getHeight());
        if (bm == null) {
            return null;
        }
        Canvas canvas = sCanvas;
        canvas.setBitmap(bm);
        Paint p = sPaint;
        canvas.drawBitmap(heartBorder, 0, 0, p);
        p.setColorFilter(new PorterDuffColorFilter(color, PorterDuff.Mode.SRC_ATOP));
        float dx = (heartBorder.getWidth() - heart.getWidth()) / 2f;
        float dy = (heartBorder.getHeight() - heart.getHeight()) / 2f;
        canvas.drawBitmap(heart, dx, dy, p);
        p.setColorFilter(null);
        canvas.setBitmap(null);
        return bm;
    }

```
如何创建一个path
```
 public Path createPath(AtomicInteger counter, View view, int factor) {
        Random r = mRandom;
        int x = r.nextInt(mConfig.xRand);
        int x2 = r.nextInt(mConfig.xRand);
        int y = view.getHeight() - mConfig.initY;
        int y2 = counter.intValue() * 15 + mConfig.animLength * factor + r.nextInt(mConfig.animLengthRand);
        factor = y2 / mConfig.bezierFactor;
        //随机xPoint
        int xPointFactor = mRandom.nextInt(mConfig.xPointFactor);
        x = xPointFactor + x;
        x2 = xPointFactor + x2;
        int y3 = y - y2;
        y2 = y - y2 / 2;
        Path p = new Path();
        p.moveTo(mConfig.initX, y);
        p.cubicTo(mConfig.initX, y - factor, x, y2 + factor, x, y2);
        p.moveTo(x, y2);
        p.cubicTo(x, y2 - factor, x2, y3 + factor, x2, y3);
        return p;
    }
```

Activity中代码：

![](http://upload-images.jianshu.io/upload_images/4614633-3dcfbfbe01ea8295.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

![](http://upload-images.jianshu.io/upload_images/4614633-1f6974c4cc4bb648.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

![](http://upload-images.jianshu.io/upload_images/4614633-50ed7cde92899ffd.gif?imageMogr2/auto-orient/strip)

### 更多文章

[ 2017上半年技术文章集合—184篇文章分类汇总](http://blog.csdn.net/androidstarjack/article/details/77923753)

[ NDK项目实战—高仿360手机助手之卸载监听](http://blog.csdn.net/androidstarjack/article/details/77984865)

#### 相信自己，没有做不到的，只有想不到的

![加入大牛圈](http://img.blog.csdn.net/20170910215455020?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQvYW5kcm9pZHN0YXJqYWNr/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/SouthEast)

 如果你觉得此文对您有所帮助，欢迎入群 QQ交流群 ：644196190
微信公众号：终端研发部

![技术+职场](https://user-gold-cdn.xitu.io/2017/8/1/d354d51a5c58fb8a5ba576f2d9ea7a8e)


