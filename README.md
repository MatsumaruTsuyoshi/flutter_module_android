# flutter_module_android

# 目的
AndroidアプリからFlutterモジュールを呼び出す

# やり方
2つ方法がある。
1. FlutterSDKをインストールせず、FlutterモジュールをAARに変換してを使用する
1. FlutterSDKを使ってFlutterモジュールをそのまま使う

今回は２つめの”FlutterSDKを使ってFlutterモジュールをそのまま使う”をやってみた。

## Flutterモジュールを作成

`$ flutter create -t module --org com.example 任意の名前`


## Andoridアプリの設定
settings.gradleに以下追記

    include ':app'                                    // assumed existing content
    setBinding(new Binding([gradle: this]))                                // new
    evaluate(new File(                                                     // new
      settingsDir.parentFile,                                              // new
      '任意の名前/.android/include_flutter.groovy'                         // new
    ))                                                                     // new

FlutterモジュールはAndroidアプリのプロジェクトファイルと同じ階層に置いておく。

appレベルのbuild.gradleに以下追記  

    implementation project(':flutter')
    

## Andoridアプリからの呼び出し

    //Flutterエンジンをこしらえて
    val flutterEngine = FlutterEngine(this)
    flutterEngine.dartExecutor.executeDartEntrypoint(DartExecutor.DartEntrypoint.createDefault())

    //キャッシュしておく
    FlutterEngineCache.getInstance().put("my_engine_id", flutterEngine)
    
    //ボタンを押したらFlutterモジュールが立ち上がる（キャッシュを使うように指定する）
    button.setOnClickListener {
        startActivity(FlutterActivity.withCachedEngine("my_engine_id").build(this))
    }


## 参考記事
Androidはほぼ公式通り  
https://flutter.dev/docs/development/add-to-app/android/project-setup
