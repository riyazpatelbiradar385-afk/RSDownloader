FREE APK BUILD (GitHub Actions)

1. Sign in to GitHub and create a NEW PUBLIC or PRIVATE repository named RSDownloader.
2. Upload the CONTENTS of this folder (not the outer RSDownloader folder) to the repository root. Make sure .github/workflows/build-apk.yml is included. On mobile, enable "Show hidden files" if needed; alternatively upload the workflow file separately in GitHub's web editor at .github/workflows/build-apk.yml.
3. Open the repository's Actions tab.
4. Select "Build RS Downloader APK" and tap "Run workflow" (or wait for the first push build).
5. Open the completed run, scroll to Artifacts, and download RS-Downloader-debug-apk.
6. Extract the downloaded artifact ZIP; install app-debug.apk on your Android phone. Allow installation from your browser/file manager if Android asks.

This builds a debug APK. It is for authorized audio URLs/files only; it does not extract audio from YouTube.
