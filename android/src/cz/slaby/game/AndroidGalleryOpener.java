package cz.slaby.game;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;

import com.badlogic.gdx.files.FileHandle;


public class AndroidGalleryOpener extends AndroidLauncher implements cz.slaby.game.OpenGallery {
    Activity activity;
    public static final int SELECT_IMAGE_CODE = 1;
    public static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 831;

    private String currentImagePath;

    public AndroidGalleryOpener(Activity activity) {
        this.activity = activity;
    }

    @Override
    public void getGalleryImagePath() {

        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        if (activity.checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            activity.requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
        } else {
            activity.startActivityForResult(Intent.createChooser(intent, "Select Users Image"), SELECT_IMAGE_CODE);
        }
    }

    public void setImageResult(String path) {
        currentImagePath = path;
    }

    @Override
    public String getSelectedFilePath() {
        return currentImagePath;
    }
}
