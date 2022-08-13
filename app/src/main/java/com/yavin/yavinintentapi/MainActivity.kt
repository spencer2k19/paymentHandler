package com.yavin.yavinintentapi

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.yavin.yavinintentapi.databinding.ActivityMainBinding
import com.yavin.yavinintentapi.ui.main.payment.PaymentFragment
import com.yavin.yavinintentapi.ui.main.ViewPagerAdapter
import com.yavin.yavinintentapi.ui.main.print.PrintFragment
import com.yavin.yavinintentapi.ui.main.share.ShareFragment
import com.yavin.yavinintentapi.ui.main.transactions.TransactionsFragment


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val viewPager: ViewPager2 = binding.viewPager

        val fragments = arrayListOf(
            PaymentFragment.newInstance(),
            PrintFragment.newInstance(),
            TransactionsFragment.newInstance(),
            ShareFragment.newInstance(),
        )

        val adapter = ViewPagerAdapter(this, fragments)
        viewPager.adapter = adapter
        adapter.setupTabLayout(binding.tabLayout, viewPager)
    }
}