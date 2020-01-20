
# react-native-baidu-vtts
This is an React-native library that can do text-to-speech

### Before
please follow this page [ReactNative集成百度语音合成](https://blog.csdn.net/wayne214/article/details/85045229)
make setting.

### notice
current is only support android, ios coming soon.

## Getting started

`$ npm install react-native-baidu-vtts --save`

### Mostly automatic installation

`$ react-native link react-native-baidu-vtts`

### Manual installation


#### iOS

coming soon

<!-- 1. In XCode, in the project navigator, right click `Libraries` ➜ `Add Files to [your project's name]`
2. Go to `node_modules` ➜ `react-native-baidu-vtts` and add `RNBaiduvoice.xcodeproj`
3. In XCode, in the project navigator, select your project. Add `libRNBaiduvoice.a` to your project's `Build Phases` ➜ `Link Binary With Libraries`
4. Run your project (`Cmd+R`)< -->

#### Android

1. Open up `android/app/src/main/java/[...]/MainActivity.java`
  - Add `import com.wayne.baiduvoice.RNBaiduvoicePackage;` to the imports at the top of the file
  - Add `new RNBaiduvoicePackage()` to the list returned by the `getPackages()` method
2. Append the following lines to `android/settings.gradle`:
  	```
  	include ':react-native-baidu-vtts'
  	project(':react-native-baidu-vtts').projectDir = new File(rootProject.projectDir, 	'../node_modules/react-native-baidu-vtts/android')
  	```
3. Insert the following lines inside the dependencies block in `android/app/build.gradle`:
  	```
    implementation project(':react-native-baidu-vtts')
  	```


## Usage
```javascript
import RNBaiduvoice from 'react-native-baidu-vtts';

// TODO: What to do with the module?
class App extends Component{

    componentDidMount() {
    	// 填写百度语音官网申请的appid, apikey, secretkey
    	const appid = ''
    	const apikey = ''
    	const secretkey = ''
        RNBaiduvoice.initBaiduTTS(appid,apikey,secretkey)
    }

    _speechText = () => {
        RNBaiduvoice.speak('百度语音')
    }

    render() {
        return (
            <View style={styles.container}>
                {/*<TwoList/>*/}
                <TouchableOpacity onPress={this._speechText}>
                    <Text style={{fontSize: 20, height: 30}}>测试语音</Text>
                </TouchableOpacity>
            </View>
        );
    }
}


```
  