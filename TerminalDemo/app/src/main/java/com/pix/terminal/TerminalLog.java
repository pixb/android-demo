package com.pix.terminal;

/**
 * <p>Copyright: Copyright (c) 2016</p>
 * <p/>
 * <p>Company: 浙江齐聚科技有限公司<a href="www.guagua.cn">www.guagua.cn</a></p>
 *
 * @author TPX
 * @version 1.0.0
 * @description
 *      终端的LOG信息
 * @modify
 */
public class TerminalLog {
    public static final byte NAVIGATE_LOG = 0X01;
    public static final byte RLSP_LOG = 0X01 << 1;
    public static final byte CMSP_LOG = 0X01 << 2;
    public static final byte PLAYER_LOG = 0X01 << 3;

    public byte type ;
    public String logStr ;
    public TerminalLog(byte type,String log) {
        this.type = type;
        logStr = log;
    }
}
