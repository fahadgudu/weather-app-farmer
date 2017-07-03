package network;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;

import java.io.IOException;

/**
 * The Class AppUtil.
 */

@SuppressWarnings("resource")
public class AppUtil {

    final static String TAG = "AppUtil";

    /**
     * The Constant SECOND.
     */
    public static final long SECOND = 1000;

    /**
     * The Constant MINT.
     */
    public static final long MINT = 60 * AppUtil.SECOND;

    /**
     * The Constant HOUR.
     */
    public static final long HOUR = 60 * AppUtil.MINT;

    /**
     * The Constant DAY.
     */
    public static final long DAY = 24 * AppUtil.HOUR;

    public static final long WEEK = 7 * AppUtil.DAY;

    public static final long MONTH = 30 * AppUtil.DAY;

    public static final long YEAR = 12 * AppUtil.MONTH;

    public static String [] weatherSummary = {"سارا دن موسم خشک اور صاف رہےگا اور زیادہ تر دھوپ کے امکانات ہیں", "رات کا موسم صاف اور خشک رہنے کا امکان ہے اور آسمان بھی صاف رہنے کی اطلاع ہے", "موسم جزوی طور پر ابر اٌلود رہے گا اور بارش کے بہت امکانات ہیں", "برفیلی ہوا کے جھونکے آئیں گے اور برف باری کے کچھ امکانات بھی ہیں", "تیز برفانی بارش کے امکانات ہیں", "تیز ہوا اور موسم خوشگوار رہنے کے امکانات ہیں", "موسم خوشگوار اور کالے بادل کے امکانات ہیں", "دن کے وقت موسم جزوی ابر آلود اور بادل کے امکانات ہیں","رات موسم جزوی طور پر ابر آلود اور بادل کے امکانات ہیں", "دن کے وقت اولے پڑھنے اور موسم میں خنکی کے بہت امکانات ہیں", "رات تیز ہواؤں کے ساتھ بارش اور طوفان کی توقع ہے", "دن کے وقت تیز آندھی کے بہت امکانات ہیں", "دن کے وقت تیز آندھی کے بہت امکانات ہیں"};

    public static String getWeatherSummary (int index) {
        return weatherSummary[index];

    }

    public static void playAudio(Context context, String fileName) {
        final MediaPlayer mp = new MediaPlayer();
        if (mp.isPlaying()) {
            mp.stop();
        }
        try {
            mp.reset();
            AssetFileDescriptor afd = null;
            afd = context.getAssets().openFd(fileName);//claps

            assert afd != null;
            mp.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
            mp.prepare();
            mp.start();
        } catch (IllegalStateException | IOException e) {
            e.printStackTrace();
        }
    }

}
