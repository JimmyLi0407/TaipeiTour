package com.example.taipeitourv1

import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.core.text.HtmlCompat
import com.bumptech.glide.Glide
import com.example.taipeitourv1.DataClass.AttractionsData


class TourContentFragment : Fragment()  {
    private lateinit var taipeitourDataToPass: ArrayList<AttractionsData>
    private lateinit var layoutView: View
    private lateinit var tour_content_toolbar: Toolbar
    private lateinit var tour_content_toolbar_title: TextView
    private lateinit var tour_content_imageViewContainer: LinearLayout
    private lateinit var tour_content_title: TextView
    private lateinit var tour_content_content: TextView
    private lateinit var tour_content_address: TextView
    private lateinit var tour_content_lastupdatetime: TextView
    private lateinit var tour_content_weburl: TextView
    private lateinit var tour_content_gridLayout: GridLayout

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        layoutView = inflater.inflate(R.layout.fragment_tour_content, container, false)
        val bundle = arguments
        if (bundle != null) {
            taipeitourDataToPass = bundle.getSerializable("taipeitourDataToPass") as ArrayList<AttractionsData>
        }
        initView()
        setToolbar(taipeitourDataToPass.get(0).name)

        return layoutView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadCoverImage()
        setContent()
        setWebViewLink()
        loadAllImages()
    }

    private fun initView() {
        tour_content_toolbar = layoutView.findViewById(R.id.Tour_Content_Toolbar)
        tour_content_toolbar_title = layoutView.findViewById(R.id.Tour_Content_Toolbar_Title)
        tour_content_imageViewContainer = layoutView.findViewById(R.id.Tour_Content_ImageViewContainer)
        tour_content_title = layoutView.findViewById(R.id.Tour_Content_Title)
        tour_content_content = layoutView.findViewById(R.id.Tour_Content_Content)
        tour_content_address = layoutView.findViewById(R.id.Tour_Content_Address)
        tour_content_lastupdatetime = layoutView.findViewById(R.id.Tour_Content_LastUpdatedTime)
        tour_content_weburl = layoutView.findViewById(R.id.Tour_Content_WebUrl)
        tour_content_gridLayout = layoutView.findViewById(R.id.Tour_Content_GridLayout)
    }

    private fun setToolbar(toolbar_title: String) {
        tour_content_toolbar_title.text = toolbar_title
        tour_content_toolbar.navigationIcon = ContextCompat.getDrawable(requireContext(), R.drawable.ic_arrow_back_white)
        tour_content_toolbar.setNavigationOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }
    }

    private fun loadImage(view: ImageView, position: Int) {
        Glide.with(this)
            .load(taipeitourDataToPass.get(0).images.get(position).src)
            .placeholder(R.drawable.image)
            .error(R.drawable.image)
            .into(view)
    }

    private fun loadCoverImage() {
        if (taipeitourDataToPass.get(0).images.size != 0) {
            val imageView = ImageView(requireContext())

            val layoutParams = LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )

            tour_content_imageViewContainer.layoutParams = layoutParams
            tour_content_imageViewContainer.addView(imageView)
            loadImage(imageView, 0)
        }
    }

    private fun setContent() {
        tour_content_title.text = taipeitourDataToPass.get(0).name
        tour_content_content.text = taipeitourDataToPass.get(0).introduction
        tour_content_address.text = "Address\n" + taipeitourDataToPass.get(0).address
        tour_content_lastupdatetime.text = "Last Update Time\n" + taipeitourDataToPass.get(0).modified
    }

    private fun setWebViewLink() {
        val linkText = "<a href=\" + taipeitourDataToPass.get(0).url + \">" + taipeitourDataToPass.get(0).url + "</a>"
        tour_content_weburl.text = HtmlCompat.fromHtml(linkText, HtmlCompat.FROM_HTML_MODE_LEGACY)
        tour_content_weburl.movementMethod = LinkMovementMethod.getInstance()
        tour_content_weburl.setOnClickListener {
            activity?.let { activity ->
                val WebViewFragment = WebViewFragment()
                val bundle = Bundle()
                bundle.putString("url", taipeitourDataToPass.get(0).url)
                WebViewFragment.arguments = bundle
                val fragmentTransaction = activity.supportFragmentManager.beginTransaction()
                fragmentTransaction.replace(R.id.Main_FrameLayout, WebViewFragment)
                fragmentTransaction.addToBackStack(null)
                fragmentTransaction.commit()
            }
        }
    }

    private fun loadAllImages() {
        val columnCount = 3
        val spacingDp = 4
        val spacingPx = (spacingDp * resources.displayMetrics.density + 0.5f).toInt()

        if (taipeitourDataToPass.get(0).images.size != 0) {
            for (i in 0 .. taipeitourDataToPass.get(0).images.size-1) {
                val imageView = ImageView(requireContext())
                val displayMetrics = resources.displayMetrics
                val screenWidth = displayMetrics.widthPixels
                val imageViewWidth = screenWidth / columnCount
                imageView.layoutParams = GridLayout.LayoutParams().apply {
                    width = imageViewWidth
                    height = ViewGroup.LayoutParams.WRAP_CONTENT
                    marginEnd = spacingPx
                    bottomMargin = spacingPx
                    setGravity(Gravity.CENTER_VERTICAL)
                }
                tour_content_gridLayout.addView(imageView)
                loadImage(imageView, i)
            }
        }
    }
}