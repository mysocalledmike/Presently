package journal.gratitude.com.gratitudejournal.ui.themes

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.presently.logging.AnalyticsLogger
import com.presently.logging.CrashReporter
import com.presently.settings.PresentlySettings
import com.presently.ui.setStatusBarColorsForBackground
import dagger.hilt.android.AndroidEntryPoint
import journal.gratitude.com.gratitudejournal.R
import journal.gratitude.com.gratitudejournal.databinding.FragmentThemeBinding
import journal.gratitude.com.gratitudejournal.model.Designer
import journal.gratitude.com.gratitudejournal.model.OPENED_PRIVACY_POLICY
import journal.gratitude.com.gratitudejournal.model.Theme
import javax.inject.Inject

@AndroidEntryPoint
class ThemeFragment : Fragment() {

    @Inject lateinit var settings: PresentlySettings
    @Inject lateinit var analytics: AnalyticsLogger
    @Inject lateinit var crashReporter: CrashReporter

    private var _binding: FragmentThemeBinding? = null
    private val binding get() = _binding!!

    private var listener = object : OnThemeSelectedListener {
        override fun onThemeSelected(theme: String) {
            settings.setTheme(theme)

            parentFragmentManager.popBackStack()
            activity?.recreate()
        }

        override fun onDesignerClicked(designer: Designer) {
            analytics.recordEvent(OPENED_PRIVACY_POLICY)

            try {
                val browserIntent =
                    Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse(designer.designerWebsite)
                    )
                startActivity(browserIntent)
            } catch (activityNotFoundException: ActivityNotFoundException) {
                Toast.makeText(requireContext(), com.presently.strings.R.string.no_app_found, Toast.LENGTH_SHORT).show()
                crashReporter.logHandledException(activityNotFoundException)
            }
        }
    }

    private val adapter = ThemeListAdapter(listener)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentThemeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val themeList: List<Theme> = listOf(
            Theme(
                "Original",
                ContextCompat.getColor(requireContext(), com.presently.ui.R.color.originalTimelineColor),
                ContextCompat.getColor(requireContext(), com.presently.ui.R.color.originalBackgroundColor),
                ContextCompat.getColor(requireContext(), com.presently.ui.R.color.originalBackgroundColor),
                ContextCompat.getColor(requireContext(), com.presently.ui.R.color.originalTimelineColor),
                com.presently.ui.R.drawable.ic_flower
            ),
            Theme(
                "Midnight",
                ContextCompat.getColor(requireContext(), com.presently.ui.R.color.midnightToolbarColor),
                ContextCompat.getColor(requireContext(), com.presently.ui.R.color.midnightBackgroundColor),
                ContextCompat.getColor(requireContext(), com.presently.ui.R.color.midnightToolbarItemColor),
                ContextCompat.getColor(requireContext(), com.presently.ui.R.color.midnightMainTextAndButtonColor),
                com.presently.ui.R.drawable.ic_moon
            ),
            Theme(
                "Brittany",
                ContextCompat.getColor(requireContext(), com.presently.ui.R.color.brittanyToolbarColor),
                ContextCompat.getColor(requireContext(), com.presently.ui.R.color.brittanyBackgroundColor),
                ContextCompat.getColor(requireContext(), com.presently.ui.R.color.brittanyToolbarItemColor),
                ContextCompat.getColor(requireContext(), com.presently.ui.R.color.brittanyMainTextAndButtonColor),
                com.presently.ui.R.drawable.ic_brittany,
                true
            ),
            Theme(
                "Sophia",
                ContextCompat.getColor(requireContext(), com.presently.ui.R.color.sophiaToolbarColor),
                ContextCompat.getColor(requireContext(), com.presently.ui.R.color.sophiaTimelineBackgroundColor),
                ContextCompat.getColor(requireContext(), com.presently.ui.R.color.sophiaToolbarLogoColor),
                ContextCompat.getColor(requireContext(), com.presently.ui.R.color.sophiaTimelineHeaderColor),
                com.presently.ui.R.drawable.ic_sophia,
                true
            ),
            Theme(
                "Annalisa",
                ContextCompat.getColor(requireContext(), com.presently.ui.R.color.annalisaToolbarColor),
                ContextCompat.getColor(requireContext(), com.presently.ui.R.color.annalisaTimelineBackgroundColor),
                ContextCompat.getColor(requireContext(), com.presently.ui.R.color.annalisaToolbarLogoColor),
                ContextCompat.getColor(requireContext(), com.presently.ui.R.color.annalisaTimelineHeaderColor),
                com.presently.ui.R.drawable.ic_annalisa,
                true
            ),
            Theme(
                "Celia",
                ContextCompat.getColor(requireContext(), com.presently.ui.R.color.celiaToolbarColor),
                ContextCompat.getColor(requireContext(), com.presently.ui.R.color.celiaTimelineBackgroundColor),
                ContextCompat.getColor(requireContext(), com.presently.ui.R.color.celiaToolbarLogoColor),
                ContextCompat.getColor(requireContext(), com.presently.ui.R.color.celiaTimelineHeaderColor),
                com.presently.ui.R.drawable.ic_celia,
                true
            ),
            Theme(
                "Betty",
                ContextCompat.getColor(requireContext(), com.presently.ui.R.color.bettyToolbarColor),
                ContextCompat.getColor(requireContext(), com.presently.ui.R.color.bettyBackgroundColor),
                ContextCompat.getColor(requireContext(), com.presently.ui.R.color.bettyToolbarItemColor),
                ContextCompat.getColor(requireContext(), com.presently.ui.R.color.bettyMainTextAndButtonColor),
                com.presently.ui.R.drawable.ic_betty,
                true
            ),
            Theme(
                "Autumn",
                ContextCompat.getColor(requireContext(), com.presently.ui.R.color.autumnToolbarColor),
                ContextCompat.getColor(requireContext(), com.presently.ui.R.color.autumnBackgroundColor),
                ContextCompat.getColor(requireContext(), com.presently.ui.R.color.autumnToolbarItemColor),
                ContextCompat.getColor(requireContext(), com.presently.ui.R.color.autumnMainTextAndButtonColor),
                com.presently.ui.R.drawable.ic_autumn_leaves,
                true
            ),
            Theme(
                "Boo",
                ContextCompat.getColor(requireContext(), com.presently.ui.R.color.booToolbarColor),
                ContextCompat.getColor(requireContext(), com.presently.ui.R.color.booBackgroundColor),
                ContextCompat.getColor(requireContext(), com.presently.ui.R.color.booToolbarItemColor),
                ContextCompat.getColor(requireContext(), com.presently.ui.R.color.booMainTextAndButtonColor),
                com.presently.ui.R.drawable.ic_boo,
                true
            ),
            Theme(
                "Pacific",
                ContextCompat.getColor(requireContext(), com.presently.ui.R.color.pacificToolbarColor),
                ContextCompat.getColor(requireContext(), com.presently.ui.R.color.pacificTimelineBackgroundColor),
                ContextCompat.getColor(requireContext(), com.presently.ui.R.color.pacificToolbarLogoColor),
                ContextCompat.getColor(requireContext(), com.presently.ui.R.color.pacificTimelineHeaderColor),
                com.presently.ui.R.drawable.ic_pacific,
                true
            ),
            Theme(
                "Emilia",
                ContextCompat.getColor(requireContext(), com.presently.ui.R.color.emiliaToolbarColor),
                ContextCompat.getColor(requireContext(), com.presently.ui.R.color.emiliaTimelineBackgroundColor),
                ContextCompat.getColor(requireContext(), com.presently.ui.R.color.emiliaToolbarLogoColor),
                ContextCompat.getColor(requireContext(), com.presently.ui.R.color.emiliaTimelineHeaderColor),
                com.presently.ui.R.drawable.ic_emilia,
                true
            ),
            Theme(
                "Betsy",
                ContextCompat.getColor(requireContext(), com.presently.ui.R.color.betsyToolbarColor),
                ContextCompat.getColor(requireContext(), com.presently.ui.R.color.betsyTimelineBackgroundColor),
                ContextCompat.getColor(requireContext(), com.presently.ui.R.color.betsyToolbarLogoColor),
                ContextCompat.getColor(requireContext(), com.presently.ui.R.color.betsyTimelineHeaderColor),
                com.presently.ui.R.drawable.ic_betsy,
                true
            ),
            Theme(
                "Calm",
                ContextCompat.getColor(requireContext(), com.presently.ui.R.color.calmToolbarColor),
                ContextCompat.getColor(requireContext(), com.presently.ui.R.color.calmTimelineBackgroundColor),
                ContextCompat.getColor(requireContext(), com.presently.ui.R.color.calmToolbarLogoColor),
                ContextCompat.getColor(requireContext(), com.presently.ui.R.color.calmTimelineHeaderColor),
                com.presently.ui.R.drawable.ic_calm,
                true,
                Designer("Tishya Oedit", "https://www.instagram.com/tishyaoedit/")
            ),
            Theme(
                "Passion",
                ContextCompat.getColor(requireContext(), com.presently.ui.R.color.passionToolbarColor),
                ContextCompat.getColor(requireContext(), com.presently.ui.R.color.passionTimelineBackgroundColor),
                ContextCompat.getColor(requireContext(), com.presently.ui.R.color.passionToolbarLogoColor),
                ContextCompat.getColor(requireContext(), com.presently.ui.R.color.passionTimelineHeaderColor),
                com.presently.ui.R.drawable.ic_passion,
                true,
                Designer("Tishya Oedit", "https://www.instagram.com/tishyaoedit/")
            ),
            Theme(
                "Joy",
                ContextCompat.getColor(requireContext(), com.presently.ui.R.color.joyToolbarColor),
                ContextCompat.getColor(requireContext(), com.presently.ui.R.color.joyTimelineBackgroundColor),
                ContextCompat.getColor(requireContext(), com.presently.ui.R.color.joyToolbarLogoColor),
                ContextCompat.getColor(requireContext(), com.presently.ui.R.color.joyTimelineHeaderColor),
                com.presently.ui.R.drawable.ic_joy,
                true,
                Designer("Tishya Oedit", "https://www.instagram.com/tishyaoedit/")
            ),
            Theme(
                "Rem'mie",
                ContextCompat.getColor(requireContext(), com.presently.ui.R.color.loveToolbarColor),
                ContextCompat.getColor(requireContext(), com.presently.ui.R.color.loveBackgroundColor),
                ContextCompat.getColor(requireContext(), com.presently.ui.R.color.loveToolbarItemColor),
                ContextCompat.getColor(requireContext(), com.presently.ui.R.color.loveMainTextAndButtonColor),
                com.presently.ui.R.drawable.ic_rainbow,
                true
            ),
            Theme(
                "Marsha",
                ContextCompat.getColor(requireContext(), com.presently.ui.R.color.marshaToolbarColor),
                ContextCompat.getColor(requireContext(), com.presently.ui.R.color.marshaBackgroundColor),
                ContextCompat.getColor(requireContext(), com.presently.ui.R.color.marshaToolbarItemColor),
                ContextCompat.getColor(requireContext(), com.presently.ui.R.color.marshaMainTextAndButtonColor),
                com.presently.ui.R.drawable.ic_trans_hearts,
                true
            ),
            Theme(
                "Brayla",
                ContextCompat.getColor(requireContext(), com.presently.ui.R.color.braylaToolbarColor),
                ContextCompat.getColor(requireContext(), com.presently.ui.R.color.braylaBackgroundColor),
                ContextCompat.getColor(requireContext(), com.presently.ui.R.color.braylaToolbarItemColor),
                ContextCompat.getColor(requireContext(), com.presently.ui.R.color.braylaMainTextAndButtonColor),
                com.presently.ui.R.drawable.ic_brayla,
                true
            ),
            Theme(
                "Dawn",
                ContextCompat.getColor(requireContext(), com.presently.ui.R.color.dawnToolbarColor),
                ContextCompat.getColor(requireContext(), com.presently.ui.R.color.dawnBackgroundColor),
                ContextCompat.getColor(requireContext(), com.presently.ui.R.color.dawnToolbarItemColor),
                ContextCompat.getColor(requireContext(), com.presently.ui.R.color.dawnMainTextAndButtonColor),
                com.presently.ui.R.drawable.ic_sun_icon
            ),
            Theme(
                "Daisy",
                ContextCompat.getColor(requireContext(), com.presently.ui.R.color.daisyToolbarColor),
                ContextCompat.getColor(requireContext(), com.presently.ui.R.color.daisyBackgroundColor),
                ContextCompat.getColor(requireContext(), com.presently.ui.R.color.daisyToolbarItemColor),
                ContextCompat.getColor(requireContext(), com.presently.ui.R.color.daisyMainTextAndButtonColor),
                com.presently.ui.R.drawable.daisies,
                true
            ),
            Theme(
                "Tulip",
                ContextCompat.getColor(requireContext(), com.presently.ui.R.color.tulipToolbarColor),
                ContextCompat.getColor(requireContext(), com.presently.ui.R.color.tulipBackgroundColor),
                ContextCompat.getColor(requireContext(), com.presently.ui.R.color.tulipToolbarItemColor),
                ContextCompat.getColor(requireContext(), com.presently.ui.R.color.tulipMainTextAndButtonColor),
                com.presently.ui.R.drawable.ic_tulip,
                true
            ),
            Theme(
                "Waves",
                ContextCompat.getColor(requireContext(), com.presently.ui.R.color.wavesToolbarColor),
                ContextCompat.getColor(requireContext(), com.presently.ui.R.color.wavesBackgroundColor),
                ContextCompat.getColor(requireContext(), com.presently.ui.R.color.wavesToolbarItemColor),
                ContextCompat.getColor(requireContext(), com.presently.ui.R.color.wavesMainTextAndButtonColor),
                com.presently.ui.R.drawable.ic_wave
            ),

            Theme(
                "Sunlight",
                ContextCompat.getColor(requireContext(), com.presently.ui.R.color.sunlightToolbarColor),
                ContextCompat.getColor(requireContext(), com.presently.ui.R.color.sunlightBackgroundColor),
                ContextCompat.getColor(requireContext(), com.presently.ui.R.color.sunlightToolbarItemColor),
                ContextCompat.getColor(requireContext(), com.presently.ui.R.color.sunlightMainTextAndButtonColor),
                com.presently.ui.R.drawable.ic_sunshine,
                true
            ),
            Theme(
                "Katie",
                ContextCompat.getColor(requireContext(), com.presently.ui.R.color.katieToolbarColor),
                ContextCompat.getColor(requireContext(), com.presently.ui.R.color.katieBackgroundColor),
                ContextCompat.getColor(requireContext(), com.presently.ui.R.color.katieToolbarItemColor),
                ContextCompat.getColor(requireContext(), com.presently.ui.R.color.katieMainTextAndButtonColor),
                com.presently.ui.R.drawable.ic_katie,
                true
            ),
            Theme(
                "Matisse",
                ContextCompat.getColor(requireContext(), com.presently.ui.R.color.matisseToolbarColor),
                ContextCompat.getColor(requireContext(), com.presently.ui.R.color.matisseBackgroundColor),
                ContextCompat.getColor(requireContext(), com.presently.ui.R.color.matisseToolbarItemColor),
                ContextCompat.getColor(requireContext(), com.presently.ui.R.color.matisseMainTextAndButtonColor),
                com.presently.ui.R.drawable.ic_matisse
            ),
            Theme(
                "Jungle",
                ContextCompat.getColor(requireContext(), com.presently.ui.R.color.jungleToolbarColor),
                ContextCompat.getColor(requireContext(), com.presently.ui.R.color.jungleBackgroundColor),
                ContextCompat.getColor(requireContext(), com.presently.ui.R.color.jungleToolbarItemColor),
                ContextCompat.getColor(requireContext(), com.presently.ui.R.color.jungleMainTextAndButtonColor),
                com.presently.ui.R.drawable.ic_tiger,
                true
            ),
            Theme(
                "Monstera",
                ContextCompat.getColor(requireContext(), com.presently.ui.R.color.monsteraToolbarColor),
                ContextCompat.getColor(requireContext(), com.presently.ui.R.color.monsteraBackgroundColor),
                ContextCompat.getColor(requireContext(), com.presently.ui.R.color.monsteraToolbarItemColor),
                ContextCompat.getColor(requireContext(), com.presently.ui.R.color.monsteraMainTextAndButtonColor),
                com.presently.ui.R.drawable.ic_monstera
            ),
            Theme(
                "Field",
                ContextCompat.getColor(requireContext(), com.presently.ui.R.color.fieldToolbarColor),
                ContextCompat.getColor(requireContext(), com.presently.ui.R.color.fieldBackgroundColor),
                ContextCompat.getColor(requireContext(), com.presently.ui.R.color.fieldToolbarItemColor),
                ContextCompat.getColor(requireContext(), com.presently.ui.R.color.fieldMainTextAndButtonColor),
                com.presently.ui.R.drawable.ic_field
            ),
            Theme(
                "Clouds",
                ContextCompat.getColor(requireContext(), com.presently.ui.R.color.cloudsToolbarColor),
                ContextCompat.getColor(requireContext(), com.presently.ui.R.color.cloudsBackgroundColor),
                ContextCompat.getColor(requireContext(), com.presently.ui.R.color.cloudsToolbarItemColor),
                ContextCompat.getColor(requireContext(), com.presently.ui.R.color.cloudsMainTextAndButtonColor),
                com.presently.ui.R.drawable.clouds
            ),
            Theme(
                "Wesley",
                ContextCompat.getColor(requireContext(), com.presently.ui.R.color.wesleyToolbarColor),
                ContextCompat.getColor(requireContext(), com.presently.ui.R.color.wesleyBackgroundColor),
                ContextCompat.getColor(requireContext(), com.presently.ui.R.color.wesleyToolbarItemColor),
                ContextCompat.getColor(requireContext(), com.presently.ui.R.color.wesleyMainTextAndButtonColor),
                com.presently.ui.R.drawable.ic_cube
            ),
            Theme(
                "Beach",
                ContextCompat.getColor(requireContext(), com.presently.ui.R.color.beachToolbarColor),
                ContextCompat.getColor(requireContext(), com.presently.ui.R.color.beachBackgroundColor),
                ContextCompat.getColor(requireContext(), com.presently.ui.R.color.beachToolbarItemColor),
                ContextCompat.getColor(requireContext(), com.presently.ui.R.color.beachMainTextAndButtonColor),
                com.presently.ui.R.drawable.ic_shell
            ),
            Theme(
                "Ellen",
                ContextCompat.getColor(requireContext(), com.presently.ui.R.color.ellenToolbarColor),
                ContextCompat.getColor(requireContext(), com.presently.ui.R.color.ellenBackgroundColor),
                ContextCompat.getColor(requireContext(), com.presently.ui.R.color.ellenToolbarItemColor),
                ContextCompat.getColor(requireContext(), com.presently.ui.R.color.ellenMainTextAndButtonColor),
                com.presently.ui.R.drawable.ic_ellen,
                true
            ),
            Theme(
                "Western",
                ContextCompat.getColor(requireContext(), com.presently.ui.R.color.westernToolbarColor),
                ContextCompat.getColor(requireContext(), com.presently.ui.R.color.westernBackgroundColor),
                ContextCompat.getColor(requireContext(), com.presently.ui.R.color.westernToolbarItemColor),
                ContextCompat.getColor(requireContext(), com.presently.ui.R.color.westernMainTextAndButtonColor),
                com.presently.ui.R.drawable.ic_cactus
            ),
            Theme(
                "Lotus",
                ContextCompat.getColor(requireContext(), com.presently.ui.R.color.lotusToolbarColor),
                ContextCompat.getColor(requireContext(), com.presently.ui.R.color.lotusBackgroundColor),
                ContextCompat.getColor(requireContext(), com.presently.ui.R.color.lotusToolbarItemColor),
                ContextCompat.getColor(requireContext(), com.presently.ui.R.color.lotusMainTextAndButtonColor),
                com.presently.ui.R.drawable.ic_lotus
            ),
            Theme(
                "Sunset",
                ContextCompat.getColor(requireContext(), com.presently.ui.R.color.sunsetToolbarColor),
                ContextCompat.getColor(requireContext(), com.presently.ui.R.color.sunsetBackgroundColor),
                ContextCompat.getColor(requireContext(), com.presently.ui.R.color.sunsetToolbarItemColor),
                ContextCompat.getColor(requireContext(), com.presently.ui.R.color.sunsetAndroidWidgetColor),
                com.presently.ui.R.drawable.ic_sun_icon
            ),
            Theme(
                "Danah",
                ContextCompat.getColor(requireContext(), com.presently.ui.R.color.danahToolbarColor),
                ContextCompat.getColor(requireContext(), com.presently.ui.R.color.danahBackgroundColor),
                ContextCompat.getColor(requireContext(), com.presently.ui.R.color.danahToolbarItemColor),
                ContextCompat.getColor(requireContext(), com.presently.ui.R.color.danahMainTextAndButtonColor),
                com.presently.ui.R.drawable.ic_danah,
                true
            ),
            Theme(
                "Glacier",
                ContextCompat.getColor(requireContext(), com.presently.ui.R.color.glacierToolbarColor),
                ContextCompat.getColor(requireContext(), com.presently.ui.R.color.glacierBackgroundColor),
                ContextCompat.getColor(requireContext(), com.presently.ui.R.color.glacierToolbarItemColor),
                ContextCompat.getColor(requireContext(), com.presently.ui.R.color.glacierMainTextAndButtonColor),
                com.presently.ui.R.drawable.ic_cube
            ),
            Theme(
                "Rosie",
                ContextCompat.getColor(requireContext(), com.presently.ui.R.color.rosieToolbarColor),
                ContextCompat.getColor(requireContext(), com.presently.ui.R.color.rosieBackgroundColor),
                ContextCompat.getColor(requireContext(), com.presently.ui.R.color.rosieToolbarItemColor),
                ContextCompat.getColor(requireContext(), com.presently.ui.R.color.rosieMainTextAndButtonColor),
                com.presently.ui.R.drawable.ic_rosie,
                true
            ),
            Theme(
                "Julie",
                ContextCompat.getColor(requireContext(), com.presently.ui.R.color.julieToolbarColor),
                ContextCompat.getColor(requireContext(), com.presently.ui.R.color.julieBackgroundColor),
                ContextCompat.getColor(requireContext(), com.presently.ui.R.color.julieToolbarItemColor),
                ContextCompat.getColor(requireContext(), com.presently.ui.R.color.julieMainTextAndButtonColor),
                com.presently.ui.R.drawable.ic_julie,
                true
            ),
            Theme(
                "Ahalya",
                ContextCompat.getColor(requireContext(), com.presently.ui.R.color.ahalyaToolbarColor),
                ContextCompat.getColor(requireContext(), com.presently.ui.R.color.ahalyaBackgroundColor),
                ContextCompat.getColor(requireContext(), com.presently.ui.R.color.ahalyaToolbarItemColor),
                ContextCompat.getColor(requireContext(), com.presently.ui.R.color.ahalyaMainTextAndButtonColor),
                com.presently.ui.R.drawable.ic_butterfly,
                true
            ),
            Theme(
                "Moonlight",
                ContextCompat.getColor(requireContext(), com.presently.ui.R.color.moonlightToolbarColor),
                ContextCompat.getColor(requireContext(), com.presently.ui.R.color.moonlightBackgroundColor),
                ContextCompat.getColor(requireContext(), com.presently.ui.R.color.moonlightToolbarItemColor),
                ContextCompat.getColor(requireContext(), com.presently.ui.R.color.moonlightMainTextAndButtonColor),
                com.presently.ui.R.drawable.ic_moon
            ),
            Theme(
                "Ivy",
                ContextCompat.getColor(requireContext(), com.presently.ui.R.color.ivyToolbarColor),
                ContextCompat.getColor(requireContext(), com.presently.ui.R.color.ivyBackgroundColor),
                ContextCompat.getColor(requireContext(), com.presently.ui.R.color.ivyToolbarItemColor),
                ContextCompat.getColor(requireContext(), com.presently.ui.R.color.ivyMainTextAndButtonColor),
                com.presently.ui.R.drawable.ic_flower
            ),
            Theme(
                "Moss",
                ContextCompat.getColor(requireContext(), com.presently.ui.R.color.mossToolbarColor),
                ContextCompat.getColor(requireContext(), com.presently.ui.R.color.mossBackgroundColor),
                ContextCompat.getColor(requireContext(), com.presently.ui.R.color.mossToolbarItemColor),
                ContextCompat.getColor(requireContext(), com.presently.ui.R.color.mossMainTextAndButtonColor),
                com.presently.ui.R.drawable.ic_flower
            ),
            Theme(
                "Gelato",
                ContextCompat.getColor(requireContext(), com.presently.ui.R.color.gelatoToolbarColor),
                ContextCompat.getColor(requireContext(), com.presently.ui.R.color.gelatoBackgroundColor),
                ContextCompat.getColor(requireContext(), com.presently.ui.R.color.gelatoToolbarItemColor),
                ContextCompat.getColor(requireContext(), com.presently.ui.R.color.gelatoMainTextAndButtonColor),
                com.presently.ui.R.drawable.ic_flower
            ),
            Theme(
                "Clean",
                ContextCompat.getColor(requireContext(), com.presently.ui.R.color.cleanToolbarColor),
                ContextCompat.getColor(requireContext(), com.presently.ui.R.color.cleanBackgroundColor),
                ContextCompat.getColor(requireContext(), com.presently.ui.R.color.cleanToolbarItemColor),
                ContextCompat.getColor(requireContext(), com.presently.ui.R.color.cleanMainTextAndButtonColor),
                com.presently.ui.R.drawable.ic_flower
            )
        )
        adapter.addData(themeList)

        // Set the adapter
        binding.themes.layoutManager = GridLayoutManager(context, 3)
        binding.themes.adapter = adapter

        binding.backIcon.setOnClickListener {
            parentFragmentManager.popBackStack()
        }

        ViewCompat.setOnApplyWindowInsetsListener(binding.themeContainer) { v, insets ->
            v.updatePadding(top = insets.getInsets(WindowInsetsCompat.Type.statusBars()).top)
            insets
        }

        val window = requireActivity().window
        val typedValue = TypedValue()
        requireActivity().theme.resolveAttribute(com.presently.sharing.com.presently.ui.R.attr.toolbarColor, typedValue, true)
        setStatusBarColorsForBackground(window, typedValue.data)
        window.statusBarColor = typedValue.data
    }

    interface OnThemeSelectedListener {
        fun onThemeSelected(theme: String)
        fun onDesignerClicked(designer: Designer)
    }

}
