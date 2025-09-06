package journal.gratitude.com.gratitudejournal

import android.app.Activity
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.res.Configuration
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailability
import com.google.android.play.core.splitcompat.SplitCompat
import com.presently.logging.AnalyticsLogger
import com.presently.settings.PresentlySettings
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.EntryPointAccessors
import journal.gratitude.com.gratitudejournal.di.SettingsEntryPoint
import journal.gratitude.com.gratitudejournal.model.CAME_FROM_NOTIFICATION
import journal.gratitude.com.gratitudejournal.ui.security.AppLockFragment
import journal.gratitude.com.gratitudejournal.util.LocaleHelper
import journal.gratitude.com.gratitudejournal.util.reminders.NotificationScheduler
import journal.gratitude.com.gratitudejournal.util.reminders.ReminderReceiver.Companion.fromNotification
import javax.inject.Inject

@AndroidEntryPoint
class ContainerActivity : AppCompatActivity() {

    companion object {
        const val CHANNEL_ID = "Presently Gratitude Reminder"
        const val BACKUP_STATUS_CHANNEL = "Presently Automatic Backup Status"
        const val NOTIFICATION_SCREEN_EXTRA = "NOTIFICATION_EXTRA"
    }

    @Inject lateinit var settings: PresentlySettings
    @Inject lateinit var analyticsLogger: AnalyticsLogger

    override fun attachBaseContext(newBase: Context) {
        val settings = EntryPointAccessors.fromApplication(newBase, SettingsEntryPoint::class.java).settings
        val context: Context = LocaleHelper.onAppAttached(newBase, settings)
        super.attachBaseContext(context)
        SplitCompat.installActivity(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val currentTheme = settings.getCurrentTheme()
        setAppTheme(currentTheme)
        setContentView(R.layout.container_activity)

        createNotificationChannels()

        intent.extras?.let {
            val cameFromNotification = it.getBoolean(fromNotification, false)
            if (cameFromNotification) {
                analyticsLogger.recordEvent(CAME_FROM_NOTIFICATION)
            }
        }

        NotificationScheduler().configureNotifications(this, settings)

        if (resources.configuration.orientation != Configuration.ORIENTATION_LANDSCAPE) {
            //lays app behind system bars
                //not in landscape mode so navigation bar doesn't block UI
            WindowCompat.setDecorFitsSystemWindows(window, false)
        }
    }

    override fun onResume() {
        super.onResume()
        isGooglePlayServicesAvailable(this)
    }

    private fun isGooglePlayServicesAvailable(activity: Activity): Boolean {
        val googleApiAvailability = GoogleApiAvailability.getInstance()
        val status = googleApiAvailability.isGooglePlayServicesAvailable(activity)
        if (status != ConnectionResult.SUCCESS) {
            if (googleApiAvailability.isUserResolvableError(status)) {
                googleApiAvailability.getErrorDialog(activity, status, 2404)?.show()
            }
            return false
        }
        return true
    }

    override fun onStart() {
        super.onStart()

        val isBiometricsEnabled = settings.isBiometricsEnabled()
        if (isBiometricsEnabled) {
            if (settings.shouldLockApp()) {
                val fragment = AppLockFragment()
                supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.container_fragment, fragment)
                    .commit()
            }
        }
    }

    override fun onPause() {
        super.onPause()
        if (settings.isBiometricsEnabled()) {
            settings.setOnPauseTime()
        }
    }

    private fun createNotificationChannels() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel(CHANNEL_ID, getString(com.presently.strings.R.string.channel_name),  NotificationManager.IMPORTANCE_DEFAULT)
            notificationChannel.description = getString(com.presently.strings.R.string.channel_description)
            notificationChannel.enableVibration(true)

            val backupChannel = NotificationChannel(BACKUP_STATUS_CHANNEL, getString(com.presently.strings.R.string.backup_channel_name), NotificationManager.IMPORTANCE_HIGH)
            backupChannel.description = getString(com.presently.strings.R.string.backup_channel_description)
            backupChannel.enableVibration(true)

            val notificationManager = getSystemService(NotificationManager::class.java)

            notificationManager.createNotificationChannels(listOf(notificationChannel, backupChannel))

        }
    }

    private fun setAppTheme(currentTheme: String) {
        when (currentTheme) {
            "Sunset" -> setTheme(com.presently.ui.R.style.AppTheme_SUNSET)
            "Moonlight" -> setTheme(com.presently.ui.R.style.AppTheme_MOONLIGHT)
            "Midnight" -> setTheme(com.presently.ui.R.style.AppTheme_MIDNIGHT)
            "Ivy" -> setTheme(com.presently.ui.R.style.AppTheme_IVY)
            "Dawn" -> setTheme(com.presently.ui.R.style.AppTheme_DAWN)
            "Wesley" -> setTheme(com.presently.ui.R.style.AppTheme_WESLEY)
            "Moss" -> setTheme(com.presently.ui.R.style.AppTheme_MOSS)
            "Clean" -> setTheme(com.presently.ui.R.style.AppTheme_CLEAN)
            "Glacier" -> setTheme(com.presently.ui.R.style.AppTheme_GLACIER)
            "Gelato" -> setTheme(com.presently.ui.R.style.AppTheme_GELATO)
            "Waves" -> setTheme(com.presently.ui.R.style.AppTheme_WAVES)
            "Beach" -> setTheme(com.presently.ui.R.style.AppTheme_BEACH)
            "Field" -> setTheme(com.presently.ui.R.style.AppTheme_FIELD)
            "Western" -> setTheme(com.presently.ui.R.style.AppTheme_WESTERN)
            "Sunlight" -> setTheme(com.presently.ui.R.style.AppTheme_SUNLIGHT)
            "Tulip" -> setTheme(com.presently.ui.R.style.AppTheme_TULIP)
            "Rosie" -> setTheme(com.presently.ui.R.style.AppTheme_ROSIE)
            "Daisy" -> setTheme(com.presently.ui.R.style.AppTheme_DAISY)
            "Matisse" -> setTheme(com.presently.ui.R.style.AppTheme_MATISSE)
            "Clouds" -> setTheme(com.presently.ui.R.style.AppTheme_CLOUDS)
            "Monstera" -> setTheme(com.presently.ui.R.style.AppTheme_MONSTERA)
            "Lotus" -> setTheme(com.presently.ui.R.style.AppTheme_LOTUS)
            "Katie" -> setTheme(com.presently.ui.R.style.AppTheme_KATIE)
            "Brittany" -> setTheme(com.presently.ui.R.style.AppTheme_BRITTANY)
            "Jungle" -> setTheme(com.presently.ui.R.style.AppTheme_JUNGLE)
            "Julie" -> setTheme(com.presently.ui.R.style.AppTheme_JULIE)
            "Ellen" -> setTheme(com.presently.ui.R.style.AppTheme_ELLEN)
            "Danah" -> setTheme(com.presently.ui.R.style.AppTheme_DANAH)
            "Ahalya" -> setTheme(com.presently.ui.R.style.AppTheme_AHALYA)
            "Rem'mie" -> setTheme(com.presently.ui.R.style.AppTheme_REMMIE)
            "Marsha" -> setTheme(com.presently.ui.R.style.AppTheme_MARSHA)
            "Brayla" -> setTheme(com.presently.ui.R.style.AppTheme_BRAYLA)
            "Autumn" -> setTheme(com.presently.ui.R.style.AppTheme_AUTUMN)
            "Betty" -> setTheme(com.presently.ui.R.style.AppTheme_BETTY)
            "Boo" -> setTheme(com.presently.ui.R.style.AppTheme_BOO)
            "Calm" -> setTheme(com.presently.ui.R.style.AppTheme_CALM)
            "Passion" -> setTheme(com.presently.ui.R.style.AppTheme_PASSION)
            "Joy" -> setTheme(com.presently.ui.R.style.AppTheme_JOY)
            "Annalisa" -> setTheme(com.presently.ui.R.style.AppTheme_ANNALISA)
            "Celia" -> setTheme(com.presently.ui.R.style.AppTheme_CELIA)
            "Sophia" -> setTheme(com.presently.ui.R.style.AppTheme_SOPHIA)
            "Emilia" -> setTheme(com.presently.ui.R.style.AppTheme_EMILIA)
            "Betsy" -> setTheme(com.presently.ui.R.style.AppTheme_BETSY)
            "Pacific" -> setTheme(com.presently.ui.R.style.AppTheme_PACIFIC)
            else -> setTheme(com.presently.ui.R.style.Base_AppTheme)
        }
    }

}
