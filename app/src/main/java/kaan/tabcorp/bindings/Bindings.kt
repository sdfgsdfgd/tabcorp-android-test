package kaan.tabcorp.bindings

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.squareup.picasso.Picasso

@BindingAdapter("imageUrl")
fun getImagePicasso(view: ImageView, url: String?) {
    Picasso.get().load("$url").fit().centerInside().into(view)
}

//@BindingAdapter("categories")
//fun setCategories(container: ViewGroup, categories: List<Any>) {
//    val inflater = LayoutInflater.from(container.context)
//
//    for (c in categories) {
//        val binding = CategoryTextviewBinding.inflate(inflater, container, false)
//        binding.item = c
//
//        binding.categoryTextview.text = c.name
//
//        container.addView(binding.root)
//    }
//}