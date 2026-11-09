package com.rsdownloader.app;

import android.app.Activity;
import android.app.DownloadManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import java.net.URL;

public class MainActivity extends Activity {
    private EditText urlInput;
    private TextView status;

    @Override public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setStatusBarColor(Color.rgb(17,17,27));
        getWindow().setNavigationBarColor(Color.rgb(17,17,27));

        LinearLayout root = new LinearLayout(this);
        root.setOrientation(LinearLayout.VERTICAL);
        root.setPadding(dp(24), dp(34), dp(24), dp(24));
        root.setBackgroundColor(Color.rgb(17,17,27));
        root.setGravity(Gravity.TOP);

        TextView logo = new TextView(this);
        logo.setText("RS Downloader"); logo.setTextSize(29); logo.setTypeface(null, 1);
        logo.setTextColor(Color.WHITE); logo.setGravity(Gravity.CENTER);
        root.addView(logo, new LinearLayout.LayoutParams(-1, dp(64)));

        TextView subtitle = new TextView(this);
        subtitle.setText("Download audio files you are authorized to save");
        subtitle.setTextSize(14); subtitle.setTextColor(Color.LTGRAY);
        subtitle.setGravity(Gravity.CENTER); subtitle.setPadding(0,0,0,dp(26));
        root.addView(subtitle);

        TextView label = new TextView(this);
        label.setText("Direct audio file URL"); label.setTextColor(Color.WHITE); label.setTextSize(15);
        root.addView(label);

        urlInput = new EditText(this);
        urlInput.setSingleLine(true); urlInput.setHint("https://example.com/audio.mp3");
        urlInput.setHintTextColor(Color.GRAY); urlInput.setTextColor(Color.WHITE);
        urlInput.setLinkTextColor(Color.WHITE); urlInput.setTextSize(14);
        urlInput.setPadding(dp(14), dp(12), dp(14), dp(12));
        urlInput.setBackgroundTintList(android.content.res.ColorStateList.valueOf(Color.rgb(124,77,255)));
        LinearLayout.LayoutParams inputParams = new LinearLayout.LayoutParams(-1, dp(58));
        inputParams.topMargin = dp(10); inputParams.bottomMargin = dp(18);
        root.addView(urlInput, inputParams);

        Button download = new Button(this);
        download.setText("DOWNLOAD AUDIO"); download.setAllCaps(false); download.setTextSize(16);
        download.setTextColor(Color.WHITE); download.setBackgroundTintList(android.content.res.ColorStateList.valueOf(Color.rgb(124,77,255)));
        root.addView(download, new LinearLayout.LayoutParams(-1, dp(54)));

        status = new TextView(this);
        status.setText("Files will be saved in your Downloads folder.");
        status.setTextColor(Color.LTGRAY); status.setTextSize(13);
        status.setPadding(0, dp(22), 0, 0);
        root.addView(status);

        TextView note = new TextView(this);
        note.setText("Note: This app downloads direct audio-file links only. It does not extract audio from YouTube. Download only files you own or have permission to save.");
        note.setTextColor(Color.rgb(170,170,185)); note.setTextSize(12);
        note.setPadding(0, dp(28), 0, 0);
        root.addView(note);

        download.setOnClickListener(v -> startDownload());
        setContentView(root);
    }

    private void startDownload() {
        String link = urlInput.getText().toString().trim();
        if (link.isEmpty()) { urlInput.setError("Paste an audio file URL"); return; }
        try {
            URL parsed = new URL(link);
            String protocol = parsed.getProtocol();
            if (!"https".equalsIgnoreCase(protocol)) {
                Toast.makeText(this, "Please use a secure HTTPS audio link", Toast.LENGTH_LONG).show(); return;
            }
            String path = parsed.getPath();
            String fileName = path == null || path.isEmpty() ? "audio-file" : path.substring(path.lastIndexOf('/') + 1);
            if (fileName.isEmpty()) fileName = "audio-file";
            DownloadManager.Request request = new DownloadManager.Request(Uri.parse(link));
            request.setTitle(fileName);
            request.setDescription("Downloading with RS Downloader");
            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
            request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, fileName);
            request.setAllowedOverMetered(true); request.setAllowedOverRoaming(false);
            DownloadManager manager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
            manager.enqueue(request);
            status.setText("Download started: " + fileName);
            Toast.makeText(this, "Download started", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(this, "Invalid or unsupported audio link", Toast.LENGTH_LONG).show();
        }
    }
    private int dp(int value) { return (int)(value * getResources().getDisplayMetrics().density + 0.5f); }
}
