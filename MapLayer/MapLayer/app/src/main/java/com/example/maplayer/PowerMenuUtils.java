package com.example.maplayer;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.Gravity;

import androidx.appcompat.view.ContextThemeWrapper;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;

import com.skydoves.powermenu.CircularEffect;
import com.skydoves.powermenu.MenuAnimation;
import com.skydoves.powermenu.OnDismissedListener;
import com.skydoves.powermenu.OnMenuItemClickListener;
import com.skydoves.powermenu.PowerMenu;
import com.skydoves.powermenu.PowerMenuItem;

public class PowerMenuUtils {

    public static PowerMenu getIconPowerMenu(
            Context context,
            LifecycleOwner lifecycleOwner,
            OnMenuItemClickListener<PowerMenuItem> onMenuItemClickListener) {

        Context styledContext = new ContextThemeWrapper(context, R.style.PopupCardThemeOverlay);

        return new PowerMenu.Builder(styledContext)
                .addItem(new PowerMenuItem("",R.drawable.icon0))
                .addItem(new PowerMenuItem("", R.drawable.icon1))
                .addItem(new PowerMenuItem("", R.drawable.icon2))
                .addItem(new PowerMenuItem("", R.drawable.icon3))
                .addItem(new PowerMenuItem("", R.drawable.icon4))
                .addItem(new PowerMenuItem("", R.drawable.icon5))
                .setLifecycleOwner(lifecycleOwner)
                .setOnMenuItemClickListener(onMenuItemClickListener)
                .setWidth(170)
                .setAnimation(MenuAnimation.FADE)
                .setMenuRadius(context.getResources().getDimensionPixelSize(R.dimen.menu_corner_radius))
                .setMenuShadow(context.getResources().getDimensionPixelSize(R.dimen.menu_elevation))
                .setShowBackground(false)
                .setFocusable(false)
                .setIsMaterial(true)
                .build();
    }

    public static PowerMenu getEditPowerMenu(
            Context context,
            LifecycleOwner lifecycleOwner,
            OnMenuItemClickListener<PowerMenuItem> onMenuItemClickListener) {

        Context styledContext = new ContextThemeWrapper(context, R.style.PopupCardThemeOverlay);

        return new PowerMenu.Builder(styledContext)
                .addItem(new PowerMenuItem("Colors"))
                .addItem(new PowerMenuItem("None"))
                .setLifecycleOwner(lifecycleOwner)
                .setOnMenuItemClickListener(onMenuItemClickListener)
                .setWidth(250)
                .setAnimation(MenuAnimation.FADE)
                .setMenuRadius(context.getResources().getDimensionPixelSize(R.dimen.menu_corner_radius))
                .setMenuShadow(context.getResources().getDimensionPixelSize(R.dimen.menu_elevation))
                .setShowBackground(false)
                .setFocusable(false)
                .setIsMaterial(true)
                .build();
    }

    public static PowerMenu getColorsPowerMenu(
            Context context,
            LifecycleOwner lifecycleOwner,
            OnMenuItemClickListener<PowerMenuItem> onMenuItemClickListener) {

        Context styledContext = new ContextThemeWrapper(context, R.style.PopupCardThemeOverlay);

        return new PowerMenu.Builder(styledContext)
                .addItem(new PowerMenuItem("Red",R.drawable.red))
                .addItem(new PowerMenuItem("Blue",R.drawable.blue))
                .addItem(new PowerMenuItem("Green",R.drawable.green))
                .setLifecycleOwner(lifecycleOwner)
                .setOnMenuItemClickListener(onMenuItemClickListener)
                .setWidth(350)
                .setAnimation(MenuAnimation.FADE)
                .setMenuRadius(context.getResources().getDimensionPixelSize(R.dimen.menu_corner_radius))
                .setMenuShadow(context.getResources().getDimensionPixelSize(R.dimen.menu_elevation))
                .setShowBackground(false)
                .setFocusable(false)
                .setIsMaterial(true)
                .build();
    }

    public static PowerMenu getShapesPowerMenu(
            Context context,
            LifecycleOwner lifecycleOwner,
            OnMenuItemClickListener<PowerMenuItem> onMenuItemClickListener) {

        Context styledContext = new ContextThemeWrapper(context, R.style.PopupCardThemeOverlay);

        return new PowerMenu.Builder(styledContext)
                .addItem(new PowerMenuItem("Dot",R.drawable.dot))
                .addItem(new PowerMenuItem("Line",R.drawable.line))
                .addItem(new PowerMenuItem("Cross",R.drawable.cross))
                .setLifecycleOwner(lifecycleOwner)
                .setOnMenuItemClickListener(onMenuItemClickListener)
                .setWidth(350)
                .setAnimation(MenuAnimation.FADE)
                .setMenuRadius(context.getResources().getDimensionPixelSize(R.dimen.menu_corner_radius))
                .setMenuShadow(context.getResources().getDimensionPixelSize(R.dimen.menu_elevation))
                .setShowBackground(false)
                .setFocusable(false)
                .setIsMaterial(true)
                .build();
    }

    public static PowerMenu getNumbersDotsPowerMenu(
            Context context,
            LifecycleOwner lifecycleOwner,
            OnMenuItemClickListener<PowerMenuItem> onMenuItemClickListener) {

        Context styledContext = new ContextThemeWrapper(context, R.style.PopupCardThemeOverlay);

        return new PowerMenu.Builder(styledContext)
                .addItem(new PowerMenuItem("",R.drawable.dot))
                .addItem(new PowerMenuItem("",R.drawable.dot2))
                .addItem(new PowerMenuItem("",R.drawable.dot3))
                .addItem(new PowerMenuItem("",R.drawable.dot4))
                .setLifecycleOwner(lifecycleOwner)
                .setOnMenuItemClickListener(onMenuItemClickListener)
                .setWidth(170)
                .setAnimation(MenuAnimation.FADE)
                .setMenuRadius(context.getResources().getDimensionPixelSize(R.dimen.menu_corner_radius))
                .setMenuShadow(context.getResources().getDimensionPixelSize(R.dimen.menu_elevation))
                .setShowBackground(false)
                .setFocusable(false)
                .setIsMaterial(true)
                .build();
    }

    public static PowerMenu getNumberLinesPowerMenu(
            Context context,
            LifecycleOwner lifecycleOwner,
            OnMenuItemClickListener<PowerMenuItem> onMenuItemClickListener) {

        Context styledContext = new ContextThemeWrapper(context, R.style.PopupCardThemeOverlay);

        return new PowerMenu.Builder(styledContext)
                .addItem(new PowerMenuItem("",R.drawable.line1))
                .addItem(new PowerMenuItem("",R.drawable.line2))
                .addItem(new PowerMenuItem("",R.drawable.line3))
                .setLifecycleOwner(lifecycleOwner)
                .setOnMenuItemClickListener(onMenuItemClickListener)
                .setWidth(170)
                .setAnimation(MenuAnimation.FADE)
                .setMenuRadius(context.getResources().getDimensionPixelSize(R.dimen.menu_corner_radius))
                .setMenuShadow(context.getResources().getDimensionPixelSize(R.dimen.menu_elevation))
                .setShowBackground(false)
                .setFocusable(false)
                .setIsMaterial(true)
                .build();
    }

    public static PowerMenu getNumberCrossPowerMenu(
            Context context,
            LifecycleOwner lifecycleOwner,
            OnMenuItemClickListener<PowerMenuItem> onMenuItemClickListener) {

        Context styledContext = new ContextThemeWrapper(context, R.style.PopupCardThemeOverlay);

        return new PowerMenu.Builder(styledContext)
                .addItem(new PowerMenuItem("",R.drawable.cross1))
                .addItem(new PowerMenuItem("",R.drawable.cross2))
                .addItem(new PowerMenuItem("",R.drawable.cross3))
                .addItem(new PowerMenuItem("",R.drawable.cross4))
                .addItem(new PowerMenuItem("",R.drawable.cross5))
                .setLifecycleOwner(lifecycleOwner)
                .setOnMenuItemClickListener(onMenuItemClickListener)
                .setWidth(170)
                .setAnimation(MenuAnimation.FADE)
                .setMenuRadius(context.getResources().getDimensionPixelSize(R.dimen.menu_corner_radius))
                .setMenuShadow(context.getResources().getDimensionPixelSize(R.dimen.menu_elevation))
                .setShowBackground(false)
                .setFocusable(false)
                .setIsMaterial(true)
                .build();
    }






}
