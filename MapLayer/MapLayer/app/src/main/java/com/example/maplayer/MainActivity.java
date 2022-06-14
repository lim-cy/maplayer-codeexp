package com.example.maplayer;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.otaliastudios.zoom.ZoomLayout;
import com.skydoves.powermenu.OnMenuItemClickListener;
import com.skydoves.powermenu.PowerMenu;
import com.skydoves.powermenu.PowerMenuItem;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;

import io.github.muddz.quickshot.QuickShot;

public class MainActivity extends AppCompatActivity implements QuickShot.QuickShotListener {

    private PowerMenu iconMenu;
    private PowerMenu editMenu;
    private PowerMenu colorsMenu;
    private PowerMenu shapesMenu;
    private PowerMenu numberDotsMenu;
    private PowerMenu numberLinesMenu;
    private PowerMenu numberCrossMenu;

    private View currentSelected;
    private LinearLayout childLayout;

    private ImageView objectImageView;
    private static final int PICK_IMAGE_REQUEST=100;
    private Uri imageFilePath;
    private Bitmap imageToStore;
    private FrameLayout linearLayout;
    private ImageView weChat;
    private Integer moving;
    private ImageButton toggle;
    private ZoomLayout maxP;

    private ImageButton icons;
    private Integer icon;
    private String color;
    private String shape;
    private String text;

    private Spinner spinner;

    private ImageView imgView;

    private String[] layers = {"Layer1", "Layer2", "Layer3", "Layer4"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        linearLayout= findViewById(R.id.parent);
        objectImageView = findViewById(R.id.imageGet);
        toggle = findViewById(R.id.movingButton);
        maxP = findViewById(R.id.maxParent);
        icons = findViewById(R.id.imageButton);
        spinner = findViewById(R.id.spinner);
        Resources res = getResources();
        moving = 1;
        iconMenu = PowerMenuUtils.getIconPowerMenu(this, this, onIconMenuItemClickListener);
        editMenu = PowerMenuUtils.getEditPowerMenu(this, this, onEditMenuItemClickListener);
        colorsMenu = PowerMenuUtils.getColorsPowerMenu(this, this, onColorsMenuItemClickListener);
        shapesMenu = PowerMenuUtils.getShapesPowerMenu(this, this, onShapesMenuItemClickListener);
        numberDotsMenu = PowerMenuUtils.getNumbersDotsPowerMenu(this, this, onNumberDotsMenuItemClickListener);
        numberLinesMenu = PowerMenuUtils.getNumberLinesPowerMenu(this, this, onNumberLinesMenuItemClickListener);
        numberCrossMenu = PowerMenuUtils.getNumberCrossPowerMenu(this, this, onNumberCrossMenuItemClickListener);

        //Move on/off
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                text = spinner.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });
    }

    /*

    public void onHamburger(View view) {
        if (hamburgerMenu.isShowing()) {
            hamburgerMenu.dismiss();
            return;
        }
        hamburgerMenu.showAsDropDown(view);
    }
     */

    public void onHamburger(View view) {
        if (iconMenu.isShowing()) {
            iconMenu.dismiss();
            return;
        }
        iconMenu.showAsDropDown(view, 600,-300);
    }

    //IMAGE ADDING
    public void chooseImage(View objectView){
        Intent objectIntent = new Intent();
        objectIntent.setType("image/*");
        objectIntent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(objectIntent,PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==PICK_IMAGE_REQUEST&&resultCode==RESULT_OK && data.getData()!=null){
            imageFilePath = data.getData();
            try {
                imageToStore = MediaStore.Images.Media.getBitmap(getContentResolver(), imageFilePath);
                objectImageView.setImageBitmap(imageToStore);
            } catch (Exception e) {
                Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    //Draggable Icon
    private View.OnTouchListener imgListener = new View.OnTouchListener() {
        private float x, y; //The x, y position of the image.
        private float mx, my; // The distance from original position to finger dragging.

        // For the draggable options, we can use ACTION_DOWN and ACTION_MOVE
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            if(moving == 1) {
                currentSelected = v;

                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN: //ACTION_DOWN.
                        x = v.getX() - event.getRawX();
                        y = v.getY() - event.getRawY();
                        break;
                    case MotionEvent.ACTION_MOVE: //ACTION_MOVE.
                        v.animate().x(event.getRawX() + x).y(event.getRawY() + y).setDuration(0).start();
                        break;
                }
            }
                return true;
        }
    };

    public void onMoving(View view) {
        if (moving == 1) {
            toggle.setImageResource(R.drawable.lock);
            moving = 0;
        } else if (moving == 0){
            toggle.setImageResource(R.drawable.unlock);
            moving = 1;
        }
    }
    public void increaseZoom(View view){
        maxP.zoomBy((float) 1.2, true);
    }
    public void decreaseZoom(View view){
        maxP.zoomBy((float) 0.8, true);
    }

    public void imagesavetomyphonegallery(View view) {
        QuickShot.of(maxP).setResultListener(this).save();
    }

    //Icon + Text
    private final OnMenuItemClickListener<PowerMenuItem> onIconMenuItemClickListener =
            new OnMenuItemClickListener<PowerMenuItem>() {
                @Override
                public void onItemClick(int position, PowerMenuItem item) {
                    if (position == 0) {
                        if (iconMenu.isShowing()) {
                            iconMenu.dismiss();
                        }
                        editMenu.showAsDropDown(icons, 600,-300);
                        icon = 0;
                    } else if (position == 1) {
                        if (iconMenu.isShowing()) {
                            iconMenu.dismiss();
                        }
                        editMenu.showAsDropDown(icons, 600,-300);
                        icon = 1;
                    } else if (position == 2) {
                        if (iconMenu.isShowing()) {
                            iconMenu.dismiss();
                        }
                        editMenu.showAsDropDown(icons, 600,-300);
                        icon = 2;
                    } else if (position == 3) {
                        if (iconMenu.isShowing()) {
                            iconMenu.dismiss();
                        }
                        editMenu.showAsDropDown(icons, 600,-300);
                        icon = 3;
                    } else if (position == 4) {
                        if (iconMenu.isShowing()) {
                            iconMenu.dismiss();
                        }
                        editMenu.showAsDropDown(icons, 600,-300);
                        icon = 4;
                    } else if (position == 5) {
                        if (iconMenu.isShowing()) {
                            iconMenu.dismiss();
                        }
                        editMenu.showAsDropDown(icons, 600,-300);
                        icon = 5;
                    }
                    //Toast.makeText(getBaseContext(), item.getTitle(), Toast.LENGTH_SHORT).show();
                }
            };

    public void onDelete(View v){
        linearLayout.removeView(currentSelected);
    }



    private final OnMenuItemClickListener<PowerMenuItem> onEditMenuItemClickListener =
            new OnMenuItemClickListener<PowerMenuItem>() {
                @Override
                public void onItemClick(int position, PowerMenuItem item) {
                    if (position == 0) {
                        if (editMenu.isShowing()) {
                            editMenu.dismiss();
                        }
                        colorsMenu.showAsDropDown(icons, 600,-300);

                    } else if (position == 1) {
                        switch(icon) {
                            case 0:
                                weChat = new ImageView(getApplicationContext());
                                weChat.setImageResource(R.drawable.icon0);
                                linearLayout.addView(weChat);
                                weChat.getLayoutParams().width = 100;
                                weChat.setOnTouchListener(imgListener);
                                weChat.setTag(text);
                                break;
                            case 1:
                                weChat = new ImageView(getApplicationContext());
                                weChat.setImageResource(R.drawable.icon1);
                                linearLayout.addView(weChat);
                                weChat.getLayoutParams().width = 100;
                                weChat.setOnTouchListener(imgListener);
                                weChat.setTag(text);
                                break;
                            case 2:
                                weChat = new ImageView(getApplicationContext());
                                weChat.setImageResource(R.drawable.icon2);
                                linearLayout.addView(weChat);
                                weChat.getLayoutParams().width = 100;
                                weChat.setOnTouchListener(imgListener);
                                weChat.setTag(text);
                                break;
                            case 3:
                                weChat = new ImageView(getApplicationContext());
                                weChat.setImageResource(R.drawable.icon3);
                                linearLayout.addView(weChat);
                                weChat.getLayoutParams().width = 100;
                                weChat.setOnTouchListener(imgListener);
                                weChat.setTag(text);
                                break;
                            case 4:
                                weChat = new ImageView(getApplicationContext());
                                weChat.setImageResource(R.drawable.icon4);
                                linearLayout.addView(weChat);
                                weChat.getLayoutParams().width = 100;
                                weChat.setOnTouchListener(imgListener);
                                weChat.setTag(text);
                                break;
                            case 5:
                                weChat = new ImageView(getApplicationContext());
                                weChat.setImageResource(R.drawable.icon5);
                                linearLayout.addView(weChat);
                                weChat.getLayoutParams().width = 100;
                                weChat.setOnTouchListener(imgListener);
                                weChat.setTag(text);
                                break;
                            default:
                                // code block
                        }

                    }
                    //Toast.makeText(getBaseContext(), item.getTitle(), Toast.LENGTH_SHORT).show();
                }
            };

    private final OnMenuItemClickListener<PowerMenuItem> onColorsMenuItemClickListener =
            new OnMenuItemClickListener<PowerMenuItem>() {
                @Override
                public void onItemClick(int position, PowerMenuItem item) {
                    if (position == 0) {
                        color = "red";
                        if (colorsMenu.isShowing()) {
                            colorsMenu.dismiss();
                        }
                        shapesMenu.showAsDropDown(icons, 600,-300);

                    } else if (position == 1) {
                        color = "blue";
                        if (colorsMenu.isShowing()) {
                            colorsMenu.dismiss();
                        }
                        shapesMenu.showAsDropDown(icons, 600,-300);

                    } else if (position == 2) {
                        color = "green";
                        if (colorsMenu.isShowing()) {
                            colorsMenu.dismiss();
                        }
                        shapesMenu.showAsDropDown(icons, 600,-300);

                    }
                    //Toast.makeText(getBaseContext(), item.getTitle(), Toast.LENGTH_SHORT).show();
                }
            };

    private final OnMenuItemClickListener<PowerMenuItem> onShapesMenuItemClickListener =
            new OnMenuItemClickListener<PowerMenuItem>() {
                @Override
                public void onItemClick(int position, PowerMenuItem item) {
                    if (position == 0) {
                        if (shapesMenu.isShowing()) {
                            shapesMenu.dismiss();
                        }
                        numberDotsMenu.showAsDropDown(icons, 600,-300);

                    } else if (position == 1) {
                        if (shapesMenu.isShowing()) {
                            shapesMenu.dismiss();
                        }
                        numberLinesMenu.showAsDropDown(icons, 600,-300);

                    } else if (position == 2) {
                        if (shapesMenu.isShowing()) {
                            shapesMenu.dismiss();
                        }
                        numberCrossMenu.showAsDropDown(icons, 600,-300);

                    }
                    //Toast.makeText(getBaseContext(), item.getTitle(), Toast.LENGTH_SHORT).show();
                }
            };

    private final OnMenuItemClickListener<PowerMenuItem> onNumberDotsMenuItemClickListener =
            new OnMenuItemClickListener<PowerMenuItem>() {
                @Override
                public void onItemClick(int position, PowerMenuItem item) {
                    if (position == 0) {
                        switch(color) {
                            case "red":
                                weChat = new ImageView(getApplicationContext());
                                weChat.setImageResource(R.drawable.rdot1);
                                linearLayout.addView(weChat);
                                weChat.getLayoutParams().width = 100;
                                weChat.setOnTouchListener(imgListener);
                                weChat.setTag(text);
                                break;
                            case "blue":
                                weChat = new ImageView(getApplicationContext());
                                weChat.setImageResource(R.drawable.bdot1);
                                linearLayout.addView(weChat);
                                weChat.getLayoutParams().width = 100;
                                weChat.setOnTouchListener(imgListener);
                                weChat.setTag(text);
                                break;
                            case "green":
                                weChat = new ImageView(getApplicationContext());
                                weChat.setImageResource(R.drawable.gdot1);
                                linearLayout.addView(weChat);
                                weChat.getLayoutParams().width = 100;
                                weChat.setOnTouchListener(imgListener);
                                weChat.setTag(text);
                                break;
                        }

                    } else if (position == 1) {
                        switch(color) {
                            case "red":
                                weChat = new ImageView(getApplicationContext());
                                weChat.setImageResource(R.drawable.rdot2);
                                linearLayout.addView(weChat);
                                weChat.getLayoutParams().width = 100;
                                weChat.setOnTouchListener(imgListener);
                                weChat.setTag(text);
                                break;
                            case "blue":
                                weChat = new ImageView(getApplicationContext());
                                weChat.setImageResource(R.drawable.bdot2);
                                linearLayout.addView(weChat);
                                weChat.getLayoutParams().width = 100;
                                weChat.setOnTouchListener(imgListener);
                                weChat.setTag(text);
                                break;
                            case "green":
                                weChat = new ImageView(getApplicationContext());
                                weChat.setImageResource(R.drawable.gdot2);
                                linearLayout.addView(weChat);
                                weChat.getLayoutParams().width = 100;
                                weChat.setOnTouchListener(imgListener);
                                weChat.setTag(text);
                                break;
                        }

                    } else if (position == 2) {
                        switch(color) {
                            case "red":
                                weChat = new ImageView(getApplicationContext());
                                weChat.setImageResource(R.drawable.rdot3);
                                linearLayout.addView(weChat);
                                weChat.getLayoutParams().width = 100;
                                weChat.setOnTouchListener(imgListener);
                                weChat.setTag(text);
                                break;
                            case "blue":
                                weChat = new ImageView(getApplicationContext());
                                weChat.setImageResource(R.drawable.bdot3);
                                linearLayout.addView(weChat);
                                weChat.getLayoutParams().width = 100;
                                weChat.setOnTouchListener(imgListener);
                                weChat.setTag(text);
                                break;
                            case "green":
                                weChat = new ImageView(getApplicationContext());
                                weChat.setImageResource(R.drawable.gdot3);
                                linearLayout.addView(weChat);
                                weChat.getLayoutParams().width = 100;
                                weChat.setOnTouchListener(imgListener);
                                weChat.setTag(text);
                                break;
                        }

                    }else if (position == 3) {
                        switch(color) {
                            case "red":
                                weChat = new ImageView(getApplicationContext());
                                weChat.setImageResource(R.drawable.rdot4);
                                linearLayout.addView(weChat);
                                weChat.getLayoutParams().width = 100;
                                weChat.setOnTouchListener(imgListener);
                                weChat.setTag(text);
                                break;
                            case "blue":
                                weChat = new ImageView(getApplicationContext());
                                weChat.setImageResource(R.drawable.bdot4);
                                linearLayout.addView(weChat);
                                weChat.getLayoutParams().width = 100;
                                weChat.setOnTouchListener(imgListener);
                                weChat.setTag(text);
                                break;
                            case "green":
                                weChat = new ImageView(getApplicationContext());
                                weChat.setImageResource(R.drawable.gdot4);
                                linearLayout.addView(weChat);
                                weChat.getLayoutParams().width = 100;
                                weChat.setOnTouchListener(imgListener);
                                weChat.setTag(text);
                                break;
                        }

                    }
                    //Toast.makeText(getBaseContext(), item.getTitle(), Toast.LENGTH_SHORT).show();
                }
            };

    private final OnMenuItemClickListener<PowerMenuItem> onNumberLinesMenuItemClickListener =
            new OnMenuItemClickListener<PowerMenuItem>() {
                @Override
                public void onItemClick(int position, PowerMenuItem item) {
                    if (position == 0) {
                        switch (color) {
                            case "red":
                                weChat = new ImageView(getApplicationContext());
                                weChat.setImageResource(R.drawable.lr1);
                                linearLayout.addView(weChat);
                                weChat.getLayoutParams().width = 100;
                                weChat.setOnTouchListener(imgListener);
                                weChat.setTag(text);
                                break;
                            case "blue":
                                weChat = new ImageView(getApplicationContext());
                                weChat.setImageResource(R.drawable.line1b);
                                linearLayout.addView(weChat);
                                weChat.getLayoutParams().width = 100;
                                weChat.setOnTouchListener(imgListener);
                                weChat.setTag(text);
                                break;
                            case "green":
                                weChat = new ImageView(getApplicationContext());
                                weChat.setImageResource(R.drawable.lg1);
                                linearLayout.addView(weChat);
                                weChat.getLayoutParams().width = 100;
                                weChat.setOnTouchListener(imgListener);
                                weChat.setTag(text);
                                break;
                        }
                    } else if (position == 1) {
                        switch (color) {
                            case "red":
                                weChat = new ImageView(getApplicationContext());
                                weChat.setImageResource(R.drawable.rl2);
                                linearLayout.addView(weChat);
                                weChat.getLayoutParams().width = 100;
                                weChat.setOnTouchListener(imgListener);
                                weChat.setTag(text);
                                break;
                            case "blue":
                                weChat = new ImageView(getApplicationContext());
                                weChat.setImageResource(R.drawable.bl2);
                                linearLayout.addView(weChat);
                                weChat.getLayoutParams().width = 100;
                                weChat.setOnTouchListener(imgListener);
                                weChat.setTag(text);
                                break;
                            case "green":
                                weChat = new ImageView(getApplicationContext());
                                weChat.setImageResource(R.drawable.gl2);
                                linearLayout.addView(weChat);
                                weChat.getLayoutParams().width = 100;
                                weChat.setOnTouchListener(imgListener);
                                weChat.setTag(text);
                                break;
                        }
                    }else if (position == 2) {
                        switch (color) {
                            case "red":
                                weChat = new ImageView(getApplicationContext());
                                weChat.setImageResource(R.drawable.rl3);
                                linearLayout.addView(weChat);
                                weChat.getLayoutParams().width = 100;
                                weChat.setOnTouchListener(imgListener);
                                weChat.setTag(text);
                                break;
                            case "blue":
                                weChat = new ImageView(getApplicationContext());
                                weChat.setImageResource(R.drawable.bl3);
                                linearLayout.addView(weChat);
                                weChat.getLayoutParams().width = 100;
                                weChat.setOnTouchListener(imgListener);
                                weChat.setTag(text);
                                break;
                            case "green":
                                weChat = new ImageView(getApplicationContext());
                                weChat.setImageResource(R.drawable.gl3);
                                linearLayout.addView(weChat);
                                weChat.getLayoutParams().width = 100;
                                weChat.setOnTouchListener(imgListener);
                                weChat.setTag(text);
                                break;
                        }
                    }
                }
            };

    private final OnMenuItemClickListener<PowerMenuItem> onNumberCrossMenuItemClickListener =
            new OnMenuItemClickListener<PowerMenuItem>() {
                @Override
                public void onItemClick(int position, PowerMenuItem item) {
                    if (position == 0) {
                        switch (color) {
                            case "red":
                                weChat = new ImageView(getApplicationContext());
                                weChat.setImageResource(R.drawable.rc1);
                                linearLayout.addView(weChat);
                                weChat.getLayoutParams().width = 100;
                                weChat.setOnTouchListener(imgListener);
                                weChat.setTag(text);
                                break;
                            case "blue":
                                weChat = new ImageView(getApplicationContext());
                                weChat.setImageResource(R.drawable.bc1);
                                linearLayout.addView(weChat);
                                weChat.getLayoutParams().width = 100;
                                weChat.setOnTouchListener(imgListener);
                                weChat.setTag(text);
                                break;
                            case "green":
                                weChat = new ImageView(getApplicationContext());
                                weChat.setImageResource(R.drawable.gc1);
                                linearLayout.addView(weChat);
                                weChat.getLayoutParams().width = 100;
                                weChat.setOnTouchListener(imgListener);
                                weChat.setTag(text);
                                break;
                        }
                    } else if (position == 1) {
                        switch (color) {
                            case "red":
                                weChat = new ImageView(getApplicationContext());
                                weChat.setImageResource(R.drawable.rc2);
                                linearLayout.addView(weChat);
                                weChat.getLayoutParams().width = 100;
                                weChat.setOnTouchListener(imgListener);
                                weChat.setTag(text);
                                break;
                            case "blue":
                                weChat = new ImageView(getApplicationContext());
                                weChat.setImageResource(R.drawable.bc2);
                                linearLayout.addView(weChat);
                                weChat.getLayoutParams().width = 100;
                                weChat.setOnTouchListener(imgListener);
                                weChat.setTag(text);
                                break;
                            case "green":
                                weChat = new ImageView(getApplicationContext());
                                weChat.setImageResource(R.drawable.gc2);
                                linearLayout.addView(weChat);
                                weChat.getLayoutParams().width = 100;
                                weChat.setOnTouchListener(imgListener);
                                weChat.setTag(text);
                                break;
                        }
                    }else if (position == 2) {
                        switch (color) {
                            case "red":
                                weChat = new ImageView(getApplicationContext());
                                weChat.setImageResource(R.drawable.rc3);
                                linearLayout.addView(weChat);
                                weChat.getLayoutParams().width = 100;
                                weChat.setOnTouchListener(imgListener);
                                weChat.setTag(text);
                                break;
                            case "blue":
                                weChat = new ImageView(getApplicationContext());
                                weChat.setImageResource(R.drawable.bc3);
                                linearLayout.addView(weChat);
                                weChat.getLayoutParams().width = 100;
                                weChat.setOnTouchListener(imgListener);
                                weChat.setTag(text);
                                break;
                            case "green":
                                weChat = new ImageView(getApplicationContext());
                                weChat.setImageResource(R.drawable.gc3);
                                linearLayout.addView(weChat);
                                weChat.getLayoutParams().width = 100;
                                weChat.setOnTouchListener(imgListener);
                                weChat.setTag(text);
                                break;
                        }
                    }else if (position == 3) {
                        switch (color) {
                            case "red":
                                weChat = new ImageView(getApplicationContext());
                                weChat.setImageResource(R.drawable.rc4);
                                linearLayout.addView(weChat);
                                weChat.getLayoutParams().width = 100;
                                weChat.setOnTouchListener(imgListener);
                                weChat.setTag(text);
                                break;
                            case "blue":
                                weChat = new ImageView(getApplicationContext());
                                weChat.setImageResource(R.drawable.bc4);
                                linearLayout.addView(weChat);
                                weChat.getLayoutParams().width = 100;
                                weChat.setOnTouchListener(imgListener);
                                weChat.setTag(text);
                                break;
                            case "green":
                                weChat = new ImageView(getApplicationContext());
                                weChat.setImageResource(R.drawable.gc4);
                                linearLayout.addView(weChat);
                                weChat.getLayoutParams().width = 100;
                                weChat.setOnTouchListener(imgListener);
                                weChat.setTag(text);
                                break;
                        }
                    }else if (position == 4) {
                        switch (color) {
                            case "red":
                                weChat = new ImageView(getApplicationContext());
                                weChat.setImageResource(R.drawable.rc5);
                                linearLayout.addView(weChat);
                                weChat.getLayoutParams().width = 100;
                                weChat.setOnTouchListener(imgListener);
                                weChat.setTag(text);
                                break;
                            case "blue":
                                weChat = new ImageView(getApplicationContext());
                                weChat.setImageResource(R.drawable.bc5);
                                linearLayout.addView(weChat);
                                weChat.getLayoutParams().width = 100;
                                weChat.setOnTouchListener(imgListener);
                                weChat.setTag(text);
                                break;
                            case "green":
                                weChat = new ImageView(getApplicationContext());
                                weChat.setImageResource(R.drawable.gc5);
                                linearLayout.addView(weChat);
                                weChat.getLayoutParams().width = 100;
                                weChat.setOnTouchListener(imgListener);
                                weChat.setTag(text);
                                break;
                        }
                    }
                }
            };

    @Override
    public void onQuickShotSuccess(String path) {
        Toast.makeText(this, "Image saved at: " + path, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onQuickShotFailed(String path, String errorMsg) {
        Toast.makeText(this, errorMsg, Toast.LENGTH_LONG).show();
    }

    private static ArrayList<View> getViewsByTag(ViewGroup root, String tag){
        ArrayList<View> views = new ArrayList<View>();
        final int childCount = root.getChildCount();
        for (int i = 0; i < childCount; i++) {
            final View child = root.getChildAt(i);
            if (child instanceof ViewGroup) {
                views.addAll(getViewsByTag((ViewGroup) child, tag));
            }

            final Object tagObj = child.getTag();
            if (tagObj != null && tagObj.equals(tag)) {
                views.add(child);
            }

        }
        return views;
    }


    public void onCheckboxClicked(View view) {
        ImageView imgView;
        ArrayList list;
        // Is the view now checked?
        boolean checked = ((CheckBox) view).isChecked();
        // Check which checkbox was clicked
        switch (view.getId()) {
            case R.id.Layer1:
                list = getViewsByTag(maxP, "Layer 1");
                if (checked) {
                    for (int counter = 0; counter < list.size(); counter++) {
                        imgView = (ImageView) list.get(counter);
                        imgView.setVisibility(View.VISIBLE);
                    }


                } else {
                    for (int counter = 0; counter < list.size(); counter++) {
                        imgView = (ImageView) list.get(counter);
                        imgView.setVisibility(View.INVISIBLE);
                    }
                    //imgView.setVisibility(View.INVISIBLE);
                }
                break;
            case R.id.Layer2:
                list = getViewsByTag(maxP, "Layer 2");
                if (checked) {
                    for (int counter = 0; counter < list.size(); counter++) {
                        imgView = (ImageView) list.get(counter);
                        imgView.setVisibility(View.VISIBLE);
                    }


                } else {
                    for (int counter = 0; counter < list.size(); counter++) {
                        imgView = (ImageView) list.get(counter);
                        imgView.setVisibility(View.INVISIBLE);
                    }
                }
                break;
            case R.id.Layer3:
                list = getViewsByTag(maxP, "Layer 3");
                if (checked) {
                    for (int counter = 0; counter < list.size(); counter++) {
                        imgView = (ImageView) list.get(counter);
                        imgView.setVisibility(View.VISIBLE);
                    }


                } else {
                    for (int counter = 0; counter < list.size(); counter++) {
                        imgView = (ImageView) list.get(counter);
                        imgView.setVisibility(View.INVISIBLE);
                    }
                }
                break;
            case R.id.Layer4:
                list = getViewsByTag(maxP, "Layer 4");
                if (checked) {
                    for (int counter = 0; counter < list.size(); counter++) {
                        imgView = (ImageView) list.get(counter);
                        imgView.setVisibility(View.VISIBLE);
                    }


                } else {
                    for (int counter = 0; counter < list.size(); counter++) {
                        imgView = (ImageView) list.get(counter);
                        imgView.setVisibility(View.INVISIBLE);
                    }
                }
                break;
        }
    }
};