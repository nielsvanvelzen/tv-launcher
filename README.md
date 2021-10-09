# TV Launcher

TV Launcher (name to be determined) is an Android launcher meant for big screens like televisions. It can be used as a
replacement for the default Leanback or Google TV launcher on various Android TV devices like the Nvidia Shield or
Chromecast with Google TV.

The app is currently in an early development stage.

<img src="https://user-images.githubusercontent.com/2305178/127557717-ac3d46d6-acfc-400d-b4a8-2f4c0c8c0832.png" height="400" />

## Features

- Configurable toolbar

  The toolbar is shown on the top of the screen and contains various items that can be hidden and reordered by the user.
  The current items that can be shown are:

  - Time display
  - Settings button

- Tiles

  A tile can be either an app or an input like HDMI. They can be shown or hidden from the main screen and reordered.

### Missing features

Some features are not implemented because of platform limitations. The following features can not be implemented:

- "Watch Next" and other channels
  
  This feature would require the `com.android.providers.tv.permission.ACCESS_ALL_EPG_DATA` permission which is only
  available for system apps.

- Wallpapers

  Android TV does not allow apps to set a wallpaper since there is no implementation for the
  [WallpaperManager actions](https://developer.android.com/reference/android/app/WallpaperManager.html).

- Search
## Installation

### Build

prerequisites: Android SDK
- `./gradlew --build-cache --no-daemon --info assembleDebug`

### Install

Install with adb to your android tv.
- Enable adb on android tv
- `adb connect <android_tv_IP>:5555`
- `adb install app/build/outputs/apk/debug/app-debug.apk`

### Define as default launcher

Disable default launcher
- `adb shell pm disable-user --user 0 com.google.android.tvlauncher`


### Revert
Enable default launcher
- `adb shell pm enable-user --user 0 com.google.android.tvlauncher`

