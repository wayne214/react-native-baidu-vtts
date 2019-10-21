
# react-native-baidu-voice

## Getting started

`$ npm install react-native-baiduvoice --save`

### Mostly automatic installation

`$ react-native link react-native-baidu-voice`

### Manual installation


#### iOS

1. In XCode, in the project navigator, right click `Libraries` ➜ `Add Files to [your project's name]`
2. Go to `node_modules` ➜ `react-native-baidu-voice` and add `RNBaiduvoice.xcodeproj`
3. In XCode, in the project navigator, select your project. Add `libRNBaiduvoice.a` to your project's `Build Phases` ➜ `Link Binary With Libraries`
4. Run your project (`Cmd+R`)<

#### Android

1. Open up `android/app/src/main/java/[...]/MainActivity.java`
  - Add `import com.wayne.baiduvoice.RNBaiduvoicePackage;` to the imports at the top of the file
  - Add `new RNBaiduvoicePackage()` to the list returned by the `getPackages()` method
2. Append the following lines to `android/settings.gradle`:
  	```
  	include ':react-native-baidu-voice'
  	project(':react-native-baidu-voice').projectDir = new File(rootProject.projectDir, 	'../node_modules/react-native-baidu-voice/android')
  	```
3. Insert the following lines inside the dependencies block in `android/app/build.gradle`:
  	```
      compile project(':react-native-baidu-voice')
  	```


## Usage
```javascript
import RNBaiduvoice from 'react-native-baiduvoice';

// TODO: What to do with the module?
RNBaiduvoice;
```
  