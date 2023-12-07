package com.example.taipeitour

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.taipeitourv1.DataClass.AttractionsData
import com.example.taipeitourv1.DataClass.TaipeiTourData
import com.example.taipeitourv1.Dialog.LoadingDialog
import com.example.taipeitourv1.R
import com.example.taipeitourv1.TaipeiTourList.OnItemClickListener
import com.example.taipeitourv1.TaipeiTourList.TaipeiTourAdapter
import com.example.taipeitourv1.TourContentFragment
import com.example.taipeitourv1.ViewModel.TaipeiTourViewModel
import com.example.taipeitourv1.ViewModel.ViewModelFactory
import java.io.Serializable

class TaipeiTourFragment : Fragment(), OnItemClickListener {
    private lateinit var viewModel: TaipeiTourViewModel
    private lateinit var loadingDialog: LoadingDialog
    private lateinit var layoutView: View
    private lateinit var taipei_tour_recyclerview: RecyclerView
    private lateinit var taipei_tour_toolbar: Toolbar
    private var previous_language = "zh-tw"
    private var language = "zh-tw"
    private var previous_title = "臺北旅遊"
    private var title = "臺北旅遊"

    private lateinit var taipeitourData: ArrayList<TaipeiTourData>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        layoutView = inflater.inflate(R.layout.fragment_taipei_tour, container, false)
        initView()
        setMenu()
        loadingDialog = LoadingDialog(requireContext())

        activity?.let {activity ->
            viewModel = ViewModelProvider(activity, ViewModelFactory(requireContext().applicationContext)).get(TaipeiTourViewModel::class.java)

            viewModel.fetchTaipeiTourData(language)

            viewModel.loading.observe(activity, Observer{ isLoading ->
                if (isLoading) {
                    loadingDialog.show()
                } else {
                    setToolbar(title)
                    loadingDialog.dismiss()
                }
            })

            viewModel.loadingError.observe(activity, Observer{ isLoadingError ->
                if (isLoadingError) {
                    title = previous_title
                    language = previous_language
                    loadingDialog.dismiss()
                    Toast.makeText(requireContext(), "sorry, this data is currently under maintenance.", Toast.LENGTH_SHORT).show()
                }
            })

            viewModel.taipeitourData.observe(activity, Observer{taipeitour ->
                taipeitourData = taipeitour
                taipei_tour_recyclerview.layoutManager = LinearLayoutManager(activity)
                taipei_tour_recyclerview.setHasFixedSize(true)
                taipei_tour_recyclerview.adapter = TaipeiTourAdapter(emptyList(), this)
                taipei_tour_recyclerview.adapter = TaipeiTourAdapter(taipeitour, this)
            })
        }

        return layoutView
    }

    private fun initView() {
        taipei_tour_recyclerview = layoutView.findViewById(R.id.Taipei_Tour_RecyclerView)
        taipei_tour_toolbar = layoutView.findViewById(R.id.Taipei_Tour_Toolbar)
    }

    private fun setToolbar(toolbar_title: String) {
        taipei_tour_toolbar.title = toolbar_title
    }

    private fun setMenu() {
        taipei_tour_toolbar.inflateMenu(R.menu.translation_button)
        taipei_tour_toolbar.setOnMenuItemClickListener { Menu ->
            when (Menu.itemId) {
                R.id.menu_translation -> {
                    val languageList = resources.getStringArray(R.array.language)
                    AlertDialog.Builder(requireContext()).setItems(languageList) { _, which ->
                        val name = languageList[which]
                        when (name) {
                            getString(R.string.zh_tw) -> {
                                setLanguage("臺北旅遊", "zh-tw")
                            }

                            getString(R.string.zh_cn) -> {
                                setLanguage("台北旅游", "zh-cn")
                            }

                            getString(R.string.en) -> {
                                setLanguage("TaipeiTour", "en")
                            }

                            getString(R.string.ja) -> {
                                setLanguage("台北ツアー", "ja")
                            }

                            getString(R.string.ko) -> {
                                setLanguage("타이페이 투어", "ko")
                            }

                            getString(R.string.es) -> {
                                setLanguage("Tour por Taipéi", "es")
                            }

                            getString(R.string.id) -> {
                                setLanguage("Tur Taipei", "id")
                            }

                            getString(R.string.th) -> {
                                setLanguage("ทัวร์ไทเป", "th")
                            }

                            getString(R.string.vi) -> {
                                setLanguage("Chuyến thăm Taipei", "vi")
                            }
                        }
                    }.show()
                }
            }
            false
        }
    }

    private fun setLanguage(settitle: String, setlanguage: String) {
        previous_language = language
        previous_title = title
        title = settitle
        language = setlanguage
        viewModel.fetchTaipeiTourData(language)
    }

    override fun onItemClick(position: Int) {
        activity?.let { activity ->
            var taipeitourDataToPass = ArrayList<AttractionsData>()
            taipeitourDataToPass.add(taipeitourData.get(0).data.get(position))
            val tourContentFragment = TourContentFragment()
            val bundle = Bundle()
            bundle.putSerializable("taipeitourDataToPass", taipeitourDataToPass as Serializable)
            tourContentFragment.arguments = bundle
            val fragmentTransaction = activity.supportFragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.Main_FrameLayout, tourContentFragment)
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()
        }
    }
}