package nodes;

import java.util.HashMap;

import nodes.behavior.FillAmmeterData;

/**
 *
 * Created by jiyangkang on 2016/4/8 0008.
 */
public class Ammeter extends Node {

    private volatile static Ammeter ammeter;

    private String value;

    private OnValueReceived mOnValueReceived;

    public void setmOnValueReceived(OnValueReceived onValueReceived){
        this.mOnValueReceived = onValueReceived;
    }

    private Ammeter(){
        mFillDatas = new FillAmmeterData();
        mSendCMD = null;
        setName("智能电表");
    }

    public static Ammeter getAmmeter(){
        if (ammeter == null){
            synchronized (Ammeter.class){
                if (ammeter == null)
                    ammeter = new Ammeter();
            }
        }
        return ammeter;
    }

    public void setValue(byte[] data){
        HashMap<String, String> thHash = mFillDatas.fillData(data);
        value = thHash.get("电量");
        mOnValueReceived.onValueReceived(value);
    }

    public interface OnValueReceived{
        void onValueReceived(String value);
    }
}
