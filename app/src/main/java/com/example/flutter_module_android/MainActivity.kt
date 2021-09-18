package com.example.flutter_module_android

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import io.flutter.embedding.android.FlutterActivity
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.embedding.engine.FlutterEngineCache
import io.flutter.embedding.engine.dart.DartExecutor

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val flutterEngine = FlutterEngine(this)
        flutterEngine.dartExecutor.executeDartEntrypoint(DartExecutor.DartEntrypoint.createDefault())

        //キャッシュしておく
        FlutterEngineCache.getInstance().put("my_engine_id", flutterEngine)

        // Instantiation of linearLayout
        val layout = LinearLayout(this)
        setContentView(layout)

        // Instantiation of Button
        val button = Button(this)
        button.text = getString(R.string.button)
        layout.addView(button)

        //キャッシュを使うように指定する
        button.setOnClickListener {
            startActivity(FlutterActivity.withCachedEngine("my_engine_id").build(this))
        }

    }


}