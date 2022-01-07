package com.example.pocwebview

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Base64
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var todoAdapter: TodoAdapter;

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main);


//        webViewSetup();

        val myWebView: WebView = findViewById(R.id.webviewScreen);
        myWebView.webViewClient = WebViewClient();
        myWebView.settings.javaScriptEnabled = true;
//        myWebView.settings.setSupportZoom(true);
//        myWebView.settings.safeBrowsingEnabled = true;
//        myWebView.loadUrl("https://www.google.com.br/");
        val unencodedHtml =
            "&lt;html&gt;&lt;body&gt;'%23' is the percent code for ‘#‘ &lt;/body&gt;&lt;/html&gt;"
        val summary = "<html><body>You scored <b>192</b> points.</body></html>"
        val encodedHtml = Base64.encodeToString(unencodedHtml.toByteArray(), Base64.NO_PADDING)
//        myWebView.loadData(encodedHtml, "text/html", "base64")
        myWebView.loadData(summary, "text/html", null);
//        myWebView.loadUrl("file:///android_asset/index.html");

        todoAdapter = TodoAdapter(mutableListOf());
        rvTodoItems.adapter = todoAdapter;
        rvTodoItems.layoutManager = LinearLayoutManager(this);

        btnAddTask.setOnClickListener{
            val todoTitle = etTodoTitle.text.toString();
            if (todoTitle.isNotEmpty()){
                val todo = Todo(todoTitle);
                todoAdapter.addTodo(todo);
                etTodoTitle.text.clear();
            }
        }

        btnDeleteDone.setOnClickListener{
            todoAdapter.deleteDoneTodos();
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun webViewSetup(){
        webviewScreen.webViewClient = WebViewClient();

        webviewScreen.apply {
            loadUrl("https://www.youtube.com/");
            settings.javaScriptEnabled = true;
            settings.safeBrowsingEnabled = true;
            settings.setSupportZoom(true);
        }
    }

    override fun onBackPressed() {
        if (webviewScreen.canGoBack()) webviewScreen.goBack() else super.onBackPressed()
    }
}