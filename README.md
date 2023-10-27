# Online Grocer Demo
## Create online store in minutes

Add menu.json and media images to /app/src/main/assets directory

Format of menu.json:

```
[
  {
    "s": store name,
    "h": store logo,
    "n": menu item name,
    "p": menu item price,
    "i": menu item icon (shown in cart)
    "m": menu item image (shown in category menu),
    "c": menu item category,
    "g": menu item category image (shown in main menu),
  },
  ..{}

]
```

Add files  
./app/src/release/res/values/google_maps_api.xml  
./app/src/debug/res/values/google_maps_api.xml

```
<resources>
    <string name="google_maps_key" templateMergeStrategy="preserve" translatable="false">your_google_maps_api_key</string>
</resources>
```

Build the customized APK.

&copy; S. Sheinin
sergei.sheinin@gmail.com
