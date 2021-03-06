package kaan.tabcorp.bindings

import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources
import androidx.databinding.BindingAdapter
import com.squareup.picasso.Picasso
import kaan.tabcorp.R
import kaan.tabcorp.ui.spacex.LaunchItem
import kaan.tabcorp.utilities.DateTimeUtils.toReadableDate

@BindingAdapter("imageUrl")
fun setImagePicasso(view: ImageView, url: String?) {
    url?.let {
        Picasso.get().load("$url").placeholder(R.drawable.ic_fortune_cat).into(view)
    }
}

@BindingAdapter("imageSuccess")
fun setImageSuccess(view: ImageView, success: Boolean?) {
    success?.let {
        view.setImageDrawable(
            if (success == true)
                AppCompatResources.getDrawable(view.context, R.drawable.ic_fortune_cat)
            else null
        )
    }
}

@BindingAdapter("text")
fun setText(view: TextView, text: String?) {
    view.text = text?.let {
        if (text.isNotEmpty())
            text
        else
            view.context.getString(R.string.launch_empty_description)
    } ?: view.context.getString(R.string.launch_no_description)
}

@BindingAdapter("successText")
fun setSuccessText(view: TextView, success: Boolean?) {
    view.text = success?.let {
        if (success)
            view.context.getString(R.string.launch_text_success)
        else
            view.context.getString(R.string.launch_text_fail)
    } ?: view.context.getString(R.string.launch_text_unknown)
}

@BindingAdapter("date")
fun setDate(view: TextView, launchItem: LaunchItem?) {
    val dateText = launchItem?.date?.toReadableDate() ?: view.context.getString(R.string.launch_text_date_unknown)
    val missionText = launchItem?.missionName.orEmpty()
    view.text = view.context.getString(R.string.date_and_mission_text, dateText, missionText)
}

