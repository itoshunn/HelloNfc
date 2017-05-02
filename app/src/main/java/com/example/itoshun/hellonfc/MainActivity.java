package com.example.itoshun.hellonfc;

import android.app.PendingIntent;
import android.content.Intent;
import android.nfc.NfcAdapter;
import android.nfc.tech.NfcA;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    private NfcAdapter mNfcAdapter; // nfc adapter

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // NFC を扱うためのインスタンスを取得
        mNfcAdapter = NfcAdapter.getDefaultAdapter(this);

    }

    // レジューム（動作中保持）

    @Override
    protected void onResume() {
        super.onResume();

        // NFC がかざされた場合に優先的に受け取る
        Intent intent = new Intent(this, this.getClass());
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(
                getApplicationContext(), 0, intent, 0);
        mNfcAdapter.enableForegroundDispatch(this, pendingIntent, null, null);

    }

    // バックグラウンド時の優先動作
    @Override
    protected void onPause(){
        super.onPause();

        // Activity がバッググラウンドに回った場合、停止する。
        mNfcAdapter.disableForegroundDispatch((this));
    }

    @Override
    protected void onNewIntent(Intent intent){
        super.onNewIntent(intent);

        // NFC-UID を取得する
        byte[] uid = intent.getByteArrayExtra(NfcAdapter.EXTRA_ID);

        // NFC-UID を文字列に変換して表示する
        Toast.makeText(this, Arrays.toString(uid), Toast.LENGTH_SHORT).show();

    }

}
