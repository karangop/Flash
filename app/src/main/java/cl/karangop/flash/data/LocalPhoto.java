package cl.karangop.flash.data;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by karan_000 on 06-03-2017.
 */

public class LocalPhoto {

    private static final String LOCAL_PHOTO_GROUP_KEY="LOCAL_PHOTO_GROUP_KEY";
    private static final String LOCAL_PHOTO_KEY="LOCAL_PHOTO_KEY";

    private Context context;

    public LocalPhoto(Context context) {
        this.context = context;
    }

    public void savePath(String selectedImageUriPath) {
        SharedPreferences savePhotoData = context.getSharedPreferences(LOCAL_PHOTO_GROUP_KEY, Context.MODE_PRIVATE); //mode privado, para no compartir mis preferencias
        SharedPreferences.Editor prefEditor = savePhotoData.edit();
        prefEditor.putString(LOCAL_PHOTO_KEY, selectedImageUriPath);
//        prefEditor.commit();
        prefEditor.apply();
    }

    public String getPath() {
        SharedPreferences getPhotoPath = context.getSharedPreferences(LOCAL_PHOTO_GROUP_KEY, Context.MODE_PRIVATE);
        return getPhotoPath.getString(LOCAL_PHOTO_KEY, null);
    }
}
