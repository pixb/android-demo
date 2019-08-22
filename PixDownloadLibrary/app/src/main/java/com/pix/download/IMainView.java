package com.pix.download;

import com.pix.download.bean.DownloadBean;
import com.pix.downloadlib.bean.LoadInfo;

import java.util.List;

/**
 * Created by Administrator on 2017/2/21.
 */

public interface IMainView extends IBaseView {
    public void updateList(List<DownloadBean> list);
}
