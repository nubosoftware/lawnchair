package ch.deletescape.lawnchair.ui.preferences

import android.content.ComponentName
import android.content.Intent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.os.bundleOf
import com.android.launcher3.R
import com.android.launcher3.notification.NotificationListener
import android.provider.Settings.ACTION_NOTIFICATION_LISTENER_SETTINGS as actionNotificationListenerSettings

@Composable
fun NotificationDotsPreference(interactor: PreferenceInteractor) {
    val context = LocalContext.current
    val extraFragmentArgKey = ":settings:fragment_args_key"
    val extraShowFragmentArgs = ":settings:show_fragment_args"

    fun onClick() {
        val intent = if (interactor.notificationDotsEnabled.value) {
            Intent("android.settings.NOTIFICATION_SETTINGS")
                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                .putExtra(extraFragmentArgKey, "notification_badging")
        } else {
            val cn = ComponentName(context, NotificationListener::class.java)
            val showFragmentArgs = bundleOf(extraFragmentArgKey to cn.flattenToString())
            Intent(actionNotificationListenerSettings)
                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                .putExtra(extraFragmentArgKey, cn.flattenToString())
                .putExtra(extraShowFragmentArgs, showFragmentArgs)
        }
        context.startActivity(intent)
    }

    Column(
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .height(48.dp)
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(start = 16.dp, end = 16.dp),
    ) {
        Text(
            text = stringResource(id = R.string.notification_dots),
            style = MaterialTheme.typography.subtitle1,
            color = MaterialTheme.colors.onBackground
        )
    }
}
