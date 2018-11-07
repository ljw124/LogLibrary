## 为什么需要LogLibrary？
* 开发Android项目时，经常会遇到程序崩溃的情况，这时我们可以在logcat中查看崩溃日志，但有时错误的堆栈信息并没有显示出来，这时找起来很不方便。
* 可以把程序运行的详细信息记录到日志里。
* 项目在测试阶段，如果有崩溃，只需向测试人员要崩溃日志即可，可以查看详细信息。

## LogLibrary 使用
Step 1.先在 build.gradle(Project:XXXX) 的 repositories 添加:
```javascript
allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
  ```
  
  Step 2. 然后在 build.gradle(Module:app) 的 dependencies 添加:
  ```javascript
  dependencies {
	        implementation 'com.github.ljw124:LogLibrary:1.0.5'
	}
    ```
  
  Step3. 在Application中初始化:
    ```javascript
  public class MyApplication extends Application {

    protected static Logger log = Logger.getLogger(MyApplication.class);

    @Override
    public void onCreate() {
        super.onCreate();
        
        //初始化LogLibrary
        CrashHandler crashHandler = CrashHandler.getInstance();
        crashHandler.init(this);
        new Thread(){
            @Override
            public void run() {
                Log4jConfigure.configure(getFilesDir().getAbsolutePath());
                log.info("configure log4j ok");
            }
        }.start();
    }
}
  ```

Step4. 在需要的地方使用:
    ```javascript
public class MainActivity extends AppCompatActivity {

    protected static Logger log = Logger.getLogger(MainActivity.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //记录正常日志
        log.info("MainActivity 创建啦");
        //记录错误日志
        log.error("网络出问题啦……");
    }
}
  ```
