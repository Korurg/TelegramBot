#!/usr/bin/env /bin/sh

LIBS_PATH="${TELEGRAM_BOT_LIBS_PATH:./libs}"
java -jar ./telegram-bot.jar --bot.libs="$LIBS_PATH"
