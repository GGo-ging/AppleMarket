package com.example.applemarket

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.media.AudioAttributes
import android.media.RingtoneManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ImageView
import android.widget.Spinner
import android.widget.Toast
//import android.support.v7.widget.DividerItemDecoration
//import android.support.v7.widget.LinearLayoutManager
//import android.support.v7.widget.RecyclerView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.accessibility.AccessibilityManagerCompat.TouchExplorationStateChangeListener
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.applemarket.Data.Product
import com.example.applemarket.adapter.ProductAdapter

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        addressSpinner()

        val alarm_notifi: ImageView = findViewById(R.id.alarm_notifi)
        alarm_notifi.setOnClickListener {
            notificationAlarm()
        }

        val recyclerView : RecyclerView = findViewById(R.id.mainRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))

        val products = listOf(
            Product(1, "산진 한달된 선풍기 팝니다", R.drawable.sample1, 1000, "대현동", "서울 서대문구 창천동", "이사가서 필요가 없어졌어요 급하게 내놓습니다"),
            Product(2, "Product 2", R.drawable.sample2, 20000, "seller 2", "address 2", "desciption 2"),
            Product(3, "Product 3", R.drawable.sample3, 30000, "seller 3", "address 3", "desciption 3"),
            Product(4, "Product 4", R.drawable.sample4, 40000, "seller 4", "address 4", "desciption 4"),
            Product(5, "Product 5", R.drawable.sample5, 50000, "seller 5", "address 5", "desciption 5"),
            Product(6, "Product 6", R.drawable.sample6, 60000, "seller 6", "address 6", "desciption 6"),
            Product(7, "Product 7", R.drawable.sample7, 70000, "seller 7", "address 7", "desciption 7"),
            Product(8, "Product 8", R.drawable.sample8, 80000, "seller 8", "address 8", "desciption 8"),
            Product(9, "Product 9", R.drawable.sample9, 90000, "seller 9", "address 9", "desciption 9"),
            Product(10, "Product 10", R.drawable.sample10, 100000, "seller 10", "address 10", "desciption 10")

        )

        val adapter = ProductAdapter(products) { product ->
            val intent = Intent(this, ProductDetailActivity::class.java)
            intent.putExtra("product", product)
            startActivity(intent)
        }

        recyclerView.adapter = adapter
    }

    override fun onBackPressed() {

        AlertDialog.Builder(this)
            .setMessage("종료하시겠습니까?")
            .setPositiveButton("확인") { _, _ ->
                super.onBackPressed()
                finish()}
            .setNegativeButton("취소", null)
            .show()
    }

    private fun addressSpinner() {
        val locationSpinner: Spinner = findViewById(R.id.locationSpinenr)

        val items = listOf("내배캠동")

        val myAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, items)
        locationSpinner.adapter = myAdapter

        locationSpinner.onItemSelectedListener = object  : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                when (position) {
                    0 -> {
                        Toast.makeText(this@MainActivity, "내배캠동 선택됨", Toast.LENGTH_SHORT).show()
                    }
                    else -> {
                        Toast.makeText(this@MainActivity, "false", Toast.LENGTH_SHORT).show()
                    }

                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                Toast.makeText(this@MainActivity, "onNothingSelected", Toast.LENGTH_SHORT).show()
            }

        }
    }

    private fun notificationAlarm() {

        val manager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager

        val builder: NotificationCompat.Builder
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            // 26 버전 이상
            val channelId="AppleMarketChannel"
            val channelName="AppleMarket"
            val channel = NotificationChannel(
                channelId,
                channelName,
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply {
                // 채널에 다양한 정보 설정
                description = "My Channel One Description"
                setShowBadge(true)
                val uri: Uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
                val audioAttributes = AudioAttributes.Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    .setUsage(AudioAttributes.USAGE_ALARM)
                    .build()
                setSound(uri, audioAttributes)
                enableVibration(true)
            }
            // 채널을 NotificationManager에 등록
            manager.createNotificationChannel(channel)

            // 채널을 이용하여 builder 생성
            builder = NotificationCompat.Builder(this, channelId)

        }else {
            // 26 버전 이하
            builder = NotificationCompat.Builder(this)
        }

//        val bitmap = BitmapFactory.decodeResource(resources, R.drawable.baseline_notifications_none_24)
        val intent = Intent(this, ProductDetailActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
//        val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)
        // 알림의 기본 정보
        builder.run {
            setSmallIcon(R.drawable.baseline_notifications_none_24)
            setWhen(System.currentTimeMillis())
            setContentTitle("새로운 알림입니다.")
            setContentText("알람 확인")

            manager.notify(1, builder.build())
        }


        manager.notify(11, builder.build())
    }


}
