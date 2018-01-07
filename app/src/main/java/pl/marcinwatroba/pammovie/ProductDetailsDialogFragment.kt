package pl.marcinwatroba.pammovie

import android.app.Dialog
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v7.app.AlertDialog
import kotlinx.android.synthetic.main.dialog_product_details.view.*

/**
 * Created by Marcin on 03.01.2018.
 */

class ProductDetailsDialogFragment : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val inflater = activity.layoutInflater
        val view = inflater.inflate(R.layout.dialog_product_details, null)

        val type = arguments.getInt(TYPE)

        if (type == GLASS) {
            view.costTextView.text = getString(R.string.glass_cost)
            view.descriptionTextView.text = getString(R.string.glass_descriotion)
        } else {
            view.costTextView.text = getString(R.string.plate_cost)
            view.descriptionTextView.text = getString(R.string.plate_description)
        }

        return AlertDialog.Builder(activity)
                .setTitle(if (type == GLASS) "Kieliszki IKEA" else "Talerz IKEA")
                .setView(view)
                .create()
    }

    companion object {

        private const val TYPE = "TYPE"
        const val PLATE = 0
        const val GLASS = 1


        fun newInstance(type: Int): ProductDetailsDialogFragment {
            val frag = ProductDetailsDialogFragment()
            val args = Bundle()
            args.putInt(TYPE, type)
            frag.arguments = args
            return frag
        }
    }
}