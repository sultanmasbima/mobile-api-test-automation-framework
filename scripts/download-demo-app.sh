#!/usr/bin/env bash
# Downloads the public Sauce Labs "My Demo App" APK used by the mobile tests.
# APK files are not committed to Git (binary, ~18 MB); this script fetches it instead.
set -euo pipefail

VERSION="2.3.0"
APK="mda-${VERSION}-27.apk"
TARGET_DIR="$(cd "$(dirname "$0")/.." && pwd)/apps"

mkdir -p "$TARGET_DIR"
if [[ -f "$TARGET_DIR/$APK" ]]; then
  echo "Already downloaded: apps/$APK"
  exit 0
fi

curl -fSL -o "$TARGET_DIR/$APK" \
  "https://github.com/saucelabs/my-demo-app-android/releases/download/${VERSION}/${APK}"
echo "Downloaded: apps/$APK"
