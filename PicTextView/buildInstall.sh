#!/bin/bash
gradle clean
gradle assembleDebug
adb install -r app/build/outputs/apk/app-debug.apk
