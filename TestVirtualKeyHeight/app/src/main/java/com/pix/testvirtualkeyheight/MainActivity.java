package com.pix.testvirtualkeyheight;

import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.WindowManager;
import android.widget.TextView;

import java.lang.reflect.InvocationTargetException;

/**
 * Test Phone's virtual key height
 */
public class MainActivity extends Activity {
    private TextView m_tvContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        m_tvContent = (TextView) findViewById(R.id.tv_content);
        Point point = getNavigationBarSize(this);
        m_tvContent.setText("Point,x:" + point.x + ",y:" + point.y);

    }

    public static Point getNavigationBarSize(Context context) {

        Point appUsableSize = getAppUsableScreenSize(context);

        Point realScreenSize = getRealScreenSize(context);

        // navigation bar on the right

        if (appUsableSize.x < realScreenSize.x) {

            return new Point(realScreenSize.x - appUsableSize.x, appUsableSize.y);
        }

        // navigation bar at the bottom

        if (appUsableSize.y < realScreenSize.y) {

            return new Point(appUsableSize.x, realScreenSize.y - appUsableSize.y);

        }
        return new Point();
    }

    public static Point getAppUsableScreenSize(Context context) {

        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);

        Display display = windowManager.getDefaultDisplay();

        Point size = new Point();

        display.getSize(size);

        return size;

    }

    public static Point getRealScreenSize(Context context) {

        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);

        Display display = windowManager.getDefaultDisplay();

        Point size = new Point();

        if (Build.VERSION.SDK_INT >= 17) {

            display.getRealSize(size);

        } else if (Build.VERSION.SDK_INT >= 14) {

            try {

                size.x = (Integer) Display.class.getMethod("getRawWidth").invoke(display);

                size.y = (Integer) Display.class.getMethod("getRawHeight").invoke(display);

            } catch (IllegalAccessException e) {
            } catch (InvocationTargetException e) {
            } catch (NoSuchMethodException e) {
            }

        }

        return size;
    }
}
