package com.pix.anim.project.jufan;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.pix.anim.R;
import com.pix.anim.bean.ScaleFramLayout;
import com.pix.anim.bean.SurfaceAnimScene;

public class EnterRoomActivity extends Activity implements View.OnClickListener ,SurfaceAnimScene.RunStateListener{

    private static final String TAG = "EnterRoomActivity";
    private ScaleFramLayout m_sflContainer;
    private boolean isRunView;
    private Handler mHandler = new Handler();
    private UserEnterUpgradeScene scene;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_room);
        m_sflContainer = (ScaleFramLayout) findViewById(R.id.sfl_container);
        scene = new UserEnterUpgradeScene(this);
        scene.addRunStateListener(this);
        m_sflContainer.addView(scene);

    }

    public void onClick(View v) {
        if (isRunView) {
            return;
        }
        Log.d(TAG, "onClick(),isRunView:" + isRunView);
        String name = ((Button) v).getText().toString();
        if (name.equals("Level-1")) {
            if (!isRunView) {
                scene.runUser(new UserEnterUpgradeScene.EnterUser("库拉拉库拉拉库拉", 25,
                        UserEnterUpgradeScene.TYPE_ENTER_ROOM, -1));
            }
        } else if (name.equals("Level-2")) {
            if (!isRunView) {
                scene.runUser(new UserEnterUpgradeScene.EnterUser("库拉拉库拉拉库拉拉啊abdeeeeeee", 45,
                        UserEnterUpgradeScene.TYPE_ENTER_ROOM, 0));
            }
        } else if (name.equals("Level-3")) {
            if (!isRunView) {
                scene.runUser(new UserEnterUpgradeScene.EnterUser("库拉拉123", 65,
                        UserEnterUpgradeScene.TYPE_ENTER_ROOM, 1));
            }
        } else if (name.equals("Level-4")) {
            if (!isRunView) {
                scene.runUser(new UserEnterUpgradeScene.EnterUser("库拉拉123", 99,
                        UserEnterUpgradeScene.TYPE_ENTER_ROOM, 0));
            }
        } else if (name.equals("Upgrade-1")) {
            if (!isRunView) {
                scene.runUser(new UserEnterUpgradeScene.EnterUser("库拉拉123", 12,
                        UserEnterUpgradeScene.TYPE_UPGRADE, -1));
            }
        } else if (name.equals("Upgrade-2")) {
            if (!isRunView) {
                scene.runUser(new UserEnterUpgradeScene.EnterUser("库拉拉123", 99,
                        UserEnterUpgradeScene.TYPE_UPGRADE, -1));
            }
        }
    }

    @Override
    public void onAnimStart() {
        Log.d(TAG, "onAnimStart()");
        isRunView = true;
    }

    @Override
    public void onAnimFinish() {
        Log.d(TAG, "onAnimStop()");
        isRunView = false;
    }
}
