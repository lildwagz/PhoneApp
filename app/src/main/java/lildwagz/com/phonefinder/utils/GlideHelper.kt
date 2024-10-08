package lildwagz.com.phonefinder.utils

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import lildwagz.com.phonefinder.R
import lildwagz.com.phonefinder.R.color

object GlideHelper {

    private const val CORNER_RADIUS = 10

    fun loadCover(
        context: Context,
        imageUrl: String,
        imageView: ImageView,
        width: Int,
        height: Int
    ) {

        val requestOptions = RequestOptions()
            .transform(CenterCrop(), RoundedCorners(CORNER_RADIUS))

        Glide.with(context)
            .load(imageUrl)
            .apply(RequestOptions().override(width, height))
            .placeholder(
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                    context.getColor(color.white)
                } else {
                    context.resources.getColor(color.white)
                }
            )
            .transition(DrawableTransitionOptions.withCrossFade())
            .apply(requestOptions)
            .into(imageView)
    }
}