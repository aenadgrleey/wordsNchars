# wordsNchars
[![](https://jitpack.io/v/aenadgrleey/wordsNchars.svg)](https://jitpack.io/#aenadgrleey/wordsNchars)

That library allows you to you implement simple "half" rich-text editor out of box. 

Currently it works only with EditTect component from XML. 

Example of using in Activity:

```kotlin
val viewModelTextEditor : ViewModelTextEditor by viewModels()
TextEditor(viewModelTextEditor, binding.editText)
```

Example of using in Fragment:

```kotlin
val viewModelTextEditor : ViewModelTextEditor by activityViewModels()
TextEditor(viewModelTextEditor, binding.editText)
```
Placing toolbar with XML tag:

```xml
<com.wordsnchars.ui_xml.TextEditorToolbar
android:id="@+id/text_editor_toolbar"
android:layout_width="match_parent"
android:layout_height="wrap_content"
android:visibility="visible"/>
```

To include it in the project add this in the project build.gradle:
```gradle
allprojects {
	repositories {
		...
		maven { url 'https://jitpack.io' }
	}
}
```

And this into module's(app's or library's) build.gradle:
```gradle
dependencies {
        implementation 'com.github.aenadgrleey:wordsNchars:Tag'
}
```


Examples of currently available functions

Text stylling:

![](https://github.com/aenadgrleey/wordsNchars/blob/main/media/style_example.gif)

Text undrelining:

![](https://github.com/aenadgrleey/wordsNchars/blob/main/media/underline_example.gif)

Text highlighting:

![](https://github.com/aenadgrleey/wordsNchars/blob/main/media/highlight_example.gif)

Text sizing:

![](https://github.com/aenadgrleey/wordsNchars/blob/main/media/size_example.gif)

Text "scription"ing (sub- and super-scription):

![](https://github.com/aenadgrleey/wordsNchars/blob/main/media/scription_example.gif)
