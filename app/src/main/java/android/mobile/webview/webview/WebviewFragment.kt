package android.mobile.webview.webview

import android.app.ProgressDialog
import android.graphics.Bitmap
import android.mobile.webview.R
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.app.AppCompatActivity
import android.view.*
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast

class WebviewFragment : Fragment() {

    lateinit var res: WebView
    lateinit var pd: ProgressDialog
    lateinit var mSwipeRefreshLayout: SwipeRefreshLayout

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.fragment_webview, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        (activity as AppCompatActivity).supportActionBar!!.hide()
        res = getView()!!.findViewById(R.id.webview) as WebView
        mSwipeRefreshLayout = getView()!!.findViewById(R.id.swipeToRefresh) as SwipeRefreshLayout
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary)

        LoadWebview()

        mSwipeRefreshLayout.setOnRefreshListener {
            res.reload()
            mSwipeRefreshLayout.isRefreshing = false
        }

        res.canGoBack()
        res.setOnKeyListener(View.OnKeyListener { v, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_BACK
                    && event.action == MotionEvent.ACTION_UP
                    && res.canGoBack()) {
                res.goBack()
                return@OnKeyListener true
            }
            false
        })
    }

    private fun LoadWebview() {
        mSwipeRefreshLayout.isRefreshing = false
        pd = ProgressDialog(activity)
        pd.setMessage("Mohon menunggu beberapa saat ...")
        pd.setCancelable(false)
        res.settings.javaScriptEnabled = true
        res.webViewClient = object : WebViewClient() {
            override fun onReceivedError(view: WebView, request: WebResourceRequest, error: WebResourceError) {
                super.onReceivedError(view, request, error)
                Toast.makeText(activity, "Maaf, terjadi kesalahan", Toast.LENGTH_SHORT).show()
                pd.dismiss()
            }

            override fun onPageStarted(view: WebView, url: String, favicon: Bitmap?) {
                super.onPageStarted(view, url, favicon)
                pd.show()
            }

            override fun onPageFinished(view: WebView, url: String) {
                super.onPageFinished(view, url)
                pd.dismiss()
            }
        }
        res.loadUrl("https://atp.apps.cs.ipb.ac.id/")
    }
}