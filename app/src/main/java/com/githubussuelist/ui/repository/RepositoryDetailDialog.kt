package com.githubussuelist.ui.repository

import android.app.Dialog
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import com.githubussuelist.R
import com.githubussuelist.databinding.DialogRepositoryDetailBinding
import com.githubussuelist.extension.fragmentViewModelProvider
import com.githubussuelist.extension.injector
import com.githubussuelist.model.room.RepositoryEntityModel
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.dialog_repository_detail.*
import timber.log.Timber


class RepositoryDetailDialog : BottomSheetDialogFragment() {
    private val mViewModel by lazy {
        fragmentViewModelProvider(
            RepositoryDetailViewModel::class.java,
            injector.repositoryViewModelFactory()
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DialogRepositoryDetailBinding.inflate(inflater, container, false).also {
            it.repositoryViewModel = mViewModel
            it.lifecycleOwner = this
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupFullscreenDialog()
        toolbar_repository.setNavigationOnClickListener { dismiss() }
    }

    private fun setupFullscreenDialog() {
        dialog?.setOnShowListener { dialog ->
            val bottomSheetDialog = dialog as BottomSheetDialog
            val bottomSheet =
                bottomSheetDialog.findViewById<FrameLayout>(R.id.design_bottom_sheet) as FrameLayout
            val behavior: BottomSheetBehavior<*> =
                BottomSheetBehavior.from(bottomSheet)
            val layoutParams = bottomSheet.layoutParams

            val windowHeight: Int = getWindowHeight()
            if (layoutParams != null) {
                layoutParams.height = windowHeight
            }
            bottomSheet.layoutParams = layoutParams
            behavior.state = BottomSheetBehavior.STATE_EXPANDED
            behavior.peekHeight = windowHeight
            Timber.d("Window height: $windowHeight")

        }
    }

    private fun getWindowHeight(): Int { // Calculate window height for fullscreen use
        val displayMetrics = DisplayMetrics()
        requireActivity().windowManager.defaultDisplay.getMetrics(displayMetrics)
        return displayMetrics.heightPixels
    }

    override fun getTheme(): Int = R.style.RoundBottomSheetDialog
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog =
        BottomSheetDialog(requireContext(), theme)

    companion object {
        const val TAG = "RepositoryDialogFragment"
        private const val REPOSITORY_ENTITY_MODEL_EXTRA_KEY = "REPOSITORY_ENTITY_MODEL_EXTRA_KEY"

        fun getInstance(repositoryEntityModel: RepositoryEntityModel?) =
            RepositoryDetailDialog().also {
                it.arguments = Bundle().also { arguments ->
                    arguments.putParcelable(
                        REPOSITORY_ENTITY_MODEL_EXTRA_KEY,
                        repositoryEntityModel
                    )
                }
            }
    }
}