package com.moyoi.library_common.widget

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import com.moyoi.library_base.base.BaseDialog
import com.moyoi.library_common.databinding.DialogEditBinding
import com.moyoi.library_common.databinding.DialogStandardBinding

class EditDialog : BaseDialog() {

    companion object {
        @JvmStatic
        fun newInstance(): EditDialog {
            return EditDialog()
        }
    }

    private lateinit var binding: DialogEditBinding

    private var listener: OnDialogClickListener? = null
    private var title: String? = null
    private var name: String? = null
    private var link: String? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DialogEditBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        title?.apply {
            binding.title.text = this
            binding.title.visibility = View.VISIBLE
        }

        name?.apply {
            binding.editName.setText(this)
        }

        link?.apply {
            binding.editLink.setText(this)
        }

        binding.confirm.setOnClickListener {
            dismiss()
            listener?.apply {
                onConfirm(this@EditDialog)
            }
        }
        binding.cancel.setOnClickListener {
            dismiss()
            listener?.apply {
                onCancel(this@EditDialog)
            }
        }
    }

    fun setTitle(text: String): EditDialog {
        this.title = text
        return this
    }

    fun setName(text: String): EditDialog {
        this.name = text
        return this
    }

    fun setLink(text: String): EditDialog {
        this.link = text
        return this
    }

    fun getName() = binding.editName.text.toString()
    fun getLink() = binding.editLink.text.toString()

    fun setOnDialogClickListener(listener: OnDialogClickListener): EditDialog {
        this.listener = listener
        return this
    }

    interface OnDialogClickListener {
        fun onConfirm(dialog: EditDialog)
        fun onCancel(dialog: EditDialog)
    }

}