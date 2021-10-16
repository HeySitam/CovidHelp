package com.sitamadex11.CovidHelp.activity

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.sitamadex11.CovidHelp.R
import kotlinx.android.synthetic.main.bottom_sheet_other_input.*

class OtherItemBottomSheet(
  private val onCancel: () -> Unit,
  private val onSave: (text: String) -> Unit
) : BottomSheetDialogFragment() {

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    return inflater.inflate(R.layout.bottom_sheet_other_input, container, false)
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    button_cancel.setOnClickListener {
      onCancel.invoke()
      dismiss()
    }
    button_save.setOnClickListener {
      onSave.invoke(etOthers.text.toString())
      dismiss()
    }
  }

  override fun onDismiss(dialog: DialogInterface) {
    if (etOthers.text.isNullOrEmpty()) {
      onCancel.invoke()
    }
    dismiss()
  }

}