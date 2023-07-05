package com.train.educate.happysever.act

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat.startActivity
import com.train.educate.happysever.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {

    override fun hasBackButton(): Boolean {
        return false
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }

    override fun init(savedInstanceState: Bundle?) {
        new_game.setOnClickListener {
            startActivity(Intent(this@MainActivity, RobotGameActivity::class.java))
        }
        fight.setOnClickListener {
            startActivity(Intent(this@MainActivity, PersonGameActivity::class.java))
        }
        conn_fight.setOnClickListener {
            startActivity(Intent(this@MainActivity, ConnectionActivity::class.java))
        }
        conn_about.setOnClickListener {
            val b = AlertDialog.Builder(this@MainActivity)
            b.setTitle(getString(R.string.about) + ":" + getString(R.string.app_name))
            var version = "v1.1"
            try {
                var pkgInfo = packageManager.getPackageInfo(packageName, 0)
                version = "v" + pkgInfo.versionName
            } catch (aa: Throwable) {

            }

            b.setMessage("Version: " + version)
            b.setPositiveButton(R.string.ok) { dialog, which -> }
            b.show()
        }
    }
}
