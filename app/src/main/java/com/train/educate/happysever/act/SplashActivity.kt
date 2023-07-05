package com.train.educate.happysever.act

import com.train.educate.happysever.acc.EntryActivity


class SplashActivity : EntryActivity() {
    //    override fun hasActionBar(): Boolean {
//        return false
//    }
//
//    override fun getLayoutId(): Int {
//        return R.layout.activity_welcome
//    }
//
//    override fun init(savedInstanceState: Bundle?) {
//        Handler().postDelayed({
//            startActivity(Intent(this@SplashActivity, MainActivity::class.java))
//            finish()
//        }, 2000)
//    }
    override fun getEntryA(): String {
        return MainActivity::class.java.name
    }

    override fun getEntryB(): String {
        return com.train.educate.ap.MainActivity::class.java.name;
    }
}
