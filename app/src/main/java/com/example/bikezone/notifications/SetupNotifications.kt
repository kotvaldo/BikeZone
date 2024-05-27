package com.example.bikezone.notifications

import android.Manifest
import android.app.Activity
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import com.example.bikezone.R
import com.example.bikezone.ui.order.OrderDetail


private const val ORDER_CHANNEL_ID = "order_channel"
private const val ORDER_NOTIFICATION_ID = 2
private const val CHANNEL_ID = "logout_channel"
private const val NOTIFICATION_ID = 1
const val REQUEST_CODE_NOTIFICATION_PERMISSION = 1001

fun showDeleteAccountNotification(context: Context) {

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val name = context.getString(R.string.channel_name)
        val descriptionText = context.getString(R.string.channel_description)
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
            description = descriptionText
        }
        val notificationManager: NotificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }

    val builder = NotificationCompat.Builder(context, CHANNEL_ID)
        .setSmallIcon(R.drawable.baseline_logout_24)  // Musíte mať túto ikonu v drawable zdrojoch
        .setContentTitle(context.getString(R.string.delete_acc_notification_title))
        .setContentText(context.getString(R.string.delete_acc_notification_text))
        .setPriority(NotificationCompat.PRIORITY_DEFAULT)

    with(NotificationManagerCompat.from(context)) {
        // Zobraziť notifikáciu
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return
        }
        notify(NOTIFICATION_ID, builder.build())
    }
}

fun showOrderCreateNotification(context: Context) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val name = context.getString(R.string.order_channel_name)
        val descriptionText = context.getString(R.string.order_channel_description)
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel(ORDER_CHANNEL_ID, name, importance).apply {
            description = descriptionText
        }

        val notificationManager: NotificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }

    val builder = NotificationCompat.Builder(context, ORDER_CHANNEL_ID)
        .setSmallIcon(R.drawable.baseline_shopping_cart_checkout_24)  // Uistite sa, že máte túto ikonu v drawable zdrojoch
        .setContentTitle(context.getString(R.string.order_notification_title))
        .setContentText(context.getString(R.string.order_notification_text))
        .setPriority(NotificationCompat.PRIORITY_DEFAULT)

    with(NotificationManagerCompat.from(context)) {
        // Zobraziť notifikáciu
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // Tu požiadajte o povolenie ak nie je udelené
            return
        }
        notify(ORDER_NOTIFICATION_ID, builder.build())
    }
}


fun showDeleteOrderNotification(context: Context, orderDetail: OrderDetail) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val name = context.getString(R.string.order_channel_name)
        val descriptionText = context.getString(R.string.order_channel_description)
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel(ORDER_CHANNEL_ID, name, importance).apply {
            description = descriptionText
        }

        val notificationManager: NotificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }

    val builder = NotificationCompat.Builder(context, ORDER_CHANNEL_ID)
        .setSmallIcon(R.drawable.baseline_shopping_cart_checkout_24)  // Uistite sa, že máte túto ikonu v drawable zdrojoch
        .setContentTitle(context.getString(R.string.order_delete_notification_title, orderDetail.id.toString()))
        .setContentText(context.getString(R.string.order_delete_notification_text, orderDetail.id.toString()))
        .setPriority(NotificationCompat.PRIORITY_DEFAULT)

    with(NotificationManagerCompat.from(context)) {
        // Zobraziť notifikáciu
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // Tu požiadajte o povolenie ak nie je udelené
            return
        }
        notify(ORDER_NOTIFICATION_ID, builder.build())
    }
}

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
fun requestNotificationPermission(activity: Activity) {
    ActivityCompat.requestPermissions(
        activity,
        arrayOf(Manifest.permission.POST_NOTIFICATIONS),
        REQUEST_CODE_NOTIFICATION_PERMISSION
    )
}
@RequiresApi(Build.VERSION_CODES.TIRAMISU)
fun checkNotificationPermission(context: Context): Boolean {
    return ContextCompat.checkSelfPermission(
        context,
        Manifest.permission.POST_NOTIFICATIONS
    ) == PackageManager.PERMISSION_GRANTED
}